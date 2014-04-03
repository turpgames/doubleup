package com.turpgames.doubleup.movers;

import com.turpgames.doubleup.entities.Cell;
import com.turpgames.doubleup.entities.Grid;
import com.turpgames.doubleup.entities.Row;

class LeftMover extends GridMover {

	@Override
	public void move(Grid grid) {
		int matrixSize = grid.getMatrixSize();
		for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
			for (int colIndex = 0; colIndex < matrixSize; colIndex++) {
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
		Row row = cell.getRow();
		int colIndex = cell.getColIndex();

		for (int i = colIndex - 1; i >= 0; i--) {
			if (row.getCell(i).isBrick()) {
				return i;
			}
		}

		return -1;
	}

	private Cell findTargetCell(Cell cell, int endIndex) {
		Row row = cell.getRow();
		int colIndex = cell.getColIndex();
		
		Cell target = null;

		for (int i = colIndex - 1; i > endIndex; i--) {
			Cell targetCandidate = row.getCell(i);

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
