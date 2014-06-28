package com.turpgames.doubleup.view;

import com.turpgames.doubleup.utils.DoubleUpAds;
import com.turpgames.doubleup.utils.StatActions;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.util.Game;

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

			if (Settings.getInteger("game-installed", 0) == 0) {
				TurpClient.sendStat(StatActions.GameInstalled, Game.getPhysicalScreenSize().toString());
				Settings.putInteger("game-installed", 1);
			}

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
