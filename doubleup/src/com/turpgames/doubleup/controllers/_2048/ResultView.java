package com.turpgames.doubleup.controllers._2048;

import com.turpgames.doubleup.controllers.GridController;
import com.turpgames.doubleup.entities.Grid;
import com.turpgames.doubleup.entity.Score;
import com.turpgames.doubleup.utils.DoubleUpColors;
import com.turpgames.doubleup.utils.DoubleUpSettings;
import com.turpgames.doubleup.utils.Facebook;
import com.turpgames.doubleup.utils.GlobalContext;
import com.turpgames.doubleup.utils.R;
import com.turpgames.doubleup.utils.ScoreManager;
import com.turpgames.doubleup.utils.Textures;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;

public class ResultView implements IDrawable {
	private final static Color bgColor = Color.fromHex("#000000B0");

	private ResultText resultText;
	private SendScoreButton sendScoreButton;
	private ShareButton shareButton;
	private NewGameButton newGameButton;
	private final ResultViewOverlay overlay;

	private final GridController controller;

	public ResultView(GridController controller) {
		this.controller = controller;

		resultText = new ResultText();
		resultText.setFontScale(0.8f);

		sendScoreButton = new SendScoreButton();
		shareButton = new ShareButton();
		newGameButton = new NewGameButton();

		overlay = new ResultViewOverlay();
	}

	public void activate() {
		shareButton.activate();
		newGameButton.activate();

		if (!Facebook.isLoggedIn())
			sendScoreButton.activate();
		sendScore();

		String text = "";

		if (GlobalContext.hasNewHiScore || GlobalContext.hasNewMaxNumber)
			text = "Congratulations!";
		else
			text = "Game Over!";

		if (GlobalContext.hasNewHiScore)
			text += "\n\nNew Hi Score: " + GlobalContext.finalScore;
		else
			text += "\n\nYou Scored " + GlobalContext.finalScore;

		if (GlobalContext.hasNewMaxNumber)
			text += "\n\nNew Maximum: " + DoubleUpSettings.getMaxNumber();

		resultText.setText(text);
	}

	public void deactivate() {
		sendScoreButton.deactivate();
		shareButton.deactivate();
		newGameButton.deactivate();
	}

	@Override
	public void draw() {
		overlay.draw();
		shareButton.draw();
		newGameButton.draw();
		resultText.draw();
		if (sendScoreButton.isActive())
			sendScoreButton.draw();
	}

	private void closeResultView() {
		deactivate();
		controller.init();
	}

	private void sendScore() {
		ScoreManager.instance.sendScore(
				GlobalContext.matrixSize == 4 ? Score.Mode4x4 : Score.Mode5x5,
				GlobalContext.finalScore,
				GlobalContext.finalMax);
	}

	private void loginWithFacebook() {
		Facebook.login(new ICallback() {
			@Override
			public void onSuccess() {
				ScoreManager.instance.sendScoresInBackground();
				sendScoreButton.deactivate();
			}

			@Override
			public void onFail(Throwable t) {

			}
		});
	}

	private void shareScoreOnFacebook() {
		Score score = new Score();
		score.setMode(GlobalContext.matrixSize == 5 ? Score.Mode5x5 : Score.Mode4x4);
		score.setScore(GlobalContext.finalScore);
		score.setMaxNumber(GlobalContext.finalMax);

		Facebook.shareScore(score, ICallback.NULL);
	}

	private class ResultText extends Text {
		public ResultText() {
			setAlignment(Text.HAlignCenter, Text.VAlignTop);
			setPadY(200f);
			setSize(Game.getVirtualWidth(), Game.getVirtualHeight());
			getColor().set(R.colors.turpYellow);
		}
	}

	private class SendScoreButton extends TextButton {
		public SendScoreButton() {
			super(DoubleUpColors.color32, R.colors.turpYellow);
			setText("Send Score");
			setFontScale(0.8f);
			getLocation().set((Game.getVirtualWidth() - getWidth()) / 2, 310);
			setListener(new IButtonListener() {
				@Override
				public void onButtonTapped() {
					loginWithFacebook();
				}
			});
		}

		@Override
		public boolean ignoreViewport() {
			return false;
		}
	}

	private class ShareButton extends TextButton {
		public ShareButton() {
			super(DoubleUpColors.color32, R.colors.turpYellow);
			setText("Share Score");
			setFontScale(0.8f);
			getLocation().set((Game.getVirtualWidth() - getWidth()) / 2, 250);
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
			super(DoubleUpColors.color32, R.colors.turpYellow);
			setText("New Game");
			setFontScale(0.8f);
			getLocation().set((Game.getVirtualWidth() - getWidth()) / 2, 190);
			setListener(new IButtonListener() {
				@Override
				public void onButtonTapped() {
					closeResultView();
				}
			});
		}

		@Override
		public boolean ignoreViewport() {
			return false;
		}
	}

	private class ResultViewOverlay extends GameObject {

		public ResultViewOverlay() {
			getLocation().set(
					(Game.getVirtualWidth() - Grid.size) / 2,
					(Game.getVirtualHeight() - Grid.size) / 2);
			setWidth(Grid.size);
			setHeight(Grid.size);

			getColor().set(bgColor);
		}

		@Override
		public void draw() {
			TextureDrawer.draw(Textures.overlay, this);
		}
	}
}
