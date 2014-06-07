package com.turpgames.doubleup.client;

import com.turpgames.doubleup.entity2.Actions;
import com.turpgames.doubleup.entity2.Player;
import com.turpgames.doubleup.utils.DoubleUpPlayer;
import com.turpgames.doubleup.utils.DoubleUpSettings;
import com.turpgames.doubleup.utils.Facebook;
import com.turpgames.framework.v0.IHttpResponse;
import com.turpgames.framework.v0.impl.HttpRequest.Builder;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.social.SocialUser;
import com.turpgames.json.JsonEncoder;

public class RegisterPlayerAction extends DoubleUpAction {
	public RegisterPlayerAction() {
		super(Actions.registerPlayer, true);

	}

	@Override
	protected void onSuccess(IHttpResponse response, ICallback callback) {
		try {
			Player p = JsonEncoder.decode(response.getInputStream(), Player.class);

			Player player = DoubleUpPlayer.getInstance();

			player.setEmail(p.getEmail());
			player.setFacebookId(p.getFacebookId());
			player.setUsername(p.getUsername());
			player.setId(p.getId());
			player.setJoinTime(p.getJoinTime());

			callback.onSuccess();
		} catch (Throwable t) {
			callback.onFail(t);
		}
	}

	@Override
	protected void setContent(Builder builder) {
		Player player = new Player();

		if (Facebook.isLoggedIn()) {
			SocialUser user = Facebook.getUser();

			player.setEmail(user.getEmail());
			player.setFacebookId(user.getSocialId());
			player.setUsername(user.getName());
			player.setId(DoubleUpSettings.playerId.get());
		}
		else {
			player.setId(DoubleUpSettings.anonymousPlayerId.get());
		}

		builder.setJsonContentFrom(player);
	}
}
