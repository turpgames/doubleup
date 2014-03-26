package com.turpgames.doubleup.utils;

import com.turpgames.doubleup.objects.display.LoadingAnimation;
import com.turpgames.framework.v0.IDrawingInfo;
import com.turpgames.framework.v0.IResourceManager;
import com.turpgames.framework.v0.ISound;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.component.UIBlocker;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;

public final class DoubleUp {
		
	public static String getString(String resourceKey) {
		return Game.getLanguageManager().getString(resourceKey);
	}
	
	public static String getStoreUrl() {
//		if (Game.isIOS()) {
//			if (Game.getOSVersion().getMajor() < 7)
//				return Game.getParam(R.game.params.appStoreAddressOld);
//			else
//				return Game.getParam(R.game.params.appStoreAddressIOS7);
//		}
//		else {
//			return Game.getParam(R.game.params.playStoreAddress);
//		}
		return "";
	}
	
	public static void blockUI(String message) {
		LoadingAnimation.instance.setMessage(DoubleUp.getString(message));
		UIBlocker.instance.block(LoadingAnimation.instance);
	}

	public static void unblockUI() {
		UIBlocker.instance.unblock();
	}

	public static void updateBlockMessage(String message) {
		LoadingAnimation.instance.setMessage(DoubleUp.getString(message));
	}

	private DoubleUp() {

	}
}
