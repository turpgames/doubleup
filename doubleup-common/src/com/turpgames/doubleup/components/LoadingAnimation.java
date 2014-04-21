package com.turpgames.doubleup.components;

import com.turpgames.framework.v0.impl.AnimatedGameObject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;

public class LoadingAnimation extends AnimatedGameObject {
	private final Text message;

	private LoadingAnimation() {
		addAnimation("loading");
		setWidth(75);
		setHeight(75);
		getLocation().x = (Game.getVirtualWidth() - getWidth()) / 2;
		getLocation().y = (Game.getVirtualHeight() - getHeight()) / 2;
		startAnimation("loading");

		message = new Text();
		message.setFontScale(0.75f);
		message.setAlignment(Text.HAlignCenter, Text.VAlignBottom);
		message.setY(getLocation().y - 30f);
	}

	public void setMessage(String message) {
		this.message.setText(message);
	}

	public final static LoadingAnimation instance = new LoadingAnimation();
	
	@Override
	public void draw() {
		message.draw();
		super.draw();
	}
}
