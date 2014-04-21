package com.turpgames.doubleup.utils;

import com.turpgames.doubleup.utils.GlobalContext;
import com.turpgames.framework.v0.impl.Settings;

public class DoubleUpSettings {
	public static int getHiScore() {
		return Settings.getInteger("hi-score" + GlobalContext.matrixSize, 0);
	}

	public static void setHiScore(int hiscore) {
		Settings.putInteger("hi-score" + GlobalContext.matrixSize, hiscore);
	}

	public static int getMaxNumber() {
		return Settings.getInteger("max-number" + GlobalContext.matrixSize, 0);
	}

	public static void setMaxNumber(int maxnumber) {
		Settings.putInteger("max-number" + GlobalContext.matrixSize, maxnumber);
	}
}
