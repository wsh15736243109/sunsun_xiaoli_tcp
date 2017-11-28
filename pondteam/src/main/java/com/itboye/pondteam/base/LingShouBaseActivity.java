package com.itboye.pondteam.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;

import com.itboye.pondteam.utils.loadingutil.LoadingDialog;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.itboye.pondteam.base.BaseTwoActivity.EVENT_TYPE_UNKNOWN;

/**
 * Created by wu shun hui on 2017/5/16.
 * 2017/5/16 添加自定义注解，修复activty中一旦定义某个View便自动生成点击事件（某些控件我们不想要点击），影响点击
 * 2017/5/16 添加透明状态状态栏
 */

public abstract class LingShouBaseActivity extends FragmentActivity implements View.OnClickListener {
    public String TAG = "BaseActivity";
    LoadingDialog progressDialog = null;
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        TAG = getClass().getName();

        progressDialog = new LoadingDialog();
        initWindow();
        initData();
    }



    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//Android Lg8.0 虚拟按键会隐藏布局
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        smartInject();// 自动获取控件
    }

    private void smartInject() {

        try {
            Class<? extends Activity> clz = getClass();

            while (clz != LingShouBaseActivity.class) {

                Field[] fs = clz.getDeclaredFields();
                Resources res = getResources();
                String packageName = getPackageName();
                for (Field field : fs) {
                    if (!View.class.isAssignableFrom(field.getType())) {
                        continue;
                    }
                    int viewId = res.getIdentifier(field.getName(), "id",
                            packageName);
                    if (viewId == 0)
                        continue;
                    field.setAccessible(true);
                    try {
                        View v = findViewById(viewId);
                        field.set(this, v);
                        Class<?> c = field.getType();
                        //判断是否有注解
                        if (field.getAnnotations() != null) {
                            if (field.isAnnotationPresent(IsNeedClick.class)) {//如果属于这个注解
                                //为这个控件设置属性
                                field.setAccessible(true);//允许修改反射属性
                                IsNeedClick inject = field.getAnnotation(IsNeedClick.class);
//                                int value=inject.value();//得到注解的值
//                                if(value==-1){
//
//                                }
                            } else {
                                Method m = c.getMethod("setOnClickListener",
                                        View.OnClickListener.class);
                                m.invoke(v, this);
                            }
                        } else {
//                            Method m = c.getMethod("setOnClickListener",
//                                    View.OnClickListener.class);
//                            m.invoke(v, this);
                        }
                    } catch (Throwable e) {
                    }
                    field.setAccessible(false);

                }

                clz = (Class<? extends Activity>) clz.getSuperclass();

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
    /**
     * //     * @param data
     *
     * @return 2016-4-23 hebidu
     */
    public ResultEntity handlerError(Object data) {
        ResultEntity result = (ResultEntity) data;
        if (result == null) {
            result = new ResultEntity(result.getCode(), "数据格式错误!~", data);
            return result;
        }
        if (result.getCode()==111) {

            MAlert.alert(result.getMsg()+"要重登录拉");
            return result;
        }
        if (result.hasError()) {

            if (result.getMsg().contains("java.net.UnknownHostException")) {
                MAlert.alert("请连接网络 ");
            } else {
                MAlert.alert(result.getMsg());
            }
            result.setEventType(EVENT_TYPE_UNKNOWN);
            return result;
        }

        return result;
    }
    /**
     * 显示进度对话框,带有消息 flag 是否可以取消
     */
    @SuppressLint("NewApi")
    public void showProgressDialog(String message, boolean flag) {
        if (progressDialog == null)
            return;
        setProgressDialogMessage(message);
        if (!progressDialog.isAdded()) {
            progressDialog.show(getFragmentManager(), message);
            progressDialog.setCancelable(flag);
        }
    }
    /**
     * 设置进度对话框消息
     *
     * @param message
     */
    public void setProgressDialogMessage(String message) {
        progressDialog.setMsg(message);
    }
    /**
     * 关闭进度对话框
     */
    @SuppressLint("NewApi")
    public void closeProgressDialog() {
        try {
            if (progressDialog != null) {
//			if (progressDialog.getDialog().isShowing()) {
                progressDialog.dismiss();
//			}
            }
        }catch (Exception e){

        }

    }
}
