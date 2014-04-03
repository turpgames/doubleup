package com.turpgames.doubleupex.view;

import com.badlogic.gdx.Input.Keys;
import com.turpgames.doubleupex.levels.Level1;
import com.turpgames.doubleupex.objects.Background;
import com.turpgames.doubleupex.objects.DoubleUpLogo;
import com.turpgames.doubleupex.objects.MoveDirection;
import com.turpgames.doubleupex.objects.Table;
import com.turpgames.doubleupex.objects.display.DoubleUpToolbar;
import com.turpgames.doubleupex.utils.DoubleUpAds;
import com.turpgames.doubleupex.utils.R;
import com.turpgames.framework.v0.component.Toolbar;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public class GameScreen_ extends Screen {
	private Table table;

	@Override
	public void init() {
		super.init();

		table = new Level1();

		registerDrawable(new DoubleUpLogo(), Game.LAYER_SCREEN);
		registerDrawable(new Background(), Game.LAYER_BACKGROUND);

		registerDrawable(DoubleUpToolbar.getInstance(), Game.LAYER_INFO);
	}

	protected Table getLevel() {
		return new Level1();
	}

	@Override
	protected boolean onBeforeActivate() {
		DoubleUpAds.showAd();
		DoubleUpToolbar.getInstance().setListener(
				new Toolbar.IToolbarListener() {
					@Override
					public void onToolbarBack() {
						onBack();
					}
				});

		table.init();

		return super.onBeforeActivate();
	}

	@Override
	protected void onAfterActivate() {
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
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
		return true;
	}
}
