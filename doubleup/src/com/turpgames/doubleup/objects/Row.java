package com.turpgames.doubleup.objects;

class Row {
	final Cell[] cells;
	final Table table;
	final int rowIndex;

	Row(Table table, int rowIndex) {
		this.table = table;
		this.rowIndex = rowIndex;
		this.cells = new Cell[4];

		for (int i = 0; i < cells.length; i++) {
			cells[i] = new Cell(table, this, i);
		}
	}

	public void reset() {
		for (int i = 0; i < cells.length; i++) {
			cells[i].reset();
		}
	}
}
