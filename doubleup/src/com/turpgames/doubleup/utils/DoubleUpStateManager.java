package com.turpgames.doubleup.utils;

import com.turpgames.doubleup.entities.Grid;
import com.turpgames.doubleup.state.GridState;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.utils.Util;

public class DoubleUpStateManager {
	public static Grid loadGrid(int matrixSize) {
		String key = "grid" + matrixSize;
		
		String data = Settings.getString(key, "");
		if (Util.Strings.isNullOrWhitespace(data))
			return null;

		byte[] serialized = Util.Strings.fromBase64String(data);
		GridState state = (GridState) Util.IO.deserialize(serialized);
		
		Grid grid = new Grid();
		grid.loadState(state);
		return grid;
	}

	public static void saveTableState(Grid grid) {
		String key = "grid" + grid.getMatrixSize();

		GridState state = grid.getState();
		byte[] serialized = Util.IO.serialize(state);
		String data = Util.Strings.toBase64String(serialized);
		Settings.putString(key, data);
	}

	public static void deleteGridState(Grid grid) {
		String key = "grid" + grid.getMatrixSize();
		Settings.putString(key, "");
	}
}
