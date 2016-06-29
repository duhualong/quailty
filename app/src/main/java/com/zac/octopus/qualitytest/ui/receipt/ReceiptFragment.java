package com.zac.octopus.qualitytest.ui.receipt;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import butterknife.BindView;
import com.zac.octopus.qualitytest.BaseFragment;
import com.zac.octopus.qualitytest.R;

/**
 * Receipts Page
 * Created by zac on 5/4/16.
 */
public class ReceiptFragment extends BaseFragment
    implements SwipeRefreshLayout.OnRefreshListener, TabLayout.OnTabSelectedListener {

  private static final String TAG = "ReceiptFragment";

  @BindView(R.id.receipt_tab_estimate) TabLayout mEstimateTabs;
  @BindView(R.id.receipt_rv_receipt_list) RecyclerView mReceiptListView;
  @BindView(R.id.receipt_refresh_switch) SwipeRefreshLayout mRefreshSwitch;

  @Override protected int getContentView() {
    return R.layout.fragment_page_receipts;
  }

  @Override protected void updateUI() {
    mEstimateTabs.setOnTabSelectedListener(this);
    mRefreshSwitch.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
    mRefreshSwitch.setOnRefreshListener(this);
  }

  /**
   * Implementation of OnRefreshListener interface
   */
  @Override public void onRefresh() {

  }

  /**
   * Implementation of Tab Selected Listener
   *
   * @param tab tab that be selected
   */
  @Override public void onTabUnselected(TabLayout.Tab tab) {
  }

  @Override public void onTabReselected(TabLayout.Tab tab) {
  }

  @Override public void onTabSelected(TabLayout.Tab tab) {
    switch (tab.getPosition()) {
      case 0:
        Log.d(TAG, "onTabSelected: fuck");
        break;
      case 1:
        Log.d(TAG, "onTabSelected: you");
        break;
    }
  }
}
