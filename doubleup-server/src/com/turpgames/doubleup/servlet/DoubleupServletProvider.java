package com.turpgames.doubleup.servlet;

import java.util.HashMap;
import java.util.Map;

import com.turpgames.db.IConnectionProvider;
import com.turpgames.doubleup.db.DoubleUpConnectionProvider;
import com.turpgames.doubleup.servlet.handlers.GetLeadersBoardActionHandler;
import com.turpgames.doubleup.servlet.handlers.RegisterPlayerActionHandler;
import com.turpgames.doubleup.servlet.handlers.SaveHiScoreActionHandler;
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
		String action = context.getParam(DoubleupServlet.request.params.action);

		String key = context.getMethod() + "." + action;
		
		synchronized (actionHandlers) {
			if (actionHandlers.containsKey(key))
				return actionHandlers.get(key);

			IServletActionHandler handler;

			if (DoubleupServlet.request.method.get.equals(context.getMethod()))
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
		if (DoubleupServlet.request.values.action.saveHiScore.equals(action))
			return new SaveHiScoreActionHandler();
		
		if (DoubleupServlet.request.values.action.getLeadersBoard.equals(action))
			return new GetLeadersBoardActionHandler();

		if (DoubleupServlet.request.values.action.registerPlayer.equals(action))
			return new RegisterPlayerActionHandler();

		return IServletActionHandler.NULL;
	}
}
