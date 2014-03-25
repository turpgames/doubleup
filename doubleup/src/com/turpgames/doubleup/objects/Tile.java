package com.turpgames.doubleup.objects;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.effects.moving.IMovingEffectSubject;
import com.turpgames.framework.v0.effects.moving.MovingEffect;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.TextureDrawer;

class Tile extends GameObject implements IMovingEffectSubject {

	private final Text text;
	private boolean isActive;
	private int value;
	
	Cell cell;

	private final MovingEffect moveEffect;
	private final List<TileCommand> commands;

	public Tile() {
		text = new Text();
		text.setAlignment(Text.HAlignCenter, Text.VAlignCenter);
		text.setSize(Cell.size, Cell.size);
		text.getColor().set(Color.white());

		setWidth(Cell.size);
		setHeight(Cell.size);

		moveEffect = new MovingEffect(this);
		moveEffect.setLooping(false);
		moveEffect.setDuration(0.1f);

		commands = new ArrayList<TileCommand>();
	}

	int getValue() {
		return value;
	}

	void setValue(int value) {
		this.value = value;
		this.isActive = value > 0;
		this.text.setText(value + "");
		this.getColor().set(getColor(value));
	}

	void addTo(Tile toTile) {
		TileAddCommand cmd = new TileAddCommand();
		cmd.from = this;
		cmd.to = toTile;
		addCommand(cmd);
	}

	void moveToCell(final Cell toCell) {
		TileMoveCommand cmd = new TileMoveCommand();
		cmd.tile = this;
		cmd.to = toCell;
		addCommand(cmd);
	}

	void popInCell(Cell cell) {
		TilePopCommand cmd = new TilePopCommand();
		cmd.cell = cell;
		cmd.tile = this;
		addCommand(cmd);
	}
	
	private void addCommand(TileCommand cmd) {
		//commands.add(cmd);
		cmd.execute();
	}
	
	void executeCommands() {
		for (TileCommand cmd : commands)
			cmd.execute();
		commands.clear();
	}
	
	void setCell(Cell cell) {
		this.cell = cell;
		if (cell != null)
			syncWithCell();
	}

	private void syncWithCell() {
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

	private static Color getColor(int value) {
		switch (value) {
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

}
