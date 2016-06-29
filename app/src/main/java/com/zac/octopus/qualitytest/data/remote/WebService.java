package com.zac.octopus.qualitytest.data.remote;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Web Service
 * Created by Zac on 2016/6/29.
 */
@Singleton public interface WebService {

  class Creator {
    @Inject public WebService create() {
      OkHttpClient client =
          new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();

      Retrofit retrofit = new Retrofit.Builder().client(client)
          .client(client)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .build();
      return retrofit.create(WebService.class);
    }
  }
}
