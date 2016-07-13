package com.zac.octopus.qualitytest.model.post;

/**
 * Created by Computer on 2016/7/12.
 */
public class ModifyPwd {

  private String newpwd;

  public ModifyPwd(String newpwd) {

    this.newpwd = newpwd;
  }

  public String getNewpwd() {
    return newpwd;
  }

  public void setNewpwd(String newpwd) {
    this.newpwd = newpwd;
  }
}
