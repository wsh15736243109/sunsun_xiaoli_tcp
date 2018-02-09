package sunsun.xiaoli.jiarebang.shuizuzhijia.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.UploadImageBean;
import sunsun.xiaoli.jiarebang.device.jinligang.ForgetPasswordActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.GlideLoader;
import sunsun.xiaoli.jiarebang.utils.XGlideLoaderNew;
import sunsun.xiaoli.jiarebang.utils.uploadmultipleimage.UploadImageUtils;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

public class PersonSettingActivity extends BaseActivity implements
        OnClickListener, UploadImageUtils.UploadResult, Observer {

    public static String AVARTACTION = "com.itboye.avart";

    public static String EMAILACTION = "com.itboye.email";
    private RelativeLayout phonerela;
    private RelativeLayout emailrela;
    private RelativeLayout wechatrela;
    private RelativeLayout aboutrela;
    private RelativeLayout protocalrela;
    private RelativeLayout backrela;
    private RelativeLayout uploadrela;
    private RelativeLayout exitrela;
    private RelativeLayout relayoutUpdate;
    private DisplayImageOptions options;
    private ImageView avatarview;
    // private ImageView wechatview;
    private TextView phone;
    private TextView versionname;
    private TextView txt_title;
    EditText nicktext;
    private IWXAPI api;
    private static final String APP_ID = "wxabf9bc3a048000ac";
    private TextView emailtext;
    private String phonenumber;

    public static String EXITCHANGE = "com.itboye.sunsun.person.exit";
    // 点击数字拨打电话

    private String localSelectPath;
//	private CameraPopupWindow mCameraPopupWindow;

    private ImageView img_back;

    TextView wechatview;
    String openid;

    Button btn_submit;
    UserPresenter userPresenter;
    private String nickName;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_personsetting);
        txt_title.setText("个人资料");
        userPresenter = new UserPresenter(this);
        setMyData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.uploadrela:
                openLibrary();
                break;
            case R.id.phonerela:
                onPhone();
                break;
            case R.id.relayoutUpdate:
                Intent intent = new Intent(PersonSettingActivity.this,
                        ForgetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_submit:
                nickName = nicktext.getText().toString();
                if (nickName == null) {
                    MAlert.alert("请输入昵称");
                    return;
                }
                toUpdateData();
                break;
            default:
                break;

        }

    }

    private void toUpdateData() {
        userPresenter.updateMyData( getSp(Const.S_ID), getSp(Const.UID), nickName);
    }

    //选择图片
    private void openLibrary() {
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

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    // 判断手机号是否为空，当为空的时候去绑定手机不为空的时候去修改
    public void onPhone() {
        System.out.println("phonenumberphonenumberphonenumber" + phonenumber);
        if (!isMobileNO(phonenumber)) {
//
//			Intent intent5 = new Intent(PersonSettingActivity.this,
//					PhoneBandingActivity.class);
//			startActivity(intent5);

        } else {

//			Intent intent5 = new Intent(PersonSettingActivity.this,
//					ChangeBindActivity.class);
//			startActivity(intent5);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            File file = new File(pathList.get(0));
            Glide.with(getApplicationContext()).load(pathList.get(0)).placeholder(R.drawable.lingshou_logo).into(avatarview);
            new UploadImageUtils(getSp(Const.UID), "avatar").beginUpload(Const.XIAOLI_HEAD_UPLOAD_URL, "image", file, this);
        }
    }

    // 显示头像
    public void setMyData() {
        phonenumber = getSp(Const.MOBILE);
        nickName = getSp(Const.NICK);
        XGlideLoaderNew.displayImageCircular(App.getInstance(), getSp(Const.HEAD), avatarview);
        // 显示版本号
        phone.setText(phonenumber);
        nicktext.setText(nickName);
//        try {
//            PackageManager pm = App.getInstance().getPackageManager();
//            PackageInfo info = pm.getPackageInfo(App.getInstance().getPackageName(), 0);
//            String appVersion = info.versionName;
//            versionname.setText(appVersion);
//        } catch (Exception e) {
//
//        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void uploadSuccess(UploadImageBean response) {
        SPUtils.put(this, null, Const.HEAD, response.getData().get(0).getId() + "");
        MAlert.alert("头像上传成功");
    }

    @Override
    public void uploadFail(VolleyError error) {
        MAlert.alert("头像上传失败" + error.toString());
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getData());
            } else {
                if (entity.getEventType() == UserPresenter.updateMyData_success) {
                    SPUtils.put(this, null, Const.NICK, nickName);
                    MAlert.alert(entity.getData());
                    finish();
                } else if (entity.getEventType() == UserPresenter.updateMyData_fail) {
                    MAlert.alert(entity.getData());
                }
            }
        }
    }
}
