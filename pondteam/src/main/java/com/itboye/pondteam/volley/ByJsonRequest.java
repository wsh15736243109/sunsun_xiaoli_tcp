/**
 *
 */
package com.itboye.pondteam.volley;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * @param
 * @author w
 */
public class ByJsonRequest<E> extends XJsonRequest<E> {

    ByJsonRequest(String url, XRequestListener<E> listener,
                  XErrorListener errlistener) {
        super(url, listener, errlistener);
    }

    ByJsonRequest(int method, String url, XRequestListener<E> listener,
                  XErrorListener errlistener) {
        super(method, url, listener, errlistener);

    }

    public static class Builder<T> {
        private ByJsonRequest<T> buildRequest;
        private String url = Const.URL;
        //        private String url="http://mw.api.itboye.com/public/index.php";
        private XRequestListener<T> listener;
        private XErrorListener errlistener;
        private Type expectReturnType;
        private String typekey;
        private String apiVer;

        private Map<String, Object> map;
        private Map<String, Object> userParam;
        private String time = TimesUtils.getStamp();
        private String notify_id = TimesUtils.getStamp();
        private String desContent;

        /**
         * 获取App的版本号
         *
         * @return 返回App的版本号
         */
        private String getVersionCode() {
            int appVersion = 0;
            try {

                PackageManager pm = MyApplication.getInstance()
                        .getPackageManager();
                PackageInfo info = pm.getPackageInfo(MyApplication.getInstance()
                        .getPackageName(), 0);
                appVersion = info.versionCode;
            } catch (Exception e) {

            }
            return "" + appVersion;
        }

        String appv = String.valueOf(getVersionCode());

        public Builder() {
            map = new HashMap<String, Object>();
            userParam = new HashMap<String, Object>();
            String autoCode = SPUtils.get(MyApplication.getInstance(), null, Const.S_ID, "") + "";
            // 公共参数
            map.put(Const.APP_VERSION, getVersionCode());
            map.put(Const.APP_TYPE, "android");
            map.put(Const.TIME, time);
//			map.put(Const.ALG, Const.ALG_VALUE);
            map.put(Const.NOTIFY_ID, time);
            map.put("client_id", Const.CLIENT_ID);

            Const.language = MyApplication.getInstance().getLanguage();
            if (autoCode.equals("")) {
                this.url = url + "?alg=md5_v2&client_id=" + Const.CLIENT_ID + "&lang=" + Const.language;
            } else {
                this.url = url + "?alg=md5_v2&client_id=" + Const.CLIENT_ID + "&s_id=" + autoCode + "&lang=" + Const.language;
            }
            System.out.println("接口地址" + url);
        }


        public Builder<T> addParams(Map<String, Object> params) {
            if (params != null) {
                Set<String> keys = params.keySet();
                Iterator<String> it = keys.iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    Object obj = params.get(key);
                    userParam.put(key, obj);
                }
            }
            return this;
        }

        public Builder<T> param(String k, String v) {
            userParam.put(k, v);
            return this;
        }

        /**
         * @param k
         * @param v
         * @return
         */
        public Builder<T> param(String k, Object v) {
            userParam.put(k, v);
            return this;
        }

        public Builder<T> notifyID(String notify_id) {
            this.notify_id = notify_id;
            return this;
        }

        public Builder<T> url(String url) {
            this.url = url;
            return this;
        }

        public Builder<T> requestListener(XRequestListener<T> listener) {
            this.listener = listener;
            return this;
        }

        public Builder<T> errorListener(XErrorListener errlistener) {
            this.errlistener = errlistener;
            return this;
        }

        /**
         * - 同时设置 type，ver，params
         *
         * @param typekey
         * @param ver
         * @param params
         * @return
         */
        public Builder<T> setTypeVerParams(String typekey, String ver,
                                           Map<String, Object> params) {
            this.typekey = typekey;
            this.apiVer = ver;
            this.userParam = params;
            return this;
        }

        public Builder<T> setTypeVerParamsAndReturnClass(String typekey,
                                                         String ver, Map<String, Object> params, Type expectReturnType) {
            this.typekey = typekey;
            this.apiVer = ver;
            this.userParam = params;
            this.expectReturnType = expectReturnType;
            return this;
        }

        public Builder<T> typeKey(String typekey) {
            this.typekey = typekey;
            return this;
        }

        public Builder<T> apiVer(String ver) {
            this.apiVer = ver;
            return this;
        }

        public Builder<T> requestMethod(int method) {
            return this;
        }

        public Builder<T> desEncode() {

            String param = new ParamDeal().dataEncrypt(userParam);
            String sign = signInfo(param);
            this.addParams(map).param(Const.TYPE, typekey)
                    .param(Const.API_VER, apiVer)
                    .param(Const.DATA, param)
                    .param(Const.SIGN, sign);
//
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(
                            FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .registerTypeAdapter(Spanned.class, new String2Spanned())
                    .create();

            String jsonStr = gson.toJson(this.userParam);

            Log.d("request_params", "----------------------------------请求开始---------------------------------------");
            Log.d("request_params", "请求参数" + jsonStr);
            this.desContent = DESUtil.encode(jsonStr,
                    Const.CLIENT_SECERET);


            return this;
        }

        /**
         * 设置返回数据类型
         * <p>
         * //		 * @param expectClass
         *
         * @return
         */
        public Builder<T> setReturnDataType(Type expectReturnType) {
            this.expectReturnType = expectReturnType;
            return this;
        }

        /**
         * 创建 MyJsonRequest对象
         *
         * @return
         */
        public ByJsonRequest<T> desEncodeThenBuildAndSend() {
            MyApplication.addRequest(this.desEncode().build());
            return this.buildRequest;
        }


        /**
         * 创建 MyJsonRequest对象
         *
         * @return
         */
        public ByJsonRequest<T> build() {
            if (listener == null) {
                System.err.println("XRequestListener == null !");
            }
            if (errlistener == null) {
                System.err.println("ErrorListener == null !");
            }

            if (null == typekey) {
                throw new RuntimeException(
                        "typekey can not be null ! you should use typeKey(String typekey) ");
            }

            if (null == apiVer) {
                throw new RuntimeException(
                        "apiVer can not be null ! you should use apiVer(String ver) ");
            }

            buildRequest = new ByJsonRequest<T>(url, listener, errlistener);
            buildRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            buildRequest.setExpectReturnType(expectReturnType);
            String msg = bytetoString(Base64.encode(desContent.getBytes(), Base64.DEFAULT));
//            String msg=(desContent);
            buildRequest.addParam(Const.ITBOYE, msg);
            System.out.println("请求参数》》》" + msg);
//			map.put("html","");
            buildRequest.addHeader("alg", "md5_v2");
            buildRequest.addHeader("Accept", "text/html,application/json");
            buildRequest.setMethod(Method.POST);

            // 增加SocketLog调试
            buildRequest.addHeader("SocketLog",
                    "SocketLog(tabid=2&client_id=slog_fc68e9)");
            return buildRequest;

        }

        private String signInfo(String userInf) {

            ParamDeal operate = new ParamDeal();
            String sign = operate.getMD5Sign(time, typekey, userInf,
                    Const.CLIENT_SECERET, notify_id);
            System.out.println("msg5" + sign);
            return sign;
        }

    }

    /**
     * 字节数组转为普通字符串（ASCII对应的字符）
     *
     * @param bytearray byte[]
     * @return String
     */
    public static String bytetoString(byte[] bytearray) {
        String result = "";
        char temp;

        int length = bytearray.length;
        for (int i = 0; i < length; i++) {
            temp = (char) bytearray[i];
            result += temp;
        }
        return result;
    }
}