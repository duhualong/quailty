package com.zac.octopus.qualitytest.ui.personal;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.OnClick;
import com.zac.octopus.qualitytest.App;
import com.zac.octopus.qualitytest.BaseActivity;
import com.zac.octopus.qualitytest.R;
import com.zac.octopus.qualitytest.ui.LoginActivity;

public class PersonalSettingActivity extends BaseActivity {
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.modify_pwd) RelativeLayout modifyPwd;
  @BindView(R.id.btn_login) Button btnLogin;

  @Override protected int getContentView() {
    return R.layout.activity_setting;
  }

  @Override protected void updateUI() {
    setupActionBar();
  }

  private void setupActionBar() {
    setSupportActionBar(mToolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setTitle("设置");
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

  @OnClick({ R.id.modify_pwd, R.id.btn_login}) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.modify_pwd:
        startActivity(new Intent(PersonalSettingActivity.this, ModifyPwdActivity.class));
        break;
      case R.id.btn_login:
        loginOut();
        break;
    }
  }

  //退出登录
  private void loginOut() {
    AlertDialog dialog = new AlertDialog.Builder(PersonalSettingActivity.this).setMessage("是否退出登录?")
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
            startActivity(new Intent(PersonalSettingActivity.this, LoginActivity.class));
            mPrefsHelper.clear();
            App.clearStack();
          }
        })
        .setNegativeButton("取消", null)
        .setCancelable(true)
        .create();
    dialog.show();
  }
}