package com.zac.octopus.qualitytest.util;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Photo process utilities
 * Created by Zac on 4/5/2016.
 */
public class PhotoUtils {

  public static final String CROP_IMAGE_NAME = "crop_image.jpeg";

  /**
   * Get bitmap from photo uri
   *
   * @param photoUri photo uri
   * @param imageHolder image file place
   * @return photo bitmap
   */
  public static Bitmap getPhotoBitmap(Context context, Uri photoUri, ImageView imageHolder) {
    String[] projection = { MediaStore.MediaColumns.DATA };
    CursorLoader cursorLoader = new CursorLoader(context, photoUri, projection, null, null, null);
    Cursor cursor = cursorLoader.loadInBackground();
    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
    cursor.moveToFirst();

    String selectedImagePath = cursor.getString(columnIndex);

    int targetWidth = imageHolder.getWidth();
    int targetHeight = imageHolder.getHeight();

    Bitmap bitmap;
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(selectedImagePath, options);

    // Determine how much to scale down the image
    options.inSampleSize =
        Math.min(options.outWidth / targetWidth, options.outHeight / targetHeight);

    // Decode the image file into a Bitmap sized to fill the View;
    options.inJustDecodeBounds = false;

    bitmap = BitmapFactory.decodeFile(selectedImagePath, options);

    return bitmap;
  }

  /**
   * Verify if can capture image now.
   *
   * @param context ui context
   * @param photoFile photo file
   * @return true if can take image.
   */
  public static boolean isCanTakePhoto(Context context, File photoFile) {
    return photoFile != null
        && new Intent(MediaStore.ACTION_IMAGE_CAPTURE).resolveActivity(context.getPackageManager())
        != null;
  }

  /**
   * Get a photo filename
   *
   * @return a temporary photo file name.
   */
  private static String getPhotoFilename() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
    String timestamp = sdf.format(new Date());
    return "IMG_" + timestamp + "_";
  }

  /**
   * Create a temporary photo file
   *
   * @return a temporary photo file.
   */
  public static File createImageFile() {
    File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    try {
      return File.createTempFile(getPhotoFilename(), /* prefix */
          ".jpg", /* suffix */
          storageDir /* directory */);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Get UCrop crop image options
   *
   * @return crop image options
   */
  public static UCrop.Options getCropOptions() {
    UCrop.Options options = new UCrop.Options();
    options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
    options.setCompressionQuality(80);
    return options;
  }

  /**
   * Save a file: path for use with ACTION_VIEW intents
   *
   * @param photoFile photo file
   * @return photo file
   */
  public static String getCapturePhotoPath(File photoFile) {
    return "file:" + photoFile.getAbsolutePath();
  }

  /**
   * Pixel transfer to Dip
   *
   * @param px pixel
   * @return dip
   */
  public static float pxToDp(float px) {
    float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
    return px / (densityDpi / 160f);
  }

  /**
   * Dip transfer to Pixel
   *
   * @param dp dip
   * @return pixel
   */
  public static int dpToPx(int dp) {
    float density = Resources.getSystem().getDisplayMetrics().density;
    return Math.round(dp * density);
  }
}
