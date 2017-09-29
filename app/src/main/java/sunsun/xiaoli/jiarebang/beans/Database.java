package sunsun.xiaoli.jiarebang.beans;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

/**
 * Created by 赵武 on 2015/3/9.
 */
public class Database {

	private SQLiteDatabase mDb;
	public Context mContext;

	/**
	 * 判断某个表是否在数据库中
	 *
	 * @param table 表名称
	 * @return 存在返回true，不存在返回false
	 */
	private boolean exits(String table) {
		boolean exits = false;
		String sql = "select * from sqlite_master where name=" + "'" + table + "'";
		Cursor cursor = mDb.rawQuery(sql, null);
		if (cursor.getCount() != 0) {
			exits = true;
		}
		return exits;
	}

	/**
	 * 构造，自动创建并打开数据库
	 *
	 * @param context 应用程序上下文
	 */
	public Database(Context context) {
		this.mContext = context;
		mDb = this.mContext.openOrCreateDatabase("main.db", Context.MODE_PRIVATE, null);
		if (!exits("DEVLIST")) {
			// 设备列表
			String sql = "CREATE TABLE IF NOT EXISTS DEVLIST (ID INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " did TEXT, password TEXT, name TEXT, icon TEXT)";
			mDb.execSQL(sql);
		}
		if (!exits("POSTCFG")) {
			// 设备列表
			String sql = "CREATE TABLE IF NOT EXISTS POSTCFG (ID INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " did TEXT, temp TEXT, level TEXT, load TEXT,"
					+ " temp_sub_id INTEGER, level_sub_id INTEGER, load_sub_id INTEGER)";
			mDb.execSQL(sql);
		}
	}

	/**
	 * 存储故障推送设置值
	 *
	 * @param did          设备DID
	 * @param temp         温度推送使能值
	 * @param level        水位推送使能值
	 * @param load         负载设备推送使能值
	 * @param temp_sub_id  温度SubscribeID
	 * @param level_sub_id 水位SubscribeID
	 * @param load_sub_id  负载设备SubscribeID
	 */
	public void saveFaultConfig(String did, boolean temp, boolean level, boolean load, int temp_sub_id, int level_sub_id, int load_sub_id) {
		synchronized (this) {
			String tempStr = "0";
			String levelStr = "0";
			String loadStr = "0";
			if (temp) {
				tempStr = "1";
			}
			if (level) {
				levelStr = "1";
			}
			if (load) {
				loadStr = "1";
			}
			String sql = "SELECT * FROM POSTCFG WHERE did = '" + did + "'";
			Cursor cur = mDb.rawQuery(sql, null);
			if (cur.getCount() == 0) {
				// 添加
				String sql1 = "INSERT INTO POSTCFG (did, temp, level, load, temp_sub_id, level_sub_id, load_sub_id) VALUES ('" + did + "', '";
				sql1 = sql1 + tempStr + "', '" + levelStr + "', '" + loadStr + "',";
				sql1 = sql1 + String.valueOf(temp_sub_id) + ",";
				sql1 = sql1 + String.valueOf(level_sub_id) + ",";
				sql1 = sql1 + String.valueOf(load_sub_id) + ")";
				mDb.execSQL(sql1);
			} else {
				// 修改
				String sql1 = "UPDATE POSTCFG SET did = '" + did + "',";
				sql1 = sql1 + "temp = '" + tempStr + "',";
				sql1 = sql1 + "level = '" + levelStr + "',";
				sql1 = sql1 + "load = '" + loadStr + "',";
				sql1 = sql1 + "temp_sub_id = " + String.valueOf(temp_sub_id) + ",";
				sql1 = sql1 + "level_sub_id = " + String.valueOf(level_sub_id) + ",";
				sql1 = sql1 + "load_sub_id = " + String.valueOf(load_sub_id);
				sql1 = sql1 + " WHERE did = '" + did + "'";
				mDb.execSQL(sql1);
			}
		}
	}

	private int getFaultConfigSubscribeID(String did, int valueIndex) {
		String sql = "SELECT * FROM POSTCFG WHERE did = '" + did + "'";
		Cursor cur = mDb.rawQuery(sql, null);
		cur.moveToFirst();
		if (cur.getCount() == 0) {
			return 0;
		}
		return cur.getInt(valueIndex);
	}

	public int getTempFaultConfigSubscribeID(String did) {
		synchronized (this) {
			return getFaultConfigSubscribeID(did, 5);
		}
	}

	public int getLevelFaultConfigSubscribeID(String did) {
		synchronized (this) {
			return getFaultConfigSubscribeID(did, 6);
		}
	}

	public int getLoadFaultConfigSubscribeID(String did) {
		synchronized (this) {
			return getFaultConfigSubscribeID(did, 7);
		}
	}

	private boolean getFaultConfig(String did, int valueIndex) {
		String sql = "SELECT * FROM POSTCFG WHERE did = '" + did + "'";
		Cursor cur = mDb.rawQuery(sql, null);
		cur.moveToFirst();
		if (cur.getCount() == 0) {
			return false;
		}
		String o = cur.getString(valueIndex);
		if (o.equalsIgnoreCase("1")) {
			return true;
		}
		return false;
	}

	public boolean getTempFaultConfig(String did) {
		synchronized (this) {
			return getFaultConfig(did, 2);
		}
	}

	public boolean getLevelFaultConfig(String did) {
		synchronized (this) {
			return getFaultConfig(did, 3);
		}
	}

	public boolean getLoadFaultConfig(String did) {
		synchronized (this) {
			return getFaultConfig(did, 4);
		}
	}

	/**
	 * 保存或者覆盖指定设备信息
	 *
	 * @param devInfo 设备信息，依据DID修改
	 */
	public void saveDeviceInfo(AqDeviceInfo devInfo) {
		synchronized (this) {
			String did = devInfo.mDid;
			String name = devInfo.mDeviceName;
			String sql = "SELECT * FROM DEVLIST WHERE did = '" + did + "'";
			Cursor cur = mDb.rawQuery(sql, null);
			if (cur.getCount() == 0) {
				// 添加
				String sql1 = "INSERT INTO DEVLIST (did, password, name, icon) VALUES ('" + did + "', '";
				sql1 = sql1 + devInfo.mPassword + "', '" + name + "', '" + "icon" + "')";
				mDb.execSQL(sql1);
			} else {
				// 修改
				String sql1 = "UPDATE DEVLIST SET did = '" + did + "',";
				sql1 = sql1 + "password = '" + devInfo.mPassword + "',";
				sql1 = sql1 + "name = '" + name + "',";
				sql1 = sql1 + "icon = '" + "icon" + "' WHERE did = '" + did + "'";
				mDb.execSQL(sql1);
			}
		}
	}

	/**
	 * 删除指定设备信息
	 *
	 * @param devInfo 设备信息，根据DID删除
	 */
	public void deleteDeviceInfo(AqDeviceInfo devInfo) {
		synchronized (this) {
			String sql = "DELETE FROM DEVLIST WHERE did = '" + devInfo.mDid + "'";
			mDb.execSQL(sql);
		}
	}

	/**
	 * 读取设备信息列表
	 *
	 * @return 设备信息列表，如果数组成员数为0表示无设备
	 */
	public ArrayList<AqDeviceInfo> getAllDeviceInfo() {
		synchronized (this) {
			ArrayList<AqDeviceInfo> dbDev = new ArrayList<AqDeviceInfo>();
			String sql = "SELECT * FROM DEVLIST";
			Cursor cur = mDb.rawQuery(sql, null);
			cur.moveToFirst();
			for (int i = 0; i < cur.getCount(); i++) {
				AqDeviceInfo di = new AqDeviceInfo();
				di.mDid = cur.getString(1);
				di.mPassword = cur.getString(2);
				di.mDeviceName = cur.getString(3);
				dbDev.add(di);
				cur.moveToNext();
			}
			return dbDev;
		}
	}
}
