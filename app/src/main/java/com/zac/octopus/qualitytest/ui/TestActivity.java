package com.zac.octopus.qualitytest.ui;

import android.app.Activity;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.zac.octopus.qualitytest.R;
import com.zac.octopus.qualitytest.util.Encrypt;

/**
 * Fucking activity
 * Created by Zac on 2016/7/7.
 */
public class TestActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ass);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.ass_we) public void onClick() {
    String text = "15";
    String key =
        "{\"commentstate\": 1,\"blgcompanycode\": \"201605130134573658\",\"startindex\": 0,\"pagesize\": 10}";
    String data = Encrypt.encrypt(key, text);
    System.out.println("Data >> " + data);
  }
}
