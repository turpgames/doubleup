package com.turpgames.doubleup.entity2;

import java.io.Serializable;

public class LeadersBoardRequest implements Serializable {
	private static final long serialVersionUID = -7284047532480840815L;

	private int playerId;
	private int mode;
	private int days;
	private int whose;
	private String[] friendsFacebookIds;

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getWhose() {
		return whose;
	}

	public void setWhose(int whose) {
		this.whose = whose;
	}

	public String[] getFriendsFacebookIds() {
		return friendsFacebookIds;
	}

	public void setFriendsFacebookIds(String[] friendsFacebookIds) {
		this.friendsFacebookIds = friendsFacebookIds;
	}
}
