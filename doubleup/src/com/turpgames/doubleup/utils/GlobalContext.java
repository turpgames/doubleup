package com.turpgames.doubleup.utils;

import com.turpgames.doubleup.entities.Grid;


public class GlobalContext {
	public static Grid grid;
	public static int matrixSize;
	public static boolean hasMove;
	public static int moveScore;
	public static int max;
	public static int maxPower;
	public static int finalScore;
	
	public static void resetMove() {
		hasMove = false;
		moveScore = 0;
	}
	
	public static void reset(Grid grid) {
		GlobalContext.grid = grid;
		resetMove();
		matrixSize = grid.getMatrixSize();
		max = DoubleUpSettings.getHiScore();
		maxPower = DoubleUpSettings.getMaxNumber();
	}
}
