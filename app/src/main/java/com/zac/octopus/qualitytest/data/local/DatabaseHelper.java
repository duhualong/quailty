package com.zac.octopus.qualitytest.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database Helper
 * Created by Zac on 2016/6/30.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "quality_test.db";
  private static final int DATABASE_VERSION = 1;

  public static final String TABLE_USER = "tab_user";
  public static final String FIELD_UID = "uid";
  public static final String FIELD_LOGO = "logo";
  public static final String FIELD_COMPANY_CODE = "company_code"; // 所属公司ID
  public static final String FIELD_USERNAME = "username";
  public static final String FIELD_PHONE = "phone";
  public static final String FIELD_EMAIL = "email";

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_USER  +  "(" +
        FIELD_UID + " TEXT, " +
        FIELD_USERNAME + " TEXT, " +
        FIELD_PHONE + " TEXT, " +
        FIELD_EMAIL + " TEXT, " +
        FIELD_COMPANY_CODE + " TEXT, " +
        FIELD_LOGO + " TEXT);";

    sqLiteDatabase.execSQL(sql);
  }

  @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    String sql = "DROP TABLE IF EXISTS " + TABLE_USER;
    sqLiteDatabase.execSQL(sql);
  }
}
