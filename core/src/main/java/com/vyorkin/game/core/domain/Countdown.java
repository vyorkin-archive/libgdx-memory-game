package com.vyorkin.game.core.domain;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.vyorkin.engine.E;
import com.vyorkin.engine.base.Renderable;
import com.vyorkin.engine.helpers.FontHelper;
import com.vyorkin.game.core.resources.GameSound;

public class Countdown implements Renderable {
	class TickTask extends Task {
		private final Runnable doneCallback;
		
		public TickTask(final Runnable doneCallback) {
			this.doneCallback = doneCallback;
		}
		
		@Override
		public void run() {
			E.sounds.play(GameSound.MENU_EXIT);
			if (seconds-- == 0 || cancelled) {
				this.cancel();
				doneCallback.run();
			}
		}
		
	}
	
	private final Vector2 position;
	private int seconds;
	private boolean cancelled;
	
	public Countdown(int width, int height) {
		this.position = new Vector2(width / 2, height / 2);
	}
	
	@Override
	public void render(float delta) {
		if (isCounting()) {
			FontHelper.draw(seconds, position);
		}
	}
	
	public void start(int seconds, final Runnable doneCallback) {
		this.cancelled = false;
		this.seconds = seconds;
		
		Timer.schedule(new TickTask(doneCallback), 0, 1);
	}
	
	public void cancel() {
		cancelled = true;
	}
	
	public boolean isCounting() {
		return seconds > 0 && !cancelled;
	}
}
