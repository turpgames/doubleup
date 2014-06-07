package com.turpgames.doubleup.view;

import com.turpgames.doubleup.utils.DoubleUpAds;
import com.turpgames.doubleup.utils.DoubleUpPlayer;
import com.turpgames.framework.v0.social.ICallback;

public class MenuScreen extends MenuScreenBase {
	private boolean isFirstActivate = true;

	@Override
	public void init() {
		super.init();

		DoubleUpPlayer.bindToFacebook();
	}

	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();
		if (isFirstActivate) {
			isFirstActivate = false;
			registerPlayer();
		}
		DoubleUpAds.showAd(false);
	}

	private void registerPlayer() {
		DoubleUpPlayer.register(ICallback.NULL);
	}
}
