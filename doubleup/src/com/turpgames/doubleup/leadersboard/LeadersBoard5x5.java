package com.turpgames.doubleup.leadersboard;

import com.turpgames.doubleup.entity.Score;
import com.turpgames.doubleup.utils.ScoreManager;
import com.turpgames.doubleup.utils.ScoreManager.ILeadersBoardCallback;

public class LeadersBoard5x5 extends LeadersBoardView {
	@Override
	public String getId() {
		return "5x5";
	}

	@Override
	protected void loadLeadersBoard(ILeadersBoardCallback callback) {
		ScoreManager.instance.getLeadersBoard(Score.Mode5x5, Score.AllTime, Score.General, callback);
	}
}
