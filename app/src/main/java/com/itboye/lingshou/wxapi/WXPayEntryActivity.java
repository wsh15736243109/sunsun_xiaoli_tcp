package com.itboye.lingshou.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {


    private IWXAPI api;
    public static String FinishQueRen = "com.itboye.sunsun.querenzhifu";
    App app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setContentView(R.layout.pay_result);
        api = ((App) getApplication()).getIwxapi();
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                MAlert.alert(getString(R.string.pay_success));
                Intent intent = new Intent("close");
                sendBroadcast(intent);
            } else if (resp.errCode == -2) {
                MAlert.alert(getString(R.string.pay_cancel));
                Intent intent = new Intent("close");
                sendBroadcast(intent);
            }

        }
        this.finish();
    }

    @Override
    public void onClick(View v) {

    }
}