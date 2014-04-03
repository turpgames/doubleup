package com.turpgames.doubleup.movers;

import com.turpgames.doubleup.entities.Cell;
import com.turpgames.doubleup.entities.Grid;
import com.turpgames.doubleup.entities.Row;

class UpMover extends GridMover {

	@Override
	public void move(Grid grid) {
		int matrixSize = grid.getMatrixSize();
		for (int colIndex = 0; colIndex < matrixSize; colIndex++) {
			for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
				move(grid.getCell(rowIndex, colIndex));
			}
		}
	}

	@Override
	protected Cell findTargetCell(Cell cell) {
		int brickIndex = getClosestBrickIndex(cell);
		return findTargetCell(cell, brickIndex);
	}

	private int getClosestBrickIndex(Cell cell) {
		Grid grid = cell.getGrid();
		Row row = cell.getRow();
		int rowIndex = row.getRowIndex();
		int colIndex = cell.getColIndex();

		for (int i = rowIndex - 1; i >= 0; i--) {
			if (grid.getCell(i, colIndex).isBrick()) {
				return i;
			}
		}

		return -1;
	}

	private Cell findTargetCell(Cell cell, int endIndex) {
		Grid grid = cell.getGrid();
		Row row = cell.getRow();
		int rowIndex = row.getRowIndex();
		int colIndex = cell.getColIndex();
		
		Cell target = null;

		for (int i = rowIndex - 1; i > endIndex; i--) {
			Cell targetCandidate = grid.getCell(i, colIndex);
			
			if (targetCandidate.isEmpty()) {
				target = targetCandidate;
				continue;
			}
			
			if (targetCandidate.getValue() == cell.getValue() && !targetCandidate.getTile().isUsed()) {
				target = targetCandidate;
			}

			break;
		}

		return target;
	}
}