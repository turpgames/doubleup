package com.turpgames.doubleup.updates;

import com.turpgames.framework.v0.impl.UpdateProcessor;

public final class DoubleUpUpdateManager {

	public static void runUpdates() {
		UpdateProcessor.instance.addProcess(new Update170());
		UpdateProcessor.instance.execute();
	}
	
	private DoubleUpUpdateManager() {
		
	}
}
