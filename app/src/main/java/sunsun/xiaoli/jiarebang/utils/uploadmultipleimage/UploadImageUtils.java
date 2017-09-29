package sunsun.xiaoli.jiarebang.utils.uploadmultipleimage;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.utils.Const;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sunsun.xiaoli.jiarebang.beans.UploadImageBean;

/**
 * Created by Administrator on 2017/6/19.
 */

public class UploadImageUtils {
    String uploadURl = Const.updaloadURL;
    Map<String, String> hashMap=null;
    public UploadImageUtils(String... param){
        hashMap=new HashMap<>();
        hashMap.put("uid",param[0]);
        hashMap.put("type",param[1]);
    }
    public void beginUpload( String filePartName, File file, final UploadResult uploadResult) {
        uploadURl="http://dev.sale.sunsunxiaoli.com/index.php/file/upload";
        MultipartRequest multipartRequest = new MultipartRequest(uploadURl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response= decode(response);
                Log.v("reqeust", response+"成功22222"+uploadURl);
                Gson gson = new Gson();
                UploadImageBean uploadImageBean=null;
                try {
                    uploadImageBean = gson.fromJson((response), UploadImageBean.class);
                    if (uploadImageBean != null) {
                        if (uploadImageBean.getCode() != 0) {
                            uploadResult.uploadFail(new VolleyError(uploadImageBean.getData().toString()));
                        } else {
                            uploadResult.uploadSuccess(uploadImageBean);
                        }
                    } else {
                        uploadResult.uploadFail(new VolleyError());
                    }
                }catch (Exception e){
                    uploadResult.uploadFail(new VolleyError(response));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                uploadResult.uploadFail(error);
            }
        }, filePartName, file, hashMap);
        MyApplication.addRequest(multipartRequest);
    }

    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5) && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr.charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }

    public void beginUpload(Map<String, String> hashMap, String filePartName, ArrayList<File> file, final UploadResult uploadResult) {
        MultipartRequest multipartRequest = new MultipartRequest(uploadURl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                UploadImageBean uploadImageBean = gson.fromJson(response, UploadImageBean.class);
                if (uploadImageBean != null) {
                    if (uploadImageBean.getCode() != 0) {
                        uploadResult.uploadFail(new VolleyError(uploadImageBean.getData().toString()));
                    } else {
                        uploadResult.uploadSuccess(uploadImageBean);
                    }
                } else {
                    uploadResult.uploadFail(new VolleyError());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                uploadResult.uploadFail(error);
            }
        }, filePartName, file, hashMap);
        MyApplication.addRequest(multipartRequest);
    }

    public interface UploadResult {
        void uploadSuccess(UploadImageBean response);

        void uploadFail(VolleyError error);
    }
}
