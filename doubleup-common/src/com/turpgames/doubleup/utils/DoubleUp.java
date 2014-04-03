package com.turpgames.doubleup.utils;

import com.turpgames.framework.v0.util.Game;

public final class DoubleUp {
		
	public static String getString(String resourceKey) {
		return Game.getLanguageManager().getString(resourceKey);
	}
	
	private DoubleUp() {

	}
}
