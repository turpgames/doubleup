package com.turpgames.doubleup.utils;

import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.util.Setting;
import com.turpgames.framework.v0.util.Setting.IntSetting;
import com.turpgames.framework.v0.util.Setting.StringSetting;

public final class DoubleUpSettings {
	private DoubleUpSettings() {

	}

	public final static IntSetting playerId = Setting.intKey("player-id", 0);
	public final static IntSetting anonymousPlayerId = Setting.intKey("anonymous-player-id", 0);
	public final static StringSetting facebookId = Setting.stringKey("player-facebook-id", "");
	
	private static String makeHiScoreKey(int matrixSize) {
		return "hi-score" + matrixSize;
	}
	
	private static String makeMaxNumberKey(int matrixSize) {
		return "max-number" + matrixSize;
	}

	public static int getHiScore(int matrixSize) {
		return Settings.getInteger(makeHiScoreKey(matrixSize), 0);
	}

	public static void setHiScore(int matrixSize, int score) {
		Settings.putInteger(makeHiScoreKey(matrixSize), score);
	}

	public static int getMaxNumber(int matrixSize) {
		return Settings.getInteger(makeMaxNumberKey(matrixSize), 0);
	}

	public static void setMaxNumber(int matrixSize, int maxNumber) {
		Settings.putInteger(makeMaxNumberKey(matrixSize), maxNumber);
	}
}
