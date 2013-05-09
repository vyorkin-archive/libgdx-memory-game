package com.vyorkin.game.core.domain;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.vyorkin.engine.E;

public class Board implements Disposable {	
	private final int SIZE = 4;
	
	private final ShapeRenderer renderer;
	
	private final float width;
	private final float height;
	
	private final Vector2 offset;
	private final Vector2 size;
	private final int cellCount;
	
	private float cellSize;
	private float radius;
	
	private long seed = 0;
	private List<CircleItem> circles;
	
	private final BoardGenerator generator;
	
	public Board() {
		this.seed = MathUtils.random(100);
		
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();
		
		float aspectRatio = this.width / this.height;
		
		this.size = new Vector2(MathUtils.floor(SIZE * aspectRatio), SIZE);
		this.cellCount = (int)this.size.x * (int)this.size.y;
		this.cellSize = this.height / (this.size.y + 1);
		this.offset = new Vector2(
			(this.width - this.cellSize*this.size.x)/2, 
			(this.height - this.cellSize*this.size.y)/2
		);
		this.radius = (this.cellSize)/2;
		
		this.renderer = new ShapeRenderer();
		this.generator = new BoardGenerator(
			this.size, this.offset, this.radius);
	}
	
	public void generate(int level) {
		setCircles(generator.generate(level));
	}
	
	public void setCircles(List<CircleItem> circles) {
		this.circles = circles;
		this.seed++;
	}

	public List<CircleItem> getCircles() {
		return circles;
	}
	
	public Vector2 getSize() {
		return size;
	}
	
	public int getCellCount() {
		return cellCount;
	}
	
	public void update(float delta) {	
		MathUtils.random.setSeed(seed);		
	}
	
	public void render(LevelState state, Camera camera) {
		renderer.setProjectionMatrix(camera.combined);
		if (E.preferences.isDeveloperMode()) {
			renderLines();
			renderHint(state);
		}
		renderCircles(state);
	}
	
	private void renderCircles(LevelState state) {
		renderer.begin(ShapeType.Filled);
		for (CircleItem item : circles) {
			item.render(state, renderer);
		}	
		renderer.end();
	}
	
	private void renderLines() {
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.WHITE);
		
		// h
		for (int y = 0; y <= size.y; y++) {
			renderer.line(
				offset.x, offset.y + y*cellSize, 
				size.x*cellSize + offset.x, offset.y + y*cellSize
			);
		}
		
		// v
		for (int x = 0; x <= size.x; x++) {
			renderer.line(
				offset.x + x*cellSize, offset.y, 
				offset.x + x*cellSize, offset.y + size.y*cellSize
			);
		}
		
		renderer.end();
	}
	
	private void renderHint(LevelState state) {
		E.font.draw(E.batch, String.format(
			"%dx%d (%f, %f): %d : %s", 
			(int)size.x, (int)size.y, offset.x, offset.y, 
			circles.size(), state.name()), 20, 20);
	}

	@Override
	public void dispose() {
		renderer.dispose();
	}
}
