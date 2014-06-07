package com.turpgames.doubleup.entity2;

import java.io.Serializable;
import java.util.Date;

import com.turpgames.utils.Util;

public class Player implements Serializable {
	private static final long serialVersionUID = -7814102813411510877L;

	private int id;
	private String username;
	private String email;
	private String facebookId;
	private long joinTime;
	private Date joinDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public long getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}

	public Date getJoinDate() {
		if (joinDate == null)
			joinDate = new Date(joinTime);
		return joinDate;
	}

	public boolean isAnonymous() {
		return Util.Strings.isNullOrWhitespace(facebookId);
	}

	private String profilePictureUrl;

	public String getFacebookProfilePictureUrl() {
		if (profilePictureUrl == null)
			profilePictureUrl = "http://graph.facebook.com/" + facebookId + "/picture?width=64&height=64";
		return profilePictureUrl;
	}

	@Override
	public String toString() {
		return username;
	}
}
