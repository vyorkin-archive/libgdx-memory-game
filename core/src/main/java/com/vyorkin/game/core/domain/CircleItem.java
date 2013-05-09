package com.vyorkin.game.core.domain;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.vyorkin.engine.E;

public class CircleItem {
	public static final float PADDING = 10;
	private static final int CIRCLE_SEGMENTS = 64;
	
	public Vector2 cell;
	public final Circle shape;
	public final int number;
	public Color color;
	public boolean marked;
	
	public CircleItem(Vector2 cell, Circle shape, int number, Color color) {
		this.cell = cell;
		this.shape = shape;
		this.number = number;
		this.color = color;
	}
	
	public void mark() {
		color = Color.GREEN;
		marked = true;
	}

	public void render(LevelState state, ShapeRenderer renderer) {
		renderer.setColor(color);
		renderer.circle(shape.x, shape.y, shape.radius, CIRCLE_SEGMENTS);
		
		if (state == LevelState.Memorization || 
			state == LevelState.Playing && marked) {
			renderNumber();
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
	
	private void renderNumber() {
		
		String numberStr = Integer.toString(number);
		TextBounds numberBounds = E.font.getBounds(numberStr);
		
		float posx = shape.x - numberBounds.width/2;
		float posy = shape.y + numberBounds.height/2;
		
		E.font.draw(E.batch, numberStr, posx, posy);
	}
}