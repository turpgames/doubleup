package com.turpgames.doubleup.leadersboard;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.doubleup.entity.LeadersBoard;
import com.turpgames.doubleup.entity.Player;
import com.turpgames.doubleup.entity.Score;
import com.turpgames.doubleup.utils.DoubleUpToast;
import com.turpgames.doubleup.utils.ScoreManager.ILeadersBoardCallback;
import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.impl.Text;

public abstract class LeadersBoardView implements IView {
	private final Text title;
	private volatile List<LeadersBoardRow> rows = new ArrayList<LeadersBoardRow>();

	protected LeadersBoardView() {
		title = new Text();
		title.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		title.setPadY(150f);
		title.setText(getId());
	}

	@Override
	public void draw() {
		title.draw();
		for (LeadersBoardRow row : rows)
			row.draw();
	}

	@Override
	public void activate() {

	}

	@Override
	public boolean deactivate() {
		return true;
	}
	
	public void loadScores() {
		loadLeadersBoard(new ILeadersBoardCallback() {
			@Override
			public void onSuccess(LeadersBoard leadersBoard) {
				bindData(leadersBoard);
			}

			@Override
			public void onFail(Throwable t) {
				DoubleUpToast.showError("Loading Scores Failed!");
			}
		});
	}

	private void bindData(LeadersBoard leadersBoard) {
		List<LeadersBoardRow> tmpRows = new ArrayList<LeadersBoardRow>();
		int i = 1;
		for (Score score : leadersBoard.getScores()) {
			Player player = leadersBoard.getPlayer(score.getPlayerId());
			tmpRows.add(new LeadersBoardRow(i++, score, player));
		}
		rows = tmpRows;
	}

	protected abstract void loadLeadersBoard(ILeadersBoardCallback callback);
}
