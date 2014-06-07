package com.turpgames.doubleup.utils;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.social.IFacebookConnector;
import com.turpgames.framework.v0.social.SocialFeed;
import com.turpgames.framework.v0.social.SocialUser;
import com.turpgames.framework.v0.util.Game;

public class Facebook {
	public static interface IFacebookListener {
		void onLogin();

		void onLogout();

		void onShareScore();
	}

	public static interface IShareTitleBuilder {
		String buildTitle();
	}

	private static IFacebookConnector facebook;
	private static List<IFacebookListener> listeners;

	static {
		facebook = Game.getFacebookConnector();
		listeners = new ArrayList<Facebook.IFacebookListener>();
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

	private static void notifyShareScore() {
		for (IFacebookListener listener : listeners)
			listener.onShareScore();
	}

	public static SocialUser getUser() {
		return facebook.getUser();
	}

	public static boolean isLoggedIn() {
		return facebook.isLoggedIn();
	}

	public static void login(final ICallback callback) {
		DoubleUp.blockUI("Logging in...");
		facebook.login(new ICallback() {
			@Override
			public void onSuccess() {
				DoubleUp.unblockUI();
				DoubleUpToast.showInfo("Login Successful");

				notifyLogin();

				callback.onSuccess();
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

	public static void shareScore(final IShareTitleBuilder titleBuilder, final ICallback callback) {
		if (Facebook.isLoggedIn()) {
			doShareScore(titleBuilder, callback);
		} else {
			login(new ICallback() {
				@Override
				public void onSuccess() {
					doShareScore(titleBuilder, callback);
				}

				@Override
				public void onFail(Throwable t) {
					callback.onFail(t);
				}
			});
		}
	}

	private static void doShareScore(IShareTitleBuilder titleBuilder, final ICallback callback) {
		try {
			DoubleUp.blockUI("Sharing score...");

			SocialFeed scoreFeed = SocialFeed
					.newBuilder()
					.setTitle(titleBuilder.buildTitle())
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
					notifyShareScore();
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

	public static void loadFriendList(ICallback callback) {
		if (getUser().getFriends() == null)
			facebook.loadFriends(callback);
		else
			callback.onSuccess();
	}
}
