package com.turpgames.doubleup.leadersboard;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.doubleup.entity2.LeadersBoard;
import com.turpgames.doubleup.entity2.Player;
import com.turpgames.doubleup.entity2.Score;
import com.turpgames.doubleup.utils.DoubleUpToast;
import com.turpgames.doubleup.utils.ScoreManager;
import com.turpgames.doubleup.utils.ScoreManager.ILeadersBoardCallback;
import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.impl.Text;

class LeadersBoardView implements IView {
	private final int mode;
	private final int days;

	private final String id;
	private final Text title;
	private final Text subTitle;
	private volatile List<LeadersBoardRow> rows = new ArrayList<LeadersBoardRow>();

	public LeadersBoardView(int mode, int days) {
		this.mode = mode;
		this.days = days;

		title = new Text();
		title.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		title.setPadY(150f);

		subTitle = new Text();
		subTitle.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		subTitle.setPadY(200f);
		subTitle.setFontScale(0.5f);

		title.setText(mode == Score.Mode4x4 ? "4x4" : "5x5");
		if (days == Score.AllTime)
			subTitle.setText("All Time");
		if (days == Score.Monthly)
			subTitle.setText("Last Month");
		if (days == Score.Weekly)
			subTitle.setText("Last Week");
		if (days == Score.Daily)
			subTitle.setText("Today");
		
		id = title.getText() + subTitle.getText();
	}

	@Override
	public void draw() {
		title.draw();
		subTitle.draw();
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

	private void loadLeadersBoard(ILeadersBoardCallback callback) {
		ScoreManager.instance.getLeadersBoard(mode, days, Score.General, callback);
	}

	@Override
	public String getId() {
		return id;
	}
}
