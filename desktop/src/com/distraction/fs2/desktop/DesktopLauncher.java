package com.distraction.fs2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.distraction.fs2.Constants;
import com.distraction.fs2.FlippySlime2;

class DesktopLauncher {
   public static void main(String[] args) {
      LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
      config.forceExit = false;
      config.title = Constants.TITLE;
      config.width = Constants.DESKTOP_WIDTH;
      config.height = Constants.DESKTOP_HEIGHT;
      new LwjglApplication(new FlippySlime2(), config);
   }
}
