package com.turpgames.doubleup.components;

import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.component.ToggleButton;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.util.Color;

public class AudioButton extends ToggleButton {

	public AudioButton() {
		this(R.sizes.menuButtonSizeToScreen, R.sizes.menuButtonSizeToScreen,
				R.settings.sound, R.game.textures.toolbar.soundOn,
				R.game.textures.toolbar.soundOff, R.colors.buttonDefault,
				R.colors.buttonDefault);

	}

	private AudioButton(float width, float height, String settingsKey,
			String onTextureId, String offTextureId, Color onColor,
			Color offColor) {
		super(width, height, settingsKey, onTextureId, offTextureId, onColor,
				offColor);
	}

	@Override
	protected boolean onTap() {
		super.onTap();
		Settings.putBoolean("music", isOn);
		
		return true;
	}

}
