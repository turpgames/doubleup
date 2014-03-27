package com.turpgames.doubleup.objects;

import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.util.Color;

public class ResetButton extends ImageButton {
	public ResetButton(final Table table) {
		setTexture("reset");
		getColor().set(Color.fromHex("#6783c2"));;
		setTouchedColor(Color.fromHex("#40b8ea"));
		setWidth(R.sizes.menuButtonSizeToScreen);
		setHeight(R.sizes.menuButtonSizeToScreen);
		setLocation(Button.AlignSW, R.sizes.toolbarMargin, R.sizes.toolbarMargin);
		
		final Dialog confirmDialog = new Dialog();
		confirmDialog.addButton("ok", "Ok");
		confirmDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogClosed() {
				
			}

			@Override
			public void onDialogButtonClicked(String id) {
				if ("ok".equals(id))
					table.init();
			}
		});
		
		setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				confirmDialog.open("Are you sure you want to reset game?");
			}
		});
	}
}
