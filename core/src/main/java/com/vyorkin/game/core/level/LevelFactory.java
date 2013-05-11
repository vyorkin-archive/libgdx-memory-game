package com.vyorkin.game.core.level;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.vyorkin.game.core.entities.Entity;
import com.vyorkin.game.core.entities.EntityFactory;

public class LevelFactory {
	
	private final EntityFactory entityFactory;
	private final LevelView view;
	
	public LevelFactory(LevelView view) {
		this.entityFactory = new EntityFactory();
		this.view = view;
	}
	
	public Level create(LevelMetadata metadata) {
		List<Vector2> cells = createCells(metadata.entityCount);
		List<Entity> entities = new ArrayList<Entity>(cells.size());
		
		int index = 1;
		for (Vector2 cell : cells) {
			Circle shape = createCircle(cell);
			entities.add(entityFactory.create(cell, shape, index++));
		}
		
		return new Level(metadata, entities);
	}
	
	public Circle createCircle(Vector2 cell) {
		return new Circle(getPosition(cell), view.radius - view.padding);
	}
	
	public List<Vector2> createCells(int count) {
		List<Vector2> cells = new ArrayList<Vector2>(count);
		
		while (cells.size() < count) {
			Vector2 cell = createCell();			
			if (!cells.contains(cell))
				cells.add(cell);
		}
		
		return cells;
	}
	
	private Vector2 createCell() {
		return new Vector2(
			(int)MathUtils.random(1, view.cols),
			(int)MathUtils.random(1, view.rows)
		);
	}
	
	public Vector2 getPosition(Vector2 cell) {
		return new Vector2(
			view.offsetX + cell.x*view.diameter - view.radius,
			view.offsetY + cell.y*view.diameter - view.radius
		);
	}
}
