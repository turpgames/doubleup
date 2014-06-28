package com.turpgames.doubleup.components.hiscore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.turpgames.framework.v0.util.Game;
import com.turpgames.service.message.GetHiScoresRequest;
import com.turpgames.service.message.GetHiScoresResponse;
import com.turpgames.utils.Util;

public final class HiScoreCache {
	private HiScoreCache() {

	}

	private final static String settingsKeyPrefix = "HiScoresCache_";
	private final static long timeout = 20 * 60 * 1000; // 20 minutes
	private final static Map<String, HiScoreCacheItem> cache = new HashMap<String, HiScoreCacheItem>();

	public static GetHiScoresResponse getHiScores(int days, int mode, boolean ignoreTimeout) {
		return getHiScores(days, mode, GetHiScoresRequest.General, ignoreTimeout);
	}

	private static GetHiScoresResponse getHiScores(int days, int mode, int whose, boolean ignoreTimeout) {
		String key = prepareKey(mode, days, whose);
		HiScoreCacheItem cacheItem = cache.get(key);

		if (cacheItem == null) {
			cacheItem = readFromSettings(key);
			if (cacheItem == null)
				return null;
			cache.put(key, cacheItem);
		}

		Date now = new Date();
		if (!ignoreTimeout && now.getTime() - cacheItem.getLastLoadDate().getTime() > timeout)
			return null;

		return cacheItem.getHiScores();
	}

	public static void putHiScores(int days, int mode, GetHiScoresResponse hiScores) {
		putHiScores(days, mode, GetHiScoresRequest.General, hiScores);
	}

	private static void putHiScores(int days, int mode, int whose, GetHiScoresResponse hiScores) {
		String key = prepareKey(mode, days, whose);

		HiScoreCacheItem cacheItem = new HiScoreCacheItem();
		cacheItem.setHiScores(hiScores);
		cacheItem.setLastLoadDate(new Date());

		cache.put(key, cacheItem);

		writeToSettings(key, cacheItem);
	}

	private static void writeToSettings(String key, HiScoreCacheItem cacheItem) {
		String settingsKey = settingsKeyPrefix + key;
		byte[] serialized = Util.IO.serialize(cacheItem);
		String base64SettingsValue = Util.Strings.toBase64String(serialized);
		Game.getSettings().putString(settingsKey, base64SettingsValue);
	}

	private static HiScoreCacheItem readFromSettings(String key) {
		String settingsKey = settingsKeyPrefix + key;
		String base64SettingsValue = Game.getSettings().getString(settingsKey, null);
		if (base64SettingsValue == null)
			return null;

		byte[] serialized = Util.Strings.fromBase64String(base64SettingsValue);
		HiScoreCacheItem cacheItem = (HiScoreCacheItem) Util.IO.deserialize(serialized);
		return cacheItem;
	}

	private static String prepareKey(int mode, int days, int whose) {
		return mode + "" + days + "" + whose;
	}
}
