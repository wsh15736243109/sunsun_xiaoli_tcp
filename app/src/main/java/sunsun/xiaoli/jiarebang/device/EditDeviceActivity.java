package sunsun.xiaoli.jiarebang.device;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.DeviceListBean;
import com.itboye.pondteam.db.DBManager;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

@SuppressLint("NewApi")
public class EditDeviceActivity extends BaseActivity implements Observer {

    App mApp;
    TextView mTextViewDid;
    TextView mTextViewName;
    EditText mEditTextName;
    Button mButtonDelete;
    Context mContext;
    EditDeviceActivity mThis;
    EditText mEditTextPassword;
    CheckBox mCheckBoxPassword;
    DBManager dbManager;
    private String psw;
    UserPresenter userPresenter;
    TextView txt_right;
    ImageView img_back;
    private String pass;
    String id;
    boolean isMasterDevice = false;
    DeviceListBean deviceListBean;
    String type;
    ImageView edit_device_imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device);
        mApp = (App) getApplication();
        mApp.mEditDeviceUi = this;
        id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
        setIcon();
        userPresenter = new UserPresenter(this);
        dbManager = new DBManager(this);
        if (mApp.mEditDeviceInfo == null) {
            this.finish();
        }
        isMasterDevice = getIntent().getBooleanExtra("isMasterDevice", true);
        deviceListBean = (DeviceListBean) getIntent().getSerializableExtra("model");

        mContext = this;
        mThis = this;

        mTextViewName = (TextView) findViewById(R.id.edit_device_textView1);
        mTextViewDid = (TextView) findViewById(R.id.edit_device_textView2);
        mEditTextName = (EditText) findViewById(R.id.edit_device_editText1);
        mButtonDelete = (Button) findViewById(R.id.edit_device_button1);
        mEditTextPassword = (EditText) findViewById(R.id.edit_device_editText2);
        mCheckBoxPassword = (CheckBox) findViewById(R.id.edit_device_checkBox1);

        mEditTextName.setText(mApp.mEditDeviceInfo.getDevice_nickname());
        mTextViewName.setText(mApp.mEditDeviceInfo.getDevice_nickname());
        if (isMasterDevice) {
//            psw = dbManager.queryDevicePswByDid(mApp.mEditDeviceInfo.getDid(), getSp(Const.UID));
            try {
                psw = new JSONObject(mApp.mEditDeviceInfo.getExtra()).getString("pwd");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            if (deviceListBean != null) {
                psw = deviceListBean.getSlave_pwd();
            }
        }
        if (isMasterDevice) {
            //只有主设备才能修改名称
            txt_right.setVisibility(View.VISIBLE);
            txt_right.setText(getString(R.string.save));
        } else {
            mEditTextName.setEnabled(false);
            mEditTextPassword.setEnabled(false);
            mButtonDelete.setText(getString(R.string.unbind_device));
            mEditTextName.setText(deviceListBean.getSlave_name());
        }
        mTextViewDid.setText(mApp.mEditDeviceInfo.getDid());

        mEditTextPassword.setKeyListener(null);
        mEditTextPassword.setText("********");

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMasterDevice) {
                    new AlertDialog.Builder(mContext)
                            .setTitle(getString(R.string.tips))
                            .setMessage(getString(R.string.make_sure_delete))
                            .setPositiveButton(getString(R.string.no), null)
                            .setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 删除设备
                                    userPresenter.deleteDevice(id, getSp(Const.UID));
//								mApp.removeDevice(mApp.mEditDeviceInfo);
//                                    mThis.finish();
                                }
                            })
                            .show();
                } else {
                    new AlertDialog.Builder(mContext)
                            .setTitle(getString(R.string.tips))
                            .setMessage(getString(R.string.makesure_unbind_device))
                            .setPositiveButton(getString(R.string.no), null)
                            .setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 删除设备
                                    userPresenter.cameraUnBind(deviceListBean.getMaster_did(), deviceListBean.getSlave_did());
//								mApp.removeDevice(mApp.mEditDeviceInfo);
//                                    mThis.finish();
                                }
                            })
                            .show();
                }
            }
        });

        mCheckBoxPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 密码显示状态改变
                if (isChecked) {
                    mEditTextPassword.setText(psw);
                } else {
                    mEditTextPassword.setText("********");
                }
            }
        });
    }

    private void setIcon() {
        if (type.equalsIgnoreCase("S01")) {
            edit_device_imageView1.setBackgroundResource(R.drawable.device_chitangguolv);
        } else if (type.equalsIgnoreCase("S02")) {

            edit_device_imageView1.setBackgroundResource(R.drawable.device_jiarebang);
        } else if (type.equalsIgnoreCase("S03") ) {
            edit_device_imageView1.setBackgroundResource(R.drawable.device_aq);
        }else if (type.equalsIgnoreCase("S03-1")){
            edit_device_imageView1.setBackgroundResource(R.drawable.device_500);
        }else if (type.equalsIgnoreCase("S03-2")){
            edit_device_imageView1.setBackgroundResource(R.drawable.device_700);
        } else if (type.equalsIgnoreCase("S04")) {
            edit_device_imageView1.setBackgroundResource(R.drawable.device_ph);
        } else if (type.equalsIgnoreCase("S05")) {

            edit_device_imageView1.setBackgroundResource(R.drawable.device_shuibeng);
        } else if (type.equalsIgnoreCase("S06")) {

            edit_device_imageView1.setBackgroundResource(R.drawable.device_shuizudeng);
        } else if (type.equalsIgnoreCase("SCHD") || type.equalsIgnoreCase("chiniao_wifi_camera")) {

            edit_device_imageView1.setBackgroundResource(R.drawable.device_shexiangtou);
        }
    }


//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
//		if (id == android.R.id.home) {
//			// 返回
//			mApp.mEditDeviceUi = null;
//			this.finish();
//			return true;
//		}
//		if (id == R.id.edit_device_action_settings1) {
//			// 保存并返回
//			mApp.mEditDeviceInfo.mDeviceName = mEditTextName.getText().toString();
//			mApp.addDevice(mApp.mEditDeviceInfo);
//			this.finish();
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 如果是返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mApp.mEditDeviceUi = null;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.txt_right) {
            String deviceName = mEditTextName.getText().toString();
            if (deviceName.equals("")) {
                MAlert.alert(getString(R.string.device_name_empty));
                return;
            }
            pass = mEditTextPassword.getText().toString();
            if (pass.equals("")) {
                MAlert.alert(getString(R.string.password_empty));
                return;
            }
            userPresenter.updateDeviceName(mApp.mEditDeviceInfo.getId(), deviceName, "", "", "", "", -1, -1);
        } else if (i == R.id.img_back) {
            finish();

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

            if (entity.getEventType() == UserPresenter.deleteDevice_success) {
                MAlert.alert(entity.getData());
                finish();
            } else if (entity.getEventType() == UserPresenter.deleteDevice_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.update_devicename_success) {
                long queryRe = dbManager.queryDeviceCountByDid(mApp.mEditDeviceInfo.getDid(), getSp(Const.UID));
                if (queryRe > 0) {
                    long re = dbManager.updateDeviceData(mApp.mEditDeviceInfo.getDid(), pass, getSp(Const.UID));
                    if (re > 0) {
                        MAlert.alert(getString(R.string.update_success));
                        finish();
                    } else {
                        MAlert.alert(getString(R.string.update_fail));
                    }
                } else {
                    long re = dbManager.insertDeviceData(mApp.mEditDeviceInfo.getDid(), pass, getSp(Const.UID));
                    if (re > 0) {
                        MAlert.alert(getString(R.string.insert_success));
                        finish();
                    } else {
                        MAlert.alert(getString(R.string.insert_fail));
                    }
                }
            } else if (entity.getEventType() == UserPresenter.update_devicename_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.cameraUnBind_success) {
                MAlert.alert(entity.getData());
                finish();
            } else if (entity.getEventType() == UserPresenter.cameraUnBind_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
