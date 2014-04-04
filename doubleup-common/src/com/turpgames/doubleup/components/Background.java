package com.turpgames.doubleup.components;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.IDrawingInfo;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.ShapeDrawer;

public class Background implements IDrawable {
	private final static Color bgColor = Color.fromHex("#246dc7FF");

	@Override
	public void draw() {
		ShapeDrawer.drawRect(0, 0, IDrawingInfo.screen.getWidth(), IDrawingInfo.screen.getHeight(), bgColor, true, IDrawingInfo.screen.ignoreViewport());
	}
}
