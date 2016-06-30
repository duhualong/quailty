package com.zac.octopus.qualitytest.model.post;

/**
 * Login data used to post
 * Created by Zac on 2016/6/30.
 */
public class LoginData {
  private String username;
  private String data;

  public LoginData() {
  }

  public LoginData(String username, String data) {
    this.username = username;
    this.data = data;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
