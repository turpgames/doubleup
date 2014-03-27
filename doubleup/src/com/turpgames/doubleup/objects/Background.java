package com.turpgames.doubleup.objects;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.IDrawingInfo;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.framework.v0.util.TextureDrawer;

public class Background implements IDrawable {
	private ITexture bg;

	public Background() {
		bg = Game.getResourceManager().getTexture("bg");
	}

	@Override
	public void draw() {
//		TextureDrawer.draw(bg, IDrawingInfo.screen);
		ShapeDrawer.drawRect(0, 0, IDrawingInfo.screen.getWidth(), IDrawingInfo.screen.getHeight(), Color.fromHex("#0080FF40"), true, IDrawingInfo.screen.ignoreViewport());
	}
}

