package sunsun.xiaoli.jiarebang.device.jinligang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

import java.io.File;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.custom.RatioImageView;
import sunsun.xiaoli.jiarebang.utils.ScreenUtil;
import sunsun.xiaoli.jiarebang.utils.Util;

import static sunsun.xiaoli.jiarebang.utils.ImageCompress.compressImage;

/**
 * Created by Administrator on 2017/5/10.
 */

public class ImageDetailActivity extends BaseActivity {
    ImageView img_back;
    RatioImageView img;
    TextView txt_title, share_wx, share_CircleFriends;
    String imgUrl = "";
    App app;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        txt_title.setText(getString(R.string.share));
        imgUrl = getIntent().getStringExtra("img");
        app = (App) getApplication();
        bundle = getIntent().getExtras();
        System.out.println("图片路径" + imgUrl);
        Bitmap bitmap = BitmapFactory.decodeFile(imgUrl);
        if (bitmap==null) {
            MAlert.alert("图片已损坏");
            return;
        }
        float imageWidth = bitmap.getWidth();
        float imageHeight = bitmap.getHeight();
        float screenW = ScreenUtil.getPhoneSize(ImageDetailActivity.this)[0];
        float height = screenW * imageHeight / imageWidth;
//        Glide.with(this).load(new File(imgUrl)).into(img);
//        Glide.with(this).load(new File(imgUrl)).asBitmap().error(R.drawable.icon).placeholder(R.drawable.placeholder_gray).into(img);
        img.setmRatio(screenW / height, ScreenUtil.getPhoneSize(ImageDetailActivity.this)[0], ScreenUtil.getPhoneSize(ImageDetailActivity.this)[1]);
        loadIntoUseFitWidth(mContext, imgUrl, R.drawable.placeholder_gray, img);
//        Glide.with(this).load(new File(imgUrl)).asBitmap().placeholder(R.drawable.placeholder_gray).fitCenter().error(R.drawable.device_aq).into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                float imageWidth = resource.getWidth();
//                float imageHeight = resource.getHeight();
//                float screenW=ScreenUtil.getPhoneSize(ImageDetailActivity.this)[0];
//                float height = screenW * imageHeight / imageWidth;
//                ViewGroup.LayoutParams para = img.getLayoutParams();
//                para.height = (int)height;
//                para.width = ScreenUtil.getPhoneSize(ImageDetailActivity.this)[0];
//                img.setImageBitmap(resource);
//            }
//        });
    }

    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public static void loadIntoUseFitWidth(Context context, final String imageUrl, int errorImageId, final ImageView imageView) {
        Glide.with(context)
                .load(new File(imageUrl))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<File, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, File model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, File model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;
                    }
                })
                .placeholder(errorImageId)
                .error(errorImageId)
                .into(imageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.share_CircleFriends:
                //分享朋友圈
                sendImg(false);
//                shareToWxOrCircle(false);
                break;
            case R.id.share_wx:
                //分享微信
                sendImg(true);
//                shareToWxOrCircle(true);
                break;
        }
    }

    private static final int THUMB_SIZE = 150;
    private void sendImg(boolean isWx) {
        String imagePath = imgUrl;
        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(imagePath);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.description="aq806";

        Bitmap bmp = BitmapFactory.decodeFile(imagePath);
        bmp=compressImage(bmp,32);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArrayNew(thumbBmp, true);
        msg.title=" ";
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "img"+String.valueOf(System.currentTimeMillis());
        req.message = msg;
        if (isWx) {
            //微信会话
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }else{
            //微信朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        app.getIwxapi().sendReq(req);
    }
}
