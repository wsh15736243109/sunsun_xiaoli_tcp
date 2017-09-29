package sunsun.xiaoli.jiarebang.popwindow;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Message;

/**
 * Created by wsh on 2017/5/17.
 * 自动消失进度框
 */

public class SureDeleteDialog extends AlertDialog {
    int count = 0;
    private int FLAG_DISMISS = 1;
    private int FLAG_SHOW = -1;
    private int FLAG_TIMEOUT = -2;
    //自动消失的时间
    int timeOutSeconds = 0;

    public SureDeleteDialog(Context context, String title, String msg, String buttonLeft, String buttonRight, int timeOutSeconds) {
        super(context);
        count = 0;
        this.timeOutSeconds = timeOutSeconds;
        setTitle(title);
        setMessage(msg);
        setButton(BUTTON_POSITIVE, buttonRight, new Message());
        setButton(BUTTON_NEGATIVE, buttonLeft, new Message());
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
