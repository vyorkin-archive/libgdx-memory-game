package com.vyorkin.game.core.level;

import com.vyorkin.engine.E;
import com.vyorkin.engine.base.Renderer;

public class HudRenderer implements Renderer<Level> {	
	
	// Renders:
	// -----------------------------
	// 1) Season - Number
	// 2) Progress decrease
	// 3) UI: pause/replay buttons
	// 4) Remaining lives (stars)
	// -----------------------------
	
	@Override
	public void render(Level model, float delta) {
		LevelMetadata metadata = model.getMetadata();
		
		E.font.draw(E.batch,
			String.format("Level: %s Lives: %d Errors: %d", 
				metadata.getNumberString(), 
				metadata.lives - model.errors, model.errors), 
			20, 50
		);
	}
}
