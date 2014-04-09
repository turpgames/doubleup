package com.turpgames.doubleupex.utils;

import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.social.IFacebookConnector;
import com.turpgames.framework.v0.social.SocialFeed;
import com.turpgames.framework.v0.social.SocialUser;
import com.turpgames.framework.v0.util.Game;

public class Facebook {
	private static IFacebookConnector facebook;

	static {
		facebook = Game.getFacebookConnector();
	}

	private static SocialUser getUser() {
		return facebook.getUser();
	}

	private static boolean isLoggedIn() {
		return facebook.isLoggedIn();
	}

	public static void shareScore(final int mode, final int level, final ICallback callback) {
		if (Facebook.isLoggedIn()) {
			doShareScore(mode, level, callback);
		} else {
			Facebook.login(new ICallback() {
				@Override
				public void onSuccess() {
					doShareScore(mode, level, callback);
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

	private static void doShareScore(int mode, int level, final ICallback callback) {
		try {
			SocialFeed scoreFeed = SocialFeed
					.newBuilder()
					.setTitle(prepareMessage(mode, level))
					.setSubtitle("turpgames")
					.setMessage("Double Up")
					.setHref("http://www.turpgames.com/doubleupexredirect.html")
					.setImageUrl(
							"http://www.turpgames.com/res/img/doubleupex_logo.png")
					.build();
			facebook.postFeed(scoreFeed, callback);
		} catch (Throwable t) {
			callback.onFail(t);
		}
	}

	private static String prepareMessage(int mode, int level) {
		return String.format("%s has just completed level %d of %d mode in Double Up Ex!",
				getUser().getName().split(" ")[0], level, mode);
	}
}
