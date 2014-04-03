package com.turpgames.doubleupex.controllers.mode2;

import com.turpgames.doubleup.view.IDoubleUpView;

public class Level1 extends Mode2Level {
	public Level1(IDoubleUpView view) {
		super(view, 9);
	}

	@Override
	protected int[][] getLevelData() {
		return new int[][] {
				new int[] { 1, -1, 0, -1, 0, 8, 0, 0, 4 },
				new int[] { 0, 1, 1, 0, 0, 0, 0, 2, 0 },
				new int[] { 0, -1, 0, 1, -1, 0, 0, 4, 0 },
				new int[] { 0, 0, 2, 0, 4, 0, 0, 0, 0 },
				new int[] { 1, -1, 0, -1, 0, -1, 0, 8, 0 },
				new int[] { 0, 1, 1, 0, -1, 0, 0, -1, -1 },
				new int[] { 0, 0, 2, 0, -1, 2, -1, 16, 0 },
				new int[] { 0, -1, 0, 1, -1, 0, 0, -1, 1 },
				new int[] { 0, 0, 2, 0, 0, -1, 1, 0, 0 },
		};
	}

	@Override
	protected void putRandom() {
		super.putRandom();
	}

	@Override
	protected int getLevel() {
		return 1;
	}

	@Override
	protected int getTargetNumber() {
		return 64;
	}
}
