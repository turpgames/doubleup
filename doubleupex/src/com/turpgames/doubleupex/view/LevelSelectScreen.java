package com.turpgames.doubleupex.view;

import com.turpgames.doubleup.utils.R;
import com.turpgames.doubleupex.levels.ModeView;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public class LevelSelectScreen extends Screen {
	@Override
	public void init() {
		super.init();

		registerDrawable(new ModeView(), Game.LAYER_GAME);
	}
	
	@Override
	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
		return true;
	}
}
