package com.zac.octopus.qualitytest.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * A scoping annotation to permit objects with activity lifecycle
 * Created by Zac on 2016/6/29.
 */
@Scope @Retention(RetentionPolicy.RUNTIME) public @interface PerActivity {
}
