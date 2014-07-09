package com.turpgames.doubleup.view;

import com.turpgames.doubleup.controllers._2048.DoubleUp2048Controller;
import com.turpgames.doubleup.controllers._2048.DoubleUpForJoshController;

public abstract class GameDoubleUpForJoshScreen extends GameScreen {
	@Override
	protected int getMatrixSize() {
		return 4;
	}

	@Override
	protected DoubleUp2048Controller createController() {
		return new DoubleUpForJoshController(this, getMatrixSize(), getN());
	}
	
	protected abstract int getN();
}
