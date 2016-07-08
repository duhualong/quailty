package com.zac.octopus.qualitytest.ui.receipt;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import butterknife.BindView;
import com.zac.octopus.qualitytest.BaseFragment;
import com.zac.octopus.qualitytest.R;

/**
 * Receipts Page
 * Created by zac on 5/4/16.
 */
public class ReceiptFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

  public static final int UNEVALUATED_TYPE = 0; // 未评价
  public static final int EVALUATED_TYPE = 1; // 已评价

  @BindView(R.id.receipt_tab_estimate) TabLayout mEstimateTabs;

  @Override protected int getContentView() {
    return R.layout.fragment_page_receipts;
  }

  @Override protected void updateUI() {
    mEstimateTabs.addOnTabSelectedListener(this);
    getFragmentManager().beginTransaction()
        .add(R.id.list_fragment_container, ReceiptEvaluateListFragment.newInstance(UNEVALUATED_TYPE))
        .commit();
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
    Fragment fragment;
    int evaluateType;
    switch (tab.getPosition()) {
      case 0:
        evaluateType = UNEVALUATED_TYPE;
        break;
      case 1:
        evaluateType = EVALUATED_TYPE;
        break;
      default:
        evaluateType = UNEVALUATED_TYPE;
        break;
    }
    fragment = ReceiptEvaluateListFragment.newInstance(evaluateType);
    addFragment(R.id.list_fragment_container, fragment);
  }
}
