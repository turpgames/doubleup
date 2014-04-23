package com.turpgames.doubleup.view;

import com.turpgames.doubleup.components.DoubleUpLogo;
import com.turpgames.doubleup.components.FacebookLoginButton;
import com.turpgames.doubleup.utils.DoubleUpAds;
import com.turpgames.doubleup.utils.Facebook;
import com.turpgames.framework.v0.impl.FormScreen;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Game;

public abstract class MenuScreenBase extends FormScreen {
	private boolean isFirstActivate = true;
	private FacebookLoginButton loginButton;

	@Override
	public void init() {
		super.init();

		loginButton = new FacebookLoginButton();

		registerDrawable(loginButton, Game.LAYER_SCREEN);
		registerInputListener(loginButton);

		registerDrawable(new DoubleUpLogo(), Game.LAYER_SCREEN);
		setForm("mainMenu", false);
	}

	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();
		if (isFirstActivate) {
			isFirstActivate = false;
			loginWithFacebook();
		}
		DoubleUpAds.showAd(false);
	}

	private void loginWithFacebook() {
		if (Facebook.canLogin()) {
			Facebook.login(ICallback.NULL);
		}
	}

	protected boolean onBack() {
		Game.toHomeScreen();
		return true;
	}
}