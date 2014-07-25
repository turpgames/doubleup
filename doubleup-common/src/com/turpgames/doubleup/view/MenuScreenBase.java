package com.turpgames.doubleup.view;

import com.turpgames.doubleup.components.DoubleUpLogo;
import com.turpgames.doubleup.components.FacebookLoginButton;
import com.turpgames.framework.v0.impl.FormScreen;
import com.turpgames.framework.v0.util.Game;

public abstract class MenuScreenBase extends FormScreen {
	private FacebookLoginButton loginButton;

	@Override
	public void init() {
		super.init();

		loginButton = FacebookLoginButton.getInstance();

		registerDrawable(loginButton, Game.LAYER_SCREEN);

		registerDrawable(new DoubleUpLogo(), Game.LAYER_SCREEN);
		setForm("mainMenu", false);
	}

	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();
		loginButton.activate();
	}
	
	@Override
	protected boolean onBeforeDeactivate() {
		loginButton.deactivate();
		return super.onBeforeDeactivate();
	}

	protected boolean onBack() {
		Game.exit();
		return true;
	}
}