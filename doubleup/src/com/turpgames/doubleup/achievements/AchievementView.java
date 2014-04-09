package com.turpgames.doubleup.achievements;

import com.turpgames.doubleup.entities.Grid;
import com.turpgames.doubleup.utils.GlobalContext;
import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;

public abstract class AchievementView implements IView {
	protected final static Color noColor = new Color(0f, 0f);
	
	protected final Grid grid;
	protected final Text text;
	
	public AchievementView() {
		grid = new Grid();
		GlobalContext.matrixSize = getMatrixSize();

		grid.init(getMatrixSize());
		
		text = new Text();
		text.setAlignment(Text.HAlignCenter, Text.VAlignBottom);
		text.setLocation(0, 40f);

		initTiles();
	}
	
	protected abstract void initTiles();
	
	protected abstract int getMatrixSize();
	
	protected abstract void updateTileColors();
	
	protected abstract void updateText();

	@Override
	public void draw() {
		grid.draw();
		text.draw();
	}

	@Override
	public void activate() {
		GlobalContext.matrixSize = getMatrixSize();
		updateTileColors();
		updateText();
	}

	@Override
	public boolean deactivate() {
		return true;
	}
}
