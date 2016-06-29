package com.zac.octopus.qualitytest.di.module;

import android.app.Application;
import android.content.Context;
import com.zac.octopus.qualitytest.data.local.PreferencesHelper;
import com.zac.octopus.qualitytest.data.remote.WebService;
import com.zac.octopus.qualitytest.di.ApplicationContext;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Application Module
 * Created by Zac on 2016/6/29.
 */
@Module public class ApplicationModule {
  protected final Application mApplication;

  public ApplicationModule(@ApplicationContext Application application) {
    mApplication = application;
  }

  @Provides Application provideApplication() {
    return mApplication;
  }

  @Provides @ApplicationContext Context provideContext() {
    return mApplication;
  }

  @Provides @Singleton WebService provideWebService() {
    return new WebService.Creator().create();
  }

  @Provides @Singleton PreferencesHelper providePrefsHelper() {
    return new PreferencesHelper(mApplication);
  }
}
