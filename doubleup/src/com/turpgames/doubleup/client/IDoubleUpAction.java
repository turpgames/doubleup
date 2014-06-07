package com.turpgames.doubleup.client;

import com.turpgames.framework.v0.social.ICallback;

public interface IDoubleUpAction {
	void execute(ICallback callback);
	
	/*
	 * async
	 * 
	 * RegisterAnonymous
	 * SendStat
	 * SendScore
	 * 
	 * 
	 * 
	 * 
	 * 
	 * sync
	 * 
	 * RegisterFacebook
	 * GetLeadersBoard
	 */
}
