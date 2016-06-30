package com.zac.octopus.qualitytest.data.remote;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.zac.octopus.qualitytest.model.post.LoginData;
import com.zac.octopus.qualitytest.model.response.ApiResponse;
import com.zac.octopus.qualitytest.model.response.User;
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

  String BASE_URL = "https://www.ezhangyu.com/index.php/Appapi/Qualitytest/";

  @POST("Login") Single<ApiResponse<User>> login(@Body LoginData loginData);

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
