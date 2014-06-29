package com.turpgames.doubleup.view;

import com.turpgames.doubleup.utils.DoubleUpAds;
import com.turpgames.doubleup.utils.StatActions;
import com.turpgames.framework.v0.client.TurpClient;

public class MenuScreen extends MenuScreenBase {
	private boolean isFirstActivate = true;

	@Override
	public void init() {
		super.init();
	}

	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();
		if (isFirstActivate) {
			isFirstActivate = false;
			TurpClient.init();
			TurpClient.sendStat(StatActions.StartGame);
		}
		DoubleUpAds.showAd();
	}

	@Override
	protected boolean onBack() {
		TurpClient.sendStat(StatActions.ExitGame);
		return super.onBack();
	}
}
