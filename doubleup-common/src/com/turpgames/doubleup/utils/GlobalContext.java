package com.turpgames.doubleup.utils;

public class GlobalContext {
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
	
	public static void reset() {
		finalScore = 0;
		hasNewHiScore = false;
		hasNewMaxNumber = false;
		resetMove();
	}
}
