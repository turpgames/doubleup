package com.turpgames.doubleupex.objects2;

import com.turpgames.doubleupex.movers.GridMover;
import com.turpgames.doubleupex.objects2.Cell;
import com.turpgames.doubleupex.objects2.Row;
import com.turpgames.framework.v0.IDrawable;

public class Grid implements IDrawable {
	public final static float size = 512f;
	
	private int matrixSize;
	private Row[] rows;
	
	public int getMatrixSize() {
		return matrixSize;
	}
	
	public void init(int matrixSize) {
		this.matrixSize = matrixSize;
		
		rows = new Row[matrixSize];
		
		for (int i = 0; i < matrixSize; i++)
			rows[i] = new Row(this, i);
	}

	public Row getRow(int rowIndex) {
		return rows[rowIndex];
	}

	public Cell getCell(int rowIndex, int colIndex) {
		return getRow(rowIndex).getCell(colIndex);
	}
	
	public void putBrick(int rowIndex, int colIndex) {
		Cell cell = getCell(rowIndex, colIndex);
		
		Tile tile = new Tile();
		tile.setBrick(true);
		tile.setVisible(true);
		tile.fitToCell(cell);
		tile.updateView();
		
		cell.setTile(tile);
	}
	
	public void putTile(int rowIndex, int colIndex, int value) {
		Cell cell = getCell(rowIndex, colIndex);
		
		Tile tile = new Tile();
		tile.setValue(value);
		tile.fitToCell(cell);
		tile.updateView();
		
		cell.setTile(tile);
		
		tile.runPopEffect();
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
	
	public boolean hasEmptyCell() {
		for (Row row : rows) {
			for (Cell cell : row.getCells()) {
				if (cell.isEmpty())
					return true;
			}
		}
		return false;
	}
	
	private int getCellValue(int rowIndex, int colIndex) {
		return getCell(rowIndex, colIndex).getValue();
	}
	
	public boolean hasMove() {
		if (hasEmptyCell())
			return true;

		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < rows.length; j++) {
				if ((i > 0 && getCellValue(i, j) == getCellValue(i - 1, j))
						|| (i < rows.length - 1 && getCellValue(i, j) == getCellValue(i + 1, j))
						|| (j < rows.length - 1 && getCellValue(i, j) == getCellValue(i, j + 1))
						|| (j > 0 && getCellValue(i, j) == getCellValue(i,j - 1)))
					return true;
			}
		}

		return false;
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
}
