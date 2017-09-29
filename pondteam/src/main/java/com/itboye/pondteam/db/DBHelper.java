package com.itboye.pondteam.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/4/27.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static String DBNAME="DEVICEDATA.db";
    public static String TABLENAME="device_info";
    public static String TABLENAME_Video="video_info";
    String dbSql=("create table "+TABLENAME+" (id Integer primary key,did text,psw text,uid text)");
    String dbSql2=("create table "+TABLENAME_Video+" (id Integer primary key,did text,uid text,todaytime text,flow text)");
    public DBHelper(Context context) {
        super(context, DBNAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(dbSql);
        db.execSQL(dbSql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
