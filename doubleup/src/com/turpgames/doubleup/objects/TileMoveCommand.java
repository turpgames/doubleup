package com.turpgames.doubleup.objects;

public class TileMoveCommand extends TileCommand {
	public Tile tile;
	public Cell to;

	@Override
	public void execute() {
		tile.runMoveEffect(to);
		MoveContext.hasMove = true;
	}
}
