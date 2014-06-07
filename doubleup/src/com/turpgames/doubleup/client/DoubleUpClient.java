package com.turpgames.doubleup.client;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Debug;
import com.turpgames.utils.Util;

class DoubleUpClient {
	private final static Queue<IDoubleUpAction> commandQueue = new PriorityBlockingQueue<IDoubleUpAction>();

	private static boolean isRunning = false;

	public synchronized static void start() {
		if (isRunning)
			return;

		Util.Threading.runInBackground(new Runnable() {
			@Override
			public void run() {
				executeCommands();
			}
		});
	}

	public synchronized static void stop() {
		isRunning = false;
	}

	public void enqueue(IDoubleUpAction command) {
		commandQueue.add(command);
	}

	private static void executeCommands() {
		while (isRunning) {
			Util.Threading.threadSleep(1000);
			try {
				IDoubleUpAction cmd;

				while ((cmd = commandQueue.poll()) != null) {
					cmd.execute(new ICallback() {
						@Override
						public void onSuccess() {
							Debug.println("Command succeeded");
						}

						@Override
						public void onFail(Throwable t) {
							Debug.println("Command failed: " + t);
						}
					});
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}
}
