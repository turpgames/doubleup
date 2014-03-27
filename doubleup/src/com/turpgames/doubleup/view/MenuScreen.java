package com.turpgames.doubleup.view;

import com.turpgames.doubleup.objects.Background;
import com.turpgames.framework.v0.impl.FormScreen;
import com.turpgames.framework.v0.util.Game;

public class MenuScreen extends FormScreen {
	@Override
	public void init() {
		super.init();
		registerDrawable(new Background(), Game.LAYER_BACKGROUND);
		setForm("mainMenu", false);
	}
}