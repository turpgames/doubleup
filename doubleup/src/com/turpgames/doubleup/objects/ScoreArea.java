package com.turpgames.doubleup.objects;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.Text;

public class ScoreArea extends GameObject {
	private final Text text;
	private final String key;

	public ScoreArea(String key) {
		setWidth(200);
		setHeight(50);

		this.key = key;

		text = new Text();
		text.setFontScale(0.75f);
		text.setAlignment(Text.HAlignLeft, Text.VAlignCenter);
		text.setPadX(10f);
		text.setSize(getWidth(), getHeight());
		
		setScore(0);
	}

	public void setLocation(float x, float y) {
		getLocation().set(x, y);
		text.setLocation(x, y);
	}

	public void setScore(long score) {
		this.text.setText(key + ": " + score);
	}

	@Override
	public void draw() {
		text.draw();
	}
}
