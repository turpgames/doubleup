package com.turpgames.doubleup.view;

import com.turpgames.doubleup.objects.Background;
import com.turpgames.doubleup.objects.DoubleUpLogo;
import com.turpgames.framework.v0.impl.FormScreen;
import com.turpgames.framework.v0.util.Game;

public class MenuScreen extends FormScreen {
	@Override
	public void init() {
		super.init();
		registerDrawable(new Background(), Game.LAYER_BACKGROUND);
		registerDrawable(new DoubleUpLogo(), Game.LAYER_SCREEN);
		setForm("mainMenu", false);
	}
	
	protected boolean onBack() {
		Game.toHomeScreen();
		return true;
	}
}