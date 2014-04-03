package com.turpgames.doubleupex.objects;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.doubleupex.utils.Textures;
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
import com.turpgames.framework.v0.util.Timer;

class Tile extends GameObject implements IMovingEffectSubject,
		IScaleEffectSubject {
	private final Text text;
	private int value;

	private final static List<TileCommand> commands = new ArrayList<TileCommand>();

	private final TilePopCommand popCommand;
	private final TileAddCommand addCommand;
	private final TileMoveCommand moveCommand;

	private int popValue;
	private final Timer popEffectDelayTimer;

	private final ScaleUpEffect popEffect;
	private final BreathEffect addEffect;
	private final MovingEffect moveEffect;

	private final MoveEffectEndListener moveEffectEndListener;
	private final TileState state;

	private boolean isBrick;

	public Tile() {
		float size = Table.size / GlobalContext.matrixSize;
		text = new Text();
		text.setAlignment(Text.HAlignCenter, Text.VAlignCenter);
		text.setSize(size, size);
		text.getColor().set(Color.white());
		text.setFontScale(4f / GlobalContext.matrixSize);

		state = new TileState();

		setWidth(size);
		setHeight(size);

		popCommand = new TilePopCommand();
		popCommand.tile = this;

		addCommand = new TileAddCommand();
		addCommand.from = this;

		moveCommand = new TileMoveCommand();
		moveCommand.tile = this;

		final float effectDuration = 0.15f;

		popEffectDelayTimer = new Timer();
		popEffectDelayTimer.setInterval(effectDuration);
		popEffectDelayTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				popEffectDelayTimer.stop();
				popEffect.start();
				popValue = 0;
			}
		});

		popEffect = new ScaleUpEffect(this);
		popEffect.setDuration(effectDuration * 1.5f);
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
		addEffect.setDuration(effectDuration * 1.5f);

		moveEffectEndListener = new MoveEffectEndListener();

		moveEffect = new MovingEffect(this);
		moveEffect.setLooping(false);
		moveEffect.setDuration(effectDuration);
		moveEffect.setListener(moveEffectEndListener);
	}

	int getValue() {
		if (isBrick)
			return 0;
		return value;
	}

	void setBrick(boolean isBrick) {
		this.isBrick = isBrick;
		if (isBrick) {
			this.getColor().set(brickColor);
		} else {
			setValue(value);
		}
	}

	boolean isBrick() {
		return isBrick;
	}

	void setValue(int value) {
		if (isBrick)
			return;
		this.value = value;
		this.text.setText(value + "");
		this.getColor().set(getColor(value));
	}

	void addTo(Cell toCell) {
		if (isBrick)
			return;
		addCommand.to = toCell.getTile();
		addCommand(addCommand);
	}

	void moveToCell(Cell toCell) {
		if (isBrick)
			return;
		moveCommand.to = toCell;
		addCommand(moveCommand);
	}

	void popInCell(Cell cell, int value) {
		if (isBrick)
			return;
		popValue = value;
		popCommand.cell = cell;
		addCommand(popCommand);
	}

	private void addCommand(TileCommand cmd) {
		if (isBrick)
			return;
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
		if (isBrick)
			return;
		popEffectDelayTimer.start();
		setValue(popValue);
	}

	public void runAddEffect() {
		if (isBrick)
			return;
		addEffect.start();
	}

	public void runMoveEffect(final Cell to) {
		if (isBrick)
			return;
		moveEffectEndListener.to = to;
		moveEffect.setFrom(this.getLocation());
		moveEffect.setTo(to.getLocation());
		moveEffect.start();
	}

	void fitToCell(Cell cell) {
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
		if (isBrick) {
			TextureDrawer.draw(Textures.tile, this);
		} else if (value > 0 && popValue == 0) {
			TextureDrawer.draw(Textures.tile, this);
			text.draw();
		}
	}

	public TileState getState() {
		if (value == 0) // pratikte bu if'e girmemesi lazim
			return null;
		state.setValue(value);
		return state;
	}

	public static Tile fromState(TileState tileState) {
		if (tileState == null)
			return null;
		if (tileState.getValue() == 0) // pratikte bu if'e girmemesi lazim
			return null;
		Tile tile = new Tile();
		tile.setValue(tileState.getValue());
		return tile;
	}

	private static Color getColor(long value) {
		switch ((int) value) {
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

	private final static Color brickColor = Color.fromHex("#000000ff");
	private final static Color color0 = Color.fromHex("#00000000");
	private final static Color color1 = Color.fromHex("#fb9d49FF");
	private final static Color color2 = Color.fromHex("#f4d040FF");
	private final static Color color4 = Color.fromHex("#aed361FF");
	private final static Color color8 = Color.fromHex("#71c055FF");
	private final static Color color16 = Color.fromHex("#71c6a5FF");
	private final static Color color32 = Color.fromHex("#40b8eaFF");
	private final static Color color64 = Color.fromHex("#039fd6FF");
	private final static Color color128 = Color.fromHex("#5b52a3FF");
	private final static Color color256 = Color.fromHex("#9a6db0FF");
	private final static Color color512 = Color.fromHex("#d1499bFF");
	private final static Color color1024 = Color.fromHex("#f15f90FF");
	private final static Color color2048 = Color.fromHex("#ed1e24FF");
	private final static Color color4096 = Color.fromHex("#000000F0");

	private final class MoveEffectEndListener implements IEffectEndListener {
		Cell to;

		@Override
		public boolean onEffectEnd(Object obj) {
			Tile.this.fitToCell(to);
			return true;
		}
	}
}