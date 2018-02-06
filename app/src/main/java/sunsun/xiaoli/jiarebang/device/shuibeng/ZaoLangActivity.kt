package sunsun.xiaoli.jiarebang.device.shuibeng

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.RelativeLayout
import android.widget.TextView
import com.itboye.pondteam.base.BaseActivity
import com.itboye.pondteam.bean.DeviceDetailModel
import com.itboye.pondteam.presenter.UserPresenter
import com.itboye.pondteam.utils.loadingutil.MAlert
import com.itboye.pondteam.volley.ResultEntity
import kotlinx.android.synthetic.main.activity_zao_lang.*
import sunsun.xiaoli.jiarebang.R
import sunsun.xiaoli.jiarebang.app.App
import java.util.*

class ZaoLangActivity : BaseActivity(), Observer {
    override fun update(o: Observable?, data: Any?) {

        var entity = data as ResultEntity
        MAlert.alert(entity.data)
        if (entity != null) {
            if (entity.code != 0) {

            } else {
                if (entity.eventType == UserPresenter.deviceSet_shuiBengsuccess) {

                } else if (entity.eventType == UserPresenter.deviceSet_shuiBengfail) {

                } else if (entity.eventType == zaolang_liuliang_success) {
                    app!!.deviceShuiBengUI.requestTime = System.currentTimeMillis()
                    gear = tempValue
                    setZaoLangData()
                } else if (entity.eventType == zaolang_zhouqi_success) {
                    app!!.deviceShuiBengUI.requestTime = System.currentTimeMillis()
                    wc = tempValue
                    setZaoLangData()
                } else if (entity.eventType == zaolang_success) {
                    app!!.deviceShuiBengUI.requestTime = System.currentTimeMillis()
                    if (we == 0) {
                        we = 1
                    } else {
                        we = 0
                    }
                    setZaoLangData()
                }
            }
        }
    }

    internal var txt_title: TextView? = null
    internal var img_back: ImageView? = null
    internal var re_zaolang_zhouqi_choose: RelativeLayout? = null
    internal var re_zaolang_liuliang_choose: RelativeLayout? = null
    var iv_zaolang_status: ImageView? = null
    internal var userPresenter: UserPresenter? = null
    var app: App? = null
    private val zaolang_success: String = "zaolang_success"

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_back -> {
                finish()
            }
            R.id.iv_zaolang_status -> {
                //造浪状态
                userPresenter!!.deviceSet_shuiBeng(app!!.deviceShuiBengUI.deviceDetailModel!!.did, -1, -1, -1, -1, -1, -1, -1, if (we == 0) {
                    1
                } else {
                    0
                }, -1, -1, zaolang_success)
            }
            R.id.re_zaolang_zhouqi_choose -> {
                //造浪周期
                val liuliang = arrayOfNulls<String>(9)
                for (i in liuliang.indices) {
                    liuliang[i] = String.format(getString(R.string.dang), i + 1)
                }
                showAlert(tv_zaolang_zhouqi, liuliang)
            }
            R.id.re_zaolang_liuliang_choose -> {
                //造浪周期
                val liuliang = arrayOfNulls<String>(9)
                for (i in liuliang.indices) {
                    liuliang[i] = String.format(getString(R.string.dang), i + 1)
                }
                showAlert(tv_zaolang_liuliang_gear, liuliang)
            }
            else -> {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zao_lang)
        txt_title!!.text = intent.getStringExtra("title")
        app = application as App?
        app!!.zaolangUI = this
        detailModel = app!!.deviceShuiBengUI.detailModelTcp
        getZaoLangStatus()
        setZaoLangData()
        userPresenter = UserPresenter(this)
    }

    private var tempValue: Int = 0

    private val zaolang_liuliang_success: String = "_zaolang_liuliang_success"

    private val zaolang_zhouqi_success: String = "_zaolang_zhouqi_success"

    private fun showAlert(tv: TextView, msg: Array<String?>) {
        val alert = AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT)
        //        alert.setTitle(title);
        val numberPicker = NumberPicker(this)
        numberPicker.displayedValues = msg
        numberPicker.minValue = 0
        numberPicker.maxValue = msg.size - 1
        numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        alert.setPositiveButton(R.string.ok) { dialog, which ->
            //设置档位
            when (tv) {
                tv_zaolang_zhouqi -> {
                    //设置造浪周期
                    userPresenter!!.deviceSet_shuiBeng(app!!.deviceShuiBengUI.deviceDetailModel!!.did, -1, -1, -1, -1, -1, -1, -1, -1, -1, numberPicker.value, zaolang_zhouqi_success)
                }
                tv_zaolang_liuliang_gear -> {
                    //设置造浪流量
                    userPresenter!!.deviceSet_shuiBeng(app!!.deviceShuiBengUI.deviceDetailModel!!.did, -1, -1, -1, -1, -1, -1, -1, -1, numberPicker.value, -1, zaolang_liuliang_success)
                }
                else -> {
                }
            }
            tempValue = numberPicker.value

        }
        alert.setNegativeButton(R.string.cancel) { dialog, which -> }
        alert.setView(numberPicker)
        alert.show()
    }

    private var we: Int = 0

    private var gear: Int = 0

    private var wc: Int = 0

    private var detailModel: DeviceDetailModel? = null

    private fun getZaoLangStatus() {
        //造浪状态	适用于CDP-WIFI水泵
        //0：正常循环模式
        //1：造浪模式
        we = detailModel!!.we
        /**
         * 造浪档位	适用于CDP-WIFI水泵
         * 0 - 9：最小到最大档位
         */
        gear = detailModel!!.gear + 1
        /**
         * 造浪周期	适用于CDP-WIFI水泵
         * 0 - 9：最小到最大档位
         */

        wc = detailModel!!.wc + 1
    }

    private fun setZaoLangData() {
        if (we == 1) {
            iv_zaolang_status?.setBackgroundResource(R.drawable.kai)
            setVisible(View.VISIBLE)
        } else {
            iv_zaolang_status?.setBackgroundResource(R.drawable.guan)
            setVisible(View.GONE)
        }
        app!!.deviceShuiBengUI.setZaoLangStatus(we)

        tv_zaolang_liuliang_gear.text = String.format(getString(R.string.dang), gear)
        tv_zaolang_zhouqi.text = String.format(getString(R.string.dang), wc)
    }

    private fun setVisible(isVisible: Int) {
        re_zaolang_zhouqi_choose!!.visibility = isVisible
        re_zaolang_liuliang_choose!!.visibility=isVisible
    }
}
