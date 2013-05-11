package com.vyorkin.game.core.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import com.vyorkin.engine.base.Updatable;

public abstract class Entity implements Updatable {
	private static final float VELOCITY_MIN = 20;
	private static final float VELOCITY_MAX = 40;
	
	private final int number;
	private final Vector2 cell;
	private final Circle shape;
	private final float velocity;
	
	private boolean moving;
	private boolean marked;
	
	protected Entity(Vector2 cell, Circle shape, int number) {
		this.velocity = MathUtils.random(VELOCITY_MIN, VELOCITY_MAX);
		
		this.marked = false;
		this.moving = false;
		this.number = number;
		this.cell = cell;
		this.shape = shape;
	}
	
	public boolean isClicked(Vector2 position) {
		return !marked && shape.contains(position.x, position.y);
	}
	
	public Circle getShape() {
		return shape;
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	public boolean isMarked() {
		return marked;
	}
	
	public void mark() {
		this.marked = true;
	}
	
	public int getNumber() {
		return number;
	}
	
	public Vector2 getCell() {
		return cell;
	}
	
	public float getVelocity() {
		return velocity;
	}
	
	@Override
	public void update(float delta) {
	}
}
