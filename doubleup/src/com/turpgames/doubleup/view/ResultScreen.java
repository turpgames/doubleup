package com.turpgames.doubleup.view;

import com.turpgames.doubleup.objects.Background;
import com.turpgames.doubleup.objects.GlobalContext;
import com.turpgames.doubleup.utils.DoubleUpSettings;
import com.turpgames.doubleup.utils.Facebook;
import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
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

		shareButton = new ShareButton();
		newGameButton = new NewGameButton();

		super.registerDrawable(resultText, Game.LAYER_SCREEN);
		super.registerDrawable(shareButton, Game.LAYER_SCREEN);
		super.registerDrawable(newGameButton, Game.LAYER_SCREEN);
		super.registerDrawable(new Background(), Game.LAYER_BACKGROUND);
	}

	@Override
	protected void onAfterActivate() {
		shareButton.activate();
		newGameButton.activate();

		String text = "Game Over!";

		int hiscore = DoubleUpSettings.getHiScore();
		if (GlobalContext.finalScore > hiscore)
			text += "\n\nNew Hi Score: " + GlobalContext.finalScore;

		int max = DoubleUpSettings.getMaxNumber();
		if (newMax = GlobalContext.max > max)
			text += "\n\nNew Maximum: " + GlobalContext.max;

		resultText.setText(text);
		saveScores();
	}

	@Override
	protected boolean onBeforeDeactivate() {
		shareButton.deactivate();
		newGameButton.deactivate();
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
		GlobalContext.table.reset();
		ScreenManager.instance.switchTo(R.screens.game, false);
	}

	private void saveScores() {
		int max = DoubleUpSettings.getMaxNumber();
		if (GlobalContext.max > max)
			DoubleUpSettings.setMaxNumber(GlobalContext.max);

		int hiscore = DoubleUpSettings.getHiScore();
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
			super(Color.white(), Color.white());
			setText("Share Score on Facebook");
			getLocation().set((Game.getVirtualWidth() - getWidth()) / 2, 300);
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
			super(Color.white(), Color.white());
			setText("New Game");
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
}
