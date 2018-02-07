package sunsun.xiaoli.jiarebang.shuizuzhijia.store;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.bean.NavigationBean;
import com.itboye.pondteam.db.DBManager;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.utils.LocationUtil;
import sunsun.xiaoli.jiarebang.utils.Util;

/**
 * 门店
 *
 * @author liu
 */
public class StoreFragment extends LingShouBaseFragment implements OnClickListener, Observer, LocationUtil.OnLocationResult {

    int searchType = 0; // 搜索的类型，在显示时区分

    TextView tvLocation;
    RelativeLayout realyoutcity;
    TextView tvNavigation;
    // 定位相关声明
    boolean isFirstLoc = true;// 是否首次定位
    TextView addr, anquanyanzheng;
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

    MapView bmapsView = null;
    protected float zoom = 10;

    //    protected NavigationBean navi;
    private LocationClient mLocClient;
    public static String addrDetail_navigation;

    UserPresenter userPresenter;
    LocationUtil locationUtil;

    int page = 1, size = 10;
    String city;
    long longValue, lati;
    private NavigationBean navigationBean;
    private NavigationBean.NavigationDetail navi;

    ImageView img_back;
    TextView txt_exist, txt_title;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_store;
    }

    @Override
    protected void initData() {
        userPresenter = new UserPresenter(this);
        descriptor = BitmapDescriptorFactory
                .fromBitmap(BitmapFactory
                        .decodeResource(getActivity().getResources(),
                                R.drawable.map_descriptor2));
        baiduMap = bmapsView.getMap();
        locationUtil = new LocationUtil(getActivity(), this);
        initTop();
        initMapViewEvent();
    }

    private void initTop() {
        txt_title.setText(getString(R.string.store_query));
        img_back.setVisibility(View.GONE);
        txt_exist.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.jiantou_down, 0);
    }

    private void initMapViewEvent() {
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                //更改下方的信息
                Bundle model = arg0.getExtraInfo();
                navi = (NavigationBean.NavigationDetail) model.get("model");
                // 创建InfoWindow展示的view
                baiduMap.hideInfoWindow();
                Button button = new Button(getActivity()
                        .getApplicationContext());
                button.setPadding(5, 5, 5, 5);
                button.setBackgroundColor(Color.WHITE);
                button.setText(navi.getName());
                button.setTextColor(Color.parseColor("#000000"));
                // 定义用于显示该InfoWindow的坐标点 .
                LatLng pt = new LatLng(arg0.getPosition().latitude, arg0
                        .getPosition().longitude);
                // 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                InfoWindow mInfoWindow = new InfoWindow(button, pt, -47);

                // 显示InfoWindow
                baiduMap.showInfoWindow(mInfoWindow);
                setStoreDetail(navi);
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
        bmapsView.showZoomControls(false);// 隐藏缩放按钮
        // 隐藏logo
        View child = bmapsView.getChildAt(1);
        if (child != null
                && (child instanceof ImageView || child instanceof ZoomControls)) { // 隐藏百度地图LOGO
            child.setVisibility(View.INVISIBLE);
        }
        baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                baiduMap.hideInfoWindow();
            }

            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub

            }
        });
        baiduMap.setOnMapStatusChangeListener(onMapStatusChangeListener);
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
            } else {
                if (entity.getEventType() == UserPresenter.branchSearch_success) {
                    navigationBean = (NavigationBean) entity.getData();
                    if (navigationBean != null) {
                        if (navigationBean.getList().size() > 0) {
                            setStoreDetail(navigationBean.getList().get(0));
                            setMapData();
                        }
                    } else {
                        MAlert.alert("出错了");
                    }
                } else if (entity.getEventType() == UserPresenter.branchSearch_fail) {
                    MAlert.alert(entity.getData());
                }
            }
        }
    }

    private void setMapData() {
        ArrayList<NavigationBean.NavigationDetail> navigationDetailArrayList = navigationBean.getList();
        Bundle bundle;
        // 将View转换为BitmapDescriptor
        LatLng ll = new LatLng(navigationDetailArrayList.get(0).getLat(),
                navigationDetailArrayList.get(0).getLng());
        MapStatusUpdate u = MapStatusUpdateFactory
                .newLatLngZoom(ll, zoom); // 设置地图中心点以及缩放级别
        baiduMap.animateMapStatus(u);
        for (int i = 0; i < navigationDetailArrayList.size(); i++) {
            bundle = new Bundle();
            bundle.putSerializable("model", navigationDetailArrayList.get(i));
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(
                            new LatLng(navigationDetailArrayList.get(i)
                                    .getLat(), navigationDetailArrayList
                                    .get(i).getLng()))
                    .icon(descriptor)
                    .title(navigationDetailArrayList.get(i).getName())
                    .zIndex(5).draggable(true).extraInfo(bundle);
            baiduMap.addOverlay(markerOptions);
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
    public void getLatAndLng(final String cityName, double lat, double lng, final String areaName) {
        if (!isSearch) {
            isSearch = true;
            city = Util.queryCityNo(cityName);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txt_exist.setText(cityName);
                }
            });
//            area = Util.queryDistrictNo(areaName);
            userPresenter.branchSearch(city, area, lng, lat, page, size);
        }
    }

    OnMapStatusChangeListener onMapStatusChangeListener = new OnMapStatusChangeListener() {

        @Override
        public void onMapStatusChangeStart(MapStatus arg0) {
            // TODO Auto-generated method stub
//            System.out.println(arg0.zoom+"缩放onMapStatusChangeStart");
        }

        @Override
        public void onMapStatusChangeFinish(MapStatus arg0) {
            // TODO Auto-generated method stub
            System.out.println(arg0.zoom + "缩放onMapStatusChangeFinish");
//            if (arg0.zoom<=14) {
//                getStoreList(cityCode, 1);
//            }
//            else{
//                getStoreList(cityCode, 0);
//            }
        }

        @Override
        public void onMapStatusChange(MapStatus arg0) {
            // TODO Auto-generated method stub
//            System.out.println(arg0.zoom+"缩放onMapStatusChange");
        }
    };

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
//            case R.id.tvLocation:
//                // Intent intent = new Intent(getActivity(),
//                // CityListActivity.class);
//                // Intent intent = new Intent(getActivity(),
//                // LetterSortActivity.class);
//                latLongString = (String) SPUtils.get(App.ctx, null,
//                        SPContants.PROVINCE, "");
//                System.out.println("resultDatasresultDatas" + latLongString);
//                if (latLongString == null) {
//                    Intent intent = new Intent(getActivity(),
//                            CityListActivity.class);
//                    intent.putExtra("resultDatas", dingei);
//                    System.out.println("resultDatasresultDatas" + dingei);
//
//                    startActivityForResult(intent, 101);
//                } else {
//                    Intent intent = new Intent(getActivity(),
//                            CityListActivity.class);
//                    intent.putExtra("resultDatas", latLongString);
//                    startActivityForResult(intent, 101);
//                }
//
//                break;
//            case R.id.realyoutcity:
//                // Intent intent = new Intent(getActivity(),
//                // CityListActivity.class);
//                // Intent intent = new Intent(getActivity(),
//                // LetterSortActivity.class);
//                latLongString = (String) SPUtils.get(App.ctx, null,
//                        SPContants.PROVINCE, "");
//                if (latLongString == null) {
//                    Intent intent = new Intent(getActivity(),
//                            CityListActivity.class);
//                    intent.putExtra("resultDatas", dingei);
//                    startActivityForResult(intent, 101);
//                } else {
//                    Intent intent = new Intent(getActivity(),
//                            CityListActivity.class);
//                    intent.putExtra("resultDatas", latLongString);
//                    startActivityForResult(intent, 101);
//                }
//                ;
//                System.out.println("resultDatasresultDatas" + latLongString);
//
//                break;
//            case R.id.reback:
//                latLongString = (String) SPUtils.get(App.ctx, null,
//                        SPContants.PROVINCE, "");
//                if (latLongString == null) {
//                    Intent intent = new Intent(getActivity(),
//                            CityListActivity.class);
//                    intent.putExtra("resultDatas", dingei);
//                    startActivityForResult(intent, 101);
//                } else {
//                    Intent intent = new Intent(getActivity(),
//                            CityListActivity.class);
//                    intent.putExtra("resultDatas", latLongString);
//                    startActivityForResult(intent, 101);
//                }
//                break;
            case R.id.tvNavigation:
                //跳转导航列表
                Intent intent1 = new Intent(getActivity(),
                        Navigationactivity.class);
                intent1.putExtra("areaCode", areaCode);
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
            default:
                break;
        }
    }

    /**
     * 打开数据库
     *
     * @param dbFile
     * @return SQLiteDatabase
     * @author sy
     */
    private final int BUFFER_SIZE = 400000;
    protected String storeId;
    protected String[] branches;

    public static double lng_my;
    public static double lat_my;
    public static double lng;
    public static double lat;
    protected String phone;
    protected String contactName;
    protected String addrDetail;
    private TextView tvContacts;
    private TextView tvPhones;
    private TextView tvMobilePhones;
    protected String mobile;

    // protected float radius;
    // private boolean searchType = false;
    BitmapDescriptor descriptor = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 104) {
            latLongString = data.getStringExtra("province");
            cityCode = data.getStringExtra("number");

            System.out.println("latLongStringlatLongString" + latLongString);

//            SPUtils.put(App.ctx, null, SPContants.PROVINCE, latLongString);
//            getStoreList(cityCode, all);

            if (latLongString == null) {
                tvLocation.setText("定位中...");
            } else
                tvLocation.setText(latLongString + "市");
            searchType = 1;
            // mPoiSearch.searchInCity((new PoiCitySearchOption())
            // .city(latLongString).keyword("森森").pageNum(loadIndex));
        }
        if (requestCode == 101 && resultCode == 106) {
            areacode = data.getStringExtra("numbers");
            area = data.getStringExtra("provinces");
            if (area == null) {
                tvLocation.setText("定位中...");
            } else {
                tvLocation.setText(area);
//                getArea(areacode);
                // mPoiSearch.searchInCity((new PoiCitySearchOption())
                // .city(area).keyword("森森").pageNum(loadIndex));
            }
            System.out.println("latLongStringlatLongStringareacode" + areacode);

//            getStoreList(cityCode, all);
        }
//        getStoreList(cityCode, all);
    }

    private String getAreayCode(String city) {
        // TODO Auto-generated method stub
        if (database != null)
            cursor = database.query("hat_city", new String[]{"cityID"},
                    "city=?", new String[]{city}, null, null, null);
        if (cursor != null)
            while (cursor.moveToNext()) {
                cityCode = cursor.getString(0);
            }
        Log.v("cityId", cityCode);
        System.out.println("cityIdcityId" + cityCode);
//        getStoreList(cityCode, all);
        return cityCode;
    }

//    private void getTopBannerData(NavigationBean bean) {
//        branches = bean.getBranchImgs().toString().split(",");
//        storeLunBo.removeAllViews();
//        List<ImageView> bmps = new ArrayList<ImageView>();
//        for (String pictureBean : branches) {
//            ImageView imageView = new ImageView(App.ctx);
//
//            imageView.setScaleType(ScaleType.CENTER_CROP);
//            XImageLoader.load(NetPublicConstant.IMAGE_URL + pictureBean,
//                    imageView);
//            bmps.add(imageView);
//            imageView.setTag(pictureBean);
//
//        }
//        storeLunBo.setImageBitmaps(bmps);
//    }

    BaiduMap baiduMap;

    private String areaCode;

    private String getCityCode(String city) {
        // TODO Auto-generated method stub
        if (database != null)
            cursor = database.query("hat_city", new String[]{"cityID"},
                    "city=?", new String[]{city}, null, null, null);
        if (cursor != null)
            while (cursor.moveToNext()) {
                cityCode = cursor.getString(0);
            }
        Log.v("cityId", cityCode);
        System.out.println("cityIdcityId" + cityCode);
        if (city.equals("北京市")) {
            cityCode = "110100";
        } else if (city.equals("上海市")) {
            cityCode = "310000";

        } else if (city.equals("重庆市")) {
            cityCode = "500000";

        } else if (city.equals("天津市")) {
            cityCode = "120000";

        }
//        getStoreList(cityCode, all);

        return cityCode;
    }

    private String getPrivinceCityCodeBy(String city) {
        // TODO Auto-generated method stub
        if (database != null)
            cursor = database.query("hat_province",
                    new String[]{"provinceID"}, "province=?",
                    new String[]{city}, null, null, null);
        if (cursor != null)
            while (cursor.moveToNext()) {
                cityCode = cursor.getString(0);
            }
        Log.v("cityId", cityCode);
//        getStoreList(cityCode, all);
        return cityCode;
    }

    protected String getDistrctCode(String district) {
        // TODO Auto-generated method stub
        if (database != null)
            cursor = database.query("hat_area", new String[]{"areaID"},
                    "area=?", new String[]{district}, null, null, null);
        if (cursor != null)
            while (cursor.moveToNext()) {
                areaCode = cursor.getString(0);
            }
        Log.v("areaCode", areaCode);
        return areaCode;
    }

}
