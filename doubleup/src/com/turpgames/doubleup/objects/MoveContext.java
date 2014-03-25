package com.turpgames.doubleup.objects;

public class MoveContext {
	public static boolean hasMove;
	public static int score;
	
	public static void reset() {
		hasMove = false;
		score = 0;
	}
}
