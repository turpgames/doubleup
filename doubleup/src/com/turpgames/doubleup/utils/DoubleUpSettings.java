package com.turpgames.doubleup.utils;

import com.turpgames.framework.v0.util.Setting;
import com.turpgames.framework.v0.util.Setting.IntSetting;
import com.turpgames.framework.v0.util.Setting.StringSetting;

public final class DoubleUpSettings {
	private DoubleUpSettings() {

	}

	public final static IntSetting playerId = Setting.intKey("player-id", 0);
	public final static IntSetting anonymousPlayerId = Setting.intKey("anonymous-player-id", 0);
	public final static StringSetting facebookId = Setting.stringKey("player-facebook-id", "");
	
	public final static IntSetting hiScore4x4 = Setting.intKey("hi-score4", 0);
	public final static IntSetting hiScore5x5 = Setting.intKey("hi-score5", 0);
	public final static IntSetting maxNumber4x4 = Setting.intKey("max-number4", 0);
	public final static IntSetting maxNumber5x5 = Setting.intKey("max-number5", 0);

	public static int getHiScore(int matrixSize) {
		return matrixSize == 4 ? hiScore4x4.get() : hiScore5x5.get();
	}

	public static void setHiScore(int matrixSize, int score) {
		if (matrixSize == 4)
			hiScore4x4.set(score);
		else
			hiScore5x5.set(score);
	}

	public static int getMaxNumber(int matrixSize) {
		return matrixSize == 4 ? maxNumber4x4.get() : maxNumber5x5.get();
	}

	public static void setMaxNumber(int matrixSize, int maxNumber) {
		if (matrixSize == 4)
			maxNumber4x4.set(maxNumber);
		else
			maxNumber5x5.set(maxNumber);
	}
}
