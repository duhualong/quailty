package com.zac.octopus.qualitytest.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

/**
 * Image Select Dialog Fragment
 * Created by zac on 5/6/16.
 */
public class ImageSelectDialogFragment extends DialogFragment {

  private static final String TAG = "ImageSelectDialogFragment";

  private Context context;
  private Uri photoUri;

  public static final String PHOTO_UTI = "photo_uri";
  public static final int CAPTURE_IMAGE_CODE = 0x101;
  public static final int SELECT_PHOTO_CODE = 0x102;

  /**
   * Create a new select image dialog instance.
   *
   * @param photoUri temporary camera image save uri.
   * @return new select image dialog instance.
   */
  public static ImageSelectDialogFragment newInstance(Uri photoUri) {
    ImageSelectDialogFragment fragment = new ImageSelectDialogFragment();

    if (photoUri != null) {
      Bundle args = new Bundle();
      args.putParcelable(PHOTO_UTI, photoUri);
      fragment.setArguments(args);
    }

    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      photoUri = getArguments().getParcelable(PHOTO_UTI);
    }
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {

    context = getActivity();
    AlertDialog.Builder builder = new AlertDialog.Builder(context).setPositiveButton("Gallery",
        new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            selectPhoto(SELECT_PHOTO_CODE);
          }
        }).setNegativeButton("Camera", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialogInterface, int i) {
        captureImage(CAPTURE_IMAGE_CODE);
      }
    });
    return builder.create();
  }

  private void captureImage(int requestCode) {
    Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if (photoUri != null) {
      captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
      getTargetFragment().startActivityForResult(captureIntent, requestCode);
    }
  }

  private void selectPhoto(int requestCode) {
    //激活系统图库，选择一张图片
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setType("image/*");
    // 开启一个带有返回值的Activity，请求码为REQUEST_GALLERY_CODE
    getTargetFragment().startActivityForResult(intent, requestCode);
  }

  public void show(FragmentManager fragmentManager) {
    super.show(fragmentManager, TAG);
  }
}
