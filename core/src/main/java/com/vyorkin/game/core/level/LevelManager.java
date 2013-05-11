package com.vyorkin.game.core.level;

import com.vyorkin.game.core.domain.PlayerProfile;

public class LevelManager {
	public static final int MAX_SEASONS = 3;
	public static final int MAX_NUMBERS = 9;
	
	private final LevelMetadata[][] levels;
	
	public LevelManager(PlayerProfile profile) {
		
		LevelMetadata l = new LevelMetadata(1, 1);
		l.countdownTime = 3;
		l.memorizationTime = 3;
		l.lives = 3;
		
		levels = new LevelMetadata[MAX_SEASONS - 1][MAX_NUMBERS - 1];
		for (int i = 0; i < MAX_SEASONS - 1; i++) {
			for (int j = 0; j < MAX_NUMBERS - 1; j++) {
				levels[i][j] = l;
				levels[i][j].entityCount = i + 1;	// TODO: That is not the case
			}
		}
	}
	
	public LevelMetadata get(int season, int number) {
		// TODO: add arguments validation 
		return levels[season - 1][number - 1];
	}
}
