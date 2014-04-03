package com.turpgames.doubleupex.controllers.mode1;

import com.turpgames.doubleup.components.ScoreArea;
import com.turpgames.doubleup.entities.Grid;
import com.turpgames.doubleup.utils.DoubleUpAudio;
import com.turpgames.doubleup.utils.DoubleUpStateManager;
import com.turpgames.doubleup.utils.GlobalContext;
import com.turpgames.doubleup.utils.R;
import com.turpgames.doubleup.view.IDoubleUpView;
import com.turpgames.doubleupex.DoubleUpExGridController;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public abstract class Mode1Level extends DoubleUpExGridController {
	public Mode1Level(IDoubleUpView view, int matrixSize) {
		super(view, matrixSize);
		
		float y = (Game.getVirtualHeight() - Grid.size) / 2;

		ScoreArea targetNumberArea = new ScoreArea("Target");
		targetNumberArea.setScore(getTargetNumber());
		targetNumberArea.setLocation((Game.getVirtualWidth() - targetNumberArea.getWidth()) / 2, y - 75);
		
		view.registerDrawable(targetNumberArea, Game.LAYER_GAME);
	}

	@Override
	protected int getMode() {
		return 1;
	}

	@Override
	protected void afterMove() {
		if (!GlobalContext.didMove) {
			DoubleUpAudio.playNoMoveSound();
			return;
		}

		if (GlobalContext.moveScore > 0) {
			if (getTargetNumber() == grid.getMaxNumber()) {
				DoubleUpAudio.playGameOverSound();
				DoubleUpStateManager.deleteGridState(getGridStateKey());
				this.reset();
			}
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
	
	private void onGameOver() {
		DoubleUpAudio.playGameOverSound();
		DoubleUpStateManager.deleteGridState(getGridStateKey());

		grid.reset();

		ScreenManager.instance.switchTo(R.screens.result, false);
	}
	
	protected abstract int getTargetNumber();
}
