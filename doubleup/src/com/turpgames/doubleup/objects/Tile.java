package com.turpgames.doubleup.objects;

import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.effects.moving.IMovingEffectSubject;
import com.turpgames.framework.v0.effects.moving.MovingEffect;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.TextureDrawer;
import com.turpgames.framework.v0.util.Vector;

class Tile extends GameObject implements IMovingEffectSubject {

	private final Text text;
	private boolean isActive;
	private int value;
	private Cell cell;

	private final MovingEffect moveEffect;

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
	}

	int getValue() {
		return value;
	}

	int addTo(Tile toTile) {
		toTile.setValue(toTile.value + this.value);
		this.setValue(0);
		return toTile.value;
	}

	void moveToCell(final Cell toCell, final IMoveCallback callback) {
		if (this.cell == null) {
			setCell(toCell);
			syncWithCell();
			callback.moveEnd(0, false);
		} else {
			cell.setTile(null);
			setCell(toCell);

			moveEffect.setDestination(toCell.getLocation().x, toCell.getLocation().y);
			moveEffect.start(new IEffectEndListener() {
				@Override
				public boolean onEffectEnd(Object obj) {
					syncWithCell();
					callback.moveEnd(0, true);
					return false;
				}
			});
		}
	}

	private void setCell(Cell toCell) {
		cell = toCell;
		toCell.setTile(this);
	}

	void setValue(int value) {
		this.value = value;
		this.isActive = value > 0;
		this.text.setText(value + "");
		this.getColor().set(getColor(value));
	}

	@Override
	public void setLocation(float x, float y) {
		getLocation().set(x, y);
		text.setLocation(x, y);
	}

	@Override
	public void addLocation(Vector v) {
		getLocation().add(v.x, v.y);
		text.setLocation(getLocation().x, getLocation().y);
	}
	
	private void syncWithCell() {
		getLocation().set(cell.getLocation());
		getRotation().set(cell.getRotation());
		text.setLocation(cell.getLocation());
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
