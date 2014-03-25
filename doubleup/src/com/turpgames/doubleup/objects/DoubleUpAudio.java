package com.turpgames.doubleup.objects;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.turpgames.framework.v0.IMusic;
import com.turpgames.framework.v0.ISound;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.framework.v0.util.Timer.ITimerTickListener;

public class DoubleUpAudio {

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
	
	public static void playScoreSound(int score) {
		if (score < 2048)
			queue.add(sounds.get(score));
		else
			queue.add(sounds.get(2048));
	}
	
	public static void playGameOverSound() {
		gameover.play();
	}
	
	public static void playNoMoveSound() {
		nomove.play();
	}
	
	public static void playNoScoreSound() {
		noscore.play();
	}
	
	public static void playThemeMusic() {
		music.play();
	}
	
	public static void stopThemeMusic() {
		music.stop();
	}
	
	private final static Map<Integer, ISound> sounds = new HashMap<Integer, ISound>();
	private final static ISound gameover = Game.getResourceManager().getSound("gameover");
	private final static ISound nomove = Game.getResourceManager().getSound("nomove");
	private final static ISound noscore = Game.getResourceManager().getSound("noscore");
	
	private final static IMusic music = Game.getResourceManager().getMusic("theme");
	
	static {
		for (int i = 1; i <= 2048; i *= 2) {
			sounds.put(i, Game.getResourceManager().getSound("" + i));
		}
	}
}
