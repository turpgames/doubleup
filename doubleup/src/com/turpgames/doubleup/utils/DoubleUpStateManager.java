package com.turpgames.doubleup.utils;

import com.turpgames.doubleup.objects.Table;
import com.turpgames.doubleup.objects.TableState;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.utils.Util;

public class DoubleUpStateManager {
	public static Table loadTable(int matrixSize) {
		String key = "table" + matrixSize;
		
		String data = Settings.getString(key, null);
		if (data == null)
			return null;

		byte[] serialized = Util.Strings.fromBase64String(data);
		TableState state = (TableState) Util.IO.deserialize(serialized);
		Table table = Table.fromState(state);
		return table;
	}

	public static void saveTableState(Table table) {
		String key = "table" + table.getMatrixSize();

		TableState state = table.getState();
		byte[] serialized = Util.IO.serialize(state);
		String data = Util.Strings.toBase64String(serialized);
		Settings.putString(key, data);
	}
}
