package com.zac.octopus.qualitytest;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import com.zac.octopus.qualitytest.ui.receipt.ReceiptFragment;
import com.zac.octopus.qualitytest.ui.personal.PersonalFragment;
import com.zac.octopus.qualitytest.ui.scanner.ScannerFragment;
import java.util.List;

public class MainActivity extends BaseActivity {

  private static final int NAVIGATOR_COUNT = 3;
  private static final String[] titles = {"质检评价","质检","个人中心"};

  int[] navigatorDrawablesNormal = {
      R.drawable.ic_nav_receipt_nor, R.drawable.ic_nav_scan_nor, R.drawable.ic_nav_personal_nor
  };

  int[] navigatorDrawablesSelect = {
      R.drawable.ic_nav_receipt_sel, R.drawable.ic_nav_scan_sel, R.drawable.ic_nav_personal_sel
  };

  @BindViews({ R.id.nav_receipt, R.id.nav_scan, R.id.nav_personal }) List<TextView> navigators;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.page_title) TextView mPageTitleView;
  @BindView(R.id.root_container) RelativeLayout rootView;

  @Override protected int getContentView() {
    return R.layout.activity_main;
  }

  @Override protected void updateUI() {
    setSupportActionBar(mToolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle("");
    }
    getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment_container, new ScannerFragment())
        .commit();
  }

  @OnClick({ R.id.nav_scan, R.id.nav_receipt, R.id.nav_personal })
  public void onNavigatorClick(View view) {
    int pageIndex = 0;
    switch (view.getId()) {
      case R.id.nav_receipt:
        pageIndex = 0;
        break;
      case R.id.nav_scan:
        pageIndex = 1;
        break;
      case R.id.nav_personal:
        pageIndex = 2;
        break;
    }
    setCurrentPager(pageIndex);
    setCurrentNavigator(pageIndex);
  }

  private void setCurrentPager(int index) {
    Fragment fragment = null;
    switch (index) {
      case 0:
        fragment = new ReceiptFragment();
        break;
      case 1:
        fragment = new ScannerFragment();
        break;
      case 2:
        fragment = new PersonalFragment();
        break;
    }
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment_container, fragment)
        .commit();
  }

  private void refreshNavigator() {
    for (int i = 0; i < NAVIGATOR_COUNT; i++) {
      navigators.get(i)
          .setCompoundDrawablesWithIntrinsicBounds(0, navigatorDrawablesNormal[i], 0, 0);
      navigators.get(i).setTextColor(ContextCompat.getColor(MainActivity.this, R.color.gray));
    }
  }

  private void setCurrentNavigator(int index) {
    refreshNavigator();
    navigators.get(index)
        .setCompoundDrawablesWithIntrinsicBounds(0, navigatorDrawablesSelect[index], 0, 0);
    if (index != 1) {
      navigators.get(index).setTextColor(ContextCompat.getColor(MainActivity.this, R.color.blue));
    }
    mPageTitleView.setText(titles[index]);
  }

  public View getRootView() {
    return rootView;
  }
}
