package com.turpgames.doubleup.view;

import com.turpgames.doubleup.updates.DoubleUpUpdateManager;

public class SplashScreen extends SplashScreenBase {
	@Override
	protected void runUpdates() {
		DoubleUpUpdateManager.runUpdates();
	}
}
