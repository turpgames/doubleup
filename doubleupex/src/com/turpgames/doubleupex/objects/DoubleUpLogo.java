package com.turpgames.doubleupex.objects;

import com.turpgames.doubleupex.utils.Textures;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;

public class DoubleUpLogo extends GameObject {
	private GameObject icon;
	private GameObject logo;
	
	public DoubleUpLogo() {
		logo = new GameObject() {
			@Override
			public void draw() {
				TextureDrawer.draw(Textures.logo, this);
			}
		};
		
		float w = 700f;
		float h = 156f;
		float scale = (Game.getVirtualWidth() * 0.50f) / w;
		
		logo.setWidth(scale * w);
		logo.setHeight(scale * h);
		
		icon = new GameObject() {
			@Override
			public void draw() {
				TextureDrawer.draw(Textures.icon, this);
			}
		};

		w = 338f;
		h = 338f;
		scale *= 0.66f;
		
		icon.setWidth(scale * w);
		icon.setHeight(scale * h);
		
		logo.getLocation().set(
				(Game.getVirtualWidth() - logo.getWidth() - icon.getWidth()) / 2,
				Game.getVirtualHeight() - logo.getHeight() - 45f
				);
		
		icon.getLocation().set(
				logo.getLocation().x + logo.getWidth(),
				logo.getLocation().y - (icon.getHeight() - logo.getHeight()) / 2 
				);
	}

	@Override
	public void draw() {
		logo.draw();
		icon.draw();
	}

}
