package com.vyorkin.game.core.domain;

public class Level {
	public int errors;
	
	private int number;
	private LevelState state;
	
	public Level(int number) {
		this.errors = 0;
		this.number = number;
		this.state = LevelState.Countdown;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void next() {
		errors = 0;
		if (state == LevelState.Win) {
			number++;
		} else if (state == LevelState.Lose) {
			number--;
		}
	}
	
	public boolean isDone() {
		return state == LevelState.Win || state == LevelState.Lose;
	}
	
	public LevelState getState() {
		return state;
	}
	
	public void setState(LevelState state) {
		this.state = state;
	}
	
	public void nextState() {
		if (state == LevelState.Countdown) {
			state = LevelState.Memorization;
		} else if (state == LevelState.Memorization) {
			state = LevelState.Playing;
		}
	}
}
