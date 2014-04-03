package com.turpgames.doubleup.state;

import java.io.Serializable;

public class TileState implements Serializable {
	private static final long serialVersionUID = -5974658213583064924L;

	private int value;
	private boolean isBrick;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isBrick() {
		return isBrick;
	}

	public void setBrick(boolean isBrick) {
		this.isBrick = isBrick;
	}
}