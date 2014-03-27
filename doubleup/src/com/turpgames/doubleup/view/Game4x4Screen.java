package com.turpgames.doubleup.view;

import com.turpgames.doubleup.objects.Table;

public class Game4x4Screen extends GameScreen {
	@Override
	protected boolean onBeforeActivate() {
		Table.matrixSize = 4;
		return super.onBeforeActivate();
	}
}
