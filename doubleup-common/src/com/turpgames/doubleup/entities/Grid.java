package com.turpgames.doubleup.entities;

import com.turpgames.doubleup.movers.GridMover;
import com.turpgames.doubleup.state.GridState;
import com.turpgames.doubleup.state.RowState;
import com.turpgames.doubleup.entities.Cell;
import com.turpgames.doubleup.entities.Row;
import com.turpgames.framework.v0.IDrawable;

public class Grid implements IDrawable {
	public final static float size = 512f;

	private int matrixSize;
	private Row[] rows;

	private final GridState state;

	public Grid() {
		state = new GridState();
	}

	public int getMatrixSize() {
		return matrixSize;
	}

	public void reset() {
		init(matrixSize);
	}

	public void init(int matrixSize) {
		this.matrixSize = matrixSize;

		rows = new Row[matrixSize];
		for (int i = 0; i < matrixSize; i++)
			rows[i] = new Row(this, i);
	}

	public Row[] getRows() {
		return rows;
	}

	public Row getRow(int rowIndex) {
		return rows[rowIndex];
	}

	public Cell getCell(int rowIndex, int colIndex) {
		return getRow(rowIndex).getCell(colIndex);
	}

	private int getCellValue(int rowIndex, int colIndex) {
		return getCell(rowIndex, colIndex).getValue();
	}

	public int getMaxNumber() {
		int max = 0;

		for (Row row : rows) {
			for (Cell cell : row.getCells()) {
				if (!cell.isEmpty() && cell.getValue() > max)
					max = cell.getValue();
			}
		}

		return max;
	}

	private boolean hasEmptyCell() {
		for (Row row : rows) {
			for (Cell cell : row.getCells()) {
				if (cell.isEmpty())
					return true;
			}
		}
		return false;
	}

	public boolean hasMove() {
		if (hasEmptyCell())
			return true;

		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < rows.length; j++) {
				if (getCell(i, j).isBrick())
					continue;
				
				if ((i > 0 && getCellValue(i, j) == getCellValue(i - 1, j)) ||
						(i < rows.length - 1 && getCellValue(i, j) == getCellValue(i + 1, j)) ||
						(j < rows.length - 1 && getCellValue(i, j) == getCellValue(i, j + 1)) ||
						(j > 0 && getCellValue(i, j) == getCellValue(i, j - 1)))
					return true;
			}
		}

		return false;
	}

	public Tile putBrick(int rowIndex, int colIndex) {
		Cell cell = getCell(rowIndex, colIndex);

		Tile tile = new Tile();
		tile.setBrick(true);
		tile.setVisible(true);
		tile.fitToCell(cell);
		tile.updateView();

		cell.setTile(tile);
		
		return tile;
	}

	public Tile putTile(int rowIndex, int colIndex, int value) {
		Cell cell = getCell(rowIndex, colIndex);

		Tile tile = new Tile();
		tile.setValue(value);
		tile.fitToCell(cell);
		tile.updateView();

		cell.setTile(tile);

		tile.runPopEffect();
		
		return tile;
	}

	public void moveDown() {
		GridMover.moveDown(this);
	}

	public void moveUp() {
		GridMover.moveUp(this);
	}

	public void moveLeft() {
		GridMover.moveLeft(this);
	}

	public void moveRight() {
		GridMover.moveRight(this);
	}

	@Override
	public void draw() {
		if (rows == null)
			return;

		for (Row row : rows) {
			for (Cell cell : row.getCells()) {
				cell.draw();
			}
		}
	}

	public GridState getState() {
		RowState[] rowStates = new RowState[rows.length];
		for (int i = 0; i < rows.length; i++) {
			rowStates[i] = rows[i].getState();
		}

		state.setRows(rowStates);

		return state;
	}

	public void loadState(GridState state) {
		RowState[] rowStates = state.getRows();

		for (int i = 0; i < rows.length; i++) {
			rows[i].loadState(rowStates[i]);
		}
	}
}
