package com.turpgames.doubleup.objects;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;

class Cell extends GameObject {
	private final static int matrixSize = Table.matrixSize;
	
	public final static float size = 128f;

	final int colIndex;
	final Row row;
	final Table table;

	private Tile tile;

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

	public boolean moveRight() {
		if (this.isEmpty())
			return false;

		for (int i = matrixSize - 1; i > colIndex; i--) {
			if (row.getCell(i).isEmpty()) {
				move(row.getCell(i));
				return true;
			}
		}

		return false;
	}

	public int addRight() {
		if (this.isEmpty())
			return 0;

		if (this.colIndex == matrixSize - 1)
			return 0;

		if (row.getCellValue(colIndex + 1) != this.getValue())
			return 0;

		return add(row.getCell(colIndex + 1));
	}

	public boolean moveLeft() {
		if (this.isEmpty())
			return false;

		for (int i = 0; i < colIndex; i++) {
			if (row.getCell(i).isEmpty()) {
				move(row.getCell(i));
				return true;
			}
		}

		return false;
	}

	public int addLeft() {
		if (this.isEmpty())
			return 0;

		if (this.colIndex == 0)
			return 0;

		if (row.getCellValue(colIndex - 1) != this.getValue())
			return 0;

		return add(row.getCell(colIndex - 1));
	}

	public boolean moveUp() {
		if (this.isEmpty())
			return false;

		for (int i = 0; i < row.rowIndex; i++) {
			if (table.getCell(i, colIndex).isEmpty()) {
				move(table.getCell(i, colIndex));
				return true;
			}
		}

		return false;
	}

	public int addUp() {
		if (this.isEmpty())
			return 0;

		if (this.row.rowIndex == 0)
			return 0;
		
		if (table.getCellValue(row.rowIndex - 1, colIndex) != this.getValue())
			return 0;

		return add(table.getCell(row.rowIndex - 1, colIndex));
	}

	public boolean moveDown() {
		if (this.isEmpty())
			return false;

		for (int i = matrixSize - 1; i > row.rowIndex; i--) {
			if (table.getCell(i, colIndex).isEmpty()) {
				move(table.getCell(i, colIndex));
				return true;
			}
		}

		return false;
	}

	public int addDown() {
		if (this.isEmpty())
			return 0;

		if (this.row.rowIndex == matrixSize - 1)
			return 0;
		
		if (table.getCellValue(row.rowIndex + 1, colIndex) != this.getValue())
			return 0;

		return add(table.getCell(row.rowIndex + 1, colIndex));
	}

	int getValue() {
		return tile == null ? 0 : tile.getValue();
	}

	private int add(Cell toCell) {
		int score = this.tile.addTo(toCell.tile);
		this.tile = null;
		return score;
	}

	private void move(Cell toCell) {
		this.tile.moveToCell(toCell);
		toCell.setTile(this.tile);
		this.tile = null;
	}

	void setTile(Tile tile) {
		if (this.tile != null)
			throw new UnsupportedOperationException("Cell already has a tile!");
		this.tile = tile;
		tile.moveToCell(this);
	}

	public boolean isEmpty() {
		return tile == null;
	}

	void reset() {
		if (tile != null) {
			tile.dispose();
			tile = null;
		}
	}

	@Override
	public void draw() {
		if (tile != null)
			tile.draw();
	}

//	void beginUpdate() {
//		if (tile != null)
//			tile.beginUpdate();
//	}
//
//	void endUpdate() {
//		if (tile != null)
//			tile.endUpdate();
//	}
}
