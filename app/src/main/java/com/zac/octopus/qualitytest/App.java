package com.zac.octopus.qualitytest;

import android.app.Activity;
import android.app.Application;

import android.content.Context;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.zac.octopus.qualitytest.di.component.ApplicationComponent;
import com.zac.octopus.qualitytest.di.component.DaggerApplicationComponent;
import com.zac.octopus.qualitytest.di.module.ApplicationModule;
import java.util.Stack;

/**
 * Custom Application
 * Created by zac on 4/29/16.
 */
public class App extends Application {

  ApplicationComponent mApplicationComponent;
  private static Stack<Activity> sActivityStack;
  @Override public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);
    sActivityStack = new Stack<>();
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
  public static void addActivity(Activity activity) {
    if (activity != null) {
      sActivityStack.add(activity);
    }
  }
  public static void clearStack() {
    if (sActivityStack != null && !sActivityStack.isEmpty()) {
      for (Activity activity : sActivityStack) {
        activity.finish();
      }
    }
  }
}
