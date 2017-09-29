package sunsun.xiaoli.jiarebang.sunsunlingshou.utils;

import android.content.Context;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.CarouselView;

/**
 * Created by Administrator on 2017/6/23.
 */

public class LunBoHelper {
    public void setLunBoData(Context context, CarouselView carouselView, List<String> imgUrl) {
        ArrayList<ImageView> arrayBmps = new ArrayList<>();
        for (int i = 0; i < imgUrl.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            GlidHelper.glidLoad(imageView, imgUrl.get(i));
            arrayBmps.add(imageView);
            imageView.setTag(i);
        }
        carouselView.setImageBitmaps(arrayBmps);
    }
}
