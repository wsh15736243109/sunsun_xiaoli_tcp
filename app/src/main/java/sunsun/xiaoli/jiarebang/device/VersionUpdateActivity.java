package sunsun.xiaoli.jiarebang.device;

import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.R;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.bean.PondTeamMostNewModel;
import com.itboye.pondteam.custom.CustomProgressBarSingle;
import com.itboye.pondteam.custom.XScrollView;
import com.itboye.pondteam.interfaces.SmartConfigTypeSingle;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.app.App;


public class VersionUpdateActivity extends BaseActivity implements Observer {
    private ImageView img_back;// 返回
    private TextView tvVersion, tv_currentVersion, txt_shuoming, txt_tips, txt_current_status;
    TextView txt_title;
    CustomProgressBarSingle my_progress2;
    //版本
//	public AqDeviceInfo mDeviceInfo;
    App myApp;
    UserPresenter userPresenter;
    private PondTeamMostNewModel model;
    RelativeLayout re_wait_update, re_alreadynew;
    String did;
    public boolean isMostNew = true;
    XScrollView scroll;
    public SmartConfigTypeSingle smartConfigType;
    String version;
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_version_update);
        myApp = (App) getApplication();
        showProgressDialog(getString(R.string.posting), true);
        did = getIntent().getStringExtra("did");
        version=getIntent().getStringExtra("version");
        smartConfigType = SmartConfigTypeSingle.UPDATE_INIT;
        my_progress2.setSmartConfigType(smartConfigType);//初始化progressBar
        myApp = (App) getApplication();
        myApp.updateActivityUI = this;
        txt_title.setText(getString(R.string.gujiangengxin));
        userPresenter = new UserPresenter(this);
        userPresenter.getMostNewDevice(did);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            finish();
        }
        if (v.getId() == R.id.my_progress2) {
            if (did.startsWith("S01")) {
                if (myApp.pondDeviceDetailUI.isConnect == false) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
            } else if (did.startsWith("S02")) {
                if (myApp.deviceJiaReBangUI.isConnect == false) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
            } else if (did.startsWith("S03")) {
                if (myApp.jinLiGangdetailUI.isConnect == false) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
            } else if (did.startsWith("S04")) {
                if (myApp.devicePhUI.isConnect == false) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
            } else if (did.startsWith("S05")) {
                if (myApp.deviceShuiBengUI.isConnect == false) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
            } else if (did.startsWith("S06")) {
                if (myApp.ledDetailActivity.isConnect == false) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
            }
            if (model == null) {
                MAlert.alert(getString(R.string.deviceid_error));
                return;
            }
            if (isMostNew) {
                MAlert.alert(getString(R.string.already_most_new));
                finish();
            } else {
//                moni();
                if (my_progress2.getSmartConfigType() == SmartConfigTypeSingle.NO_UPDTE) {
                    MAlert.alert(getString(R.string.already_most_new));
                } else if (my_progress2.getSmartConfigType() == SmartConfigTypeSingle.UPDATE_ING) {
                    MAlert.alert(getString(R.string.update_ing));
                } else {
                    showProgressDialog(getString(R.string.posting), true);
                    userPresenter.beginUpdatePondTeam(did);
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object data) {
        try {
            closeProgressDialog();
        } catch (Exception e) {

        }
        ResultEntity resultEntity = handlerError(data);
        if (resultEntity != null) {
            if (resultEntity.getCode() != 0) {
                MAlert.alert(resultEntity.getMsg() + "  code=" + resultEntity.getCode());
                return;
            }
            if (resultEntity.getEventType() == UserPresenter.getMostNewPondDevice_success) {
                model = (PondTeamMostNewModel) resultEntity.getData();
                float oldV = 0;
                if (did.startsWith("S01")) {
                    oldV = Float.parseFloat(myApp.pondDeviceDetailUI.deviceDetailModel.getVer().substring(1, myApp.pondDeviceDetailUI.deviceDetailModel.getVer().length()));
                } else if (did.startsWith("S02")) {
                    oldV = Float.parseFloat(myApp.deviceJiaReBangUI.deviceDetailModel.getVer().substring(1, myApp.deviceJiaReBangUI.deviceDetailModel.getVer().length()));
                } else if (did.startsWith("S03")) {
                    oldV = Float.parseFloat(myApp.jinLiGangdetailUI.deviceDetailModel.getVer().substring(1, myApp.jinLiGangdetailUI.deviceDetailModel.getVer().length()));
                } else if (did.startsWith("S04")) {
                    oldV = Float.parseFloat(myApp.devicePhUI.deviceDetailModel.getVer().substring(1, myApp.devicePhUI.deviceDetailModel.getVer().length()));
                }else if (did.startsWith("S05")) {
                    oldV = Float.parseFloat(myApp.deviceShuiBengUI.deviceDetailModel.getVer().substring(1, myApp.deviceShuiBengUI.deviceDetailModel.getVer().length()));
                }else if (did.startsWith("S06")) {
                    oldV = Float.parseFloat(myApp.ledDetailActivity.detailModel.getVer().substring(1, myApp.ledDetailActivity.detailModel.getVer().length()));
                }
                oldV=Float.parseFloat((oldV+"").replace(".",""));
                float newV = Float.parseFloat(model.getVersion().substring(1, model.getVersion().length()).replace(".",""));
                isMostNew = (oldV >= newV);
                isMostNew(isMostNew);
            } else if (resultEntity.getEventType() == UserPresenter.getMostNewPondDevice_fail) {
                MAlert.alert(resultEntity.getData());
            } else if (resultEntity.getEventType() == UserPresenter.beginUpdatePondTeam_success) {
                MAlert.alert(resultEntity.getData());
                smartConfigType = SmartConfigTypeSingle.UPDATE_ING;
                my_progress2.setSmartConfigType(smartConfigType);
                closeProgressDialog();
            } else if (resultEntity.getEventType() == UserPresenter.beginUpdatePondTeam_fail) {
                MAlert.alert(resultEntity.getData());
                closeProgressDialog();
            } else if (resultEntity.getEventType() == UserPresenter.getdeviceinfosuccess) {
                DeviceDetailModel model = (DeviceDetailModel) resultEntity.getData();
//                setJindu(deviceDetailModel);
            } else if (resultEntity.getEventType() == UserPresenter.getdeviceinfofail) {
                MAlert.alert(resultEntity.getData());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myApp.updateActivityUI = null;
    }

    private void setJindu(DeviceDetailModel model) {
        setProgress(model.getUpd_state() + "");
    }

    int count = 0;

    public void setProgress(String num) {
        int progress = Integer.parseInt(num);
        float oldV = 0;
        if (did.startsWith("S01")) {
            oldV = Float.parseFloat(myApp.pondDeviceDetailUI.deviceDetailModel.getVer().substring(1, myApp.pondDeviceDetailUI.deviceDetailModel.getVer().length()));
        } else if (did.startsWith("S02")) {
            oldV = Float.parseFloat(myApp.deviceJiaReBangUI.deviceDetailModel.getVer().substring(1, myApp.deviceJiaReBangUI.deviceDetailModel.getVer().length()));
        } else if (did.startsWith("S03")) {
            oldV = Float.parseFloat(myApp.jinLiGangdetailUI.deviceDetailModel.getVer().substring(1, myApp.jinLiGangdetailUI.deviceDetailModel.getVer().length()));
        } else if (did.startsWith("S04")) {
            oldV = Float.parseFloat(myApp.devicePhUI.deviceDetailModel.getVer().substring(1, myApp.devicePhUI.deviceDetailModel.getVer().length()));
        }else if (did.startsWith("S05")) {
            oldV = Float.parseFloat(myApp.deviceShuiBengUI.deviceDetailModel.getVer().substring(1, myApp.deviceShuiBengUI.deviceDetailModel.getVer().length()));
        }else if (did.startsWith("S06")) {
            oldV = Float.parseFloat(myApp.ledDetailActivity.detailModel.getVer().substring(1, myApp.ledDetailActivity.detailModel.getVer().length()));
        }
        float newV = Float.parseFloat(model.getVersion().substring(1, model.getVersion().length()));
        if ((oldV == newV)) {
            isMostNew(true);
            isMostNew = true;
            smartConfigType = SmartConfigTypeSingle.NO_UPDTE;
            my_progress2.setSmartConfigType(smartConfigType);
        } else if (progress == 101) {
            smartConfigType = SmartConfigTypeSingle.UPDATE_FAIL;
            my_progress2.setSmartConfigType(smartConfigType);
        } else {
            count++;
            if (count == 1 && progress == 100) {
                progress = 0;
            } else {

            }
            smartConfigType = SmartConfigTypeSingle.UPDATE_ING;
            my_progress2.setSmartConfigType(smartConfigType);
            setViewUpdateing(true);
            my_progress2.setProgress(progress);
        }
    }

    private void setViewUpdateing(boolean b) {
        if (b) {
            txt_tips.setText(getString(R.string.update_ing));
            txt_current_status.setText(getString(R.string.updateing_description));
        } else {

        }
    }

    private void isMostNew(boolean is) {
        scroll.setVisibility(View.VISIBLE);
        if (is) {
            re_alreadynew.setVisibility(View.VISIBLE);
            re_wait_update.setVisibility(View.GONE);
            tvVersion.setText(Html.fromHtml(getString(R.string.current_version) + " " + version));
            smartConfigType = SmartConfigTypeSingle.NO_UPDTE;
        } else {
            re_alreadynew.setVisibility(View.GONE);
            re_wait_update.setVisibility(View.VISIBLE);
            txt_tips.setText(getString(R.string.hasupdate));
            tv_currentVersion.setText(getString(R.string.version) + model.getVersion());
            txt_shuoming.setText(getString(R.string.update_desciption) + model.getVersion_desc());
            //当前不是最新的版本，需要更新  udp_state=100:上次更新已经完成 101：上次更新失败
            if (did.startsWith("S01")) {
                if (myApp.pondDeviceDetailUI.deviceDetailModel.getUpd_state() <= 0 || myApp.pondDeviceDetailUI.deviceDetailModel.getUpd_state() == 100 || myApp.pondDeviceDetailUI.deviceDetailModel.getUpd_state() == 101) {
                    //当前设备还没有开始更新
                    smartConfigType = SmartConfigTypeSingle.UPDATE_INIT;
                } else {
                    //当前设备正在更新中
                    smartConfigType = SmartConfigTypeSingle.UPDATE_ING;
                }
            } else if (did.startsWith("S02")) {
                if (myApp.deviceJiaReBangUI.deviceDetailModel.getUpd_state() <= 0 || myApp.deviceJiaReBangUI.deviceDetailModel.getUpd_state() == 100 || myApp.deviceJiaReBangUI.deviceDetailModel.getUpd_state() == 101) {
                    //当前设备还没有开始更新
                    smartConfigType = SmartConfigTypeSingle.UPDATE_INIT;
                } else {
                    //当前设备正在更新中
                    smartConfigType = SmartConfigTypeSingle.UPDATE_ING;
                }
            } else if (did.startsWith("S03")) {
                if (myApp.jinLiGangdetailUI.detailModelTcp.getUpd_state() <= 0 || myApp.jinLiGangdetailUI.detailModelTcp.getUpd_state() == 100 || myApp.jinLiGangdetailUI.detailModelTcp.getUpd_state() == 101) {
                    //当前设备还没有开始更新
                    smartConfigType = SmartConfigTypeSingle.UPDATE_INIT;
                } else {
                    //当前设备正在更新中
                    smartConfigType = SmartConfigTypeSingle.UPDATE_ING;
                }
            } else if (did.startsWith("S04")) {
                if (myApp.devicePhUI.deviceDetailModel.getUpd_state() <= 0 || myApp.devicePhUI.deviceDetailModel.getUpd_state() == 100 || myApp.devicePhUI.deviceDetailModel.getUpd_state() == 101) {
                    //当前设备还没有开始更新
                    smartConfigType = SmartConfigTypeSingle.UPDATE_INIT;
                } else {
                    //当前设备正在更新中
                    smartConfigType = SmartConfigTypeSingle.UPDATE_ING;
                }
            }else if (did.startsWith("S05")) {
                if (myApp.deviceShuiBengUI.deviceDetailModel.getUpd_state() <= 0 || myApp.deviceShuiBengUI.deviceDetailModel.getUpd_state() == 100 || myApp.deviceShuiBengUI.deviceDetailModel.getUpd_state() == 101) {
                    //当前设备还没有开始更新
                    smartConfigType = SmartConfigTypeSingle.UPDATE_INIT;
                } else {
                    //当前设备正在更新中
                    smartConfigType = SmartConfigTypeSingle.UPDATE_ING;
                }
            }else if (did.startsWith("S06")) {
                if (myApp.ledDetailActivity.detailModel.getUpd_state() <= 0 || myApp.ledDetailActivity.detailModel.getUpd_state() == 100 || myApp.ledDetailActivity.detailModel.getUpd_state() == 101) {
                    //当前设备还没有开始更新
                    smartConfigType = SmartConfigTypeSingle.UPDATE_INIT;
                } else {
                    //当前设备正在更新中
                    smartConfigType = SmartConfigTypeSingle.UPDATE_ING;
                }
            }

        }
        my_progress2.setSmartConfigType(smartConfigType);
    }


    int pro;

    void moni() {
        this.pro += 5;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                my_progress2.setProgress(VersionUpdateActivity.this.pro);
                if (pro == 100) {
                    smartConfigType = SmartConfigTypeSingle.NO_UPDTE;
                    isMostNew(true);
                } else {
                    smartConfigType = SmartConfigTypeSingle.UPDATE_ING;
                    moni();
                }
                my_progress2.setSmartConfigType(smartConfigType);
            }
        }, 1000);
    }
}
