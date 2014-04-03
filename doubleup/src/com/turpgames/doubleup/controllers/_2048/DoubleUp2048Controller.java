package com.turpgames.doubleup.controllers._2048;

import com.turpgames.doubleup.components.ScoreArea;
import com.turpgames.doubleup.controllers.GridController;
import com.turpgames.doubleup.entities.Grid;
import com.turpgames.doubleup.state.GridState;
import com.turpgames.doubleup.utils.DoubleUpAudio;
import com.turpgames.doubleup.utils.DoubleUpSettings;
import com.turpgames.doubleup.utils.DoubleUpStateManager;
import com.turpgames.doubleup.utils.GlobalContext;
import com.turpgames.doubleup.utils.R;
import com.turpgames.doubleup.view.IDoubleUpView;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public class DoubleUp2048Controller extends GridController {
	private final int matrixSize;
	private int score;

	private final ScoreArea scoreArea;
	private final ScoreArea hiscoreArea;
	private final ScoreArea hiscoreBlockArea;

	public DoubleUp2048Controller(IDoubleUpView view, int matrixSize) {
		super(view);
		this.matrixSize = matrixSize;

		float x = (Game.getVirtualWidth() - Grid.size) / 2;
		float y = (Game.getVirtualHeight() - Grid.size) / 2;

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
	public void init() {
		GlobalContext.matrixSize = matrixSize;
		
		grid.init(matrixSize);

		GridState state = DoubleUpStateManager.getGridState(getGridStateKey());
		if (state == null) {
			putRandom();
			putRandom();

			score = 0;
			
			saveState();
		} else {
			grid.loadState(state);
			score = state.getScore();
		}

		updateScoreTexts();
	}

	@Override
	protected void beforeMove() {
		GlobalContext.resetMove();
	}

	@Override
	protected void afterMove() {
		if (!GlobalContext.didMove) {
			DoubleUpAudio.playNoMoveSound();
			return;
		}

		if (GlobalContext.moveScore > 0) {
			score += GlobalContext.moveScore;

			if (score > DoubleUpSettings.getHiScore()) {
				GlobalContext.hasNewHiScore = true;
				DoubleUpSettings.setHiScore(score);
			}

			int maxNumber = grid.getMaxNumber();
			if (maxNumber > DoubleUpSettings.getMaxNumber()) {
				GlobalContext.hasNewMaxNumber = true;
				DoubleUpSettings.setMaxNumber(maxNumber);
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
		hiscoreArea.setScore(DoubleUpSettings.getHiScore());
		hiscoreBlockArea.setScore(DoubleUpSettings.getMaxNumber());
	}

	private void onGameOver() {
		DoubleUpStateManager.deleteGridState(getGridStateKey());

		GlobalContext.finalScore = this.score;

		DoubleUpAudio.playGameOverSound();

		grid.reset();

		ScreenManager.instance.switchTo(R.screens.result, false);
	}

	private String getGridStateKey() {
		return "grid-state-" + grid.getMatrixSize();
	}
}
