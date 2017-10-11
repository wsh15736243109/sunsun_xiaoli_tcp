package sunsun.xiaoli.jiarebang.sunsunlingshou.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.itboye.pondteam.utils.Const;

import java.util.ArrayList;
import java.util.Arrays;
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

    public void setLunBoData(Context context, CarouselView carouselView, String imgUrl) {
        ArrayList<ImageView> arrayBmps = new ArrayList<>();
        List<String> arrayList=new ArrayList<>();
        if (imgUrl.equals("")) {
            return;
        }
        if (imgUrl.contains(",")) {
            arrayList= Arrays.asList(imgUrl.split(","));//String[] ==> List<String>   List<String>  ==> String[]:String[] toBeStored = new list().toArray(new String[10]);
        }
        else{
            arrayList.add(imgUrl);
        }
        for (int i = 0; i < arrayList.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            GlidHelper.glidLoad(imageView, Const.imgurl+arrayList.get(i));
            Log.v("img_url",Const.imgurl+arrayList.get(i));
            arrayBmps.add(imageView);
            imageView.setTag(i);
        }
        carouselView.setImageBitmaps(arrayBmps);
    }
}
