package com.turpgames.doubleup.servlet.handlers2;

import java.io.IOException;

import com.turpgames.doubleup.db2.Db;
import com.turpgames.doubleup.entity2.Player;
import com.turpgames.servlet.IServletActionHandler;
import com.turpgames.servlet.RequestContext;

public class RegisterPlayerActionHandler implements IServletActionHandler {
	@Override
	public void handle(RequestContext context) throws IOException {
		Player player = context.getRequestContentAs(Player.class);

		boolean isNewUser = player.getId() < 1;
		boolean isAnonymous = player.isAnonymous();

		if (isAnonymous && isNewUser)
			player = Db.Players.createAnonymousPlayer();
		else if (isAnonymous && !isNewUser)
			player = Db.Players.get(player.getId());
		else if (!isAnonymous && isNewUser)
			player = Db.Players.createFacebookPlayer(player);
		else
			player = Db.Players.findByFacebookId(player.getFacebookId());

		context.writeToResponse(player);
	}
}