package com.vyorkin.game.core.level;

import java.util.List;

import com.vyorkin.game.core.entities.Entity;

public class Level {
	private final LevelMetadata metadata;
	
	private List<Entity> entities;
	private LevelState state;
	private LevelResult result;
	
	public int index;
	public int errors;
	
	public Level(LevelMetadata metadata, List<Entity> entities) {
		this.metadata = metadata;
		this.entities = entities;
		
		index = 1;
		errors = 0;
		state = LevelState.Countdown;
		result = new LevelResult();
	}
	
	public LevelResult getResult() {
		return result;
	}
	
	public LevelMetadata getMetadata() {
		return metadata;
	}
	
	public LevelState getState() {
		return state;
	}
	
	public void setState(LevelState state) {
		this.state = state;
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public void updateState() {
		if (errors > metadata.lives) {
			state = LevelState.Lose;
		} else if (index > entities.size() + 1) {
			state = LevelState.Win;
		}
	}
	
	public void nextState() {
		if (state == LevelState.Countdown) {
			state = LevelState.Memorization;
		} else if (state == LevelState.Memorization) {
			state = LevelState.Playing;
		}
	}
	
	public boolean isDone() {
		return 
			state == LevelState.Win || 
			state == LevelState.Lose;
	}
}
