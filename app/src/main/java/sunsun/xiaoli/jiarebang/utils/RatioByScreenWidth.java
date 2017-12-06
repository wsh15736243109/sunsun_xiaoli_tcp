package sunsun.xiaoli.jiarebang.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import sunsun.xiaoli.jiarebang.sunsunlingshou.LingShouMainActivity;

/**
 * Created by Administrator on 2017/12/6.
 */

public class RatioByScreenWidth extends BitmapTransformation {

    public RatioByScreenWidth(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {

        int targetWidth = LingShouMainActivity.WIDTH;
        Log.e("width", "source.getHeight()=" + toTransform.getHeight()
                + ",source.getWidth()=" + toTransform.getWidth()
                + ",targetWidth=" + targetWidth);

        if (toTransform.getWidth() == 0) {
            return toTransform;
        }
        // ByAlert.alert(source.getWidth() + ",targetWidth=" + targetWidth);
        // 如果图片小于设置的宽度，则返回原图
        // if (source.getWidth() < targetWidth) {
        // //if (tr) {
        // //
        // //}
        //
        // return source;
        // } else {
        // 如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
        double aspectRatio = (double) toTransform.getHeight()
                / (double) toTransform.getWidth();
        int targetHeight = (int) (targetWidth * aspectRatio);
        if (targetHeight != 0 && targetWidth != 0) {
            Bitmap result = Bitmap.createScaledBitmap(toTransform, targetWidth,
                    targetHeight, false);
            if (result != toTransform) {
                // Same bitmap is returned if sizes are the same
                toTransform.recycle();
            }
            return result;
        } else {
            return toTransform;
        }
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
