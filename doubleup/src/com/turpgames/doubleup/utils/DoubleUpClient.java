package com.turpgames.doubleup.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import com.turpgames.doubleup.entity2.LeadersBoard;
import com.turpgames.doubleup.entity2.Player;
import com.turpgames.doubleup.entity2.Score;
import com.turpgames.doubleup.utils.ScoreManager.ILeadersBoardCallback;
import com.turpgames.framework.v0.IHttpResponse;
import com.turpgames.framework.v0.IHttpResponseListener;
import com.turpgames.framework.v0.impl.HttpRequest;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.social.SocialUser;
import com.turpgames.framework.v0.util.Debug;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.json.JsonEncoder;
import com.turpgames.utils.Util;

public class DoubleUpClient {
	private final static String sendScoreUrlFormat;
	private final static String getLeadersBoardUrlFormat;
	private final static String registerPlayerUrlFormat;

	static {
		String server = Game.getParam("server");
		String baseUrl = Game.getParam(server + "-server");

		sendScoreUrlFormat = baseUrl + Game.getParam("send-score-params");
		getLeadersBoardUrlFormat = baseUrl + Game.getParam("get-leadersboard-params");
		registerPlayerUrlFormat = baseUrl + Game.getParam("register-player-params");
	}

	public static void sendScore(Score score, final ICallback callback) {
		String url = String.format(sendScoreUrlFormat, score.getPlayerId(), score.getMode(), score.getScore(), score.getMaxNumber());

		Debug.println("doSendScore, sending score...");
		HttpRequest.newPostRequestBuilder()
				.setUrl(url)
				.setTimeout(5000)
				.setAsync(true)
				.build()
				.send(new IHttpResponseListener() {
					@Override
					public void onHttpResponseReceived(IHttpResponse response) {
						if (response.getStatus() == 200) {
							callback.onSuccess();
						} else {
							callback.onFail(null);
						}
					}

					@Override
					public void onError(Throwable t) {
						callback.onFail(t);
					}

					@Override
					public void requestCancelled() {
						callback.onFail(null);
					}
				});
	}

	public static void registerPlayer(final ICallback callback) {
		try {
			Debug.println("registerPlayer, getting player...");
			final Player player = DoubleUpPlayer.getInstance();

			String url = String.format(registerPlayerUrlFormat,
					player.getFacebookId(),
					URLEncoder.encode(player.getEmail(), "UTF-8"),
					URLEncoder.encode(player.getUsername(), "UTF-8"));

			Debug.println("sending http request...");
			HttpRequest.newPostRequestBuilder()
					.setUrl(url)
					.setAsync(true)
					.setTimeout(5000)
					.build()
					.send(new IHttpResponseListener() {
						@Override
						public void onHttpResponseReceived(IHttpResponse response) {
							if (response.getStatus() == 200) {
								try {
									String idStr = Util.IO.readUtf8String(response.getInputStream());
									int playerId = Util.Strings.parseInt(idStr);

									if (playerId > 0) {
										player.setId(playerId);

										Debug.println("register player succeeded...");

										//Facebook.facebookIdSetting.set(player.getFacebookId());

										callback.onSuccess();
									}
									else {
										Debug.println("register player failed, playerId is less then 1");
										callback.onFail(null);
									}
								} catch (Throwable t) {
									Debug.println("register player failed while processing registration response");
									callback.onFail(t);
								}
							} else {
								Debug.println("register player failed with http status code: " + response.getStatus());
								callback.onFail(null);
							}
						}

						@Override
						public void onError(Throwable t) {
							Debug.println("register player http request failed");
							callback.onFail(t);
						}

						@Override
						public void requestCancelled() {
							callback.onFail(null);
						}
					});
		} catch (Throwable t) {
			Debug.println("register player failed");
			callback.onFail(t);
		}
	}

	public static void getLeadersBoard(int mode, int days, int whose, final ILeadersBoardCallback callback) {
		int playerId = DoubleUpPlayer.getInstance().getId();
		final String url = String.format(getLeadersBoardUrlFormat, playerId, mode, days, whose);

		if (whose == Score.FriendsScores) {
			Facebook.loadFriendList(new ICallback() {
				@Override
				public void onSuccess() {
					byte[] contentData = getRequestContentForFriendsHiScores();

					InputStream contentStream = new ByteArrayInputStream(contentData);
					int contentLength = contentData.length;

					doGetLeadersBoard(callback, url, contentStream, contentLength);
				}

				@Override
				public void onFail(Throwable t) {
					callback.onFail(t);
				}
			});
			return;
		}
		else {
			doGetLeadersBoard(callback, url, null, 0);
		}
	}

	private static byte[] getRequestContentForFriendsHiScores() {
		String friendIds = "";

		SocialUser user = Facebook.getUser();
		SocialUser[] friends = user.getFriends();
		for (int i = 0; i < friends.length; i++) {
			friendIds += friends[i].getSocialId();
			if (i < friends.length - 1)
				friendIds += ",";
		}

		return Util.Strings.toUtf8ByteArray(friendIds);
	}

	private static void doGetLeadersBoard(final ILeadersBoardCallback callback, final String url, InputStream contentStream, int contentLength) {
		HttpRequest.newPostRequestBuilder()
				.setUrl(url)
				.setTimeout(5000)
				.setAsync(true)
				.setContent(contentStream, contentLength)
				.build()
				.send(new IHttpResponseListener() {
					@Override
					public void onHttpResponseReceived(IHttpResponse response) {
						if (response.getStatus() == 200) {
							try {
								LeadersBoard lb = JsonEncoder.decode(response.getInputStream(), LeadersBoard.class);
								callback.onSuccess(lb);
							} catch (IOException e) {
								callback.onFail(e);
							}
						} else {
							callback.onFail(null);
						}
					}

					@Override
					public void onError(Throwable t) {
						callback.onFail(t);
					}

					@Override
					public void requestCancelled() {
						callback.onFail(null);
					}
				});
	}
}
