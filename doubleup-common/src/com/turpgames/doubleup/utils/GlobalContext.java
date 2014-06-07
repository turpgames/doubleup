package com.turpgames.doubleup.utils;

import com.turpgames.doubleup.entities.Grid;


public class GlobalContext {
	public static Grid grid;
	public static int moveScore;
	public static int finalScore;
	public static int finalMax;
	public static boolean didMove;
	public static boolean hasNewHiScore;
	public static boolean hasNewMaxNumber;
	
	public static void resetMove() {
		didMove = false;
		moveScore = 0;
	}
	
	public static void reset(Grid grid) {
		GlobalContext.grid = grid;
		finalScore = 0;
		hasNewHiScore = false;
		hasNewMaxNumber = false;
		resetMove();
	}
}
