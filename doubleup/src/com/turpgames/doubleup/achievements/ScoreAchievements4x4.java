package com.turpgames.doubleup.achievements;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.turpgames.doubleup.entities.Cell;
import com.turpgames.doubleup.entities.Row;
import com.turpgames.doubleup.entities.Tile;
import com.turpgames.doubleup.utils.DoubleUpColors;
import com.turpgames.doubleup.utils.DoubleUpSettings;
import com.turpgames.framework.v0.util.Color;

public class ScoreAchievements4x4 extends AchievementView {
	private final static Color noColor = new Color(0f, 0f);
	private static Map<Integer, Color> colors = new LinkedHashMap<Integer, Color>();

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
		Iterator<Integer> scores = colors.keySet().iterator();

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Integer score = scores.next();
				putTile(i, j, score);
			}
		}
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
