package sunsun.xiaoli.jiarebang.sunsunlingshou.utils;

import android.app.Activity;
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
        if (url == null) {
            Glide.with(activity).load(resource).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        } else {
            Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        }
    }
}
