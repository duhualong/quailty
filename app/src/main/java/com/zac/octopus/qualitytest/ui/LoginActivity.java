package com.zac.octopus.qualitytest.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.OnClick;
import com.zac.octopus.qualitytest.MainActivity;
import com.zac.octopus.qualitytest.R;
import com.zac.octopus.qualitytest.databinding.ActivityLoginBinding;
import com.zac.octopus.qualitytest.viewmodel.LoginViewModel;

/**
 * Login Activity
 * Created by Zac on 2016/6/30.
 */
public class LoginActivity extends AppCompatActivity implements LoginViewModel.DataChangedListener {
  private LoginViewModel mViewModel;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    mViewModel = new LoginViewModel(this, this);
    binding.setViewModel(mViewModel);
    mViewModel.setDataChangedListener(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mViewModel.destroy();
  }

  @OnClick(R.id.login_tv_forget_passwd) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.login_tv_forget_passwd: // TODO forget password ui.
        break;
    }
  }

  @Override public void onLoginListener(boolean isLoginSuccess) {
    if (isLoginSuccess) {
      startActivity(new Intent(this, MainActivity.class));
    }
  }
}
