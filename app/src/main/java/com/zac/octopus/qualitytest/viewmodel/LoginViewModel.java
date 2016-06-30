package com.zac.octopus.qualitytest.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import com.google.gson.JsonObject;
import com.zac.octopus.qualitytest.App;
import com.zac.octopus.qualitytest.R;
import com.zac.octopus.qualitytest.data.local.dao.UserDao;
import com.zac.octopus.qualitytest.data.remote.WebService;
import com.zac.octopus.qualitytest.model.post.LoginData;
import com.zac.octopus.qualitytest.model.response.ApiResponse;
import com.zac.octopus.qualitytest.model.response.User;
import com.zac.octopus.qualitytest.util.Encrypt;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Login View Model
 * Created by Zac on 2016/6/30.
 */
public class LoginViewModel implements ViewModel {
  public ObservableInt progressVisibility;
  public ObservableField<String> username;
  public ObservableField<String> password;
  public ObservableField<String> usernameError;
  public ObservableField<String> passwordError;

  private Context mContext;
  private Subscription mSubscription;
  private DataChangedListener mDataListener;

  @Inject WebService mWebService;
  @Inject UserDao mUserDao;

  public LoginViewModel(Context context, DataChangedListener dataListener) {
    mContext = context;
    mDataListener = dataListener;
    progressVisibility = new ObservableInt(View.INVISIBLE);
    username = new ObservableField<>();
    password = new ObservableField<>();
    usernameError = new ObservableField<>();
    passwordError = new ObservableField<>();

    App.get(mContext).getComponent().inject(this);
  }

  public void setDataChangedListener(DataChangedListener listener) {
    mDataListener = listener;
  }

  public void onClickLogin(View view) {
    boolean isValid = checkInputValid();
    if (isValid) {
      login(username.get(), password.get());
    }
  }

  public TextWatcher getUsernameInputWatcher() {
    return new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        clearError();
      }

      @Override public void afterTextChanged(Editable editable) {
      }
    };
  }

  private void clearError() {
    usernameError.set(null);
    passwordError.set(null);
  }

  private void setLoginError(String errorMsg) {
    usernameError.set(errorMsg);
  }

  private boolean checkInputValid() { // Simplest implementation
    boolean result = true;
    if (TextUtils.isEmpty(username.get())) {
      usernameError.set("账号不允许为空！");
      result = false;
    }

    if (TextUtils.isEmpty(password.get())) {
      passwordError.set("密码不允许为空！");
      result = false;
    }
    return result;
  }

  private void login(String username, String password) {
    progressVisibility.set(View.VISIBLE);
    String data = generateLoginData(password);
    LoginData loginData = new LoginData(username, data);
    mSubscription = mWebService.login(loginData)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<ApiResponse<User>>() {
          @Override public void onCompleted() {
            progressVisibility.set(View.GONE);
          }

          @Override public void onError(Throwable e) {
            progressVisibility.set(View.GONE);
            setLoginError(mContext.getString(R.string.login_error));
            mDataListener.onLoginListener(false);
          }

          @Override public void onNext(ApiResponse<User> response) {
            if (response.getState() == 200) {
              mDataListener.onLoginListener(true);
              // TODO initUserData
              User user = response.getData();
              mUserDao.initUserData(user);
            } else {
              setLoginError(response.getMsg());
              mDataListener.onLoginListener(false);
            }
          }
        });
  }

  private String generateLoginData(String text) {
    String key = Encrypt.md5(text).toUpperCase();
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("userpwd", key);
    return Encrypt.encrypt(key, jsonObject.toString());
  }

  @Override public void destroy() {
    this.mContext = null;
    if (mSubscription != null && !mSubscription.isUnsubscribed()) {
      mSubscription.unsubscribe();
    }
  }

  public interface DataChangedListener {
    void onLoginListener(boolean isLoginSuccess);
  }
}
