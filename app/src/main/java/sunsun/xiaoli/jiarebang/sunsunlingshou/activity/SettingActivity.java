package sunsun.xiaoli.jiarebang.sunsunlingshou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.LingShouPersonDataBean;
import sunsun.xiaoli.jiarebang.beans.UploadImageBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.GlideLoader;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.web.WebActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.utils.uploadmultipleimage.UploadImageUtils;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class SettingActivity extends LingShouBaseActivity implements UploadImageUtils.UploadResult {

    TranslucentActionBar actionBar;
    Button btn_exit_login;
    RelativeLayout re_head;
    @IsNeedClick
    ImageView img_head;

    RelativeLayout re_sensen;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        initTitlebarStyle1(this, actionBar, "设置", R.mipmap.ic_left_light, "", 0, "");
        //初始actionBar
        actionBar.setData("设置", 0, "", 0, "", null);
        //开启渐变
//        actionBar.setNeedTranslucent();
        //设置状态栏高度
        actionBar.setStatusBarHeight(getStatusBarHeight());
        actionBar.setBarBackgroundColor(getResources().getColor(R.color.main_yellow));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit_login:
                new LingShouPersonDataBean().setPersonData(null);
                Intent intent = new Intent();
                intent.setAction(Const.LOGIN_ACTION);
                sendBroadcast(intent);
                MAlert.alert("退出成功");
                finish();
                break;
            case R.id.re_head:
                openLibrary(img_head);
                break;
            case R.id.re_sensen:
                startActivity(
                        new Intent(this, WebActivity.class)
                                .putExtra("url", Const.aboutMe)
                                .putExtra("title", "关于我们"));
                break;
        }
    }

    private void openLibrary(ImageView iv) {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.blue))
                .titleBgColor(getResources().getColor(R.color.blue))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // (截图默认配置：关闭    比例 1：1    输出分辨率  500*500)
//                .crop(2, 1, 1000, 500)
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();

        ImageSelector.open(this, imageConfig);   // 开启图片选择器
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            File file = new File(pathList.get(0));
            Glide.with(getApplicationContext()).load(pathList.get(0)).placeholder(R.drawable.lingshou_logo).into(img_head);
            img_head.setTag(pathList.get(0));
            new UploadImageUtils(getSp(Const.UID), "avatar").beginUpload("image", file, this);
        }
    }


    @Override
    public void uploadSuccess(UploadImageBean response) {
        MAlert.alert(response + "成功");
    }

    @Override
    public void uploadFail(VolleyError error) {
        MAlert.alert(error.getMessage() + "失败");
    }
}
