package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.web

import android.os.Bundle
import android.view.View
import com.itboye.pondteam.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_publish.*
import kotlinx.android.synthetic.main.activity_inputwifi.*
import kotlinx.android.synthetic.main.activity_web.*
import sunsun.xiaoli.jiarebang.R

class WebActivity :BaseActivity() {
    override fun onClick(v: View?) {
        when (v) {
            img_back-> {
                 finish()
            }
            else -> {
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        txt_title.text=intent.getStringExtra("title")
        webView.loadUrl(intent.getStringExtra("url"))
    }
}
