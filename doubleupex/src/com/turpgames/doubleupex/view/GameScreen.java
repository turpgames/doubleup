package com.turpgames.doubleupex.view;

import com.turpgames.doubleupex.objects.Background;
import com.turpgames.doubleupex.objects.DoubleUpLogo;
import com.turpgames.doubleupex.objects.display.DoubleUpToolbar;
import com.turpgames.doubleupex.objects2.GridController;
import com.turpgames.doubleupex.utils.DoubleUpAds;
import com.turpgames.doubleupex.utils.R;
import com.turpgames.framework.v0.component.Toolbar;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public class GameScreen extends Screen {
	private GridController mode;

	@Override
	public void init() {
		super.init();

		mode = new GridController(this);

		registerDrawable(new DoubleUpLogo(), Game.LAYER_SCREEN);
		registerDrawable(new Background(), Game.LAYER_BACKGROUND);

		registerDrawable(DoubleUpToolbar.getInstance(), Game.LAYER_INFO);
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

		mode.init();
		mode.activate();

		return super.onBeforeActivate();
	}

	@Override
	protected void onAfterActivate() {
		DoubleUpToolbar.getInstance().enable();
		mode.activate();
		super.onAfterActivate();
	}

	@Override
	protected boolean onBeforeDeactivate() {
		DoubleUpToolbar.getInstance().disable();
		mode.deactivate();
		return super.onBeforeDeactivate();
	}

	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
		return true;
	}
}