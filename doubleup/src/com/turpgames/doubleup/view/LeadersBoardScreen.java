package com.turpgames.doubleup.view;

import com.turpgames.doubleup.components.DoubleUpToolbar;
import com.turpgames.doubleup.components.hiscore.HiScoreController;
import com.turpgames.doubleup.utils.R;
import com.turpgames.doubleup.utils.StatActions;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.component.Toolbar;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public class LeadersBoardScreen extends Screen {
	private HiScoreController controller;
	
	@Override
	public void init() {
		super.init();

		controller = new HiScoreController();
		
		registerDrawable(controller, Game.LAYER_GAME);

		registerDrawable(DoubleUpToolbar.getInstance(), Game.LAYER_INFO);
	}

	@Override
	protected void onAfterActivate() {
		controller.activate();
		DoubleUpToolbar.getInstance().enable();
		DoubleUpToolbar.getInstance().setListener(new Toolbar.IToolbarListener() {
			@Override
			public void onToolbarBack() {
				onBack();
			}
		});
		TurpClient.sendStat(StatActions.EnterLeadersBoard);
	}
	
	@Override
	protected boolean onBeforeDeactivate() {
		DoubleUpToolbar.getInstance().disable();
		controller.deactivate();
		return super.onBeforeDeactivate();
	}
	
	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
		return true;
	}
}
