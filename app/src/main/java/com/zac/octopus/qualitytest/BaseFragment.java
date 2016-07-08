package com.zac.octopus.qualitytest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.gson.Gson;
import com.zac.octopus.qualitytest.data.local.PreferencesHelper;
import com.zac.octopus.qualitytest.data.local.dao.UserDao;
import com.zac.octopus.qualitytest.data.remote.WebService;
import com.zac.octopus.qualitytest.di.ActivityContext;
import javax.inject.Inject;
import rx.Subscription;

/**
 * Base features Fragment
 * Created by Zac on 3/16/2016.
 */
public abstract class BaseFragment extends Fragment {

  protected View mainView;

  // 获取子类布局
  protected abstract int getContentView();

  // 更新页面ui
  protected abstract void updateUI();

  @Inject protected PreferencesHelper mPrefsHelper;
  @Inject @ActivityContext protected Context mContext;
  @Inject protected UserDao mUserDao;
  @Inject protected WebService mWebService;
  @Inject protected Gson mGson;

  protected Subscription mSubscription;

  private Unbinder unbinder;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    if (getContentView() != 0) {
      mainView = inflater.inflate(getContentView(), container, false);
      unbinder = ButterKnife.bind(this, mainView);

      ((BaseActivity) getActivity()).getActivityComponent().inject(this);

      updateUI();

      return mainView;
    }
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override public void onStop() {
    super.onStop();
    if (mSubscription != null && !mSubscription.isUnsubscribed()) {
      mSubscription.unsubscribe();
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if (mSubscription != null) {
      mSubscription = null;
    }
    unbinder.unbind();
  }

  /**
   * Add fragment to container
   *
   * @param containerId container id
   * @param fragment fragment to add to container
   */
  protected void addFragment(int containerId, Fragment fragment) {
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    if (getFragmentManager().findFragmentById(containerId) == null) {
      transaction.add(containerId, fragment).commit();
    } else {
      transaction.replace(containerId, fragment).commit();
    }
  }

  /**
   * Back press action in fragment
   */
  protected void onBackPressed() {
    if (getFragmentManager().getBackStackEntryCount() > 0) {
      getFragmentManager().popBackStack();
    } else {
      getActivity().onBackPressed();
    }
  }
}
