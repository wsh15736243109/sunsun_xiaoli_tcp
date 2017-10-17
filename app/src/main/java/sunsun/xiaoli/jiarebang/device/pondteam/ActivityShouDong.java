package sunsun.xiaoli.jiarebang.device.pondteam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.R;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.custom.CustomCircleProgress;
import com.itboye.pondteam.custom.wheelview.view.WheelPicker;
import com.itboye.pondteam.presenter.UserPresenter;
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
        userPresenter = new UserPresenter(this);
        setShouDongData();
        txt_title.setText(title);
    }

    //        int pro;
    public void setShouDongData() {
        once_again.setVisibility(View.GONE);
        String wash_status = "";
        if (myApp.pondDeviceDetailUI.detailModelTcp!=null) {
            if (myApp.pondDeviceDetailUI.detailModelTcp.getCl_state()!=null) {
                String tempStatus = myApp.pondDeviceDetailUI.detailModelTcp.getCl_state();
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
                setItsColor(wash_status);
            }
            txt_was_time.setText(myApp.pondDeviceDetailUI.detailModelTcp.getCl_dur() + getString(R.string.seconds));
            txt_was_time.setTag(myApp.pondDeviceDetailUI.detailModelTcp.getCl_dur());
            totalCount = myApp.pondDeviceDetailUI.detailModelTcp.getCl_dur();
            circleProgress.setMax(totalCount);
            circleProgress.setProgress(Integer.parseInt(myApp.pondDeviceDetailUI.detailModelTcp.getCl_sche()));
        }
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
//                wash_status = getString(R.string.current_status) + getString(R.string.pause_washing);
                //点击则变成关闭暂停状态
                userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), null, null, "", -1, "", "", "2", "", "", "", "", "", "", -1, -1, "", "", "", "", -1, -1);
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
                userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), null, null, "", -1, "", "", "1", "", "", "", "", "", "", -1, -1, "", "", "", "", -1, -1);
//                wash_status = getString(R.string.current_status) + getString(R.string.washing);
                //点击则变成开启状态
//                circleProgress.setStatus(CustomCircleProgress.Status.Starting);
//                    handler.sendMessage(message);
            }
//            setItsColor(wash_status);
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
            showTimePeroid();

        }

    }

    List<String> list = new ArrayList<>();

    private void showTimePeroid() {
        mNewTempValue = myApp.pondDeviceDetailUI.detailModelTcp.getCl_dur();
        View view = LayoutInflater.from(this).inflate(R.layout.alertdialog_number, null);
        WheelPicker myNumberPickerView = (WheelPicker) view.findViewById(R.id.number_picker1);
        list = Arrays.asList(getResources().getStringArray(R.array.timeArray));
        int position = list.indexOf(mNewTempValue + "");
        myNumberPickerView.setData(list);
        myNumberPickerView.setSelectedItemPosition(position == -1 ? 0 : position);
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(this);
        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userPresenter.deviceSet(myApp.pondDeviceDetailUI.deviceDetailModel.getDid(), null, null, "", -1, "", mNewTempValue + "", "", "", "", "", "", "", "", -1, -1, "", "", "", "", -1, -1);
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

    @Override
    protected void onDestroy() {
        myApp.shoudongUI = null;
        super.onDestroy();
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
            } else if (resultEntity.getEventType() == UserPresenter.deviceSet_fail) {
                MAlert.alert(resultEntity.getData());
            }
        }

    }
}
