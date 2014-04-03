package com.turpgames.doubleup.effects;

import com.turpgames.doubleup.entities.Cell;
import com.turpgames.doubleup.entities.Tile;
import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.effects.moving.MovingEffect;

public class TileMoveEffect implements ITileEffect {

	private final MovingEffect moveEffect;
	private final Tile tile;
	private Tile toTile;
	private Cell toCell;
	private Cell fromCell;
	private boolean isBeingAdded;
	
	public TileMoveEffect(Tile tile, float effectDuration) {
		this.tile = tile;
		
		moveEffect = new MovingEffect(tile);
		moveEffect.setLooping(false);
		moveEffect.setDuration(effectDuration);
		moveEffect.setListener(new IEffectEndListener() {
			@Override
			public boolean onEffectEnd(Object obj) {
				effectEnd();
				return false;
			}
		});
	}
	
	public void setToCell(Cell toCell) {
		this.toCell = toCell;
		this.toTile = toCell.getTile();
	}
	
	public void setFromCell(Cell fromCell) {
		this.fromCell = fromCell;
	}
	
	public void setBeingAdded(boolean isBeingAdded) {
		this.isBeingAdded = isBeingAdded;
	}
	
	@Override
	public void start() {
		moveEffect.setFrom(tile.getLocation());
		moveEffect.setTo(toCell.getLocation());
		moveEffect.start();
	}

	@Override
	public void stop() {
		moveEffect.stop();
	}

	private void effectEnd() {
		if (isBeingAdded) {
			tile.setVisible(false);
			tile.setValue(0);
			fromCell.removeTempTile();
			
			toTile.setValue(toTile.getValue() * 2);
			toTile.updateView();
			toTile.runAddEffect();
		}
		else {
			tile.fitToCell(toCell);
			tile.updateView();
		}
	}
}
