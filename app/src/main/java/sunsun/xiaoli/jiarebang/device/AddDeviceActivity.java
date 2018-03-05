package sunsun.xiaoli.jiarebang.device;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.bean.DeviceListBean;
import com.itboye.pondteam.db.DBManager;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.BuildConfig;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.SearchDeviceInfo;
import sunsun.xiaoli.jiarebang.device.aq118.Aq118DetailActivity;
import sunsun.xiaoli.jiarebang.device.jiarebang.DeviceJiaReBangDetailActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.JinLiGangDetailActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.VideoActivity;
import sunsun.xiaoli.jiarebang.device.led.LEDDetailActivity;
import sunsun.xiaoli.jiarebang.device.phdevice.DevicePHDetailActivity;
import sunsun.xiaoli.jiarebang.device.pondteam.ActivityPondDeviceDetail;
import sunsun.xiaoli.jiarebang.device.qibeng.DeviceQiBengDetailActivity;
import sunsun.xiaoli.jiarebang.device.shuibeng.DeviceShuiBengDetailActivity;
import sunsun.xiaoli.jiarebang.utils.DeviceType;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.utils.Util.getNickName;


/**
 * 日期：2015-05-05
 * 1. 自动同步设备名称
 * 日期：2015-05-12
 * 1. 点击已经添加的设备提示到设备列表中连接设备
 */
@SuppressLint("NewApi")
public class AddDeviceActivity extends BaseActivity implements Observer {

    private String selectDviceType = "";
    ListView mListView;
    App mApp;
    Context mContext;
    SearchDeviceInfo mSelectDeviceInfo;
    TextView txt_title;
    UserPresenter userPresenter;
    private ArrayList<HashMap<String, Object>> listItems;
    ImageView img_back;
    DBManager dbManager;
    String aq_did;
    TextView txt_title_2;
    DeviceType deviceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        mApp = (App) getApplication();
        mApp.mAddDeviceUi = this;
        mContext = this;
        aq_did = getIntent().getStringExtra("aq_did");
        deviceType = (DeviceType) getIntent().getSerializableExtra("device");
        txt_title.setText(getString(R.string.LANDEVICE));
        txt_title_2.setText(R.string.connect_device);
        mSelectDeviceInfo = null;
        dbManager = new DBManager(this);
        userPresenter = new UserPresenter(this);
        mListView = (ListView) findViewById(R.id.add_device_listView);
        // 列表项短按键处理
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                mSelectDeviceInfo = mApp.mLanDeviceList.get(position);
                mSelectDeviceInfo = new SearchDeviceInfo();
                String did = listItems.get(position).get("ItemDid").toString().substring(4, listItems.get(position).get("ItemDid").toString().length());
                String psw = listItems.get(position).get("ItemPsw").toString();
                mSelectDeviceInfo.setDid(did);
                mSelectDeviceInfo.setPwd(psw);
                mSelectDeviceInfo.setType(listItems.get(position).get("ItemDeviceType").toString());
                AddDeviceActivity.this.selectDviceType = mSelectDeviceInfo.getType();
                //测试版本直接进入设备详情
                if (BuildConfig.APP_TYPE.equals("小鲤智能测试版")) {
                    getDeviceData(did);
                } else {
                    if ((boolean) (listItems.get(position).get("add_status"))) {
                        new AlertDialog.Builder(mContext)
                                .setTitle(getString(R.string.tips))
                                .setMessage(getString(R.string.hasAdd))
                                .setPositiveButton(getString(R.string.ok), null)
                                .show();
                        return;
                    }
                    new AlertDialog.Builder(mContext)
                            .setTitle(getString(R.string.tips))
                            .setMessage(getString(R.string.make_sure_adddevice))
                            .setPositiveButton(getString(R.string.no), null)
                            .setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (aq_did != null) {
                                        boolean bindYes = hasBindAq();
                                        if (bindYes) {
                                            MAlert.alert(getString(R.string.hasBind));
                                            return;
                                        }
                                    }
                                    String type = mSelectDeviceInfo.getType();
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    Gson gson = new Gson();
                                    if (type == null) {
                                        MAlert.alert(getString(R.string.device_type_empty) + "-------->" + mSelectDeviceInfo.toString());
                                        return;
                                    }
                                    if (mSelectDeviceInfo.getDid() == null) {
                                        MAlert.alert(getString(R.string.did_empty));
                                        return;
                                    }
                                    if (mSelectDeviceInfo.getDid().equals("")) {
                                        MAlert.alert(getString(R.string.did_empty));
                                        return;
                                    }
                                    if (mSelectDeviceInfo.getDid().startsWith("SCHD")) {
                                        hashMap.put("pwd", mSelectDeviceInfo.getPwd());
                                        String extra = gson.toJson(hashMap);
                                        userPresenter.addDevice(getSp(Const.UID), mSelectDeviceInfo.getDid(), BuildConfig.APP_TYPE.equals("小绵羊智能") ? getString(R.string.device_zhinengshexiangtou_yihu) : getString(R.string.device_zhinengshexiangtou), "chiniao_wifi_camera", extra);
                                    } else {
                                        if (BuildConfig.APP_TYPE.equals("pondTeam")) {
                                            hashMap.put("notify_email", 1);
                                        }
                                        hashMap.put("first_upd", System.currentTimeMillis() + "");
                                        hashMap.put("pwd", mSelectDeviceInfo.getPwd());
                                        String extra = gson.toJson(hashMap);
                                        switch (deviceType) {
                                            case DEVICE_AQ500:
                                                userPresenter.addDevice(getSp(Const.UID), mSelectDeviceInfo.getDid(), App.getInstance().getString(R.string.device_zhineng500), "S03-1", extra);
                                                break;
                                            case DEVICE_AQ700:
                                                userPresenter.addDevice(getSp(Const.UID), mSelectDeviceInfo.getDid(), App.getInstance().getString(R.string.device_zhineng700), "S03-2", extra);
                                                break;
                                            case DEVICE_AQ600:
                                                userPresenter.addDevice(getSp(Const.UID), mSelectDeviceInfo.getDid(), App.getInstance().getString(R.string.device_zhineng600), "S03-3", extra);
                                                break;
//                                        case DEVICE_SHUIZUDENG:
//                                            userPresenter.addDevice(getSp(Const.UID), mSelectDeviceInfo.getDid(), type.equalsIgnoreCase("S06-1") ? "ADT-C" : "ADT-H", mSelectDeviceInfo.getType(), extra);
//                                            break;
                                            default:
                                                userPresenter.addDevice(getSp(Const.UID), mSelectDeviceInfo.getDid(), getNickName(mSelectDeviceInfo.getDid(), type), mSelectDeviceInfo.getType(), extra);
                                                break;
                                        }
                                    }
                                    refreshDeviceList();
                                }
                            })
                            .show();
                }
            }
        });
        refreshDeviceList();
    }

    private void getDeviceData(String did) {
        userPresenter.getDeviceDetailInfo(did, "0");
    }

    private void startDeviceUI(DeviceDetailModel deviceDetailModel) {
        Intent intent = null;
        String deviceType = selectDviceType;
        if (deviceType.contains("S02")) {
            //加热棒
            intent = new Intent(this, DeviceJiaReBangDetailActivity.class);
        } else if (deviceType.contains("S01")) {
            //过滤桶
            intent = new Intent(this, ActivityPondDeviceDetail.class);
        } else if (deviceType.contains("S03")) {
            //806
            intent = new Intent(this, JinLiGangDetailActivity.class);
        } else if (deviceType.contains("S04")) {
            //aph
            intent = new Intent(this, DevicePHDetailActivity.class);
        } else if (deviceType.contains("S05")) {
            //变频水泵
            intent = new Intent(this, DeviceShuiBengDetailActivity.class);
        } else if (deviceType.contains("S06")) {
            //Led水族灯
            intent = new Intent(this, LEDDetailActivity.class);
        } else if (deviceType.contains("S07")) {
            //CP1000
            intent = new Intent(this, DeviceQiBengDetailActivity.class);
        } else if (deviceType.contains("S08")) {
            //AQ118
            intent = new Intent(this, Aq118DetailActivity.class);
        } else if (deviceType.contains("S09")) {
            //变频水泵
            intent = new Intent(this, DeviceShuiBengDetailActivity.class);
        } else if (deviceType.contains("chiniao_wifi_camera")) {
            //摄像头
            intent = new Intent(this, VideoActivity.class);
        } else {
            MAlert.alert(getString(R.string.no_support_device));
            return;
        }
        intent.putExtra("title", deviceDetailModel.getDevice_nickname());
        intent.putExtra("did", mSelectDeviceInfo.getDid());
        intent.putExtra("id", deviceDetailModel.getId());
        intent.putExtra("hasPsw", true);//无密码则应该重新进入插入密码
        intent.putExtra("detailModel", deviceDetailModel);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mApp.isStartSearch = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApp.isStartSearch = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 如果是返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mApp.mAddDeviceUi = null;
        }
        return super.onKeyDown(keyCode, event);
    }

    HashMap<String, Object> map = new HashMap<>();

    /**
     * 刷新局域网设备列表
     */
    public void refreshDeviceList() {
        listItems = new ArrayList<>();
        for (int i = 0; i < mApp.mLanDeviceList.size(); i++) {
            boolean hasAdd = false;
            SearchDeviceInfo deviceInfo = mApp.mLanDeviceList.get(i);
            switch (deviceType) {
                case DEVICE_AQ806:
                    if (!deviceInfo.getType().equalsIgnoreCase("S03")) {
                        continue;
                    }
                    break;
                case DEVICE_AQ500:
                    if (!deviceInfo.getType().equalsIgnoreCase("S03-1")) {
                        continue;
                    }
                    break;
                case DEVICE_AQ700:
                    if (!deviceInfo.getType().equalsIgnoreCase("S03-2")) {
                        continue;
                    }
                    break;
                case DEVICE_AQ600:
                    if (!deviceInfo.getType().equalsIgnoreCase("S03-3")) {
                        continue;
                    }
                    break;
                case DEVICE_AQ118:
                    if (!deviceInfo.getType().equalsIgnoreCase("S08")) {
                        continue;
                    }
                    break;
                case DEVICE_JIAREBANG:
                    if (!deviceInfo.getDid().startsWith("S02")) {
                        continue;
                    }
                    break;
                case DEVICE_PH:
                    if (!deviceInfo.getDid().startsWith("S04")) {
                        continue;
                    }
                    break;
                case DEVICE_SHUIBENG:
                    if (!deviceInfo.getDid().startsWith("S05")) {
                        continue;
                    }
                    break;
                case DEVICE_GUOLVTONG:
                    if (!deviceInfo.getDid().startsWith("S01")) {
                        continue;
                    }
                    break;
                case DEVICE_CAMERA:
                    if (!deviceInfo.getDid().startsWith("SCHD")) {
                        continue;
                    }
                    break;
                case DEVICE_SHUIZUDENG:
                    if (!deviceInfo.getDid().startsWith("S06")) {
                        continue;
                    }
                    break;
                case DEVICE_QIBENG:
                    if (!deviceInfo.getDid().startsWith("S07")) {
                        continue;
                    }
                    break;
                case DEVICE_WEISHIQI:
                    if (!deviceInfo.getDid().startsWith("S08")) {
                        continue;
                    }
                    break;

            }

            map = new HashMap<>();
            map.put("ItemDid", "DID:" + deviceInfo.getDid());
            //主设备添加状态
            if (aq_did == null) {
                if (mApp.mDeviceUi != null) {
                    if (mApp.mDeviceUi.arrayList.size() > 0) {
                        for (int i1 = 0; i1 < mApp.mDeviceUi.arrayList.size(); i1++) {
                            DeviceListBean deviceInfoInner = mApp.mDeviceUi.arrayList.get(i1);
                            if (deviceInfoInner.getDid().equals(deviceInfo.getDid())) {
                                hasAdd = true;
                                break;
                            } else {
                                hasAdd = false;
                            }
                        }
                    }
                } else if (mApp.mXiaoLiUi != null) {
                    if (mApp.mXiaoLiUi.arrayList.size() > 0) {
                        for (int i1 = 0; i1 < mApp.mXiaoLiUi.arrayList.size(); i1++) {
                            DeviceListBean deviceInfoInner = mApp.mXiaoLiUi.arrayList.get(i1);
                            if (deviceInfoInner.getDid().equals(deviceInfo.getDid())) {
                                hasAdd = true;
                                break;
                            } else {
                                hasAdd = false;
                            }
                        }
                    }
                }

            } else {
                if (mApp.mCameraDevice.arrayList.size() > 0) {
                    for (int i1 = 0; i1 < mApp.mCameraDevice.arrayList.size(); i1++) {
                        DeviceListBean deviceInfoInner = mApp.mCameraDevice.arrayList.get(i1);
                        if (deviceInfoInner.getSlave_did().equals(deviceInfo.getDid())) {
                            hasAdd = true;
                            break;
                        } else {
                            hasAdd = false;
                        }
                    }
                }
            }
            //从设备绑定状态

            if (deviceInfo.getType().equalsIgnoreCase("S01")) {
                //过滤桶
                map.put("ItemIcon", R.drawable.device_chitangguolv);
            } else if (deviceInfo.getType().equalsIgnoreCase("S02")) {
                //加热棒
                map.put("ItemIcon", R.drawable.device_jiarebang);
            } else if (deviceInfo.getType().equalsIgnoreCase("S03")) {
                map.put("ItemIcon", R.drawable.device_aq);
            } else if (deviceInfo.getType().equalsIgnoreCase("S03-1")) {
                map.put("ItemIcon", R.drawable.device_500);
            } else if (deviceInfo.getType().equalsIgnoreCase("S03-2")) {
                map.put("ItemIcon", R.drawable.device_700);
            } else if (deviceInfo.getType().equalsIgnoreCase("S03-3")) {
                map.put("ItemIcon", R.drawable.device_600);
            } else if (deviceInfo.getType().equalsIgnoreCase("S04")) {
                map.put("ItemIcon", R.drawable.device_ph);
            } else if (deviceInfo.getType().startsWith("S05")) {
                map.put("ItemIcon", R.drawable.device_shuibeng);
            } else if (deviceInfo.getType().startsWith("S06")) {
                map.put("ItemIcon", R.drawable.device_shuizudeng);
            } else if (deviceInfo.getType().equalsIgnoreCase("S07")) {
                map.put("ItemIcon", R.drawable.device_jiaozhiliubeng);
            } else if (deviceInfo.getType().equalsIgnoreCase("SCHD")) {
                map.put("ItemIcon", R.drawable.device_shexiangtou);
            } else if (deviceInfo.getType().equalsIgnoreCase("S08")) {
                map.put("ItemIcon", R.drawable.device_118);
            } else {
                map.put("ItemIcon", R.drawable.ic_aplacher);
            }
            if (!hasAdd) {
                map.put("ItemState1", getString(R.string.hasU));
                map.put("ItemState2", "");
            } else {
                map.put("ItemState1", "");
                map.put("ItemState2", getString(R.string.hasA));
            }
            map.put("ItemName", deviceInfo.getDid());
            map.put("ItemRightArrow", null);
            map.put("add_status", hasAdd);
            map.put("ItemPsw", deviceInfo.getPwd());
            map.put("ItemDeviceType", deviceInfo.getType());
            map.put("ItemDeviceVer", deviceInfo.getVer());
            map.put("ItemDeviceRes", deviceInfo.getRes());
            listItems.add(map);
        }
        SimpleAdapter listItemsAdapter = new SimpleAdapter(this, listItems, R.layout.device_item,
                new String[]{"ItemName", "ItemDid", "ItemState1", "ItemState2", "ItemRightArrow", "ItemIcon"},
                new int[]{R.id.textView_dev_name, R.id.textView_dev_did, R.id.textView_dev_state1, R.id.textView_dev_state2, R.id.imageView_dev_right_arrow, R.id.imageView_dev_logo});
        mListView.setAdapter(listItemsAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
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
                if (mApp.mXiaoLiUi != null) {
                    mApp.mXiaoLiUi.getDeviceList();
                }
                if (mApp.mDeviceUi != null) {
                    mApp.mDeviceUi.getDeviceList();
                }
                dbManager.insertDeviceData(mSelectDeviceInfo.getDid(), mSelectDeviceInfo.getPwd(), getSp(Const.UID));
                if (aq_did != null) {
                    //需要绑定
                    //绑定从设备
                    //判断是否已经在绑定之列
                    boolean bindYes = hasBindAq();
                    if (!bindYes) {
                        userPresenter.cameraBind(aq_did, mSelectDeviceInfo.getDid(), "chiniao_wifi_camera", mSelectDeviceInfo.getDid(), mSelectDeviceInfo.getPwd());
                    } else {
                        if (mApp.addDeviceUI != null) {
                            mApp.addDeviceUI.finish();
                        }
                        if (mApp.mXiaoLiUi != null) {
                            mApp.mXiaoLiUi.mListView.smoothScrollToPosition(0);
                        }
                        if (mApp.mDeviceUi != null) {
                            mApp.mDeviceUi.mListView.smoothScrollToPosition(0);
                        }
                        finish();
                    }
                } else {
                    if (mApp.addDeviceUI != null) {
                        mApp.addDeviceUI.finish();
                    }
                    if (mApp.mXiaoLiUi != null) {
                        mApp.mXiaoLiUi.mListView.smoothScrollToPosition(0);
                    } else if (mApp.mDeviceUi != null) {
                        mApp.mDeviceUi.mListView.smoothScrollToPosition(0);
                    }
                    finish();
                }
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.adddevice_success) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.cameraBind_success) {
                finish();
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.cameraBind_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.getdeviceinfosuccess) {
                DeviceDetailModel deviceDetailModel = (DeviceDetailModel) entity.getData();
                startDeviceUI(deviceDetailModel);
            } else if (entity.getEventType() == UserPresenter.getdeviceinfofail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    private boolean hasBindAq() {
        boolean isAdd = false;
        if (mApp.mCameraDevice.arrayList != null) {
            for (int i = 0; i < mApp.mCameraDevice.arrayList.size(); i++) {
                if (mSelectDeviceInfo.getDid().equals(mApp.mCameraDevice.arrayList.get(i).getSlave_did())) {
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
