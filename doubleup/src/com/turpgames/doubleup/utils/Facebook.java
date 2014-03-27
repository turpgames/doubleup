package com.turpgames.doubleup.utils;

import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.social.ISocializer;
import com.turpgames.framework.v0.social.Player;
import com.turpgames.framework.v0.social.SocialFeed;
import com.turpgames.framework.v0.util.Game;

public class Facebook {
	private static ISocializer facebook;

	static {
		facebook = Game.getSocializer("facebook");
	}

	private static Player getPlayer() {
		return facebook.getPlayer();
	}

	private static boolean isLoggedIn() {
		return facebook.isLoggedIn();
	}

	public static void shareScore(final long score, final long max,
			final ICallback callback) {
		if (Facebook.isLoggedIn()) {
			doShareScore(score, max, callback);
		} else {
			Facebook.login(new ICallback() {
				@Override
				public void onSuccess() {
					doShareScore(score, max, callback);
				}

				@Override
				public void onFail(Throwable t) {
					callback.onFail(t);
				}
			});
		}
	}

	private static void login(final ICallback callback) {
		facebook.login(new ICallback() {
			@Override
			public void onSuccess() {
				callback.onSuccess();
			}

			@Override
			public void onFail(Throwable t) {
				callback.onFail(t);
			}
		});
	}

	private static void doShareScore(final long score, final long max,
			final ICallback callback) {
		try {
			SocialFeed scoreFeed = SocialFeed
					.newBuilder()
					.setTitle(prepareScoreMessage(score, max))
					.setSubtitle("turpgames")
					.setMessage("Double Up")
					.setHref("http://www.turpgames.com/doubleupredirect.html")
					.setImageUrl(
							"http://www.turpgames.com/res/img/doubleup_logo.png")
					.build();
			facebook.postFeed(scoreFeed, callback);
		} catch (Throwable t) {
			callback.onFail(t);
		}
	}

	private static String prepareScoreMessage(long score, long max) {
		if (max == 0)
			return String.format("%s just made %d points in Double Up!",
					getPlayer().getName().split(" ")[0], score);
		return String.format(
				"%s just reached a new max number %d with %d points in Double Up!",
				getPlayer().getName().split(" ")[0], max, score);
	}
}
