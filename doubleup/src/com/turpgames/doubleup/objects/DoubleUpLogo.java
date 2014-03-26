package com.turpgames.doubleup.objects;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;

public class DoubleUpLogo extends GameObject {

	public DoubleUpLogo() {
		final float w = 2182f;
		final float h = 415f;
		float scale = (Game.getVirtualWidth() * 0.66f) / w;

		setWidth(scale * w);
		setHeight(scale * h);
		getLocation().set(
				Game.getVirtualWidth() / 6f,
				Game.getVirtualHeight() - getHeight() - 25f
				);
	}

	@Override
	public void draw() {
		TextureDrawer.draw(Textures.doubleup, this);
	}

}
