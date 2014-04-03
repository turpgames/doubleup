package com.turpgames.doubleupex.objects;

import java.io.Serializable;

public class TableState implements Serializable {
	private static final long serialVersionUID = -7190257567355292842L;
	
	private int matrixSize;
	private int score;
	private RowState[] rows;

	public int getMatrixSize() {
		return matrixSize;
	}

	public void setMatrixSize(int matrixSize) {
		this.matrixSize = matrixSize;
	}

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