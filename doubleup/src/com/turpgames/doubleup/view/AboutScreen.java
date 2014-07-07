package com.turpgames.doubleup.view;

import com.turpgames.doubleup.components.DoubleUpLogo;
import com.turpgames.doubleup.components.DoubleUpToolbar;
import com.turpgames.doubleup.utils.R;
import com.turpgames.doubleup.utils.StatActions;
import com.turpgames.doubleup.utils.Textures;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.Toolbar;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;

public class AboutScreen extends Screen {
	private final static float buttonSize = 128f;
	private final static int buttonPerRow = 3;
	
	private AboutButton facebookButton;
	private AboutButton twitterButton;
	private AboutButton webSiteButton;
	private AboutButton didYouLikeButton;
	private AboutButton ballgameButton;
	private AboutButton ichiguButton;

	@Override
	public void init() {
		super.init();

		initVersionText();
		initJoshText();
		initFacebookButton();
		initTwitterButton();
		initWebSiteButton();
		initDidYouLikeButton();
		initBallGameButton();
		initIchiguButton();

		registerDrawable(new DoubleUpLogo(), Game.LAYER_SCREEN);
		registerDrawable(DoubleUpToolbar.getInstance(), Game.LAYER_INFO);
	}

	private void initVersionText() {
		Text text = new Text();
		text.setText("v" + Game.getVersion());
		text.setFontScale(0.66f);
		text.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		text.setPadY(125f);
		registerDrawable(text, Game.LAYER_SCREEN);
	}

	private void initJoshText() {
		Text text = new Text();
		text.setText("Who is Josh?\n\nJosh, is 5 years old and he is our favourite fan. He wanted extra modes and so we did under 'For Josh' menu.\nHappy playing, Josh!");
		text.setFontScale(0.66f);
		text.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		text.setPadY(200f);
		text.setPadX(10f);
		registerDrawable(text, Game.LAYER_SCREEN);
	}
	
	private float getX(int i) {
		float dx = (Game.getVirtualWidth() - buttonPerRow * buttonSize) / (buttonPerRow + 1);
		return (i + 1) * dx + buttonSize * i;
	}

	private void initFacebookButton() {
		facebookButton = createButton(Textures.facebook, StatActions.ClickedFacebookInAbout, Game.getParam("facebook-address"), buttonSize, getX(0), 225f);
		registerDrawable(facebookButton, Game.LAYER_SCREEN);
	}

	private void initTwitterButton() {
		twitterButton = createButton(Textures.twitter, StatActions.ClickedTwitterInAbout, Game.getParam("twitter-address"), buttonSize, getX(1), 225f);
		registerDrawable(twitterButton, Game.LAYER_SCREEN);
	}

	private void initWebSiteButton() {
		webSiteButton = createButton(Textures.turplogo, StatActions.ClickedWebSiteInAbout, Game.getParam("turp-address"), buttonSize, getX(2), 225f);
		registerDrawable(webSiteButton, Game.LAYER_SCREEN);

	}

	private void initDidYouLikeButton() {
		didYouLikeButton = createButton(Textures.doubleup, StatActions.ClickedDoubleUpInAbout, getStoreUrl(), buttonSize, getX(0), 50f);
		registerDrawable(didYouLikeButton, Game.LAYER_SCREEN);
	}

	private void initBallGameButton() {
		ballgameButton = createButton(Textures.ballgame, StatActions.ClickedBallGameInAbout, getBallGameStoreUrl(), buttonSize, getX(1), 50f);
		registerDrawable(ballgameButton, Game.LAYER_SCREEN);
	}

	private void initIchiguButton() {
		ichiguButton = createButton(Textures.ichigu, StatActions.ClickedIchiguInAbout, getIchiguStoreUrl(), buttonSize, getX(2), 50f);
		registerDrawable(ichiguButton, Game.LAYER_SCREEN);
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

	private String getIchiguStoreUrl() {
		if (Game.isIOS()) {
			if (Game.getOSVersion().getMajor() < 7)
				return Game.getParam("ichigu-app-store-address-old");
			else
				return Game.getParam("ichigu-app-store-address-ios7");
		}
		else {
			return Game.getParam("ichigu-play-store-address");
		}
	}

	private static AboutButton createButton(ITexture texture, final int statAction, final String url, float size, float x, float y) {
		AboutButton btn = new AboutButton();
		btn.setTexture(texture);
		btn.setWidth(size);
		btn.setHeight(size);

		btn.getLocation().set(x, y);
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
		ichiguButton.activate();
	}

	@Override
	protected boolean onBeforeDeactivate() {
		DoubleUpToolbar.getInstance().disable();

		facebookButton.deactivate();
		twitterButton.deactivate();
		webSiteButton.deactivate();
		didYouLikeButton.deactivate();
		ballgameButton.deactivate();
		ichiguButton.deactivate();

		return super.onBeforeDeactivate();
	}

	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
		return true;
	}

	private static class AboutButton extends ImageButton {
		@Override
		public boolean ignoreViewport() {
			return false;
		}
	}
}
