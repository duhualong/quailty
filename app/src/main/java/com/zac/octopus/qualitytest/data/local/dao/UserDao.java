package com.zac.octopus.qualitytest.data.local.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.zac.octopus.qualitytest.data.local.DatabaseHelper;
import com.zac.octopus.qualitytest.di.ApplicationContext;
import com.zac.octopus.qualitytest.model.response.User;
import com.zac.octopus.qualitytest.util.Utils;
import javax.inject.Singleton;

/**
 * User data access object
 * Created by Zac on 2016/6/30.
 */
@Singleton public class UserDao {

  private DatabaseHelper mDatabaseHelper;

  public UserDao(@ApplicationContext Context context) {
    mDatabaseHelper = new DatabaseHelper(context);
  }

  /**
   * 初始化用户数据
   *
   * @param user user data object
   * @return the row ID of the newly inserted row, or -1 if an error occurred
   */
  public long initUserData(User user) {
    SQLiteDatabase database = null;
    try {
      database = mDatabaseHelper.getWritableDatabase();
      clearUserData(database, user.getUid());
      ContentValues values = new ContentValues();
      values.put(DatabaseHelper.FIELD_UID, user.getUid());
      values.put(DatabaseHelper.FIELD_USERNAME, user.getUsername());
      values.put(DatabaseHelper.FIELD_COMPANY_CODE, user.getBlgcompanycode());
      values.put(DatabaseHelper.FIELD_PHONE, user.getUserphone());
      values.put(DatabaseHelper.FIELD_EMAIL, user.getEmail());
      values.put(DatabaseHelper.FIELD_LOGO, user.getCompanylogo());

      return database.insert(DatabaseHelper.TABLE_USER, null, values);
    } finally {
      Utils.close(database);
    }
  }

  /**
   * 清除用户数据
   *
   * @param database SQLiteDatabase
   * @param userId user id
   */
  public void clearUserData(SQLiteDatabase database, String userId) {
    if (database == null) {
      database = mDatabaseHelper.getWritableDatabase();
    }
    database.delete(DatabaseHelper.TABLE_USER, DatabaseHelper.FIELD_UID + "=?",
        new String[] { userId });
  }

  /**
   * 获取公司ID
   * @param userId 用户ID
   * @return 公司ID
   */
  public String getCompanyCodeById(String userId) {
    String companyCode = null;
    String[] column = { DatabaseHelper.FIELD_COMPANY_CODE };
    String selection = DatabaseHelper.FIELD_UID + "=?";

    SQLiteDatabase database = null;
    Cursor cursor = null;
    try {
      database = mDatabaseHelper.getReadableDatabase();
      cursor = database.query(DatabaseHelper.TABLE_USER, column, selection, new String[] { userId },
          null, null, null);
      if (cursor.moveToFirst()) {
        companyCode = cursor.getString(0);
      }
      return companyCode;
    } finally {
      Utils.close(cursor);
      Utils.close(database);
    }
  }
}
