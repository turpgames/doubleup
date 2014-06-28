package com.turpgames.server.db;

import java.util.Calendar;

public abstract class Cache<T> {
	private final int timeoutMinutes;
	
	private Calendar timeout;	
	private T data;
	
	protected Cache(int timeoutMinutes) {
		this.timeoutMinutes = timeoutMinutes;
	}
	
	private boolean requiresReload() {	
		return data == null || Calendar.getInstance().compareTo(timeout) > 0;
	}
	
	public T getData() {
		if (requiresReload()) {
			data = load();
			timeout = Calendar.getInstance();
			timeout.add(Calendar.MINUTE, timeoutMinutes);
		}
		
		return data;
	}
	
	protected abstract T load();
}
