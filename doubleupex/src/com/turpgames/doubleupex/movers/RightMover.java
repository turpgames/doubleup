package com.turpgames.doubleupex.movers;

import com.turpgames.doubleupex.objects2.Cell;
import com.turpgames.doubleupex.objects2.Grid;
import com.turpgames.doubleupex.objects2.Row;

class RightMover extends GridMover {

	@Override
	public void move(Grid grid) {
		int matrixSize = grid.getMatrixSize();
		for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
			for (int colIndex = matrixSize - 1; colIndex >= 0; colIndex--) {
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
		int matrixSize = grid.getMatrixSize();
		int colIndex = cell.getColIndex();

		for (int i = colIndex + 1; i < matrixSize; i++) {
			if (row.getCell(i).isBrick()) {
				return i;
			}
		}

		return matrixSize;
	}

	private Cell findTargetCell(Cell cell, int endIndex) {
		Row row = cell.getRow();
		int colIndex = cell.getColIndex();

		Cell target = null;

		for (int i = colIndex + 1; i < endIndex; i++) {
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
