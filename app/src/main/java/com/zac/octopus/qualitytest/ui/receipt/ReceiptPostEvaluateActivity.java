package com.zac.octopus.qualitytest.ui.receipt;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.OnClick;
import com.zac.octopus.qualitytest.BaseActivity;
import com.zac.octopus.qualitytest.R;
import com.zac.octopus.qualitytest.model.post.EncryptData;
import com.zac.octopus.qualitytest.model.request.PostEvaluateRequest;
import com.zac.octopus.qualitytest.model.response.ApiResponse;
import com.zac.octopus.qualitytest.model.response.EvaluateResult;
import com.zac.octopus.qualitytest.util.Constants;
import com.zac.octopus.qualitytest.util.Encrypt;
import com.zac.octopus.qualitytest.util.RxUtils;
import java.util.concurrent.TimeUnit;
import rx.Single;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * 发布评价UI
 * Created by Zac on 2016/7/8.
 */
public class ReceiptPostEvaluateActivity extends BaseActivity {

  public static final String EXTRA_RECEIPT_INFO = "extra_receipt";

  @BindView(R.id.root_layout) View mRootLayout;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.receipt_evaluate_et_evaluate) EditText mEvaluateInput;
  @BindView(R.id.receipt_evaluate_rb_quality) AppCompatRatingBar mQualityRating;
  @BindView(R.id.receipt_evaluate_rb_service) AppCompatRatingBar mServiceRating;
  @BindView(R.id.receipt_evaluate_rb_logistics) AppCompatRatingBar mLogisticsRating;
  @BindView(R.id.progress_bar) ProgressBar mProgressBar;

  private String mUid;
  private String mCompanyCode;
  private String mContractId;
  private String mTesterId;

  @Override protected int getContentView() {
    return R.layout.activity_receipt_evaluate;
  }

  @Override protected void updateUI() {

    setupActionBar(mToolbar);

    EvaluateResult evaluateResult = getIntent().getParcelableExtra(EXTRA_RECEIPT_INFO);
    if (evaluateResult != null) {
      mUid = evaluateResult.getTestuid();
      mTesterId = evaluateResult.getTestid();
      mContractId = evaluateResult.getMainorderid();
      mCompanyCode = evaluateResult.getTestcompany();
    }
  }

  private void setupActionBar(Toolbar toolbar) {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle("发表评价");
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

  @OnClick(R.id.receipt_evaluate_tv_post) public void onClick() {
    boolean isValid = checkValid();

    if (isValid) {
      mProgressBar.setVisibility(View.VISIBLE);

      EncryptData encryptData = generateEncryptData();

      postEvaluate(encryptData);
    }
  }

  private void postEvaluate(EncryptData data) {
    mSubscription = mWebService.postEvaluate(data)
        .compose(RxUtils.<ApiResponse>applySchedulers())
        .subscribe(new Subscriber<ApiResponse>() {
          @Override public void onCompleted() {
            mProgressBar.setVisibility(View.INVISIBLE);
          }

          @Override public void onError(Throwable e) {
            mProgressBar.setVisibility(View.INVISIBLE);
          }

          @Override public void onNext(ApiResponse apiResponse) {
            if (apiResponse.getState() == 200) {
              Snackbar.make(mRootLayout, apiResponse.getMsg(), Snackbar.LENGTH_SHORT).show();

              Single.just("").delay(2, TimeUnit.SECONDS).subscribe(new Action1<String>() {
                @Override public void call(String s) {
                  ReceiptPostEvaluateActivity.this.finish();
                }
              });
            }
          }
        });
  }

  private EncryptData generateEncryptData() {
    int qualityLevel = (int) (mQualityRating.getRating() + 0.5f);
    int serviceLevel = (int) (mServiceRating.getRating() + 0.5f);
    int logisticsLevel = (int) (mLogisticsRating.getRating() + 0.5f);

    String comments = mEvaluateInput.getText().toString().trim();

    PostEvaluateRequest request =
        new PostEvaluateRequest(mTesterId, qualityLevel, serviceLevel, logisticsLevel, comments,
            mContractId, mCompanyCode);

    String requestStr = mGson.toJson(request);

    String key = mPrefsHelper.getPrefs().getString(Constants.KKK, "");
    return new EncryptData(mUid, Encrypt.encrypt(key, requestStr));
  }

  private boolean checkValid() {
    boolean isValid = true;
    if (mQualityRating.getRating() == 0.0f
        || mServiceRating.getRating() == 0.0f
        || mLogisticsRating.getRating() == 0.0f) {
      isValid = false;
      String errorMsg = "您尚有未完成评价条目!";
      Snackbar.make(mRootLayout, errorMsg, Snackbar.LENGTH_SHORT).show();
    }

    if (TextUtils.isEmpty(mEvaluateInput.getText())) {
      isValid = false;
      String errorMsg = "您尚未填写评价!";
      mEvaluateInput.setError(errorMsg);
    }

    return isValid;
  }
}
