package com.vyorkin.game.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import com.vyorkin.engine.E;
import com.vyorkin.engine.screens.GameScreen;
import com.vyorkin.engine.screens.UIScreen;
import com.vyorkin.engine.utils.TouchUpListener;

import com.vyorkin.game.core.resources.*;

public class MenuScreen extends UIScreen {
	public MenuScreen() {
		super(GameSkin.UI);
	}

	private class SetScreenListener extends TouchUpListener {
		private final GameScreen screen;
		
		public SetScreenListener(GameScreen screen) {
			this.screen = screen;
		}
		
		@Override
    	public void touchUp(InputEvent event, 
			float x, float y, int pointer, int button) {
    		E.sounds.play(GameSound.MENU_ENTER_CLICK);
    		setNextScreen(screen);
    	}
	}
	
	private GameScreen nextScreen;
	
	private void setNextScreen(GameScreen screen) {
		this.nextScreen = screen;
		setDone();
	}
	
	public GameScreen getNextScreen() {
		return nextScreen;
	}
	
	@Override
	public void show() {
		super.show();
		
        table.add(E.settings.title).spaceBottom(50);
        table.row();
        
        addButton("New Game", new LevelScreen(1));
        
        TextButton button = new TextButton("Exit", skin);
        button.addListener(new TouchUpListener() {
        	public void touchUp(InputEvent event, 
    			float x, float y, int pointer, int button) {
        		
        		Gdx.app.exit();
        	}
		});
        table.add(button).uniform().fill().spaceBottom(10).row();
        
        E.music.play(GameMusic.MENU);
	}
	
	private void addButton(String text, GameScreen screen) {
		TextButton button = new TextButton(text, skin);
		button.addListener(new SetScreenListener(screen));
        table.add(button).uniform().fill().spaceBottom(10).row();
	}

	@Override
	public void load() {
		super.load();
		E.assets.load(GameMusic.MENU, Music.class);
	}

	@Override
	protected void unload() {
		super.unload();
		E.assets.unload(GameMusic.MENU);
	}
}