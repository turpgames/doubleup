package com.turpgames.doubleupex.objects;

import com.turpgames.doubleupex.utils.Textures;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;

public class Cell extends GameObject {
	private final int matrixSize = GlobalContext.matrixSize;

	public final float size = Table.size / GlobalContext.matrixSize;

	final int colIndex;
	final Row row;
	final Table table;

	private Tile tile;
	private final CellState state;

	Cell(Table table, Row row, int colIndex) {
		this.table = table;
		this.row = row;
		this.colIndex = colIndex;

		setWidth(size);
		setHeight(size);
		float dx = (Game.getVirtualWidth() - Table.size) / 2;
		float dy = (Game.getVirtualHeight() - Table.size) / 2;
		getLocation().set(dx + size * colIndex,
				dy + size * (matrixSize - 1 - row.rowIndex));
		getRotation().origin.set(getLocation().x + size / 2, getLocation().y
				+ size / 2);
		this.getColor().set(Color.fromHex("#f0f0ff20"));

		state = new CellState();
	}

	public void moveRight() {
		if (this.isEmpty())
			return;

		if (this.isBrick())
			return;

		int limit = matrixSize - 1;
		for (int i = matrixSize - 1; i > colIndex; i--) {
			if (row.getCell(i).isBrick()) {
				limit = i - 1;
			}
		}

		for (int i = limit; i > colIndex; i--) {
			if (row.getCell(i).isEmpty()) {
				move(row.getCell(i));
				return;
			}
		}
	}

	public void addRight() {
		if (this.isEmpty())
			return;

		if (this.isBrick())
			return;

		if (this.colIndex == matrixSize - 1)
			return;

		if (row.getCellValue(colIndex + 1) != this.getValue())
			return;

		add(row.getCell(colIndex + 1));
	}

	public void moveLeft() {
		if (this.isEmpty())
			return;

		if (this.isBrick())
			return;

		int limit = 0;
		for (int i = 0; i < colIndex; i++) {
			if (row.getCell(i).isBrick()) {
				limit = i + 1;
			}
		}

		for (int i = limit; i < colIndex; i++) {
			if (row.getCell(i).isEmpty()) {
				move(row.getCell(i));
				return;
			}
		}
	}

	public void addLeft() {
		if (this.isEmpty())
			return;

		if (this.isBrick())
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

		if (this.isBrick())
			return;

		int limit = 0;
		for (int i = 0; i < row.rowIndex; i++) {
			if (table.getCell(i, colIndex).isBrick()) {
				limit = i + 1;
			}
		}

		for (int i = limit; i < row.rowIndex; i++) {
			if (table.getCell(i, colIndex).isEmpty()) {
				move(table.getCell(i, colIndex));
				return;
			}
		}
	}

	public void addUp() {
		if (this.isEmpty())
			return;

		if (this.isBrick())
			return;

		if (this.row.rowIndex == 0)
			return;

		if (table.getCellValue(row.rowIndex - 1, colIndex) != this.getValue())
			return;

		add(table.getCell(row.rowIndex - 1, colIndex));
	}

	public void moveDown() {
		if (this.isEmpty())
			return;

		if (this.isBrick())
			return;

		int limit = matrixSize - 1;
		for (int i = matrixSize - 1; i > row.rowIndex; i--) {
			if (table.getCell(i, colIndex).isBrick()) {
				limit = i - 1;
			}
		}

		for (int i = limit; i > row.rowIndex; i--) {
			if (table.getCell(i, colIndex).isEmpty()) {
				move(table.getCell(i, colIndex));
				return;
			}
		}
	}

	public void addDown() {
		if (this.isEmpty())
			return;

		if (this.isBrick())
			return;

		if (this.row.rowIndex == matrixSize - 1)
			return;

		if (table.getCellValue(row.rowIndex + 1, colIndex) != this.getValue())
			return;

		add(table.getCell(row.rowIndex + 1, colIndex));
	}

	public int getValue() {
		if (isEmpty())
			return 0;
		return tile.getValue();
	}

	private void add(Cell toCell) {
		this.tile.addTo(toCell);
		this.tile = null;
	}

	private void move(Cell toCell) {
		this.tile.moveToCell(toCell);
		toCell.setTile(this.tile);
		this.tile = null;
	}

	public void setTile(Tile tile) {
		if (!this.isEmpty()) {
			throw new UnsupportedOperationException("Cell is not empty!");
		}
		this.tile = tile;
	}

	public boolean isEmpty() {
		return tile == null;
	}

	public boolean isBrick() {
		return !isEmpty() && tile.isBrick();
	}

	void empty() {
		tile = null;
	}

	@Override
	public void draw() {
		TextureDrawer.draw(Textures.tile, this);
		if (tile != null)
			tile.draw();
	}

	public Tile getTile() {
		return tile;
	}

	public CellState getState() {
		if (tile == null)
			state.setTileState(null);
		else if (tile.getValue() == 0) // pratikte bu if'e girmemesi lazim
			state.setTileState(null);
		else
			state.setTileState(tile.getState());
		return state;
	}

	void loadState(CellState cellState) {
		tile = Tile.fromState(cellState.getTileState());
		if (tile != null)
			tile.fitToCell(this);
	}
}
