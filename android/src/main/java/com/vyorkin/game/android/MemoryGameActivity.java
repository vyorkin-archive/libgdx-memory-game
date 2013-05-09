package com.vyorkin.game.android;

import com.vyorkin.game.core.MemoryGame;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import com.vyorkin.engine.GameSettings;

public class MemoryGameActivity extends AndroidApplication {
	
	@Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

       AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
       
       MemoryGame game = new MemoryGame();
       GameSettings settings = game.getSettings();
       
       cfg.useGL20 = settings.useGL20;
       cfg.useAccelerometer = settings.useAccelerometer;
       cfg.useCompass = settings.useCompass;
       cfg.useWakelock = settings.useWakelock;
       cfg.hideStatusBar = settings.hideStatusBar;
       
       initialize(game, cfg);
   }
}
