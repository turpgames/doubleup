package com.turpgames.doubleup.utils;

import com.turpgames.doubleup.components.LoadingAnimation;
import com.turpgames.framework.v0.component.UIBlocker;
import com.turpgames.framework.v0.util.Game;

public final class DoubleUp {

	public static String getString(String resourceKey) {
		return Game.getLanguageManager().getString(resourceKey);
	}

	public static void blockUI(String message) {
		updateBlockMessage(message);
		UIBlocker.instance.block(LoadingAnimation.instance);
	}

	public static void unblockUI() {
		UIBlocker.instance.unblock();
	}

	public static void updateBlockMessage(String message) {
		LoadingAnimation.instance.setMessage(message);
	}

	private DoubleUp() {

	}
}
