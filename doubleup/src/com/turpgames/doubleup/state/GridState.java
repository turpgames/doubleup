package com.turpgames.doubleup.state;

import java.io.Serializable;

public class GridState implements Serializable {
	private static final long serialVersionUID = -7190257567355292842L;

	private int score;
	private RowState[] rows;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public RowState[] getRows() {
		return rows;
	}

	public void setRows(RowState[] rows) {
		this.rows = rows;
	}
}