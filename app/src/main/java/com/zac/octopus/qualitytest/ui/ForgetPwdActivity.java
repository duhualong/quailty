package com.zac.octopus.qualitytest.ui;

import butterknife.OnClick;
import com.zac.octopus.qualitytest.BaseActivity;
import com.zac.octopus.qualitytest.R;

/**
 * Created by Computer on 2016/7/13.
 */
public class ForgetPwdActivity extends BaseActivity {
  @Override protected int getContentView() {
    return R.layout.activity_forget_pwd;
  }

  @Override protected void updateUI() {

  }


  @OnClick(R.id.ic_back_left) public void onClick() {
    onBackPressed();
  }
}
