package com.turpgames.doubleup.servlet.handlers2;

import java.io.IOException;
import java.util.List;

import com.turpgames.doubleup.db2.Db;
import com.turpgames.doubleup.db2.LeadersBoardCache;
import com.turpgames.doubleup.entity2.LeadersBoard;
import com.turpgames.doubleup.entity2.LeadersBoardRequest;
import com.turpgames.doubleup.entity2.Player;
import com.turpgames.doubleup.entity2.Score;
import com.turpgames.servlet.IServletActionHandler;
import com.turpgames.servlet.RequestContext;
import com.turpgames.utils.Util;

public class GetLeadersBoardActionHandler implements IServletActionHandler {
	@Override
	public void handle(RequestContext context) throws IOException {
		LeadersBoardRequest request = context.getRequestContentAs(LeadersBoardRequest.class);

		LeadersBoard leadersBoard;
		if (request.getWhose() == Score.FriendsScores)
			leadersBoard = getFriendsHiScores(request);
		else if (request.getWhose() == Score.General)
			leadersBoard = getGeneralLeadersBoard(request);
		else if (request.getWhose() == Score.MyScores)
			leadersBoard = getMyHiScores(request);
		else
			leadersBoard = new LeadersBoard();

		context.writeToResponse(leadersBoard);
	}

	private static LeadersBoard getMyHiScores(LeadersBoardRequest request) {
		return LeadersBoardCache.getLeadersBoard(request.getMode(), request.getDays(), request.getPlayerId());
	}

	private static LeadersBoard getGeneralLeadersBoard(LeadersBoardRequest request) {
		LeadersBoard leadersBoard = LeadersBoardCache.getLeadersBoard(request.getMode(), request.getDays(), -1);

		if (request.getPlayerId() > 0) {
			Score ownScore = Db.LeadersBoards.getPlayersHiScore(request.getMode(), request.getDays(), request.getPlayerId());
			if (ownScore != null) {
				int rank = Db.LeadersBoards.getRank(request.getMode(), request.getDays(), ownScore.getScore());
				if (rank > 10) {
					leadersBoard.setOwnRank(rank);
					leadersBoard.setOwnScore(ownScore);
				}
			}
		}

		return leadersBoard;
	}

	private static LeadersBoard getFriendsHiScores(LeadersBoardRequest request) throws IOException {
		Player self = Db.Players.get(request.getPlayerId());
		if (self == null)
			return new LeadersBoard();

		List<String> tmp = Util.Misc.toList(request.getFriendsFacebookIds());
		tmp.add(self.getFacebookId());
		request.setFriendsFacebookIds(tmp.toArray(new String[0]));

		LeadersBoard leadersBoard = Db.LeadersBoards.getFriendsLeadersBoard(request.getMode(), request.getDays(), request.getPlayerId(), 10, request.getFriendsFacebookIds());

		if (request.getPlayerId() > 0) {
			Score ownScore = Db.LeadersBoards.getPlayersHiScore(request.getMode(), request.getDays(), request.getPlayerId());
			if (ownScore != null) {
				int rank = Db.LeadersBoards.getRank(request.getMode(), request.getDays(), ownScore.getScore(), request.getFriendsFacebookIds());
				if (rank > 10) {
					leadersBoard.setOwnRank(rank);
					leadersBoard.setOwnScore(ownScore);
				}
			}
		}

		return leadersBoard;
	}
}