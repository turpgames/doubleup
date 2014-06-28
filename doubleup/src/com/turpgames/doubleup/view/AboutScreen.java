package com.turpgames.doubleup.view;

import com.turpgames.doubleup.components.DoubleUpLogo;
import com.turpgames.doubleup.components.DoubleUpToolbar;
import com.turpgames.doubleup.utils.R;
import com.turpgames.doubleup.utils.StatActions;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.component.Toolbar;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;

public class AboutScreen extends Screen {
	private TextButton facebookButton;
	private TextButton twitterButton;
	private TextButton webSiteButton;
	private TextButton didYouLikeButton;
	private TextButton ballgameButton;

	@Override
	public void init() {
		super.init();

		initVersionText();
		initFacebookButton();
		initTwitterButton();
		initWebSiteButton();
		initDidYouLikeButton();
		initBallGameButton();

		registerDrawable(new DoubleUpLogo(), Game.LAYER_SCREEN);
		registerDrawable(DoubleUpToolbar.getInstance(), Game.LAYER_INFO);
	}
	

	private void initVersionText() {
		Text versionText = new Text();
		versionText.setText("v" + Game.getVersion());
		versionText.setFontScale(0.66f);
		versionText.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		versionText.setPadY(125f);
		registerDrawable(versionText, Game.LAYER_SCREEN);
	}

	private void initFacebookButton() {
		facebookButton = createButton("turpgames@facebook", StatActions.ClickedFacebookInAbout, Game.getParam("facebook-address"), 500);
		registerDrawable(facebookButton, Game.LAYER_SCREEN);
	}

	private void initTwitterButton() {
		twitterButton = createButton("turpgames@twitter", StatActions.ClickedTwitterInAbout, Game.getParam("twitter-address"), 400);
		registerDrawable(twitterButton, Game.LAYER_SCREEN);
	}

	private void initWebSiteButton() {
		webSiteButton = createButton("www.turpgames.com", StatActions.ClickedWebSiteInAbout, Game.getParam("turp-address"), 300);
		registerDrawable(webSiteButton, Game.LAYER_SCREEN);

	}

	private void initDidYouLikeButton() {
		didYouLikeButton = createButton("Did you like Double Up?", StatActions.ClickedDidYouLikeInAbout, getStoreUrl(), 200);
		registerDrawable(didYouLikeButton, Game.LAYER_SCREEN);
	}

	private void initBallGameButton() {
		ballgameButton = createButton("Tap the Ball Game", StatActions.ClickedBallGameInAbout, getBallGameStoreUrl(), 100);
		registerDrawable(ballgameButton, Game.LAYER_SCREEN);
	}

	private String getStoreUrl() {
		if (Game.isIOS()) {
			if (Game.getOSVersion().getMajor() < 7)
				return Game.getParam("app-store-address-old");
			else
				return Game.getParam("app-store-address-ios7");
		}
		else {
			return Game.getParam("play-store-address");
		}
	}

	private String getBallGameStoreUrl() {
		if (Game.isIOS()) {
			if (Game.getOSVersion().getMajor() < 7)
				return Game.getParam("ballgame-app-store-address-old");
			else
				return Game.getParam("ballgame-app-store-address-ios7");
		}
		else {
			return Game.getParam("ballgame-play-store-address");
		}
	}

	private static TextButton createButton(String text, final int statAction, final String url, float y) {
		TextButton btn = new TextButton(Color.white(), R.colors.yellow);
		btn.setText(text);
		btn.setFontScale(0.8f);

		btn.getLocation().set((Game.getVirtualWidth() - btn.getWidth()) / 2, y);
		btn.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				Game.openUrl(url);
				TurpClient.sendStat(statAction);
			}
		});

		return btn;
	}

	@Override
	protected void onAfterActivate() {
		DoubleUpToolbar.getInstance().enable();
		DoubleUpToolbar.getInstance().setListener(new Toolbar.IToolbarListener() {
			@Override
			public void onToolbarBack() {
				onBack();
			}
		});

		facebookButton.activate();
		twitterButton.activate();
		webSiteButton.activate();
		didYouLikeButton.activate();
		ballgameButton.activate();
	}

	@Override
	protected boolean onBeforeDeactivate() {
		DoubleUpToolbar.getInstance().disable();

		facebookButton.deactivate();
		twitterButton.deactivate();
		webSiteButton.deactivate();
		didYouLikeButton.deactivate();
		ballgameButton.deactivate();

		return super.onBeforeDeactivate();
	}

	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
		return true;
	}
}
