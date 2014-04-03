package com.turpgames.doubleupex.movers;

import com.turpgames.doubleupex.objects2.Cell;
import com.turpgames.doubleupex.objects2.Grid;
import com.turpgames.doubleupex.objects2.Row;

class DownMover extends GridMover {

	@Override
	public void move(Grid grid) {
		int matrixSize = grid.getMatrixSize();
		for (int colIndex = 0; colIndex < matrixSize; colIndex++) {
			for (int rowIndex = matrixSize - 1; rowIndex >= 0; rowIndex--) {
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
		int rowIndex = row.getRowIndex();
		int colIndex = cell.getColIndex();

		for (int i = rowIndex + 1; i < matrixSize; i++) {
			if (grid.getCell(i, colIndex).isBrick()) {
				return i;
			}
		}

		return matrixSize;
	}

	private Cell findTargetCell(Cell cell, int endIndex) {
		Grid grid = cell.getGrid();
		Row row = cell.getRow();
		int rowIndex = row.getRowIndex();
		int colIndex = cell.getColIndex();
		
		Cell target = null;

		for (int i = rowIndex + 1; i < endIndex; i++) {
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