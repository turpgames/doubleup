package com.turpgames.doubleup.utils;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.doubleup.entity.Score;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.social.IFacebookConnector;
import com.turpgames.framework.v0.social.SocialFeed;
import com.turpgames.framework.v0.social.SocialUser;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.utils.Util;

public class Facebook {
	public static interface IFacebookListener {
		void onLogin();

		void onLogout();
	}

	private static IFacebookConnector facebook;
	private static List<IFacebookListener> listeners;
	private static IDoubleUpAuth auth;

	static {
		facebook = Game.getFacebookConnector();
		listeners = new ArrayList<Facebook.IFacebookListener>();

		String authClass = Game.getParam("auth-class");
		auth = (IDoubleUpAuth) Util.Misc.createInstance(authClass);
	}

	public static void registerListener(IFacebookListener listener) {
		listeners.add(listener);
	}

	private static void notifyLogin() {
		for (IFacebookListener listener : listeners)
			listener.onLogin();
	}

	private static void notifyLogout() {
		for (IFacebookListener listener : listeners)
			listener.onLogout();
	}

	public static SocialUser getUser() {
		return facebook.getUser();
	}

	public static boolean isLoggedIn() {
		return facebook.isLoggedIn();
	}

	public static boolean canLogin() {
		return !"".equals(CommonSettings.getPlayerFacebookId());
	}

	public static void shareScore(final Score score, final ICallback callback) {
		if (Facebook.isLoggedIn()) {
			doShareScore(score, callback);
		} else {
			login(new ICallback() {
				@Override
				public void onSuccess() {
					doShareScore(score, callback);
				}

				@Override
				public void onFail(Throwable t) {
					callback.onFail(t);
				}
			});
		}
	}

	public static void login(final ICallback callback) {
		DoubleUp.blockUI("Logging in...");
		facebook.login(new ICallback() {
			@Override
			public void onSuccess() {
				auth.registerPlayer(new ICallback() {
					@Override
					public void onSuccess() {
						DoubleUp.unblockUI();
						DoubleUpToast.showInfo("Login Successful");

						notifyLogin();

						callback.onSuccess();
					}

					@Override
					public void onFail(Throwable t) {
						logout(ICallback.NULL);

						DoubleUp.unblockUI();
						DoubleUpToast.showError("Login Failed");

						callback.onFail(t);
					}
				});
			}

			@Override
			public void onFail(Throwable t) {
				DoubleUp.unblockUI();

				DoubleUpToast.showError("Login Failed");

				callback.onFail(t);
			}
		});
	}

	public static void logout(final ICallback callback) {
		facebook.logout(new ICallback() {
			@Override
			public void onSuccess() {
				CommonSettings.setPlayerId("");
				CommonSettings.setPlayerFacebookId("");

				DoubleUpToast.showInfo("Logout Successful");

				notifyLogout();

				callback.onSuccess();
			}

			@Override
			public void onFail(Throwable t) {
				DoubleUpToast.showError("Logout Failed");
				callback.onFail(t);
			}
		});
	}

	private static void doShareScore(Score score, final ICallback callback) {
		try {
			DoubleUp.blockUI("Sharing score...");

			SocialFeed scoreFeed = SocialFeed
					.newBuilder()
					.setTitle(buildTitle(score))
					.setSubtitle("turpgames")
					.setMessage("Double Up")
					.setHref("http://www.turpgames.com/doubleupredirect.html")
					.setImageUrl("http://www.turpgames.com/res/img/doubleup_logo.png")
					.build();

			facebook.postFeed(scoreFeed, new ICallback() {
				@Override
				public void onSuccess() {
					DoubleUp.unblockUI();
					DoubleUpToast.showInfo("Score Shared");
					callback.onSuccess();
				}

				@Override
				public void onFail(Throwable t) {
					DoubleUp.unblockUI();
					DoubleUpToast.showError("Score Share Failed");
					callback.onFail(t);
				}
			});
		} catch (Throwable t) {
			DoubleUp.unblockUI();
			DoubleUpToast.showError("Score Share Failed");
			callback.onFail(t);
		}
	}

	private static String buildTitle(Score score) {
		if (score.getMode() == Score.Mode4x4 || score.getMode() == Score.Mode5x5) {
			String mode = score.getMode() == Score.Mode5x5 ? "5x5" : "4x4";
			String name = Facebook.getUser().getName().split(" ")[0];

			return String.format("%s just reached %d with %d points in Double Up %s mode!",
					name, score.getMaxNumber(), score.getScore(), mode);
		}
		return "";
	}

	public static void loadFriendList(ICallback callback) {
		if (getUser().getFriends() == null)
			facebook.loadFriends(callback);
		else
			callback.onSuccess();
	}
}
