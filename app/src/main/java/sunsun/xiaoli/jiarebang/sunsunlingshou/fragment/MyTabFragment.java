package sunsun.xiaoli.jiarebang.sunsunlingshou.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

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
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.GoodsClassifyActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.GoodDetailActivity;
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
        lingShouPresenter = new LingShouPresenter(this);
        recycler_aqhardwareorhotgoods.setOnItemClickListener(this);
        switch (this.type) {
            case 0:
                near_store.setVisibility(View.VISIBLE);
                getNearStore();
                break;
            case 1:
                progress.setVisibility(View.GONE);
                near_store.setVisibility(View.GONE);
                recycler_aqhardwareorhotgoods.setVerticalSpacing(0);
                recycler_aqhardwareorhotgoods.setNumColumns(3);
                HomeDeivcesAdapter adapter = new HomeDeivcesAdapter(getActivity(), arDevice, R.layout.item_lingshou_device);
//            HomeDeviceAdapter adapter = new HomeDeviceAdapter(getActivity(), arDevice, R.layout.item_lingshou_device);
                recycler_aqhardwareorhotgoods.setAdapter(adapter);
                break;
            case 2:
                near_store.setVisibility(View.GONE);
                lingShouPresenter.getHotSearchGoods();
                break;
        }
    }

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
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (this.type) {
            case 0:
                Intent intent = new Intent(getActivity(), GoodsClassifyActivity.class);
                intent.putExtra("model", bean.getList().get(position));
                intent.putExtra("store_id", bean.getList().get(position).getId());
                startActivity(intent);
                break;
            case 1:
                break;
            case 2:
                startActivity(new Intent(getActivity(), GoodDetailActivity.class).putExtra("id", goodsList.getList().get(position).getId()).putExtra("store_id", goodsList.getList().get(position).getStore_id()));
                break;
        }
    }
}
