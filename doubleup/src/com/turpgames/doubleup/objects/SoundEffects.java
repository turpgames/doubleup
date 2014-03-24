package com.turpgames.doubleup.objects;

import java.util.LinkedList;

import com.turpgames.framework.v0.ISound;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.framework.v0.util.Timer.ITimerTickListener;

public class SoundEffects {

	private static LinkedList<ISound> queue;
	private static Timer effectTimer;
	static {
		queue = new LinkedList<ISound>();
		
		effectTimer = new Timer();
		effectTimer.setInterval(0.070f);
		effectTimer.setTickListener(new ITimerTickListener() {
			
			@Override
			public void timerTick(Timer timer) {
				if (queue.isEmpty())
					return;
				queue.pop().play();
			}
		});
		
		effectTimer.start();
	}
	
	public static void addEffect(ISound sound) {
		queue.add(sound);
	}
}
