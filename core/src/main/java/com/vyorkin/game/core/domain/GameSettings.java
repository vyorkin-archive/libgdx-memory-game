package com.vyorkin.game.core.domain;

import com.vyorkin.engine.E;

public class GameSettings {
	private final static int ENTITY_COUNT_MIN = 4;
	private final static int SIZE_MIN = 8;
	
	private final int sizeMin;
	private final int entityCountMin;
	
	public GameSettings() {
		this.sizeMin = E.preferences.get().getInteger("sizeMin", SIZE_MIN);
		this.entityCountMin = E.preferences.get().getInteger("entityCountMin", ENTITY_COUNT_MIN);
	}
	
	public int getSizeMin() {
		return sizeMin;
	}
	
	public int getEntityCountMin() {
		return entityCountMin;
	}
}
