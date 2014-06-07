package com.turpgames.doubleup.achievements;

import com.turpgames.doubleup.entities.Cell;
import com.turpgames.doubleup.entities.Row;
import com.turpgames.doubleup.entities.Tile;
import com.turpgames.doubleup.utils.DoubleUpColors;
import com.turpgames.doubleup.utils.DoubleUpSettings;

public class MaxNumberAchievements4x4 extends AchievementView {
	@Override
	protected int getMatrixSize() {
		return 4;
	}
	
	@Override
	protected void initTiles() {
		putTile(0, 1, 32);
		putTile(0, 2, 64);
		putTile(1, 0, 128);
		putTile(1, 3, 256);
		putTile(2, 0, 512);
		putTile(2, 3, 1024);
		putTile(3, 1, 2048);
		putTile(3, 2, 4096);
	}

	@Override
	public String getId() {
		return "MaxNumberAchievements4x4";
	}
	
	@Override
	protected void updateTileColors() {
		int max = DoubleUpSettings.getMaxNumber(getMatrixSize());

		for (Row row : grid.getRows()) {
			for (Cell cell : row.getCells()) {
				if (!cell.isEmpty()) {
					Tile tile = cell.getTile();
					tile.getColor().set(
							tile.getValue() > max
									? noColor
									: DoubleUpColors.getColor(tile.getValue()));
				}
			}
		}
	}

	private void putTile(int rowIndex, int colIndex, int value) {
		int max = DoubleUpSettings.getMaxNumber(getMatrixSize());
		grid.putTile(rowIndex, colIndex, value).getColor().set(
				value > max
						? noColor
						: DoubleUpColors.getColor(value));
	}

	@Override
	protected void updateText() {
		text.setText("Max Number\n" + DoubleUpSettings.getMaxNumber(getMatrixSize()));
	}
}
