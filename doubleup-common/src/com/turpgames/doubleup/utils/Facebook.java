package com.turpgames.doubleup.utils;

import java.util.ArrayList;
import java.util.List;

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
		auth = (IDoubleUpAuth)Util.Misc.createInstance(authClass);
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

	public static void shareScore(final String title, final ICallback callback) {
		if (Facebook.isLoggedIn()) {
			doShareScore(title, callback);
		} else {
			login(new ICallback() {
				@Override
				public void onSuccess() {
					doShareScore(title, callback);
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
						facebook.logout(ICallback.NULL);
						
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

	private static void doShareScore(String title, final ICallback callback) {
		try {
			DoubleUp.blockUI("Sharing score...");

			SocialFeed scoreFeed = SocialFeed
					.newBuilder()
					.setTitle(title)
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

	public static void loadFriendList(ICallback callback) {
		if (getUser().getFriends() == null)
			facebook.loadFriends(callback);
		else
			callback.onSuccess();
	}
}
