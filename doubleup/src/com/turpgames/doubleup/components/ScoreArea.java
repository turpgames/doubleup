package com.turpgames.doubleup.components;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.Text;

public class ScoreArea extends GameObject {
	private final Text text;
	private final String key;

	public ScoreArea(String key) {
		setWidth(150);
		setHeight(75);

		this.key = key;

		text = new Text();
		text.setFontScale(0.75f);
		text.setAlignment(Text.HAlignCenter, Text.VAlignCenter);
		text.setPadX(10f);
		text.setSize(getWidth(), getHeight());
		
		setScore(0);
	}

	public void setLocation(float x, float y) {
		getLocation().set(x, y);
		text.setLocation(x, y);
	}

	public void setScore(long score) {
		this.text.setText(key + "\n" + score);
	}

	@Override
	public void draw() {
		text.draw();
	}
}
