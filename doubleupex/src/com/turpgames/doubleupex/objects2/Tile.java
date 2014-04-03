package com.turpgames.doubleupex.objects2;

import com.turpgames.doubleupex.utils.Textures;
import com.turpgames.framework.v0.effects.moving.IMovingEffectSubject;
import com.turpgames.framework.v0.effects.scaling.IScaleEffectSubject;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.TextureDrawer;

public class Tile extends GameObject implements IMovingEffectSubject, IScaleEffectSubject{
	private int value;
	private final Text text;
	private boolean isVisible;
	private boolean isBrick;
	private boolean isUsed;

	private final TilePopEffect popEffect;
	private final TileAddEffect addEffect;
	private final TileMoveEffect moveEffect;

	public Tile() {
		text = new Text();
		text.setAlignment(Text.HAlignCenter, Text.VAlignCenter);
		text.getColor().set(Color.white());

		final float effectDuration = 0.15f;

		popEffect = new TilePopEffect(this, effectDuration);
		addEffect = new TileAddEffect(this, effectDuration);
		moveEffect = new TileMoveEffect(this, effectDuration);
	}

	public void fitToCell(Cell cell) {
		setWidth(cell.getWidth());
		setHeight(cell.getHeight());

		// matrixSize == 4 => fontScale = 1f
		text.setFontScale(4f / cell.getRow().getGrid().getMatrixSize());
		text.setSize(getWidth(), getWidth());
		text.setLocation(cell.getLocation());

		getLocation().set(cell.getLocation());
		getRotation().set(cell.getRotation());
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public int getValue() {
		return isBrick ? 0 : value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public boolean isBrick() {
		return isBrick;
	}

	public void setBrick(boolean isBrick) {
		this.isBrick = isBrick;
	}

	public void updateView() {
		if (isBrick) {
			text.setText("");
			getColor().set(brickColor);
		}
		else {
			text.setText(value + "");
			getColor().set(getColor(value));
		}
	}
	
	public void runPopEffect() {
		popEffect.stop();
		popEffect.start();
	}
	
	public void runAddEffect() {
		addEffect.stop();
		addEffect.start();
	}
	
	public void runMoveEffect(Cell toCell, Cell fromCell, boolean isBeingAdded) {
		moveEffect.stop();
		moveEffect.setToCell(toCell);
		moveEffect.setFromCell(fromCell);
		moveEffect.setBeingAdded(isBeingAdded);
		moveEffect.start();
	}

	@Override
	public void draw() {
		if (!isVisible)
			return;

		TextureDrawer.draw(Textures.tile, this);

		if (!isBrick)
			text.draw();
	}

	@Override
	public void setScale(float scale) {
		this.getScale().set(scale);
	}

	@Override
	public void setLocation(float x, float y) {
		this.getLocation().set(x, y);
		text.setLocation(x, y);
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
}
