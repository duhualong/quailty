package com.zac.octopus.qualitytest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

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

  private Unbinder unbinder;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    if (getContentView() != 0) {
      mainView = inflater.inflate(getContentView(), container, false);
      unbinder = ButterKnife.bind(this, mainView);

      updateUI();

      return mainView;
    }
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
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
