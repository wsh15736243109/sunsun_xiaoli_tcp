package sunsun.xiaoli.jiarebang.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.itboye.pondteam.utils.Const;

import sunsun.xiaoli.jiarebang.R;


/**
 * 图片加载类
 */
public class XGlideLoaderNew {
    /**
     * 单图片查看--加载图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void displayImage(Context context, String path, ImageView imageView) {

        String imgPath=path.startsWith("http") ?path : Const.imgurl + path;
        Glide.with(context).load(imgPath).fitCenter().error(R.drawable.lingshou_logo).placeholder(R.drawable.lingshou_logo).into(imageView);
        Log.v("request_params", "displayImage 图片路径"+imgPath);
    }
    /**
     * 单图片查看--加载图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void displayImageForUser(Context context, String path, ImageView imageView) {
        String imgPath=path.startsWith("http") ?path : Const.imgurl + path;
        Glide.with(context).load(imgPath).error(R.drawable.lingshou_logo).placeholder(R.drawable.lingshou_logo).into(imageView);
        Log.v("request_params", "displayImageForUser 图片路径"+imgPath);
    }

    /**
     * 单图片查看--加载图片(圆形图片)
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void displayImageCircular(Context context, String path, ImageView imageView) {
        String imgPath=path.startsWith("http") ?path : Const.imgSunsunUrl + path;
        Glide.with(context)
                .load(imgPath)
                .transform(new GlideCircleTransform(context))
                .error(R.drawable.lingshou_logo).placeholder(R.drawable.lingshou_logo)
                .into(imageView);
        Log.v("request_params", "displayImageCircular 图片路径"+imgPath);

    }
    /**
     * 单图片查看--加载图片(圆形图片)
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void displayImageCircularForUser(Context context, String path, ImageView imageView) {
        String imgPath=path.startsWith("http") ?path : Const.IMAGE_HEAD + path;
        Glide.with(context)
                .load(imgPath)
                .transform(new GlideCircleTransform(context))
                .error(R.drawable.lingshou_logo).placeholder(R.drawable.lingshou_logo)
                .into(imageView);
        Log.v("request_params", "displayImageCircularForUser 图片路径"+imgPath);

    }

    public static void displayRatioImageByScreenWidth(Context context, String path, ImageView imageView){
        String imgPath=path.startsWith("http") ?path : Const.IMAGE_HEAD + path;
        Glide.with(context)
                .load(imgPath)
                .transform(new RatioByScreenWidth(context))
                .error(R.drawable.lingshou_logo).placeholder(R.drawable.lingshou_logo)
                .into(imageView);
        Log.v("request_params", "displayRatioImageByScreenWidth 图片路径"+imgPath);
    }

}
