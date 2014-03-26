package com.turpgames.doubleup.objects.display;

import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.ToggleButton;
import com.turpgames.framework.v0.component.Toolbar;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.doubleup.objects.AudioButton;
import com.turpgames.doubleup.utils.R;

public class DoubleUpToolbar extends Toolbar {

	private static DoubleUpToolbar instance;

	public static DoubleUpToolbar getInstance() {
		if (instance == null)
			instance = new DoubleUpToolbar();
		return instance;
	}

	private DoubleUpToolbar() {
		soundButton.activate();
	}

	public void disable() {
		settingsButton.deactivate();
		backButton.deactivate();
	}

	public void enable() {
		settingsButton.activate();
		backButton.activate();
	}
	
	public GameObject getBackButton() {
		return backButton;
	}

	@Override
	protected void concreteAddBackButton() {
		backButton = new ImageButton(R.sizes.menuButtonSizeToScreen, R.sizes.menuButtonSizeToScreen, R.game.textures.toolbar.back, R.colors.buttonDefault, R.colors.buttonTouched);
		backButton.setLocation(Button.AlignNW, R.sizes.toolbarMargin, R.sizes.toolbarMargin);
	}

	@Override
	protected void concreteAddSettingsButton() {
		settingsButton = new ImageButton(R.sizes.menuButtonSizeToScreen, R.sizes.menuButtonSizeToScreen, R.game.textures.toolbar.settings, R.colors.buttonDefault, R.colors.buttonTouched);
		settingsButton.setLocation(Button.AlignNE,R.sizes. toolbarMargin, R.sizes.toolbarMargin);
	}

	@Override
	protected void concreteAddSoundButton() {
		soundButton = new AudioButton();
		soundButton.setLocation(Button.AlignNE, R.sizes.toolbarMargin, R.sizes.toolbarMargin);
	}

	@Override
	protected void concreteAddVibrationButton() {
		vibrationButton = new ToggleButton(R.sizes.menuButtonSizeToScreen, R.sizes.menuButtonSizeToScreen, R.settings.vibration, R.game.textures.toolbar.vibrationOn, 
				R.game.textures.toolbar.vibrationOff, R.colors.ichiguCyan, R.colors.ichiguWhite);
		vibrationButton.setLocation(Button.AlignNE, 2 * R.sizes.menuButtonSizeToScreen + 3 * R.sizes.menuButtonSpacing + R.sizes.toolbarMargin, R.sizes.toolbarMargin);	
	}
	
	@Override
	public void draw() {
		soundButton.draw();
	}
}
