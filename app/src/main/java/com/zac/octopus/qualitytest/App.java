package com.zac.octopus.qualitytest;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Custom Application
 * Created by zac on 4/29/16.
 */
public class App extends Application {

  @Override public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);
  }
}
