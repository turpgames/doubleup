package com.turpgames.doubleup.servlet.handlers;

import java.io.IOException;
import java.util.Calendar;

import com.turpgames.doubleup.db.Db;
import com.turpgames.doubleup.servlet.DoubleupServlet;
import com.turpgames.doubleup.entity.Score;
import com.turpgames.servlet.IServletActionHandler;
import com.turpgames.servlet.RequestContext;
import com.turpgames.utils.Util;

public class SaveHiScoreActionHandler implements IServletActionHandler {
	@Override
	public void handle(RequestContext context) throws IOException {
		String modeStr = context.getParam(DoubleupServlet.request.params.mode);
		String playerIdStr = context.getParam(DoubleupServlet.request.params.playerId);
		String scoreStr = context.getParam(DoubleupServlet.request.params.score);
		String maxNumberStr = context.getParam(DoubleupServlet.request.params.maxNumber);

		int mode = Util.Strings.parseInt(modeStr);
		int playerId = Util.Strings.parseInt(playerIdStr);
		int score = Util.Strings.parseInt(scoreStr);
		int maxNumber = Util.Strings.parseInt(maxNumberStr);

		boolean isFakeRequest = 
				(mode == Score.Mode4x4 && (score > 100000 || score < 0)) ||
				(mode == Score.Mode5x5 && (score > 1000000 || score < 0));

		if (isFakeRequest) {
			System.err.println(String.format("Fake SaveHiScore request detected. mode: %s, playerId: %s, score: %s, ip: %s",
					modeStr, playerIdStr, scoreStr, context.getClientIP()));
			return;
		}

		Score scr = new Score();
		scr.setMode(mode);
		scr.setPlayerId(playerId);
		scr.setScore(score);
		scr.setMaxNumber(maxNumber);
		scr.setTime(Calendar.getInstance().getTimeInMillis());

		Db.Scores.insert(scr);
	}
}
