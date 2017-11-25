package sunsun.xiaoli.jiarebang.sunsunlingshou.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.bean.DeviceListBean;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.HomeDeivcesAdapter;
import sunsun.xiaoli.jiarebang.adapter.HomeHotGoodsAdapter;
import sunsun.xiaoli.jiarebang.adapter.HomeNearStoreAdapter;
import sunsun.xiaoli.jiarebang.beans.GoodsListBean;
import sunsun.xiaoli.jiarebang.beans.StoreListBean;
import sunsun.xiaoli.jiarebang.custom.MyGridView;
import sunsun.xiaoli.jiarebang.device.DeviceActivity;
import sunsun.xiaoli.jiarebang.device.jiarebang.DeviceJiaReBangDetailActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.AddDeviceNewActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.JinLiGangDetailActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.VideoActivity;
import sunsun.xiaoli.jiarebang.device.led.LEDDetailActivity;
import sunsun.xiaoli.jiarebang.device.phdevice.DevicePHDetailActivity;
import sunsun.xiaoli.jiarebang.device.pondteam.ActivityPondDeviceDetail;
import sunsun.xiaoli.jiarebang.device.qibeng.DeviceQiBengDetailActivity;
import sunsun.xiaoli.jiarebang.device.shuibeng.DeviceShuiBengDetailActivity;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.GoodsClassifyActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.GoodDetailActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.LingShouSwitchLoginOrRegisterActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.DeviceTypeModel;
import sunsun.xiaoli.jiarebang.utils.MapHelper;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static com.itboye.pondteam.utils.ScreenUtil.getDimension;

/**
 * Created by Mr.w on 2017/10/28.
 */

@SuppressLint("ValidFragment")
public class MyTabFragment extends LingShouBaseFragment implements Observer, AdapterView.OnItemClickListener {
    public int type = 0;
    LingShouPresenter lingShouPresenter;
    MyGridView recycler_aqhardwareorhotgoods;
    ArrayList<DeviceTypeModel> arDevice = new ArrayList<>();
    private int pageIndex = 1;
    private StoreListBean bean;
    private GoodsListBean goodsList;
    LinearLayout near_store;
    ProgressBar progress;
    BaiduMap baiduMap;
    MapView mapView;
    private StoreListBean.ListEntity listEntity1;
    private Dialog alert;
    private RadioButton tvTitle;
    private RadioButton tvMessage;
    private Intent intent;
    UserPresenter userPresenter;

    //设备列表ArrayList
    private ArrayList<DeviceListBean> arrayList;
    private DeviceListBean mSelectDeviceInfo;//选中项的设备bean

    private ProgressDialog loadingDialog;
    private DeviceDetailModel deviceDetailModel;

    @SuppressLint("ValidFragment")
    public MyTabFragment(int type) {
        this.type = type;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mytab;
    }

    @Override
    protected void initData() {
        baiduMap = mapView.getMap();
        loadingDialog = new ProgressDialog(getActivity());
        DeviceTypeModel deviceListBean = new DeviceTypeModel(R.drawable.home_aq_806, getString(R.string.device_zhineng806));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_jiarebang, getString(R.string.device_zhinengjiarebang));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_ph, getString(R.string.device_yuancheng_ph));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_shuibeng, getString(R.string.device_zhinengbianpinshuibeng));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_guolvtong, getString(R.string.device_chitangguolv));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_shenxiangtou, getString(R.string.device_zhinengshexiangtou));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_shuizudeng, getString(R.string.device_shuizudeng));
        arDevice.add(deviceListBean);
        deviceListBean = new DeviceTypeModel(R.drawable.home_aq_228, getString(R.string.device_zhineng228));
        arDevice.add(deviceListBean);
        //零售接口
        lingShouPresenter = new LingShouPresenter(this);

        //小鲤接口
        userPresenter = new UserPresenter(this);
        recycler_aqhardwareorhotgoods.setOnItemClickListener(this);
        switch (this.type) {
            case 0:
                near_store.setVisibility(View.VISIBLE);
                getNearStore();
                break;
            case 1:
                getDeviceList();
                break;
            case 2:
                near_store.setVisibility(View.GONE);
                lingShouPresenter.getHotSearchGoods();
                break;
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Const.STORE_CHANGE);
        intentFilter.addAction(Const.DEVICE_CHANGE);
        getActivity().registerReceiver(receiver, intentFilter);
    }

    private void getDeviceList() {
        if (!getSp(Const.UID).equals("")) {
            userPresenter.getMyDeviceList(getSp(Const.UID));
        } else {
            arrayList = new ArrayList<>();
            refreshDeviceList();
        }
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Const.STORE_CHANGE)) {
                lingShouPresenter.getHotSearchGoods();
            } else if (action.equals(Const.DEVICE_CHANGE)) {
                getDeviceList();
            }
        }
    };

    public void getNearStore() {
        if (lingShouPresenter == null) {
            lingShouPresenter = new LingShouPresenter(this);
        }
        lingShouPresenter.getNearStore(getSp(Const.CITY_CODE), getSp(Const.LNG) + "", getSp(Const.LAT) + "", "", "", pageIndex, 10);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_boda:
                listEntity1 = (StoreListBean.ListEntity) v.getTag();
                alert = new Dialog(getActivity(), R.style.callphonedialog);
                View view = View.inflate(getActivity(), R.layout.poup_home_callphone, null);
                view.setMinimumWidth(getActivity().getWindowManager().getDefaultDisplay().getWidth() - 100);
                tvTitle = (RadioButton) view.findViewById(R.id.tvTitle);
                tvTitle.setChecked(true);
                tvTitle.setText("手机:" + listEntity1.getPhone());
                tvMessage = (RadioButton) view.findViewById(R.id.tvMessage);
                tvMessage.setText("电话:" + listEntity1.getMobile());
                TextView tvBtnLeft = (TextView) view.findViewById(R.id.tvBtnLeft);
                tvBtnLeft.setOnClickListener(this);
                TextView tvBtnRight = (TextView) view.findViewById(R.id.tvBtnRight);
                tvBtnRight.setOnClickListener(this);
                alert.setContentView(view);
                alert.show();
                break;
            case R.id.tvBtnLeft:
                alert.dismiss();
                break;
            case R.id.tvBtnRight:
                try {
                    String number = "";
                    if (tvMessage.isChecked()) {
                        number = listEntity1.getMobile();
                    } else {
                        number = listEntity1.getPhone();
                    }
                    if (number.equals("")) {
                        MAlert.alert("当前电话不可用");
                        return;
                    }
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                    intent.setAction(Intent.ACTION_DIAL);
                    startActivity(intent);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                alert.dismiss();
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        progress.setVisibility(View.GONE);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == LingShouPresenter.getNearStore_success) {
                recycler_aqhardwareorhotgoods.setVerticalSpacing(getDimension(getActivity(), 20));
                recycler_aqhardwareorhotgoods.setNumColumns(1);
                recycler_aqhardwareorhotgoods.setVisibility(View.VISIBLE);
                bean = (StoreListBean) entity.getData();
                HomeNearStoreAdapter adapter = new HomeNearStoreAdapter(this, bean.getList(), R.layout.item_home_nearshangjia);
                recycler_aqhardwareorhotgoods.setAdapter(adapter);
                new MapHelper().setPoint(getActivity(), baiduMap, bean.getList());
            } else if (entity.getEventType() == LingShouPresenter.getNearStore_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.getHotSearchGoods_success) {

                recycler_aqhardwareorhotgoods.setVerticalSpacing(0);
                recycler_aqhardwareorhotgoods.setNumColumns(2);
                recycler_aqhardwareorhotgoods.setVisibility(View.VISIBLE);
                goodsList = (GoodsListBean) entity.getData();
                ArrayList<GoodsListBean.ListEntity> arTemp = new ArrayList<>();
                if (goodsList != null) {
                    if (goodsList.getList() != null) {
                        arTemp.addAll(goodsList.getList());
                    }
                }
                HomeHotGoodsAdapter adapter = new HomeHotGoodsAdapter(getActivity(), arTemp, R.layout.item_home_shangpin);
                recycler_aqhardwareorhotgoods.setAdapter(adapter);
            } else if (entity.getEventType() == LingShouPresenter.getHotSearchGoods_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.getMyDeviceList_success) {
                arrayList = (ArrayList<DeviceListBean>) entity.getData();
                refreshDeviceList();
            } else if (entity.getEventType() == UserPresenter.getMyDeviceList_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.getdeviceinfosuccess) {
                deviceDetailModel = (DeviceDetailModel) entity.getData();
                if (deviceDetailModel == null) {
                    //未获取到设备信息
                    loadingDialog.setMessage(getString(R.string.get_device_status_fail));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setDialoadDismiss(loadingDialog);
                        }
                    }, 2000);
                    return;
                } else {
                    loadingDialog.setMessage(getString(R.string.get_device_status_success));
                    if (!deviceDetailModel.getIs_disconnect().equals("0")) {
                        //设备不在线
                        loadingDialog.setMessage(getString(R.string.device) + getString(R.string.offline));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setDialoadDismiss(loadingDialog);
                            }
                        }, 2000);
                        return;
                    }
                    JSONObject jsonObject = null;
                    String psw = "sunsun123456";
                    try {
                        jsonObject = new JSONObject(mSelectDeviceInfo.getExtra());
                        if (jsonObject.has("pwd")) {
                            psw = jsonObject.getString("pwd");
                        }
                    } catch (JSONException e) {
                    }
                    //验证设备密码
                    userPresenter.authDevicePwd(mSelectDeviceInfo.getDid(), psw, mSelectDeviceInfo.getDevice_type());
                }
            } else if (entity.getEventType() == UserPresenter.getdeviceinfofail) {

            } else if (entity.getEventType() == UserPresenter.authDevicePwdsuccess) {
                //验证设备密码成功
                startDeviceUI(true);
            } else if (entity.getEventType() == UserPresenter.authDevicePwdfail) {
                //验证设备密码失败
                loadingDialog.setMessage(entity.getData() + "");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setDialoadDismiss(loadingDialog);
                    }
                }, 2000);
            }
        }
    }

    private void startDeviceUI(final boolean hasPsw) {
        loadingDialog.setMessage(getString(R.string.yanzheng_success));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setDialoadDismiss(loadingDialog);
                Intent intent = null;
                String deviceType = mSelectDeviceInfo.getDevice_type();
                if (deviceType.contains("S02")) {
                    //加热棒
                    intent = new Intent(getActivity(), DeviceJiaReBangDetailActivity.class);
                } else if (deviceType.contains("S01")) {
                    //过滤桶
                    intent = new Intent(getActivity(), ActivityPondDeviceDetail.class);
                } else if (deviceType.contains("S03")) {
                    //806
                    intent = new Intent(getActivity(), JinLiGangDetailActivity.class);
                } else if (deviceType.contains("S04")) {
                    //aph
                    intent = new Intent(getActivity(), DevicePHDetailActivity.class);
                } else if (deviceType.contains("S05")) {
                    //变频水泵
                    intent = new Intent(getActivity(), DeviceShuiBengDetailActivity.class);
                } else if (deviceType.contains("S06")) {
                    //Led水族灯
                    intent = new Intent(getActivity(), LEDDetailActivity.class);
                } else if (deviceType.contains("S07")) {
                    //CP1000
                    intent = new Intent(getActivity(), DeviceQiBengDetailActivity.class);
                } else if (deviceType.contains("S08")) {
                    //变频水泵
                    intent = new Intent(getActivity(), DeviceShuiBengDetailActivity.class);
                } else if (deviceType.contains("S09")) {
                    //变频水泵
                    intent = new Intent(getActivity(), DeviceShuiBengDetailActivity.class);
                } else if (deviceType.contains("chiniao_wifi_camera")) {
                    //摄像头
                    intent = new Intent(getActivity(), VideoActivity.class);
                } else {
                    MAlert.alert(getString(R.string.no_support_device));
                    return;
                }
                intent.putExtra("title", mSelectDeviceInfo.getDevice_nickname());
                intent.putExtra("did", mSelectDeviceInfo.getDid());
                intent.putExtra("id", mSelectDeviceInfo.getId());
                intent.putExtra("hasPsw", hasPsw);//无密码则应该重新进入插入密码
                intent.putExtra("detailModel", deviceDetailModel);
                startActivityForResult(intent, 101);
            }
        }, 2000);
    }

    private void setDialoadDismiss(ProgressDialog loadingDialog) {
        try {
            if (loadingDialog != null && loadingDialog.isShowing() && !getActivity().isFinishing() && this != null) {
                loadingDialog.dismiss();
            }
        } catch (Exception e) {

        }
    }

    private void refreshDeviceList() {
        progress.setVisibility(View.GONE);
        near_store.setVisibility(View.GONE);
        recycler_aqhardwareorhotgoods.setVerticalSpacing(0);
        recycler_aqhardwareorhotgoods.setNumColumns(3);


        HomeDeivcesAdapter adapter = new HomeDeivcesAdapter(getActivity(), arrayList, R.layout.item_lingshou_device);
//            HomeDeviceAdapter adapter = new HomeDeviceAdapter(getActivity(), arDevice, R.layout.item_lingshou_device);
        recycler_aqhardwareorhotgoods.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        switch (this.type) {
            case 0:
                intent = new Intent(getActivity(), GoodsClassifyActivity.class);
                intent.putExtra("model", bean.getList().get(position));
                intent.putExtra("store_id", bean.getList().get(position).getId());
                startActivity(intent);
                break;
            case 1:
                if (arrayList != null) {
                    if (arrayList.size() > 0) {
                        if (position == arrayList.size() - 1 && arrayList.size() < 9) {
                            //添加更多设备
                            if (getSp(Const.UID).equals("")) {
                                intent = new Intent(getActivity(), LingShouSwitchLoginOrRegisterActivity.class);
                            } else {
                                intent = new Intent(getActivity(), AddDeviceNewActivity.class);
                            }
                            startActivity(intent);
                        } else if (arrayList.size() >= 9) {
                            //查看更多设备
                            intent = new Intent(getActivity(), DeviceActivity.class);
                            startActivity(intent);
                        } else {
                            mSelectDeviceInfo = arrayList.get(position);
                            String selectDid = mSelectDeviceInfo.getDid();
                            if (!selectDid.toLowerCase().startsWith("SCHD".toLowerCase())) {
                                loadingDialog.setMessage(getString(R.string.get_deviceInfoing));
                                loadingDialog.setCanceledOnTouchOutside(false);
                                loadingDialog.show();
                                userPresenter.getDeviceDetailInfo(selectDid, getSp((Const.UID)));//先获取设备详情信息
                            } else {
                                //摄像头
                                String psw = "";
                                try {
                                    JSONObject jsonObject = new JSONObject(mSelectDeviceInfo.getExtra());
                                    psw = jsonObject.getString("pwd");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                intent = new Intent(getActivity(), VideoActivity.class);
                                intent.putExtra("cameraDid", mSelectDeviceInfo.getDid());
                                intent.putExtra("cameraPsw", psw);
                                intent.putExtra("isMasterDevice", true);
                                intent.putExtra("model", mSelectDeviceInfo);
                                startActivity(intent);
                            }
                        }
                    } else {
                        if (getSp(Const.UID).equals("")) {
                            intent = new Intent(getActivity(), LingShouSwitchLoginOrRegisterActivity.class);
                        } else {
                            intent = new Intent(getActivity(), AddDeviceNewActivity.class);
                        }
                        startActivity(intent);
                    }
                } else {
                    MAlert.alert("未获取到设备列表信息");
                }
                break;
            case 2:
                intent = new Intent(getActivity(), GoodDetailActivity.class);
                intent.putExtra("id", goodsList.getList().get(position).getId());
                intent.putExtra("store_id", goodsList.getList().get(position).getStore_id());
                startActivity(intent);
                break;
        }
    }
}
