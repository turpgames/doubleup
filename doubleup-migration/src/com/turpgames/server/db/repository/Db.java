package com.turpgames.server.db.repository;

import java.sql.Types;
import java.util.Date;

import com.turpgames.entity.Analytics;
import com.turpgames.entity.Game;
import com.turpgames.entity.Player;
import com.turpgames.entity.Score;

public class Db {
	private Db() {

	}

	public final static Repository<Player> players = new Repository<Player>(Player.class, "players", "id");
	public final static Repository<Score> scores = new Repository<Score>(Score.class, "scores", null);
	public final static Repository<Game> games = new Repository<Game>(Game.class, "games", "id");
	public final static Repository<Analytics> analytics = new Repository<Analytics>(Analytics.class, "analytics", null);
	
	public static int getSqlType(Class<?> type) {
		if (Integer.TYPE.equals(type) || Boolean.TYPE.equals(type))
			return Types.INTEGER;
		if (Long.TYPE.equals(type))
			return Types.BIGINT;
		if (String.class.equals(type))
			return Types.VARCHAR;
		if (Date.class.equals(type))
			return Types.TIMESTAMP;
		throw new UnsupportedOperationException("Unknown type: " + type);
	}
}
