package com.turpgames.doubleup.objects;

import com.turpgames.framework.v0.util.Game;

public class GlobalContext {
	public static Table table;
	
	public static boolean hasMove;
	public static int moveScore;
	public static long max;
	public static long finalScore;

	private static int effectCount;
	
	public static void resetMove() {
		hasMove = false;
		moveScore = 0;
		effectCount = 0;
	}
	
	public static void effectStart() {
		if (effectCount == 0)
			Game.getInputManager().deactivate();
		effectCount++;
	}
	
	public static void effectEnd() {
		effectCount--;
		if (effectCount == 0)
			Game.getInputManager().activate();
	}
}
