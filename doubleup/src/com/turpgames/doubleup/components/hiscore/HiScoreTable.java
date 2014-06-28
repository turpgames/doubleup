package com.turpgames.doubleup.components.hiscore;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.entity.Player;
import com.turpgames.entity.Score;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.service.message.GetHiScoresResponse;

class HiScoreTable implements IDrawable {
	private volatile List<HiScoreRow> rows;

	@Override
	public void draw() {
		if (rows == null)
			return;
		for (HiScoreRow row : rows)
			row.draw();
	}

	public void bindData(GetHiScoresResponse data) {
		Score[] scores = data.getScores();
		Player[] players = data.getPlayers();

		List<HiScoreRow> tmpRows = new ArrayList<HiScoreRow>();

		int rank = 1;
		for (Score score : scores) {
			tmpRows.add(new HiScoreRow(rank++, score, findPlayer(score, players)));
		}

		if (data.getOwnHiScore() != null) {
			tmpRows.add(new HiScoreRow(data.getOwnRank(), data.getOwnHiScore(), TurpClient.getPlayer()));
		}

		rows = tmpRows;
	}

	private static Player findPlayer(Score score, Player[] players) {
		for (Player player : players) {
			if (player.getId() == score.getPlayerId()) {
				return player;
			}
		}
		return null;
	}
}
