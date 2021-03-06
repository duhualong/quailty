package com.zac.octopus.qualitytest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.zac.octopus.qualitytest.data.local.PreferencesHelper;
import com.zac.octopus.qualitytest.data.local.dao.UserDao;
import com.zac.octopus.qualitytest.data.remote.WebService;
import com.zac.octopus.qualitytest.di.component.ActivityComponent;
import com.zac.octopus.qualitytest.di.component.DaggerActivityComponent;
import com.zac.octopus.qualitytest.di.module.ActivityModule;
import javax.inject.Inject;
import rx.Subscription;

/**
 * Base Activity
 * Created by zac on 12/16/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

  protected abstract int getContentView();
  protected abstract void updateUI();

  @Inject protected PreferencesHelper mPrefsHelper;
  @Inject protected WebService mWebService;
  @Inject protected UserDao mUserDao;
  @Inject protected Gson mGson;

  private ActivityComponent mActivityComponent;
  protected Subscription mSubscription;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getContentView() != 0) {
      setContentView(getContentView());
    }

    App.get(this).getComponent().inject(this);

    ButterKnife.bind(this);
    App.addActivity(this);
    updateUI();
  }

  /**
   * 点击空白区域自动隐藏软键盘
   */
  @Override public boolean onTouchEvent(MotionEvent event) {
    InputMethodManager inputMgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    if (inputMgr.isActive() && getCurrentFocus() != null) {
      if (getCurrentFocus().getWindowToken() != null) {
        inputMgr.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS);
      }
    }
    return super.onTouchEvent(event);
  }

  @Override protected void onStop() {
    super.onStop();
    if (mSubscription != null && !mSubscription.isUnsubscribed()) {
      mSubscription.unsubscribe();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (mSubscription != null) {
      mSubscription = null;
    }
  }

  /**
   * Add fragment to activity container
   *
   * @param containerId container id
   * @param fragment fragment to add to activity.
   */
  protected void addFragment(int containerId, Fragment fragment) {
    getSupportFragmentManager().beginTransaction().add(containerId, fragment).commit();
  }

  /**
   * Fix fragment addToBackStack() not working
   */
  @Override public void onBackPressed() {
    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
      getSupportFragmentManager().popBackStack();
    } else {
      super.onBackPressed();
    }
  }

  /**
   * Show Toast
   * @param msg show message in the toast
   */
  protected void showToast(String msg) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  }

  public ActivityComponent getActivityComponent() {
    if (mActivityComponent == null) {
      mActivityComponent = DaggerActivityComponent.builder()
          .activityModule(new ActivityModule(this))
          .applicationComponent(App.get(this).getComponent())
          .build();
    }
    return mActivityComponent;
  }
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event){
    if(KeyEvent.KEYCODE_BACK==keyCode)
      return false ;
    return super.onKeyDown(keyCode, event);
  }
}
