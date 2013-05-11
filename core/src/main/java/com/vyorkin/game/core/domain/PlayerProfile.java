package com.vyorkin.game.core.domain;

import com.vyorkin.game.core.level.LevelManager;

public class PlayerProfile {
	public int season;
	public int number;
	
	public PlayerProfile() {
		this.season = 1;
		this.number = 1;
	}
	
	public void nextLevel() {
		if (number > LevelManager.MAX_NUMBERS) {
			season++;
			number = 1;
			if (season > LevelManager.MAX_SEASONS)
				season = 1;
		}
		else
			number++;
	}
	
	public void save() {
		
	}
}
