package com.zac.octopus.qualitytest.ui.scanner;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.zac.octopus.qualitytest.BaseFragment;
import com.zac.octopus.qualitytest.R;

/**
 * Scanner Page
 * Created by zac on 5/4/16.
 */
public class ScannerFragment extends BaseFragment {

  private static final String TAG = "ScannerFragment";

  @BindView(R.id.scanner_et_barcode_input) EditText barcodeInput;

  @Override protected int getContentView() {
    return R.layout.fragment_page_scanner;
  }

  @Override protected void updateUI() {

  }

  @OnClick({ R.id.btn_ok, R.id.scanner_iv_scan_entry }) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_ok:
        break;
      case R.id.scanner_iv_scan_entry:
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(ScannerFragment.this);
        integrator.setCaptureActivity(ToolbarCaptureActivity.class);
        integrator.initiateScan();
        break;
    }
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    if (scanResult != null) {
      // handle scan result
      String contents = scanResult.getContents();
      if (contents != null) {
        Log.d(TAG, "onActivityResult: " + scanResult.toString());
        barcodeInput.setText(contents);
      } else {
        Log.d(TAG, "onActivityResult: scan result null");
      }
    }
    super.onActivityResult(requestCode, resultCode, data);
  }
}
