package sunsun.xiaoli.jiarebang.device.led;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.custom.ptr.BasePtr;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.DeviceStatusShow;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.device.FeedbackActivity;
import sunsun.xiaoli.jiarebang.device.VersionUpdateActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.RatioRelativeLayout;
import sunsun.xiaoli.jiarebang.utils.TcpUtil;
import sunsun.xiaoli.jiarebang.utils.loadingutil.CameraConsolePopupWindow;

import static com.itboye.pondteam.custom.ptr.BasePtr.setRefreshTime;
import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static com.itboye.pondteam.utils.ScreenUtil.getDimension;


public class LEDDetailActivity extends BaseActivity implements Observer {

    RelativeLayout re_tuisongsetting, re_tiaoguangsetting, re_shiduansetting, re_moshichange;
    ImageView img_back, img_switch, img_right, img_tuisong_status;
    @IsNeedClick
    TextView txt_title, txt_zhuangtai, txt_moshi_value, txt_ledusetime;
    CameraConsolePopupWindow popupWindow;
    String did = "S0600000001";
    @IsNeedClick
    RatioRelativeLayout re_switchbg;
    boolean isOpen = true;
    private UserPresenter userPresenter;
    private App app;
    String id;
    boolean isTuiSong;
    public DeviceDetailModel detailModel;
    public boolean isConnect;
    private TextView device_status;
    private JSONObject jsonObject;
    private Button buttonZiDong;
    private Button buttonShouDong;
    PtrFrameLayout ptrFrame;
    ImageView loading;
    private TcpUtil mTcpUtil;
    public String ledType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leddetail);
        BasePtr.setRefreshOnlyStyle(ptrFrame);
        ptrFrame.setPtrHandler(ptrHandler);
        app = (App) getApplication();
        app.ledDetailActivity = this;
        id = getIntent().getStringExtra("id");
        did = getIntent().getStringExtra("did");
        userPresenter = new UserPresenter(this);
        userPresenter.deviceSet_led(did, -1, -1, -1, "", -1, -1, -1, -1, -1);
        userPresenter.getDeviceOnLineState(did, "");
        img_right.setBackgroundResource(R.drawable.menu);
        popupWindow = new CameraConsolePopupWindow(
                this, this);
        mTcpUtil = new TcpUtil(handData, did, getSp(Const.UID), "101");
        mTcpUtil.start();
        new Thread(runnable).start();
    }

    public DeviceDetailModel detailModelTcp;
    Handler handData = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 101:
                    System.out.println(mTcpUtil.getMsg() + "----->101 TCP 接收数据");
                    System.out.println(mTcpUtil.getMsg() + "----->101 TCP 接收数据 " + msg.obj);
                    break;
                case 102:
                    detailModelTcp = (DeviceDetailModel) msg.obj;
                    setData();
                    System.out.println(mTcpUtil.getMsg() + "----->102 TCP 接收数据 ");
                    System.out.println(mTcpUtil.getMsg() + "----->102 TCP 接收数据 " + detailModelTcp.getDid());
                    break;
            }
        }
    };

    public void setLoadingIsVisible(boolean is) {
        if (is) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }
    }

    PtrDefaultHandler ptrHandler = new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            if (detailModel != null) {
                setRefreshTime(detailModel.getUpdate_time());
                setLoadingIsVisible(true);
                userPresenter.getDeviceDetailInfo(did, getSp(Const.UID));
            } else {
                ptrFrame.refreshComplete();
            }
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(1);
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            beginRequest();
            handler.postDelayed(runnable, Const.intervalTime);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_right:
                popupWindow.showAtLocation(v, Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.re_moshichange:
                AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);//使用浅色背景 主题
                builder.setCancelable(false);
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                buttonZiDong = new Button(this);
                buttonZiDong.setBackgroundColor(getResources().getColor(R.color.gray));
                buttonZiDong.setText(getString(R.string.mode_zidong));
                buttonZiDong.setTextColor(getResources().getColor(R.color.colorSecondaryText));
                buttonZiDong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAutoMode(true);
                    }
                });
                linearLayout.addView(buttonZiDong);
                View view = new View(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getDimension(this, 1));
                linearLayout.addView(view, layoutParams);
                buttonShouDong = new Button(this);
                buttonShouDong.setText(getString(R.string.mode_shoudong));
                buttonShouDong.setSelected(true);
                buttonShouDong.setTextColor(getResources().getColor(R.color.colorSecondaryText));
                buttonShouDong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAutoMode(false);
                    }
                });
                buttonShouDong.setBackgroundColor(getResources().getColor(R.color.gray));
                linearLayout.addView(buttonShouDong);
                builder.setView(linearLayout);
                setAutoMode(detailModelTcp.getMode().equals("1"));
                builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (detailModelTcp != null) {
                            if (buttonZiDong.isSelected()) {
                                //选中的自动
                                if (detailModelTcp.getMode().equals("1")) {
                                    MAlert.alert(getString(R.string.mode_isauto));
                                    return;
                                }
                                userPresenter.deviceSet_led(did, 1, -1, -1, "", -1, -1, -1, -1, -1);
                            } else {
                                //选中的手动
                                if (detailModelTcp.getMode().equals("0")) {
                                    MAlert.alert(getString(R.string.mode_ismanual));
                                    return;
                                }
                                userPresenter.deviceSet_led(did, 0, -1, -1, "", -1, -1, -1, -1, -1);
                            }
                        }else{

                        }
                    }
                });
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create();
                builder.show();
                break;
            case R.id.re_shiduansetting:
                if (detailModelTcp.getMode().equals("1")) {
                    MAlert.alert(getString(R.string.changeshodongatfirst));
                    return;
                }
                intent = new Intent(this, LEDPeriodSettings.class);
                startActivity(intent);
                break;
            case R.id.re_tiaoguangsetting:
                if (detailModelTcp.getMode().equals("1")) {
                    MAlert.alert(getString(R.string.changeshodongatfirst));
                    return;
                }
                intent = new Intent(this, TiaoGuangActivity.class);
                startActivity(intent);
                break;
            case R.id.re_tuisongsetting:
                break;
            case R.id.tvUpdate:
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                View viewEdit = LayoutInflater.from(this).inflate(R.layout.edit_view, null);
                EditText edit = (EditText) viewEdit.findViewById(R.id.editIntPart);
                showAlertDialog(getString(R.string.nickname), viewEdit, 3, edit);
                break;
            case R.id.pick_upgrade:
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                if (isConnect == false) {
                    MAlert.alert(getString(R.string.disconnect), Gravity.CENTER);
                    return;
                }

                //固件
                intent = new Intent(this,
                        VersionUpdateActivity.class);
                intent.putExtra("did", did);
                intent.putExtra("version", detailModel.getVer());
                intent.putExtra("deviceType", "S06");
                startActivity(intent);
                break;
            case R.id.pick_Delete:
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                showAlertDialog(getString(R.string.tips), null, 4, null);
                break;
            case R.id.pick_share:
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.pick_feedback:
//				反馈
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                intent = new Intent(this, FeedbackActivity.class);
                intent.putExtra("device_type", 3);
                startActivity(intent);
                break;
            case R.id.camera_cancel:
//				取消
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.img_switch:
                if (detailModelTcp.getMode().equals("1")) {
                    MAlert.alert(getString(R.string.changeshodongatfirst));
                    return;
                }
                userPresenter.deviceSet_led(did, -1, -1, -1, "", -1, -1, -1, -1, isOpen ? 0 : 1);
                break;
            case R.id.img_tuisong_status:
                userPresenter.adtExtraUpdate(app.mDeviceUi.mSelectDeviceInfo.getId(), isTuiSong ? "0" : "1");
                break;

        }
    }

    private void setAutoMode(boolean isAuto) {
        buttonShouDong.setSelected(false);
        buttonZiDong.setSelected(false);
        if (!isAuto) {
            buttonShouDong.setSelected(true);
            buttonShouDong.setTextColor(getResources().getColor(R.color.main_green));
            buttonZiDong.setTextColor(getResources().getColor(R.color.colorSecondaryText));
        } else {
            buttonZiDong.setSelected(true);
            buttonZiDong.setTextColor(getResources().getColor(R.color.main_green));
            buttonShouDong.setTextColor(getResources().getColor(R.color.colorSecondaryText));
        }
    }

    public void showAlertDialog(String title, View view, final int type, final EditText edit) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        if (type == 1 || type == 2 || type == 3) {
            edit.setHint(title);
            alert.setView(view);
        } else if (type == 4) {
            alert.setMessage(getString(R.string.make_sure_delete));
        } else if (type == 5 || type == 6) {
            alert.setView(view);
        }
        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (type) {
                    case 1:
                        //设置高温
//                        ((TextView)textView).setText(edit.getText());
                        break;
                    case 2:
                        //设置低温
//                        ((TextView)textView).setText(edit.getText());
                        break;
                    case 3:
                        if (edit.getText().toString().equals("")) {
                            MAlert.alert(getString(R.string.device_name_empty));
                            return;
                        }
                        //修改设备名称
                        userPresenter.updateDeviceName(id, edit.getText().toString(), "", "", "", "", -1, -1);
                        break;
                    case 4:
                        //删除设备
                        userPresenter.deleteDevice(id, getSp(Const.UID));
                        break;
                    case 5:
                        //高温报警
                        break;
                    case 6:
                        //低温报警
                        break;
                }

//                ((TextView) textView).setText(edit.getText());
            }
        });
        alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create();
        alert.show();
    }

    private void switchTurn(boolean isOpen) {
        if (isOpen) {
            if (ledType.equals("ADT-C")) {
                //水草版
                re_switchbg.setBackgroundResource(R.drawable.switch_bg_red);
            } else {
                //海水版
                re_switchbg.setBackgroundResource(R.drawable.switch_bg_blue);
            }
            img_switch.setBackgroundResource(R.drawable.led_switch_white);
        } else {
            re_switchbg.setBackgroundColor(getResources().getColor(R.color.gray_c9));
            img_switch.setBackgroundResource(R.drawable.led_switch_black);
        }
//        this.isOpen = !isOpen;
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        ptrFrame.refreshComplete();
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                finish();
                return;
            }
            if (entity.getEventType() == UserPresenter.update_devicename_success) {
                MAlert.alert(entity.getData());
                app.mDeviceUi.getDeviceList();
                beginRequest();
            } else if (entity.getEventType() == UserPresenter.update_devicename_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.deleteDevice_success) {
                MAlert.alert(entity.getData());
                finish();
            } else if (entity.getEventType() == UserPresenter.deleteDevice_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.getdeviceinfosuccess) {
                detailModel = (DeviceDetailModel) entity.getData();
                if (detailModel != null) {
                    setData();
                }
            } else if (entity.getEventType() == UserPresenter.getdeviceinfofail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.adtExtraUpdate_success) {
                MAlert.alert(entity.getData());
                app.mDeviceUi.getDeviceList();
                beginRequest();
            } else if (entity.getEventType() == UserPresenter.adtExtraUpdate_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.deviceSet_led_success) {
                MAlert.alert(entity.getData());
                beginRequest();
            } else if (entity.getEventType() == UserPresenter.deviceSet_led_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.getDeviceOnLineState_success) {
                DeviceDetailModel deviceDetailModel = (DeviceDetailModel) entity.getData();
                ledType = deviceDetailModel.getDevice_type();
            } else if (entity.getEventType() == UserPresenter.getDeviceOnLineState_success) {
                ledType = "ADT-C";
            }
        }
    }

    public void beginRequest() {
        userPresenter.getDeviceDetailInfo(did, getSp(Const.UID));
    }

    @IsNeedClick
    RelativeLayout re_status_bottom;

    public void setData() {
        isConnect = detailModel.getIs_disconnect().equals("0");
        DeviceStatusShow.setDeviceStatus(device_status, detailModel.getIs_disconnect());
        txt_title.setText(detailModel.getDevice_nickname());
        try {
            jsonObject = new JSONObject(detailModel.getExtra());
            if (jsonObject.has("push")) {
                isTuiSong = jsonObject.getString("push").equals("1");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (detailModelTcp != null) {
            isOpen = detailModelTcp.getSw() == 1;
            switchTurn(isOpen);
            img_tuisong_status.setBackgroundResource(isTuiSong ? R.drawable.kai : R.drawable.guan);
            txt_moshi_value.setText(getString(R.string.current_mode) + "" + (detailModelTcp.getMode().equals("0") ? getString(R.string.manual) : getString(R.string.auto)));
            txt_zhuangtai.setText(Html.fromHtml(getString(R.string.current_status) + "<font color=\"#00bbaa\">" + (detailModelTcp.getMode().equals("0") ? getString(R.string.manual) : getString(R.string.auto)) + "，" + (isOpen ? getString(R.string.opening) : getString(R.string.close)) + "</font>"));
            String per = detailModelTcp.getPer();
            if (app.ledPeriodSettingsUI != null) {
                app.ledPeriodSettingsUI.setData(per);
            }
//            if (app.tiaoGuangUI != null) {
//                app.tiaoGuangUI.initProgress();
//            }
        }
//        if (detailModelTcp!=null) {
//
//        }
//        isOpen = detailModel.getSw() == 1;
//        switchTurn(isOpen);
//        img_tuisong_status.setBackgroundResource(isTuiSong ? R.drawable.kai : R.drawable.guan);
//        txt_moshi_value.setText(getString(R.string.current_mode) + "" + (detailModel.getMode().equals("0") ? getString(R.string.manual) : getString(R.string.auto)));
//        txt_zhuangtai.setText(Html.fromHtml(getString(R.string.current_status) + "<font color=\"#00bbaa\">" + (detailModel.getMode().equals("0") ? getString(R.string.manual) : getString(R.string.auto)) + "，" + (isOpen ? getString(R.string.opening) : getString(R.string.close)) + "</font>"));
//        String per = detailModel.getPer();
//        if (app.ledPeriodSettingsUI != null) {
//            app.ledPeriodSettingsUI.setData(per);
//        }
//        if (app.tiaoGuangUI != null) {
//            app.tiaoGuangUI.initProgress();
//        }
    }
}
