package com.vyorkin.game.core.screens;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

import com.vyorkin.engine.E;
import com.vyorkin.engine.screens.StageScreen;
import com.vyorkin.engine.utils.SetDoneRunnable;

import com.vyorkin.game.core.resources.*;

public class SplashScreen extends StageScreen {
	private Image splashImage;
	
	@Override
	public void show() {
		super.show();
		E.music.play(GameMusic.SPLASH, false);
		Texture texture = E.assets.get(GameTexture.SPLASH, Texture.class);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    
        TextureRegion region = new TextureRegion(texture, 0, 100, 512, 412);
        Drawable drawable = new TextureRegionDrawable(region);
        splashImage = new Image(drawable, Scaling.stretch);
        splashImage.setFillParent(true);

        splashImage.getColor().a = 0f;
		
		splashImage.addAction(Actions.sequence(
			Actions.fadeIn(.5f), 
			Actions.delay(E.preferences.isDeveloperMode() ? 0 : 1), 
			Actions.fadeOut(.5f),
			Actions.run(new SetDoneRunnable(this))
		));
		
		stage.addActor(splashImage);
	}
	
	@Override
	public void load() {
		super.load();
		E.assets.load(GameMusic.SPLASH, Music.class);
		E.assets.load(GameTexture.SPLASH, Texture.class);
	}

	@Override
	protected void unload() {
		super.unload();
		E.assets.unload(GameMusic.SPLASH);
		E.assets.unload(GameTexture.SPLASH);
	}
}
