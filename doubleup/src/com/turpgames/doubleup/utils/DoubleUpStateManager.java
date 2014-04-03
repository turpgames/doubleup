package com.turpgames.doubleup.utils;

import com.turpgames.doubleup.state.GridState;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.utils.Util;

public class DoubleUpStateManager {
	public static GridState getGridState(String key) {
		String data = Settings.getString(key, "");
		if (Util.Strings.isNullOrWhitespace(data))
			return null;

		byte[] serialized = Util.Strings.fromBase64String(data);
		return (GridState) Util.IO.deserialize(serialized);
	}

	public static void saveGridState(String key, GridState state) {
		byte[] serialized = Util.IO.serialize(state);
		String data = Util.Strings.toBase64String(serialized);
		Settings.putString(key, data);
	}

	public static void deleteGridState(String key) {
		Settings.putString(key, "");
	}
}
