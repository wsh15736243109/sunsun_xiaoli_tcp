package sunsun.xiaoli.jiarebang.sunsunlingshou.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.EmptyUtil;
import com.itboye.pondteam.utils.SPUtils;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.utils.popupwindow.SaveAlertView;
import com.itboye.pondteam.utils.udp.VersionUtil;
import com.itboye.pondteam.volley.ResultEntity;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.AppConfigBean;
import sunsun.xiaoli.jiarebang.beans.LingShouPersonDataBean;
import sunsun.xiaoli.jiarebang.beans.UploadImageBean;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.BindPhoneActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.GlideLoader;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.web.WebActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.utils.XGlideLoader;
import sunsun.xiaoli.jiarebang.utils.uploadmultipleimage.UploadImageUtils;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class SettingActivity extends LingShouBaseActivity implements UploadImageUtils.UploadResult, Observer {

    TranslucentActionBar actionBar;
    Button btn_exit_login;
    RelativeLayout re_head;
    @IsNeedClick
    ImageView img_head;

    RelativeLayout re_sensen;

    RelativeLayout re_nick, re_tiaokuan, re_kefu, re_bindphone;
    private LingShouPresenter lingShouPresenter;
    private EditText editText;
    String sign, email, weixin, company, job_title, loc_country, loc_area;
    private String nickName;
    @IsNeedClick
    TextView txt_nickname, txt_ver, txt_bindphone;
    private SaveAlertView saveDialog;
    private Dialog alert;
    private RadioButton tvTitle;
    private AppConfigBean appConfigBean;
    ImageView iv_actionbar_left;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        lingShouPresenter = new LingShouPresenter(this);
        lingShouPresenter.getAppConfig();
        initTitlebarStyle1(this, actionBar, "设置", R.mipmap.ic_left_light, "", 0, "");
        //开启渐变
//        actionBar.setNeedTranslucent();
        //设置状态栏高度
        actionBar.setStatusBarHeight(getStatusBarHeight());
        actionBar.setBarBackgroundColor(getResources().getColor(R.color.main_yellow));
        setUserMessage();
        setAppVersion();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setBindPhone();
    }

    private void setBindPhone() {
        txt_bindphone.setText(getSp(Const.MOBILE).equals("") ? "未绑定" : getSp(Const.MOBILE));
    }

    private void setAppVersion() {
        txt_ver.setText(VersionUtil.getVersionName());
    }

    private void setUserMessage() {
        txt_nickname.setText(getSp(Const.NICK));
        XGlideLoader.displayImageCircular(this, getSp(Const.HEAD), img_head);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit_login:
                new LingShouPersonDataBean().setPersonData(null);
                Intent intent = new Intent();
                intent.setAction(Const.LOGIN_ACTION);
                sendBroadcast(intent);
                //优惠券
                Intent intentYouHuiQuan = new Intent();
                intentYouHuiQuan.setAction(Const.YOUHUIQUAN_CHANGE);
                sendBroadcast(intentYouHuiQuan);

                Intent intentDevice = new Intent();
                intentDevice.setAction(Const.DEVICE_CHANGE);
                sendBroadcast(intentDevice);
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
            case R.id.re_nick:
                doUpdateDevice("修改昵称");
                break;
            case R.id.re_tiaokuan:
                startActivity(
                        new Intent(this, WebActivity.class)
                                .putExtra("url", Const.TIAOKUAN)
                                .putExtra("title", "使用条款与协议"));
                break;
            case R.id.re_kefu:
                alert = new Dialog(this, R.style.callphonedialog);
                View view = View.inflate(this, R.layout.poup_home_callphone, null);
                view.setMinimumWidth(this.getWindowManager().getDefaultDisplay().getWidth() - 100);
                tvTitle = (RadioButton) view.findViewById(R.id.tvTitle);
                tvTitle.setChecked(true);
                tvTitle.setText("电话:" + appConfigBean.getCustomer_phone().getTel());
                RadioButton tvMessage = (RadioButton) view.findViewById(R.id.tvMessage);
//                tvMessage.setText("电话:" + listEntity1.getPhone());
                tvMessage.setVisibility(View.GONE);
                TextView tvBtnLeft = (TextView) view.findViewById(R.id.tvBtnLeft);
                tvBtnLeft.setOnClickListener(this);
                TextView tvBtnRight = (TextView) view.findViewById(R.id.tvBtnRight);
                tvBtnRight.setOnClickListener(this);
                alert.setContentView(view);
                alert.show();
                break;
            case R.id.tvBtnLeft:
                alert.dismiss();
                break;
            case R.id.tvBtnRight:
                try {
                    String number = "";
                    number = appConfigBean.getCustomer_phone().getTel();
                    if (number.equals("")) {
                        MAlert.alert("当前电话不可用");
                        return;
                    }
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                    intent.setAction(Intent.ACTION_DIAL);
                    startActivity(intent);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                alert.dismiss();
                break;
            case R.id.re_bindphone:
                startActivity(new Intent(this, BindPhoneActivity.class).putExtra("title","更换绑定"));
                break;
            case R.id.iv_actionbar_left:
                finish();
                break;
        }
    }

    public void doUpdateDevice(final String title) {
        if (saveDialog != null) {
            saveDialog.dismiss();
        }
        saveDialog = new SaveAlertView(this, title, "", getString(R.string.cancel), getString(R.string.ok), 2);
        saveDialog.show();
        saveDialog.setClicklistener(new SaveAlertView.ClickListenerInterface() {
            @Override
            public void doLeft() {
                saveDialog.dismiss();
            }

            @Override
            public void doRight() {
                EditText edit = saveDialog.getEditTextView();
                boolean isEmpty = EmptyUtil.isEmpty(edit);

                if (isEmpty) {
                    MAlert.alert(getString(com.itboye.pondteam.R.string.device_name_empty));
                } else {
                    nickName = EmptyUtil.getCustomText(edit);
                    lingShouPresenter.updateUserMessage(getSp(Const.S_ID), getSp(Const.UID), nickName, 0, sign, email, weixin, company, job_title, loc_country, loc_area);
                }
                saveDialog.dismiss();
            }
        });
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
//            img_head.setTag(pathList.get(0));
            new UploadImageUtils(getSp(Const.UID), "avatar").beginUpload("image", file, this);
        }
    }


    @Override
    public void uploadSuccess(UploadImageBean response) {
        MAlert.alert("头像上传成功");
        SPUtils.put(this, null, Const.HEAD, Const.imgurl + response.getData().get(0).getId());
        setUserMessage();
        //通知MeFragment更改用户信息
        Intent intent = new Intent(Const.LOGIN_ACTION);
        sendBroadcast(intent);
    }

    @Override
    public void uploadFail(VolleyError error) {
        MAlert.alert("头像上传失败  {\n" + error.getMessage() + "}");
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }

            if (entity.getEventType() == LingShouPresenter.updateUserMessage_success) {
                MAlert.alert(entity.getData());
                //更新用户资料
                SPUtils.put(this, null, Const.NICK, nickName);
                setUserMessage();
                //通知MeFragment更改用户信息
                Intent intent = new Intent(Const.LOGIN_ACTION);
                sendBroadcast(intent);
            } else if (entity.getEventType() == LingShouPresenter.updateUserMessage_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.getAppConfig_success) {
                appConfigBean = (AppConfigBean) entity.getData();

            } else if (entity.getEventType() == LingShouPresenter.getAppConfig_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
