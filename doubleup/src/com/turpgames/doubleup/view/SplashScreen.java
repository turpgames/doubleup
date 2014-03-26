package com.turpgames.doubleup.view;

import com.turpgames.doubleup.objects.TurpLogo;
import com.turpgames.doubleup.updates.DoubleUpUpdateManager;
import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.IResourceManager;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;

public class SplashScreen extends Screen {

	private IResourceManager resourceManager;
	private Color progressColor;

	@Override
	public void draw() {
		super.draw();

		float width = 500 * resourceManager.getProgress();
		float height = 20;
		float x = (Game.getVirtualWidth() - width) / 2;

		ShapeDrawer.drawRect(x, 100, width, height, progressColor, true, false);
	}

	@Override
	public void init() {
		super.init();
		registerDrawable(new TurpLogo(), Game.LAYER_BACKGROUND);
		progressColor = new Color(R.colors.turpYellow);
		resourceManager = Game.getResourceManager();
	}

	@Override
	public void update() {
		if (!resourceManager.isLoading()) {
			DoubleUpUpdateManager.runUpdates();

			switchToGame();
		}
	}

	private void switchToGame() {
		ScreenManager.instance.switchTo(R.screens.menu, false);
	}
}