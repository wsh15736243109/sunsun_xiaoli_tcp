package sunsun.xiaoli.jiarebang.device.pondteam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.R;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.custom.CustomCircleProgress;
import com.itboye.pondteam.custom.wheelview.view.WheelPicker;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.app.App;

import static com.itboye.pondteam.utils.ColoTextUtil.setColorfulValue;


/**
 * Created by Administrator on 2017/3/7.
 */
public class ActivityShouDong extends BaseActivity implements Observer {
    ImageView img_back;
    CustomCircleProgress circleProgress;
    String title;
    TextView txt_title;
    //    DeviceDetailModel model;
    TextView status;
    TextView once_again;
    TextView txt_was_time;
    RelativeLayout re_single_time_setting;
    UserPresenter userPresenter;
    App myApp;
    private int totalCount;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoudong);
        myApp = (App) getApplication();
        myApp.shoudongUI = this;
        circleProgress.setStatus(CustomCircleProgress.Status.INIT);//初始化状态
        title = getIntent().getStringExtra("title");
//        model = (DeviceDetailModel) getIntent().getSerializableExtra("model");
        userPresenter = new UserPresenter(this);
        setShouDongData();
        txt_title.setText(title);
    }

    //        int pro;
    public void setShouDongData() {
//        System.out.println("进度执行了？"+pro);
        once_again.setVisibility(View.GONE);
        String wash_status = "";

        String tempStatus = myApp.pondDeviceDetailUI.deviceDetailModel.getCl_state();
//        String tempStatus = "1";
        if (tempStatus.equals("0")) {
            wash_status = getString(R.string.current_status) + getString(R.string.readytoclean);
            circleProgress.setStatus(CustomCircleProgress.Status.INIT);
        } else if (tempStatus.equals("1")) {
            wash_status = getString(R.string.current_status) + getString(R.string.washing);
            circleProgress.setStatus(CustomCircleProgress.Status.Starting);
        } else if (tempStatus.equals("2")) {
            wash_status = getString(R.string.current_status) + getString(R.string.pause_washing);
            circleProgress.setStatus(CustomCircleProgress.Status.Pause);
        } else if (tempStatus.equals("3")) {
            wash_status = getString(R.string.current_status) + getString(R.string.problem);
            circleProgress.setStatus(CustomCircleProgress.Status.End);
            once_again.setVisibility(View.VISIBLE);
        }

//        else if (tempStatus.equals("4")) {
//            wash_status = getString(R.string.current_status) + getString(R.string.prepare_washing);
//            circleProgress.setStatus(CustomCircleProgress.Status.P);
//        }
        setItsColor(wash_status);
        mNewTempValue = myApp.pondDeviceDetailUI.deviceDetailModel.getCl_dur();
        txt_was_time.setText(mNewTempValue + getString(R.string.seconds));
        txt_was_time.setTag(mNewTempValue);
        totalCount = myApp.pondDeviceDetailUI.deviceDetailModel.getCl_dur();
        circleProgress.setMax(totalCount);
        circleProgress.setProgress(Integer.parseInt(myApp.pondDeviceDetailUI.deviceDetailModel.getCl_sche()));
//        pro+=5;
//        circleProgress.setProgress(pro);
    }

    private void setItsColor(String wash_status) {
        int startPo = getString(R.string.current_status).length();
        int stopPo = wash_status.length();
        setColorfulValue(startPo, stopPo, R.color.main_green, wash_status, status);
    }

    int mNewTempValue = 0;

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.img_back) {
            finish();

        } else if (i == R.id.circleProgress) {
            if (myApp.pondDeviceDetailUI.isConnect == false) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            String wash_status = "";
            if (circleProgress.getStatus() == CustomCircleProgress.Status.Starting) {//如果是开始状态
                wash_status = getString(R.string.current_status) + getString(R.string.pause_washing);
                //点击则变成关闭暂停状态
                userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(),null,null, "", -1, "", "", "2", "", "", "", "", "","", -1, -1, "", "", "", "", -1, -1);
//                circleProgress.setStatus(CustomCircleProgress.Status.Pause);
                //注意，当我们暂停时，同时还要移除消息，不然的话进度不会被停止
//                    handler.removeMessages(PROGRESS_CIRCLE_STARTING);
            }
//                else if (circleProgress.getStatus() == CustomCircleProgress.Status.End) {//如果是开始状态
//                    //点击则变成关闭暂停状态
//                    circleProgress.setStatus(CustomCircleProgress.Status.Starting);
//                    //注意，当我们暂停时，同时还要移除消息，不然的话进度不会被停止
//                    handler.removeMessages(PROGRESS_CIRCLE_STARTING);
//                }
            else {
                //将清洗时间换成总的秒数，便于线程
                userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(),null,null, "", -1, "", "", "1", "", "", "", "", "","", -1, -1, "", "", "", "", -1, -1);
                wash_status = getString(R.string.current_status) + getString(R.string.washing);
                //点击则变成开启状态
//                circleProgress.setStatus(CustomCircleProgress.Status.Starting);
//                    handler.sendMessage(message);
            }
            setItsColor(wash_status);
        } else if (i == R.id.re_single_time_setting || i == R.id.txt_was_time) {
            if (myApp.pondDeviceDetailUI.isConnect == false) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            if (circleProgress.getStatus() == CustomCircleProgress.Status.Starting || circleProgress.getStatus() == CustomCircleProgress.Status.Pause) {
                //正在清洗中
                MAlert.alert(getString(R.string.device) + getString(R.string.washing));
                return;
            }
            showTimePeroid(txt_was_time.getTag() + "");
//            mNewTempValue = 20;
//            NumberPicker mPicker = new NumberPicker(
//                    this);
//            mPicker.setMinValue(0);
//            mPicker.setMaxValue(60);
//            mPicker.setValue(mNewTempValue);
//            mPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//                @Override
//                public void onValueChange(NumberPicker picker, int oldVal,
//                                          int newVal) {
//                    mNewTempValue = newVal;
//                }
//            });
//            View view = LayoutInflater.from(this).inflate(R.layout.ui_alert_view, null);
//            TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
//            tvTitle.setText("60");
//            tvTitle.setOnClickListener(this);
//
//            TextView tvgengxingujian = (TextView) view.findViewById(R.id.tvgengxingujian);
//            tvgengxingujian.setText("90");
//            tvgengxingujian.setOnClickListener(this);
//
//            TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
//            tvMessage.setText("120");
//            tvMessage.setOnClickListener(this);
//
//            TextView tvFeedback = (TextView) view.findViewById(R.id.tvFeedback);
//            tvFeedback.setText("160");
//            tvFeedback.setOnClickListener(this);
//
//            TextView tvBtnLeft = (TextView) view.findViewById(R.id.tvBtnLeft);
//            tvBtnLeft.setText("180");
//            tvBtnLeft.setOnClickListener(this);
//
//            TextView tv1 = (TextView) view.findViewById(R.id.tv1);
//            tv1.setVisibility(View.VISIBLE);
//            tv1.setText("30");
//            tv1.setOnClickListener(this);
//            mAlertDialog = new AlertDialog.Builder(
//                    this)
//                    .setTitle(getString(R.string.choose_single_washtime))
//                    .setView(view).create();
//            mAlertDialog.show();
////                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                                txt_was_time.setText(mNewTempValue + getString(R.string.minute));
////                                userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), "", -1, "", mNewTempValue + "", "", "", "", "", "", "", -1, -1);
////                            }
////                        })
////                        .setNegativeButton(getString(R.string.cancel),
////                                new DialogInterface.OnClickListener() {
////                                    @Override
////                                    public void onClick(DialogInterface dialog,
////                                                        int which) {
////                                    }
////                                }).create();
//            mAlertDialog.show();

        }

//        else if (i == R.id.tvgengxingujian) {
//            mAlertDialog.dismiss();
//            if (circleProgress.getStatus() == CustomCircleProgress.Status.Starting || circleProgress.getStatus() == CustomCircleProgress.Status.Pause) {
//                //正在清洗中
//                MAlert.alert(getString(R.string.device) + getString(R.string.washing));
//                return;
//            }
//            //90秒
//            mNewTempValue = 90;
//            txt_was_time.setText(mNewTempValue + getString(R.string.seconds));
//            userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), "", -1, "", mNewTempValue + "", "", "", "", "", "", "", -1, -1);
//
//        } else if (i == R.id.tvMessage) {
//            mAlertDialog.dismiss();
//            if (circleProgress.getStatus() == CustomCircleProgress.Status.Starting || circleProgress.getStatus() == CustomCircleProgress.Status.Pause) {
//                //正在清洗中
//                MAlert.alert(getString(R.string.device) + getString(R.string.washing));
//                return;
//            }
//            //120秒
//            mNewTempValue = 120;
//            txt_was_time.setText(mNewTempValue + getString(R.string.seconds));
//            userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), "", -1, "", mNewTempValue + "", "", "", "", "", "", "", -1, -1);
//
//        } else if (i == R.id.tvFeedback) {
//            mAlertDialog.dismiss();
//            if (circleProgress.getStatus() == CustomCircleProgress.Status.Starting || circleProgress.getStatus() == CustomCircleProgress.Status.Pause) {
//                //正在清洗中
//                MAlert.alert(getString(R.string.device) + getString(R.string.washing));
//                return;
//            }
//            mNewTempValue = 90;
//            //160秒
//            mNewTempValue = 160;
//            txt_was_time.setText(mNewTempValue + getString(R.string.seconds));
//            userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), "", -1, "", mNewTempValue + "", "", "", "", "", "", "", -1, -1);
//
//        } else if (i == R.id.tvBtnLeft) {
//            mAlertDialog.dismiss();
//            if (circleProgress.getStatus() == CustomCircleProgress.Status.Starting || circleProgress.getStatus() == CustomCircleProgress.Status.Pause) {
//                //正在清洗中
//                MAlert.alert(getString(R.string.device) + getString(R.string.washing));
//                return;
//            }
//            //180秒
//            mNewTempValue = 180;
//            txt_was_time.setText(mNewTempValue + getString(R.string.seconds));
//            userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), "", -1, "", mNewTempValue + "", "", "", "", "", "", "", -1, -1);
//
//        } else if (i == R.id.tvTitle) {
//            mAlertDialog.dismiss();
//            if (circleProgress.getStatus() == CustomCircleProgress.Status.Starting || circleProgress.getStatus() == CustomCircleProgress.Status.Pause) {
//                //正在清洗中
//                MAlert.alert(getString(R.string.device) + getString(R.string.washing));
//                return;
//            }
//            //60秒
//            mNewTempValue = 60;
//            txt_was_time.setText(mNewTempValue + getString(R.string.seconds));
//            userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), "", -1, "", mNewTempValue + "", "", "", "", "", "", "", -1, -1);
//
//        } else if (i == R.id.tv1) {
//            mAlertDialog.dismiss();
//            if (circleProgress.getStatus() == CustomCircleProgress.Status.Starting || circleProgress.getStatus() == CustomCircleProgress.Status.Pause) {
//                //正在清洗中
//                MAlert.alert(getString(R.string.device) + getString(R.string.washing));
//                return;
//            }
//            //30秒
//            mNewTempValue = 30;
//            txt_was_time.setText(mNewTempValue + getString(R.string.seconds));
//            userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), "", -1, "", mNewTempValue + "", "", "", "", "", "", "", -1, -1);
//
//        }
    }

    private void showTimePeroid(String currentValue) {
        View view = LayoutInflater.from(this).inflate(R.layout.alertdialog_number, null);
        WheelPicker myNumberPickerView = (WheelPicker) view.findViewById(R.id.number_picker1);
        List<String> list = new ArrayList<>();
        list = Arrays.asList(getResources().getStringArray(R.array.timeArray));
        int position = list.indexOf(Integer.parseInt(currentValue));
        myNumberPickerView.setData(list);
        myNumberPickerView.setSelectedItemPosition(position == -1 ? 0 : position);
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(this);
        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                txt_was_time.setText(mNewTempValue + getString(R.string.seconds));
                txt_was_time.setTag(mNewTempValue);
                userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(),null,null, "", -1, "", mNewTempValue + "", "", "", "", "", "", "","", -1, -1, "", "", "", "", -1, -1);
            }
        });
        alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        myNumberPickerView.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                mNewTempValue = Integer.valueOf(data + "");
            }
        });
        alert.setView(view);
        alert.create();
        alert.show();
    }

    private int progress;
    //    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            String wash_status = "";
//            switch (msg.what) {
//                case PROGRESS_CIRCLE_STARTING:
//                    progress = Integer.parseInt(myApp.pondDeviceDetailUI.deviceDetailModel.getCl_sche());
////                    progress+=1;
//                    circleProgress.setProgress(progress);
//                    if (progress >= totalCount) {
//                        wash_status = getString(R.string.current_status) + getString(R.string.finish_washing);
//                        handler.removeMessages(PROGRESS_CIRCLE_STARTING);
//                        progress = 0;
//                        circleProgress.setProgress(0);
//                        circleProgress.setStatus(CustomCircleProgress.Status.End);//修改显示状态为完成
//                    } else {
//                        wash_status = getString(R.string.current_status) + getString(R.string.washing);
//                        //延迟100ms后继续发消息，实现循环，直到progress=100
//                        handler.sendEmptyMessageDelayed(PROGRESS_CIRCLE_STARTING, 1000);
//                    }
//                    setItsColor(wash_status);
//                    break;
//            }
//        }
//    };
    public static final int PROGRESS_CIRCLE_STARTING = 0x110;

    @Override
    public void update(Observable o, Object data) {
        ResultEntity resultEntity = handlerError(data);
        if (resultEntity != null) {
            if (resultEntity.getCode() != 0) {
                MAlert.alert(resultEntity.getMsg());
                return;
            }
            if (resultEntity.getEventType() == UserPresenter.deviceSet_success) {
                MAlert.alert(resultEntity.getData());
                Const.intervalTime=500;
                myApp.pondDeviceDetailUI.threadStart();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Const.intervalTime=10000;
                    }
                },5000);
            } else if (resultEntity.getEventType() == UserPresenter.deviceSet_fail) {
                MAlert.alert(resultEntity.getData());
            } else if (resultEntity.getEventType() == UserPresenter.getdeviceinfosuccess) {
                myApp.pondDeviceDetailUI.deviceDetailModel = (DeviceDetailModel) resultEntity.getData();
                myApp.pondDeviceDetailUI.setShouDongQingXi(myApp.pondDeviceDetailUI.deviceDetailModel);
                setShouDongData();
            } else if (resultEntity.getEventType() == UserPresenter.getdeviceinfofail) {
                MAlert.alert(resultEntity.getData());
            }
        }

    }
}
