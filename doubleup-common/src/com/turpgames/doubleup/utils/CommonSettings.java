package com.turpgames.doubleup.utils;

import java.util.ArrayList;

import com.turpgames.doubleup.entity.Score;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.utils.Util;

public class CommonSettings {
	public static String getPlayerId() {
		return Settings.getString("player-id", "");
	}
	
	public static void setPlayerId(String playerId) {
		Settings.putString("player-id", playerId);
	}
	
	public static String getPlayerFacebookId() {
		return Settings.getString("player-facebook-id", "");
	}
	
	public static void setPlayerFacebookId(String playerId) {
		Settings.putString("player-facebook-id", playerId);
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Score> getScoresToSend() {
		ArrayList<Score> scores = new ArrayList<Score>();
		try {
			String encoded = Settings.getString(R.settings.scoresToSend, "");
			if (!Util.Strings.isNullOrWhitespace(encoded)) {
				byte[] serialized = Util.Strings.fromBase64String(encoded);
				return (ArrayList<Score>) Util.IO.deserialize(serialized);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return scores;
	}

	public static void setScoresToSend(ArrayList<Score> scoresToSend) {
		byte[] serialized = Util.IO.serialize(scoresToSend);
		String encoded = Util.Strings.toBase64String(serialized);
		Settings.putString(R.settings.scoresToSend, encoded);
	}
}
