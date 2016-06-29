package com.zac.octopus.qualitytest.ui.personal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zac.octopus.qualitytest.BaseFragment;
import com.zac.octopus.qualitytest.MainActivity;
import com.zac.octopus.qualitytest.R;
import com.zac.octopus.qualitytest.ui.dialog.ImageSelectDialogFragment;
import com.zac.octopus.qualitytest.util.PermissionUtils;
import com.zac.octopus.qualitytest.util.PhotoUtils;
import java.io.File;

/**
 * Personal Center Page
 * Created by zac on 5/4/16.
 */
public class PersonalFragment extends BaseFragment {

  private static final String TAG = "PersonalFragment";
  public static final int REQUEST_CAMERA_PERMISSION = 0x101;
  private static final int REQUEST_IO_PERMISSION = 0x102;
  private View containerRootView;

  private ImageSelectDialogFragment imageSelectDialog;
  private Uri photoUri;

  @BindView(R.id.personal_sdv_avatar) SimpleDraweeView avatarView;

  @Override protected int getContentView() {
    return R.layout.fragment_page_personal;
  }

  @Override protected void updateUI() {

  }

  @OnClick({ R.id.personal_iv_settings, R.id.personal_sdv_avatar }) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.personal_iv_settings:
        break;
      case R.id.personal_sdv_avatar:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          boolean hasPermission = PermissionUtils.checkCamera(getContext())
              || PermissionUtils.checkWriteExternalStorage(getContext());
          if (hasPermission) {
            showPhotoHeadFindDialog();
          } else {
            requestCameraPermission();
          }
        } else {
          showPhotoHeadFindDialog();
        }
        break;
    }
  }

  private void showPhotoHeadFindDialog() {
    File photoFile = PhotoUtils.createImageFile();
    photoUri = Uri.fromFile(photoFile);

    imageSelectDialog = ImageSelectDialogFragment.newInstance(photoUri);
    imageSelectDialog.setTargetFragment(PersonalFragment.this, 0);
    imageSelectDialog.show(fragmentMgr);
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);

    if (context instanceof Activity) {
      containerRootView = ((MainActivity) context).getRootView();
    }
  }

  private void requestCameraPermission() {
    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
        Manifest.permission.CAMERA)) {
      Snackbar.make(containerRootView, "请提供摄像头的权限，以预览相机图片", Snackbar.LENGTH_INDEFINITE)
          .setAction("OK", v -> {
            PermissionUtils.requestCamera(PersonalFragment.this, REQUEST_CAMERA_PERMISSION);
          })
          .show();
    } else {
      PermissionUtils.requestCamera(PersonalFragment.this, REQUEST_CAMERA_PERMISSION);
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
    switch (requestCode) {
      case REQUEST_IO_PERMISSION:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          Snackbar.make(containerRootView,
              "CAMERA permission has now been granted. Showing preview.", Snackbar.LENGTH_SHORT)
              .show();
        } else {
          //Log.i(TAG, "CAMERA permission was NOT granted.");
          Snackbar.make(containerRootView, "CAMERA permission was NOT granted.",
              Snackbar.LENGTH_SHORT).show();
        }
        break;
      case REQUEST_CAMERA_PERMISSION:
        Log.d(TAG, "onRequestPermissionsResult: ");
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          // Camera permission has been granted, preview can be displayed
          // Log.i(TAG, "CAMERA permission has now been granted. Showing preview.");
          //                    Snackbar.make(containerRootView, "CAMERA permission has now been granted. Showing preview.",
          //                            Snackbar.LENGTH_SHORT).show();
          showPhotoHeadFindDialog();
        } else {
          //Log.i(TAG, "CAMERA permission was NOT granted.");
          Snackbar.make(containerRootView, "CAMERA permission was NOT granted.",
              Snackbar.LENGTH_SHORT).show();
        }
        break;
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  private void hideDialog() {
    if (imageSelectDialog.getShowsDialog()) {
      imageSelectDialog.dismiss();
      imageSelectDialog = null;
    }
  }
}
