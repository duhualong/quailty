package com.zac.octopus.qualitytest.ui.receipt;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Toast;
import butterknife.BindView;
import com.zac.octopus.qualitytest.BaseFragment;
import com.zac.octopus.qualitytest.R;
import com.zac.octopus.qualitytest.adapter.EvaluateListAdapter;
import com.zac.octopus.qualitytest.model.post.EncryptData;
import com.zac.octopus.qualitytest.model.request.EvaluateListRequest;
import com.zac.octopus.qualitytest.model.response.ApiResponse;
import com.zac.octopus.qualitytest.model.response.EvaluateResult;
import com.zac.octopus.qualitytest.ui.widget.DividerItemDecoration;
import com.zac.octopus.qualitytest.util.Constants;
import com.zac.octopus.qualitytest.util.Encrypt;
import com.zac.octopus.qualitytest.util.RxUtils;
import java.util.ArrayList;
import java.util.List;
import rx.Subscriber;

/**
 * 评论列表ui
 * Created by Zac on 2016/7/8.
 */
public class ReceiptEvaluateListFragment extends BaseFragment
    implements SwipeRefreshLayout.OnRefreshListener {

  public static final String EXTRA_EVALUATE_TYPE = "extra_type";

  @BindView(R.id.receipt_rv_receipt_list) RecyclerView mReceiptListView;
  @BindView(R.id.receipt_refresh_switch) SwipeRefreshLayout mRefreshSwitch;

  private int evaluateType; // 评价类别 0：未评论 1：已经评论 默认未评论
  private int offset; // 从哪个位置开始获取数据
  private int limit = 20;// 一次最多获取多少条记录
  private boolean isCanLoadMore; // 是否可以继续加载
  private boolean isPullUp;

  private EvaluateListAdapter mAdapter;
  private LinearLayoutManager mLayoutManager;

  public static ReceiptEvaluateListFragment newInstance(int evaluateType) {
    Bundle args = new Bundle();
    args.putInt(EXTRA_EVALUATE_TYPE, evaluateType);
    ReceiptEvaluateListFragment fragment = new ReceiptEvaluateListFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override protected int getContentView() {
    return R.layout.fragment_receipt_evaluate_list;
  }

  @Override protected void updateUI() {
    evaluateType = getArguments().getInt(EXTRA_EVALUATE_TYPE);

    mRefreshSwitch.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
    mRefreshSwitch.setOnRefreshListener(this);

    mLayoutManager = new LinearLayoutManager(mContext);
    mReceiptListView.setLayoutManager(mLayoutManager);

    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(
        ContextCompat.getDrawable(mContext, R.drawable.divider_horizontal));
    mReceiptListView.addItemDecoration(itemDecoration);
    mAdapter = new EvaluateListAdapter(mContext, new ArrayList<EvaluateResult>());
    mReceiptListView.setAdapter(mAdapter);
    onRefresh();
  }

  /**
   * Implementation of OnRefreshListener interface
   */
  @Override public void onRefresh() {
    if (!isPullUp) { // 下拉刷新清除数据
      mAdapter.clear();
    }
    String userId = mPrefsHelper.getPrefs().getString(Constants.UID, "");
    if (TextUtils.isEmpty(userId)) {
      return;
    }

    String companyCode = mUserDao.getCompanyCodeById(userId);
    EvaluateListRequest
        requestModel = new EvaluateListRequest(evaluateType, companyCode, offset, limit);
    String modelStr = mGson.toJson(requestModel);

    mSubscription = mWebService.getEvaluateList(generateEncryptData(userId, modelStr))
        .compose(RxUtils.<ApiResponse<List<EvaluateResult>>>applySchedulers())
        .subscribe(new Subscriber<ApiResponse<List<EvaluateResult>>>() {
          @Override public void onCompleted() {
            mRefreshSwitch.setRefreshing(false);
            isPullUp = false;
          }

          @Override public void onError(Throwable e) {
            mRefreshSwitch.setRefreshing(false);
            isPullUp = false;
            Toast.makeText(mContext, "请求数据失败,请检查网络连接!", Toast.LENGTH_LONG).show();
          }

          @Override public void onNext(ApiResponse<List<EvaluateResult>> resultApiResponse) {
            if (resultApiResponse.getState() == 200) {
              isCanLoadMore = "true".equals(resultApiResponse.getLength());
              List<EvaluateResult> evaluateList = resultApiResponse.getData();
              if (isPullUp) { // 上拉状态
                mAdapter.addAll(evaluateList);
              } else { // 下滑状态
                mAdapter.setData(evaluateList);
              }
              if (isCanLoadMore) {
                mReceiptListView.addOnScrollListener(getOnScrollListener());
              }
            }
          }
        });
  }

  private RecyclerView.OnScrollListener getOnScrollListener() {
    return new RecyclerView.OnScrollListener() {

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) {
          int visible = mLayoutManager.getChildCount();
          int total = mLayoutManager.getItemCount();
          int past = mLayoutManager.findFirstVisibleItemPosition();
          if ((visible + past) >= total) {
            isPullUp = true;
            offset += limit;
            mRefreshSwitch.setRefreshing(true);
          }
        } else if (dy == 0) {
          offset = 0;
          isPullUp = false;
        }
      }
    };
  }

  @Override public void onStop() {
    super.onStop();
    if (mRefreshSwitch != null) {
      mRefreshSwitch.setRefreshing(false);
    }
  }

  private EncryptData generateEncryptData(String uid, String modelStr) {
    String key = mPrefsHelper.getPrefs().getString(Constants.KKK, "");

    String encryptStr = Encrypt.encrypt(key, modelStr);

    return new EncryptData(uid, encryptStr);
  }
}
