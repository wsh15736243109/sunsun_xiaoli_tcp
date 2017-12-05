package com.itboye.lingshou.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.PersonDataBean;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.LingShouPersonDataBean;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.BindPhoneActivity;

import static com.itboye.pondteam.utils.Const.getDeviceToken;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler, Observer {

    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;

    private Button gotoBtn, regBtn, launchBtn, checkBtn, scanBtn;

    private IWXAPI api;
    LingShouPresenter lingShouPresenter;
    App app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getApplication();
        lingShouPresenter = new LingShouPresenter(this);
        api = app.getIwxapi();
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        String code = ((SendAuth.Resp) resp).code;
        int result = 0;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (resp.transaction.equals(Const.WX_LOGIN)) {
                    lingShouPresenter.wxLogin(getDeviceToken(), "android", code);
                } else {

                }
                result = R.string.errcode_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }
    }


    private void goToShowMsg(ShowMessageFromWX.Req showReq) {
//		WXMediaMessage wxMsg = showReq.message;
//		WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
//
//		StringBuffer msg = new StringBuffer();
//		msg.append("description: ");
//		msg.append(wxMsg.description);
//		msg.append("\n");
//		msg.append("extInfo: ");
//		msg.append(obj.extInfo);
//		msg.append("\n");
//		msg.append("filePath: ");
//		msg.append(obj.filePath);
//
//		Intent intent = new Intent(this, ShowFromWXActivity.class);
//		intent.putExtra(Constants.ShowMsgActivity.STitle, wxMsg.title);
//		intent.putExtra(Constants.ShowMsgActivity.SMessage, msg.toString());
//		intent.putExtra(Constants.ShowMsgActivity.BAThumbData, wxMsg.thumbData);
//		startActivity(intent);
//		finish();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg() + "fail11");
                return;
            }
            if (entity.getEventType() == LingShouPresenter.wxLogin_success) {
                PersonDataBean personDataBean = (PersonDataBean) entity.getData();
                if (personDataBean != null) {
                    MAlert.alert(personDataBean.toString());
                    MAlert.alert("登录成功");
                    new LingShouPersonDataBean().setPersonData(personDataBean);
                    Intent intent = new Intent();
                    intent.setAction(Const.LOGIN_ACTION);
                    sendBroadcast(intent);

                    //通知首页设备列表更新设备
                    Intent intentDevice = new Intent();
                    intentDevice.setAction(Const.DEVICE_CHANGE);
                    sendBroadcast(intentDevice);
                    //通知《登录》界面finish
                    if (app.lingshouLogin != null) {
                        app.lingshouLogin.finish();
                    }
                    //通知《登录或者注册》界面finish
                    if (app.lingShouSwitchRL != null) {
                        app.lingShouSwitchRL.finish();
                    }
                    if (personDataBean.getPhoneValidate().equals("1")) {
                        //已经绑定过手机
                    } else {
                        //没有绑定跳转绑定手机界面
                        startActivity(new Intent(this, BindPhoneActivity.class).putExtra("title", "绑定手机"));
                    }
                    finish();
                } else {
                    MAlert.alert("获取信息有误");
                }
            } else if (entity.getEventType() == LingShouPresenter.wxLogin_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}