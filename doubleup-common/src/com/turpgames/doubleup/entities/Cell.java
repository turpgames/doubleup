package com.turpgames.doubleup.entities;

import com.turpgames.doubleup.state.CellState;
import com.turpgames.doubleup.utils.Textures;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;

public class Cell extends GameObject {
	private Tile tile;
	private Tile tempTile;

	private final Row row;
	private final Grid grid;
	private final int colIndex;

	private final CellState state;

	public Cell(Row row, int colIndex) {
		this.row = row;
		this.grid = row.getGrid();
		this.colIndex = colIndex;

		this.state = new CellState();

		float size = Grid.size / grid.getMatrixSize();
		setWidth(size);
		setHeight(size);

		float dx = (Game.getVirtualWidth() - Grid.size) / 2;
		float dy = (Game.getVirtualHeight() - Grid.size) / 2;
		getLocation().set(dx + size * colIndex,
				dy + size * (grid.getMatrixSize() - 1 - row.getRowIndex()));
		getRotation().origin.set(getLocation().x + size / 2, getLocation().y
				+ size / 2);

		getColor().set(Color.fromHex("#f0f0ff20"));
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		if (!this.isEmpty() && tile != null) {
			throw new UnsupportedOperationException("Cell is not empty!");
		}
		this.tile = tile;
	}

	public Grid getGrid() {
		return grid;
	}

	public Row getRow() {
		return row;
	}

	public int getColIndex() {
		return colIndex;
	}

	public boolean isEmpty() {
		return tile == null;
	}

	public int getValue() {
		return isEmpty() ? 0 : tile.getValue();
	}

	public boolean isBrick() {
		return !isEmpty() && tile.isBrick();
	}

	public void setTileAsTemp() {
		tempTile = tile;
		tile = null;
	}

	public void removeTempTile() {
		tempTile = null;
	}

	@Override
	public void draw() {
		TextureDrawer.draw(Textures.tile, this);
		if (tempTile != null)
			tempTile.draw();
		if (tile != null)
			tile.draw();
	}

	public CellState getState() {
		if (tile == null)
			state.setTileState(null);
		else
			state.setTileState(tile.getState());
		return state;
	}

	public void loadState(CellState state) {
		if (state.getTileState() == null) {
			setTile(null);
			return;
		}
		
		tile = new Tile();
		tile.loadState(state.getTileState());
		tile.fitToCell(this);
		tile.updateView();
		tile.runPopEffect();
	}
}
