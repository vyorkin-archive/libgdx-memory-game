package com.vyorkin.game.core.level;

public class LevelMetadata {
	public int season;
	public int number;
	public boolean locked;
	
	public int entityCount;
	public int lives;
	public int memorizationTime;
	public int countdownTime;
	
	// + some kind of score multiplier ?
	
	public LevelMetadata(int season, int number) {
		this.season = season;
		this.number = number;
	}
	
	public String getNumberString() {
		return season + "-" + number;
	}
}
