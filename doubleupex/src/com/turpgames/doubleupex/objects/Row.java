package com.turpgames.doubleupex.objects;

public class Row {
	private final Cell[] cells;
	final Table table;
	final int rowIndex;

	private final RowState state;

	Row(Table table, int rowIndex) {
		this.table = table;
		this.rowIndex = rowIndex;
		this.cells = new Cell[GlobalContext.matrixSize];

		for (int i = 0; i < cells.length; i++) {
			cells[i] = new Cell(table, this, i);
		}

		state = new RowState();
	}

	public void empty() {
		for (int i = 0; i < cells.length; i++) {
			cells[i].empty();
		}
	}

	public Cell getCell(int colIndex) {
		return cells[colIndex];
	}

	public long getCellValue(int colIndex) {
		return cells[colIndex].getValue();
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

	void loadState(RowState rowState) {
		for (int i = 0; i < cells.length; i++) {
			cells[i].loadState(rowState.getCells()[i]);
		}
	}
}
