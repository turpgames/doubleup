package com.turpgames.doubleupex.objects2;

import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.effects.scaling.ScaleUpEffect;
import com.turpgames.framework.v0.util.Timer;

public class TilePopEffect implements ITileEffect {
	private final ScaleUpEffect popEffect;
	private final Timer popEffectDelayTimer;
	private final Tile tile;

	public TilePopEffect(Tile tile, float effectDuration) {
		this.tile = tile;
		
		popEffectDelayTimer = new Timer();
		popEffectDelayTimer.setInterval(effectDuration);
		popEffectDelayTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				startEffect();
			}
		});

		popEffect = new ScaleUpEffect(tile);
		popEffect.setDuration(effectDuration * 1.5f);
		popEffect.setLooping(false);
		popEffect.setMaxScale(1.0f);
		popEffect.setMinScale(0.2f);
		popEffect.setListener(new IEffectEndListener() {
			@Override
			public boolean onEffectEnd(Object obj) {
				endEffect();
				return true;
			}
		});
	}
	
	@Override
	public void start() {
		popEffectDelayTimer.start();
	}

	@Override
	public void stop() {
		popEffect.stop();
		popEffectDelayTimer.stop();
		endEffect();
	}

	private void startEffect() {
		tile.setVisible(true);
		popEffectDelayTimer.stop();
		popEffect.start();
	}
	
	private void endEffect() {
		tile.setScale(1f);
	}
}
