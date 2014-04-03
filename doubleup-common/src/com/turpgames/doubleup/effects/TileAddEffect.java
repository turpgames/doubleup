package com.turpgames.doubleup.effects;

import com.turpgames.doubleup.entities.Tile;
import com.turpgames.framework.v0.effects.BreathEffect;
import com.turpgames.framework.v0.effects.IEffectEndListener;

public class TileAddEffect implements ITileEffect {
	private final BreathEffect addEffect;
	private final Tile tile;

	public TileAddEffect(Tile tile, float effectDuration) {
		this.tile = tile;

		addEffect = new BreathEffect(tile);
		addEffect.setMinFactor(0.9f);
		addEffect.setMaxFactor(1.1f);
		addEffect.setFinalScale(1f);
		addEffect.setLooping(false);
		addEffect.setDuration(effectDuration);
		addEffect.setListener(new IEffectEndListener() {
			@Override
			public boolean onEffectEnd(Object obj) {
				effectEnd();
				return true;
			}
		});
	}

	@Override
	public void start() {
		addEffect.start();
	}

	@Override
	public void stop() {
		addEffect.stop();
		tile.getScale().set(1f);
	}

	private void effectEnd() {
		tile.getScale().set(1f);
	}
}
