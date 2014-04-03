package com.turpgames.doubleupex.controllers.mode1;

import com.turpgames.doubleup.view.IDoubleUpView;

public class Level1 extends Mode1Level {
	public Level1(IDoubleUpView view) {
		super(view, 9);
	}

	@Override
	protected int[][] getLevelData() {
		return new int[][] {
				new int[] { 1, -1, 0, -1, 0, 0, 0, 0, 0 },
				new int[] { 0, 1, 1, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, -1, 0, 1, 0, 0, 0, 0, 0 },
				new int[] { 0, 0, 2, 0, 0, 0, 0, 0, 0 },
				new int[] { 1, -1, 0, -1, 0, -1, 0, 0, 0 },
				new int[] { 0, 1, 1, 0, -1, 0, 0, -1, -1 },
				new int[] { 0, 0, 2, 0, -1, 0, -1, 16, 0 },
				new int[] { 0, -1, 0, 1, -1, 0, 0, -1, 0 },
				new int[] { 0, 0, 2, 0, 0, -1, 0, 0, 0 },
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
		return 128;
	}
}
