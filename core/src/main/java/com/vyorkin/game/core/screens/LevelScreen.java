package com.vyorkin.game.core.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import com.vyorkin.engine.E;
import com.vyorkin.engine.common.GameCamera;
import com.vyorkin.engine.helpers.FontHelper;
import com.vyorkin.engine.screens.GameScreen;

import com.vyorkin.game.core.domain.Board;
import com.vyorkin.game.core.domain.CircleItem;
import com.vyorkin.game.core.domain.Countdown;
import com.vyorkin.game.core.domain.Level;
import com.vyorkin.game.core.domain.LevelState;
import com.vyorkin.game.core.resources.GameAtlas;
import com.vyorkin.game.core.resources.GameEffect;
import com.vyorkin.game.core.resources.GameFolder;
import com.vyorkin.game.core.resources.GameMusic;
import com.vyorkin.game.core.resources.GameSound;

public class LevelScreen extends GameScreen {
	
	private final int ERRORS_MAX = 2;
	private final int LIVES_MAX = 3;
	
	private final Board board;
	private final Level level;
	private final Countdown countdown;
	
	private int lives;
	private int memorizationTime;
	private int countdownTime;
	private int shapeIndex;
	
	private ParticleEffect effect;
	
	private final GameCamera camera;
	
	public LevelScreen(int levelNumber) {
		this.camera = new GameCamera(E.settings.width, E.settings.height);
		
		this.memorizationTime = 3;
		this.countdownTime = 3;
		
		this.lives = LIVES_MAX;
		
		this.level = new Level(levelNumber);
		this.countdown = new Countdown();
		this.board = new Board();
		
		effect = new ParticleEffect();
//		effect.load(
//			Gdx.files.internal(GameEffects.SALUT), 
//			Gdx.files.internal(GameFolder.TEXTURE)
//		);
	}
	
	private void startLevel() {
		
		shapeIndex = 1;
		level.setState(LevelState.Countdown);
		
		E.music.stop();
		
		countdown.start(countdownTime, new Runnable() {
			@Override
			public void run() {
				
				board.generate(level.getNumber());
				level.nextState();
				
				E.sounds.play(GameSound.START);
				E.music.play(GameMusic.LEVEL);
				
				Timer.schedule(new Task() {
					@Override
					public void run() {
						level.nextState();
						E.sounds.play(GameSound.START);
					}
				}, memorizationTime);
			}
		});
	}

	@Override
	public Camera getCamera() {
		return camera;
	}
	
	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(this);
		
		startLevel();
	}
	
	@Override
	protected void update(float delta) {
		board.update(delta);
		camera.refresh();
	}
	
	@Override
	protected void draw(float delta) {
		LevelState state = level.getState();
		
		if (state == LevelState.GameOver) {
			FontHelper.draw("Game Over", camera.getSize().div(2));
		} else if (state == LevelState.Countdown) {
			countdown.render(delta);
		} else {
			board.render(state, camera);
			effect.draw(E.batch, delta);
		}
		
		renderHUD(delta);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		camera.resize(width, height);
	}
	
	private void renderHUD(float delta) {
		E.font.draw(E.batch,
			String.format("Level: %d Lives: %d Errors: %d", 
				level.getNumber(), lives, level.errors), 
			20, 50
		);
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (level.getState() != LevelState.Playing)
			return false;
		
		Vector2 pos = camera.unproject(x, y);
		
		E.log("mouse(x, y): " + pos.x + ", " + pos.y);
		
//		effect.setPosition(pos.x, pos.y);
//		effect.start();
		
		for (CircleItem item : board.getCircles()) {
			if (!item.marked && item.shape.contains(pos.x, pos.y)) {
				Gdx.input.vibrate(100);
				if (item.number == shapeIndex) {
					E.sounds.play(GameSound.SELECT);
					
					item.mark();
					shapeIndex++;
					
					if (shapeIndex > board.getCircles().size()) {
						level.setState(LevelState.Win);
					}
				} else {
					level.errors++;
					
					E.sounds.play(GameSound.ERROR);
					
					if (level.errors > ERRORS_MAX) {
						lives--;
						if (lives <= 0 || level.getNumber() <= 1) {
							level.setState(LevelState.GameOver);
							E.music.play(GameMusic.GAME_OVER);
							Timer.schedule(new Task() {
								@Override
								public void run() {
									setDone();
								}
							}, 8);
						} else {
							level.setState(LevelState.Lose);
						}
					}
				}
			}
		}
		
		if (level.isDone()) {
			if (level.getNumber() < board.getCellCount()) {
				level.next();
				startLevel();	
			} else {
				setDone();
			}
		}
		
		return true;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.ESCAPE)
			Gdx.app.exit();
		
		if (keycode == Input.Keys.SPACE) {
			countdown.cancel();
			level.setState(LevelState.Win);
			level.next();
			startLevel();
		}
		if (keycode == Input.Keys.BACK) {
			countdown.cancel();
			setDone();
		}
		
		if (keycode == Input.Keys.D) {
			E.preferences.toggleDeveloperMode();
		}
		return true;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		effect.dispose();
		board.dispose();
	}
	
	@Override
	public void load() {
		E.assets.load(GameAtlas.LEVEL, TextureAtlas.class);
		E.assets.load(GameSound.SELECT, Sound.class);
		E.assets.load(GameSound.START, Sound.class);
		E.assets.load(GameSound.ERROR, Sound.class);
		
		E.assets.load(GameMusic.LEVEL, Music.class);
		E.assets.load(GameMusic.GAME_OVER, Music.class);
	}
	
	@Override
	protected void unload() {
		E.assets.unload(GameAtlas.LEVEL);
		E.assets.unload(GameSound.SELECT);
		E.assets.unload(GameSound.START);
		E.assets.unload(GameSound.ERROR);
		
		E.assets.unload(GameMusic.LEVEL);
		E.assets.unload(GameMusic.GAME_OVER);
	}
}






















