package sunsun.xiaoli.jiarebang.shuizuzhijia.store;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.bean.NavigationBean;
import com.itboye.pondteam.db.DBManager;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.custom.My2dMapView;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.AddressFragment;
import sunsun.xiaoli.jiarebang.utils.LocationUtil;
import sunsun.xiaoli.jiarebang.utils.Util;

import static sunsun.xiaoli.jiarebang.utils.GPSUtil.bd09_To_Gcj02;

/**
 * 门店
 *
 * @author liu
 */
public class StoreFragment extends LingShouBaseFragment implements OnClickListener, Observer, LocationUtil.OnLocationResult, AddressFragment.GetInforListener {

    int searchType = 0; // 搜索的类型，在显示时区分

    TextView tvLocation;
    RelativeLayout realyoutcity;
    TextView tvNavigation;
    // 定位相关声明
    boolean isFirstLoc = true;// 是否首次定位
    TextView addr, anquanyanzheng, tvMobilePhones, tvPhones, tvContacts;
    protected String cityCode;
    // protected String city;
    SQLiteDatabase database;

    private Cursor cursor;
    private DBManager dbManager;
    ImageView reback;
    ScrollView xScrollView;

    private RelativeLayout relyoutDaoHang;
    RelativeLayout lineMap;

    // UI相关

    String latLongString, areacode, area;

    My2dMapView bmapsView = null;

    UserPresenter userPresenter;
    LocationUtil locationUtil;

    int page = 1, size = 100;
    private NavigationBean navigationBean;
    private NavigationBean.NavigationDetail navi;

    ImageView img_back;
    TextView txt_exist, txt_title;
    private String cityNo;
    private String cityName;
    private String provinceName;
    Bundle savedInstanceState;
    private ArrayList<Marker> markerArrayList = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_store;
    }

    @Override
    protected void initData() {
        bmapsView.onCreate(savedInstanceState);
        userPresenter = new UserPresenter(this);
        descriptor = com.amap.api.maps2d.model.BitmapDescriptorFactory
                .fromBitmap(BitmapFactory
                        .decodeResource(getActivity().getResources(),
                                R.drawable.map_descriptor2));
        baiduMap = bmapsView.getMap();
        locationUtil = new LocationUtil(getActivity(), this);
        initTop();
        initMapViewEvent();
    }

    private void initTop() {
        txt_exist.setText(getString(R.string.position_ing));
        txt_title.setText(getString(R.string.store_query));
        txt_title.setTextColor(getResources().getColor(R.color.main_green));
        img_back.setVisibility(View.GONE);
        txt_exist.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.jiantou_down, 0);
    }

    private void initMapViewEvent() {
        baiduMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                System.out.println(cameraPosition.zoom + "缩放onMapStatusChangeFinish");

                if (cameraPosition.zoom <= 8 && isAll == false) {
                    showProgressDialog("正在查询全国门店", true);
                    all = 1;
//                zoom = arg0.zoom;
                    isAll = true;
                    userPresenter.branchSearchAll(all);
                } else if (cameraPosition.zoom > 8 && isAll) {
                    showProgressDialog("正在查询" + cityName + "下的门店", true);
                    isAll = false;
                    all = 0;
                    userPresenter.branchSearch(all, cityNo, area, StoreFragment.lng, StoreFragment.lat, page, size);
                }
            }
        });
        baiduMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                NavigationBean.NavigationDetail navigationDetail = (NavigationBean.NavigationDetail) marker.getObject();
                setStoreDetail(navigationDetail);
                return false;
            }
        });
//        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//
//            @Override
//            public boolean onMarkerClick(Marker arg0) {
//                //更改下方的信息
//                Bundle model = arg0.getExtraInfo();
//                navi = (NavigationBean.NavigationDetail) model.get("model");
//                // 创建InfoWindow展示的view
//                baiduMap.hideInfoWindow();
//                Button button = new Button(getActivity()
//                        .getApplicationContext());
//                button.setPadding(5, 5, 5, 5);
//                button.setBackgroundColor(Color.WHITE);
//                button.setText(navi.getName());
//                button.setTextColor(Color.parseColor("#000000"));
//                // 定义用于显示该InfoWindow的坐标点 .
//                LatLng pt = new LatLng(arg0.getPosition().latitude, arg0
//                        .getPosition().longitude);
//                // 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
//                InfoWindow mInfoWindow = new InfoWindow(button, pt, -47);
//
//                // 显示InfoWindow
//                baiduMap.showInfoWindow(mInfoWindow);
//                setStoreDetail(navi);
//                return true;
//            }
//        });
//        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
//
//            @Override
//            public boolean onMapPoiClick(MapPoi arg0) {
//                // TODO Auto-generated method stub
//                return false;
//            }
//
//            @Override
//            public void onMapClick(LatLng arg0) {
//                // TODO Auto-generated method stub
//                baiduMap.hideInfoWindow();
//            }
//        });
//        bmapsView.showZoomControls(false);// 隐藏缩放按钮
//        // 隐藏logo/算了，隐藏不掉
//        UiSettings uiSettings=baiduMap.getUiSettings();
//        uiSettings.setLogoPosition(-7);
//        baiduMap.
//        ViewGroup child = (ViewGroup) bmapsView.getChildAt(0);//地图框架
//        child.getChildAt(3).setVisibility(View.GONE);//logo
//        UiSettings uiSettings = baiduMap.getUiSettings();
//        uiSettings.setLogoPosition(-50);
//        View child = bmapsView.getChildAt(1);
//        if (child != null
//                && (child instanceof ImageView || child instanceof ZoomControls)) { // 隐藏百度地图LOGO
//            child.setVisibility(View.INVISIBLE);
//        }
//        baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
//
//            @Override
//            public void onMarkerDragStart(Marker arg0) {
//                // TODO Auto-generated method stub
//                baiduMap.hideInfoWindow();
//            }
//
//            @Override
//            public void onMarkerDragEnd(Marker arg0) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onMarkerDrag(Marker arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//        baiduMap.setOnMapStatusChangeListener(onMapStatusChangeListener);
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        try {
            closeProgressDialog();
        } catch (Exception e) {

        }
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
            } else {
                if (entity.getEventType() == UserPresenter.branchSearch_success) {
                    navigationBean = (NavigationBean) entity.getData();
                    if (navigationBean != null) {
                        if (navigationBean.getList().size() > 0) {
                            setStoreDetail(navigationBean.getList().get(0));
                            setMapData(navigationBean.getList());
                        }
                    } else {
                        MAlert.alert("出错了");
                    }
                } else if (entity.getEventType() == UserPresenter.branchSearch_fail) {
                    MAlert.alert(entity.getData());
                } else if (entity.getEventType() == UserPresenter.branchSearchAll_success) {
                    ArrayList<NavigationBean.NavigationDetail> arrayList = (ArrayList<NavigationBean.NavigationDetail>) entity.getData();
                    setStoreDetail(arrayList.get(0));
                    setMapData(arrayList);
                } else if (entity.getEventType() == UserPresenter.branchSearchAll_fail) {
                    MAlert.alert(entity.getData());
                }
            }
        }
    }

    private void setMapData(List<NavigationBean.NavigationDetail> navigationDetailArrayList) {
        double[] latLng = bd09_To_Gcj02(navigationDetailArrayList.get(0).getLat(),
                navigationDetailArrayList.get(0).getLng());
        LatLng ll = new LatLng(latLng[0], latLng[1]);
        baiduMap.moveCamera(CameraUpdateFactory.changeLatLng(ll));
        for (Marker marker : markerArrayList) {
            marker.remove();
        }
        baiduMap.invalidate();//刷新地图
//        MAlert.alert("当前一共找到" + navigationDetailArrayList.size() + "条数据");
        markerArrayList = new ArrayList<>();
        for (int i = 0; i < navigationDetailArrayList.size(); i++) {
            NavigationBean.NavigationDetail navigationDetail = navigationDetailArrayList.get(i);
            MarkerOptions markerOption = new MarkerOptions();
            latLng = bd09_To_Gcj02(navigationDetail.getLat(), navigationDetail.getLng());
            com.amap.api.maps2d.model.LatLng latLngs = new com.amap.api.maps2d.model.LatLng(latLng[0], latLng[1]);
            markerOption.position(latLngs);
            markerOption.title(navigationDetail.getAddress());
//                    .snippet(navigationDetail.getAddressDetail() + ": " + navigationDetail.getLat() + ", " + navigationDetail.getLng());
//"西安市：34.341568, 108.940174"
            markerOption.draggable(true);//设置Marker可拖动
            markerOption.icon(descriptor);
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
//            markerOption.setFlat(true);//设置marker平贴地图效果
            Marker marker = baiduMap.addMarker(markerOption);
            marker.setObject(navigationDetail);
            markerArrayList.add(marker);
        }
    }

    private void setStoreDetail(NavigationBean.NavigationDetail navigationDetail) {
        navi = navigationDetail;
        addr.setText(navigationDetail.getAddressDetail());
        tvContacts.setText(navigationDetail.getPersonName());
        tvPhones.setText(navigationDetail.getTelephone());
        tvMobilePhones.setText(navigationDetail.getMobile());
    }

    boolean isSearch = false;

    @Override
    public void getLatAndLng(final String provinceName, final String cityName, double lat, double lng, final String areaName) {
        if (!isSearch) {
            isSearch = true;
            cityNo = Util.queryCityNo(cityName);
            this.cityName = cityName;
            this.provinceName = provinceName;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txt_exist.setText(cityName);
                }
            });
//            area = Util.queryDistrictNo(areaName);
            all = 0;
            userPresenter.branchSearch(all, cityNo, area, lng, lat, page, size);
        }
    }

    private int all = 0;
    boolean isAll = false;


    @Override
    public void onDestroy() {
        if (bmapsView != null) {
            bmapsView.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        if (bmapsView != null) {
            bmapsView.onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        if (bmapsView != null) {
            bmapsView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tvNavigation:
                //跳转导航列表
                Intent intent1 = new Intent(getActivity(),
                        Navigationactivity.class);
//                intent1.putExtra("areaCode", areaCode);
                intent1.putExtra("cityCode", cityCode);
                intent1.putExtra("searchType", searchType);
                intent1.putExtra("navi", navi);
                startActivity(intent1);
                break;
            case R.id.tvContacts:

                Intent intent3 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                        + contactName));
                startActivity(intent3);
                break;
            case R.id.tvPhones:
                Intent intent4 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                        + phone));
                startActivity(intent4);
                break;
            case R.id.tvMobilePhones:
                Intent intent5 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                        + mobile));
                startActivity(intent5);
                break;
            case R.id.txt_exist:
                AddressFragment addressFragment = new AddressFragment(this);
                addressFragment.setAreaVisible(false);
                if (provinceName != null) {
                    addressFragment.setProvince(this.provinceName);
                    addressFragment.setCity(this.cityName);
                }
                addressFragment.show(getFragmentManager(), "addressfragment");
                break;

            default:
                break;
        }
    }

    public static double lng;
    public static double lat;
    protected String phone;
    protected String contactName;
    protected String mobile;
    com.amap.api.maps2d.model.BitmapDescriptor descriptor = null;


    AMap baiduMap;

    @Override
    public void onGetinforListener(String province, String city, String district, String provinceNo, String cityNo, String districtNo) {
        this.cityNo = cityNo;
        this.cityName = city;
        this.provinceName = province;
        txt_exist.setText(city);
        all = 0;
        userPresenter.branchSearch(all, cityNo, null, -1, -1, page, size);
    }
}
