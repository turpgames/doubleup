package com.turpgames.doubleup.components;

import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.ToggleButton;
import com.turpgames.framework.v0.impl.GameObject;

public class DoubleUpToolbar extends GameObject {

	protected ImageButton backButton;
	protected ToggleButton soundButton;
	
	private IToolbarListener listener;
	
	private static DoubleUpToolbar instance;

	public static DoubleUpToolbar getInstance() {
		if (instance == null)
			instance = new DoubleUpToolbar();
		return instance;
	}

	private DoubleUpToolbar() {
		addBackButton();
		addSoundButton();
		listenInput(true);
	}

	public void setListener(IToolbarListener listener) {
		this.listener = listener;
	}

	public void activateBackButton() {
		backButton.activate();
	}

	public void deactivateBackButton() {
		backButton.deactivate();
	}
	
	public void disable() {
		soundButton.deactivate();
		backButton.deactivate();
	}

	public void enable() {
		soundButton.activate();
		backButton.activate();
	}
	
	public ImageButton getBackButton() {
		return backButton;
	}

	protected void addBackButton() {
		backButton = new ImageButton(R.sizes.menuButtonSizeToScreen, R.sizes.menuButtonSizeToScreen, R.game.textures.toolbar.back, R.colors.buttonDefault, R.colors.buttonDefault);
		backButton.setLocation(Button.AlignNW, R.sizes.toolbarMargin, R.sizes.toolbarMargin);
		backButton.deactivate();
		backButton.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				if (listener != null)
					listener.onToolbarBack();
			}
		});
	}

	protected void addSoundButton() {
		soundButton = new AudioButton();
		soundButton.setLocation(Button.AlignNE, R.sizes.toolbarMargin, R.sizes.toolbarMargin);
		soundButton.deactivate();
	}
	
	@Override
	public void draw() {
		soundButton.draw();
		backButton.draw();
	}

	@Override
	public boolean ignoreViewport() {
		return true;
	}
}
