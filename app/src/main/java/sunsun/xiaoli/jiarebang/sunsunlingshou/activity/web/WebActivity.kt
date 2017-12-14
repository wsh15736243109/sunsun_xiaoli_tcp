package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.*
import com.itboye.pondteam.base.BaseActivity
import com.itboye.pondteam.utils.Const
import com.itboye.pondteam.utils.EmptyUtil.getSp
import com.itboye.pondteam.utils.loadingutil.MAlert
import com.itboye.pondteam.utils.udp.VersionUtil
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
    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        txt_title.text = intent.getStringExtra("title")
        webView.loadUrl(intent.getStringExtra("url"))
        webView.addJavascriptInterface(JSInterface(), "android")
        webSettings = webView.settings
        //设置支持JavaScript
        webSettings?.loadWithOverviewMode = true
        webSettings?.javaScriptEnabled = true
        //设置不调用浏览器，使用本WebView
        webView.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (intent.getStringExtra("title") == "ConfigInfo") {
                    webView.loadUrl("javascript:getVersionCode('" + VersionUtil.getVersionCode() + ":" + getSp(Const.UID) + "')")
//                    webView.reload()
                }
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
//                MAlert.alert("当前无网络连接")
                super.onReceivedError(view, request, error)
            }
        })
        img_back.setOnClickListener(WebActivity@ this)
    }

    class JSInterface {
        @JavascriptInterface
        fun getSomeThing() {
            MAlert.alert("当前应用版本号：" + VersionUtil.getVersionCode())
        }
    }
}
