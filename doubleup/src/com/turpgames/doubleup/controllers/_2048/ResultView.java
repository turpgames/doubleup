package com.turpgames.doubleup.controllers._2048;

import com.turpgames.doubleup.controllers.GridController;
import com.turpgames.doubleup.entities.Grid;
import com.turpgames.doubleup.utils.DoubleUpColors;
import com.turpgames.doubleup.utils.DoubleUpMode;
import com.turpgames.doubleup.utils.GlobalContext;
import com.turpgames.doubleup.utils.R;
import com.turpgames.doubleup.utils.Textures;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.client.IShareMessageBuilder;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;

public class ResultView implements IDrawable {
	private final static Color bgColor = Color.fromHex("#000000B0");

	private ResultText resultText;
	private ShareButton shareButton;
	private NewGameButton newGameButton;
	private final ResultViewOverlay overlay;

	private final GridController controller;
	private final int matrixSize;

	public ResultView(GridController controller, int matrixSize) {
		this.controller = controller;
		this.matrixSize = matrixSize;

		resultText = new ResultText();
		resultText.setFontScale(0.8f);

		shareButton = new ShareButton();
		newGameButton = new NewGameButton();

		overlay = new ResultViewOverlay();
	}

	public void activate() {
		shareButton.activate();
		newGameButton.activate();

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
			text += "\n\nNew Maximum: " + GlobalContext.finalMax;

		resultText.setText(text);
	}

	public void deactivate() {
		shareButton.deactivate();
		newGameButton.deactivate();
	}

	@Override
	public void draw() {
		overlay.draw();
		shareButton.draw();
		newGameButton.draw();
		resultText.draw();
	}

	private void closeResultView() {
		deactivate();
		controller.init();
	}

	private void sendScore() {
		TurpClient.sendScore(
				GlobalContext.finalScore,
				matrixSize == 4 ? DoubleUpMode.Mode4x4 : DoubleUpMode.Mode5x5,
				GlobalContext.finalMax + "");
	}

	private void shareScoreOnFacebook() {
		TurpClient.shareScoreOnFacebook(shareScoreMessageBuilder);
	}

	private final IShareMessageBuilder shareScoreMessageBuilder = new IShareMessageBuilder() {
		@Override
		public String buildMessage() {
			String mode = matrixSize == DoubleUpMode.Mode5x5 ? "5x5" : "4x4";
			String name = TurpClient.getPlayer().getName().split(" ")[0];

			return String.format("%s just reached %d with %d points in Double Up %s mode!",
					name, GlobalContext.finalMax, GlobalContext.finalScore, mode);
		}
	};

	private class ResultText extends Text {
		public ResultText() {
			setAlignment(Text.HAlignCenter, Text.VAlignTop);
			setPadY(200f);
			setSize(Game.getVirtualWidth(), Game.getVirtualHeight());
			getColor().set(R.colors.yellow);
		}
	}

	private class ShareButton extends TextButton {
		public ShareButton() {
			super(DoubleUpColors.color32, R.colors.yellow);
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
			super(DoubleUpColors.color32, R.colors.yellow);
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
