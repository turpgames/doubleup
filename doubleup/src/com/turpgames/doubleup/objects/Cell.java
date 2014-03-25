package com.turpgames.doubleup.objects;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;

class Cell extends GameObject {
	private final static int matrixSize = Table.matrixSize;

	public final static float size = Table.size / Table.matrixSize;

	final int colIndex;
	final Row row;
	final Table table;

	Tile tile;

	Cell(Table table, Row row, int colIndex) {
		this.table = table;
		this.row = row;
		this.colIndex = colIndex;

		setWidth(size);
		setHeight(size);
		float dx = (Game.getVirtualWidth() - Table.size) / 2;
		float dy = (Game.getVirtualHeight() - Table.size) / 2;
		getLocation().set(dx + size * colIndex, dy + size * (matrixSize - 1 - row.rowIndex));
		getRotation().origin.set(getLocation().x + size / 2, getLocation().y + size / 2);
	}

	public void moveRight() {
		if (this.isEmpty())
			return;

		for (int i = matrixSize - 1; i > colIndex; i--) {
			if (row.getCell(i).isEmpty()) {
				move(row.getCell(i));
				return;
			}
		}
	}

	public void addRight() {
		if (this.isEmpty())
			return;

		if (this.colIndex == matrixSize - 1)
			return;
		
		if (row.getCellValue(colIndex + 1) != this.getValue())
			return;
		
		add(row.getCell(colIndex + 1));
	}

	public void moveLeft() {
		if (this.isEmpty()) {
			return;
		}

		for (int i = 0; i < colIndex; i++) {
			if (row.getCell(i).isEmpty()) {
				move(row.getCell(i));
				return;
			}
		}
	}

	public void addLeft() {
		if (this.isEmpty())
			return;

		if (this.colIndex == 0)
			return;
		
		if (row.getCellValue(colIndex - 1) != this.getValue())
			return;

		add(row.getCell(colIndex - 1));
	}

	public void moveUp() {
		if (this.isEmpty()) {
			return;
		}

		for (int i = 0; i < row.rowIndex; i++) {
			if (table.getCell(i, colIndex).isEmpty()) {
				move(table.getCell(i, colIndex));
				return;
			}
		}
	}

	public void addUp() {
		if (this.isEmpty())
			return;

		if (this.row.rowIndex == 0)
			return;
		
		if (table.getCellValue(row.rowIndex - 1, colIndex) != this.getValue())
			return;

		add(table.getCell(row.rowIndex - 1, colIndex));
	}

	public void moveDown() {
		if (this.isEmpty()) {
			return;
		}

		for (int i = matrixSize - 1; i > row.rowIndex; i--) {
			if (table.getCell(i, colIndex).isEmpty()) {
				move(table.getCell(i, colIndex));
				return;
			}
		}
	}

	public void addDown() {
		if (this.isEmpty())
			return;

		if (this.row.rowIndex == matrixSize - 1)
			return;
		
		if (table.getCellValue(row.rowIndex + 1, colIndex) != this.getValue())
			return;

		add(table.getCell(row.rowIndex + 1, colIndex));
	}

	int getValue() {
		return tile == null ? 0 : tile.getValue();
	}

	private void add(Cell toCell) {
		this.tile.addTo(toCell.tile);
	}

	private void move(Cell toCell) {
		this.tile.moveToCell(toCell);
	}
	
	public void executeCommands() {
		if (tile != null)
			tile.executeCommands();
	}

	void setTile(Tile tile) {
		if (this.tile != null && tile != null)
			throw new UnsupportedOperationException("Cell already has a tile!");
		this.tile = tile;
	}

	public boolean isEmpty() {
		return tile == null;
	}

	void reset() {
		if (tile != null) {
			tile = null;
		}
	}

	@Override
	public void draw() {
		if (tile != null)
			tile.draw();
	}

	// void beginUpdate() {
	// if (tile != null)
	// tile.beginUpdate();
	// }
	//
	// void endUpdate() {
	// if (tile != null)
	// tile.endUpdate();
	// }
}
