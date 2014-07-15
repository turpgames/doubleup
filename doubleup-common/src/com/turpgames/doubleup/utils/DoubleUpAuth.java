package com.turpgames.doubleup.utils;

import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TurpToast;

public class DoubleUpAuth {
	private final static ICallback loginCallback = new ICallback() {
		@Override
		public void onSuccess() {
			Game.unblockUI();
			onLogin();
		}

		@Override
		public void onFail(Throwable t) {
			Game.unblockUI();
			TurpToast.showError("Login Failed!");
		}
	};

	private final static ICallback logoutCallback = new ICallback() {
		@Override
		public void onSuccess() {
			onLogout();
		}

		@Override
		public void onFail(Throwable t) {

		}
	};
	
	private static boolean changeGuestName() {
		return Settings.getBoolean("change-guest-name", true);
	}
	
	private static void changeGuestName(boolean changed) {
		Settings.putBoolean("change-guest-name", changed);
	}

	private static void onLogin() {
		changeGuestName(false);
		switchToMenuScreen();
	}

	private static void onLogout() {
		changeGuestName(true);
		switchToAuthScreen();
	}

	private static void switchToMenuScreen() {
		Game.enqueueRunnable(new Runnable() {
			@Override
			public void run() {
				ScreenManager.instance.switchTo(R.screens.menu, false);
			}
		});
	}

	private static void switchToAuthScreen() {
		Game.enqueueRunnable(new Runnable() {
			@Override
			public void run() {
				ScreenManager.instance.switchTo(R.screens.auth, false);
			}
		});
	}

	public static void init() {
		if (TurpClient.canLoginWithFacebook()) {
			doFacebookLogin();
		}
		else if (TurpClient.canLoginGuest()) {
			doGuestLogin();
		}
	}

	public static void doFacebookLogin() {
		Game.blockUI("Connecting to Facebook...");
		TurpClient.loginWithFacebook(loginCallback);
	}

	public static void doFacebookLogout() {
		TurpClient.logoutFromFacebook(logoutCallback);
	}

	public static void doGuestLogin() {
		Game.blockUI("Registering guest...");
		if (changeGuestName())
			TurpClient.registerGuestWithNewName(loginCallback);
		else
			TurpClient.loginGuest(loginCallback);
	}
}
