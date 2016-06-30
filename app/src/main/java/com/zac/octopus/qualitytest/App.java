package com.zac.octopus.qualitytest;

import android.app.Application;

import android.content.Context;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.zac.octopus.qualitytest.di.component.ApplicationComponent;
import com.zac.octopus.qualitytest.di.component.DaggerApplicationComponent;
import com.zac.octopus.qualitytest.di.module.ApplicationModule;

/**
 * Custom Application
 * Created by zac on 4/29/16.
 */
public class App extends Application {

  ApplicationComponent mApplicationComponent;

  @Override public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);

    if (BuildConfig.DEBUG) { // DEBUG 模式
      Stetho.initializeWithDefaults(this);
    }
  }

  public static App get(Context context) {
    return (App) context.getApplicationContext();
  }

  public ApplicationComponent getComponent() {
    if (mApplicationComponent == null) {
      mApplicationComponent = DaggerApplicationComponent.builder()
          .applicationModule(new ApplicationModule(this))
          .build();
    }
    return mApplicationComponent;
  }

}
