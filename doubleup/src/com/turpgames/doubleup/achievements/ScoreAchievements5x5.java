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

public class ScoreAchievements5x5 extends AchievementView {
	private static Map<Integer, Color> colors = new  LinkedHashMap<Integer, Color>();

	static {
		colors.put(5000, DoubleUpColors.getColor(1));
		colors.put(7500, DoubleUpColors.getColor(2));
		colors.put(10000, DoubleUpColors.getColor(4));
		colors.put(20000, DoubleUpColors.getColor(8));
		colors.put(30000, DoubleUpColors.getColor(16));
		colors.put(40000, DoubleUpColors.getColor(32));
		colors.put(50000, DoubleUpColors.getColor(64));
		colors.put(60000, DoubleUpColors.getColor(128));
		colors.put(70000, DoubleUpColors.getColor(256));
		colors.put(80000, DoubleUpColors.getColor(512));
		colors.put(90000, DoubleUpColors.getColor(1024));
		colors.put(100000, DoubleUpColors.getColor(2048));
		colors.put(110000, DoubleUpColors.getColor(4096));
		colors.put(120000, DoubleUpColors.getColor(8192));
		colors.put(130000, DoubleUpColors.getColor(8192 * 2));
		colors.put(140000, DoubleUpColors.getColor(8192 * 4));
		colors.put(150000, DoubleUpColors.getColor(1));
		colors.put(175000, DoubleUpColors.getColor(2));
		colors.put(200000, DoubleUpColors.getColor(4));
		colors.put(225000, DoubleUpColors.getColor(8));
		colors.put(250000, DoubleUpColors.getColor(16));
		colors.put(275000, DoubleUpColors.getColor(32));
		colors.put(300000, DoubleUpColors.getColor(64));
		colors.put(350000, DoubleUpColors.getColor(128));
		colors.put(400000, DoubleUpColors.getColor(256));
	}

	@Override
	protected int getMatrixSize() {
		return 5;
	}

	@Override
	protected void initTiles() {
		Iterator<Integer> scores = colors.keySet().iterator();

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Integer score = scores.next();
				putTile(i, j, score);
			}
		}
	}

	@Override
	public String getId() {
		return "ScoreAchievements5x5";
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

		tile.setCustomFontScale(0.55f);
	}

	@Override
	protected void updateText() {
		text.setText("Hi Score\n" + DoubleUpSettings.getHiScore());
	}
}
