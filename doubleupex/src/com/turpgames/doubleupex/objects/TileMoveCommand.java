package com.turpgames.doubleupex.objects;

public class TileMoveCommand extends TileCommand {
	public Tile tile;
	public Cell to;

	@Override
	public void execute() {
		tile.runMoveEffect(to);
		GlobalContext.hasMove = true;
	}
}