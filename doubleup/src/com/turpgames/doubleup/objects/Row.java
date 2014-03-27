package com.turpgames.doubleup.objects;

class Row {
	private final Cell[] cells;
	final Table table;
	final int rowIndex;

	Row(Table table, int rowIndex) {
		this.table = table;
		this.rowIndex = rowIndex;
		this.cells = new Cell[Table.matrixSize];

		for (int i = 0; i < cells.length; i++) {
			cells[i] = new Cell(table, this, i);
		}
	}

	public void reset() {
		for (int i = 0; i < cells.length; i++) {
			cells[i].reset();
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
}
