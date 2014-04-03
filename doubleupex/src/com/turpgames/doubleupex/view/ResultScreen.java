package com.turpgames.doubleupex.view;

import com.turpgames.doubleupex.objects.Background;
import com.turpgames.doubleupex.objects.GlobalContext;
import com.turpgames.doubleupex.objects.display.DoubleUpToolbar;
import com.turpgames.doubleupex.utils.DoubleUpAds;
import com.turpgames.doubleupex.utils.DoubleUpSettings;
import com.turpgames.doubleupex.utils.Facebook;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.component.Toolbar;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;

public class ResultScreen extends Screen {
	private ResultText resultText;
	private ShareButton shareButton;
	private NewGameButton newGameButton;

	private boolean newMax;

	@Override
	public void init() {
		super.init();
		resultText = new ResultText();
		resultText.setFontScale(0.8f);

		shareButton = new ShareButton();
		newGameButton = new NewGameButton();

		super.registerDrawable(resultText, Game.LAYER_SCREEN);
		super.registerDrawable(shareButton, Game.LAYER_SCREEN);
		super.registerDrawable(newGameButton, Game.LAYER_SCREEN);
		super.registerDrawable(new Background(), Game.LAYER_BACKGROUND);

		super.registerDrawable(DoubleUpToolbar.getInstance(), Game.LAYER_INFO);
	}

	@Override
	protected boolean onBeforeActivate() {
		DoubleUpAds.showAd();
		DoubleUpToolbar.getInstance().setListener(
				new Toolbar.IToolbarListener() {
					@Override
					public void onToolbarBack() {
						onBack();
					}
				});
		return true;
	}

	@Override
	protected void onAfterActivate() {
		shareButton.activate();
		newGameButton.activate();
		DoubleUpToolbar.getInstance().enable();

		String text = "Game Over!";

		if (GlobalContext.finalScore > DoubleUpSettings.getHiScore())
			text += "\n\nNew Hi Score: " + GlobalContext.finalScore;
		else
			text += "\n\nYou Scored " + GlobalContext.finalScore;

		if (newMax = GlobalContext.max > DoubleUpSettings.getMaxNumber())
			text += "\n\nNew Maximum: " + GlobalContext.max;

		resultText.setText(text);
		saveScores();
	}

	@Override
	protected boolean onBeforeDeactivate() {
		shareButton.deactivate();
		newGameButton.deactivate();
		DoubleUpToolbar.getInstance().disable();
		return super.onBeforeDeactivate();
	}

	private void shareScoreOnFacebook() {
		Facebook.shareScore(GlobalContext.finalScore,
				newMax ? DoubleUpSettings.getMaxNumber() : 0, new ICallback() {
					@Override
					public void onSuccess() {

					}

					@Override
					public void onFail(Throwable t) {

					}
				});
	}

	private void switchToNewGameScreen() {
		ScreenManager.instance.switchTo("game", false);
	}

	private void saveScores() {
		long max = DoubleUpSettings.getMaxNumber();
		if (GlobalContext.max > max)
			DoubleUpSettings.setMaxNumber(GlobalContext.max);

		long hiscore = DoubleUpSettings.getHiScore();
		if (GlobalContext.finalScore > hiscore)
			DoubleUpSettings.setHiScore(GlobalContext.finalScore);
	}

	private class ResultText extends Text {
		public ResultText() {
			setAlignment(Text.HAlignCenter, Text.VAlignTop);
			setPadY(200f);
			setSize(Game.getVirtualWidth(), Game.getVirtualHeight());
		}
	}

	private class ShareButton extends TextButton {
		public ShareButton() {
			super(Color.fromHex("#71f055"), Color.fromHex("#fb9d49"));
			setText("Share Score");
			setFontScale(0.8f);
			getLocation().set((Game.getVirtualWidth() - getWidth()) / 2, 275);
			setListener(new IButtonListener() {
				@Override
				public void onButtonTapped() {
					shareScoreOnFacebook();
				}
			});
		}

		@Override
		public boolean ignoreViewport() {
			return false;
		}
	}

	private class NewGameButton extends TextButton {
		public NewGameButton() {
			super(Color.fromHex("#71f055"), Color.fromHex("#fb9d49"));
			setText("New Game");
			setFontScale(0.8f);
			getLocation().set((Game.getVirtualWidth() - getWidth()) / 2, 200);
			setListener(new IButtonListener() {
				@Override
				public void onButtonTapped() {
					switchToNewGameScreen();
				}
			});
		}

		@Override
		public boolean ignoreViewport() {
			return false;
		}
	}

	protected boolean onBack() {
		ScreenManager.instance.switchTo("menu", true);
		return true;
	}
}