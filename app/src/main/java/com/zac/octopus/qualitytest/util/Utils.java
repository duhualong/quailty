package com.zac.octopus.qualitytest.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Utilities
 * Created by Zac on 2016/6/30.
 */
public class Utils {

  public static void close(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
