package com.turpgames.doubleup.objects;

import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;

public class TurpLogo extends GameObject {
	private static float logoSize;

	private ITexture logo;

	public TurpLogo() {
		logo = Game.getResourceManager().getTexture(R.textures.turpLogo);

		setLogoSize(500f);
		getLocation().set(25f, 50f + (Game.getVirtualHeight() - logoSize) / 2f); 
	}

	@Override
	public void draw() {
		TextureDrawer.draw(logo, this);
	}

	public void setLogoSize(float f) {
		logoSize = f;
		
		setWidth(logoSize);
		setHeight(logoSize);
	}
}
