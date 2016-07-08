package com.zac.octopus.qualitytest.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.OnClick;
import com.google.gson.JsonObject;
import com.zac.octopus.qualitytest.BaseActivity;
import com.zac.octopus.qualitytest.MainActivity;
import com.zac.octopus.qualitytest.R;
import com.zac.octopus.qualitytest.model.post.LoginData;
import com.zac.octopus.qualitytest.model.response.ApiResponse;
import com.zac.octopus.qualitytest.model.response.User;
import com.zac.octopus.qualitytest.util.Constants;
import com.zac.octopus.qualitytest.util.Encrypt;
import com.zac.octopus.qualitytest.util.RxUtils;
import rx.Subscriber;

/**
 * Login Activity
 * Created by Zac on 2016/6/30.
 */
public class LoginActivity extends BaseActivity {

  @BindView(R.id.login_et_username) EditText mUsernameInput;
  @BindView(R.id.login_et_passwd) EditText mPasswdInput;
  @BindView(R.id.progress_bar) ProgressBar mProgressBar;
  @BindView(R.id.root_layout) LinearLayout mRootLayout;

  @Override protected int getContentView() {
    return R.layout.activity_login;
  }

  @Override protected void updateUI() {

  }

  @OnClick({ R.id.btn_login, R.id.login_tv_forget_passwd }) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_login:
        String username = mUsernameInput.getText().toString().trim();
        String password = mPasswdInput.getText().toString().trim();
        boolean isValid = checkInputValid(username, password);
        if (isValid) {
          login(username, password);
        }
        break;
      case R.id.login_tv_forget_passwd:
        break;
    }
  }

  private void login(final String username, String password) {
    mProgressBar.setVisibility(View.VISIBLE);
    String data = generateLoginData(password);
    LoginData loginData = new LoginData(username, data);
    mSubscription = mWebService.login(loginData)
        .compose(RxUtils.<ApiResponse<User>>applySchedulers())
        .subscribe(new Subscriber<ApiResponse<User>>() {
          @Override public void onCompleted() {
            mProgressBar.setVisibility(View.INVISIBLE);
          }

          @Override public void onError(Throwable e) {
            mProgressBar.setVisibility(View.INVISIBLE);
            Snackbar.make(mRootLayout, R.string.login_error, Snackbar.LENGTH_SHORT).show();
          }

          @Override public void onNext(ApiResponse<User> response) {
            if (response.getState() == 200) {
              User user = response.getData();
              mUserDao.initUserData(user);
              mPrefsHelper.getPrefs()
                  .edit()
                  .putString(Constants.UID, user.getUid())
                  .putString(Constants.KKK, user.getUserpwd())
                  .apply();
              startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
          }
        });
  }

  private boolean checkInputValid(String username, String passwd) { // Simplest implementation
    boolean result = true;
    if (TextUtils.isEmpty(username)) {
      mUsernameInput.setText("账号不允许为空！");
      result = false;
    }

    if (TextUtils.isEmpty(passwd)) {
      mPasswdInput.setText("密码不允许为空！");
      result = false;
    }
    return result;
  }

  private String generateLoginData(String text) {
    String key = Encrypt.md5(text).toUpperCase();
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("userpwd", key);
    return Encrypt.encrypt(key, jsonObject.toString());
  }
}
