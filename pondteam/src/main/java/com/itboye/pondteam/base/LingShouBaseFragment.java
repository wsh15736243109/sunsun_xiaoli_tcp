package com.itboye.pondteam.base;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.itboye.pondteam.base.BaseTwoActivity.EVENT_TYPE_UNKNOWN;

/**
 * Created by Administrator on 2017/5/19.
 */

public abstract class LingShouBaseFragment extends Fragment implements View.OnClickListener {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        smartInject();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);

        return view;
    }

    private void smartInject() {

        try {
            Class<? extends Fragment> clz = getClass();

            while (clz != LingShouBaseFragment.class) {

                Field[] fs = clz.getDeclaredFields();
                Resources res = getResources();
                String packageName = getActivity().getPackageName();
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
                        View v = getView().findViewById(viewId);
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

                clz = (Class<? extends Fragment>) clz.getSuperclass();

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /*
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

        if (result.hasError()) {
            if (result.getMsg().contains("java.net.UnknownHostException")) {
                MAlert.alert("请连接网络 ");
            } else {
                MAlert.alert(result.getMsg());
            }
            result.setEventType(EVENT_TYPE_UNKNOWN);
            return result;
        }
//        if (result.getCode()==101) {
//            startActivity(new Intent(this,Login));
//        }
        return result;
    }

    protected abstract int getLayoutId();

    protected abstract void initData();
}
