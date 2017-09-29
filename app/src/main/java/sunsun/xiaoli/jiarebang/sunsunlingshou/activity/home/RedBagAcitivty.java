package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.itboye.pondteam.base.LingShouBaseActivity;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.utils.loadingutil.RedBagWindow;

public class RedBagAcitivty extends LingShouBaseActivity {


    Button btn_getRedBag;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_red_bag_acitivty;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getRedBag:
                RedBagWindow cameraConsolePopupWindow = new RedBagWindow(this, "获取成功");
                cameraConsolePopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                break;
        }
    }
}
