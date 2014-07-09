package com.turpgames.doubleup.view;

import com.turpgames.doubleup.components.DoubleUpLogo;
import com.turpgames.doubleup.components.DoubleUpToolbar;
import com.turpgames.doubleup.controllers.GridController;
import com.turpgames.doubleup.controllers._2048.DoubleUp2048Controller;
import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.component.Toolbar;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public abstract class GameScreen extends Screen implements IDoubleUpView {
	private GridController controller;

	@Override
	public void init() {
		super.init();

		controller = createController();

		registerDrawable(new DoubleUpLogo(), Game.LAYER_SCREEN);

		registerDrawable(DoubleUpToolbar.getInstance(), Game.LAYER_INFO);

		registerInputListener(this);
	}

	protected DoubleUp2048Controller createController() {
		return new DoubleUp2048Controller(this, getMatrixSize());
	}

	protected abstract int getMatrixSize();

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
		controller.activate();
		super.onAfterActivate();
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
