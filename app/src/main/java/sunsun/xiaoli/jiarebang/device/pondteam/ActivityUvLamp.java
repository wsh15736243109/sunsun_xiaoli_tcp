package sunsun.xiaoli.jiarebang.device.pondteam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.itboye.pondteam.R;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.custom.ptr.BasePtr;
import com.itboye.pondteam.enums.SetType;
import com.itboye.pondteam.popwindow.CustomTimePickerDialog;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.math.BigInteger;
import java.util.Observable;
import java.util.Observer;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import sunsun.xiaoli.jiarebang.app.App;

import static com.itboye.pondteam.enums.SetType.GengXinWeiHu;
import static com.itboye.pondteam.utils.ColoTextUtil.setDifferentSizeForTextView;
import static com.itboye.pondteam.utils.NumberUtils.parse2Number;
import static com.itboye.pondteam.volley.TimesUtils.localToUTC;
import static com.itboye.pondteam.volley.TimesUtils.utc2Local;


/**
 * Created by Administrator on 2017/3/7.
 */
public class ActivityUvLamp extends BaseActivity implements Observer {
    ImageView img_back;
    String title;
    TextView txt_title;
    TextView txt_totalhour, txt_shajundeng_close_time, txt_shajundeng_open_time;
    ImageView qingling, changeweihu, toggle_exception_warn;
    RelativeLayout re_closetime, re_opentime;
    private String did;
    UserPresenter userPresenter = null;
    App myApp;
    private boolean isQingXiTiShi;
    private boolean isYiChangBaoJing;
    private boolean isQingLing;
    RelativeLayout re_mode_selection;
    PtrFrameLayout ptr;
    Handler handlerRefresh = new Handler();
    TextView txt_shajundeng_mode_selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uvlamp);
        myApp = (App) getApplication();
        myApp.uvLampUI = this;
        BasePtr.setRefreshOnlyStyle(ptr);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                myApp.pondDeviceDetailUI.beginRequst();
//                BasePtr.setRefreshTime(myApp.pondDeviceDetailUI.deviceDetailModel.getUpdate_time());
                handlerRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptr.refreshComplete();
                    }
                }, 3000);
            }
        });
        title = getIntent().getStringExtra("title");
        did = getIntent().getStringExtra("did");
        txt_title.setText(title);
        userPresenter = new UserPresenter(this);
        isQingLing = false;
        setLampData();
    }

    @Override
    protected void onDestroy() {
        myApp.uvLampUI = null;
        super.onDestroy();
    }

    private void setData(SetType setType, boolean isChecked) {
        String value1 = "0";
        String value2 = "0";
        BigInteger src1 = null;
        switch (setType) {
            case GengXinWeiHu:
                if (isChecked) {
                    value1 = "1";//第一位为更换维护提示  第二位为异常报警
                } else {
                    value1 = "0";
                }
                boolean isYiChangWarn = isYiChangBaoJing;
                if (isYiChangWarn) {
                    value2 = "1";//第一位为更换维护提示  第二位为异常报警
                } else {
                    value2 = "0";
                }
                src1 = new BigInteger(value1 + value2, 2);//转换为BigInteger类型
                //更新维护提示
                userPresenter.deviceSet(
                        myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), null, null,
                        "",
                        -1,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        src1 + "", "",
                        -1,
                        -1, "", "", "", "", -1, -1,"");
                break;
            case YiChangBaoJing:
                if (isChecked) {
                    value2 = "1";//第一位为更换维护提示  第二位为异常报警
                } else {
                    value2 = "0";
                }
                boolean isQingxitishi = isQingXiTiShi;

                if (isQingxitishi) {
                    value1 = "1";//第一位为更换维护提示  第二位为异常报警
                } else {
                    value1 = "0";
                }
                src1 = new BigInteger(value1 + value2, 2);//转换为BigInteger类型
                //异常报警
                userPresenter.deviceSet(
                        myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), null, null,
                        "",
                        -1,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        src1 + "", "",
                        -1,
                        -1, "", "", "", "", -1, -1,"");
                break;
            case QingLing:
                //累计使用时间清零
                if (isChecked == true) {
                    userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), null, null, "", -1, "", "", "", "", "", "", "0", "", "", -1, -1, "", "", "", "", -1, -1,"");
                }
                break;
        }
    }

    public void setLampData() {
        if (myApp.pondDeviceDetailUI.detailModelTcp != null) {
            int startPo = (myApp.pondDeviceDetailUI.detailModelTcp.getUv_wh() + "").length();
            int endPo = (myApp.pondDeviceDetailUI.detailModelTcp.getUv_wh() + " " + getString(R.string.hour)).length();
            //累计使用小时
            setDifferentSizeForTextView(startPo, endPo, myApp.pondDeviceDetailUI.detailModelTcp.getUv_wh() + " " + getString(R.string.hour), txt_totalhour);
//        String tempOpenTime = myApp.pondDeviceDetailUI.deviceDetailModel.getUv_on().substring(0, 2) + ":" + myApp.pondDeviceDetailUI.deviceDetailModel.getUv_on().substring(2, 4);
//        String tempCloseTime = myApp.pondDeviceDetailUI.deviceDetailModel.getUv_off().substring(0, 2) + ":" + myApp.pondDeviceDetailUI.deviceDetailModel.getUv_off().substring(2, 4);
            String utcOnTime = utc2Local(myApp.pondDeviceDetailUI.detailModelTcp.getUv_on()==null?"00":myApp.pondDeviceDetailUI.detailModelTcp.getUv_on(), "HHmm", "HH:mm");
            String utcOffTime = utc2Local(myApp.pondDeviceDetailUI.detailModelTcp.getUv_off()==null?"00":myApp.pondDeviceDetailUI.detailModelTcp.getUv_off(), "HHmm", "HH:mm");
            txt_shajundeng_close_time.setText(utcOffTime);
            txt_shajundeng_open_time.setText(utcOnTime);
//        两个开关合一  首位为更换维护提示，第二位为异常报警
            String cfg = myApp.pondDeviceDetailUI.detailModelTcp.getUv_cfg()==null?"0":myApp.pondDeviceDetailUI.detailModelTcp.getUv_cfg();
            if (cfg.equals("0")) {
                //数据格式 00
                isQingXiTiShi = false;
                isYiChangBaoJing = false;
            } else if (cfg.equals("1")) {
                //数据格式 01
                isQingXiTiShi = false;
                isYiChangBaoJing = true;
            } else if (cfg.equals("2")) {
                //数据格式 10
                isQingXiTiShi = true;
                isYiChangBaoJing = false;
            } else if (cfg.equals("3")) {
                //数据格式 11
                isQingXiTiShi = true;
                isYiChangBaoJing = true;
            }
            setCheck(changeweihu, isQingXiTiShi);
            setCheck(toggle_exception_warn, isYiChangBaoJing);
            //累计使用清零
            setCheck(qingling, isQingLing);
            setMode();
        }
    }

    private void setMode() {
        int uvState = myApp.pondDeviceDetailUI.detailModelTcp.getUv_state();
        int uvCfg = Integer.parseInt(myApp.pondDeviceDetailUI.detailModelTcp.getUv_cfg());
        if ((uvCfg & (int) Math.pow(2, 2)) == (int) Math.pow(2, 2)) {
            //手动模式
            if (uvState == 0) {
                //0关闭
                tempType = 3;
                txt_shajundeng_mode_selection.setText(getString(R.string.mode_shoudong_close));
            } else {
                //1打开
                tempType = 2;
                txt_shajundeng_mode_selection.setText(getString(R.string.mode_shoudong_open));
            }
        } else {
            //自动模式
            tempType = 1;
            txt_shajundeng_mode_selection.setText(getString(R.string.mode_zidong));
        }
    }

    private void setCheck(ImageView qingxitishi, boolean isQingXiTiShi) {
        if (isQingXiTiShi) {
            qingxitishi.setBackgroundResource(R.drawable.kai);
        } else {
            qingxitishi.setBackgroundResource(R.drawable.guan);
        }
    }

    Dialog mPicker = null;

    @Override
    public void onClick(View v) {
        String times = "00:00";

        int i = v.getId();
        if (i == R.id.img_back) {
            finish();

        } else if (i == R.id.re_closetime || i == R.id.txt_shajundeng_close_time) {
            if (!myApp.pondDeviceDetailUI.isConnect) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            times = txt_shajundeng_close_time.getText().toString();
            setOpenOrCloseTime(times, false);

        } else if (i == R.id.re_opentime || i == R.id.txt_shajundeng_open_time) {
            if (!myApp.pondDeviceDetailUI.isConnect) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            times = txt_shajundeng_open_time.getText().toString();
            setOpenOrCloseTime(times, true);

        } else if (i == R.id.qingling) {
            if (!myApp.pondDeviceDetailUI.isConnect) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getString(R.string.tips));
            alert.setMessage(getString(R.string.make_sure_qingling));
            alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showProgressDialog(getString(R.string.posting), true);
                    setData(SetType.QingLing, !isQingLing);
                }
            });
            alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();

        } else if (i == R.id.toggle_exception_warn) {
            if (!myApp.pondDeviceDetailUI.isConnect) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            showProgressDialog(getString(R.string.posting), true);
            setData(SetType.YiChangBaoJing, !isYiChangBaoJing);

        } else if (i == R.id.changeweihu) {
            if (!myApp.pondDeviceDetailUI.isConnect) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            showProgressDialog(getString(R.string.posting), true);
            setData(GengXinWeiHu, !isQingXiTiShi);

        } else if (i == R.id.re_mode_selection) {
            showModeSelctionPopupWindow();
        }
    }

    int tempType = 0;

    private void showModeSelctionPopupWindow() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        View view = View.inflate(this, R.layout.item_mode_selection, null);
        final TextView txt_shoudong_open = (TextView) view.findViewById(R.id.txt_shoudong_open);
        final TextView txt_shoudong_close = (TextView) view.findViewById(R.id.txt_shoudong_close);
        final TextView txt_auto = (TextView) view.findViewById(R.id.txt_auto);
        switch (tempType) {
            case 1:
                txt_auto.setTextColor(getResources().getColor(R.color.main_green));
                break;
            case 2:
                txt_shoudong_open.setTextColor(getResources().getColor(R.color.main_green));
                break;
            case 3:
                txt_shoudong_close.setTextColor(getResources().getColor(R.color.main_green));
                break;
        }
        txt_shoudong_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectColor(v, txt_shoudong_close, txt_auto);
            }

        });
        txt_shoudong_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectColor(v, txt_shoudong_open, txt_auto);
            }
        });
        txt_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectColor(v, txt_shoudong_close, txt_shoudong_open);
            }
        });
        alert.setView(view);
        alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setPositiveButton(getString(R.string.submit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //模式选择
                /**
                 *   uvCfg	1	int		杀菌灯设置	Bit0：更换维护提示
                 *     1使能，0禁止
                 *   Bit1：异常报警
                 *     1使能，0禁止
                 *   Bit2：杀菌灯控制模式
                 *     0：自动模式，1：手动模式
                 *   uvState	1	int		杀菌灯状态	Bit0：开关状态
                 *     1打开，0关闭
                 */
                int valueUvState = myApp.pondDeviceDetailUI.detailModelTcp.getUv_state();
                int valueUvCfg = Integer.parseInt(myApp.pondDeviceDetailUI.detailModelTcp.getUv_cfg());
                if (txt_shoudong_open.isSelected()) {
                    //手动模式开
                    if (((Integer.parseInt(myApp.pondDeviceDetailUI.detailModelTcp.getUv_cfg()) & (int) Math.pow(2, 2)) == (int) Math.pow(2, 2))
                            && (myApp.pondDeviceDetailUI.detailModelTcp.getUv_state() == 1)) {
                        MAlert.alert(getString(R.string.mode_isshoudong_open));
                    } else {
                        if ((Integer.parseInt(myApp.pondDeviceDetailUI.detailModelTcp.getUv_cfg()) & (int) Math.pow(2, 2)) == (int) Math.pow(2, 2)) {

                        } else {
                            valueUvCfg = Integer.parseInt(myApp.pondDeviceDetailUI.detailModelTcp.getUv_cfg()) ^ (int) Math.pow(2, 2);
                        }
                        valueUvState = 1;
                    }
                } else if (txt_shoudong_close.isSelected()) {
                    //手动模式关
                    if (((Integer.parseInt(myApp.pondDeviceDetailUI.detailModelTcp.getUv_cfg()) & (int) Math.pow(2, 2)) == (int) Math.pow(2, 2))
                            && (myApp.pondDeviceDetailUI.detailModelTcp.getUv_state() == 0)) {
                        MAlert.alert(getString(R.string.mode_isshoudong_close));
                    } else {
                        if ((Integer.parseInt(myApp.pondDeviceDetailUI.detailModelTcp.getUv_cfg()) & (int) Math.pow(2, 2)) == (int) Math.pow(2, 2)) {

                        } else {
                            valueUvCfg = Integer.parseInt(myApp.pondDeviceDetailUI.detailModelTcp.getUv_cfg()) ^ (int) Math.pow(2, 2);
                        }
                        valueUvState = 0;
                    }
                } else if (txt_auto.isSelected()) {
                    valueUvState = 0;
                    //自动模式
                    if (myApp.pondDeviceDetailUI.detailModelTcp.getUv_state() == 0) {
                        MAlert.alert(getString(R.string.mode_isauto));
                    } else {
                        valueUvCfg = Integer.parseInt(myApp.pondDeviceDetailUI.detailModelTcp.getUv_cfg()) ^ (int) Math.pow(2, 2);
                        valueUvState = 0;
                    }
                }
                userPresenter.deviceSet(did, null, null, "", -1, "", "", "", "", "", "", "", valueUvCfg + "", valueUvState + "", -1, -1, "", "", "", "", -1, -1,"");
            }
        });
        alert.create();
        alert.show();
    }

    private void setSelectColor(View v, TextView t1, TextView t2) {
        TextView textView = (TextView) v;
        textView.setSelected(true);
        textView.setTextColor(getResources().getColor(R.color.main_green));
        t1.setTextColor(getResources().getColor(R.color.black));
        t1.setSelected(false);
        t2.setTextColor(getResources().getColor(R.color.black));
        t2.setSelected(false);
    }

    /**
     * @param times 当前时间
     * @param b     是否设置开启或关闭  true:设置开启时间  false:设置关闭时间
     */
    private void setOpenOrCloseTime(String times, final boolean b) {
        mPicker = new CustomTimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String tempHour = hourOfDay + "";
                String tempMinute = minute + "";
                if (hourOfDay < 10) {
                    tempHour = parse2Number(hourOfDay + "");
                }
                if (minute < 10) {
                    tempMinute = parse2Number(minute + "");
                }
                tempHour = localToUTC(tempHour, "HH", "HH");
                showPeriod(b, tempHour + ":" + tempMinute, b ? txt_shajundeng_open_time : txt_shajundeng_close_time);
            }
        }, Integer.parseInt(times.split(":")[0]), Integer.parseInt(times.split(":")[1]), true, getString(R.string.shajundeng_close_time));
        mPicker.show();
    }

    private void showPeriod(final boolean b, String s, TextView textView) {
        String tempValue = s.replace(":", "");
        if (b) {//设置开启时间
            userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), null, null, "", -1, "", "", "", "", tempValue, "", "", "", "", -1, -1, "", "", "", "", -1, -1,"");
        } else {//设置关闭时间
            userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), null, null, "", -1, "", "", "", "", "", tempValue, "", "", "", -1, -1, "", "", "", "", -1, -1,"");
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
                MAlert.alert(resultEntity.getMsg());
                return;
            }
            if (resultEntity.getEventType() == UserPresenter.deviceSet_success) {
                MAlert.alert(resultEntity.getData());
            } else if (resultEntity.getEventType() == UserPresenter.deviceSet_fail) {
                MAlert.alert(resultEntity.getData());
            }
        }
    }


}
