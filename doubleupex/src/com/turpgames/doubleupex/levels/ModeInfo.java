package com.turpgames.doubleupex.levels;

import java.io.Serializable;

public class ModeInfo implements Serializable {
	private static final long serialVersionUID = -6372508609260150653L;

	private String modeName;
	private LevelInfo[] levels;

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	public LevelInfo[] getLevels() {
		return levels;
	}

	public void setLevels(LevelInfo[] levels) {
		this.levels = levels;
	}
}
