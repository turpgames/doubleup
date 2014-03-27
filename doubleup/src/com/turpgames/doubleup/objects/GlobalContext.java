package com.turpgames.doubleup.objects;


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
}
