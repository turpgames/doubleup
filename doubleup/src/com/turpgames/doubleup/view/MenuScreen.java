package com.turpgames.doubleup.view;

import com.turpgames.doubleup.utils.DoubleUpAds;
import com.turpgames.doubleup.utils.StatActions;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.util.TurpToast;

public class MenuScreen extends MenuScreenBase {
	private boolean isFirstActivate = true;
	private String username;

	@Override
	public void init() {
		super.init();
	}

	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();
		if (isFirstActivate) {
			isFirstActivate = false;
			TurpClient.sendStat(StatActions.StartGame);
		}

		if (requiresWelcome()) {
			username = TurpClient.getPlayer().getName();
			TurpToast.showInfo("Welcome, " + username);
		}
		
		DoubleUpAds.showAd();
	}
	
	private boolean requiresWelcome() {
		return TurpClient.isRegistered() && !TurpClient.getPlayer().getName().equals(username);
	}

	@Override
	protected boolean onBack() {
		TurpClient.sendStat(StatActions.ExitGame);
		return super.onBack();
	}
}
