package com.turpgames.doubleup.view;

import com.turpgames.doubleup.achievements.AchievementsController;
import com.turpgames.doubleup.components.DoubleUpLogo;
import com.turpgames.doubleup.components.DoubleUpToolbar;
import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.component.Toolbar;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public class AchievementsScreen extends Screen {
	private AchievementsController controller;
	
	@Override
	public void init() {
		super.init();
		
		controller = new AchievementsController();

		registerDrawable(new DoubleUpLogo(), Game.LAYER_SCREEN);
		registerDrawable(controller, Game.LAYER_SCREEN);

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