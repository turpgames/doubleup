package com.turpgames.doubleup.utils;

import com.turpgames.framework.v0.client.ConnectionManager;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.util.Debug;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.utils.Util;

public class DoubleUpAds {
	private static boolean hasAdRequest = false;
	private final static Timer timer;

	static {
		timer = new Timer();
		timer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer t) {
				doShowAd();
			}
		});
	}

	public static void showAd() {
		showAd(30, 60);
	}

	private static void showAd(int min, int max) {
		if (hasAdRequest)
			return;

		Debug.println("BallGameAds.showAd");
		hasAdRequest = true;
		timer.setInterval(Util.Random.randInt(min, max));
		timer.start();
	}

	private static void doShowAd() {
		timer.stop();
		hasAdRequest = false;
		if (!ConnectionManager.hasConnection()) {
			Debug.println("No connection!");
			return;
		}
		if (Game.isIOS() && Game.getInputManager().isTouched()) {
			Debug.println("touched in ios. delay show Ad");
			showAd(1, 2);
			return;
		}
		Debug.println("calling Game.showPopUpAd");
		Game.showPopUpAd();
		TurpClient.sendStat(StatActions.AdShown);
	}
}
