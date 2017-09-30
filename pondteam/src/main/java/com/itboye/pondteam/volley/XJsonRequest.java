package com.itboye.pondteam.volley;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Spanned;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


/**
 * 建造者模式创建Request
 * .默认请求 DEPRECATED_GET_OR_POST
 *
 * @param <T> 请求结束后返回的对象，可以是 List<自定义类>  或  自定义类
 * @author w
 */
public class XJsonRequest<T> extends Request<T> {

    private Type expectReturnType;//期望返回参数的类
    private XRequestListener<T> listener;

    private XErrorListener errorListener;
    private Map<String, String> headers = new HashMap<String, String>();
    private int method = Method.DEPRECATED_GET_OR_POST;
    private Map<String, String> params = new HashMap<String, String>();

    /**
     * 处理code！=0时的特殊情况！！！！！！
     */
    private boolean isCodeZero = true;

    /**
     * @param
     * @param url
     * @param listener
     * @param errlistener 请求异常，失败，code！=0 时会调用，</br></br>
     *                    code!=0时，onErrorResponse(Exception exception,int code,String msg)中
     *                    exception 是CodeErrorException，code为服务器返回的状态码，msg为服务端返回的出错信息（data中字符串）
     *                    </br></br>
     *                    json解析失败时，onErrorResponse(Exception exception,int code,String msg)中
     *                    exception 是JSONException，code为0，msg为null
     *                    </br></br>
     *                    其他异常时 onErrorResponse(Exception exception,int code,String msg)中
     *                    exception 是Exception，code为0，msg为null
     */
    public XJsonRequest(String url, XRequestListener<T> listener, XErrorListener errlistener) {

        super(Method.POST, url, null);
        this.listener = listener;
        this.errorListener = errlistener;
    }

    /**
     * @param method
     * @param url
     * @param listener
     * @param errlistener 请求异常，失败，code！=0 时会调用，</br></br>
     *                    code!=0时，onErrorResponse(Exception exception,int code,String msg)中
     *                    exception 是CodeErrorException，code为服务器返回的状态码，msg为服务端返回的出错信息（data中字符串）
     *                    </br></br>
     *                    json解析失败时，onErrorResponse(Exception exception,int code,String msg)中
     *                    exception 是JSONException，code为0，msg为null
     *                    </br></br>
     *                    其他异常时 onErrorResponse(Exception exception,int code,String msg)中
     *                    exception 是Exception，code为0，msg为null
     */
    public XJsonRequest(int method, String url, XRequestListener<T> listener, XErrorListener errlistener) {

        super(method, url, null);

        this.listener = listener;
        this.errorListener = errlistener;
    }


    @Override
    protected void deliverResponse(T response) {
        if (null != listener) {
            try {
                listener.onResponse(response);
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);

        if (errorListener != null && isCodeZero) {
            try {
                errorListener.onErrorResponse(error, "0", null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public XJsonRequest<T> addHeader(String k, String value) {
        headers.put(k, value);
        return this;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }

    public XJsonRequest<T> setMethod(int method) {
        this.method = method;
        return this;
    }

    @Override
    public int getMethod() {
        return method;
    }

    public XJsonRequest<T> addParam(String k, String value) {
        params.put(k, value);
        return this;
    }

    public XJsonRequest<T> addParams(Map<String, String> params) {
        this.params.putAll(params);
        return this;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {

        return params;
    }


    public Type getExpectReturnType() {
        return expectReturnType;
    }

    public void setExpectReturnType(Type expectReturnType) {
        this.expectReturnType = expectReturnType;

    }

    //该方法中gson解析可能需要调整
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        String resultData = "";
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }

        try {
            if (listener == null) {
                return null;
            }

            JSONObject obj = new JSONObject(parsed);
            System.out.println("数据" + obj.toString());
            String type = obj.getString("type");
            resultData = obj.getString("data");
            if (type.equals("E")) {
                Log.d("request_params", "服务器出错!" + type);
                return Response.error(new VolleyError("服务器出错!"));
            }

            if (this.getExpectReturnType() == null) {
                Log.d("request_params", "必须指定返回数据类型" + this.getExpectReturnType());
                return Response.error(new VolleyError("必须指定返回数据类型"));
            }

//			Type typeClass1 = listener.getClass().getGenericSuperclass();
//			if (!(typeClass1 instanceof ParameterizedType) ){
//				return  Response.error(new VolleyError("返回参数异常!"));
//			}
//			
//			Type actualType = ((ParameterizedType) typeClass1).getActualTypeArguments()[0];


            parsed = DataEncryptionUtil.decodeData(resultData);
            JSONObject decodeJsonObj = new JSONObject(parsed);
            String code = decodeJsonObj.getString("code");
//            if (code.equals("1111")) {
//                LoginController.setLoginState(new UnLoginState());
//            }
            if (!code.equals("0")) {
                if (errorListener != null) {
                    String data = decodeJsonObj.getString("data");
                    if (data != null) {
                        Log.v("request_params", "-----------------------------------------请求失败-------------------------------2" + data);
//                        Logger.v("request_params", "-----------------------------------------请求失败-------------------------------2" + data);
                        handlerError(new CodeErrorException(data), code, data);
                    } else {
                        Log.v("request_params", "-----------------------------------------请求失败-------------------------------2" + data);
//                        Logger.v("request_params", "-------------------------------------请求失败----------------------------1!" + data);
                        handlerError(new CodeErrorException("请求失败!"), code, data);
                    }
                }
                return Response.error(new VolleyError("请求异常!"));
            }

            resultData = decodeJsonObj.getString("data");
            Log.v("request_params", "响应参数" + resultData);
//            Logger.json(resultData);
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .registerTypeAdapter(Spanned.class, new String2Spanned())
                    .create();

//            if (resultData.contains("短信已发送,请注意查收!")) {
//                resultData = "短信已发送";
//            }
            if (resultData.contains("device_tokens为空")) {
                resultData = "操作成功";
            }
            try {
                @SuppressWarnings("unchecked")
                T res =null;
                if(this.getExpectReturnType() == String.class){
                    JsonElement jsonElement=gson.toJsonTree(resultData);
                    res=gson.fromJson(jsonElement, this.expectReturnType);
                }else{
                    res=gson.fromJson(resultData, this.expectReturnType);
                }
                Log.d("request_params", "--------------------------------------------请求结束-------------------------------------------------");
                if (null != res) {
                    return Response.success(res, HttpHeaderParser.parseCacheHeaders(response));
                }
            } catch (JsonSyntaxException exception) {
                T res = gson.fromJson(gson.toJsonTree(resultData), this.expectReturnType);
                Log.d("request_params", "--------------------------------------------请求结束-------------------------------------------------"+resultData);
                if (null != res) {
                    return Response.success(res, HttpHeaderParser.parseCacheHeaders(response));
                }
            }
        } catch (JSONException e) {
            Log.d("request_params", "请求有点小问题----" + e.getCause().getMessage());
        }
        return Response.error(new VolleyError("请求异常!"));

    }

    /**
     * 输出完整json，防止一次输出过多时显示不完整问题
     *
     * @param tag
     * @param json
     */
    private void printLogDetail(String tag, String json) {

        try {
            int len = 3500;
            int position = 0;

            while (json.length() - position > len) {
                for (int i = 0; i < json.length() - position - len; i++) {
                    if (json.charAt(position + len + i) == '\n') {
                        DebugLog.v(tag, json.substring(position, position + len + i));

                        position = position + len + i + 1;
                        break;
                    }
                }

            }
            DebugLog.v(tag, json.substring(position, json.length()));

        } catch (Exception e) {
            DebugLog.v(tag, json);
        }

    }

    private void handlerError(final Exception exception, final String code, final String msg) {

        isCodeZero = false;
//		errorListener.onErrorResponse(exception, code, msg);

        new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message m) {
                errorListener.onErrorResponse(exception, code, msg);
                super.handleMessage(m);
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.sendEmptyMessage(0);


    }

    /*
     * 处理转义字符问题，防止json数据混乱，导致flexgrid显示不出来
     * params:
     *  str:需要处理的字符串
     * return:
     *  res:处理后的字符
     */
    public static String toGoodJsonStr(String str) {
        StringBuffer res = new StringBuffer();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '\"':
                    res.append("\\\"");
                    break;
                case '\\':
                    res.append("\\\\");
                    break;
                case '/':
                    str.replace("/", "\\/");
                    break;
                case '\b':
                    res.append("\\b");
                    break;
                case '\f':
                    res.append("\\f");
                    break;
                case '\n':
                    res.append("\\n");
                    break;
                case '\r':
                    res.append("\\r");
                    break;
                case '\t':
                    res.append("\\t");
                    break;
                case '\'':
                    res.append("\\\'");
                    break;
                case '=':
                    str.replace("=", "");
                    break;
                default:
                    res.append(c);
            }
        }
        return res.toString();
    }
}
