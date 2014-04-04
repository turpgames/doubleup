package com.turpgames.doubleupex.levels;

import java.io.Serializable;

public class LevelInfo implements Serializable {
	private static final long serialVersionUID = 1856988582125938788L;
	
	private String levelName;
	private boolean isLocked;
	private int score;
	private int mode;

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

}
