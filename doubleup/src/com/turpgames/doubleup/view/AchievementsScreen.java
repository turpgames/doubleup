package com.turpgames.doubleup.view;

import com.turpgames.doubleup.achievements.AchievementsController;
import com.turpgames.doubleup.components.DoubleUpLogo;
import com.turpgames.doubleup.components.DoubleUpToolbar;
import com.turpgames.doubleup.components.IToolbarListener;
import com.turpgames.doubleup.utils.GlobalContext;
import com.turpgames.doubleup.utils.R;
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
		GlobalContext.n = 1;
		controller.activate();
		DoubleUpToolbar.getInstance().enable();
		DoubleUpToolbar.getInstance().setListener(new IToolbarListener() {
			@Override
			public void onToolbarBack() {
				onBack();
			}
			
			@Override
			public void onResetGame() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onShowDescription() {
				// TODO Auto-generated method stub
				
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
