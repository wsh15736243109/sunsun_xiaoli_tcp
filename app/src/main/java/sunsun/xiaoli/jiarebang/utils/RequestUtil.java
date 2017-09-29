package sunsun.xiaoli.jiarebang.utils;

import android.os.Handler;

/**
 * Created by Administrator on 2017/9/25.
 */

public class RequestUtil {
    public static void threadStart(Handler handler,Runnable runnable){
        handler.removeCallbacks(runnable);
        new Thread(runnable).start();
    }
}
