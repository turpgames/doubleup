package com.turpgames.doubleupex.objects2;

public class Row {
	private final Cell[] cells;
	private final Grid grid;
	private final int rowIndex;

	public Row(Grid grid, int rowIndex) {
		this.grid = grid;
		this.rowIndex = rowIndex;
		this.cells = new Cell[grid.getMatrixSize()];

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
}
