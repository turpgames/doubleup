package com.turpgames.doubleup.view;

import com.turpgames.doubleup.objects.display.DoubleUpToolbar;
import com.turpgames.framework.v0.component.Toolbar;
import com.turpgames.framework.v0.impl.BaseGame;
import com.turpgames.framework.v0.impl.FormScreen;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;

public class DoubleUpGame extends BaseGame {
	public static Toolbar toolbar;

	public static Toolbar getToolbar() {
		if (toolbar == null) {
			toolbar = DoubleUpToolbar.getInstance();
			toolbar.setListener(new Toolbar.IToolbarListener() {
				@Override
				public void onToolbarBack() {
					Screen screen = ScreenManager.instance.getCurrentScreen();
					if (screen instanceof DoubleUpScreen)
						((DoubleUpScreen) screen).back();
					else if (screen instanceof FormScreen)
						((FormScreen) screen).back();
				}
			});
		}
		return toolbar;
	}
}
