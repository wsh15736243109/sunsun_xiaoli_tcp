package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me;

import android.view.View;
import android.widget.Button;

import com.itboye.pondteam.base.LingShouBaseActivity;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;

import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class AdviceActivity extends LingShouBaseActivity {


    TranslucentActionBar actionBar;
    Button btn_submit;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_advice;
    }

    @Override
    protected void initData() {
        initTitlebarStyle1(this,actionBar,"意见反馈",R.mipmap.ic_left_light,"",0,"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.btn_submit:
                break;
        }
    }
}
