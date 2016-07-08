package com.zac.octopus.qualitytest.model.post;

/**
 * Encrypt Data Model
 * Created by Zac on 2016/7/7.
 */
public class EncryptData {
  private String uid;
  private String data;

  public EncryptData(String uid, String data) {
    this.uid = uid;
    this.data = data;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
