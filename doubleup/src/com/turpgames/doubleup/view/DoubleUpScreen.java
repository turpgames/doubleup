package com.turpgames.doubleup.view;

import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public abstract class DoubleUpScreen extends Screen {
	protected IDoubleUpViewListener screenListener = IDoubleUpViewListener.NULL;

	@Override
	public void init() {
		super.init();

		registerDrawable(DoubleUpGame.getToolbar(), Game.LAYER_INFO);
		registerInputListener(this);
	}

	public void onExitConfirmed() {
		unregisterDrawable(screenListener);
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
	}

	protected void notifyScreenActivated() {
		registerDrawable(screenListener, Game.LAYER_SCREEN);
		screenListener.onScreenActivated();
	}

	protected boolean notifyScreenDeactivated() {
		if (screenListener.onScreenDeactivated()) {
			unregisterDrawable(screenListener);
			return true;
		}
		return false;
	}
	
	@Override
	protected void onAfterActivate() {
		notifyScreenActivated();
	}

	@Override
	protected boolean onBack() {
		return true;
	}

	@Override
	protected boolean onBeforeDeactivate() {
		return notifyScreenDeactivated();
	}

	protected void setScreenListener(IDoubleUpViewListener listener) {
		this.screenListener = listener;
	}

	void back() {
		onBack();
	}
}