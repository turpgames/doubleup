package com.turpgames.doubleupex.controllers.mode1;

import com.turpgames.doubleup.controllers.GridController;
import com.turpgames.doubleup.state.GridState;
import com.turpgames.doubleup.utils.DoubleUpAudio;
import com.turpgames.doubleup.utils.DoubleUpStateManager;
import com.turpgames.doubleup.utils.GlobalContext;
import com.turpgames.doubleup.utils.R;
import com.turpgames.doubleup.view.IDoubleUpView;
import com.turpgames.framework.v0.impl.ScreenManager;

public abstract class Mode1LevelController extends GridController {
	private final int matrixSize;
	private int score;

	protected Mode1LevelController(IDoubleUpView view, int matrixSize) {
		super(view);
		this.matrixSize = matrixSize;
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
			initLevel();

			score = 0;

			saveState();
		} else {
			grid.loadState(state);
			score = state.getScore();
		}
	}

	protected abstract int[][] getLevelData();

	private void initLevel() {
		int[][] levelData = getLevelData();

		for (int i = 0; i < matrixSize; i++) {
			for (int j = 0; j < matrixSize; j++) {
				int value = levelData[i][j];
				if (value == 0)
					continue;

				if (value == -1) {
					grid.putBrick(i, j);
				}
				else {
					grid.putTile(i, j, value);
				}
			}
		}
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

	private void onGameOver() {
		DoubleUpAudio.playGameOverSound();
		DoubleUpStateManager.deleteGridState(getGridStateKey());

		GlobalContext.finalScore = this.score;
		this.score = 0;
		grid.reset();

		ScreenManager.instance.switchTo(R.screens.result, false);
	}

	private String getGridStateKey() {
		return "grid-state-" + grid.getMatrixSize();
	}
}
