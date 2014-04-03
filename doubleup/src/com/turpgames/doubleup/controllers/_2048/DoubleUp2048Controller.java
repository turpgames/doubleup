package com.turpgames.doubleup.controllers._2048;

import com.turpgames.doubleup.controllers.GridController;
import com.turpgames.doubleup.view.IDoubleUpView;

public class DoubleUp2048Controller extends GridController {
	private final int matrixSize;
	
	public DoubleUp2048Controller(IDoubleUpView view, int matrixSize) {
		super(view);
		this.matrixSize = matrixSize;
	}

	@Override
	public String getId() {
		return "2048";
	}

	@Override
	public void init() {
		grid.init(matrixSize);
		putRandom();
		putRandom();
	}
}
