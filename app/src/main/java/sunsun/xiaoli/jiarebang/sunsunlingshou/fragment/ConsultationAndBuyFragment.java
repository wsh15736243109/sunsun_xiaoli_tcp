package sunsun.xiaoli.jiarebang.sunsunlingshou.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.lingshouadapter.NearStoreAdapter;
import sunsun.xiaoli.jiarebang.beans.AddressBean;
import sunsun.xiaoli.jiarebang.beans.FreightPriceBean;
import sunsun.xiaoli.jiarebang.beans.StoreListBean;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.ChooseTimeActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.web.WebActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentScrollView;
import sunsun.xiaoli.jiarebang.utils.MapHelper;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;


/**
 * 首页
 */
public class ConsultationAndBuyFragment extends LingShouBaseFragment implements TranslucentScrollView.TranslucentChangedListener, Observer, IRecycleviewClick {

    private TranslucentActionBar actionBar;//可渐变的标题栏
    private TranslucentScrollView pullzoom_scrollview;//添加滑动监听的滑动组件
    RecyclerView recyclerview_near_store;
    Button btn_sure_store;
    EditText ed_store;
    LingShouPresenter lingShouPresenter;
    private StoreListBean bean;
    private int pageIndex = 1;
    MapView mapView;
    BaiduMap baiduMap;
    private StoreListBean.ListEntity listEntity;
    private String selectAddress;
    private AddressBean selectAddressBean;
    private String addressId;
    private String storeId;
    TextView txt_freight,txt_service_fanwei;
    private ProgressDialog loadingDialog;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_consultationandbuy;
    }

    @Override
    protected void initData() {
        //初始actionBar
        loadingDialog = new ProgressDialog(getActivity());
        lingShouPresenter = new LingShouPresenter(this);
        initTitlebarStyle1(getActivity(), actionBar, "咨询购买", 0, "", 0, "");
        queryStore();
        baiduMap = mapView.getMap();
        initMapView();
        initRecyclerView();

//        caculateFreight();
        IntentFilter intentFilter = new IntentFilter(Const.STORE_CHANGE);

        getActivity().registerReceiver(receiver, intentFilter);
    }

    private void queryStore() {
        lingShouPresenter.getNearStore(getSp(Const.CITY_CODE), getSp(Const.LNG) + "", getSp(Const.LAT) + "", "", "", pageIndex, 10);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    private void caculateFreight() {
        selectAddress = getSp(Const.SELECT_ADDRESS);
        if (!selectAddress.equals("")) {
            //用户选择了地址
            selectAddressBean = new Gson().fromJson(selectAddress, AddressBean.class);
            if (selectAddressBean != null) {
                addressId = selectAddressBean.getId();
                if (!Const.S_ID.equals("")) {
                    lingShouPresenter.queryFreightPrice(storeId, addressId, getSp(Const.S_ID));
                }
            }
        } else {
            //没有选择地址获取用户默认地址
            lingShouPresenter.getDefaultAddress(getSp(Const.UID), getSp(Const.S_ID));
        }
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            queryStore();
        }
    };


    private void initMapView() {
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                //更改下方的信息
                Bundle model = arg0.getExtraInfo();
                listEntity = (StoreListBean.ListEntity) model.get("model");
                // 创建InfoWindow展示的view
                baiduMap.hideInfoWindow();
                Button button = new Button(getActivity()
                        .getApplicationContext());
                button.setBackgroundColor(Color.WHITE);
                button.setText(listEntity.getName());
                button.setTextColor(Color.parseColor("#000000"));
                // 定义用于显示该InfoWindow的坐标点 .
                LatLng pt = new LatLng(arg0.getPosition().latitude, arg0
                        .getPosition().longitude);
                // 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                InfoWindow mInfoWindow = new InfoWindow(button, pt, -47);

                // 显示InfoWindow
                baiduMap.showInfoWindow(mInfoWindow);
                setSelectStore();
                return true;
            }
        });
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                // TODO Auto-generated method stub
                baiduMap.hideInfoWindow();
            }
        });
        mapView.showZoomControls(false);// 隐藏缩放按钮
        // 隐藏logo
        View child = mapView.getChildAt(1);
        if (child != null
                && (child instanceof ImageView || child instanceof ZoomControls)) { // 隐藏百度地图LOGO
            child.setVisibility(View.INVISIBLE);
        }
    }

    private void setSelectStore() {
        storeId = listEntity.getId();
        ed_store.setText(listEntity.getName());
        txt_freight.setText(Html.fromHtml("配送费：<font color='red'>￥" + listEntity.getFreight_price() / 100 + "</font>"));
//        caculateFreight();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview_near_store.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure_store:
                String storeName = ed_store.getText().toString();
                if (TextUtils.isEmpty(storeName)) {
                    MAlert.alert("请先选择商家");
                    return;
                }
                Intent intent = new Intent(getActivity(), ChooseTimeActivity.class);
                intent.putExtra("model", listEntity);
                intent.putExtra("canPack", 1);
                startActivity(intent);
                break;
            case R.id.txt_service_fanwei:
                startActivity(new Intent(getActivity(), WebActivity.class).putExtra("url", Const.SERVICE_FANWEI)
                        .putExtra("title", "服务范围"));
                break;
        }
    }

    @Override
    public void onTranslucentChanged(int transAlpha) {
        actionBar.tvTitle.setVisibility(transAlpha > 48 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == LingShouPresenter.getNearStore_success) {
                bean = (StoreListBean) entity.getData();
                NearStoreAdapter adapter = new NearStoreAdapter(getActivity(), bean.getList());
                adapter.setOnItemListener(this);
                recyclerview_near_store.setAdapter(adapter);
                //将所有点设置到地图上去
                setMapPoint();
                //设置一个默认的商家
                setDefaultStore();
            } else if (entity.getEventType() == LingShouPresenter.getNearStore_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.queryFreightPrice_success) {
                FreightPriceBean freightPriceBean = (FreightPriceBean) entity.getData();
                txt_freight.setText(Html.fromHtml("配送费：<font color='red'>￥" + freightPriceBean.getFreight_price() / 100 + "</font>"));
            } else if (entity.getEventType() == LingShouPresenter.queryFreightPrice_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.getDefaultAddress_success) {
                ArrayList<AddressBean> addressBeanArrayList = (ArrayList<AddressBean>) entity.getData();
                if (addressBeanArrayList.size() > 0) {
                    addressId = addressBeanArrayList.get(0).getId();
                    lingShouPresenter.queryFreightPrice(storeId, addressId, Const.S_ID);
                } else {
                    MAlert.alert("没有设置默认地址");
                }
            } else if (entity.getEventType() == LingShouPresenter.getDefaultAddress_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    private void setDefaultStore() {
        if (bean.getList() != null) {
            if (bean.getList().size() > 0) {
                listEntity = bean.getList().get(0);
                setSelectStore();
            }
        }
    }


    private void setMapPoint() {
        new MapHelper().setPoint(getActivity(), baiduMap, bean.getList());
    }

    @Override
    public void onItemClick(int position) {
        listEntity = bean.getList().get(position);
        setSelectStore();
    }

    @Override
    public void onItemLongClick(int position) {

    }
}
