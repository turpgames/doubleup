package com.turpgames.doubleup.view;

import com.turpgames.doubleup.components.TurpLogo;
import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.IResourceManager;
import com.turpgames.framework.v0.client.ConnectionManager;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;

public abstract class SplashScreenBase extends Screen {

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
		ConnectionManager.init();
		super.init();
		registerDrawable(new TurpLogo(), Game.LAYER_BACKGROUND);
		progressColor = new Color(R.colors.yellow);
		resourceManager = Game.getResourceManager();
	}
	
	@Override
	protected void onAfterDeactivate() {
		super.onAfterDeactivate();
		Game.getCurrent().getBgColor().set(Color.fromHex("#246dc7FF"));
	}

	@Override
	public void update() {
		if (!resourceManager.isLoading()) {
			runUpdates();
			switchToGame();
		}
	}

	private void switchToGame() {
		ScreenManager.instance.switchTo("auth", false);
	}
	
	protected abstract void runUpdates();
}