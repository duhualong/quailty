package com.zac.octopus.qualitytest.model.response;

/**
 * Login Response model
 * Created by Zac on 2016/6/30.
 */
public class User {
  private String companylogo;
  private String uid;
  private String blgcompanycode; //质检员所属工地的ID
  private String username;
  private String userphone;
  private String userpwd;
  private String email;
  private Object useravatar;

  public String getCompanylogo() {
    return companylogo;
  }

  public void setCompanylogo(String companylogo) {
    this.companylogo = companylogo;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getBlgcompanycode() {
    return blgcompanycode;
  }

  public void setBlgcompanycode(String blgcompanycode) {
    this.blgcompanycode = blgcompanycode;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUserphone() {
    return userphone;
  }

  public void setUserphone(String userphone) {
    this.userphone = userphone;
  }

  public String getUserpwd() {
    return userpwd;
  }

  public void setUserpwd(String userpwd) {
    this.userpwd = userpwd;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Object getUseravatar() {
    return useravatar;
  }

  public void setUseravatar(Object useravatar) {
    this.useravatar = useravatar;
  }

  @Override public String toString() {
    return "User{" +
        "companylogo='" + companylogo + '\'' +
        ", uid='" + uid + '\'' +
        ", blgcompanycode='" + blgcompanycode + '\'' +
        ", username='" + username + '\'' +
        ", userphone='" + userphone + '\'' +
        ", userpwd='" + userpwd + '\'' +
        ", email='" + email + '\'' +
        ", useravatar=" + useravatar +
        '}';
  }
}
