package com.zac.octopus.qualitytest.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

/**
 * Android Runtime Permission Help Utilities
 * Created by zac on 4/28/16.
 */
public class PermissionUtils {

  public static boolean checkCamera(Context context) {
    return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        == PackageManager.PERMISSION_GRANTED;
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public static boolean checkReadExternalStorage(Context context) {
    return ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_GRANTED;
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public static boolean checkWriteExternalStorage(Context context) {
    return ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_GRANTED;
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public static void requestCamera(Activity activity, int requestCode) {
    ActivityCompat.requestPermissions(activity, new String[] {
        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    }, requestCode);
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public static void requestCamera(Fragment fragment, int requestCode) {
    fragment.requestPermissions(new String[] {
        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    }, requestCode);
  }
}
