package sunsun.xiaoli.jiarebang.device.jinligang;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.popwindow.CustomTimePickerDialog;


/**
 * 1. 时段至少保留一个激活项
 * 2. 关闭的时段等于任意一个开启的时段，就是关闭
 */
@SuppressLint("NewApi")
public class PeriodActivity extends BaseActivity implements Observer {

    App mApp;

    Button period_button1;
    Button period_button3;
    Button period_button5;
    Button period_button2;
    Button period_button4;
    Button period_button6;

    ImageView mImageViewPeriodOn1;
    ImageView mImageViewPeriodOn2;
    ImageView mImageViewPeriodOn3;
    ImageView mImageViewPeriodOff1;
    ImageView mImageViewPeriodOff2;
    ImageView mImageViewPeriodOff3;

    ImageView switch_yichangbaojing;//异常报警

    TextView period_textView4, period_textView2, period_textView7, period_textView9, period_textView12, period_textView14;
    TextView period_textView3;
    TextView period_textView8;
    TextView period_textView13;
    TextView period_textView5;
    TextView period_textView10;
    TextView period_textView15;


    ImageView period_switch1;
    ImageView period_switch2;
    ImageView period_switch3;

    // 时段类型，1：照明灯 2：冲浪泵 3：杀菌灯
    int mPeriodType = 0;

    //	AqPeriod mPeriod;
    TextView txt_title;
    ImageView img_back;

    TextView img_kai, img_guan;

    TextView txt_chonglangbeng, txt_shajundeng, txt_zhaoming;

    String titlesBegin[];
    String titlesEnd[];
    Gson gson;
    JSONArray jsonObject = null;
    String[] l_times = new String[]{};
    String[] uvc_times = new String[]{};
    String[] sp_times = new String[]{};
    private Type type1;
    private ArrayList<DeviceDetailModel.TimePeriod> timer1;
    UserPresenter userPresenter;
    /*累计使用功率*//*不保存当前设置的东西*//*恢复出厂设置*/
    TextView textView2, btn_ok, btn_cancel, btn_reset;
    private String str3;//时间段一
    private String str2;//时间段二
    private String str1;//时间段三
    private boolean per1Enable;
    private boolean per2Enable;
    private boolean per3Enable;
    private boolean status3, status2, status1;
    private boolean chonglangbengStatus, shajundengStatus, zhaomingdengStatus;

    ImageView switch_qingling;

    ImageView period_imageView1, period_imageView2, period_imageView3, period_imageView4, period_imageView5, period_imageView6;
    ImageView switch_dongtaitishi;
    private boolean zhaomingdengDongtai, shajundengDongtai, chonglangbengDongtai;
    private int cfg;
    private int total1;//灯光照明累计时间
    private int total2;//杀菌灯累计时间
    private int total3;//冲浪泵累计时间

//    public boolean yanChiFinish = false;//设备返回数据延迟可能性

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period);
        mApp = (App) getApplication();
        titlesBegin = new String[]{getString(R.string.light_zhaoming) + getString(R.string.open_time2), getString(R.string.light_shajun) + getString(R.string.open_time2), getString(R.string.chonglangbeng) + getString(R.string.open_time2)};
        titlesEnd = new String[]{getString(R.string.light_zhaoming) + getString(R.string.close_time2), getString(R.string.light_shajun) + getString(R.string.close_time2), getString(R.string.chonglangbeng) + getString(R.string.close_time2)};
        txt_title.setText("");
        mApp.mPeriodUi = this;
//        yanChiFinish = false;
        gson = new Gson();
        type = 1;
        str1 = mApp.jinLiGangdetailUI.deviceDetailModel.getL_per();
        str2 = mApp.jinLiGangdetailUI.deviceDetailModel.getUvc_per();
        str3 = mApp.jinLiGangdetailUI.deviceDetailModel.getSp_per();
        setData();
        Intent mainIntent = getIntent();
        mPeriodType = mainIntent.getIntExtra("periodType", 1);

        userPresenter = new UserPresenter(this);

        mImageViewPeriodOn1 = (ImageView) findViewById(R.id.period_imageView1);
        mImageViewPeriodOn2 = (ImageView) findViewById(R.id.period_imageView3);
        mImageViewPeriodOn3 = (ImageView) findViewById(R.id.period_imageView5);
        mImageViewPeriodOff1 = (ImageView) findViewById(R.id.period_imageView2);
        mImageViewPeriodOff2 = (ImageView) findViewById(R.id.period_imageView4);
        mImageViewPeriodOff3 = (ImageView) findViewById(R.id.period_imageView6);


        period_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!(Boolean) period_switch1.getTag()) {
//                    MAlert.alert(getString(R.string.turnonfirst));
//                    return;
//                }
                if (!mApp.jinLiGangdetailUI.isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                setTimePicker(period_textView3, 1, true);
            }
        });

        period_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!(Boolean) period_switch1.getTag()) {
//                    MAlert.alert(getString(R.string.turnonfirst));
//                    return;
//                }
                if (!mApp.jinLiGangdetailUI.isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                setTimePicker(period_textView5, 1, false);
            }
        });

        period_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!(Boolean) period_switch2.getTag()) {
//                    MAlert.alert(getString(R.string.turnonfirst));
//                    return;
//                }
                if (!mApp.jinLiGangdetailUI.isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                setTimePicker(period_textView8, 2, true);
            }
        });

        period_button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!(Boolean) period_switch2.getTag()) {
//                    MAlert.alert(getString(R.string.turnonfirst));
//                    return;
//                }
                if (!mApp.jinLiGangdetailUI.isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                setTimePicker(period_textView10, 2, false);
            }
        });

        period_button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!(Boolean) period_switch3.getTag()) {
//                    MAlert.alert(getString(R.string.turnonfirst));
//                    return;
//                }
                if (!mApp.jinLiGangdetailUI.isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                setTimePicker(period_textView13, 3, true);
            }
        });

        period_button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!(Boolean) period_switch3.getTag()) {
//                    MAlert.alert(getString(R.string.turnonfirst));
//                    return;
//                }
                if (!mApp.jinLiGangdetailUI.isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                setTimePicker(period_textView15, 3, false);
            }
        });
    }


    @Override
    protected void onDestroy() {
        mApp.mPeriodUi = null;
        super.onDestroy();
    }

    private void showPeriodTextViewValue(TextView periodTextView, int hour, int min) {
//		Log.d("TEMP", "设置分钟"+min);
//		periodTextView.setText(String.valueOf(hour) + ":" + (String.format("%1$02d", min).equals("60")?"00":String.format("%1$02d", min)));
    }

    public void setTimePicker(final TextView text, int position, boolean isKaiQi) {
        String[] times = text.getText().toString().split(":");
        CustomTimePickerDialog mPicker = new CustomTimePickerDialog(PeriodActivity.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String str = (hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) + ":" + (minute < 10 ? ("0" + minute) : minute);
//                text.setText(str);
                switch (text.getId()) {
                    case R.id.period_textView3:
                        if (!(Boolean.parseBoolean(period_switch1.getTag() + ""))) {
                            period_switch1.setBackgroundResource(R.drawable.kai);
                            period_switch1.setTag(true);
                        }
                        //设置开启时间（时间段1）
                        timer1.get(0).setH0(hourOfDay);
                        timer1.get(0).setM0(minute / 10 * 10);
                        break;
                    case R.id.period_textView5:
                        //设置关闭时间（时间段1）
                        if (!(Boolean.parseBoolean(period_switch1.getTag() + ""))) {
                            period_switch1.setBackgroundResource(R.drawable.kai);
                            period_switch1.setTag(true);
                        }
                        timer1.get(0).setH1(hourOfDay);
                        timer1.get(0).setM1(minute / 10 * 10);
                        break;
                    case R.id.period_textView8:
                        //设置开启时间（时间段2）
                        if (!(Boolean.parseBoolean(period_switch2.getTag() + ""))) {
                            period_switch2.setBackgroundResource(R.drawable.kai);
                            period_switch2.setTag(true);
                        }
                        timer1.get(1).setH0(hourOfDay);
                        timer1.get(1).setM0(minute / 10 * 10);
                        break;
                    case R.id.period_textView10:
                        //设置关闭时间（时间段2）
                        if (!(Boolean.parseBoolean(period_switch2.getTag() + ""))) {
                            period_switch2.setBackgroundResource(R.drawable.kai);
                            period_switch2.setTag(true);
                        }
                        timer1.get(1).setH1(hourOfDay);
                        timer1.get(1).setM1(minute / 10 * 10);
                        break;
                    case R.id.period_textView13:
                        //设置开启时间（时间段3）
                        if (!(Boolean.parseBoolean(period_switch3.getTag() + ""))) {
                            period_switch3.setBackgroundResource(R.drawable.kai);
                            period_switch3.setTag(true);
                        }
                        timer1.get(2).setH0(hourOfDay);
                        timer1.get(2).setM0(minute / 10 * 10);
                        break;
                    case R.id.period_textView15:
                        //设置关闭时间（时间段3）
                        if (!(Boolean.parseBoolean(period_switch3.getTag() + ""))) {
                            period_switch3.setBackgroundResource(R.drawable.kai);
                            period_switch3.setTag(true);
                        }
                        timer1.get(2).setH1(hourOfDay);
                        timer1.get(2).setM1(minute / 10 * 10);
                        break;
                }
                saveConfig();
//                yanChiFinish = false;
                switch (type) {
                    case 1:
//                        str1 = gson.toJson(timer1);
                        //灯光照明
                        if (mApp.jinLiGangdetailUI.deviceDetailModel.getEx_dev().equalsIgnoreCase("AQ500")) {
                            userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "",
                                    "", "", "", "", "", "", "", str1, str2, "", "", "", -1, -1, -1, -1);
                        } else {
                            userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "",
                                    "", "", "", "", "", "", "", str1, "", "", "", "", -1, -1, -1, -1);
                        }
                        break;
                    case 2:
//                        str2 = gson.toJson(timer1);
                        //杀菌灯
                        if (mApp.jinLiGangdetailUI.deviceDetailModel.getEx_dev().equalsIgnoreCase("AQ500")) {
                            userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", str1, str2, "", "", "", -1, -1, -1, -1);
                        } else {
                            userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", "", str2, "", "", "", -1, -1, -1, -1);
                        }
                        break;
                    case 3:
//                        str3 = gson.toJson(timer1);
                        //冲浪泵
                        userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", "", "", str3, "", "", -1, -1, -1, -1);
                        break;
                }
            }
        }, Integer.parseInt(times[0]), Integer.parseInt(times[1]), true, "");
        mPicker.setTitle(null);
        mPicker.setTimerOtherTwo(timer1, position, isKaiQi);
        mPicker.setCanceledOnTouchOutside(false);
        mPicker.show();
    }

    int type;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_zhaoming:
                type = 1;
                setCurrentItem(type);
                break;
            case R.id.txt_shajundeng:
                type = 2;
                setCurrentItem(type);
                break;
            case R.id.txt_chonglangbeng:
                type = 3;
                setCurrentItem(type);
                break;
            case R.id.period_switch1:
                if (!mApp.jinLiGangdetailUI.isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                status2 = (Boolean) period_switch2.getTag();
                status3 = (Boolean) period_switch3.getTag();
                if (status2 == false && status3 == false) {
                    MAlert.alert(getString(R.string.baoliu));
                    return;
                }
                if ((Boolean) period_switch1.getTag()) {
                    per1Enable = false;
                    period_switch1.setTag(per2Enable);
                    period_switch1.setBackgroundResource(R.drawable.guan);
                    if (status2 && status3) {
                        setCancelTime(0, 1);
                    } else if (status3) {
                        setCancelTime(1, 2);
                        setCancelTime(0, 2);
                    } else if (status2) {
                        setCancelTime(2, 1);
                        setCancelTime(0, 1);
                    }
                } else {
                    if (judgeCompare(0, 1) == false || judgeCompare(0, 2) == false) {
                        if (status2) {
                            MAlert.alert("请设置与第二时间段不同的时间段");
                            return;
                        } else {

                        }
                        if (status3) {
                            MAlert.alert("请设置与第三时间段不同的时间段");
                            return;
                        } else {

                        }
                    }
                    per1Enable = true;
                    period_switch1.setTag(per1Enable);
                    period_switch1.setBackgroundResource(R.drawable.kai);
                    if (!status2) {
                        setCancelTime(0, 1);
                    } else if (!status3) {
                        setCancelTime(0, 2);
                    }
                }
//                yanChiFinish = false;
                userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", str1, str2, str3, "", "", -1, -1, -1, -1);
                break;
            case R.id.period_switch2:
                if (!mApp.jinLiGangdetailUI.isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                status1 = (Boolean) period_switch1.getTag();
                status3 = (Boolean) period_switch3.getTag();
                if (status1 == false && status3 == false) {
                    MAlert.alert(getString(R.string.baoliu));
                    return;
                }
                if ((Boolean) period_switch2.getTag()) {
                    per2Enable = false;
                    period_switch2.setTag(per2Enable);
                    period_switch2.setBackgroundResource(R.drawable.guan);
                    //关闭则设置当前时间状态为上一级
                    if (status1 && status3) {
                        setCancelTime(1, 0);
                    } else if (status1) {
                        setCancelTime(1, 0);
                        setCancelTime(2, 0);
                    } else if (status3) {
                        setCancelTime(1, 2);
                        setCancelTime(0, 2);
                    }
                } else {
                    if (judgeCompare(1, 0) == false || judgeCompare(1, 2) == false) {
                        if (status1) {
                            MAlert.alert("请设置与第一时间段两个不同的时间段");
                            return;
                        } else {

                        }
                        if (status3) {
                            MAlert.alert("请设置与第三时间段不同的时间段");
                            return;
                        } else {

                        }
                    }
                    per2Enable = true;
                    period_switch2.setTag(per2Enable);
                    period_switch2.setBackgroundResource(R.drawable.kai);
                    if (!status1) {
                        setCancelTime(1, 0);
                    } else if (!status3) {
                        setCancelTime(1, 2);
                    }
                }
//                yanChiFinish = false;
                userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", str1, str2, str3, "", "", -1, -1, -1, -1);
                break;
            case R.id.period_switch3:
                if (!mApp.jinLiGangdetailUI.isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                status1 = (Boolean) period_switch1.getTag();
                status2 = (Boolean) period_switch2.getTag();
                if (status1 == false && status2 == false) {
                    MAlert.alert(getString(R.string.baoliu));
                    return;
                }
                if ((Boolean) period_switch3.getTag()) {
                    per3Enable = false;
                    period_switch3.setTag(per3Enable);
                    period_switch3.setBackgroundResource(R.drawable.guan);
                    if (status1 && status2) {
                        setCancelTime(2, 0);
                    } else if (status1) {
                        setCancelTime(1, 0);
                        setCancelTime(2, 0);
                    } else if (status2) {
                        setCancelTime(1, 1);
                        setCancelTime(2, 1);
                    }
                } else {
                    if (judgeCompare(2, 0) == false || judgeCompare(2, 1) == false) {
                        if (status1) {
                            MAlert.alert("请设置与第一时间段不同的时间段");
                            return;
                        } else {

                        }
                        if (status2) {
                            MAlert.alert("请设置与第二时间段不同的时间段");
                            return;
                        } else {

                        }
                    }
                    per3Enable = true;
                    period_switch3.setTag(per3Enable);
                    period_switch3.setBackgroundResource(R.drawable.kai);
                    if (!status1) {
                        setCancelTime(2, 0);
                    } else if (!status2) {
                        setCancelTime(2, 1);
                    }
                }
//                yanChiFinish = false;
                userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", str1, str2, str3, "", "", -1, -1, -1, -1);
                break;
            case R.id.btn_ok:
                if (!mApp.jinLiGangdetailUI.isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                saveConfig();

                showProgressDialog(getString(R.string.requesting), true);
                userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", str1, str2, str3, cfg + "", "", total2, total3, total1, -1);
                break;
            case R.id.btn_cancel:
                showSaveDialog(2);
                break;
            case R.id.btn_reset:
                if (!mApp.jinLiGangdetailUI.isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                showSaveDialog(3);
                break;
            case R.id.switch_yichangbaojing:
                if (!mApp.jinLiGangdetailUI.isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                cfg = 0;
                //异常报警
                switch (type) {
                    case 1://灯光照明异常报警
                        cfg = mApp.jinLiGangdetailUI.detailModelTcp.getPush_cfg() ^ (int) Math.pow(2, 2);
                        break;
                    case 2://杀菌灯照明异常报警
                        cfg = mApp.jinLiGangdetailUI.detailModelTcp.getPush_cfg() ^ (int) Math.pow(2, 3);
                        break;
                    case 3://冲浪泵异常报警
                        cfg = mApp.jinLiGangdetailUI.detailModelTcp.getPush_cfg() ^ (int) Math.pow(2, 0);
                        break;
                }
//                if (Boolean.parseBoolean(switch_yichangbaojing.getTag() + "") == true) {
//                    switch_yichangbaojing.setBackgroundResource(R.drawable.guan);
//                } else {
//                    switch_yichangbaojing.setBackgroundResource(R.drawable.kai);
//                }
                switch_yichangbaojing.setTag(!Boolean.parseBoolean(switch_yichangbaojing.getTag() + ""));
                userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", "", "", "", cfg + "", "", -1, -1, -1, -1);
                break;
            case R.id.switch_qingling:
                if (!mApp.jinLiGangdetailUI.isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
//                yanChiFinish = false;
                qingLing();
                break;
            case R.id.switch_dongtaitishi:
                if (!mApp.jinLiGangdetailUI.isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                cfg = 0;
                switch (type) {
                    case 1://灯光照明异常报警
                        cfg = mApp.jinLiGangdetailUI.detailModelTcp.getPush_cfg() ^ (int) Math.pow(2, 6);
                        break;
                    case 2://杀菌灯照明异常报警
                        cfg = mApp.jinLiGangdetailUI.detailModelTcp.getPush_cfg() ^ (int) Math.pow(2, 7);
                        break;
                    case 3://冲浪泵异常报警
                        cfg = mApp.jinLiGangdetailUI.detailModelTcp.getPush_cfg() ^ (int) Math.pow(2, 8);
                        break;
                }
//                if (Boolean.parseBoolean(switch_dongtaitishi.getTag() + "") == true) {
//                    switch_dongtaitishi.setBackgroundResource(R.drawable.guan);
//                } else {
//                    switch_dongtaitishi.setBackgroundResource(R.drawable.kai);
//                }
                switch_dongtaitishi.setTag(!Boolean.parseBoolean(switch_dongtaitishi.getTag() + ""));
//                yanChiFinish = false;
                userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", "", "", "", cfg + "", "", -1, -1, -1, -1);
                break;
        }
    }

    private void qingLing() {
        showSaveDialog(4);
    }

    private void saveConfig() {
        status1 = (Boolean) period_switch1.getTag();
        status2 = (Boolean) period_switch2.getTag();
        status3 = (Boolean) period_switch3.getTag();
        if (!status1) {
            if (status2) {
                setCancelTime(0, 1);
            } else if (status3) {
                setCancelTime(0, 2);
            }
        }
        if (!status2) {
            if (status1) {
                setCancelTime(1, 0);
            } else if (status3) {
                setCancelTime(1, 2);
            }
        }
        if (!status3) {
            if (status1) {
                setCancelTime(2, 0);
            } else if (status2) {
                setCancelTime(2, 1);
            }
        }
        if (status1 && status2 && status3) {
            parseJson();
        }
    }

    private void restoreFactorySettings() {
        switch (type) {
            case 1:
                timer1.get(0).setH0(7);
                timer1.get(0).setH1(11);
                timer1.get(0).setM0(0);
                timer1.get(0).setM1(0);
                timer1.get(1).setH0(12);
                timer1.get(1).setH1(17);
                timer1.get(1).setM0(0);
                timer1.get(1).setM1(0);
                timer1.get(2).setH0(18);
                timer1.get(2).setH1(21);
                timer1.get(2).setM0(0);
                timer1.get(2).setM1(0);
                str1 = gson.toJson(timer1);
                break;
            case 2:
            case 3:
                timer1.get(0).setH0(6);
                timer1.get(0).setH1(6);
                timer1.get(0).setM0(0);
                timer1.get(0).setM1(30);
                timer1.get(1).setH0(11);
                timer1.get(1).setH1(11);
                timer1.get(1).setM0(0);
                timer1.get(1).setM1(30);
                timer1.get(2).setH0(17);
                timer1.get(2).setH1(17);
                timer1.get(2).setM0(0);
                timer1.get(2).setM1(30);
                str2 = gson.toJson(timer1);
                str3 = gson.toJson(timer1);
                break;
        }
        str1 = str1.replaceAll(" ", "");
        str2 = str2.replaceAll(" ", "");
        str3 = str3.replaceAll(" ", "");
        switch (type) {
            case 1:
                userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", str1, "", "", "", "", 0, 0, 0, -1);
                break;
            case 2:
                userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", "", str2, "", "", "", 0, 0, 0, -1);
                break;
            case 3:
                userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", "", "", str3, "", "", 0, 0, 0, -1);
                break;
        }
    }

    private void setCancelTime(int i, int i1) {
        timer1.get(i).setH0((timer1.get(i1).getH0()));
        timer1.get(i).setM0((timer1.get(i1).getM0()));
        timer1.get(i).setM1((timer1.get(i1).getM1()));
        timer1.get(i).setH1((timer1.get(i1).getH1()));
        parseJson();
    }

    private void parseJson() {
        if (type == 1) {
            str1 = gson.toJson(timer1);
        } else if (type == 2) {
            str2 = gson.toJson(timer1);
        } else {
            str3 = gson.toJson(timer1);
        }
        str1 = str1.replaceAll(" ", "");
        str2 = str2.replaceAll(" ", "");
        str3 = str3.replaceAll(" ", "");
    }

    public void setLabelText(int type) {
        period_textView2.setText(titlesBegin[type - 1]);
        period_textView4.setText(titlesEnd[type - 1]);
        period_textView7.setText(titlesBegin[type - 1]);
        period_textView9.setText(titlesEnd[type - 1]);
        period_textView12.setText(titlesBegin[type - 1]);
        period_textView14.setText(titlesEnd[type - 1]);

    }


    private void showSaveDialog(final int i) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.tips));
//        if (i == 2) {
//            alert.setMessage(getString(R.string.cancel_set));
//        } else if (i == 0 || i == 1) {
//            alert.setMessage(getString(R.string.no_save_exist));
//        }
        if (i == 3) {
            alert.setMessage(getString(R.string.chuchangshezhi));
        } else if (i == 4) {
            alert.setMessage(getString(R.string.clear_wh_time));
        }
        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                if (i == 1) {
//                    str1 = str1.replaceAll(" ", "");
//                    str2 = str2.replaceAll(" ", "");
//                    str3 = str3.replaceAll(" ", "");
//                    yanChiFinish=false;
//                    userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", str1, str2, str3, "", "", -1, -1, -1);
//                } else if (i == 0) {
//                    finish();
//                } else if (i == 2) {
//                    setCurrentItem(type);
//                }
                if (i == 3) {
//                    yanChiFinish = true;
                    restoreFactorySettings();
                } else if (i == 4) {
//                    yanChiFinish = true;
                    setWhTime(0);
                    switch (type) {
                        case 1://灯光照明累计时间清零
                            total1 = 0;
                            setWhTime(0);
                            userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", "", "", "", "", "", -1, -1, total1, -1);
                            break;
                        case 2://杀菌灯累计时间清零
                            total2 = 0;
                            userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", "", "", "", "", "", total2, -1, -1, -1);
                            break;
                        case 3://冲浪泵累计时间清零
                            total3 = 0;
                            userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", "", "", "", "", "", -1, total3, -1, -1);
                            break;
                    }
                    switch_qingling.setBackgroundResource(R.drawable.kai);
                }
            }
        });
        alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                if (i == 1 || i == 0) {
////                    finish();
//                }
////                else if(i==0){
////                    finish();
////                }
////                else if(i==2){
////                    setCurrentItem(1);
////                }
            }
        });
//        if (i == 0 || i == 1) {
//            alert.setNeutralButton(getString(R.string.alreay_submit), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                }
//            });
//        }
        alert.create();
        alert.show();
    }

    public void setData() {
        setCurrentItem(type);
    }

    public void setCurrentItem(int type) {
        try {
            if (mApp.jinLiGangdetailUI.detailModelTcp != null) {
                str1 = mApp.jinLiGangdetailUI.detailModelTcp.getL_per();
                str2 = mApp.jinLiGangdetailUI.detailModelTcp.getUvc_per();
                str3 = mApp.jinLiGangdetailUI.detailModelTcp.getSp_per();
                txt_zhaoming.setBackgroundResource(R.drawable.border_gray);
                txt_shajundeng.setBackgroundResource(R.drawable.border_gray);
                txt_chonglangbeng.setBackgroundResource(R.drawable.border_gray);
                txt_zhaoming.setTextColor(getResources().getColor(R.color.black));
                txt_shajundeng.setTextColor(getResources().getColor(R.color.black));
                txt_chonglangbeng.setTextColor(getResources().getColor(R.color.black));
                switch_qingling.setBackgroundResource(R.drawable.guan);
                if (mApp.jinLiGangdetailUI.detailModelTcp.getEx_dev().equalsIgnoreCase("AQ500")) {
                    txt_chonglangbeng.setVisibility(View.GONE);
                    txt_shajundeng.setText(getString(R.string.shaju_chonglang));
                } else {
                    txt_chonglangbeng.setVisibility(View.VISIBLE);
                }
                switch (type) {
                    case 1:
                        // 照明灯
                        type1 = new TypeToken<ArrayList<DeviceDetailModel.TimePeriod>>() {
                        }.getType();
                        timer1 = gson.fromJson(str1, type1);
                        txt_zhaoming.setBackgroundResource(R.drawable.bg_change_green);
                        txt_zhaoming.setTextColor(getResources().getColor(R.color.white));
                        setWhTime(mApp.jinLiGangdetailUI.detailModelTcp.getL_wh());
                        if ((mApp.jinLiGangdetailUI.detailModelTcp.getPush_cfg() & (int) Math.pow(2, 2)) == Math.pow(2, 2)) {
                            zhaomingdengStatus = true;
                            switch_yichangbaojing.setBackgroundResource(R.drawable.kai);
                        } else {
                            zhaomingdengStatus = false;
                            switch_yichangbaojing.setBackgroundResource(R.drawable.guan);
                        }
                        if ((mApp.jinLiGangdetailUI.detailModelTcp.getPush_cfg() & (int) Math.pow(2, 6)) == Math.pow(2, 6)) {
                            zhaomingdengDongtai = true;
                            switch_dongtaitishi.setBackgroundResource(R.drawable.kai);
                        } else {
                            zhaomingdengDongtai = false;
                            switch_dongtaitishi.setBackgroundResource(R.drawable.guan);
                        }
                        switch_yichangbaojing.setTag(zhaomingdengStatus);
                        switch_dongtaitishi.setTag(zhaomingdengDongtai);
                        period_imageView1.setBackgroundResource(R.drawable.light_select);
                        period_imageView2.setBackgroundResource(R.drawable.light_unselect2);
                        period_imageView3.setBackgroundResource(R.drawable.light_select);
                        period_imageView4.setBackgroundResource(R.drawable.light_unselect2);
                        period_imageView5.setBackgroundResource(R.drawable.light_select);
                        period_imageView6.setBackgroundResource(R.drawable.light_unselect2);

                        break;
                    case 2:
                        // 杀菌灯
                        type1 = new TypeToken<ArrayList<DeviceDetailModel.TimePeriod>>() {
                        }.getType();
                        timer1 = gson.fromJson(str2, type1);
                        txt_shajundeng.setBackgroundResource(R.drawable.bg_change_green);
                        txt_shajundeng.setTextColor(getResources().getColor(R.color.white));
                        if ((mApp.jinLiGangdetailUI.detailModelTcp.getPush_cfg() & (int) Math.pow(2, 3)) == Math.pow(2, 3)) {
                            shajundengStatus = true;
                            switch_yichangbaojing.setBackgroundResource(R.drawable.kai);
                        } else {
                            shajundengStatus = false;
                            switch_yichangbaojing.setBackgroundResource(R.drawable.guan);
                        }
                        if ((mApp.jinLiGangdetailUI.detailModelTcp.getPush_cfg() & (int) Math.pow(2, 7)) == Math.pow(2, 7)) {
                            shajundengDongtai = true;
                            switch_dongtaitishi.setBackgroundResource(R.drawable.kai);
                        } else {
                            shajundengDongtai = false;
                            switch_dongtaitishi.setBackgroundResource(R.drawable.guan);
                        }
                        switch_yichangbaojing.setTag(shajundengStatus);
                        switch_dongtaitishi.setTag(shajundengDongtai);
                        setWhTime(mApp.jinLiGangdetailUI.detailModelTcp.getUv_wh());
                        if (mApp.jinLiGangdetailUI.detailModelTcp.getEx_dev().equalsIgnoreCase("AQ500")) {
                            period_imageView1.setBackgroundResource(R.drawable.aq_500_select);
                            period_imageView2.setBackgroundResource(R.drawable.aq_500_unselect);
                            period_imageView3.setBackgroundResource(R.drawable.aq_500_select);
                            period_imageView4.setBackgroundResource(R.drawable.aq_500_unselect);
                            period_imageView5.setBackgroundResource(R.drawable.aq_500_select);
                            period_imageView6.setBackgroundResource(R.drawable.aq_500_unselect);
                        } else {
                            period_imageView1.setBackgroundResource(R.drawable.uv_select);
                            period_imageView2.setBackgroundResource(R.drawable.uv_unselect2);
                            period_imageView3.setBackgroundResource(R.drawable.uv_select);
                            period_imageView4.setBackgroundResource(R.drawable.uv_unselect2);
                            period_imageView5.setBackgroundResource(R.drawable.uv_select);
                            period_imageView6.setBackgroundResource(R.drawable.uv_unselect2);
                        }
                        break;
                    case 3:
                        // 冲浪水泵
                        type1 = new TypeToken<ArrayList<DeviceDetailModel.TimePeriod>>() {
                        }.getType();
                        timer1 = gson.fromJson(str3, type1);
                        txt_chonglangbeng.setBackgroundResource(R.drawable.bg_change_green);
                        txt_chonglangbeng.setTextColor(getResources().getColor(R.color.white));
                        if ((mApp.jinLiGangdetailUI.detailModelTcp.getPush_cfg() & (int) Math.pow(2, 0)) == Math.pow(2, 0)) {
                            chonglangbengStatus = true;
                            switch_yichangbaojing.setBackgroundResource(R.drawable.kai);
                        } else {
                            chonglangbengStatus = false;
                            switch_yichangbaojing.setBackgroundResource(R.drawable.guan);
                        }
                        if ((mApp.jinLiGangdetailUI.detailModelTcp.getPush_cfg() & (int) Math.pow(2, 8)) == Math.pow(2, 8)) {
                            chonglangbengDongtai = true;
                            switch_dongtaitishi.setBackgroundResource(R.drawable.kai);
                        } else {
                            chonglangbengDongtai = false;
                            switch_dongtaitishi.setBackgroundResource(R.drawable.guan);
                        }
                        switch_yichangbaojing.setTag(chonglangbengStatus);
                        switch_dongtaitishi.setTag(chonglangbengDongtai);
                        setWhTime(mApp.jinLiGangdetailUI.detailModelTcp.getP_wh());

                        period_imageView1.setBackgroundResource(R.drawable.chonglangbeng_select);
                        period_imageView2.setBackgroundResource(R.drawable.chonglangbeng_unselect2);
                        period_imageView3.setBackgroundResource(R.drawable.chonglangbeng_select);
                        period_imageView4.setBackgroundResource(R.drawable.chonglangbeng_unselect2);
                        period_imageView5.setBackgroundResource(R.drawable.chonglangbeng_select);
                        period_imageView6.setBackgroundResource(R.drawable.chonglangbeng_unselect2);
                        break;
                }
                String per1_open = (timer1.get(0).getH0() < 10 ? "0" + timer1.get(0).getH0() : timer1.get(0).getH0()) + ":" + (timer1.get(0).getM0() < 10 ? "0" + timer1.get(0).getM0() : timer1.get(0).getM0());
                String per1_close = (timer1.get(0).getH1() < 10 ? "0" + timer1.get(0).getH1() : timer1.get(0).getH1()) + ":" + (timer1.get(0).getM1() < 10 ? "0" + timer1.get(0).getM1() : timer1.get(0).getM1());

                String per2_open = (timer1.get(1).getH0() < 10 ? "0" + timer1.get(1).getH0() : timer1.get(1).getH0()) + ":" + (timer1.get(1).getM0() < 10 ? "0" + timer1.get(1).getM0() : timer1.get(1).getM0());
                String per2_close = (timer1.get(1).getH1() < 10 ? "0" + timer1.get(1).getH1() : timer1.get(1).getH1()) + ":" + (timer1.get(1).getM1() < 10 ? "0" + timer1.get(1).getM1() : timer1.get(1).getM1());

                String per3_open = (timer1.get(2).getH0() < 10 ? "0" + timer1.get(2).getH0() : timer1.get(2).getH0()) + ":" + (timer1.get(2).getM0() < 10 ? "0" + timer1.get(2).getM0() : timer1.get(2).getM0());
                String per3_close = (timer1.get(2).getH1() < 10 ? "0" + timer1.get(2).getH1() : timer1.get(2).getH1()) + ":" + (timer1.get(2).getM1() < 10 ? "0" + timer1.get(2).getM1() : timer1.get(2).getM1());
                //1、三个时间段都不同，均开启
                //2、任意两个时间段不同，开启不同的两个时间段，剩余时间段关闭
                setSwitchEnable(1);//时间段1必须可用
                setSwitchEnable(2);//时间段2不同于时间段1可用
                setSwitchEnable(3);//时间段三不同于时间段1和时间段2可用
                if ((per1_open.equals(per2_open) && per1_close.equals(per2_close)) && (per1_open.equals(per3_open) && per1_close.equals(per3_close)) && (per2_open.equals(per3_open) && per2_close.equals(per3_close))) {
                    period_switch1.setBackgroundResource(R.drawable.guan);
                    period_switch2.setBackgroundResource(R.drawable.kai);
                    period_switch3.setBackgroundResource(R.drawable.guan);
                }
                //3、三个时间段相同，开启一个时间段
                if ((per1_open.equals(per2_open) && per1_close.equals(per2_close)) && (per1_open.equals(per3_open) && per1_close.equals(per3_close)) && (per2_open.equals(per3_open) && per2_close.equals(per3_close))) {
                    period_switch1.setBackgroundResource(R.drawable.kai);
                    period_switch2.setBackgroundResource(R.drawable.guan);
                    period_switch3.setBackgroundResource(R.drawable.guan);
                }
                period_textView3.setText(per1_open);//时间段一开启时间
                period_textView5.setText(per1_close);//时间段一关闭时间
                period_textView8.setText(per2_open);//时间段二开启时间
                period_textView10.setText(per2_close);//时间段二关闭时间
                period_textView13.setText(per3_open);//时间段三开启时间
                period_textView15.setText(per3_close);//时间段三关闭时间

                setLabelText(type);
            }
        } catch (Exception e) {

        }
    }

    private void setWhTime(int l_wh) {
        switch (type) {
            case 1:
                textView2.setText(getString(R.string.dengguangzhaomingleiji) + l_wh + getString(R.string.hour));
                break;
            case 2:
                textView2.setText(getString(R.string.shajundeng) + l_wh + getString(R.string.hour));
                break;
            case 3:
                textView2.setText(getString(R.string.chonglangbengleiji) + l_wh + getString(R.string.hour));
                break;
        }
    }

    private void setSwitchEnable(int i) {
        switch (i) {
            case 1:
                per1Enable = true;
                period_switch1.setBackgroundResource(R.drawable.kai);
                period_switch1.setTag(per1Enable);
                break;
            case 2:
                per2Enable = judgeCompare(1, 0);
                if (per2Enable) {
                    period_switch2.setBackgroundResource(R.drawable.kai);
                } else {
                    period_switch2.setBackgroundResource(R.drawable.guan);
                }
                period_switch2.setTag(per2Enable);
                break;
            case 3:
                boolean per3and2Enable = judgeCompare(2, 1);
                boolean per3and1Enable = judgeCompare(2, 0);

                if (per3and2Enable == false || per3and1Enable == false) {
                    per3Enable = false;
                    period_switch3.setBackgroundResource(R.drawable.guan);
                } else {
                    per3Enable = true;
                    period_switch3.setBackgroundResource(R.drawable.kai);
                }
                period_switch3.setTag(per3Enable);
                break;
        }
    }

    private boolean judgeCompare(int position1, int position2) {
        if (timer1.get(position1).getH0() == (timer1.get(position2).getH0())) {
            if (timer1.get(position1).getM0() == (timer1.get(position2).getM0())) {
                if (timer1.get(position1).getH1() == (timer1.get(position2).getH1())) {
                    if (timer1.get(position1).getM1() == (timer1.get(position2).getM1())) {
                        return false;
                    }
                }
            }
        }
        return true;
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
                return;
            }
            if (entity.getEventType() == UserPresenter.deviceSet_806success) {
                MAlert.alert(entity.getData());
                total1 = -1;
                total2 = -1;
                total3 = -1;
                switch_qingling.setBackgroundResource(R.drawable.guan);
            }
            if (entity.getEventType() == UserPresenter.deviceSet_806fail) {
                MAlert.alert(entity.getData());
                switch_qingling.setBackgroundResource(R.drawable.guan);
            }
        }
    }

    public void setNewTimer() {
        str1 = mApp.jinLiGangdetailUI.deviceDetailModel.getL_per();
        str2 = mApp.jinLiGangdetailUI.deviceDetailModel.getUvc_per();
        str3 = mApp.jinLiGangdetailUI.deviceDetailModel.getSp_per();
    }
}
