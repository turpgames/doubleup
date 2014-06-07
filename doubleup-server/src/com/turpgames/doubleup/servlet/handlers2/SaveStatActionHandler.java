package com.turpgames.doubleup.servlet.handlers2;

import java.io.IOException;

import com.turpgames.doubleup.db2.Db;
import com.turpgames.doubleup.entity2.Stat;
import com.turpgames.servlet.IServletActionHandler;
import com.turpgames.servlet.RequestContext;

public class SaveStatActionHandler implements IServletActionHandler {
	@Override
	public void handle(RequestContext context) throws IOException {
		Stat stat = context.getRequestContentAs(Stat.class);
		Db.Stats.insert(stat);
	}
}
