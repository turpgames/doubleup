package com.turpgames.doubleup.migration;

import com.turpgames.entity.Score;

public class ScoreAdapter {
	private final Score oldScore;
	private final Score newScore;

	public ScoreAdapter(Score oldScore) {
		this.oldScore = oldScore;
		this.newScore = new Score();

		newScore.setExtraData(oldScore.getExtraData());
		newScore.setGameMode(oldScore.getGameMode());
		newScore.setScore(oldScore.getScore());
		newScore.setScoreTime(oldScore.getScoreTime());
		newScore.setGameId(oldScore.getGameId());
		newScore.setGameVersion(oldScore.getGameVersion());
	}

	public Score getOldScore() {
		return oldScore;
	}

	public Score getNewScore() {
		return newScore;
	}
}
