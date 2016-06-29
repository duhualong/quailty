package com.zac.octopus.qualitytest;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.zac.octopus.qualitytest.di.component.ActivityComponent;
import com.zac.octopus.qualitytest.di.component.DaggerActivityComponent;
import com.zac.octopus.qualitytest.di.module.ActivityModule;

/**
 * Base Activity
 * Created by zac on 12/16/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

  protected abstract int getContentView();
  protected abstract void updateUI();

  @BindView(R.id.root_layout) View mRootLayout;

  private ActivityComponent mActivityComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getContentView() != 0) {
      setContentView(getContentView());
    }
    ButterKnife.bind(this);
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

  /**
   * Show SnackBar Message
   * @param msg message to show on the SnackBar
   */
  protected void showSnackBar(String msg) {
    Snackbar.make(mRootLayout, msg, Snackbar.LENGTH_LONG).show();
  }

  //public ActivityComponent getActivityComponent() {
  //  if (mActivityComponent == null) {
  //    mActivityComponent = DaggerActivityComponent.builder()
  //        .activityModule(new ActivityModule(this))
  //  }
  //}

}
