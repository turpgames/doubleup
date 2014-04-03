package com.turpgames.doubleupex.movers;

import com.turpgames.doubleupex.objects.DoubleUpAudio;
import com.turpgames.doubleupex.objects.GlobalContext;
import com.turpgames.doubleupex.objects2.Cell;
import com.turpgames.doubleupex.objects2.Grid;
import com.turpgames.doubleupex.objects2.Tile;

public abstract class GridMover implements IGridMover {
	protected final void move(Cell cell) {
		if (cell.isEmpty() || cell.isBrick())
			return;
		
		cell.getTile().setUsed(false);
		
		Cell targetCell = findTargetCell(cell);

		if (targetCell == null)
			return;
		
		GlobalContext.hasMove = true;

		Tile tile = cell.getTile();
		cell.setTileAsTemp();

		if (targetCell.isEmpty()) {
			tile.runMoveEffect(targetCell, cell, false);

			targetCell.setTile(tile);
		}
		else if (targetCell.getValue() == tile.getValue()) {
			targetCell.getTile().setUsed(true);

			tile.runMoveEffect(targetCell, cell, true);
			
			DoubleUpAudio.playScoreSound(targetCell.getValue() * 2);
			GlobalContext.moveScore += targetCell.getValue() * 2;
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
