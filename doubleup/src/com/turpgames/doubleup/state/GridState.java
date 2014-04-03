package com.turpgames.doubleup.state;

import java.io.Serializable;

public class GridState implements Serializable {
	private static final long serialVersionUID = -7190257567355292842L;
	
	private int matrixSize;
	private RowState[] rows;

	public int getMatrixSize() {
		return matrixSize;
	}

	public void setMatrixSize(int matrixSize) {
		this.matrixSize = matrixSize;
	}

	public RowState[] getRows() {
		return rows;
	}

	public void setRows(RowState[] rows) {
		this.rows = rows;
	}
}