package com.turpgames.doubleup.utils;

import java.util.ArrayList;

import com.turpgames.doubleup.entity.LeadersBoard;
import com.turpgames.doubleup.entity.Score;
import com.turpgames.doubleup.leadersboard.LeadersBoardCache;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.utils.Util;

public class ScoreManager {

	public static interface ILeadersBoardCallback {
		void onSuccess(LeadersBoard leadersBoard);

		void onFail(Throwable t);
	}

	public final static ScoreManager instance = new ScoreManager();

	private int scoreToSendIndex;
	private ArrayList<Score> scoresToSend;

	private ScoreManager() {
		loadScoresToSend();
		addOldHiScores();
		sendScoresInBackground();
	}

	public void sendScore(int mode, int score, int maxNumber) {
		addScore(mode, score, maxNumber);
		sendScoresInBackground();
	}

	public void getLeadersBoard(final int mode, final int days, final int whose, final ILeadersBoardCallback callback) {
		if (!Game.isDebug()) {
			LeadersBoard leadersBoard = LeadersBoardCache.getLeadersBoard(mode, days, whose, false);
			if (leadersBoard != null) {
				callback.onSuccess(leadersBoard);
				return;
			}
		}

		DoubleUp.blockUI("Loading scores...");
		DoubleUpClient.getLeadersBoard(mode, days, whose, new ILeadersBoardCallback() {
			@Override
			public void onSuccess(LeadersBoard leadersBoard) {
				DoubleUp.unblockUI();
				LeadersBoardCache.putLeadersBoard(mode, days, whose, leadersBoard);
				callback.onSuccess(leadersBoard);
			}

			@Override
			public void onFail(Throwable t) {
				DoubleUp.unblockUI();
				LeadersBoard leadersBoard = LeadersBoardCache.getLeadersBoard(mode, days, whose, true);
				if (leadersBoard == null) {
					callback.onFail(t);
				} else {
					callback.onSuccess(leadersBoard);
				}
			}
		});
	}

	public void sendScoresInBackground() {
		if (Facebook.isLoggedIn()) {
			Util.Threading.runInBackground(new Runnable() {
				@Override
				public void run() {
					sendScores();
				}
			});
		}
	}

	private void sendScores() {
		try {
			loadScoresToSend();
			scoreToSendIndex = 0;
			sendNextScore();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private synchronized void sendNextScore() {
		if (scoresToSend.size() == 0 || scoreToSendIndex >= scoresToSend.size())
			return;

		Score score = scoresToSend.get(scoreToSendIndex);
		score.setPlayerId(DoubleUpClient.getPlayer().getId());
		scoreToSendIndex++;
		sendScoreImpl(score);
	}

	private synchronized void sendScoreImpl(final Score score) {
		DoubleUpClient.sendScore(score, new ICallback() {
			@Override
			public void onSuccess() {
				removeScore(score);
				scoreToSendIndex--;
				sendNextScore();
			}

			@Override
			public void onFail(Throwable t) {
				sendNextScore();
			}
		});
	}

	private synchronized void addScore(int mode, int score, int maxNumber) {
		Score scr = new Score();
		scr.setMode(mode);
		scr.setScore(score);
		scr.setMaxNumber(maxNumber);

		scoresToSend.add(scr);
		saveScoresToSend();
	}

	private synchronized void removeScore(Score score) {
		scoresToSend.remove(score);
		saveScoresToSend();
	}

	private synchronized void saveScoresToSend() {
		CommonSettings.setScoresToSend(scoresToSend);
	}

	private synchronized void loadScoresToSend() {
		scoresToSend = CommonSettings.getScoresToSend();
	}

	private void addOldHiScores() {
		final String flagSettingsKey = "old-hiscores-sent-to-server";

		boolean scoresAlreadySent = Settings.getBoolean(flagSettingsKey, false);
		if (scoresAlreadySent)
			return;

		int matrixSize = GlobalContext.matrixSize;

		GlobalContext.matrixSize = 4;
		addOldHiScore(Score.Mode4x4, DoubleUpSettings.getHiScore(), DoubleUpSettings.getMaxNumber());

		GlobalContext.matrixSize = 5;
		addOldHiScore(Score.Mode5x5, DoubleUpSettings.getHiScore(), DoubleUpSettings.getMaxNumber());

		GlobalContext.matrixSize = matrixSize;

		Settings.putBoolean(flagSettingsKey, true);
	}

	private void addOldHiScore(int mode, int score, int maxNumber) {
		if (score > 0)
			addScore(mode, score, maxNumber);
	}
}
