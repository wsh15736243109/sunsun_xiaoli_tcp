package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.lingshouadapter.NearStoreAdapter;
import sunsun.xiaoli.jiarebang.beans.StoreListBean;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentScrollView;

import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class ChooseStoreActivity extends LingShouBaseActivity implements Observer, IRecycleviewClick {
    private TranslucentActionBar actionBar;//可渐变的标题栏
    private TranslucentScrollView pullzoom_scrollview;//添加滑动监听的滑动组件
    RecyclerView recyclerview_near_store;
    ArrayList<String> list=new ArrayList<>();
    Button btn_sure_store;
    EditText ed_store;
    LingShouPresenter lingShouPresenter;
    private int pageIndex=1;
    private StoreListBean bean;
    private BitmapDescriptor descriptor;
    BaiduMap baiduMap;
    MapView mapView;
    private StoreListBean.ListEntity listEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_consultationandbuy;
    }

    @Override
    protected void initData() {
        //初始actionBar
        lingShouPresenter=new LingShouPresenter(this);
        baiduMap=mapView.getMap();
        initMapView();
        initTitlebarStyle1(this,actionBar,"咨询购买",0,"",0,"");
        initRecyclerView();
    }

    private void initMapView() {
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                //更改下方的信息
                Bundle model = arg0.getExtraInfo();
                listEntity = (StoreListBean.ListEntity) model.get("model");
                // 创建InfoWindow展示的view
                baiduMap.hideInfoWindow();
                Button button = new Button(getApplicationContext());
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
        ed_store.setText(listEntity.getName());
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview_near_store.setLayoutManager(linearLayoutManager);
        lingShouPresenter.getNearStore("330100",120.377819+ "", 120.377819 + "", "", "", pageIndex, 10);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure_store:
                String storeName=ed_store.getText().toString();
                if (TextUtils.isEmpty(storeName)) {
                    MAlert.alert("请先选择商家");
                    return;
                }

                Intent intent=new Intent();
                intent.putExtra("model",listEntity);
                setResult(202,intent);
                finish();
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity=handlerError(data);
        if (entity!=null) {
            if (entity.getCode()!=0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == LingShouPresenter.getNearStore_success) {
                bean = (StoreListBean) entity.getData();
                NearStoreAdapter adapter=new NearStoreAdapter(this,bean.getList());
                adapter.setOnItemListener(this);
                recyclerview_near_store.setAdapter(adapter);
                //将所有点设置到地图上去
                setMapPoint();
            } else if (entity.getEventType() == LingShouPresenter.getNearStore_fail) {
                MAlert.alert(entity.getData());
            }
        }

    }

    @Override
    public void onItemClick(int position) {
        listEntity=bean.getList().get(position);
        setSelectStore();
    }

    @Override
    public void onItemLongClick(int position) {

    }

    protected float zoom = 9;
    private void setMapPoint() {
        descriptor = BitmapDescriptorFactory
                .fromBitmap(BitmapFactory
                        .decodeResource(getResources(),
                                R.drawable.img_location));
        for (int i = 0; i < bean.getList().size(); i++) {
            LatLng l=new LatLng(bean.getList().get(i).getLat(),bean.getList().get(i).getLng());
            MapStatusUpdate u= MapStatusUpdateFactory.newLatLngZoom(l,zoom);
            baiduMap.animateMapStatus(u);
            Bundle bundle=new Bundle();
            bundle.putSerializable("model",bean.getList().get(i));
            MarkerOptions mMarkerOptions=new MarkerOptions().position(l).icon(descriptor).title(bean.getList().get(i).getName()).draggable(true).zIndex(18).extraInfo(bundle);
            baiduMap.addOverlay(mMarkerOptions);

        }
    }
}
