package com.turpgames.doubleup.objects;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.effects.BreathEffect;
import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.effects.moving.IMovingEffectSubject;
import com.turpgames.framework.v0.effects.moving.MovingEffect;
import com.turpgames.framework.v0.effects.scaling.IScaleEffectSubject;
import com.turpgames.framework.v0.effects.scaling.ScaleUpEffect;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.TextureDrawer;

class Tile extends GameObject implements IMovingEffectSubject, IScaleEffectSubject {

	private final Text text;
	private boolean isActive;
	private long value;

	private final static List<TileCommand> commands = new ArrayList<TileCommand>();

	private final TilePopCommand popCommand;
	private final TileAddCommand addCommand;
	private final TileMoveCommand moveCommand;

	private final ScaleUpEffect popEffect;
	private final BreathEffect addEffect;
	private final MovingEffect moveEffect;
	
	private final MoveEffectEndListener moveEffectEndListener;

	public Tile() {
		text = new Text();
		text.setAlignment(Text.HAlignCenter, Text.VAlignCenter);
		text.setSize(Cell.size, Cell.size);
		text.getColor().set(Color.white());
		text.setFontScale(4f / Table.matrixSize);

		setWidth(Cell.size);
		setHeight(Cell.size);
		
		popCommand = new TilePopCommand();
		popCommand.tile = this;
		
		addCommand = new TileAddCommand();
		addCommand.from = this;
		
		moveCommand = new TileMoveCommand();
		moveCommand.tile = this;
		
		popEffect= new ScaleUpEffect(this);
		popEffect.setDuration(0.2f);
		popEffect.setLooping(false);
		popEffect.setMaxScale(1.0f);
		popEffect.setMinScale(0.2f);
		popEffect.setListener(new IEffectEndListener() {
			@Override
			public boolean onEffectEnd(Object obj) {
				Tile.this.getScale().set(1f);
				return true;
			}
		});
		
		addEffect = new BreathEffect(this);
		addEffect.setMinFactor(0.9f);
		addEffect.setMaxFactor(1.1f);
		addEffect.setFinalScale(1f);
		addEffect.setLooping(false);
		addEffect.setDuration(0.2f);

		moveEffectEndListener = new MoveEffectEndListener();
		
		moveEffect = new MovingEffect(this);
		moveEffect.setLooping(false);
		moveEffect.setDuration(0.1f);
		moveEffect.setListener(moveEffectEndListener);
	}

	long getValue() {
		return value;
	}

	void setValue(long value) {
		this.value = value;
		this.isActive = value > 0;
		this.text.setText(value + "");
		this.getColor().set(getColor(value));
	}

	void addTo(Tile toTile) {
		addCommand.to = toTile;
		addCommand(addCommand);
	}

	void moveToCell(final Cell toCell) {
		moveCommand.to = toCell;
		addCommand(moveCommand);
	}

	void popInCell(Cell cell) {
		popCommand.cell = cell;
		addCommand(popCommand);
	}

	private void addCommand(TileCommand cmd) {
		commands.add(cmd);
	}

	static void executeCommands() {
		for (TileCommand cmd : commands)
			cmd.execute();
		commands.clear();
	}
	
	@Override
	public void setScale(float scale) {
		this.getScale().set(scale);
	}
	
	public void runPopEffect() {
		popEffect.start();
	}

	public void runAddEffect() {
		addEffect.start();
	}

	public void runMoveEffect(final Cell to) {
		moveEffectEndListener.to = to;
		moveEffect.setFrom(this.getLocation());
		moveEffect.setTo(to.getLocation());
		moveEffect.start();
	}

	void syncWithCell(Cell cell) {
		getLocation().set(cell.getLocation());
		getRotation().set(cell.getRotation());
		text.setLocation(cell.getLocation());
	}

	@Override
	public void setLocation(float x, float y) {
		getLocation().set(x, y);
		text.setLocation(x, y);
	}

	@Override
	public void draw() {
		if (!isActive)
			return;

		TextureDrawer.draw(Textures.tile, this);
		text.draw();
	}

	private static Color getColor(long value) {
		switch ((int)value) {
		case 0:
			return color0;
		case 1:
			return color1;
		case 2:
			return color2;
		case 4:
			return color4;
		case 8:
			return color8;
		case 16:
			return color16;
		case 32:
			return color32;
		case 64:
			return color64;
		case 128:
			return color128;
		case 256:
			return color256;
		case 512:
			return color512;
		case 1024:
			return color1024;
		case 2048:
			return color2048;
		default:
			return color4096;
		}
	}

	private final static Color color0 = Color.fromHex("#00000000");
	private final static Color color1 = Color.fromHex("#fba51a60");
	private final static Color color2 = Color.fromHex("#f4eb2060");
	private final static Color color4 = Color.fromHex("#aed36160");
	private final static Color color8 = Color.fromHex("#71c05560");
	private final static Color color16 = Color.fromHex("#71c6a560");
	private final static Color color32 = Color.fromHex("#40b8ea60");
	private final static Color color64 = Color.fromHex("#436fb660");
	private final static Color color128 = Color.fromHex("#5b52a360");
	private final static Color color256 = Color.fromHex("#9a6db060");
	private final static Color color512 = Color.fromHex("#d1499b60");
	private final static Color color1024 = Color.fromHex("#f15f9060");
	private final static Color color2048 = Color.fromHex("#ed1e2460");
	private final static Color color4096 = Color.fromHex("#00000060");
	
	private final class MoveEffectEndListener implements IEffectEndListener {
		Cell to;
		
		@Override
		public boolean onEffectEnd(Object obj) {
			Tile.this.syncWithCell(to);
			return true;
		}
	}
}
