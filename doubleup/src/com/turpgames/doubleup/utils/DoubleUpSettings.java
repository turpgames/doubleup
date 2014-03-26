package com.turpgames.doubleup.utils;

import com.turpgames.framework.v0.impl.Settings;

public class DoubleUpSettings {
	public static int getHiScore() {
		return Settings.getInteger("hi-score", 0);
	}
	
	public static void setHiScore(int hiscore) {
		Settings.putInteger("hi-score", hiscore);
	}	
	
	public static int getMaxNumber() {
		return Settings.getInteger("max-number", 0);
	}
	
	public static void setMaxNumber(int maxnumber) {
		Settings.putInteger("max-number", maxnumber);
	}
}
