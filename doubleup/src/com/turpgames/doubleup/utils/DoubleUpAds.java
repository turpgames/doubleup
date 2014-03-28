package com.turpgames.doubleup.utils;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.utils.Util;

public class DoubleUpAds {

	private static int counter = 0;

	public static void showAd() {
		if (counter % 10 == 0) {
			Game.showPanelAd();
			Util.Threading.threadSleep(1000);
		}
		else if (counter % 5 == 0) {
			Game.showFullScreenAd();

			Util.Threading.threadSleep(1000);
		}
		counter++;
	}
}
