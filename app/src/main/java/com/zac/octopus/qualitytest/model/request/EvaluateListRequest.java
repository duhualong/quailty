package com.zac.octopus.qualitytest.model.request;

/**
 * Request Evaluate List Model
 * Created by Zac on 2016/7/7.
 */
public class EvaluateListRequest {

  /**
   * commentstate：质检单是否进行评论 0：未评论 1：已经评论 默认未评论 （O）
   * blgcompanycode：质检员所属的公司的ID （R）
   * startindex：获取数据的开始位置 （R）
   * pagesize：每一次获取数据的数量（O）
   */

  private int commentstate;
  private String blgcompanycode;
  private int startindex;
  private int pagesize;

  public EvaluateListRequest() {
  }

  public EvaluateListRequest(int commentstate, String blgcompanycode, int startindex, int pagesize) {
    this.commentstate = commentstate;
    this.blgcompanycode = blgcompanycode;
    this.startindex = startindex;
    this.pagesize = pagesize;
  }

  public int getCommentstate() {
    return commentstate;
  }

  public void setCommentstate(int commentstate) {
    this.commentstate = commentstate;
  }

  public String getBlgcompanycode() {
    return blgcompanycode;
  }

  public void setBlgcompanycode(String blgcompanycode) {
    this.blgcompanycode = blgcompanycode;
  }

  public int getStartindex() {
    return startindex;
  }

  public void setStartindex(int startindex) {
    this.startindex = startindex;
  }

  public int getPagesize() {
    return pagesize;
  }

  public void setPagesize(int pagesize) {
    this.pagesize = pagesize;
  }
}
