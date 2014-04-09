package com.turpgames.doubleup.achievements;

import com.turpgames.doubleup.entities.Cell;
import com.turpgames.doubleup.entities.Row;
import com.turpgames.doubleup.entities.Tile;
import com.turpgames.doubleup.utils.DoubleUpColors;
import com.turpgames.doubleup.utils.DoubleUpSettings;

public class MaxNumberAchievements5x5 extends AchievementView {
	
	@Override
	protected int getMatrixSize() {
		return 5;
	}
	
	@Override
	protected void initTiles() {
		putTile(0, 2, 128);
		putTile(1, 1, 256);
		putTile(1, 3, 512);
		putTile(2, 0, 1024);
		putTile(2, 2, 2048);
		putTile(2, 4, 4096);
		putTile(3, 1, 8192);
		putTile(3, 3, 8192 * 2);
		putTile(4, 2, 8192 * 4);
	}

	@Override
	public String getId() {
		return "MaxNumberAchievements5x5";
	}

	@Override
	protected void updateTileColors() {
		int max = DoubleUpSettings.getMaxNumber();

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
		int max = DoubleUpSettings.getMaxNumber();
		Tile tile = grid.putTile(rowIndex, colIndex, value);

		tile.getColor().set(
				value > max
						? noColor
						: DoubleUpColors.getColor(value));

		tile.setCustomFontScale(0.66f);
	}

	@Override
	protected void updateText() {
		text.setText("Max Number\n" + DoubleUpSettings.getMaxNumber());
	}
}
