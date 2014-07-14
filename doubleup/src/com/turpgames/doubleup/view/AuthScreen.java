package com.turpgames.doubleup.view;

import com.turpgames.doubleup.components.DoubleUpLogo;
import com.turpgames.doubleup.utils.R;
import com.turpgames.doubleup.utils.StatActions;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;

public class AuthScreen extends Screen {

	private TextButton facebookButton;
	private TextButton anonymousButton;
	
	private ICallback loginCallback;
	
	@Override
	public void init() {
		super.init();

		facebookButton = initButton("Login With Facebook", 450, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				loginWithFacebook();
			}
		});
		
		anonymousButton = initButton("Play As Guest", 300, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				playAsGuest();
			}
		});
		
		registerDrawable(new DoubleUpLogo(), Game.LAYER_GAME);
		
		if (Settings.getInteger("game-installed", 0) == 0) {
			TurpClient.sendStat(StatActions.GameInstalled, Game.getPhysicalScreenSize().toString());
			Settings.putInteger("game-installed", 1);
		}
		
		TurpClient.sendStat(StatActions.StartGame);
		
		loginCallback = new ICallback() {
			@Override
			public void onSuccess() {
				switchToMenu();
			}
			
			@Override
			public void onFail(Throwable t) {
				
			}
		};
		
		tryLogin();
	}
	
	private void switchToMenu() {
		Game.enqueueRunnable(new Runnable() {
			@Override
			public void run() {
				ScreenManager.instance.switchTo(R.screens.menu, false);
			}
		});
	}
	
	private void tryLogin() {
		TurpClient.init(loginCallback);
	}
	
	private void loginWithFacebook() {
		TurpClient.loginWithFacebook(loginCallback);
	}
	
	private void playAsGuest() {
		TurpClient.loginGuest(loginCallback);
	}
	
	private TextButton initButton(String text, float y, IButtonListener listener) {
		TextButton btn = new TextButton(Color.white(), R.colors.yellow);
		
		btn.setText(text);
		btn.getLocation().set((Game.getVirtualWidth() - btn.getWidth()) / 2, y);
		btn.setListener(listener);
		
		registerDrawable(btn, Game.LAYER_GAME);
		
		return btn;
	}
	
	@Override
	protected void onAfterActivate() {
		facebookButton.activate();
		anonymousButton.activate();
	}
	
	@Override
	protected boolean onBeforeDeactivate() {
		facebookButton.deactivate();
		anonymousButton.deactivate();
		return super.onBeforeDeactivate();
	}
	
	@Override
	protected boolean onBack() {
		TurpClient.sendStat(StatActions.ExitGame);
		Game.exit();
		return true;
	}
}
