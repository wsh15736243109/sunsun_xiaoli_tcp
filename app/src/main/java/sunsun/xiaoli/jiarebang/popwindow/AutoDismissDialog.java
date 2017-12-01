package sunsun.xiaoli.jiarebang.popwindow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.itboye.pondteam.utils.loadingutil.MAlert;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.device.ActivityStepThree;

/**
 * Created by wsh on 2017/5/17.
 * 自动消失进度框
 */

public class AutoDismissDialog extends ProgressDialog implements DialogInterface.OnClickListener {
    int count = 0;
    private int FLAG_DISMISS = 1;
    private int FLAG_SHOW = -1;
    private int FLAG_TIMEOUT = -2;
    private boolean flag = true;
    ISmartConfigListener smartConfigListener;
    //自动消失的时间
    int timeOutSeconds = 0;
    Activity context;
    public AutoDismissDialog(Activity context, String title, String msg, String buttonLeft, String buttonRight, int timeOutSeconds, ISmartConfigListener smartConfigListener) {
        super(context);
        count = 0;
        this.context = context;
        this.timeOutSeconds = timeOutSeconds;
        this.smartConfigListener = smartConfigListener;
        setTitle(title);
        setMessage(msg);
//        setButton(BUTTON_POSITIVE, buttonRight, this);
//        setButton(BUTTON_NEGATIVE, buttonLeft, this);
    }

    @Override
    public void show() {
        super.show();
        if (timeOutSeconds != 1) {
            mThread.start();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        flag = false;
    }

    private Thread mThread = new Thread() {
        @Override
        public void run() {
            super.run();
            while (flag) {
                try {
                    count += 1000;
                    System.out.println("计数" + count);
                    Message msg = mHandler.obtainMessage();
//                    if (((ActivityStepThree)context).hasFind) {
//                        //应该是配置好了
//                        smartConfigListener.cameraCloseConfig();
//                    }
                    if (count == timeOutSeconds * 3) {
                        msg.what = FLAG_TIMEOUT;
                        smartConfigListener.cameraConfigTimeOut();
                    } else if (count >= timeOutSeconds-12||((ActivityStepThree)context).isBusy) {
                        //一分钟时间，应该是配置好了
                        smartConfigListener.cameraCloseConfig();
                    } else if (count < timeOutSeconds) {
                        msg.what = FLAG_SHOW;
                    } else if (count >= timeOutSeconds * 1.5 + 2000) {
                        msg.what = FLAG_DISMISS;
                    }
                    mHandler.sendMessage(msg);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == FLAG_DISMISS) {
                dismiss();
            } else if (msg.what == FLAG_TIMEOUT) {
                setMessage(App.getInstance().getString(R.string.timeout));
            }
        }

    };

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == BUTTON_POSITIVE) {
            MAlert.alert(BUTTON_POSITIVE);
        } else if (which == BUTTON_NEGATIVE) {
            MAlert.alert(BUTTON_NEGATIVE);
            smartConfigListener.cameraConfigCancel();

        }
    }

    public interface ISmartConfigListener {
        void cameraConfigTimeOut();

        void cameraConfigCancel();

        void cameraCloseConfig();
    }
}
