package com.turpgames.doubleup.migration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.turpgames.entity.Player;
import com.turpgames.entity.Score;
import com.turpgames.server.db.DbManager;
import com.turpgames.server.db.IEntityFactory;
import com.turpgames.server.db.SqlQuery;
import com.turpgames.server.db.repository.Db;
import com.turpgames.server.db.repository.RepositoryException;

public class MigrationMain {

	public static void main(String[] args) {
		try {
			if (args.length == 2)
				migrateFromDb(args[0], args[1]);
			else
				System.out.println("invalid args length: " + args.length);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static void migrateFromDb(String sourceConnStr, String targetConnStr) throws Exception {
		System.out.println("Starting migration...");

		System.out.println("Source Conn Str " + sourceConnStr);
		System.out.println("Target Conn Str:  " + targetConnStr);

		System.out.println("Fetching players...");
		List<Player> players = selectPlayers(sourceConnStr);
		List<PlayerAdapter> playerAdapters = createPlayerAdapters(players);
		System.out.println(players.size() + " players found...");

		System.out.println("Fetching scores...");
		List<Score> scores = selectScores(sourceConnStr);
		List<ScoreAdapter> scoreAdapters = createScoreAdapters(scores);
		System.out.println(scores.size() + " scores found...");

		migrate(playerAdapters, scoreAdapters, targetConnStr);
	}

	private static void migrate(List<PlayerAdapter> playerAdapters, List<ScoreAdapter> scoreAdapters, String targetConnStr) throws SQLException, RepositoryException {
		DbManager man = null;
		try {
			System.out.println("Inserting new players...");

			man = new DbManager(true, new ConnectionProviderImpl(targetConnStr));

			List<Player> existingNewPlayers = Db.players.selectAll(man);

			int count = 0;
			for (int i = 0; i < playerAdapters.size(); i++) {
				PlayerAdapter pa = playerAdapters.get(i);

				if (playerAlreadyExists(pa.getNewPlayer(), existingNewPlayers)) {
					continue;
				}

				pa.getNewPlayer().setJoinDate(getOldestScoreTime(scoreAdapters, pa.getOldPlayer().getId()));
				Db.players.insert(pa.getNewPlayer(), man);
				count++;
			}

			System.out.println(count + " players inserted ");
			System.out.println("Inserting new scores...");

			count = 0;
			for (ScoreAdapter sa : scoreAdapters) {
				setNewPlayerId(sa, playerAdapters);

				if (sa.getNewScore().getPlayerId() == 0) {
					System.out.println("SCORE NOT INSERTED: " + sa.getOldScore().getScoreTime().getTime());
					continue;
				}
				
				if (scoreAlreadyExists(sa.getNewScore(), man)) {
					continue;
				}

				Db.scores.insert(sa.getNewScore(), man);
				count++;
			}

			System.out.println(count + " scores inserted ");

			man.close();

			System.out.println("OK!");
		} catch (Throwable t) {
			man.abort();
			t.printStackTrace();
		} finally {
			if (man != null)
				man.close();
		}
	}

	private static boolean playerAlreadyExists(Player player, List<Player> players) {
		for (Player p : players) {
			if (p.getSocialId().equals(player.getSocialId())) {
				player.setId(p.getId());
				player.setJoinDate(p.getJoinDate());
				return true;
			}
		}
		return false;
	}

	private static boolean scoreAlreadyExists(Score newScore, DbManager man) throws SQLException {
		Object o = DbManager.executeScalar(
				new SqlQuery("select count(*) from scores where game_id = ? and game_mode = ? and player_id = ? and score = ? and extra_data = ?")
						.addParameter(newScore.getGameId(), Types.BIGINT)
						.addParameter(newScore.getGameMode(), Types.INTEGER)
						.addParameter(newScore.getPlayerId(), Types.BIGINT)
						.addParameter(newScore.getScore(), Types.BIGINT)
						.addParameter(newScore.getExtraData(), Types.VARCHAR)
				, man);
		if (o == null)
			return false;
		return ((Long) o) > 0;
	}

	private static Date getOldestScoreTime(List<ScoreAdapter> scores, long playerId) {
		for (ScoreAdapter score : scores) {
			if (playerId == score.getOldScore().getPlayerId())
				return score.getOldScore().getScoreTime();
		}
		return new Date();
	}

	private static void setNewPlayerId(ScoreAdapter scoreAdapter, List<PlayerAdapter> playerAdapters) {
		for (PlayerAdapter pa : playerAdapters) {
			if (scoreAdapter.getOldScore().getPlayerId() == pa.getOldPlayer().getId()) {
				scoreAdapter.getNewScore().setPlayerId(pa.getNewPlayer().getId());
				return;
			}
		}
	}

	private static List<PlayerAdapter> createPlayerAdapters(List<Player> players) {
		List<PlayerAdapter> playerAdapters = new ArrayList<PlayerAdapter>();
		for (Player player : players)
			playerAdapters.add(new PlayerAdapter(player));
		return playerAdapters;
	}

	private static List<ScoreAdapter> createScoreAdapters(List<Score> scores) {
		List<ScoreAdapter> scoreAdapters = new ArrayList<ScoreAdapter>();
		for (Score score : scores)
			scoreAdapters.add(new ScoreAdapter(score));
		return scoreAdapters;
	}

	private static List<Player> selectPlayers(String sourceConnStr) throws Exception {
		DbManager man = null;
		try {
			man = new DbManager(new ConnectionProviderImpl(sourceConnStr));

			return DbManager.executeSelectList(
					new SqlQuery("select * from players"),
					new IEntityFactory<Player>() {
						@Override
						public Player create(ResultSet rs) throws SQLException {
							Player player = new Player();

							player.setId(rs.getLong("id"));
							player.setName(rs.getString("username"));
							player.setEmail(rs.getString("email"));
							player.setSocialId(rs.getString("facebook_id"));

							return player;
						}
					}, man);
		} finally {
			if (man != null)
				man.close();
		}
	}

	private static List<Score> selectScores(String sourceConnStr) throws Exception {
		DbManager man = null;
		try {
			man = new DbManager(new ConnectionProviderImpl(sourceConnStr));

			return DbManager.executeSelectList(
					new SqlQuery("select * from scores"),
					new IEntityFactory<Score>() {
						@Override
						public Score create(ResultSet rs) throws SQLException {
							Score score = new Score();

							score.setPlayerId(rs.getLong("player_id"));
							score.setGameMode(rs.getInt("mode"));
							score.setScore(rs.getLong("score"));
							score.setExtraData(rs.getInt("max_number") + "");
							score.setScoreTime(new Date(rs.getLong("time")));

							return score;
						}
					}, man);
		} finally {
			if (man != null)
				man.close();
		}
	}
}
