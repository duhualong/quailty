package com.zac.octopus.qualitytest.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Check Connectivity Status & T1ype
 * Created by zac on 15-9-10.
 */
public class Connectivity {

  /**
   * Get the network info
   *
   * @param context ui context
   * @return Network info
   */
  public static NetworkInfo getNetworkInfo(Context context) {
    ConnectivityManager cm =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    return cm.getActiveNetworkInfo();
  }

  /**
   * Check if there is any connectivity
   *
   * @param context ui context
   * @return true if network is connected.
   */
  public static boolean isConnected(Context context) {
    NetworkInfo info = Connectivity.getNetworkInfo(context);
    return (info != null && info.isConnected());
  }

  /**
   * Check if there is any connectivity to a wifi network
   *
   * @param context ui context
   * @return true if wifi network is connected.
   */
  public static boolean isConnectedWifi(Context context) {
    NetworkInfo info = Connectivity.getNetworkInfo(context);
    return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
  }

  /**
   * Check if there is any connectivity to a mobile network
   *
   * @param context ui context
   * @return true if mobile network is connected.
   */
  public static boolean isConnectedMobile(Context context) {
    NetworkInfo info = Connectivity.getNetworkInfo(context);
    return (info != null
        && info.isConnected()
        && info.getType() == ConnectivityManager.TYPE_MOBILE);
  }
}
