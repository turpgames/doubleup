package com.turpgames.doubleup.movers;

import com.turpgames.doubleup.entities.Cell;
import com.turpgames.doubleup.entities.Grid;
import com.turpgames.doubleup.entities.Tile;
import com.turpgames.doubleup.utils.DoubleUpAudio;
import com.turpgames.doubleup.utils.GlobalContext;

public abstract class GridMover implements IGridMover {
	protected final void move(Cell cell) {
		if (cell.isEmpty() || cell.isBrick())
			return;
		
		cell.getTile().setUsed(false);
		
		Cell targetCell = findTargetCell(cell);

		if (targetCell == null)
			return;
		
		GlobalContext.didMove = true;

		Tile tile = cell.getTile();
		cell.setTileAsTemp();

		if (targetCell.isEmpty()) {
			tile.runMoveEffect(targetCell, cell, false);

			targetCell.setTile(tile);
		}
		else if (targetCell.getValue() == tile.getValue()) {
			targetCell.getTile().setUsed(true);
			targetCell.getTile().setValue(targetCell.getValue() * 2);

			tile.runMoveEffect(targetCell, cell, true);

			DoubleUpAudio.playScoreSound(targetCell.getValue());
			GlobalContext.moveScore += targetCell.getValue();
		}
		else {
			throw new UnsupportedOperationException("Cannot move or add to target cell!");
		}
	}

	protected abstract Cell findTargetCell(Cell cell);

	private final static IGridMover downMover = new DownMover();
	private final static IGridMover upMover = new UpMover();
	private final static IGridMover leftMover = new LeftMover();
	private final static IGridMover rightMover = new RightMover();
	
	public static void moveDown(Grid grid) {
		downMover.move(grid);
	}
	
	public static void moveUp(Grid grid) {
		upMover.move(grid);
	}
	
	public static void moveLeft(Grid grid) {
		leftMover.move(grid);
	}
	
	public static void moveRight(Grid grid) {
		rightMover.move(grid);
	}
}
