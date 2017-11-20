package sunsun.xiaoli.jiarebang.device.jinligang

import ChirdSdk.CHD_Client
import ChirdSdk.ClientCallBack
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.AsyncTask
import android.util.Log
import sunsun.xiaoli.jiarebang.R
import sunsun.xiaoli.jiarebang.app.App

/**
 * Created by Mr.w on 2017/11/15.
 */
class VideoHelper(activity: Activity, mClient: CHD_Client, iVideoInterface: VideoInterface) {

    private var mClient: CHD_Client? = null         /* 客户端类 */
    private var activity: Activity? = null
    private var iVideoInterface: VideoInterface? = null
    private var did: String? = null
    private var password: String? = null
    init {
        this.mClient = mClient
        this.activity = activity
        this.iVideoInterface = iVideoInterface
    }

    fun connectDevice(did: String, password: String) {
        this.did=did
        this.password=password
        MyTask().execute(did, password)
    }

    fun closeVideo(){
        mClient?.disconnectDevice()
        mClient?.closeVideoStream()
    }

    internal inner class MyTask : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg strings: String): String? {
            setCallBack(*strings)
            return ""
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            //统计流量
//            initFlowPlus()
//            setViewVisible(mClient.isConnect())
        }

    }

    private fun setCallBack(vararg strings: String) {
        mClient?.setClientCallBack(object : ClientCallBack {
            override fun paramChangeCallBack(changeType: Int) {
                iVideoInterface?.paramChangeCallBack(changeType)
            }

            override fun disConnectCallBack() {
                iVideoInterface?.disConnectCallBack()
            }

            override fun snapBitmapCallBack(bitmap: Bitmap?) {
                iVideoInterface?.snapBitmapCallBack(bitmap!!)
            }

            override fun recordTimeCountCallBack(times: String?) {
            }

            override fun recordStopBitmapCallBack(bitmap: Bitmap?) {
            }

            override fun videoStreamBitmapCallBack(bitmap: Bitmap?) {
                iVideoInterface?.videoStreamBitmapCallBack(bitmap!!)
            }

            override fun videoStreamDataCallBack(format: Int, width: Int, height: Int, datalen: Int, data: ByteArray?) {
            }

            override fun serialDataCallBack(datalen: Int, data: ByteArray?) {
            }

            override fun audioDataCallBack(datalen: Int, data: ByteArray?) {
            }

        })
//        var cameraDid = "SCHD-001009-ZWXGR"
//        var cameraPsw = "PCYkQXQg"
//        var re: Int = mClient?.connectDevice(cameraDid, cameraPsw)!!
        var re: Int = mClient?.connectDevice(strings[0], strings[1])!!
        iVideoInterface?.videoConnectStatus(re)
        mClient?.openVideoStream()
    }


    /**
     * @param re connectDevice函数返回值
     * @return
     */
    fun getVideoStatus(re: Int): String {

        Log.v("test", "video status = ${re}")
        when (re) {
            -2, -4 -> {
                return App.getInstance().getString(R.string.current_status) + App.getInstance().getString(R.string.connect_timeout)
            }
            -11 -> {
                return App.getInstance().getString(R.string.current_status) + App.getInstance().getString(R.string.connect_device_did_not_exsit)
            }
            -6, -12 -> {
                return App.getInstance().getString(R.string.current_status) + App.getInstance().getString(R.string.connect_device_offline)
            }
            -13 -> {
                return App.getInstance().getString(R.string.current_status) + App.getInstance().getString(R.string.connect_password_error)
            }
            0 -> {
                return App.getInstance().getString(R.string.current_status) + App.getInstance().getString(R.string.video_connect)
            }
            else -> {
//                return App.getInstance().getString(R.string.connect_password_unknown_error)
                return ""
            }
        }
    }

    fun showVideoMessage(activity: Activity, msg: String){
        var alert=AlertDialog.Builder(activity,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
        alert.setMessage(msg)
        alert.setNegativeButton(activity.getString(R.string.cancel)) { _, _ -> }
        alert.setPositiveButton(activity.getString(R.string.retry)) { _, _ ->
            connectDevice(did!!, password!!)
            iVideoInterface?.videoConnectInit()
        }
        if (!activity.isFinishing) {
            alert.create()
            alert.show()
        }
    }
}