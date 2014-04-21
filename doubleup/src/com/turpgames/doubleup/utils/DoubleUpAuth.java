package com.turpgames.doubleup.utils;

import com.turpgames.framework.v0.social.ICallback;

public class DoubleUpAuth implements IDoubleUpAuth {
	@Override
	public void registerPlayer(final ICallback callback) {
		DoubleUpClient.registerPlayer(callback);
	}
}
