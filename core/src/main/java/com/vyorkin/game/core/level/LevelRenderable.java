package com.vyorkin.game.core.level;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.vyorkin.engine.E;
import com.vyorkin.engine.base.Renderable;
import com.vyorkin.engine.base.Updatable;
import com.vyorkin.engine.common.GameCamera;
import com.vyorkin.game.core.domain.Countdown;
import com.vyorkin.game.core.entities.Entity;
import com.vyorkin.game.core.entities.EntityRenderer;

public class LevelRenderable implements Renderable, Updatable, Disposable {
	private Level level;
	
	private final EntityRenderer entityRenderer;
	private final DebugRenderer debugRenderer;
	private final HudRenderer hudRenderer;
	private final Countdown countdown;
	
	private final ShapeRenderer shapeRenderer;
	private final GameCamera camera;
	
	private long seed;
	
	public LevelRenderable(LevelView view, GameCamera camera, Countdown countdown) {
		this.camera = camera;
		this.shapeRenderer = new ShapeRenderer();
		this.seed = MathUtils.random(100);
		
		this.entityRenderer = new EntityRenderer(shapeRenderer);
		this.debugRenderer = new DebugRenderer(shapeRenderer, view);
		this.hudRenderer = new HudRenderer();
		
		this.countdown = countdown;
	}

	public void setLevel(Level level) {
		this.level = level;
		entityRenderer.setLevel(level);
		this.seed++;
	}
	
	@Override
	public void update(float delta) {	
		MathUtils.random.setSeed(seed);		
	}
	
	@Override
	public void render(float delta) {
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		renderEntities(delta);
		
		if (E.preferences.isDeveloperMode()) {
			debugRenderer.render(level, delta);
		}
		
		hudRenderer.render(level, delta);
		countdown.render(delta);
	}
	
	private void renderEntities(float delta) {
		shapeRenderer.begin(ShapeType.Filled);
		for (Entity entity : level.getEntities()) {
			entityRenderer.render(entity, delta);
		}	
		shapeRenderer.end();
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}
}
