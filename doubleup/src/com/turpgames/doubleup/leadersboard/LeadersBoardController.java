package com.turpgames.doubleup.leadersboard;

import com.turpgames.doubleup.utils.Facebook;
import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.impl.TouchSlidingViewSwitcher;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Game;

public class LeadersBoardController implements IView {
	private final LeadersBoard4x4 leadersBoard4x4;
	private final LeadersBoard5x5 leadersBoard5x5;

	private TouchSlidingViewSwitcher viewSwitcher;

	public LeadersBoardController() {
		leadersBoard4x4 = new LeadersBoard4x4();
		leadersBoard5x5 = new LeadersBoard5x5();

		viewSwitcher = new TouchSlidingViewSwitcher(false);
		viewSwitcher.addView(leadersBoard4x4);
		viewSwitcher.addView(leadersBoard5x5);
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
			leadersBoard4x4.loadScores();
			leadersBoard5x5.loadScores();
			viewSwitcher.activate();
		}
		else {
			Facebook.login(new ICallback() {
				@Override
				public void onSuccess() {
					leadersBoard4x4.loadScores();
					leadersBoard5x5.loadScores();
					viewSwitcher.activate();
				}

				@Override
				public void onFail(Throwable t) {
					ScreenManager.instance.switchTo(R.game.screens.menu, true);
				}
			});
		}
	}

	@Override
	public boolean deactivate() {
		viewSwitcher.deactivate();
		leadersBoard4x4.deactivate();
		leadersBoard5x5.deactivate();
		return true;
	}
}
