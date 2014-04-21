package com.turpgames.doubleup.components;

import com.turpgames.doubleup.utils.Facebook;
import com.turpgames.doubleup.utils.Facebook.IFacebookListener;
import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Game;

public class FacebookLoginButton extends ImageButton implements IFacebookListener {
	private Dialog logoutConfirmDialog;

	public FacebookLoginButton() {
		super(Game.scale(64), Game.scale(64), "facebook");
		setLocation(Button.AlignNW, Game.scale(15), Game.scale(15));
		setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				onLoginButtonTapped();
			}
		});
		
		logoutConfirmDialog = new Dialog();
		logoutConfirmDialog.addButton("yes", "Yes");
		logoutConfirmDialog.addButton("no", "No");
		logoutConfirmDialog.setListener(new Dialog.IDialogListener() {			
			@Override
			public void onDialogClosed() {

			}
			
			@Override
			public void onDialogButtonClicked(String id) {
				if (!"yes".equals(id))
					return;
				Facebook.logout(ICallback.NULL);
			}
		});
		
		Facebook.registerListener(this);
	}

	private void onLoginButtonTapped() {
		if (Facebook.isLoggedIn()) {
			logoutConfirmDialog.open("Are you sure you want to logout?");
		}
		else {
			Facebook.login(ICallback.NULL);
		}
	}

	@Override
	public void onLogin() {
		getColor().a = 0.5f;
	}

	@Override
	public void onLogout() {
		getColor().a = 1f;
	}
}
