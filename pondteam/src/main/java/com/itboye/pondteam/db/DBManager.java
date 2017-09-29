package com.itboye.pondteam.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2017/4/27.
 */

public class DBManager {
    DBHelper mDbHelper;
    Context mContext;
    SQLiteDatabase db;

    public DBManager(Context context) {
        mContext = context;
        mDbHelper = new DBHelper(mContext);
    }


    //    -----------------------------------------摄像头流量记录---------------------------------------------------------
    public long insertFlowData(String did, String uid, String todaytime, String flow) {
        db = mDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("did", did);
        cv.put("flow", flow);
//        cv.put("todaytime", todaytime);
        cv.put("uid", uid);
        long num = db.insert(DBHelper.TABLENAME_Video, null, cv);
        db.close();
        return num;
    }

    public long updateFlowData(String did, String uid, String todaytime, String flow) {
        db = mDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
//        cv.put("did", did);
//        cv.put("todaytime", todaytime);
        cv.put("flow", flow);
//        cv.put("uid", uid);
        long num = db.update(DBHelper.TABLENAME_Video, cv, "did=? and uid=?", new String[]{did, uid});
        db.close();
        return num;
    }

    public int[] queryFlow(String did, String uid, String todaytime) {
        db = mDbHelper.getReadableDatabase();
        Cursor cursor = null;
        int jisuanFlow = 0;
        int count = 0;
        int[] result=new int[2];
        if (this.db != null) {
            cursor = db.query(DBHelper.TABLENAME_Video, null, "did=? and uid=?", new String[]{did, uid}, null, null, null);
//            cursor = db.query(DBHelper.TABLENAME_Video, null, null, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    System.out.println("........流量" + (cursor.getString(cursor.getColumnIndex("flow"))) + "日期" + cursor.getString(cursor.getColumnIndex("todaytime")));
                    count++;
                    jisuanFlow += Double.parseDouble(cursor.getString(cursor.getColumnIndex("flow")));
                }
            }
        }
        result[0]=count;
        result[1]=jisuanFlow;
        return result;
    }
//    ------------------------------------------设备密码相关---------------------------------------------------------

    public long insertDeviceData(String did, String psw, String uid) {
        db = mDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("did", did);
        cv.put("psw", psw);
        cv.put("uid", uid);
        long num = db.insert(DBHelper.TABLENAME, null, cv);
        db.close();
        return num;
    }

    public long updateDeviceData(String did, String psw, String uid) {
        db = mDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("did", did);
        cv.put("psw", psw);
        cv.put("uid", uid);
        long num = db.update(DBHelper.TABLENAME, cv, "did=?", new String[]{did});
        db.close();
        return num;
    }

    public long deleteDeviceDataByDid(String did, String uid) {
        db = mDbHelper.getWritableDatabase();
        long num = db.delete(DBHelper.TABLENAME, "did=? and uid=?", new String[]{did, uid});
        return num;
    }

    public String queryDevicePswByDid(String did, String uid) {
        db = mDbHelper.getReadableDatabase();
        String psw = "";
        Cursor cursor = null;
        if (this.db != null) {
            cursor = db.query(DBHelper.TABLENAME, new String[]{"psw"}, "did=? and uid=?", new String[]{did, uid}, null, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    psw = cursor.getString(0);
                }
            }
        }
        return psw == null ? "" : psw;
    }

    public int queryDeviceCountByDid(String did, String uid) {
        db = mDbHelper.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;
        if (this.db != null) {
            cursor = db.query(DBHelper.TABLENAME, new String[]{"psw"}, "did=? and uid=?", new String[]{did, uid}, null, null, null);
            if (cursor != null) {
                count = cursor.getCount();
            }
        }
        return count;
    }

}
