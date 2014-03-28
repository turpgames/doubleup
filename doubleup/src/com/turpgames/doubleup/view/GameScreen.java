package com.turpgames.doubleup.view;

import com.badlogic.gdx.Input.Keys;
import com.turpgames.doubleup.objects.Background;
import com.turpgames.doubleup.objects.DoubleUpLogo;
import com.turpgames.doubleup.objects.GlobalContext;
import com.turpgames.doubleup.objects.MoveDirection;
import com.turpgames.doubleup.objects.Table;
import com.turpgames.doubleup.objects.display.DoubleUpToolbar;
import com.turpgames.doubleup.utils.DoubleUpAds;
import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.component.Toolbar;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public abstract class GameScreen extends Screen {
	private Table table;
	private boolean tableRequiresInit = true;

	@Override
	public void init() {
		super.init();
		GlobalContext.matrixSize = getMatrixSize();
		table = new Table(getMatrixSize());
		registerDrawable(table, Game.LAYER_SCREEN);
		registerDrawable(new DoubleUpLogo(), Game.LAYER_SCREEN);
		registerDrawable(new Background(), Game.LAYER_BACKGROUND);

		registerDrawable(DoubleUpToolbar.getInstance(), Game.LAYER_INFO);
	}

	protected abstract int getMatrixSize();

	@Override
	protected boolean onBeforeActivate() {
		DoubleUpToolbar.getInstance().setListener(new Toolbar.IToolbarListener() {
			@Override
			public void onToolbarBack() {
				onBack();
			}
		});

		if (tableRequiresInit) {
			table.init();
		} else {
			GlobalContext.reset(table);
			tableRequiresInit = true;
		}

		return super.onBeforeActivate();
	}

	@Override
	protected void onAfterActivate() {
		//DoubleUpAds.showAd();
		table.activate();
		DoubleUpToolbar.getInstance().enable();
		super.onAfterActivate();
	}

	@Override
	protected boolean onBeforeDeactivate() {
		table.deactivate();
		DoubleUpToolbar.getInstance().disable();
		return super.onBeforeDeactivate();
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

	protected boolean onBack() {
		tableRequiresInit = false;
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
		return true;
	}
}
