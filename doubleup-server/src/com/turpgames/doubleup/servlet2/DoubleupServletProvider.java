package com.turpgames.doubleup.servlet2;

import java.util.HashMap;
import java.util.Map;

import com.turpgames.db.IConnectionProvider;
import com.turpgames.doubleup.db.DoubleUpConnectionProvider;
import com.turpgames.doubleup.entity2.Actions;
import com.turpgames.servlet.IServletActionHandler;
import com.turpgames.servlet.IServletProvider;
import com.turpgames.servlet.RequestContext;

public class DoubleupServletProvider implements IServletProvider {
	private static final Map<String, IServletActionHandler> actionHandlers = new HashMap<String, IServletActionHandler>();

	@Override
	public IConnectionProvider createConnectionProvider() {
		return new DoubleUpConnectionProvider();
	}

	@Override
	public IServletActionHandler createActionHandler(RequestContext context) {
		String action = context.getParam(Actions.action);

		String key = context.getMethod() + "." + action;

		synchronized (actionHandlers) {
			if (actionHandlers.containsKey(key))
				return actionHandlers.get(key);

			IServletActionHandler handler;

			if ("GET".equals(context.getMethod()))
				handler = createGetActionHandler(action);
			else
				handler = createPostActionHandler(action);

			actionHandlers.put(key, handler);
			return handler;
		}
	}

	private IServletActionHandler createGetActionHandler(String action) {
		return IServletActionHandler.NULL;
	}

	private IServletActionHandler createPostActionHandler(String action) {
		if (Actions.saveHiScore.equals(action))
			return new com.turpgames.doubleup.servlet.handlers2.SaveHiScoreActionHandler();

		if (Actions.getLeadersBoard.equals(action))
			return new com.turpgames.doubleup.servlet.handlers2.GetLeadersBoardActionHandler();

		if (Actions.registerPlayer.equals(action))
			return new com.turpgames.doubleup.servlet.handlers2.RegisterPlayerActionHandler();

		if (Actions.saveStat.equals(action))
			return new com.turpgames.doubleup.servlet.handlers2.SaveStatActionHandler();

		return IServletActionHandler.NULL;
	}
}
