package com.turpgames.doubleup.leadersboard;

import com.turpgames.doubleup.entity.Score;
import com.turpgames.doubleup.utils.ScoreManager;
import com.turpgames.doubleup.utils.ScoreManager.ILeadersBoardCallback;

public class LeadersBoard4x4 extends LeadersBoardView {
	@Override
	public String getId() {
		return "4x4";
	}

	@Override
	protected void loadLeadersBoard(ILeadersBoardCallback callback) {
		ScoreManager.instance.getLeadersBoard(Score.Mode4x4, Score.AllTime, Score.General, callback);
	}
}
