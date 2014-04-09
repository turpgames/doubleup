package com.turpgames.doubleup.entities;

import com.turpgames.doubleup.effects.TileAddEffect;
import com.turpgames.doubleup.effects.TileMoveEffect;
import com.turpgames.doubleup.effects.TilePopEffect;
import com.turpgames.doubleup.state.TileState;
import com.turpgames.doubleup.utils.DoubleUpColors;
import com.turpgames.doubleup.utils.Textures;
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
	
	private final TileState state;

	public Tile() {
		text = new Text();
		text.setAlignment(Text.HAlignCenter, Text.VAlignCenter);
		text.getColor().set(Color.white());
		
		state = new TileState();

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
	
	public void setCustomFontScale(float scale) {
		text.setFontScale(scale);
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
			getColor().set(DoubleUpColors.brickColor);
		}
		else {
			text.setText(value + "");
			getColor().set(DoubleUpColors.getColor(value));
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
	
	public TileState getState() {
		state.setValue(value);
		state.setBrick(isBrick);
		return state;
	}
	
	public void loadState(TileState state) {
		setValue(state.getValue());
		setBrick(state.isBrick());
	}
}
