package com.turpgames.doubleup.objects.display;

import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.LanguageMenu;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.doubleup.utils.R;

public class DoubleUpLanguageMenu extends LanguageMenu {

	public DoubleUpLanguageMenu() {
		super(R.sizes.menuButtonSpacing, R.sizes.langFlagButtonSizeToScreen, R.sizes.langFlagButtonSizeToScreen, 2);
	}

	@Override
	protected void concreteAddControlButton() {
		controlButton = new ImageButton(R.sizes.menuButtonSizeToScreen, R.sizes.menuButtonSizeToScreen, Color.white(), Color.white());
		controlButton.setLocation(Button.AlignSW, 0, 0);
	}
}
