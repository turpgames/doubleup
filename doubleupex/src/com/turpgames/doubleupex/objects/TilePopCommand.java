package com.turpgames.doubleupex.objects;

public class TilePopCommand extends TileCommand {
	public Tile tile;
	public Cell cell;

	@Override
	public void execute() {
		tile.fitToCell(cell);
		tile.runPopEffect();
	}
}
