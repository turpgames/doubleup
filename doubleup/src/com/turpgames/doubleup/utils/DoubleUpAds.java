package com.turpgames.doubleup.utils;

import com.turpgames.framework.v0.util.Game;

public class DoubleUpAds {
	private static int counter = 0;

	public static void showAd() {
		if (!Game.isDebug() && counter++ % 5 == 0)
			Game.showPopUpAd();
	}
}