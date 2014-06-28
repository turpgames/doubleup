package com.turpgames.doubleup.components.hiscore;

import java.io.Serializable;
import java.util.Date;

import com.turpgames.service.message.GetHiScoresResponse;

public class HiScoreCacheItem implements Serializable {
	private static final long serialVersionUID = -5089023063302516984L;

	private GetHiScoresResponse hiScores;
	private Date lastLoadDate;

	public GetHiScoresResponse getHiScores() {
		return hiScores;
	}

	public void setHiScores(GetHiScoresResponse hiScores) {
		this.hiScores = hiScores;
	}

	public Date getLastLoadDate() {
		return lastLoadDate;
	}

	public void setLastLoadDate(Date lastLoadDate) {
		this.lastLoadDate = lastLoadDate;
	}
}
