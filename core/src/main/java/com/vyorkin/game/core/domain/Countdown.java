package com.vyorkin.game.core.domain;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.vyorkin.engine.E;
import com.vyorkin.engine.base.Renderable;
import com.vyorkin.engine.helpers.FontHelper;
import com.vyorkin.game.core.resources.GameSound;

public class Countdown implements Renderable {
	private final Vector2 position;
	private int ticks;
	private boolean cancelled;
	
	public Countdown() {
		this.position = new Vector2(E.settings.width/2, E.settings.height/2);
	}
	
	public void start(int seconds, final Runnable runnable) {
		cancelled = false;
		ticks = seconds;
		Timer.schedule(new Task() {
			@Override
			public void run() {
				E.sounds.play(GameSound.MENU_EXIT);
				if (ticks-- == 0 || cancelled) {
					this.cancel();
					runnable.run();
				}
			}
		}, 0, 1);
	}
	
	@Override
	public void render(float delta) {
		if (isCounting()) {
			FontHelper.draw(ticks, position);
		}
	}
	
	public void cancel() {
		cancelled = true;
	}
	
	public boolean isCounting() {
		return ticks >= 0;
	}
}
