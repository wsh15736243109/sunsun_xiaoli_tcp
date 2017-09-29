package com.itboye.pondteam.custom.ptr;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.itboye.pondteam.R;
import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.volley.TimesUtils;

import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 *
 *
 * @author
 * @since 1.0.0
 */
public class BasePtr {
    static TextView txt_refresh_time;
    private static ImageView img_loading;

    /**
     * 设置默认的头部View，且设置为仅能刷新。
     */
    public static void setRefreshOnlyStyle(PtrFrameLayout ptrFrameLayout) {
        setDefaultHeader(ptrFrameLayout);
        ptrFrameLayout.setLoadingMinTime(1000);
        ptrFrameLayout.setDurationToClose(100);
        ptrFrameLayout.setMode(PtrFrameLayout.Mode.REFRESH);
    }

    /**
     * 设置默认的头部View，且设置为仅能刷新。
     */
    public static void setLoadMoreOnlyStyle(PtrFrameLayout ptrFrameLayout) {
        setDefaultFooter(ptrFrameLayout);
        ptrFrameLayout.setLoadingMinTime(1000);
        ptrFrameLayout.setDurationToClose(100);
        ptrFrameLayout.setMode(PtrFrameLayout.Mode.LOAD_MORE);
    }


    /**
     * 设置可上拉下拉刷新的View样式。
     */
    public static void setPagedPtrStyle(PtrFrameLayout ptrFrameLayout) {
        setDefaultHeader(ptrFrameLayout);
        setDefaultFooter(ptrFrameLayout);
        ptrFrameLayout.setLoadingMinTime(1000);
        ptrFrameLayout.setDurationToClose(100);
        ptrFrameLayout.setMode(PtrFrameLayout.Mode.BOTH);
    }

    public static void subscribeTestPtrHandler(final PtrFrameLayout ptrFrameLayout){
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrameLayout.refreshComplete();
                    }
                },1800);
            }
        });
    }

    private static void setDefaultHeader(PtrFrameLayout ptrFrameLayout) {
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(ptrFrameLayout.getContext());
        View view= LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.header_refresh,null);
        txt_refresh_time= (TextView) view.findViewById(R.id.refresh_time);
        img_loading= (ImageView) view.findViewById(R.id.img_loading);
        Glide.with(MyApplication.getInstance().getApplicationContext()).load(R.drawable.smartconfig_loading).asGif().diskCacheStrategy(DiskCacheStrategy.RESULT).into(img_loading);
        ptrFrameLayout.setHeaderView(view);
        ptrFrameLayout.addPtrUIHandler(header);
    }

    public static void setNone(PtrFrameLayout ptrFrameLayout){

        ptrFrameLayout.setMode(PtrFrameLayout.Mode.NONE);
    }

    private static void setDefaultFooter(PtrFrameLayout ptrFrameLayout) {
        PtrClassicDefaultFooter footer = new PtrClassicDefaultFooter(ptrFrameLayout.getContext());
        ptrFrameLayout.setFooterView(footer);
        ptrFrameLayout.addPtrUIHandler(footer);
    }

    public static void setRefreshTime(String time){
        txt_refresh_time.setText("上次设备更新时间"+TimesUtils.getStringToDateThree(time));
    }
    public static void setRefreshText(String text){
        txt_refresh_time.setText(text);
    }
}
