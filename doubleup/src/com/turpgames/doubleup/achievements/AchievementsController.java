package com.turpgames.doubleup.achievements;

import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.impl.TouchSlidingViewSwitcher;
import com.turpgames.framework.v0.util.Game;

public class AchievementsController implements IView {
	private final MaxNumberAchievements4x4 maxNumberAchievements4x4;
	private final ScoreAchievements4x4 scoreAchievements4x4;
	private final MaxNumberAchievements5x5 maxNumberAchievements5x5;
	private final ScoreAchievements5x5 scoreAchievements5x5;

	private TouchSlidingViewSwitcher viewSwitcher;

	public AchievementsController() {
		maxNumberAchievements4x4 = new MaxNumberAchievements4x4();
		scoreAchievements4x4 = new ScoreAchievements4x4();
		maxNumberAchievements5x5 = new MaxNumberAchievements5x5();
		scoreAchievements5x5 = new ScoreAchievements5x5();

		viewSwitcher = new TouchSlidingViewSwitcher(false);
		viewSwitcher.addView(maxNumberAchievements4x4);
		viewSwitcher.addView(scoreAchievements4x4);
		viewSwitcher.addView(maxNumberAchievements5x5);
		viewSwitcher.addView(scoreAchievements5x5);
		viewSwitcher.setArea(0, 0, Game.getVirtualWidth(), Game.getVirtualHeight() - 200f);
	}

	@Override
	public void draw() {
		viewSwitcher.draw();
	}

	@Override
	public String getId() {
		return "AchievementsController";
	}

	@Override
	public void activate() {
		viewSwitcher.activate();
		maxNumberAchievements4x4.activate();
		scoreAchievements4x4.activate();
		maxNumberAchievements5x5.activate();
		scoreAchievements5x5.activate();
	}

	@Override
	public boolean deactivate() {
		viewSwitcher.deactivate();
		maxNumberAchievements4x4.deactivate();
		scoreAchievements4x4.deactivate();
		maxNumberAchievements5x5.deactivate();
		scoreAchievements5x5.deactivate();
		return true;
	}
}
