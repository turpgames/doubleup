package com.turpgames.doubleup.controllers._2048;

import com.turpgames.doubleup.components.ScoreArea;
import com.turpgames.doubleup.controllers.GridController;
import com.turpgames.doubleup.entities.Grid;
import com.turpgames.doubleup.state.GridState;
import com.turpgames.doubleup.utils.DoubleUpAds;
import com.turpgames.doubleup.utils.DoubleUpAudio;
import com.turpgames.doubleup.utils.DoubleUpSettings;
import com.turpgames.doubleup.utils.DoubleUpStateManager;
import com.turpgames.doubleup.utils.GlobalContext;
import com.turpgames.doubleup.utils.StatActions;
import com.turpgames.doubleup.view.IDoubleUpView;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.util.Game;

public class DoubleUp2048Controller extends GridController {
	protected final int matrixSize;
	private int score;

	private final ScoreArea scoreArea;
	private final ScoreArea hiscoreArea;
	private final ScoreArea hiscoreBlockArea;

	private final ResultView resultView;

	private boolean isGameOver;

	public DoubleUp2048Controller(IDoubleUpView view, int matrixSize) {
		super(view);
		this.matrixSize = matrixSize;

		float x = (Game.getVirtualWidth() - Grid.size) / 2;
		float y = (Game.getVirtualHeight() - Grid.size) / 2;

		resultView = new ResultView(this, matrixSize);

		scoreArea = new ScoreArea("Score");
		scoreArea.setLocation(x + 4, y - 75);

		hiscoreArea = new ScoreArea("Hi");
		hiscoreArea.setLocation((Game.getVirtualWidth() - hiscoreArea.getWidth()) / 2, y - 75);

		hiscoreBlockArea = new ScoreArea("Max");
		hiscoreBlockArea.setLocation(Game.getVirtualWidth() - hiscoreBlockArea.getWidth() - 4, y - 75);

		view.registerDrawable(scoreArea, Game.LAYER_GAME);
		view.registerDrawable(hiscoreArea, Game.LAYER_GAME);
		view.registerDrawable(hiscoreBlockArea, Game.LAYER_GAME);
	}

	@Override
	public void reset() {
		DoubleUpStateManager.deleteGridState(getGridStateKey());
		super.reset();
	}

	@Override
	public void deactivate() {
		if (isGameOver) {
			this.score = 0;
			grid.reset();
			updateScoreTexts();

			resultView.deactivate();
			view.unregisterDrawable(resultView);
		}
		super.deactivate();
	}
	
	protected void setN() {
		GlobalContext.n = 1;
	}

	@Override
	public void init() {
		resultView.deactivate();
		view.unregisterDrawable(resultView);

		resetButton.activate();

		grid.init(matrixSize);
		
		GlobalContext.reset();
		
		setN();

		isGameOver = false;

		GridState state = DoubleUpStateManager.getGridState(getGridStateKey());
		if (state == null) {
			putRandom();
			putRandom();

			// if (Game.isDebug()) {
			// for (int i = 0; i < matrixSize * matrixSize - 4; i++)
			// putRandom((int) Math.pow(2, i));
			// }

			score = 0;

			saveState();
		} else {
			grid.loadState(state);
			score = state.getScore();
		}

		updateScoreTexts();

		DoubleUpAds.showAd();

		if (matrixSize == 2) {
			TurpClient.sendStat(StatActions.StartPlaying2x2);
		}
		else if (matrixSize == 3) {
			TurpClient.sendStat(StatActions.StartPlaying3x3);
		}
		else if (matrixSize == 4) {
			TurpClient.sendStat(StatActions.StartPlaying4x4);
		}
		else if (matrixSize == 5) {
			TurpClient.sendStat(StatActions.StartPlaying5x5);
		}
	}

	@Override
	protected boolean beforeMove() {
		if (isGameOver)
			return false;
		GlobalContext.resetMove();
		return true;
	}

	@Override
	protected void afterMove() {
		if (!GlobalContext.didMove) {
			DoubleUpAudio.playNoMoveSound();
			return;
		}

		if (GlobalContext.moveScore > 0) {
			score += GlobalContext.moveScore;

			if (score > getHiScore()) {
				GlobalContext.hasNewHiScore = true;
				setHiScore(score);
			}

			GlobalContext.finalMax = grid.getMaxNumber();
			if (GlobalContext.finalMax > getMaxNumber()) {
				GlobalContext.hasNewMaxNumber = true;
				setMaxNumber(GlobalContext.finalMax);
			}

			updateScoreTexts();
		} else {
			DoubleUpAudio.playNoScoreSound();
		}

		putRandom();

		if (grid.hasMove()) {
			saveState();
		} else {
			onGameOver();
		}
	}

	private void saveState() {
		GridState state = grid.getState();
		state.setScore(score);
		DoubleUpStateManager.saveGridState(getGridStateKey(), state);
	}

	private void updateScoreTexts() {
		scoreArea.setScore(score);
		hiscoreArea.setScore(getHiScore());
		hiscoreBlockArea.setScore(getMaxNumber());
	}

	private void onGameOver() {

		DoubleUpAudio.playGameOverSound();
		DoubleUpStateManager.deleteGridState(getGridStateKey());

		GlobalContext.finalScore = this.score;

		updateScoreTexts();

		resetButton.deactivate();

		isGameOver = true;

		resultView.activate();
		view.registerDrawable(resultView, Game.LAYER_DIALOG);
	}

	protected String getGridStateKey() {
		return "grid-state-" + grid.getMatrixSize();
	}
	
	protected int getHiScore() {
		return DoubleUpSettings.getHiScore(matrixSize);
	}
	
	protected void setHiScore(int score) {
		DoubleUpSettings.setHiScore(matrixSize, score);
	}
	
	protected int getMaxNumber() {
		return DoubleUpSettings.getMaxNumber(matrixSize);
	}
	
	protected void setMaxNumber(int n) {
		DoubleUpSettings.setMaxNumber(matrixSize, n);
	}
}
