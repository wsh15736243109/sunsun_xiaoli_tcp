package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.utils.loadingutil.RedBagWindow;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

public class RedBagAcitivty extends LingShouBaseActivity implements Observer {


    Button btn_getRedBag;
    LingShouPresenter lingShouPresenter;

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
                lingShouPresenter = new LingShouPresenter(this);
                lingShouPresenter.addCharge(getSp(Const.UID), getSp(Const.S_ID));
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == LingShouPresenter.addCharge_success) {
                RedBagWindow cameraConsolePopupWindow = new RedBagWindow(this, "获取成功");
                cameraConsolePopupWindow.showAtLocation(btn_getRedBag, Gravity.CENTER, 0, 0);
                SPUtils.put(this, null, Const.HAS_CHARGE, "1");
                Intent intent = new Intent(Const.YOUHUIQUAN_CHANGE);
                sendBroadcast(intent);
                finish();
            } else if (entity.getEventType() == LingShouPresenter.addCharge_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
