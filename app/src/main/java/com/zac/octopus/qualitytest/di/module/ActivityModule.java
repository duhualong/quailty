package com.zac.octopus.qualitytest.di.module;

import android.app.Activity;
import android.content.Context;
import com.zac.octopus.qualitytest.di.ActivityContext;
import dagger.Module;
import dagger.Provides;

/**
 * Activity Module
 * Created by Zac on 2016/6/29.
 */
@Module public class ActivityModule {

  private Activity mActivity;

  public ActivityModule(Activity activity) {
    mActivity = activity;
  }

  @Provides Activity provideActivity() {
    return mActivity;
  }

  @Provides @ActivityContext Context provideContext() {
    return mActivity;
  }
}
