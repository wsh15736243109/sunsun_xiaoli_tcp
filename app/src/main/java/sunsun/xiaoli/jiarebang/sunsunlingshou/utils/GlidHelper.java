package sunsun.xiaoli.jiarebang.sunsunlingshou.utils;

import android.app.Activity;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.itboye.pondteam.app.MyApplication;

/**
 * Created by Administrator on 2017/6/23.
 */

public class GlidHelper {
    public static void glidLoad(ImageView imageView, String url) {
        Glide.with(MyApplication.getInstance()).load(url).into(imageView);
    }
    public static void loadGif(Activity activity, ImageView imageView, String url, int resource) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!activity.isDestroyed()) {
                if (url == null) {
                    Glide.with(activity).load(resource).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
                } else {
                    Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
                }
            }
        }else{
            try {
                if (url == null) {
                    Glide.with(activity).load(resource).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
                } else {
                    Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
                }
            }catch (Exception e){

            }
        }

    }

    public static void glidLoad(Activity activity, ImageView imageView, String url, int resource) {
        if (url == null) {
            Glide.with(activity).load(resource).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        } else {
            Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        }
    }
}
