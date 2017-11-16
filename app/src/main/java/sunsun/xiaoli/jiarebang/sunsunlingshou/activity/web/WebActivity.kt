package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.web

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.itboye.pondteam.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_publish.*
import kotlinx.android.synthetic.main.activity_inputwifi.*
import kotlinx.android.synthetic.main.activity_web.*
import sunsun.xiaoli.jiarebang.R

class WebActivity : BaseActivity() {
    override fun onClick(v: View?) {
        when (v) {
            img_back -> {
                finish()
            }
            else -> {
            }
        }
    }

    var webSettings: WebSettings? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        txt_title.text = intent.getStringExtra("title")
        webView.loadUrl(intent.getStringExtra("url"))
        if (intent.getStringExtra("title").equals("ConfigInfo")) {
            webView.loadUrl("javascript:getVersionCode("+"'"+"fdfe"+"'"+")")
        }
        webSettings = webView.settings
        //设置支持JavaScript
        webSettings?.javaScriptEnabled = true
        //设置不调用浏览器，使用本WebView
        webView.setWebViewClient(WebViewClient())

    }
}
