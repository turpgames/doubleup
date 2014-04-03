package com.turpgames.doubleup.components;

import com.turpgames.doubleup.controllers.GridController;
import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.util.Color;

public class ResetButton extends ImageButton {
	private final Dialog confirmDialog;

	public ResetButton(final GridController controller) {
		setTexture("reset");
		getColor().set(Color.fromHex("#fff"));
		setTouchedColor(Color.fromHex("#fff"));
		setWidth(R.sizes.menuButtonSizeToScreen);
		setHeight(R.sizes.menuButtonSizeToScreen);
		setLocation(Button.AlignSW, R.sizes.toolbarMargin, R.sizes.toolbarMargin);

		confirmDialog = new Dialog();
		confirmDialog.addButton("ok", "Ok");
		confirmDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogClosed() {

			}

			@Override
			public void onDialogButtonClicked(String id) {
				if ("ok".equals(id))
					controller.reset();
			}
		});

		setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				confirmDialog.open("Are you sure you want to reset game?");
			}
		});
	}
	
	@Override
	public void deactivate() {
		confirmDialog.close();
		super.deactivate();
	}
}
