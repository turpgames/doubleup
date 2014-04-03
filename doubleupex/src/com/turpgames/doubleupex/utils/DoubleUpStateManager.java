package com.turpgames.doubleupex.utils;

import com.turpgames.doubleupex.levels.Level1;
import com.turpgames.doubleupex.objects.Table;
import com.turpgames.doubleupex.objects.TableState;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.utils.Util;

public class DoubleUpStateManager {
	public static Table loadTable(int matrixSize) {
		String key = "table" + matrixSize;
		
		String data = Settings.getString(key, "");
		if (Util.Strings.isNullOrWhitespace(data))
			return null;

		byte[] serialized = Util.Strings.fromBase64String(data);
		TableState state = (TableState) Util.IO.deserialize(serialized);
		Table table = new Level1();
		table.loadState(state);
		return table;
	}

	public static void saveTableState(Table table) {
		String key = "table" + table.getMatrixSize();

		TableState state = table.getState();
		byte[] serialized = Util.IO.serialize(state);
		String data = Util.Strings.toBase64String(serialized);
		Settings.putString(key, data);
	}

	public static void deleteTableState(Table table) {
		String key = "table" + table.getMatrixSize();
		Settings.putString(key, "");
	}
}
