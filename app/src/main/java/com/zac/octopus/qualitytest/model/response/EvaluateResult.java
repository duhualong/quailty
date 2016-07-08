package com.zac.octopus.qualitytest.model.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 质检评价列表
 * Created by Zac on 2016/7/7.
 */
public class EvaluateResult implements Parcelable{

  /**
   * length：是否还有未获取数据；true:是 false:否
   * testid：质检单ID
   * testuid：质检员的ID
   * mainorderid：质检单所属的子合同的ID
   * recivetotal：此次配送的总量
   * reducenum：扣杂的数量
   * actugetnum：实际配送的数量
   * reducemoney：扣除的费用
   * price：送货的价格
   * totalmoney：此次配送的总的价值
   * recivetime：质检的时间
   * qualitylevel：质量的评价星级（0-5）
   * servicelevel：服务的评价星级（0-5）
   * logisticslevel：物流配送的评价星级（0-5）
   * commentcontain："评论的内容"
   * commenttrue：质检单是否进行评论
   * 0：未评论 1：已经评论
   * product_size：产品的规格
   * "producttype_one": "",产品的一级分类
   * "producttype_two": "",产品的二级分类
   * "producttype_three": "",产品的三级分类
   * "product_type": "",产品的四级分类
   * "unit": ""产品的单位
   */

  private String testid;
  private String testcompany;
  private String testuid;
  private String mainorderid;
  private String recivetotal;
  private String reducenum;
  private String actugetnum;
  private String reducemoney;
  private String price;
  private String totalmoney;
  private String recivetime;
  private String qualitylevel;
  private String servicelevel;
  private String logisticslevel;
  private String commentcontain;
  private String commenttrue;
  private String product_size;
  private String producttype_one;
  private String producttype_two;
  private String producttype_three;
  private String product_type;
  private String unit;

  protected EvaluateResult(Parcel in) {
    testid = in.readString();
    testcompany = in.readString();
    testuid = in.readString();
    mainorderid = in.readString();
    recivetotal = in.readString();
    reducenum = in.readString();
    actugetnum = in.readString();
    reducemoney = in.readString();
    price = in.readString();
    totalmoney = in.readString();
    recivetime = in.readString();
    qualitylevel = in.readString();
    servicelevel = in.readString();
    logisticslevel = in.readString();
    commentcontain = in.readString();
    commenttrue = in.readString();
    product_size = in.readString();
    producttype_one = in.readString();
    producttype_two = in.readString();
    producttype_three = in.readString();
    product_type = in.readString();
    unit = in.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(testid);
    dest.writeString(testcompany);
    dest.writeString(testuid);
    dest.writeString(mainorderid);
    dest.writeString(recivetotal);
    dest.writeString(reducenum);
    dest.writeString(actugetnum);
    dest.writeString(reducemoney);
    dest.writeString(price);
    dest.writeString(totalmoney);
    dest.writeString(recivetime);
    dest.writeString(qualitylevel);
    dest.writeString(servicelevel);
    dest.writeString(logisticslevel);
    dest.writeString(commentcontain);
    dest.writeString(commenttrue);
    dest.writeString(product_size);
    dest.writeString(producttype_one);
    dest.writeString(producttype_two);
    dest.writeString(producttype_three);
    dest.writeString(product_type);
    dest.writeString(unit);
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<EvaluateResult> CREATOR = new Parcelable.Creator<EvaluateResult>() {
    @Override
    public EvaluateResult createFromParcel(Parcel in) {
      return new EvaluateResult(in);
    }

    @Override
    public EvaluateResult[] newArray(int size) {
      return new EvaluateResult[size];
    }
  };

  public String getTestid() {
    return testid;
  }

  public void setTestid(String testid) {
    this.testid = testid;
  }

  public String getTestcompany() {
    return testcompany;
  }

  public void setTestcompany(String testcompany) {
    this.testcompany = testcompany;
  }

  public String getTestuid() {
    return testuid;
  }

  public void setTestuid(String testuid) {
    this.testuid = testuid;
  }

  public String getMainorderid() {
    return mainorderid;
  }

  public void setMainorderid(String mainorderid) {
    this.mainorderid = mainorderid;
  }

  public String getRecivetotal() {
    return recivetotal;
  }

  public void setRecivetotal(String recivetotal) {
    this.recivetotal = recivetotal;
  }

  public String getReducenum() {
    return reducenum;
  }

  public void setReducenum(String reducenum) {
    this.reducenum = reducenum;
  }

  public String getActugetnum() {
    return actugetnum;
  }

  public void setActugetnum(String actugetnum) {
    this.actugetnum = actugetnum;
  }

  public String getReducemoney() {
    return reducemoney;
  }

  public void setReducemoney(String reducemoney) {
    this.reducemoney = reducemoney;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getTotalmoney() {
    return totalmoney;
  }

  public void setTotalmoney(String totalmoney) {
    this.totalmoney = totalmoney;
  }

  public String getRecivetime() {
    return recivetime;
  }

  public void setRecivetime(String recivetime) {
    this.recivetime = recivetime;
  }

  public String getQualitylevel() {
    return qualitylevel;
  }

  public void setQualitylevel(String qualitylevel) {
    this.qualitylevel = qualitylevel;
  }

  public String getServicelevel() {
    return servicelevel;
  }

  public void setServicelevel(String servicelevel) {
    this.servicelevel = servicelevel;
  }

  public String getLogisticslevel() {
    return logisticslevel;
  }

  public void setLogisticslevel(String logisticslevel) {
    this.logisticslevel = logisticslevel;
  }

  public String getCommentcontain() {
    return commentcontain;
  }

  public void setCommentcontain(String commentcontain) {
    this.commentcontain = commentcontain;
  }

  public String getCommenttrue() {
    return commenttrue;
  }

  public void setCommenttrue(String commenttrue) {
    this.commenttrue = commenttrue;
  }

  public String getProduct_size() {
    return product_size;
  }

  public void setProduct_size(String product_size) {
    this.product_size = product_size;
  }

  public String getProducttype_one() {
    return producttype_one;
  }

  public void setProducttype_one(String producttype_one) {
    this.producttype_one = producttype_one;
  }

  public String getProducttype_two() {
    return producttype_two;
  }

  public void setProducttype_two(String producttype_two) {
    this.producttype_two = producttype_two;
  }

  public String getProducttype_three() {
    return producttype_three;
  }

  public void setProducttype_three(String producttype_three) {
    this.producttype_three = producttype_three;
  }

  public String getProduct_type() {
    return product_type;
  }

  public void setProduct_type(String product_type) {
    this.product_type = product_type;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }
}
