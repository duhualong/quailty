package com.zac.octopus.qualitytest.util;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RxJava Utils
 * Created by Zac on 2016/7/5.
 */
public class RxUtils {

  public static <T> Single.Transformer<T, T> applySchedulers() {
    return new Single.Transformer<T, T>() {
      @Override public Single<T> call(Single<T> tSingle) {
        return tSingle.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

}
