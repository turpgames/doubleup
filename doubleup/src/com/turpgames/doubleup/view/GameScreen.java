package com.turpgames.doubleup.view;

import com.badlogic.gdx.Input.Keys;
import com.turpgames.doubleup.objects.Background;
import com.turpgames.doubleup.objects.MoveDirection;
import com.turpgames.doubleup.objects.Table;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.util.Game;

public class GameScreen extends Screen {
	private Table table;

	@Override
	public void init() {
		super.init();
		table = new Table();
		registerDrawable(table, Game.LAYER_SCREEN);
		registerDrawable(new Background(), Game.LAYER_BACKGROUND);
	}
	
	@Override
	protected boolean onBeforeActivate() {
		return super.onBeforeActivate();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.DOWN) {
			table.move(MoveDirection.Down);
		}
		if (keycode == Keys.UP) {
			table.move(MoveDirection.Up);
		}
		if (keycode == Keys.LEFT) {
			table.move(MoveDirection.Left);
		}
		if (keycode == Keys.RIGHT) {
			table.move(MoveDirection.Right);
		}
		return super.keyDown(keycode);
	}

	@Override
	public boolean fling(float vx, float vy, int button) {
		if (Math.abs(vx) > Math.abs(vy)) {
			if (vx > 0)
				table.move(MoveDirection.Right);
			else
				table.move(MoveDirection.Left);
		}
		if (Math.abs(vy) > Math.abs(vx)) {
			if (vy > 0)
				table.move(MoveDirection.Down);
			else
				table.move(MoveDirection.Up);
		}
		return super.fling(vx, vy, button);
	}
}
