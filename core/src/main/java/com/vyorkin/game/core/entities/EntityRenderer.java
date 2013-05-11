package com.vyorkin.game.core.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

import com.vyorkin.engine.E;
import com.vyorkin.engine.base.Renderer;

import com.vyorkin.game.core.level.Level;
import com.vyorkin.game.core.level.LevelState;

public class EntityRenderer implements Renderer<Entity> {
	private static final int CIRCLE_SEGMENTS = 32;

	private final ShapeRenderer shapeRenderer;
	private Level level;
	
	public EntityRenderer(ShapeRenderer shapeRenderer) {
		this.shapeRenderer = shapeRenderer;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	@Override
	public void render(Entity model, float delta) {
		LevelState levelState = level.getState();
		Circle shape = model.getShape();
		
		shapeRenderer.setColor(1, 1, 1, 1);
		shapeRenderer.circle(shape.x, shape.y, shape.radius, CIRCLE_SEGMENTS);
		
		if (levelState == LevelState.Memorization || 
			levelState == LevelState.Playing && model.isMarked()) {
			renderNumber(model);
		}
		
		if (E.preferences.isDeveloperMode()) {
			E.font.draw(
				E.batch, 
//				"(" + (int)cell.x + ", " + (int)cell.y + ")",
				"(" + shape.x + ", " + shape.y + ")",
				shape.x + shape.radius / 2, 
				shape.y - shape.radius / 2
			);
		}
	}
	
	private void renderNumber(Entity model) {
		Circle shape = model.getShape();
		
		String numberStr = Integer.toString(model.getNumber());
		TextBounds numberBounds = E.font.getBounds(numberStr);
		
		float posx = shape.x - numberBounds.width / 2;
		float posy = shape.y + numberBounds.height / 2;
		
		E.font.draw(E.batch, numberStr, posx, posy);
	}
}
