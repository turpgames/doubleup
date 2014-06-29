package com.turpgames.doubleup.components;

import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.component.ToggleButton;
import com.turpgames.framework.v0.impl.Settings;

public class AudioButton extends ToggleButton {
	public AudioButton() {
		super(R.sizes.menuButtonSizeToScreen,
				R.sizes.menuButtonSizeToScreen,
				Settings.sound,
				R.game.textures.toolbar.soundOn,
				R.game.textures.toolbar.soundOff,
				R.colors.buttonDefault,
				R.colors.buttonDefault);

	}
}
