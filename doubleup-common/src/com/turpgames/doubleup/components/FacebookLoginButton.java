package com.turpgames.doubleup.components;

import com.turpgames.doubleup.utils.DoubleUpAuth;
import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.util.Game;

public class FacebookLoginButton implements IView {
	private static FacebookLoginButton instance;
	
	public static FacebookLoginButton getInstance() {
		if (instance == null)
			instance = new FacebookLoginButton();
		return instance;
	}
	
	private ImageButton button;
	
	private FacebookLoginButton() {
		button = new ImageButton(Game.scale(64), Game.scale(64), "facebook");
		button.setLocation(Button.AlignNW, Game.scale(15), Game.scale(15));
		button.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				onLoginButtonTapped();
			}
		});
	}
	
	@Override
	public String getId() {
		return "btnFacebook";
	}
	
	@Override
	public void activate() {
		button.activate();
		updateView();
	}
	
	@Override
	public boolean deactivate() {
		button.deactivate();
		return true;
	}
	
	@Override
	public void draw() {
		button.draw();
	}

	public void updateView() {
		if (TurpClient.isRegisteredWithFacebook()) {
			button.getColor().a = 0.5f;
		}
		else {
			button.getColor().a = 1f;
		}
	}

	private void onLoginButtonTapped() {
		if (TurpClient.isRegisteredWithFacebook()) {
			DoubleUpAuth.doFacebookLogout();
		}
		else {
			DoubleUpAuth.doFacebookLogin();
		}
	}
}
