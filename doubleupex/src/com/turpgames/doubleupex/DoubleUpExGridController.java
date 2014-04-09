package com.turpgames.doubleupex;

import com.turpgames.doubleup.controllers.GridController;
import com.turpgames.doubleup.state.GridState;
import com.turpgames.doubleup.utils.DoubleUpStateManager;
import com.turpgames.doubleup.utils.GlobalContext;
import com.turpgames.doubleup.view.IDoubleUpView;

public abstract class DoubleUpExGridController extends GridController {
	protected final int matrixSize;
	
	public DoubleUpExGridController(IDoubleUpView view, int matrixSize) {
		super(view);
		this.matrixSize = matrixSize;
	}
	
	protected abstract int getMode();
	
	protected abstract int getLevel();

	protected abstract int[][] getLevelData();
	
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
			saveState();
		} else {
			grid.loadState(state);
		}
	}

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
	protected boolean beforeMove() {
		GlobalContext.resetMove();
		return true;
	}
	
	protected final void saveState() {
		GridState state = grid.getState();
		DoubleUpStateManager.saveGridState(getGridStateKey(), state);
	}

	protected final String getGridStateKey() {
		return "grid-state-" + getMode() + "-" + getLevel();
	}
}
