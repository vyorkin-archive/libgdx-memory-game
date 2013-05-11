package com.vyorkin.game.core.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.vyorkin.game.core.domain.GameSettings;

public class LevelView {
	private static final int PADDING_DEFAULT = 10;
	
	public final int rows;
	public final int cols;
	
	public final float offsetX;
	public final float offsetY;
	
	public final int cellCount;
	public final float cellSize;
	
	public final int padding;
	
	public final float radius;
	public final float diameter;
	
	public LevelView(GameSettings settings) {
		
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		
		final float aspectRatio = width / height;
		final int sizeMin = settings.getSizeMin();
		
		this.rows = MathUtils.floor(sizeMin * aspectRatio);
		this.cols = sizeMin;
		
		this.cellSize = height / (rows + 1);
		
		this.offsetX = (width - cellSize * cols) / 2;
		this.offsetY = (height - cellSize * rows) / 2;
		
		this.cellCount = (int)rows * (int)cols;
		
		this.diameter = cellSize;
		this.radius = diameter / 2;
		this.padding = PADDING_DEFAULT;
	}
}
