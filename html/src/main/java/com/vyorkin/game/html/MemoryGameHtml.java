package com.vyorkin.game.html;

import com.vyorkin.game.core.MemoryGame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class MemoryGameHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new MemoryGame();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
