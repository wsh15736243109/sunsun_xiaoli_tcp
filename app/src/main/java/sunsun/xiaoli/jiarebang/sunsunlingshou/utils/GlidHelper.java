package sunsun.xiaoli.jiarebang.sunsunlingshou.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.itboye.pondteam.app.MyApplication;

/**
 * Created by Administrator on 2017/6/23.
 */

public class GlidHelper {
    public static void glidLoad(ImageView imageView,String url){
        Glide.with(MyApplication.getInstance()).load(url).into(imageView);
    }
}
