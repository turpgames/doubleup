package com.turpgames.doubleup.utils;

import com.turpgames.framework.v0.impl.Settings;

public class DoubleUpSettings {
	public static long getHiScore() {
		return Settings.getLong("hi-score", 0);
	}
	
	public static void setHiScore(long hiscore) {
		Settings.putLong("hi-score", hiscore);
	}	
	
	public static long getMaxNumber() {
		return Settings.getLong("max-number", 0);
	}
	
	public static void setMaxNumber(long maxnumber) {
		Settings.putLong("max-number", maxnumber);
	}
}
