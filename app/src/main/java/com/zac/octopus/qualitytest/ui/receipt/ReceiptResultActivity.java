package com.zac.octopus.qualitytest.ui.receipt;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.zac.octopus.qualitytest.BaseActivity;
import com.zac.octopus.qualitytest.R;
import com.zac.octopus.qualitytest.model.response.EvaluateResult;

/**
 * Receipt Result
 * Created by Zac on 2016/7/7.
 */
public class ReceiptResultActivity extends BaseActivity {

  public static final String EXTRA_RECEIPT_INFO = "extra_receipt";

  @BindView(R.id.receipt_result_tv_edit_evaluate) TextView mEditEvaluateView;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.receipt_result_tv_company_name) TextView mCompanyNameView;
  @BindView(R.id.receipt_result_tv_date) TextView mDateView;
  @BindView(R.id.receipt_result_tv_classify) TextView mClassifyView;
  @BindView(R.id.receipt_result_tv_carrying_capacity) TextView mCarryingCapacityView;
  @BindView(R.id.receipt_result_tv_extras_lost) TextView mExtrasLostView;
  @BindView(R.id.receipt_result_tv_payment) TextView mPaymentView;
  @BindView(R.id.receipt_result_tv_total_settlement) TextView mTotalSettlementView;
  @BindView(R.id.receipt_result_rb_quality) AppCompatRatingBar mQualityRatingView;
  @BindView(R.id.receipt_result_rb_service) AppCompatRatingBar mServiceRatingView;
  @BindView(R.id.receipt_result_rb_logistics) AppCompatRatingBar mLogisticsRatingView;
  @BindView(R.id.receipt_result_ll_evaluates_container) LinearLayout mEvaluatesContainer;
  private EvaluateResult mEvaluateResult;

  @Override protected int getContentView() {
    return R.layout.activity_receipt_result;
  }

  @Override protected void updateUI() {

    setupActionBar(mToolbar);

    mEvaluateResult = getIntent().getParcelableExtra(EXTRA_RECEIPT_INFO);
    if (mEvaluateResult != null) {
      String receiveTime = mEvaluateResult.getRecivetime();
      if (!TextUtils.isEmpty(receiveTime)) {
        mDateView.setText(receiveTime);
      }
      String classify = mEvaluateResult.getProducttype_one();
      if (!TextUtils.isEmpty(classify)) {
        mClassifyView.setText(classify);
      }
      String carryingCapacity = mEvaluateResult.getRecivetotal();
      if (!TextUtils.isEmpty(carryingCapacity)) {
        carryingCapacity += mEvaluateResult.getUnit();
        mCarryingCapacityView.setText(carryingCapacity);
      }
      String extraLost = mEvaluateResult.getReducenum();
      if (!TextUtils.isEmpty(extraLost)) {
        mExtrasLostView.setText(extraLost);
      }
      String payment = mEvaluateResult.getReducemoney() + "元";
      mPaymentView.setText(payment);
      String price = mEvaluateResult.getTotalmoney() + "元";
      mTotalSettlementView.setText(price);

      boolean isEvaluated = "1".equals(mEvaluateResult.getCommenttrue());
      if (isEvaluated) {
        mEvaluatesContainer.setVisibility(View.VISIBLE);
        mEditEvaluateView.setVisibility(View.INVISIBLE);
        int qualityLevel = Integer.parseInt(mEvaluateResult.getQualitylevel());
        int serviceLevel = Integer.parseInt(mEvaluateResult.getServicelevel());
        int logisticsLevel = Integer.parseInt(mEvaluateResult.getLogisticslevel());
        qualityLevel = Math.abs(qualityLevel - 10);
        mQualityRatingView.setRating(qualityLevel);
        mServiceRatingView.setRating(serviceLevel);
        mLogisticsRatingView.setRating(logisticsLevel);
      } else {
        mEditEvaluateView.setVisibility(View.VISIBLE);
        mEvaluatesContainer.setVisibility(View.INVISIBLE);
      }
    }
  }

  private void setupActionBar(Toolbar toolbar) {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle("质检结果");
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @OnClick(R.id.receipt_result_tv_edit_evaluate) public void onClick() {
    startActivity(
        new Intent(ReceiptResultActivity.this, ReceiptPostEvaluateActivity.class).putExtra(
            ReceiptPostEvaluateActivity.EXTRA_RECEIPT_INFO, mEvaluateResult));
  }
}
