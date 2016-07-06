package com.zac.octopus.qualitytest.di.component;

import android.app.Application;
import android.content.Context;
import com.zac.octopus.qualitytest.data.local.PreferencesHelper;
import com.zac.octopus.qualitytest.data.local.dao.UserDao;
import com.zac.octopus.qualitytest.data.remote.WebService;
import com.zac.octopus.qualitytest.di.ApplicationContext;
import com.zac.octopus.qualitytest.di.module.ApplicationModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Application Component
 * Created by Zac on 2016/6/29.
 */
@Singleton @Component(modules = ApplicationModule.class) public interface ApplicationComponent {

  Application application();

  @ApplicationContext Context context();

  PreferencesHelper prefsHelper();

  WebService webService();

  UserDao userDao();
}
