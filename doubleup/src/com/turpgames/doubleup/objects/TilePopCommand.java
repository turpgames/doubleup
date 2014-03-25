package com.turpgames.doubleup.objects;

public class TilePopCommand extends TileCommand {
	public Tile tile;
	public Cell cell;

	@Override
	public void execute() {
		tile.syncWithCell(cell);
		tile.runPopEffect();
	}
}
