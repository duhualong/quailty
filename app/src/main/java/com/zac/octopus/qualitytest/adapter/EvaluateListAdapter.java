package com.zac.octopus.qualitytest.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.zac.octopus.qualitytest.R;
import com.zac.octopus.qualitytest.model.response.EvaluateResult;
import com.zac.octopus.qualitytest.ui.receipt.ReceiptPostEvaluateActivity;
import com.zac.octopus.qualitytest.ui.receipt.ReceiptResultActivity;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Receipt Evaluate Adapter
 * Created by Zac on 2016/7/7.
 */
public class EvaluateListAdapter extends RecyclerView.Adapter<EvaluateListAdapter.EvaluateHolder> {

  private Context mContext;
  private List<EvaluateResult> mEvaluateResultList;

  public EvaluateListAdapter(Context context, List<EvaluateResult> evaluateResultList) {
    mContext = context;
    mEvaluateResultList = evaluateResultList;
  }

  @Override public EvaluateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView =
        LayoutInflater.from(mContext).inflate(R.layout.list_item_evaluates, parent, false);
    return new EvaluateHolder(mContext, itemView);
  }

  @Override public void onBindViewHolder(EvaluateHolder holder, int position) {
    EvaluateResult evaluateResult = mEvaluateResultList.get(position);
    if (evaluateResult == null) {
      return;
    }

    holder.setItem(evaluateResult);

    String dateTime = evaluateResult.getRecivetime();
    if (!TextUtils.isEmpty(dateTime)) {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
      try {
        Date date = formatter.parse(dateTime);
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EE", Locale.getDefault());
        String dateStr = formatter.format(date);
        holder.mDateView.setText(dateStr);
      } catch (ParseException e) {
        holder.mDateView.setText(dateTime);
      }
    }
    String companyName = evaluateResult.getTestcompany();
    if (!TextUtils.isEmpty(companyName)) {
      holder.mCompanyNameView.setText(companyName);
    }

    String specific = evaluateResult.getProduct_size();
    if (!TextUtils.isEmpty(specific)) {
      specific = "规格：" + specific;
      holder.mSpecificView.setText(specific);
    }

    String totalReceived = evaluateResult.getRecivetotal();
    String actualReceived = evaluateResult.getActugetnum();
    if (!TextUtils.isEmpty(totalReceived) && !TextUtils.isEmpty(actualReceived)) {
      BigDecimal totalDecimal = new BigDecimal(totalReceived);
      BigDecimal actualDecimal = new BigDecimal(actualReceived);
      String qualifiedRate = actualDecimal.divide(totalDecimal, 2, BigDecimal.ROUND_HALF_UP)
          .multiply(new BigDecimal(100))
          .toString();
      if (!"100.00".equals(qualifiedRate)) {
        holder.mQualifiedRateView.setTextColor(
            ContextCompat.getColor(mContext, R.color.deep_orange));
      }
      qualifiedRate += "%";
      holder.mQualifiedRateView.setText(qualifiedRate);
    }

    boolean isEvaluated = "1".equals(evaluateResult.getCommenttrue());
    if (isEvaluated) {
      holder.mEvaluateBtn.setVisibility(View.GONE);
    }
  }

  @Override public int getItemCount() {
    return mEvaluateResultList.size();
  }

  // 设置数据
  public void setData(List<EvaluateResult> resultList) {
    mEvaluateResultList = resultList;
    notifyDataSetChanged();
  }

  // 添加数据
  public void addAll(List<EvaluateResult> resultList) {
    mEvaluateResultList.addAll(resultList);
    notifyDataSetChanged();
  }

  // 清除数据
  public void clear() {
    mEvaluateResultList.clear();
    notifyDataSetChanged();
  }

  static class EvaluateHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private EvaluateResult mEvaluateResult;

    @BindView(R.id.evaluate_list_item_tv_date) TextView mDateView;
    @BindView(R.id.evaluate_list_item_tv_company) TextView mCompanyNameView;
    @BindView(R.id.evaluate_list_item_tv_specific) TextView mSpecificView;
    @BindView(R.id.evaluate_list_item_tv_qualified_rate) TextView mQualifiedRateView;
    @BindView(R.id.evaluate_list_item_btn_evaluate) Button mEvaluateBtn;

    public EvaluateHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public EvaluateHolder(Context context, View itemView) {
      super(itemView);
      mContext = context;
      ButterKnife.bind(this, itemView);
    }

    public void setItem(EvaluateResult evaluateResult) {
      mEvaluateResult = evaluateResult;
    }

    @OnClick({ R.id.evaluate_list_item_btn_check_result, R.id.evaluate_list_item_btn_evaluate })
    public void onClick(View view) {
      switch (view.getId()) {
        case R.id.evaluate_list_item_btn_check_result:
          mContext.startActivity(new Intent(mContext, ReceiptResultActivity.class).putExtra(
              ReceiptResultActivity.EXTRA_RECEIPT_INFO, mEvaluateResult));
          break;
        case R.id.evaluate_list_item_btn_evaluate:
          mContext.startActivity(new Intent(mContext, ReceiptPostEvaluateActivity.class).putExtra(
            ReceiptPostEvaluateActivity.EXTRA_RECEIPT_INFO, mEvaluateResult));
          break;
      }
    }
  }
}
