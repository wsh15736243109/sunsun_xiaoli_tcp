package sunsun.xiaoli.jiarebang.device.jinligang

import android.graphics.Bitmap

/**
 * Created by Mr.w on 2017/11/15.
 */
interface VideoInterface {
    fun videoConnectInit()
    fun videoConnectStatus(result: Int)
    fun videoStreamBitmapCallBack(bitmap: Bitmap)
    fun snapBitmapCallBack(bitmap: Bitmap)
    fun disConnectCallBack()
    fun paramChangeCallBack(changeType: Int)
}