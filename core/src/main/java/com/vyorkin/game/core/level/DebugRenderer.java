package com.vyorkin.game.core.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.vyorkin.engine.E;
import com.vyorkin.engine.base.Renderer;

public class DebugRenderer implements Renderer<Level> {
	private final LevelView view;
	private final ShapeRenderer shapeRenderer;
	
	public DebugRenderer(ShapeRenderer shapeRenderer, LevelView view) {
		this.shapeRenderer = shapeRenderer;
		this.view = view;
	}
	
	@Override
	public void render(Level model, float delta) {
		renderLines();
		renderHint(model);
	}

	private void renderLines() {
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		
		// h
		for (int row = 0; row <= view.rows; row++) {
			shapeRenderer.line(
				view.offsetX, view.offsetY + row*view.cellSize, 
				view.cols*view.cellSize + view.offsetX, view.offsetY + row*view.cellSize
			);
		}
		
		// v
		for (int col = 0; col <= view.cols; col++) {
			shapeRenderer.line(
				view.offsetX + col*view.cellSize, view.offsetY, 
				view.offsetX + col*view.cellSize, view.offsetY + view.rows*view.cellSize
			);
		}
		
		shapeRenderer.end();
	}
	
	private void renderHint(Level level) {
		E.font.draw(E.batch, 
			String.format(
				"%dx%d (%f, %f): %d : %s", 
				(int)view.rows, (int)view.cols, 
				view.offsetX, view.offsetY, 
				level.getEntities().size(), 
				level.getState().name()
			), 
		20, 20);
	}
}
