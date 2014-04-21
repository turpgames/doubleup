package com.turpgames.doubleup.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Score implements Serializable {
	private static final long serialVersionUID = 3869159083949235971L;

	public static final int Mode4x4 = 1;
	public static final int Mode5x5 = 2;

	public static final int Daily = 1;
	public static final int Weekly = 7;
	public static final int Monthly = 30;
	public static final int AllTime = -1;

	public static final int MyScores = 1;
	public static final int FriendsScores = 2;
	public static final int General = 3;

	private int playerId;
	private int mode;
	private int score;
	private int maxNumber;
	private long time;

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Date getDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return cal.getTime();
	}
}
