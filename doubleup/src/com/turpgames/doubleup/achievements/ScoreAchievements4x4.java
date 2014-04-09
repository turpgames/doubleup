package com.turpgames.doubleup.achievements;

import java.util.HashMap;
import java.util.Map;

import com.turpgames.doubleup.entities.Cell;
import com.turpgames.doubleup.entities.Row;
import com.turpgames.doubleup.entities.Tile;
import com.turpgames.doubleup.utils.DoubleUpColors;
import com.turpgames.doubleup.utils.DoubleUpSettings;
import com.turpgames.framework.v0.util.Color;

public class ScoreAchievements4x4 extends AchievementView {
	private final static Color noColor = new Color(0f, 0f);
	private static Map<Integer, Color> colors = new HashMap<Integer, Color>();

	static {
		colors.put(500, DoubleUpColors.getColor(1));
		colors.put(1000, DoubleUpColors.getColor(2));
		colors.put(2000, DoubleUpColors.getColor(4));
		colors.put(3000, DoubleUpColors.getColor(8));
		colors.put(4000, DoubleUpColors.getColor(16));
		colors.put(5000, DoubleUpColors.getColor(32));
		colors.put(7500, DoubleUpColors.getColor(64));
		colors.put(10000, DoubleUpColors.getColor(128));
		colors.put(15000, DoubleUpColors.getColor(256));
		colors.put(20000, DoubleUpColors.getColor(512));
		colors.put(25000, DoubleUpColors.getColor(1024));
		colors.put(30000, DoubleUpColors.getColor(2048));
		colors.put(35000, DoubleUpColors.getColor(4096));
		colors.put(40000, DoubleUpColors.getColor(8192));
		colors.put(45000, DoubleUpColors.getColor(8192 * 2));
		colors.put(50000, DoubleUpColors.getColor(8192 * 4));
	}
	
	@Override
	protected int getMatrixSize() {
		return 4;
	}

	@Override
	protected void initTiles() {
		putTile(0, 0, 500);
		putTile(0, 1, 1000);
		putTile(0, 2, 2000);
		putTile(0, 3, 3000);
		putTile(1, 0, 4000);
		putTile(1, 1, 5000);
		putTile(1, 2, 7500);
		putTile(1, 3, 10000);
		putTile(2, 0, 15000);
		putTile(2, 1, 20000);
		putTile(2, 2, 25000);
		putTile(2, 3, 30000);
		putTile(3, 0, 35000);
		putTile(3, 1, 40000);
		putTile(3, 2, 45000);
		putTile(3, 3, 50000);
	}

	@Override
	public String getId() {
		return "ScoreAchievements4x4";
	}

	@Override
	protected void updateTileColors() {
		int max = DoubleUpSettings.getHiScore();

		for (Row row : grid.getRows()) {
			for (Cell cell : row.getCells()) {
				if (!cell.isEmpty()) {
					Tile tile = cell.getTile();
					tile.getColor().set(
							tile.getValue() > max
									? noColor
									: colors.get(tile.getValue()));
				}
			}
		}
	}

	private void putTile(int rowIndex, int colIndex, int value) {
		int max = DoubleUpSettings.getHiScore();

		Tile tile = grid.putTile(rowIndex, colIndex, value);
		tile.getColor().set(
				value > max
						? noColor
						: colors.get(value));

		tile.setCustomFontScale(0.8f);
	}

	@Override
	protected void updateText() {
		text.setText("Hi Score\n" + DoubleUpSettings.getHiScore());
	}
}
