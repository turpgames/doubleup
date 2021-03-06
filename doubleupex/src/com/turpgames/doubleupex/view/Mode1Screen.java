package com.turpgames.doubleupex.view;

import com.turpgames.doubleup.components.DoubleUpLogo;
import com.turpgames.doubleup.components.DoubleUpToolbar;
import com.turpgames.doubleup.controllers.GridController;
import com.turpgames.doubleup.utils.R;
import com.turpgames.doubleup.view.IDoubleUpView;
import com.turpgames.doubleupex.controllers.mode1.Level1;
import com.turpgames.framework.v0.component.Toolbar;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public class Mode1Screen extends Screen implements IDoubleUpView {
	private GridController controller;

	@Override
	public void init() {
		super.init();

		controller = new Level1(this);
		
		registerDrawable(new DoubleUpLogo(), Game.LAYER_SCREEN);

		registerDrawable(DoubleUpToolbar.getInstance(), Game.LAYER_INFO);
		
		registerInputListener(this);
	}

	@Override
	protected boolean onBeforeActivate() {
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
