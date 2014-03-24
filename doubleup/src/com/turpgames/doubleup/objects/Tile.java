package com.turpgames.doubleup.objects;

import java.util.Stack;

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
		tile.requiresDispose = false;
		tile.isActive = true;
		return tile;
	}

	private final Text text;

	private final BreathEffect newSumEffect;
	private final ScaleUpEffect popEffect;
	private final MovingEffect moveEffect;

	private boolean isActive;

	private int value;
	private Cell currentCell;

	private Cell prevCell;
	private boolean requiresDispose;
	private boolean requiresPopEffect;
	private boolean requiresMoveEffect;
	private boolean requiresSumEffect;

	private Tile() {
		text = new Text();
		text.setAlignment(Text.HAlignCenter, Text.VAlignCenter);
		text.setSize(Cell.size, Cell.size);
		text.setLocation(getLocation().x, getLocation().y);
		text.getColor().set(Color.white());

		newSumEffect = new BreathEffect(this);
		newSumEffect.setDuration(0.3f);
		newSumEffect.setLooping(false);
		newSumEffect.setMinFactor(0.9f);
		newSumEffect.setMaxFactor(1.1f);

		popEffect = new ScaleUpEffect(this);
		popEffect.setDuration(0.3f);
		popEffect.setLooping(false);
		popEffect.setMinScale(0.2f);
		popEffect.setMaxScale(1f);

		moveEffect = new MovingEffect(this);
		moveEffect.setDuration(0.3f);
		moveEffect.setLooping(false);
	}

	public int getValue() {
		return value;
	}

	void beginUpdate() {
		requiresDispose = false;
		requiresPopEffect = false;
		requiresSumEffect = false;
		requiresMoveEffect = false;
		prevCell = currentCell;
	}

	void dispose() {
		requiresDispose = true;
	}

	void moveToCell(Cell cell) {
		currentCell = cell;
		requiresMoveEffect = true;
	}

	void setValue(int value) {
		if (this.value == 0)
			requiresPopEffect = true;
		else if (value == this.value * 2)
			requiresSumEffect = true;

		this.value = value;
		this.text.setText(value + "");
		this.getColor().set(getColor(value));
	}

	void setCell(Cell cell) {
		currentCell = cell;
		setLocation(cell.getLocation().x, cell.getLocation().y);
	}

	void endUpdate() {
		if (requiresDispose) {
			doDispose();
		} else if (requiresPopEffect) {
			popEffect.start();
			move(prevCell, currentCell, false);
		} else if (requiresSumEffect) {
			if (requiresMoveEffect) {
				move(prevCell, currentCell, true);
			} else {
				newSumEffect.start();
			}
		} else if (requiresMoveEffect) {
			move(prevCell, currentCell, false);
		}
	}

	private void doDispose() {
		if (!requiresDispose)
			return;
		isActive = false;
		requiresDispose = false;
		value = 0;
		currentCell = null;
		tileCache.push(this);
	}

	private void move(Cell from, Cell to, final boolean runNewSumEffectOnEnd) {
		if (from == null) {
			setLocation(to.getLocation().x, to.getLocation().y);
			if (runNewSumEffectOnEnd) {
				newSumEffect.start();
			}
		} else {
			moveEffect.setFrom(from.getLocation().x, from.getLocation().y);
			moveEffect.setTo(to.getLocation().x, to.getLocation().y);
			if (runNewSumEffectOnEnd) {
				moveEffect.start(new IEffectEndListener() {
					@Override
					public boolean onEffectEnd(Object obj) {
						newSumEffect.start();
						return true;
					}
				});
			}
		}
	}

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
		text.setLocation(getLocation().x, getLocation().y);
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
