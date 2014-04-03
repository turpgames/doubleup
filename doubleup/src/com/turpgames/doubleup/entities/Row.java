package com.turpgames.doubleup.entities;

import com.turpgames.doubleup.state.CellState;
import com.turpgames.doubleup.state.RowState;

public class Row {
	private final Cell[] cells;
	private final Grid grid;
	private final int rowIndex;

	private final RowState state;

	public Row(Grid grid, int rowIndex) {
		this.grid = grid;
		this.rowIndex = rowIndex;
		this.cells = new Cell[grid.getMatrixSize()];

		this.state = new RowState();

		for (int i = 0; i < cells.length; i++) {
			cells[i] = new Cell(this, i);
		}
	}

	public Grid getGrid() {
		return grid;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public Cell getCell(int colIndex) {
		return cells[colIndex];
	}

	public Cell[] getCells() {
		return cells;
	}

	public RowState getState() {
		CellState[] cellStates = new CellState[cells.length];
		for (int i = 0; i < cells.length; i++)
			cellStates[i] = cells[i].getState();

		state.setCells(cellStates);

		return state;
	}
	
	public void loadState(RowState state) {
		CellState[] cellStates = state.getCells();

		for (int i = 0; i < cellStates.length; i++) {
			cells[i].loadState(cellStates[i]);
		}
	}
}
