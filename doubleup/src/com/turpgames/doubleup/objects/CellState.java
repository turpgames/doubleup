package com.turpgames.doubleup.objects;

import java.io.Serializable;

public class CellState implements Serializable {
	private static final long serialVersionUID = 4448045836887800505L;

	private TileState tileState;

	public TileState getTileState() {
		return tileState;
	}

	public void setTileState(TileState tileState) {
		this.tileState = tileState;
	}
}