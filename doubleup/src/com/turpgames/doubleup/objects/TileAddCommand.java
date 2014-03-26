package com.turpgames.doubleup.objects;

public class TileAddCommand extends TileCommand {
	public Tile from;
	public Tile to;

	@Override
	public void execute() {
		from.setValue(0);

		to.setValue(to.getValue() * 2);
		to.runAddEffect();

		DoubleUpAudio.playScoreSound(to.getValue());

		GlobalContext.moveScore += to.getValue();
		GlobalContext.hasMove = true;
	}
}
