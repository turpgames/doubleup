package com.turpgames.doubleupex.view;

import com.turpgames.doubleup.components.Background;
import com.turpgames.doubleup.components.DoubleUpLogo;
import com.turpgames.doubleup.components.DoubleUpToolbar;
import com.turpgames.doubleup.controllers.GridController;
import com.turpgames.doubleup.utils.DoubleUpAds;
import com.turpgames.doubleup.utils.R;
import com.turpgames.doubleup.view.IDoubleUpView;
import com.turpgames.doubleupex.controllers.mode2.Level1;
import com.turpgames.framework.v0.component.Toolbar;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public class Mode2Screen extends Screen implements IDoubleUpView {
	private GridController controller;

	@Override
	public void init() {
		super.init();

		controller = new Level1(this);
		
		registerDrawable(new DoubleUpLogo(), Game.LAYER_SCREEN);
		registerDrawable(new Background(), Game.LAYER_BACKGROUND);

		registerDrawable(DoubleUpToolbar.getInstance(), Game.LAYER_INFO);
		
		registerInputListener(this);
	}

	@Override
	protected boolean onBeforeActivate() {
		DoubleUpAds.showAd();
		DoubleUpToolbar.getInstance().setListener(new Toolbar.IToolbarListener() {
			@Override
			public void onToolbarBack() {
				onBack();
			}
		});

		controller.init();

		return super.onBeforeActivate();
	}

	@Override
	protected void onAfterActivate() {
		DoubleUpToolbar.getInstance().enable();
		super.onAfterActivate();
	}

	@Override
	protected boolean onBeforeDeactivate() {
		DoubleUpToolbar.getInstance().disable();
		return super.onBeforeDeactivate();
	}

	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
		return true;
	}
}
