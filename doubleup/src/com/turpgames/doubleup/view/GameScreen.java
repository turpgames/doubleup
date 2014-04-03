package com.turpgames.doubleup.view;

import com.turpgames.doubleup.components.Background;
import com.turpgames.doubleup.components.DoubleUpLogo;
import com.turpgames.doubleup.components.DoubleUpToolbar;
import com.turpgames.doubleup.controllers.GridController;
import com.turpgames.doubleup.controllers._2048.DoubleUp2048Controller;
import com.turpgames.doubleup.utils.DoubleUpAds;
import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.component.Toolbar;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public abstract class GameScreen extends Screen implements IDoubleUpView {
	private GridController grid;

	@Override
	public void init() {
		super.init();

		grid = new DoubleUp2048Controller(this, getMatrixSize());
		
		registerDrawable(new DoubleUpLogo(), Game.LAYER_SCREEN);
		registerDrawable(new Background(), Game.LAYER_BACKGROUND);

		registerDrawable(DoubleUpToolbar.getInstance(), Game.LAYER_INFO);
	}

	protected abstract int getMatrixSize();

	@Override
	protected boolean onBeforeActivate() {
		DoubleUpAds.showAd();
		DoubleUpToolbar.getInstance().setListener(new Toolbar.IToolbarListener() {
			@Override
			public void onToolbarBack() {
				onBack();
			}
		});

		grid.init();

		return super.onBeforeActivate();
	}

	@Override
	protected void onAfterActivate() {
		grid.activate();
		DoubleUpToolbar.getInstance().enable();
		super.onAfterActivate();
	}

	@Override
	protected boolean onBeforeDeactivate() {
		grid.deactivate();
		DoubleUpToolbar.getInstance().disable();
		return super.onBeforeDeactivate();
	}

	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
		return true;
	}
}
