package com.vyorkin.game.core.domain;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.vyorkin.engine.E;

public class BoardGenerator {
	private final Vector2 size;
	private final Vector2 offset;
	private final float radius;
	private final float diameter;
	private final int countMin;
	
	
	public BoardGenerator(Vector2 size, Vector2 offset, float radius) {
		this.size = size;
		this.offset = offset;
		this.radius = radius;
		this.diameter = radius*2;
		this.countMin = E.preferences.get().getInteger("countMin", 4);
	}
	
	public List<CircleItem> generate(int level) {
		int count = countMin + level;
		
		List<Vector2> cells = getRandomCells(count);
		List<CircleItem> result = new ArrayList<CircleItem>(count);
		
		int number = 1;
		for (Vector2 cell : cells) {
			Vector2 pos = getCellPosition(cell);
			
//			Color color = getRandomColor();
			Color color = Color.DARK_GRAY;
			
			Circle shape = new Circle(pos, radius - CircleItem.PADDING);
			result.add(new CircleItem(cell, shape, number++, color));
		}
		return result;
	}
	
	private List<Vector2> getRandomCells(int count) {
		List<Vector2> cells = new ArrayList<Vector2>(count);
		
		while (cells.size() < count) {
			Vector2 cell = getRandomCell();			
			if (!cells.contains(cell))
				cells.add(cell);
		}
		
		return cells;
	}
	
	private Vector2 getRandomCell() {
		return new Vector2(
			(int)MathUtils.random(1, size.x),
			(int)MathUtils.random(1, size.y)
		);
	}
	
	private Vector2 getCellPosition(Vector2 cell) {
		return new Vector2(
			offset.x + cell.x*diameter - radius,
			offset.y + cell.y*diameter - radius
		);
	}
//	
//	private Color getRandomColor() {
//		return new Color(
//			MathUtils.random(0.1f, 1f), 
//			MathUtils.random(0.1f, 1f), 
//			MathUtils.random(0.1f, 1f), 1f
//		);
//	}
}
