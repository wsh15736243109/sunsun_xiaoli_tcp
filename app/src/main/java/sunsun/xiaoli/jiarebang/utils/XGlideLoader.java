package sunsun.xiaoli.jiarebang.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.itboye.pondteam.utils.Const;

import sunsun.xiaoli.jiarebang.R;


/**
 * 图片加载类
 */
public class XGlideLoader {
    /**
     * 单图片查看--加载图片
     * @param context
     * @param path
     * @param imageView
     */
    public static void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context).load(Const.imgurl +path).error(R.drawable.lingshou_logo).placeholder(R.drawable.lingshou_logo).into(imageView);
    }
    /**
     * 单图片查看--加载图片(圆形图片)
     * @param context
     * @param path
     * @param imageView
     */
    public static void displayImageCircular(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(Const.imgurl+path)
                .transform(new GlideCircleTransform(context))
                .error(R.drawable.lingshou_logo).placeholder(R.drawable.lingshou_logo)
                .into(imageView);

    }

}
