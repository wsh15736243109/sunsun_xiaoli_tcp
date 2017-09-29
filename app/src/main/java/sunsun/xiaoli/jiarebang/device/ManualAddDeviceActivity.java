package sunsun.xiaoli.jiarebang.device;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.DeviceListBean;
import com.itboye.pondteam.db.DBManager;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.BuildConfig;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.device.jinligang.AddDeviceNewActivity;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

@SuppressLint("NewApi")
public class ManualAddDeviceActivity extends BaseActivity implements Observer {

    App mApp;
    EditText manual_add_device_edit_did;
    EditText mEditTextPassword;
    EditText manual_add_device_edit_nickname;
    Button mButtonDelete;
    Context mContext;
    ManualAddDeviceActivity mThis;
    UserPresenter userPresenter;
    ImageView img_right;
    TextView txt_title,manual_add_device_textView1;
    DBManager dbManager;
    EditText manual_add_device_edit_password;
    String aq_did;
    private String did;
    private String nickName;
    private String psw;
    int position;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_add_device);
        mApp = (App) getApplication();
        mApp.mManualAddDeviceUi = this;
        manual_add_device_textView1.setText(R.string.shoudong_title2);
        mContext = this;
        position = getIntent().getIntExtra("position", 0);
        aq_did = getIntent().getStringExtra("aq_did");
        dbManager = new DBManager(this);
        mThis = this;
        userPresenter = new UserPresenter(this);
        txt_title.setText(getString(R.string.add_device));
        img_right.setBackgroundResource(R.drawable.ic_action_accept);
        mEditTextPassword = (EditText) findViewById(R.id.manual_add_device_edit_nickname);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 如果是返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mApp.mManualAddDeviceUi = null;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_right:
                did = manual_add_device_edit_did.getText().toString();
                nickName = manual_add_device_edit_nickname.getText().toString();
                if (did.equals("")) {
                    MAlert.alert(getString(R.string.deviceid_empty));
                    return;
                }
                if (did.length() <= 10) {
                    MAlert.alert(getString(R.string.did_error));
                    return;
                }
                if (nickName.equals("")) {
                    MAlert.alert(getString(R.string.device_name_empty));
                    return;
                }
                switch (position) {
                    case 0:
                        if (!did.startsWith("S03")) {
                            MAlert.alert("请输入" + AddDeviceNewActivity.name[position] + "的设备型号类型");
                            return;
                        }
                        break;
                    case 1:
                        if (!did.startsWith("S03")) {
                            MAlert.alert("请输入" + AddDeviceNewActivity.name[position] + "的设备型号类型");
                            return;
                        }
                        break;
                    case 2:
                        if (!did.startsWith("S03")) {
                            MAlert.alert("请输入" + AddDeviceNewActivity.name[position] + "的设备型号类型");
                            return;
                        }
                        break;
                    case 3:
                        if (!did.startsWith("S02")) {
                            MAlert.alert("请输入" + AddDeviceNewActivity.name[position] + "的设备型号类型");
                            return;
                        }
                        break;
                    case 4:
                        if (!did.startsWith("S04")) {
                            MAlert.alert("请输入" + AddDeviceNewActivity.name[position] + "的设备型号类型");
                            return;
                        }
                        break;
                    case 5:
                        if (!did.startsWith("S05")) {
                            MAlert.alert("请输入" + AddDeviceNewActivity.name[position] + "的设备型号类型");
                            return;
                        }
                        break;
                    case 6:
                        if (!did.startsWith("S01")) {
                            MAlert.alert("请输入" + AddDeviceNewActivity.name[position] + "的设备型号类型");
                            return;
                        }
                        break;
                    case 7:
                        if (!did.startsWith("SCHD")) {
                            MAlert.alert("请输入" + (AddDeviceNewActivity.name==null?getString(R.string.zhinengshexiangtou):AddDeviceNewActivity.name[position]) + "的设备型号类型");
                            return;
                        }
                        break;
                    case 8:
                        if (!did.startsWith("S06")) {
                            MAlert.alert("请输入" + AddDeviceNewActivity.name[position] + "的设备型号类型");
                            return;
                        }
                        break;
                }
                boolean hasAdd = false;
                //排除已经添加过的设备

                if (aq_did != null && !"".equals(aq_did)) {

                    boolean bindYes = hasBindAq();
                    if (bindYes) {
                        MAlert.alert(getString(R.string.hasBind));
                        return;
                    }
                }else {
                    if (mApp.mDeviceUi.arrayList != null) {
                        for (DeviceListBean deviceListBean : mApp.mDeviceUi.arrayList) {
                            if (deviceListBean.getDid().contains(did)) {
                                hasAdd = true;
                                break;
                            }
                        }
                    }
                    if (hasAdd) {
                        MAlert.alert(getString(R.string.hasAdd));
                        return;
                    }
                }
                psw = manual_add_device_edit_password.getText().toString();
                if (psw.equals("")) {
                    MAlert.alert(getString(R.string.psw_name_empty));
                    return;
                }
                int count = dbManager.queryDeviceCountByDid(did, getSp(Const.UID));
                if (count > 0) {
                    //先删除数据库设备，再进行添加
                    dbManager.deleteDeviceDataByDid(did, getSp(Const.UID));
                }
                dbManager.insertDeviceData(did, psw, getSp(Const.UID));
                HashMap<String, Object> hash = new HashMap<>();
                Gson gson = new Gson();

                if (did.startsWith("SCHD")) {
                    hash.put("pwd", psw);
                    String extra = gson.toJson(hash);
                    userPresenter.addDevice(getSp(Const.UID), did, nickName, "chiniao_wifi_camera", extra);
                } else {
                    if (BuildConfig.APP_TYPE.equals("pondTeam")) {
                        hash.put("notify_email", 1);
                    }
                    hash.put("first_upd", System.currentTimeMillis() + "");
                    hash.put("pwd", psw);
                    String extra = gson.toJson(hash);
                    if (position==2) {
                        userPresenter.addDevice(getSp(Const.UID), did, nickName, "S03-2", extra);
                    }else if (position==1){
                        userPresenter.addDevice(getSp(Const.UID), did, nickName, "S03-1", extra);
                    }else {
                        userPresenter.addDevice(getSp(Const.UID), did, nickName, did.substring(0, 3), extra);
                    }
                }
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
            if (entity.getEventType() == UserPresenter.adddevice_success) {
                MAlert.alert(entity.getData());
                mApp.mDeviceUi.getDeviceList();
                if (aq_did != null && !"".equals(aq_did)) {
                    //绑定从设备
                    //判断是否已经在绑定之列
                    boolean bindYes = hasBindAq();
                    if (!bindYes) {
                        userPresenter.cameraBind(aq_did, did, "chiniao_wifi_camera", did, psw);
                    } else {
                        //结束上一个activity
                        if (mApp.addDeviceUI!=null) {
                            mApp.addDeviceUI.finish();
                        }

                        mApp.mDeviceUi.mListView.smoothScrollToPosition(0);
                        //结束当前activity
                        finish();
                    }
                } else {
                    //结束上一个activity
                    mApp.addDeviceUI.finish();

                    mApp.mDeviceUi.mListView.smoothScrollToPosition(0);
                    //结束当前activity
                    finish();
                }

            } else if (entity.getEventType() == UserPresenter.adddevice_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.cameraBind_success) {
                //结束上一个activity
                if (mApp.addDeviceUI!=null) {
                    mApp.addDeviceUI.finish();
                }
                //结束当前activity
                finish();
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.cameraBind_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    private boolean hasBindAq() {
        boolean isAdd = false;
        if (mApp.mCameraDevice.arrayList != null) {
            for (int i = 0; i < mApp.mCameraDevice.arrayList.size(); i++) {
                if (did.equals(mApp.mCameraDevice.arrayList.get(i).getSlave_did())) {
                    isAdd = true;
                    break;
                } else {
                    isAdd = false;
                }
            }
        }
        return isAdd;
    }
}
