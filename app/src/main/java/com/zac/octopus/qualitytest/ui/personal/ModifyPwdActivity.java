package com.zac.octopus.qualitytest.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.OnClick;
import com.zac.octopus.qualitytest.App;
import com.zac.octopus.qualitytest.BaseActivity;
import com.zac.octopus.qualitytest.R;
import com.zac.octopus.qualitytest.model.post.EncryptData;
import com.zac.octopus.qualitytest.model.post.ModifyPwd;
import com.zac.octopus.qualitytest.model.response.ApiResponse;
import com.zac.octopus.qualitytest.ui.LoginActivity;
import com.zac.octopus.qualitytest.util.Constants;
import com.zac.octopus.qualitytest.util.Encrypt;
import com.zac.octopus.qualitytest.util.RxUtils;
import java.util.concurrent.TimeUnit;
import rx.Single;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Computer on 2016/7/12.
 */
public class ModifyPwdActivity extends BaseActivity {
  @BindView(R.id.ic_back_left) ImageView icBackLeft;
  @BindView(R.id.modify_pwd_old) EditText modifyPwdOld;
  @BindView(R.id.modify_pwd_new) EditText modifyPwdNew;
  @BindView(R.id.modify_re_pwd) EditText modifyRePwd;
  @BindView(R.id.root_layout) LinearLayout mRootLayout;
  @BindView(R.id.progress_bar) ProgressBar progressBar;

  @Override protected int getContentView() {
    return R.layout.activity_modify_pwd;
  }

  @Override protected void updateUI() {

  }

  @OnClick({ R.id.ic_back_left, R.id.btn_confirm }) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.ic_back_left:
        onBackPressed();
        break;
      case R.id.btn_confirm:
        String pwdOld = modifyPwdOld.getText().toString().trim();
        String pwdNew = modifyPwdNew.getText().toString().trim();
        String pwdRe = modifyRePwd.getText().toString().trim();
        boolean isValid = checkOldPwdValid(pwdOld);
        if (isValid) {
          boolean checkPwd = checkPwdValid(pwdNew, pwdRe);
          if (checkPwd) {
            String uid = mPrefsHelper.getPrefs().getString(Constants.UID, "");
            modifyPwd(uid, pwdNew);

          }
        }

        break;
    }
  }

  private void modifyPwd(String uid, String password) {
    progressBar.setVisibility(View.VISIBLE);
    ModifyPwd modifyPwd=new ModifyPwd(password);
    String modelStr = mGson.toJson(modifyPwd);
    mSubscription =
        mWebService.getModifyPwd(generateEncryptData(uid,modelStr)).compose(RxUtils.<ApiResponse>applySchedulers())
        .subscribe(new Subscriber<ApiResponse>() {
          @Override public void onCompleted() {
            progressBar.setVisibility(View.INVISIBLE);
          }

          @Override public void onError(Throwable e) {
            progressBar.setVisibility(View.INVISIBLE);
                Snackbar.make(mRootLayout, R.string.modify_error, Snackbar.LENGTH_SHORT).show();
              }
              @Override public void onNext(ApiResponse apiResponse) {
                if (apiResponse.getState()==200){
                  Snackbar.make(mRootLayout,"修改成功，请重新登录！", Snackbar.LENGTH_SHORT).show();
                  Single.just("").delay(1, TimeUnit.SECONDS).subscribe(new Action1<String>() {
                    @Override public void call(String s) {
                      startActivity(new Intent(ModifyPwdActivity.this, LoginActivity.class));
                      mPrefsHelper.clear();
                      App.clearStack();
                    }
                  });
                }else {
                  Snackbar.make(mRootLayout,apiResponse.getMsg(), Snackbar.LENGTH_SHORT).show();
                }

              }
            });
  }

  //检验旧密码
  private boolean checkOldPwdValid(String pwd) { // Simplest implementation
    boolean result = true;
    if (TextUtils.isEmpty(pwd)) {
      Snackbar.make(mRootLayout, "旧密码不能为空", Snackbar.LENGTH_SHORT).show();
      result = false;
    } else {
      String key = Encrypt.md5(pwd).toUpperCase();
      String keys = mPrefsHelper.getPrefs().getString(Constants.KKK, "");
      System.out.println("这个key的值是什么" + keys);
      if (key.equals(keys)) {
        result = true;
      } else {
        result = false;
        modifyPwdOld.setText("");
        Snackbar.make(mRootLayout, "输入的旧密码不正确", Snackbar.LENGTH_SHORT).show();
      }
    }
    return result;
  }

  //检验新密码和确认密码
  private boolean checkPwdValid(String pwd, String rePwd) {
    boolean result = true;
    if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(rePwd)) {
      result = false;
      Snackbar.make(mRootLayout, "新密码和确认密码不能为空", Snackbar.LENGTH_SHORT).show();
    } else if (pwd.length() < 6 || pwd.length() > 20) {
      result = false;
      Snackbar.make(mRootLayout, "新密码长度必须在6~20位之间", Snackbar.LENGTH_SHORT).show();
    } else if (!pwd.equals(rePwd)) {
      result = false;
      modifyRePwd.setText("");
      Snackbar.make(mRootLayout, "两次输入的密码不一致", Snackbar.LENGTH_SHORT).show();
    }
    return result;
  }

  //新密码加密
  private EncryptData generateEncryptData(String uid, String modelStr) {
    String key = mPrefsHelper.getPrefs().getString(Constants.KKK, "");
    String encryptStr =Encrypt.encrypt(key, modelStr);
    return new EncryptData(uid, encryptStr);
  }
}
