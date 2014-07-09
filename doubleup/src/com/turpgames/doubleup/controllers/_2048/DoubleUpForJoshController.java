package com.turpgames.doubleup.controllers._2048;

import com.turpgames.doubleup.utils.DoubleUpSettings;
import com.turpgames.doubleup.utils.GlobalContext;
import com.turpgames.doubleup.utils.StatActions;
import com.turpgames.doubleup.view.IDoubleUpView;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.utils.Util;

public class DoubleUpForJoshController extends DoubleUp2048Controller {
	private final int n;

	public DoubleUpForJoshController(IDoubleUpView view, int matrixSize, int n) {
		super(view, matrixSize);
		this.n = n;
	}
	
	@Override
	protected void setN() {
		GlobalContext.n = n;
	}
	
	@Override
	public void init() {
		super.init();

		if (n == 3) {
			TurpClient.sendStat(StatActions.StartPlaying3);
		}
		else if (n == 5) {
			TurpClient.sendStat(StatActions.StartPlaying5);
		}
		else if (n == 7) {
			TurpClient.sendStat(StatActions.StartPlaying7);
		}
		else if (n == 9) {
			TurpClient.sendStat(StatActions.StartPlaying9);
		}
	}

	@Override
	protected void putRandom() {
		putRandom(Util.Random.randInt(10) == 0 ? 2 * n : n);
	}

	@Override
	protected String getGridStateKey() {
		return super.getGridStateKey() + "-" + n;
	}

	@Override
	protected int getHiScore() {
		return DoubleUpSettings.getHiScore(matrixSize + n);
	}

	@Override
	protected void setHiScore(int score) {
		DoubleUpSettings.setHiScore(matrixSize + n, score);
	}

	@Override
	protected int getMaxNumber() {
		return DoubleUpSettings.getMaxNumber(matrixSize + n);
	}

	@Override
	protected void setMaxNumber(int num) {
		DoubleUpSettings.setMaxNumber(matrixSize + n, num);
	}
}
