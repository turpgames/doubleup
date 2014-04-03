package com.turpgames.doubleupex.objects;

import com.turpgames.doubleupex.utils.DoubleUpSettings;


public class GlobalContext {
	public static Table table;
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
	
	public static void reset(Table table) {
		GlobalContext.table = table;
		resetMove();
		finalScore = table.getScore();
		matrixSize = table.getMatrixSize();
		max = DoubleUpSettings.getHiScore();
		maxPower = DoubleUpSettings.getMaxNumber();
	}
}
