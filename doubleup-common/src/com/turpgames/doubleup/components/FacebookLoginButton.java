package com.turpgames.doubleup.components;

import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Game;

public class FacebookLoginButton extends ImageButton {
	public FacebookLoginButton() {
		super(Game.scale(64), Game.scale(64), "facebook");
		setLocation(Button.AlignNW, Game.scale(15), Game.scale(15));
		setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				onLoginButtonTapped();
			}
		});
	}

	@Override
	public void activate() {
		super.activate();
		if (TurpClient.isRegisteredWithFacebook()) {
			getColor().a = 0.5f;
		}
		else {
			getColor().a = 1f;
		}
	}

	private void onLoginButtonTapped() {
		if (TurpClient.isRegisteredWithFacebook()) {
			TurpClient.logoutFromFacebook(new ICallback() {
				@Override
				public void onSuccess() {
					getColor().a = 1f;
				}

				@Override
				public void onFail(Throwable t) {
					getColor().a = 0.5f;
				}
			});
		}
		else {
			TurpClient.loginWithFacebook(new ICallback() {
				@Override
				public void onSuccess() {
					getColor().a = 0.5f;
				}

				@Override
				public void onFail(Throwable t) {
					getColor().a = 1f;
				}
			});
		}
	}
}
