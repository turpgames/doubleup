package com.turpgames.doubleup.utils;

import com.turpgames.framework.v0.util.Game;

public class DoubleUpAds {

	// private static int counter = 0;
	//
	// public static void showAd() {
	// if (counter % 10 == 0)
	// Game.showPanelAd();
	// else if (counter % 5 == 0)
	// Game.showFullScreenAd();
	// counter++;
	// }

	public static void showMenuToGameAd() {
		Game.showFullScreenAd();
	}

	private static int newGameAdCounter = 0;

	public static void showNewGameAd() {
		newGameAdCounter++;
		if (newGameAdCounter % 2 == 0)
			Game.showPanelAd();
		else if (newGameAdCounter % 5 == 0)
			Game.showFullScreenAd();
	}
}
