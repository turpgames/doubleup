package com.turpgames.doubleup.utils;

import com.turpgames.framework.v0.util.Game;

public class DoubleUpAds {
	private static int counter = 0;

	public static void showAd(boolean force) {
		if (!ConnectionManager.hasConnection())
			return;

		if (force || ++counter % 3 == 0) {
			counter = 0;
			Game.showPopUpAd();
		}
	}
}
