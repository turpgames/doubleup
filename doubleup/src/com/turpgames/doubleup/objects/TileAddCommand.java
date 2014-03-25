package com.turpgames.doubleup.objects;

public class TileAddCommand extends TileCommand {
	public Tile from;
	public Tile to;
	
	@Override
	public void execute() {
		from.cell.setTile(null);
		from.setValue(0);
		from.setCell(null);
		
		DoubleUpAudio.playScoreSound(to.getValue());

		to.setValue(to.getValue() * 2);
		MoveContext.score += to.getValue();
		MoveContext.hasMove = true;
	}
}
