package com.turpgames.doubleup.view;

import com.turpgames.doubleup.utils.DoubleUpClient;

public class MenuScreen extends MenuScreenBase {
	@Override
	public void init() {
		super.init();
		
		DoubleUpClient.init();
	}
}
