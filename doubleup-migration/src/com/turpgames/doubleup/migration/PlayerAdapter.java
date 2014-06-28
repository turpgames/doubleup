package com.turpgames.doubleup.migration;

import com.turpgames.entity.Player;

public class PlayerAdapter {
	private final Player oldPlayer;
	private final Player newPlayer;

	public PlayerAdapter(Player oldPlayer) {
		this.oldPlayer = oldPlayer;
		this.newPlayer = new Player();

		newPlayer.setName(oldPlayer.getName());
		newPlayer.setSocialId(oldPlayer.getSocialId());
		newPlayer.setEmail(oldPlayer.getEmail());
		newPlayer.setAuthProvider(Player.AuthFacebook);
	}

	public Player getOldPlayer() {
		return oldPlayer;
	}

	public Player getNewPlayer() {
		return newPlayer;
	}
}
