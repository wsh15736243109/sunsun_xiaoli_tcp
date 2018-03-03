package sunsun.xiaoli.jiarebang.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.MapView;

/**
 * My2dMapView
 * <p>
 * Created by Mr.w on 2018/3/3.
 * <p>
 * 版本      ${version}
 * <p>
 * 修改时间
 * <p>
 * 修改内容
 */


public class My2dMapView extends MapView {
    public My2dMapView(Context context) {
        super(context);
        init(context);
    }

    public My2dMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public My2dMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public My2dMapView(Context context, AMapOptions aMapOptions) {
        super(context, aMapOptions);
        init(context);
    }

    private void init(Context context) {
//        this.context = context;
        //view加载完成时回调
        this.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ViewGroup child = (ViewGroup) getChildAt(0);//地图框架
                        child.getChildAt(3).setVisibility(View.GONE);//logo
//                        child.getChildAt(7).setVisibility(View.GONE);//缩放
                    }
                });
    }
}
