package com.vyorkin.game.core.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.vyorkin.engine.E;
import com.vyorkin.engine.common.GameCamera;
import com.vyorkin.engine.helpers.FontHelper;
import com.vyorkin.engine.screens.GameScreen;
import com.vyorkin.game.core.domain.Countdown;
import com.vyorkin.game.core.domain.GameSettings;
import com.vyorkin.game.core.domain.PlayerProfile;
import com.vyorkin.game.core.entities.Entity;
import com.vyorkin.game.core.level.CursorRenderer;
import com.vyorkin.game.core.level.Level;
import com.vyorkin.game.core.level.LevelFactory;
import com.vyorkin.game.core.level.LevelManager;
import com.vyorkin.game.core.level.LevelMetadata;
import com.vyorkin.game.core.level.LevelRenderable;
import com.vyorkin.game.core.level.LevelState;
import com.vyorkin.game.core.level.LevelView;
import com.vyorkin.game.core.resources.GameAtlas;
import com.vyorkin.game.core.resources.GameMusic;
import com.vyorkin.game.core.resources.GameSound;

public class LevelScreen extends GameScreen {
	private final PlayerProfile profile;
	
	private final LevelFactory factory;
	private final LevelManager manager;
	
	private Level level;
	
	private final LevelView view;
	private final Countdown countdown;
	private final LevelRenderable levelRenderable;
	private final CursorRenderer cursorRenderer;
	
	private final GameCamera camera;
	
	public LevelScreen(GameSettings settings, PlayerProfile profile) {
		this.profile = profile;
		
		this.manager = new LevelManager(profile);
		this.view = new LevelView(settings);
		this.factory = new LevelFactory(view);
		
		this.camera = new GameCamera(
			E.settings.width, 
			E.settings.height
		);
		this.countdown = new Countdown(
			Gdx.graphics.getWidth(), 
			Gdx.graphics.getHeight()
		);
		this.levelRenderable = new LevelRenderable(view, camera, countdown);
		this.cursorRenderer = new CursorRenderer();
	}
	
	private void startLevel() {
		E.music.stop();
		
		final LevelMetadata metadata = manager.get(
			profile.season, profile.number);
		
		this.level = factory.create(metadata);
		this.levelRenderable.setLevel(level);

		countdown.start(metadata.countdownTime, new Runnable() {
			@Override
			public void run() {
				
				level.setState(LevelState.Memorization);
				
				E.sounds.play(GameSound.START);
				E.music.play(GameMusic.LEVEL);
				
				Timer.schedule(new Task() {
					@Override
					public void run() {
						level.setState(LevelState.Playing);
						E.sounds.play(GameSound.START);
					}
				}, metadata.memorizationTime);
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
		levelRenderable.update(delta);
		camera.refresh();
	}
	
	@Override
	protected void draw(float delta) {
		if (level.isDone()) {
			FontHelper.draw("You done!", camera.getSize().cpy().div(2));
		} else {
			levelRenderable.render(delta);
			// cursorRenderer.render(model, delta)
		}
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		camera.resize(width, height);
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (level.getState() != LevelState.Playing)
			return false;
		
		Vector2 pos = camera.unproject(x, y);
		
		E.log("mouse(x, y): " + pos.x + ", " + pos.y);
		
//		effect.setPosition(pos.x, pos.y);
//		effect.start();
		
		for (Entity entity : level.getEntities()) {
			if (entity.isClicked(pos)) {
				
				Gdx.input.vibrate(100);
				
				if (entity.getNumber() == level.index) {
					entity.mark();
					level.index++;
					E.sounds.play(GameSound.SELECT);
				} else {
					level.errors++;
					E.sounds.play(GameSound.ERROR);
				}
				
				break;
			}
		}
		
		level.updateState();
		
		if (level.isDone()) {
			if (level.getState() == LevelState.Lose) {
				E.music.play(GameMusic.GAME_OVER);
				Timer.schedule(new Task() {
					@Override
					public void run() {
						setDone();
					}
				}, 8);
			}
			
			else {
				
				profile.nextLevel();
				profile.save();
				
				startLevel();	
			}
		}
		
		return true;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.ESCAPE)
			Gdx.app.exit();
		
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
		
		levelRenderable.dispose();
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






















