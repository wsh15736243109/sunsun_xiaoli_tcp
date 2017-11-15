package sunsun.xiaoli.jiarebang.device.jinligang

import android.util.Log
import sunsun.xiaoli.jiarebang.R
import sunsun.xiaoli.jiarebang.app.App

/**
 * Created by Mr.w on 2017/11/15.
 */
class VideoHelper {














    /**
     * @param re connectDevice函数返回值
     * @return
     */
    fun getVideoStatus(re: Int): String {
        when (re) {
            -2, -4 -> {
                return App.getInstance().getString(R.string.current_status)+App.getInstance().getString(R.string.connect_timeout)
            }
            -11 -> {
                return App.getInstance().getString(R.string.current_status)+App.getInstance().getString(R.string.connect_device_did_not_exsit)
            }
            -6, -12 -> {
                return App.getInstance().getString(R.string.current_status)+App.getInstance().getString(R.string.connect_device_offline)
            }
            -13 -> {
                return App.getInstance().getString(R.string.current_status)+App.getInstance().getString(R.string.connect_password_error)
            }
            0 ->{
                return App.getInstance().getString(R.string.current_status)+App.getInstance().getString(R.string.video_connect)
            }
            else -> {
//                return App.getInstance().getString(R.string.connect_password_unknown_error)
                return ""
            }
        }
        Log.v("test","video status = " + re)
    }
}