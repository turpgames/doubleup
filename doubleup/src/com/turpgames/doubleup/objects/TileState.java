package com.turpgames.doubleup.objects;

import java.io.Serializable;

public class TileState implements Serializable {
	private static final long serialVersionUID = -5974658213583064924L;
	
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}