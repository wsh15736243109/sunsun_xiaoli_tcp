package sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itboye.pondteam.bean.ProductBean;

import java.util.ArrayList;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.custom.RatioImageView;
import sunsun.xiaoli.jiarebang.utils.ScreenUtil;
import sunsun.xiaoli.jiarebang.utils.XGlideLoader;

/**
 * Created by Administrator on 2018/1/29.
 */

public class ProductAdapter extends BaseAdapter {
    Activity context;
    ArrayList<ProductBean.HomeListBean> bean;
    private float mRatio;

    public ProductAdapter(Activity context, ArrayList<ProductBean.HomeListBean> bean, float mRatio) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.bean = bean;
        this.mRatio = mRatio;
    }

    @Override
    public int getCount() {
        //
        return bean == null ? 0 : bean.size();
    }

    @Override
    public Object getItem(int position) {
        //
        return position;
    }

    @Override
    public long getItemId(int position) {
        //
        return position;
    }

    @SuppressWarnings("deprecation")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //
        ViewHouder houder;
        if (convertView == null) {
            houder = new ViewHouder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_home_product_center, null);
            houder.img = (RatioImageView) convertView.findViewById(R.id.img);
            convertView.setTag(houder);
        } else {
            houder = (ViewHouder) convertView.getTag();
        }
//
//        Drawable drawable = context.getWallpaper();
//        //将Drawable转化为Bitmap
//        Bitmap bitmap = ImageUtil.drawableToBitmap(drawable);
//        //缩放图片
//        Bitmap zoomBitmap = ImageUtil.zoomBitmap(bitmap, 100, 100);
//        //获取圆角图片
//        Bitmap roundBitmap = ImageUtil.getRoundedCornerBitmap(zoomBitmap, 10.0f);
//        //获取倒影图片
//        houder.img.setImageBitmap(roundBitmap);

        float screenW = ScreenUtil.getPhoneSize(context)[0];
        float screenH = ScreenUtil.getPhoneSize(context)[1];
        houder.img.setmRatio(mRatio, (int) screenW, (int) screenH);
        XGlideLoader.displayRoundedCornerImage(context, bean.get(position).getIcon_url(), houder.img);
        return convertView;
    }

    class ViewHouder {

        LinearLayout linyout;
        RatioImageView img;
        TextView textView1;
    }


}
