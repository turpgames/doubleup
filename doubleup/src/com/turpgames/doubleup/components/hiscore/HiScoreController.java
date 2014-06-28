package com.turpgames.doubleup.components.hiscore;

import com.turpgames.doubleup.components.DoubleUpLogo;
import com.turpgames.doubleup.utils.DoubleUpMode;
import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.client.IServiceCallback;
import com.turpgames.framework.v0.impl.TouchSlidingViewSwitcher;
import com.turpgames.framework.v0.util.Debug;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.service.message.GetHiScoresRequest;
import com.turpgames.service.message.GetHiScoresResponse;

public class HiScoreController implements IView {
	private final HiScoreInfo[] hiScoreInfos = new HiScoreInfo[] {
			new HiScoreInfo(GetHiScoresRequest.Today, DoubleUpMode.Mode4x4, "Today"),
			new HiScoreInfo(GetHiScoresRequest.LastWeek, DoubleUpMode.Mode4x4, "Last Week"),
			new HiScoreInfo(GetHiScoresRequest.LastMonth, DoubleUpMode.Mode4x4, "Last Month"),
			new HiScoreInfo(GetHiScoresRequest.AllTime, DoubleUpMode.Mode4x4, "All Time"),
			new HiScoreInfo(GetHiScoresRequest.Today, DoubleUpMode.Mode5x5, "Today"),
			new HiScoreInfo(GetHiScoresRequest.LastWeek, DoubleUpMode.Mode5x5, "Last Week"),
			new HiScoreInfo(GetHiScoresRequest.LastMonth, DoubleUpMode.Mode5x5, "Last Month"),
			new HiScoreInfo(GetHiScoresRequest.AllTime, DoubleUpMode.Mode5x5, "All Time")
	};

	private final DoubleUpLogo logo;
	private final TouchSlidingViewSwitcher viewSwitcher;

	public HiScoreController() {
		viewSwitcher = new TouchSlidingViewSwitcher(false);

		for (HiScoreInfo info : hiScoreInfos)
			viewSwitcher.addView(info.view);

		viewSwitcher.setArea(0, 0, Game.getVirtualWidth(), Game.getVirtualHeight() - 200f);
		
		logo = new DoubleUpLogo();
	}

	@Override
	public void draw() {
		logo.draw();
		viewSwitcher.draw();
	}

	@Override
	public String getId() {
		return "LeadersBoardController";
	}

	@Override
	public void activate() {
		loadScores();
		viewSwitcher.activate();
	}

	@Override
	public boolean deactivate() {
		viewSwitcher.deactivate();
		return true;
	}

	private void loadScores() {
		for (final HiScoreInfo info : hiScoreInfos) {
			HiScoreManager.getHiScores(info.days, info.mode, new IServiceCallback<GetHiScoresResponse>() {
				@Override
				public void onSuccess(GetHiScoresResponse response) {
					info.view.bindData(response);
				}
				
				@Override
				public void onFail(Throwable t) {
					Debug.println("getHiscores failed");
				}
			});
		}
	}

	private static class HiScoreInfo {
		final int days;
		final int mode;
		final HiScoreView view;

		public HiScoreInfo(int days, int mode, String title) {
			this.days = days;
			this.mode = mode;
			this.view = new HiScoreView(mode == DoubleUpMode.Mode4x4 ? "4x4" : "5x5", title);
		}
	}
}
