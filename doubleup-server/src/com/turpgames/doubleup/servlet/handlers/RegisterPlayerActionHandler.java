package com.turpgames.doubleup.servlet.handlers;

import java.io.IOException;

import com.turpgames.doubleup.db.Db;
import com.turpgames.doubleup.servlet.DoubleupServlet;
import com.turpgames.doubleup.entity.Player;
import com.turpgames.servlet.IServletActionHandler;
import com.turpgames.servlet.RequestContext;

public class RegisterPlayerActionHandler implements IServletActionHandler {
	@Override
	public void handle(RequestContext context) throws IOException {
		String facebookId = context.getParam(DoubleupServlet.request.params.facebookId);
		String email = context.getParam(DoubleupServlet.request.params.email);
		String username = context.getParam(DoubleupServlet.request.params.username);

		Player player = Db.Players.findByFacebookId(facebookId);

		if (player == null) {

			player = new Player();

			player.setFacebookId(facebookId);
			player.setEmail(email);
			player.setUsername(username);
			player.setPassword("");

			Db.Players.insert(player);
		}

		context.writeToResponse("" + player.getId());
	}
}