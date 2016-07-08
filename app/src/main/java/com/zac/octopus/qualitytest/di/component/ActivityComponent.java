package com.zac.octopus.qualitytest.di.component;

import android.content.Context;
import com.zac.octopus.qualitytest.BaseActivity;
import com.zac.octopus.qualitytest.BaseFragment;
import com.zac.octopus.qualitytest.di.ActivityContext;
import com.zac.octopus.qualitytest.di.PerActivity;
import com.zac.octopus.qualitytest.di.module.ActivityModule;
import dagger.Component;

/**
 * Activity Component
 * Created by Zac on 2016/6/29.
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
  void inject(BaseFragment fragment);
  @ActivityContext Context context();
}
