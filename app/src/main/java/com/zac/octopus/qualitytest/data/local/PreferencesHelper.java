package com.zac.octopus.qualitytest.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import com.zac.octopus.qualitytest.di.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * SharedPreferences Helper
 * Created by Zac on 2016/6/29.
 */
@Singleton public class PreferencesHelper {

  public static final String PREF_FILE_NAME = "prefs_file";

  private final SharedPreferences mPrefs;

  @Inject public PreferencesHelper(@ApplicationContext Context context) {
    mPrefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
  }

  public void clear() {
    if (mPrefs != null) {
      mPrefs.edit().clear().apply();
    }
  }
}
