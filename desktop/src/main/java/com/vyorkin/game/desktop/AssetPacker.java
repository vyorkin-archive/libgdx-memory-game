package com.vyorkin.game.desktop;

import java.io.File;
import java.io.FilenameFilter;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.vyorkin.game.core.resources.GameFolder;

public class AssetPacker {
	private static final FilenameFilter androidFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
          File f = new File(dir, name);
          return 
      		f.isDirectory() && 
      		f.getName().endsWith("android");
        }
      };
	
	public static void main(String[] args) {
        TexturePacker2.Settings s = new TexturePacker2.Settings();
        s.maxHeight = 4096;
        
        File file = new File("..");
        String[] directories = file.list(androidFilter);
        
        // if (directories.length == 0)
        //	throw new BlahblahException(); 
        
        final String androidDir = "../" + directories[0] + "/assets/" + GameFolder.ATLAS;
        
        // TODO: Refactor this so it should process each directory without explicitly specifying it
        
        TexturePacker2.process(s, GameFolder.DATA + "/atlas", androidDir, "loading.pack");
        TexturePacker2.process(s, GameFolder.DATA + "/level", androidDir, "level.pack");
    }
}

