package com.turpgames.doubleupex.objects;

import java.io.Serializable;

public class RowState implements Serializable {
	private static final long serialVersionUID = 6935171845174691969L;

	private CellState cells[];

	public CellState[] getCells() {
		return cells;
	}

	public void setCells(CellState[] cells) {
		this.cells = cells;
	}
}