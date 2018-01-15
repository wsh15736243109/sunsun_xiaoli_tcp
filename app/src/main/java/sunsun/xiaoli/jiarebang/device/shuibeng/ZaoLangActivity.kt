package sunsun.xiaoli.jiarebang.device.shuibeng

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.RelativeLayout
import android.widget.TextView
import com.itboye.pondteam.base.BaseActivity
import kotlinx.android.synthetic.main.activity_zao_lang.*
import sunsun.xiaoli.jiarebang.R

class ZaoLangActivity : BaseActivity() {

    internal var txt_title: TextView? = null
    internal var img_back: ImageView? = null
    internal var re_zaolang_zhouqi_choose: RelativeLayout? = null
    internal var iv_zaolang_status: ImageView? = null

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_back -> {
                finish()
            }
            R.id.iv_zaolang_status -> {
                //造浪状态
            }
            R.id.re_zaolang_zhouqi_choose -> {
                //造浪周期
                val liuliang = arrayOfNulls<String>(5)
                for (i in liuliang.indices) {
                    liuliang[i] = String.format(getString(R.string.dang), i + 1)
                }
                showAlert(tv_zaolang_zhouqi_gear, getString(R.string.liuliang_choose), liuliang)
            }
            else -> {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zao_lang)
        txt_title!!.text = intent.getStringExtra("title")
    }

    private fun showAlert(txt_liuliangchoose: TextView, title: String, msg: Array<String?>) {
        val alert = AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT)
        //        alert.setTitle(title);
        val numberPicker = NumberPicker(this)
        numberPicker.displayedValues = msg
        numberPicker.minValue = 0
        numberPicker.maxValue = msg.size - 1
        numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        alert.setPositiveButton(R.string.ok) { dialog, which ->
            when (txt_liuliangchoose.id) {
                R.id.txt_liuliangchoose -> {
                }
            //设置档位
//                    userPresenter.deviceSet_shuiBeng(did, -1, -1, numberPicker.value, -1, -1, -1)
            }//                        userPresenter.deviceSet_shuiBeng(did, -1, -1, -1, -1, -1, fcd * 60);
        }
        alert.setNegativeButton(R.string.cancel) { dialog, which -> }
        alert.setView(numberPicker)
        alert.show()
    }
}
