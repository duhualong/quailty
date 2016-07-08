package com.zac.octopus.qualitytest.model.response;

/**
 * Api Response model
 * Created by Zac on 2016/6/30.
 */
public class ApiResponse<T> {
  private int state;
  private String msg;
  private String length; //是否还有未获取数据；true:是 false:否
  private T data;

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getLength() {
    return length;
  }

  public void setLength(String length) {
    this.length = length;
  }
}
