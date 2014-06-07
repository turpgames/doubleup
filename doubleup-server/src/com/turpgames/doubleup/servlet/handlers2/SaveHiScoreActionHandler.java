package com.turpgames.doubleup.servlet.handlers2;

import java.io.IOException;

import com.turpgames.doubleup.db2.Db;
import com.turpgames.doubleup.entity2.Score;
import com.turpgames.servlet.IServletActionHandler;
import com.turpgames.servlet.RequestContext;

public class SaveHiScoreActionHandler implements IServletActionHandler {
	@Override
	public void handle(RequestContext context) throws IOException {
		Score score = context.getRequestContentAs(Score.class);
		Db.Scores.insert(score);
	}
}
