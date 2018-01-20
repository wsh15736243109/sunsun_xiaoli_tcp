package com.itboye.pondteam.volley;

import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.itboye.pondteam.R;
import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.utils.Const;

public class BaseErrorListener implements XErrorListener {

    private ICompleteListener listener;

    public BaseErrorListener(ICompleteListener listener) {
        this.listener = listener;
    }

    @Override
    public void onErrorResponse(Exception exception, String code, String msg) {
        if (listener != null) {
            if (code == "1111") {
                Intent intent = new Intent();
                intent.setAction(Const.LOGIN_ACTION);
                intent.putExtra("code", code);
                MyApplication.getInstance().sendBroadcast(intent);
            }
            if (code == "0") {
                code = "-199";
            }
            if (msg == null || msg.isEmpty()) {
                if (exception != null) {
                    msg = exception.getMessage();
                } else {
                    msg =  MyApplication.getInstance().getString(R.string.server_error_unknow);
                }
            }
            if (exception instanceof ServerError) {
                ServerError error = (ServerError) exception;
                code = "" + error.networkResponse.statusCode;
                msg =  MyApplication.getInstance().getString(R.string.server_error);

            } else if (exception instanceof VolleyError) {
                VolleyError jsonException = (VolleyError) exception;
                String error = jsonException.getMessage();
                if (jsonException instanceof TimeoutError) {
                    msg = MyApplication.getInstance().getString(R.string.volley_error_timeout);
                } else if (jsonException instanceof NoConnectionError) {
                    msg = MyApplication.getInstance().getString(R.string.volley_error_noconnect);
                } else if (jsonException instanceof AuthFailureError) {
                    msg = MyApplication.getInstance().getString(R.string.volley_error_authfailure);
                } else if (jsonException instanceof NetworkError) {
                    msg = MyApplication.getInstance().getString(R.string.volley_error_networkerror);
                } else if (jsonException instanceof ParseError) {
                    msg = MyApplication.getInstance().getString(R.string.volley_error_parseerror);
                } else if (jsonException instanceof ServerError) {
                    msg = MyApplication.getInstance().getString(R.string.volley_error_servererror);
                } else {
                    msg = MyApplication.getInstance().getString(R.string.volley_error_server) + jsonException;
                }

            }
            ResultEntity result = new ResultEntity(Integer.parseInt(code), msg, exception);
            listener.failure(result);
        }
    }
}

