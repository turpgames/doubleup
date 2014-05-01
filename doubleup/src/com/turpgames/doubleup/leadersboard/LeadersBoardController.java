package com.turpgames.doubleup.leadersboard;

import com.turpgames.doubleup.entity.Score;
import com.turpgames.doubleup.utils.Facebook;
import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.impl.TouchSlidingViewSwitcher;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Game;

public class LeadersBoardController implements IView {

	private TouchSlidingViewSwitcher viewSwitcher;

	public LeadersBoardController() {
		int[] modes = new int[] { Score.Mode4x4, Score.Mode5x5 };
		int[] days = new int[] { Score.Daily, Score.Weekly, Score.Monthly, Score.AllTime };

		viewSwitcher = new TouchSlidingViewSwitcher(false);

		for (int mode : modes) {
			for (int day : days) {
				viewSwitcher.addView(new LeadersBoardView(mode, day));
			}
		}

		viewSwitcher.setArea(0, 0, Game.getVirtualWidth(), Game.getVirtualHeight());
	}

	@Override
	public void draw() {
		viewSwitcher.draw();
	}

	@Override
	public String getId() {
		return "LeadersBoardController";
	}

	@Override
	public void activate() {
		if (Facebook.isLoggedIn()) {
			loadScores();
			viewSwitcher.activate();
		}
		else {
			Facebook.login(new ICallback() {
				@Override
				public void onSuccess() {
					loadScores();
					viewSwitcher.activate();
				}

				@Override
				public void onFail(Throwable t) {
					ScreenManager.instance.switchTo(R.game.screens.menu, true);
				}
			});
		}
	}

	private void loadScores() {
		for (IView view : viewSwitcher.getViews()) {
			if (view instanceof LeadersBoardView)
				((LeadersBoardView) view).loadScores();
		}
	}

	private void deactivateViews() {
		for (IView view : viewSwitcher.getViews()) {
			view.deactivate();
		}
	}

	@Override
	public boolean deactivate() {
		viewSwitcher.deactivate();
		deactivateViews();
		return true;
	}
}
