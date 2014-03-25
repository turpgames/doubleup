package com.turpgames.doubleup.objects;

import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;

public class ResetButton extends ImageButton {
	public ResetButton(final Table table) {
		setTexture("reset");
		setTouchedColor(Color.fromHex("#71c6a560"));
		setWidth(Game.scale(32f));
		setHeight(Game.scale(32f));
		getLocation().set(Game.scale(10f), Game.getScreenHeight() - Game.scale(42f));
		
		final Dialog confirmDialog = new Dialog();
		confirmDialog.addButton("ok", "Ok");
		confirmDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogClosed() {
				
			}

			@Override
			public void onDialogButtonClicked(String id) {
				if ("ok".equals(id))
					table.reset();
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
