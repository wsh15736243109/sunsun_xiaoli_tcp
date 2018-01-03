package sunsun.xiaoli.jiarebang.utils;

import android.os.Handler;

import com.itboye.pondteam.utils.loadingutil.MAlert;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;

import static com.itboye.pondteam.utils.Const.requestTimeInternal;

/**
 * Created by Administrator on 2017/9/25.
 */

public class RequestUtil {
    public static void threadStart(Handler handler,Runnable runnable){
        handler.removeCallbacks(runnable);
        new Thread(runnable).start();
    }

    public static boolean caculateRequestTimeInternal(long requestTime) {
        if (System.currentTimeMillis() - requestTime < requestTimeInternal) {
            MAlert.alert(App.getInstance().getString(R.string.opertation_fast));
            return false;
        } else {
            return true;
        }
    }
}
