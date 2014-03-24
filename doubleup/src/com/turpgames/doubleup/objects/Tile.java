package com.turpgames.doubleup.objects;

import java.util.Stack;

import com.turpgames.framework.v0.effects.BreathEffect;
import com.turpgames.framework.v0.effects.moving.IMovingEffectSubject;
import com.turpgames.framework.v0.effects.scaling.IScaleEffectSubject;
import com.turpgames.framework.v0.effects.scaling.ScaleUpEffect;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.TextureDrawer;

class Tile extends GameObject implements IScaleEffectSubject,
		IMovingEffectSubject {

	private static Stack<Tile> tileCache = new Stack<Tile>();

	static {
		for (int i = 0; i < 16; i++) {
			tileCache.push(new Tile());
		}
	}

	static Tile popTile() {
		Tile tile = tileCache.pop();
//		tile.requiresPopEffect = true;
		tile.isActive = true;
		return tile;
	}

	private final Text text;

//	private final BreathEffect newSumEffect;
//	private final ScaleUpEffect popEffect;

	private boolean isActive;

	private int value;

//	private boolean requiresPopEffect;
//	private boolean requiresSumEffect;

	private Tile() {
		text = new Text();
		text.setAlignment(Text.HAlignCenter, Text.VAlignCenter);
		text.setSize(Cell.size, Cell.size);
		text.setLocation(getLocation().x, getLocation().y);
		text.getColor().set(Color.white());

//		newSumEffect = new BreathEffect(this);
//		newSumEffect.setDuration(0.3f);
//		newSumEffect.setLooping(false);
//		newSumEffect.setMinFactor(0.9f);
//		newSumEffect.setMaxFactor(1.1f);
//
//		popEffect = new ScaleUpEffect(this);
//		popEffect.setDuration(0.3f);
//		popEffect.setLooping(false);
//		popEffect.setMinScale(0.2f);
//		popEffect.setMaxScale(1f);
	}

	int getValue() {
		return value;
	}

//	void beginUpdate() {
//		requiresPopEffect = false;
//		requiresSumEffect = false;
//	}

	int addTo(Tile toTile) {
		toTile.setValue(toTile.value + this.value);
		this.dispose();
		return toTile.value;
	}

	void moveToCell(Cell cell) {
		setLocation(cell.getLocation().x, cell.getLocation().y);
	}

	void setValue(int value) {
//		if (value == this.value * 2)
//			requiresSumEffect = true;

		this.value = value;
		this.text.setText(value + "");
		this.getColor().set(getColor(value));
	}

//	void endUpdate() {
//		if (requiresPopEffect) {
//			popEffect.start();
//		} else if (requiresSumEffect) {
//			newSumEffect.start();
//		}
//	}

	void dispose() {
		setValue(0);
		isActive = false;
//		requiresPopEffect = false;
//		requiresSumEffect = false;
		tileCache.push(this);
	}

	// private void move(Cell from, Cell to, final boolean runNewSumEffectOnEnd)
	// {
	// if (from == null) {
	// setLocation(to.getLocation().x, to.getLocation().y);
	// if (runNewSumEffectOnEnd) {
	// newSumEffect.start();
	// }
	// } else {
	// moveEffect.setFrom(from.getLocation().x, from.getLocation().y);
	// moveEffect.setTo(to.getLocation().x, to.getLocation().y);
	// if (runNewSumEffectOnEnd) {
	// moveEffect.start(new IEffectEndListener() {
	// @Override
	// public boolean onEffectEnd(Object obj) {
	// newSumEffect.start();
	// return true;
	// }
	// });
	// }
	// }
	// }

	@Override
	public void setScale(float scale) {
		super.getScale().set(scale);
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
	private final static Color color1 = Color.fromHex("#fba51a00");
	private final static Color color2 = Color.fromHex("#f4eb2000");
	private final static Color color4 = Color.fromHex("#aed36100");
	private final static Color color8 = Color.fromHex("#71c05500");
	private final static Color color16 = Color.fromHex("#71c6a500");
	private final static Color color32 = Color.fromHex("#40b8ea00");
	private final static Color color64 = Color.fromHex("#436fb600");
	private final static Color color128 = Color.fromHex("#5b52a300");
	private final static Color color256 = Color.fromHex("#9a6db000");
	private final static Color color512 = Color.fromHex("#d1499b00");
	private final static Color color1024 = Color.fromHex("#f15f9000");
	private final static Color color2048 = Color.fromHex("#ed1e2400");
	private final static Color color4096 = Color.fromHex("#00000000");
}
