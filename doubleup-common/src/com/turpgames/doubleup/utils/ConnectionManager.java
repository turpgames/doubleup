package com.turpgames.doubleup.utils;

import java.net.URL;
import java.net.URLConnection;

import com.turpgames.utils.Util;

public class ConnectionManager {
	private final static int connectTimeout = 2000; // 2 sec
	private final static long checkInterval = 5 * 60 * 1000; // 5 mins
	
	private static long lastCheck;
	private static boolean hasConnection;
	
	public static void init() {
		Util.Threading.runInBackground(new Runnable() {
			@Override
			public void run() {
				checkConnection();
			}
		});
	}
	
	public static boolean hasConnection() {
		long now = System.currentTimeMillis();
		
		if (now - lastCheck > checkInterval)
			init();
		
		return hasConnection;
	}

	private static void checkConnection() {
		try {
			URL url = new URL("http://www.google.com");
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(connectTimeout);
			conn.connect();
			hasConnection = true;
		}
		catch (Throwable t) {
			hasConnection = false;
		}
		finally {
			lastCheck = System.currentTimeMillis();	
		}
	}
}
