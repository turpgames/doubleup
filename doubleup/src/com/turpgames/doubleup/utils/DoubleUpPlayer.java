package com.turpgames.doubleup.utils;

import com.turpgames.doubleup.client.RegisterPlayerAction;
import com.turpgames.doubleup.entity2.Player;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.utils.Util;

public final class DoubleUpPlayer {
	private DoubleUpPlayer() {

	}

	public static void bindToFacebook() {
		Facebook.registerListener(new Facebook.IFacebookListener() {
			@Override
			public void onLogout() {
				player.setEmail("");
				player.setFacebookId("");
				player.setUsername("");
				player.setId(DoubleUpSettings.anonymousPlayerId.get());
				
				DoubleUpSettings.facebookId.set("");
				DoubleUpSettings.playerId.set(0);
			}

			@Override
			public void onLogin() {

			}

			@Override
			public void onShareScore() {

			}
		});
	}

	private static boolean isRegistered = false;
	private final static Player player = new Player();

	public synchronized static Player getInstance() {
		return player;
	}

	public boolean isRegistered() {
		return isRegistered;
	}

	public static void register(final ICallback callback) {
		if (isRegistered) {
			callback.onSuccess();
			return;
		}
		
		if (canUseFacebookLogin()) {
			Facebook.login(new ICallback() {
				@Override
				public void onSuccess() {
					execute(callback);
				}

				@Override
				public void onFail(Throwable t) {
					execute(callback);
				}
			});
		}
		else {
			execute(callback);
		}
	}
	
	private static void execute(final ICallback callback) {
		RegisterPlayerAction action = new RegisterPlayerAction();
		action.execute(new ICallback() {
			@Override
			public void onSuccess() {
				isRegistered = true;
				
				DoubleUpSettings.playerId.set(player.getId());
				if (player.isAnonymous())
					DoubleUpSettings.anonymousPlayerId.set(player.getId());
				else
					DoubleUpSettings.facebookId.set(player.getFacebookId());
				
				callback.onSuccess();
			}

			@Override
			public void onFail(Throwable t) {
				isRegistered = false;
				callback.onFail(t);
			}
		});
	}

	private static boolean canUseFacebookLogin() {
		return !Util.Strings.isNullOrWhitespace(DoubleUpSettings.facebookId.get());
	}
}
