package com.zac.octopus.qualitytest.data.remote;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.zac.octopus.qualitytest.model.post.EncryptData;
import com.zac.octopus.qualitytest.model.post.LoginData;
import com.zac.octopus.qualitytest.model.post.ModifyPwd;
import com.zac.octopus.qualitytest.model.response.ApiResponse;
import com.zac.octopus.qualitytest.model.response.EvaluateResult;
import com.zac.octopus.qualitytest.model.response.User;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Single;

/**
 * Web Service
 * Created by Zac on 2016/6/29.
 */
@Singleton public interface WebService {

  String BASE_URL = "http://www.ezhangyu.com/Appapi/Qualitytest/";

  @POST("Login") Single<ApiResponse<User>> login(@Body LoginData loginData);
  //修改密码
  @POST("UpdatePwd")Single<ApiResponse> getModifyPwd(@Body EncryptData data);

  /**
   * 获取质检列表
   *
   * @param data 加密数据
   * @return 质检列表
   */
  @POST("FindQualityList") Single<ApiResponse<List<EvaluateResult>>> getEvaluateList(
      @Body EncryptData data);

  /**
   * 评价质检单
   *
   * @param data 加密数据
   * @return 评价结果
   */
  @POST("CommentQualityTest") Single<ApiResponse> postEvaluate(@Body EncryptData data);

  class Creator {
    @Inject public WebService create() {
      OkHttpClient client =
          new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();

      Retrofit retrofit = new Retrofit.Builder().client(client)
          .client(client)
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .build();
      return retrofit.create(WebService.class);
    }
  }
}
