package com.turpgames.doubleupex.objects;

public class TileAddCommand extends TileCommand {
	public Tile from;
	public Tile to;

	@Override
	public void execute() {
		from.setValue(0);

		to.setValue(to.getValue() * 2);
		to.runAddEffect();
		
		updateMaxPow(to.getValue());

		DoubleUpAudio.playScoreSound(to.getValue());

		GlobalContext.moveScore += to.getValue();
		GlobalContext.hasMove = true;
	}

	private void updateMaxPow(int value) {
		int pow = 0;
		while(value / 2 > 0) {
			pow++;
			value/=2;
		}
		if (pow >= GlobalContext.maxPower)
			GlobalContext.maxPower = pow;
	}
}
