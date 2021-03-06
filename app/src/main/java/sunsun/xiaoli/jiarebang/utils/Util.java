package sunsun.xiaoli.jiarebang.utils;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;

import junit.framework.Assert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import sunsun.xiaoli.jiarebang.BuildConfig;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.address.CityModel;

public class Util {

    private static final String TAG = "SDK_Sample.Util";

    /**
     * 弃用，某些符合规定的图片也会包checkArgs invalid
     *
     * @param bmp
     * @param needRecycle
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 微信分享 微信对图片文字大小的限制
     * final boolean checkArgs() {
     * if ((getType() == 8)
     * && (((this.thumbData == null) || (this.thumbData.length == 0)))) {
     * a.a("MicroMsg.SDK.WXMediaMessage",
     * "checkArgs fail, thumbData should not be null when send emoji");
     * return false;
     * }
     * if ((this.thumbData != null) && (this.thumbData.length > 32768)) {
     * a.a("MicroMsg.SDK.WXMediaMessage",
     * "checkArgs fail, thumbData is invalid");
     * return false;
     * }
     * if ((this.title != null) && (this.title.length() > 512)) {
     * a.a("MicroMsg.SDK.WXMediaMessage",
     * "checkArgs fail, title is invalid");
     * return false;
     * }
     * if ((this.description != null) && (this.description.length() > 1024)) {
     * a.a("MicroMsg.SDK.WXMediaMessage",
     * "checkArgs fail, description is invalid");
     * return false;
     * }
     * if (this.mediaObject == null) {
     * a.a("MicroMsg.SDK.WXMediaMessage",
     * "checkArgs fail, mediaObject is null");
     * return false;
     * }
     * if ((this.mediaTagName != null) && (this.mediaTagName.length() > 64)) {
     * a.a("MicroMsg.SDK.WXMediaMessage",
     * "checkArgs fail, mediaTagName is too long");
     * return false;
     * }
     * if ((this.messageAction != null)
     * && (this.messageAction.length() > 2048)) {
     * a.a("MicroMsg.SDK.WXMediaMessage",
     * "checkArgs fail, messageAction is too long");
     * return false;
     * }
     * if ((this.messageExt != null) && (this.messageExt.length() > 2048)) {
     * a.a("MicroMsg.SDK.WXMediaMessage",
     * "checkArgs fail, messageExt is too long");
     * return false;
     * }
     * return this.mediaObject.checkArgs();
     * }
     * <p>
     * 新版
     *
     * @param bmp
     * @param needRecycle
     * @return
     */

    public static byte[] bmpToByteArrayNew(final Bitmap bmp, final boolean needRecycle) {
        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }
        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0, i, j), null);
            if (needRecycle)
                bmp.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                //F.out(e);
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }
    }

    public static byte[] getHtmlByteArray(final String url) {
        URL htmlUrl = null;
        InputStream inStream = null;
        try {
            htmlUrl = new URL(url);
            URLConnection connection = htmlUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = inputStreamToByte(inStream);

        return data;
    }

    public static byte[] inputStreamToByte(InputStream is) {
        try {
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                bytestream.write(ch);
            }
            byte imgdata[] = bytestream.toByteArray();
            bytestream.close();
            return imgdata;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] readFromFile(String fileName, int offset, int len) {
        if (fileName == null) {
            return null;
        }

        File file = new File(fileName);
        if (!file.exists()) {
            Log.i(TAG, "readFromFile: file not found");
            return null;
        }

        if (len == -1) {
            len = (int) file.length();
        }

        Log.d(TAG, "readFromFile : offset = " + offset + " len = " + len + " offset + len = " + (offset + len));

        if (offset < 0) {
            Log.e(TAG, "readFromFile invalid offset:" + offset);
            return null;
        }
        if (len <= 0) {
            Log.e(TAG, "readFromFile invalid len:" + len);
            return null;
        }
        if (offset + len > (int) file.length()) {
            Log.e(TAG, "readFromFile invalid file len:" + file.length());
            return null;
        }

        byte[] b = null;
        try {
            RandomAccessFile in = new RandomAccessFile(fileName, "r");
            b = new byte[len]; // ���������ļ���С������
            in.seek(offset);
            in.readFully(b);
            in.close();

        } catch (Exception e) {
            Log.e(TAG, "readFromFile : errMsg = " + e.getMessage());
            e.printStackTrace();
        }
        return b;
    }

    private static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;

    public static Bitmap extractThumbNail(final String path, final int height, final int width, final boolean crop) {
        Assert.assertTrue(path != null && !path.equals("") && height > 0 && width > 0);

        BitmapFactory.Options options = new BitmapFactory.Options();

        try {
            options.inJustDecodeBounds = true;
            Bitmap tmp = BitmapFactory.decodeFile(path, options);
            if (tmp != null) {
                tmp.recycle();
                tmp = null;
            }

            Log.d(TAG, "extractThumbNail: round=" + width + "x" + height + ", crop=" + crop);
            final double beY = options.outHeight * 1.0 / height;
            final double beX = options.outWidth * 1.0 / width;
            Log.d(TAG, "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
            options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
            if (options.inSampleSize <= 1) {
                options.inSampleSize = 1;
            }

            // NOTE: out of memory error
            while (options.outHeight * options.outWidth / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
                options.inSampleSize++;
            }

            int newHeight = height;
            int newWidth = width;
            if (crop) {
                if (beY > beX) {
                    newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            } else {
                if (beY < beX) {
                    newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            }

            options.inJustDecodeBounds = false;

            Log.i(TAG, "bitmap required size=" + newWidth + "x" + newHeight + ", orig=" + options.outWidth + "x" + options.outHeight + ", sample=" + options.inSampleSize);
            Bitmap bm = BitmapFactory.decodeFile(path, options);
            if (bm == null) {
                Log.e(TAG, "bitmap decode failed");
                return null;
            }

            Log.i(TAG, "bitmap decoded size=" + bm.getWidth() + "x" + bm.getHeight());
            final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
            if (scale != null) {
                bm.recycle();
                bm = scale;
            }

            if (crop) {
                final Bitmap cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1, (bm.getHeight() - height) >> 1, width, height);
                if (cropped == null) {
                    return bm;
                }

                bm.recycle();
                bm = cropped;
                Log.i(TAG, "bitmap croped size=" + bm.getWidth() + "x" + bm.getHeight());
            }
            return bm;

        } catch (final OutOfMemoryError e) {
            Log.e(TAG, "decode bitmap failed: " + e.getMessage());
            options = null;
        }

        return null;
    }

    public static String getNickName(String... args) {
        String did = args[0];
        String type = "ADT-C";
        try {
            if (args.length > 1) {
                type = args[1];
            }
        } catch (Exception e) {

        }
        if (did.startsWith("S01")) {
            return MyApplication.getInstance().getResources().getString(R.string.device_chitangguolv);
        } else if (did.startsWith("S02")) {
            return MyApplication.getInstance().getResources().getString(R.string.device_zhinengjiarebang);
        } else if (did.startsWith("S03")) {
            return MyApplication.getInstance().getResources().getString(R.string.device_zhineng806);
        } else if (did.startsWith("S04")) {
            return MyApplication.getInstance().getResources().getString(R.string.device_yuancheng_ph);
        } else if (did.startsWith("S05")) {
            return MyApplication.getInstance().getResources().getString(R.string.device_zhinengbianpinshuibeng);
        } else if (did.startsWith("S06")) {
            if (type.equalsIgnoreCase("S06-1")) {
                return "ADT-C";
            } else {
                return "ADT-H";
            }
        } else if (did.startsWith("S07")) {
            return BuildConfig.APP_TYPE.equals("小绵羊智能") ? MyApplication.getInstance().getResources().getString(R.string.device_zhinengyangqibeng) : MyApplication.getInstance().getResources().getString(R.string.device_zhinengqibeng);
        } else if (did.startsWith("S08")) {
            return MyApplication.getInstance().getResources().getString(R.string.device_zhineng118);
        } else if (did.startsWith("SCHD")) {
            return BuildConfig.APP_TYPE.equals("小绵羊智能") ? MyApplication.getInstance().getResources().getString(R.string.device_zhinengshexiangtou_yihu) : MyApplication.getInstance().getResources().getString(R.string.device_zhinengshexiangtou);
        }
        return "device";
    }

    /**
     * 解析市数据
     */
    public static String queryCityNo(String cityName) {
        String table = "common_city";
        String column = "city";
        if (cityName.equals("重庆市") || cityName.equals("北京市") || cityName.equals("天津市") || cityName.equals("上海市") || cityName.equals("重庆") || cityName.equals("北京") || cityName.equals("天津") || cityName.equals("上海")) {
            table = "common_province";
            column = "province";
            if (cityName.endsWith("市")) {

            } else {
                cityName = cityName + "市";
            }
        }
        //查询城市ID
        String sql = "select * from " + table + " where " + column + " = ?";
        Cursor cursor = App.getInstance().db.rawQuery(sql, new String[]{cityName});
        CityModel city = new CityModel();
        while (cursor.moveToNext()) {
            city = new CityModel(cursor.getString(2), cursor.getInt(1), cursor.getInt(3));
            break;
        }
        cursor.close();

        SPUtils.put(App.getInstance(), null, Const.CITY_CODE, city.getNumber() + "");
        Log.v("request_params", "城市Code" + city.getNumber());
        return city.getNumber() + "";
    }

    /**
     * 解析市数据
     */
    public static String queryDistrictNo(String cityName) {
        String table = "common_area";
        String column = "area";
        //查询城市ID
        String sql = "select * from " + table + " where " + column + " = ?";
        Cursor cursor = App.getInstance().db.rawQuery(sql, new String[]{cityName});
        CityModel city = new CityModel();
        while (cursor.moveToNext()) {
            city = new CityModel(cursor.getString(2), cursor.getInt(1), cursor.getInt(3));
            break;
        }
        cursor.close();

        SPUtils.put(App.getInstance(), null, Const.AREA_CODE, city.getNumber() + "");
        Log.v("request_params", "地区Code" + city.getNumber());
        return city.getNumber() + "";
    }

    /**
     * 解析市数据
     */
    public static CityModel queryCity(String cityName) {
        String table = "common_city";
        String column1 = "city";
        String colunm2 = "father";
        if (cityName.equals("重庆市") || cityName.equals("北京市") || cityName.equals("天津市") || cityName.equals("上海市") || cityName.equals("重庆") || cityName.equals("北京") || cityName.equals("天津") || cityName.equals("上海")) {
            table = "common_province";
            column1 = "province";
            if (cityName.endsWith("市")) {

            } else {
                cityName = cityName + "市";
            }
        }
        //查询城市ID
        String sql = "select * from " + table + " where " + column1 + " = ?";
        Cursor cursor = App.getInstance().db.rawQuery(sql, new String[]{cityName});
        CityModel city = new CityModel();
        while (cursor.moveToNext()) {
            city = new CityModel(cursor.getString(2), cursor.getInt(1), cursor.getInt(3));
            city.setId(cursor.getInt(0));
            break;
        }
        cursor.close();
        Log.v("request_params", "城市Code" + city.getNumber());
        return city;
    }

    /**
     * 解析省数据
     */
    public static CityModel queryProvince(String provinceName) {
        String table = "common_province";
        String column = "province";
        String sql = "select * from " + table + " where " + column + " = ?";
        Cursor cursor = App.getInstance().db.rawQuery(sql, new String[]{provinceName});
        CityModel province = null;
        while (cursor.moveToNext()) {
            province = new CityModel(cursor.getString(2), cursor.getInt(1), cursor.getInt(3));
            province.setId(cursor.getInt(0));
            break;
        }
        cursor.close();
        return province;
    }

    public static CityModel queryProvinceNo(String provinceName) {
        String table = "common_province";
        String column = "province";
        String sql = "select * from " + table + " where " + column + " = ?";
        Cursor cursor = App.getInstance().db.rawQuery(sql, new String[]{provinceName});
        CityModel province = null;
        while (cursor.moveToNext()) {
            province = new CityModel(cursor.getString(2), cursor.getInt(1), cursor.getInt(3));
            province.setId(cursor.getInt(0));
            break;
        }
        cursor.close();
        return province;
    }
}
