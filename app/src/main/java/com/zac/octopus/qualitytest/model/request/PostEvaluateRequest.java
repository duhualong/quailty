package com.zac.octopus.qualitytest.model.request;

/**
 * 发布评价页面
 * Created by Zac on 2016/7/8.
 */
public class PostEvaluateRequest {

  /**
   * testid : 质检单ID e.g Q57726619BACEC
   * qualitylevel : 质量的评价星级 e.g 0-5
   * commentcontain : 评价的信息内容
   * servicelevel : 服务的评价星级
   * contractid : 子合同的ID e.g Z20160628164032851501
   * blgcompanycode : 质检员所属的公司的ID C574F94F45DDCE0712
   */

  private String testid;
  private int qualitylevel;
  private String commentcontain;
  private int servicelevel;
  private int logisticslevel;
  private String contractid;
  private String blgcompanycode;

  public PostEvaluateRequest() {

  }

  public PostEvaluateRequest(String testid, int qualitylevel, int servicelevel, int logisticslevel,
      String commentcontain, String contractid, String blgcompanycode) {
    this.testid = testid;
    this.qualitylevel = qualitylevel;
    this.commentcontain = commentcontain;
    this.servicelevel = servicelevel;
    this.logisticslevel = logisticslevel;
    this.contractid = contractid;
    this.blgcompanycode = blgcompanycode;
  }

  public String getTestid() {
    return testid;
  }

  public void setTestid(String testid) {
    this.testid = testid;
  }

  public int getQualitylevel() {
    return qualitylevel;
  }

  public void setQualitylevel(int qualitylevel) {
    this.qualitylevel = qualitylevel;
  }

  public String getCommentcontain() {
    return commentcontain;
  }

  public void setCommentcontain(String commentcontain) {
    this.commentcontain = commentcontain;
  }

  public int getServicelevel() {
    return servicelevel;
  }

  public void setServicelevel(int servicelevel) {
    this.servicelevel = servicelevel;
  }

  public String getContractid() {
    return contractid;
  }

  public void setContractid(String contractid) {
    this.contractid = contractid;
  }

  public String getBlgcompanycode() {
    return blgcompanycode;
  }

  public void setBlgcompanycode(String blgcompanycode) {
    this.blgcompanycode = blgcompanycode;
  }

  @Override public String toString() {
    return "PostEvaluateRequest{" +
        "testid='" + testid + '\'' +
        ", qualitylevel=" + qualitylevel +
        ", commentcontain='" + commentcontain + '\'' +
        ", servicelevel=" + servicelevel +
        ", logisticslevel=" + logisticslevel +
        ", contractid='" + contractid + '\'' +
        ", blgcompanycode='" + blgcompanycode + '\'' +
        '}';
  }
}
