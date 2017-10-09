package sunsun.xiaoli.jiarebang.device.pondteam;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.itboye.pondteam.R;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.custom.ptr.BasePtr;
import com.itboye.pondteam.custom.wheelview.view.WheelPicker;
import com.itboye.pondteam.enums.SetType;
import com.itboye.pondteam.popwindow.CustomTimePickerDialog;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.CaculateDays;
import com.itboye.pondteam.utils.MyTimeUtil;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.math.BigInteger;
import java.util.Observable;
import java.util.Observer;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import sunsun.xiaoli.jiarebang.app.App;

import static com.itboye.pondteam.utils.ColoTextUtil.setDifferentSizeForTextView;
import static com.itboye.pondteam.utils.NumberUtils.parse2Number;
import static com.itboye.pondteam.volley.TimesUtils.getNumByWeek;
import static com.itboye.pondteam.volley.TimesUtils.localToUTC;
import static com.itboye.pondteam.volley.TimesUtils.utc2Local;


/**
 * Created by Administrator on 2017/3/7.
 */
public class ActivityZiDong extends BaseActivity implements WheelPicker.OnItemSelectedListener, Observer {
    ImageView img_back;
    String title;
    TextView txt_title;
    //    DeviceDetailModel model;
    TextView txt_zhouqi, txt_shijiansheding, txt_moshi;
    ImageView qingxitishi, toggle_exception_warn, toggle_dingshi;
    private CustomTimePickerDialog mPicker;
    RelativeLayout re_settime, re_setperiod, re_savewatermode;
    UserPresenter userPresenter;
    App myApp;
    String currentWeek;//当前星期
    private boolean isdingshiqingxi, isQingXiTiShi, isYiChangBaoJing;
    PtrFrameLayout ptr;
    Handler handlerRefresh = new Handler();
    TextView txt_not_time, days, hours, mins, secs, txt_ph;
    @IsNeedClick
    TextView savewatermode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zidong);
        title = getIntent().getStringExtra("title");
        BasePtr.setRefreshOnlyStyle(ptr);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                myApp.pondDeviceDetailUI.beginRequst();
                BasePtr.setRefreshTime(myApp.pondDeviceDetailUI.deviceDetailModel.getUpdate_time());
                handlerRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptr.refreshComplete();
                    }
                }, 3000);
            }
        });
        myApp = (App) getApplication();
        myApp.ziDongUI = this;
        userPresenter = new UserPresenter(this);

        setZiDongData();
        txt_title.setText(getString(R.string.cleabytimer));
        txt_title.setTextColor(getResources().getColor(R.color.main_green));
        setCheckLisenter();

        new Thread(runnable).start();
    }

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
            if (!myApp.pondDeviceDetailUI.deviceDetailModel.getIs_disconnect().equals("0")) {
                txt_moshi.setVisibility(View.GONE);
                txt_ph.setVisibility(View.GONE);
                txt_not_time.setVisibility(View.VISIBLE);
                txt_not_time.setText(getString(R.string.cleabytimer) + ":" + getString(R.string.close));
                days.setVisibility(View.GONE);
                hours.setVisibility(View.GONE);
                mins.setVisibility(View.GONE);
                secs.setVisibility(View.GONE);
            } else {
                if (isdingshiqingxi) {
                    String time1 = MyTimeUtil.getWeek(System.currentTimeMillis());
//                String time2 = getDays(txt_zhouqi.getText().toString()) + "," + txt_shijiansheding.getText().toString();
                    String time = myApp.pondDeviceDetailUI.tempTime.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
                    String str = CaculateDays.caculate(getNumByWeek(time1.split(":")[3]) + "," + time1.substring(0, 5), time) + "," + ((60 - Integer.parseInt(time1.split(":")[2])) == 60 ? "00" : (60 - Integer.parseInt(time1.split(":")[2])));
                    String[] strs = str.split(",");
                    String str1 = strs[0] + "days";
                    int startPo1 = strs[0].length();
                    int endPo1 = str1.length();
                    setDifferentSizeForTextView(startPo1, endPo1, str1, days);

                    String str2 = strs[1] + "hour";
                    int startPo2 = strs[1].length();
                    int endPo2 = str2.length();
                    setDifferentSizeForTextView(startPo2, endPo2, str2, hours);

                    String str3 = strs[2] + "mins";
                    int startPo3 = strs[2].length();
                    int endPo3 = str3.length();
                    setDifferentSizeForTextView(startPo3, endPo3, str3, mins);

                    String str4 = strs[3] + "secs";
                    int startPo4 = strs[3].length();
                    int endPo4 = str4.length();
                    setDifferentSizeForTextView(startPo4, endPo4, str4, secs);
                    txt_ph.setVisibility(View.VISIBLE);
                    txt_moshi.setVisibility(View.VISIBLE);
                    txt_not_time.setVisibility(View.GONE);
                    days.setVisibility(View.VISIBLE);
                    hours.setVisibility(View.VISIBLE);
                    mins.setVisibility(View.VISIBLE);
                    secs.setVisibility(View.VISIBLE);
                    txt_not_time.setText(getString(R.string.clean));
                } else {
                    days.setVisibility(View.GONE);
                    hours.setVisibility(View.GONE);
                    mins.setVisibility(View.GONE);
                    secs.setVisibility(View.GONE);
                    txt_moshi.setVisibility(View.GONE);
                    txt_ph.setVisibility(View.GONE);
                    txt_not_time.setVisibility(View.VISIBLE);
                    txt_not_time.setText(getString(R.string.cleabytimer) + ":" + getString(R.string.close));
                }
            }
            handler.postDelayed(runnable, 1000);
        }
    };

    //toggleButton选择/反选事件
    private void setCheckLisenter() {
//        //清洗提示
    }

    @Override
    protected void onDestroy() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        myApp.ziDongUI = null;
        super.onDestroy();

    }

    private void setCheckOrUnCheck(SetType setType, boolean isChecked) {
        String value1 = "0";
        String value2 = "0";
        BigInteger src1 = null;
        switch (setType) {
            case DingShiClean:
                //定时清洗
                userPresenter.deviceSet(
                        myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), null, null,
                        isChecked ? "1" : "0",
                        -1,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "", "", -1, -1, "", "", "", "", -1, -1);
                break;
            case YiChangWarn:
                if (isChecked) {
                    value2 = "1";//第一位为清洗提示设置  第二位为异常报警
                } else {
                    value2 = "0";
                }
                boolean isQingxitishi = isQingXiTiShi;

                if (isQingxitishi) {
                    value1 = "1";//第一位为清洗提示设置  第二位为异常报警
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
                        src1 + "",
                        "",
                        "",
                        "",
                        "", "", -1, -1, "", "", "", "", -1, -1);
                break;
            case QingXiAlert:
                if (isChecked) {
                    value1 = "1";//第一位为清洗提示设置  第二位为异常报警
                } else {
                    value1 = "0";
                }
                boolean isYiChangWarn = isYiChangBaoJing;

                if (isYiChangWarn) {
                    value2 = "1";//第一位为清洗提示设置  第二位为异常报警
                } else {
                    value2 = "0";
                }
                src1 = new BigInteger(value1 + value2, 2);//转换为BigInteger类型
                //清洗提示
                userPresenter.deviceSet(
                        myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), null, null,
                        "",
                        -1,
                        "",
                        "",
                        "",
                        src1 + "",
                        "",
                        "",
                        "",
                        "", "", -1, -1, "", "", "", "", -1, -1);
                break;
        }
    }

    StringBuffer stringWeek = new StringBuffer();

    public String getWeek(int position) {

        /**
         * <item>@string/Monday</item>
         <item>@string/Tuesday</item>
         <item>@string/Wednesday</item>
         <item>@string/Thursday</item>
         <item>@string/Friday</item>
         <item>@string/Saturday</item>
         <item>@string/Sunday</item>
         */
        switch (position) {
            case 0:
                stringWeek.insert(0, getString(R.string.Sunday) + "、");
                break;
            case 1:
                stringWeek.insert(0, getString(R.string.Saturday) + "、");
                break;
            case 2:
                stringWeek.insert(0, getString(R.string.Friday) + "、");
                break;
            case 3:
                stringWeek.insert(0, getString(R.string.Thursday) + "、");
                break;
            case 4:
                stringWeek.insert(0, getString(R.string.Wednesday) + "、");
                break;
            case 5:
                stringWeek.insert(0, getString(R.string.Tuesday) + "、");
                break;
            case 6:
                stringWeek.insert(0, getString(R.string.Monday) + "、");
                break;
            default:
                break;
        }
        return stringWeek.toString().contains("、") ? stringWeek.toString().substring(0, stringWeek.toString().length() - 1) : stringWeek.toString();
    }

    public void setZiDongData() {
        try {


            stringWeek = new StringBuffer();
            if (myApp.pondDeviceDetailUI.detailModelTcp.getCl_week().equals("0")) {
                txt_not_time.setVisibility(View.VISIBLE);
                if (!myApp.pondDeviceDetailUI.detailModelTcp.getIs_disconnect().equals("0")) {
                    txt_moshi.setVisibility(View.GONE);
                    txt_ph.setVisibility(View.GONE);
                    txt_not_time.setVisibility(View.VISIBLE);
                    txt_not_time.setText(getString(R.string.cleabytimer) + ":" + getString(R.string.close));
                } else if (myApp.pondDeviceDetailUI.detailModelTcp.getCl_en().equals("0")) {
                    txt_moshi.setVisibility(View.GONE);
                    txt_ph.setVisibility(View.GONE);
                    txt_not_time.setVisibility(View.VISIBLE);
                    txt_not_time.setText(getString(R.string.cleabytimer) + ":" + getString(R.string.close));
                } else if (myApp.pondDeviceDetailUI.detailModelTcp.getCl_en().equals("1")) {
                    days.setVisibility(View.VISIBLE);
                    hours.setVisibility(View.VISIBLE);
                    mins.setVisibility(View.VISIBLE);
                    secs.setVisibility(View.VISIBLE);
                }
                days.setText("");
                hours.setText("");
                mins.setText("");
                secs.setText("");
                try {
                    //先移除原有的
//                handler.removeCallbacks(runnable);
                } catch (Exception e) {

                }
                txt_moshi.setVisibility(View.GONE);
                txt_zhouqi.setText("");
            } else {
                txt_not_time.setVisibility(View.GONE);
                if (handler != null) {
                    try {
                        //先移除原有的
//                    handler.removeCallbacks(runnable);
                    } catch (Exception e) {

                    }
                }
                int week = Integer.parseInt(myApp.pondDeviceDetailUI.detailModelTcp.getCl_week());
                String binaryWeek = Integer.toBinaryString(week);
                if (binaryWeek.length() < 7) {
                    if (binaryWeek.length() == 6) {
                        binaryWeek = "0" + binaryWeek;
                    } else if (binaryWeek.length() == 5) {
                        binaryWeek = "00" + binaryWeek;
                    } else if (binaryWeek.length() == 4) {
                        binaryWeek = "000" + binaryWeek;
                    } else if (binaryWeek.length() == 3) {
                        binaryWeek = "0000" + binaryWeek;
                    } else if (binaryWeek.length() == 2) {
                        binaryWeek = "00000" + binaryWeek;
                    } else if (binaryWeek.length() == 1) {
                        binaryWeek = "000000" + binaryWeek;
                    } else {
                        binaryWeek = "0";
                    }
                }
                caculateWeek(binaryWeek);
                int position = 0;
                txt_zhouqi.setText(stringWeek.toString().contains("、") ? stringWeek.toString().substring(0, stringWeek.toString().length() - 1) : stringWeek.toString());
                txt_zhouqi.setTag(position);
//            new Thread(runnable).start();
//            txt_moshi.setVisibility(View.VISIBLE);
            }
            String tempTime = utc2Local(myApp.pondDeviceDetailUI.detailModelTcp.getCl_tm(), "HHmm", "HH:mm");
            txt_shijiansheding.setText(tempTime);
            //model.getCl_cfg():两个开关合一  首位为异常报警，第二位为清洗提示
            String cfg = myApp.pondDeviceDetailUI.detailModelTcp.getCl_cfg();
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
            isdingshiqingxi = myApp.pondDeviceDetailUI.detailModelTcp.getCl_en().equals("0") ? false : true;
            setCheck(qingxitishi, isQingXiTiShi);
            setCheck(toggle_exception_warn, isYiChangBaoJing);
            setCheck(toggle_dingshi, isdingshiqingxi);
            int onT = myApp.pondDeviceDetailUI.detailModelTcp.getWs_on_tm();
            int offT = myApp.pondDeviceDetailUI.detailModelTcp.getWs_off_tm();
            //开启30秒关闭30秒为节能50%。   开启30秒关闭15秒为节能30%。
            int seconds = myApp.pondDeviceDetailUI.detailModelTcp.getCl_dur();
            String percent = "";
            switch (seconds) {
                case 60:
                    if (onT == 12 && offT == 18) {
                        percent = "30%";
                        tempType = 3;
                    } else if (onT == 18 && offT == 12) {
                        percent = "50%";
                        tempType = 2;
                    } else {
                        percent = "other";
                    }
                    break;
                case 120:
                    if (onT == 24 && offT == 36) {
                        percent = "30%";
                        tempType = 3;
                    } else if (onT == 24 && offT == 16) {
                        percent = "50%";
                        tempType = 2;
                    } else {
                        percent = "other";
                    }
                    break;
                case 180:
                    if (onT == 24 && offT == 36) {
                        percent = "30%";
                        tempType = 3;
                    } else if (onT == 36 && offT == 24) {
                        percent = "50%";
                        tempType = 2;
                    } else {
                        percent = "other";
                    }
                    break;
                case 240:
                    if (onT == 24 && offT == 36) {
                        percent = "30%";
                        tempType = 3;
                    } else if (onT == 36 && offT == 24) {
                        percent = "50%";
                        tempType = 2;
                    } else {
                        percent = "other";
                    }
                    break;
                case 300:
                    if (onT == 29 && offT == 46) {
                        percent = "30%";
                        tempType = 3;
                    } else if (onT == 43 && offT == 32) {
                        percent = "50%";
                        tempType = 2;
                    } else {
                        percent = "other";
                    }
                    break;
            }

            if (onT == 60 && offT == 0) {
                savewatermode.setText("100%");
                tempType = 1;
            } else {
                savewatermode.setText(percent);
            }
        } catch (Exception e) {

        }
    }

    private void setCheck(ImageView qingxitishi, boolean isQingXiTiShi) {
        if (isQingXiTiShi) {
            qingxitishi.setBackgroundResource(R.drawable.kai);
        } else {
            qingxitishi.setBackgroundResource(R.drawable.guan);
        }
    }

    int bytenum = 0;

    private String caculateWeek(String binaryWeek) {
        char[] chars = binaryWeek.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int index = i;
            char string = chars[i];
            if (string == '1' || (string + "").equals("1")) {
                bytenum += Math.pow(2, (chars.length - 1 - i));
                getWeek(index);
            }
        }
        return stringWeek.toString();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.img_back) {
            finish();

        } else if (i == R.id.re_settime || i == R.id.txt_shijiansheding) {
            if (myApp.pondDeviceDetailUI.isConnect == false) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            String currentTimes = txt_shijiansheding.getText().toString();
            setTimes(currentTimes);

        } else if (i == R.id.re_setperiod || i == R.id.txt_zhouqi) {
            if (myApp.pondDeviceDetailUI.isConnect == false) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            setPeriod();

        } else if (i == R.id.qingxitishi) {
            if (myApp.pondDeviceDetailUI.isConnect == false) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            showProgressDialog(getString(R.string.posting), true);
            setCheckOrUnCheck(SetType.QingXiAlert, !isQingXiTiShi);

        } else if (i == R.id.toggle_exception_warn) {
            if (myApp.pondDeviceDetailUI.isConnect == false) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            showProgressDialog(getString(R.string.posting), true);
            setCheckOrUnCheck(SetType.YiChangWarn, !isYiChangBaoJing);

        } else if (i == R.id.toggle_dingshi) {
            if (myApp.pondDeviceDetailUI.isConnect == false) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            showProgressDialog(getString(R.string.posting), true);
            setCheckOrUnCheck(SetType.DingShiClean, !isdingshiqingxi);

        } else if (i == R.id.re_savewatermode) {
            if (myApp.pondDeviceDetailUI.isConnect == false) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            showSaveWaterModePopupwindow();
        }
    }

    int tempType = 0;

    private void showSaveWaterModePopupwindow() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        View view = View.inflate(this, R.layout.item_mode_selection, null);
        final TextView txt_shoudong_open = (TextView) view.findViewById(R.id.txt_shoudong_open);
        final TextView txt_shoudong_close = (TextView) view.findViewById(R.id.txt_shoudong_close);
        final TextView txt_auto = (TextView) view.findViewById(R.id.txt_auto);
        txt_shoudong_open.setText("100%");
        txt_shoudong_close.setText("50%");
        txt_auto.setText("30%");
        switch (tempType) {
            case 1:
                txt_shoudong_open.setTextColor(getResources().getColor(R.color.main_green));
                break;
            case 2:
                txt_shoudong_close.setTextColor(getResources().getColor(R.color.main_green));
                break;
            case 3:
                txt_auto.setTextColor(getResources().getColor(R.color.main_green));
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
        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int seconds = myApp.pondDeviceDetailUI.detailModelTcp.getCl_dur();
                int onT = 0;
                int offT = 0;
                switch (seconds) {
                    case 60:
                        if (txt_shoudong_open.isSelected()) {//100%
                            onT = 60;
                            offT = 0;
                        } else if (txt_shoudong_close.isSelected()) {//50%
                            onT = 18;
                            offT = 12;
                        } else if (txt_auto.isSelected()) {//30%
                            onT = 12;
                            offT = 18;
                        }
                        break;
                    case 120:
                        if (txt_shoudong_open.isSelected()) {//100%
                            onT = 60;
                            offT = 0;
                        } else if (txt_shoudong_close.isSelected()) {//50%
                            onT = 24;
                            offT = 16;
                        } else if (txt_auto.isSelected()) {//30%
                            onT = 24;
                            offT = 36;
                        }
                        break;
                    case 180:
                        if (txt_shoudong_open.isSelected()) {//100%
                            onT = 60;
                            offT = 0;
                        } else if (txt_shoudong_close.isSelected()) {//50%
                            onT = 36;
                            offT = 24;
                        } else if (txt_auto.isSelected()) {//30%
                            onT = 24;
                            offT = 36;
                        }
                        break;
                    case 240:
                        if (txt_shoudong_open.isSelected()) {//100%
                            onT = 60;
                            offT = 0;
                        } else if (txt_shoudong_close.isSelected()) {//50%
                            onT = 36;
                            offT = 24;
                        } else if (txt_auto.isSelected()) {//30%
                            onT = 24;
                            offT = 36;
                        }
                        break;
                    case 300:
                        if (txt_shoudong_open.isSelected()) {//100%
                            onT = 60;
                            offT = 0;
                        } else if (txt_shoudong_close.isSelected()) {//50%
                            onT = 43;
                            offT = 32;
                        } else if (txt_auto.isSelected()) {//30%
                            onT = 29;
                            offT = 46;
                        }
                        break;
                }

                //30 % 50 %百分比设置的时候   A 设置成 手动打开模式
                int a_state = Integer.parseInt(myApp.pondDeviceDetailUI.detailModelTcp.getOut_state_a());
                if ((a_state & (int) Math.pow(2, 0)) == (int) Math.pow(2, 0)) {
                    //打开模式
                } else {
                    //关闭模式
                    a_state = a_state ^ (int) Math.pow(2, 0);//更改为打开模式
                }
                if ((a_state & (int) Math.pow(2, 1)) == (int) Math.pow(2, 1)) {
                    //定时模式
                    a_state = a_state ^ (int) Math.pow(2, 1);//改为手动模式
                } else {
                    //手动模式
                }
                userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), null, null, "", -1, "", "", "", "", "", "", "", "", "", a_state, -1, "", "", "", "", onT, offT);
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

    WheelPicker myNumberPickerView;
    String tempValue = "";

    private void setPeriod() {
        //1 2 4 8 16 32 64 128周一到周日固定值
        Intent intent = new Intent(this, WenDuPeriodActivity.class);
        intent.putExtra("zhouqi", txt_zhouqi.getText().toString());
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 102) {
            showProgressDialog(getString(R.string.posting), true);
            stringWeek = new StringBuffer();
            bytenum = 0;
            int weekBinary = data.getIntExtra("weekBinary", 0);
//            if (weekBinary==0) {
//                days.setText("You haven't set a cycle");
//                hours.setText("");
//                mins.setText("");
//                secs.setText("");
//                try {
//                    //先移除原有的
//                    handler.removeCallbacks(runnable);
//                } catch (Exception e) {
//
//                }
//                txt_moshi.setVisibility(View.GONE);
//            }else{
            userPresenter.deviceSet(myApp.pondDeviceDetailUI.did, null, null, "", weekBinary, "", "", "", "", "", "", "", "", "", -1, -1, "", "", "", "", -1, -1);
//            }

        }
    }

    private int getPosition2Week(int position) {
        int num = 1;
        switch (position) {
            case 0:
                num = 1;
                break;
            case 1:
                num = 2;
                break;
            case 2:
                num = 4;
                break;
            case 3:
                num = 8;
                break;
            case 4:
                num = 16;
                break;
            case 5:
                num = 32;
                break;
            case 6:
                num = 64;
                break;
        }
        return num;
    }

    public void setTimes(String times) {
        mPicker = new CustomTimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String tempHour = hourOfDay + "";
                String tempMinute = minute + "";
                if (hourOfDay < 10) {
                    tempHour = parse2Number(hourOfDay + "");
                    //转换为UTC时间
                }
                if (minute < 10) {
                    tempMinute = parse2Number(minute + "");
                }
                tempHour = localToUTC(tempHour, "HH", "HH");
//                txt_shijiansheding.setText(tempHour + ":" + tempMinute);
                userPresenter.deviceSet(
                        myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), null, null,
                        "",
                        -1,
                        tempHour + tempMinute,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "", "",
                        -1,
                        -1, "", "", "", "", -1, -1);
            }
        }, Integer.parseInt(times.substring(0, 2)), Integer.parseInt(times.substring(3, 4)), true, getString(R.string.settime));
        mPicker.show();
    }

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {
        tempValue = position + "," + data.toString();
    }

    @Override
    public void update(Observable o, Object data) {
        try {
            closeProgressDialog();
        } catch (Exception e) {

        }
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
//                setZiDongData();
                return;
            }
            if (entity.getEventType() == UserPresenter.deviceSet_success) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.deviceSet_fail) {
                MAlert.alert(entity.getData());
                setZiDongData();
            } else if (entity.getEventType() == UserPresenter.getdeviceinfosuccess) {
                myApp.pondDeviceDetailUI.deviceDetailModel = (DeviceDetailModel) entity.getData();
                setZiDongData();
            } else if (entity.getEventType() == UserPresenter.getdeviceinfofail) {
                MAlert.alert("设备更新失败:" + entity.getData());
            }
        }
    }
}
