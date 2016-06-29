package com.zac.octopus.qualitytest.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Qualifier;

/**
 * Application Context
 * Created by Zac on 2016/6/29.
 */
@Qualifier @Retention(RetentionPolicy.RUNTIME) public @interface ApplicationContext {
}
