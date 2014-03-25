package com.turpgames.doubleup.objects;


public class TileMoveCommand extends TileCommand {
	public Tile tile;
	public Cell to;

	@Override
	public void execute() {
		to.setTile(tile);
		tile.cell.setTile(null);
		tile.setCell(to);
		
		MoveContext.hasMove = true;

		// MovingEffect moveEffect = new MovingEffect(tile);
		// moveEffect.setLooping(false);
		// moveEffect.setDuration(0.1f);
		// moveEffect.setFrom(tile.getLocation());
		// moveEffect.setTo(to.getLocation());
		// moveEffect.start(new IEffectEndListener() {
		// @Override
		// public boolean onEffectEnd(Object obj) {
		// to.setTile(tile);
		// tile.cell.setTile(null);
		// return true;
		// }
		// });
	}
}
