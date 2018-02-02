package sunsun.xiaoli.jiarebang.shuizuzhijia.store;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.db.DBManager;
import com.itboye.pondteam.presenter.UserPresenter;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.utils.LocationUtil;

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
    Double a, b;

    private double latitude = 0;
    private double longitude = 0;
    private LocationManager locationManager = null;

    MapView mMapView = null;
    //     private MyOverlay mOverlay = null;
//     private PopupOverlay pop = null;
    Button button1;

    String dingei;
    int all = 0;
    // 设置启用内置的缩放控件
    // MapController mMapController;
    float zom = 15f;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            double[] data = (double[]) msg.obj;
            // LocTxtView.setText("经度：" + data[0] + "\t纬度:" + data[1]);
//            SPUtils.put(App.ctx, null, SPContants.LATData, data[0]);
//            SPUtils.put(App.ctx, null, SPContants.LNGdata, data[1]);
//            latitude = data[0];
//            longitude = data[1];
//
//            List<Address> addList = null;
//            Geocoder ge = new Geocoder(getActivity());
//            try {
//                addList = ge.getFromLocation(data[0], data[1], 1);
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            if (addList != null && addList.size() > 0) {
//                for (int i = 0; i < addList.size(); i++) {
//                    Address ad = addList.get(i);
//                    latLongString = ad.getLocality();
//                }
//            }
//            // mPoiSearch.searchInCity((new PoiCitySearchOption())
//            // .city(latLongString).keyword("森森").pageNum(loadIndex));
//            tvLocation.setText(latLongString);
//            dingei = latLongString;
//            SPUtils.put(App.ctx, null, SPContants.ADDRESS, latLongString);
//            getCityCode(latLongString);
//            getStoreList(cityCode, all);
        }

    };

    protected float zoom = 9;

//    protected NavigationDetailBean navi;
    private LocationClient mLocClient;
    public static String addrDetail_navigation;

    UserPresenter userPresenter;
    LocationUtil locationUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_store;
    }

    @Override
    protected void initData() {
        userPresenter=new UserPresenter(this);
        locationUtil=new LocationUtil(getActivity(),this);
    }

    @Override
    public void update(Observable o, Object data) {

    }

    @Override
    public void getLatAndLng(String cityName, double lat, double lng, String areaName) {

    }


//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//        super.onActivityCreated(savedInstanceState);
        // 地图初始化
        // baiduMap = mapView.getMap();
        // // // //开启定位图层
        // baiduMap.setMyLocationEnabled(true);
//        dbManager = new DBManager(getActivity());
//        reback = (ImageView) getActivity().findViewById(R.id.reback);
//        dbManager.openDateBase();
//        database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/"
//                + DBManager.DB_NAME, null);
//        descriptor = BitmapDescriptorFactory
//                .fromBitmap(BitmapFactory
//                        .decodeResource(getActivity().getResources(),
//                                R.drawable.map_log2));
//        locationManager = (LocationManager) getActivity().getSystemService(
//                Context.LOCATION_SERVICE);
//
//        initBdMapViews();
//         addMyOverlay(latitude,longitude);
//        new Thread() {
//            @Override
//            public void run() {
//                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                Location location = locationManager
//                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                if (location != null) {
//                    latitude = location.getLatitude(); // 经度
//                    longitude = location.getLongitude(); // 纬度
//                    double[] data = {latitude, longitude};
//                    Message msg = handler.obtainMessage();
//                    msg.obj = data;
//                    handler.sendMessage(msg);
//                }
//            }
//        }.start();
        // 注册广播
//        IntentFilter intentFilterAvart = new IntentFilter("resultData");
//        getActivity().registerReceiver(avartBroadcastReceiver,
//                intentFilterAvart);
//        // 注册广播
//        IntentFilter intentFilterAvartss = new IntentFilter("resultDataresin");
//        getActivity().registerReceiver(avartBroadcast, intentFilterAvartss);
//        baiduMap = mMapView.getMap();
//        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//
//            @Override
//            public boolean onMarkerClick(Marker arg0) {
//                //更改下方的信息
//                Bundle model = arg0.getExtraInfo();
//                navi = (NavigationDetailBean) model.get("model");
//                // 创建InfoWindow展示的view
//                baiduMap.hideInfoWindow();
//                Button button = new Button(getActivity()
//                        .getApplicationContext());
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
//                setaStoreInfo(navi);
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
//        mMapView.showZoomControls(false);// 隐藏缩放按钮
//        // 隐藏logo
//        View child = mMapView.getChildAt(1);
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
//        // 开启定位图层
//        baiduMap.setMyLocationEnabled(true);
//// 定位初始化
//        mLocClient = new LocationClient(getActivity());
//        mLocClient.registerLocationListener(new MyLocationListenner());
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
//        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//        int span = 1000;
//        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
//        option.setOpenGps(true);//可选，默认false,设置是否使用gps
//        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
//        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
//        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
//        mLocClient.setLocOption(option);
//        mLocClient.start();
//    }

    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
//            baiduMap.clear();
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(100).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
//            baiduMap.setMyLocationData(locData);
//            LatLng llA=new LatLng(location.getLatitude(),location.getLongitude());
//            MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA)
//                    .zIndex(9).draggable(true);
//            baiduMap.addOverlay(ooA);
            if (location != null) {
                if (isFirstLoc) {
                    isFirstLoc = false;
//                LatLng ll = new LatLng(location.getLatitude(),
//                        location.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll).zoom(18.0f);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                    addrDetail_navigation = location.getAddress().address;
                    new TestAsyncTask().execute(location.getCity());

                }
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

//    protected void updateStoreInfo(NavigationBean navi) {
//        // TODO Auto-generated method stub
//
//    }

    class TestAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return params[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                cityCode=getCityCode(s);
            }catch (Exception e){

            }
            tvLocation.setText(s);
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



    BroadcastReceiver avartBroadcast = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
//            String resultData = (String) SPUtils.get(App.ctx, null,
//                    SPContants.SEWARPROVINCE, "");
//            System.out.println("SEWARPROVINCE" + resultData);
//            getCityCode(resultData);
//            tvLocation.setText("" + resultData);
            // mPoiSearch.searchInCity((new PoiCitySearchOption())
            // .city(resultData).keyword("森森").pageNum(loadIndex));
        }

        ;
    };

    BroadcastReceiver avartBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
//            String resultData = (String) SPUtils.get(App.ctx, null,
//                    SPContants.PROVINCE, "");
//            System.out.println("resultDataresultData" + resultData);
//            getCityCode(resultData);
//            tvLocation.setText("" + resultData);
            // mPoiSearch.searchInCity((new PoiCitySearchOption())
            // .city(resultData).keyword("森森").pageNum(loadIndex));
        }

        ;
    };

    // 初始化地图View
    private void initBdMapViews() {
        mMapView = (MapView) getActivity().findViewById(R.id.bmapsView);
    }


    @Override
    public void onDestroy() {
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        if (mMapView != null) {
            mMapView.onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        if (mMapView != null) {
            mMapView.onPause();
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

//    private void getTopBannerData(NavigationDetailBean bean) {
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

//    protected ArrayList<NavigationBean> arrayAll;
//
//    protected ArrayList<NavigationBean> arrayPart;

    // 店鋪列表
//    private void getStoreList( String cityCode, int all) {
//
//        MyJsonRequest.Builder<ArrayList<NavigationDetailBean>> builder = new MyJsonRequest.Builder<>();
//        builder.apiVer("100").typeKey("BY_BranchOffice_search");
//        if (cityCode != null) {
//            builder.param("city", cityCode);
//        }
//        builder.param("all", all);
//        // if (areaCode != null) {
//        // builder.param("area", areaCode);
//        // }
//        try {
//            mMapView.getOverlay().clear();
//        }catch (Exception e){
//
//        }
//        builder.requestListener(
//                new XRequestListener<ArrayList<NavigationDetailBean>>() {
//                    @Override
//                    public void onResponse(ArrayList<NavigationDetailBean> arrayList) {
//                        if (arrayList.size() > 0) {
//                            storeId = arrayList.get(0).getId();
//                            // 将View转换为BitmapDescriptor
//                            LatLng ll = new LatLng(arrayList.get(0).getLat(),
//                                    arrayList.get(0).getLng());
//                            MapStatusUpdate u = MapStatusUpdateFactory
//                                    .newLatLngZoom(ll, zoom); // 设置地图中心点以及缩放级别
//                            baiduMap.animateMapStatus(u);
//                            Bundle bundle = new Bundle();
//                            for (int i = 0; i < arrayList.size(); i++) {
//                                bundle = new Bundle();
//                                bundle.putSerializable("model", arrayList.get(i));
//                                MarkerOptions markerOptions = new MarkerOptions()
//                                        .position(
//                                                new LatLng(arrayList.get(i)
//                                                        .getLat(), arrayList
//                                                        .get(i).getLng()))
//                                        .icon(descriptor)
//                                        .title(arrayList.get(i).getName())
//                                        .zIndex(18).draggable(true).extraInfo(bundle);
//                                baiduMap.addOverlay(markerOptions);
//                            }
//                            getStoreDetail(storeId);
//                            xScrollView.setVisibility(View.VISIBLE);
//                            lineMap.setVisibility(View.VISIBLE);
//                        } else {
//                            ByAlert.alert("当前选择地区没有搜索到店铺哦！");
//                            xScrollView.setVisibility(View.GONE);
//                            lineMap.setVisibility(View.GONE);
//                        }
//                    }
//                }).errorListener(new XErrorListener() {
//
//            @Override
//            public void onErrorResponse(Exception exception, int code,
//                                        String msg) {
////                 ByAlert.alert(msg+"ERROR");
//                if (msg == null) {
//                    ByAlert.alert("当前选择地区没有搜索到店铺哦！");
//                    xScrollView.setVisibility(View.GONE);
//                    lineMap.setVisibility(View.GONE);
//                }
//                try {
//                    closeProgressDialog();
//
//                } catch (Exception e) {
//                    // TODO: handle exception
//                }
//            }
//        });
//
//        HttpRequest.getDefaultRequestQueue().add(builder.build());
//    }
//
//    // 店鋪列表
//    private void getArea(String areacode) {
//        MyJsonRequest.Builder<ArrayList<NavigationBean>> builder = new MyJsonRequest.Builder<>();
//        builder.apiVer("100").typeKey("BY_BranchOffice_search");
//        if (cityCode != null)
//            builder.param("area", areacode);
//
//        System.out.println(">>>>>>>>>>>>>" + areacode);
//        builder.requestListener(
//                new XRequestListener<ArrayList<NavigationBean>>() {
//                    @Override
//                    public void onResponse(ArrayList<NavigationBean> arrayList) {
//                        // ByAlert.alert(arrayList.size() + "szie");
//                        if (arrayList.size() > 0) {
//                            storeId = arrayList.get(0).getId();
//                            getStoreDetail(storeId);
//                            xScrollView.setVisibility(View.VISIBLE);
//                            lineMap.setVisibility(View.VISIBLE);
//                        } else {
//                            ByAlert.alert("当前选择地区没有搜索到店铺哦！");
//                            xScrollView.setVisibility(View.GONE);
//                            lineMap.setVisibility(View.GONE);
//
//                        }
//                    }
//                }).errorListener(new XErrorListener() {
//
//            @Override
//            public void onErrorResponse(Exception exception, int code,
//                                        String msg) {
//                // ByAlert.alert(msg+"ERROR");
//                if (msg == null) {
//                    ByAlert.alert("当前选择地区没有搜索到店铺哦！");
//                    xScrollView.setVisibility(View.GONE);
//                    lineMap.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        HttpRequest.getDefaultRequestQueue().add(builder.build());
//    }

    // 店鋪詳細
//    protected void getStoreDetail(String storeId) {
//        // TODO Auto-generated method stub
//        MyJsonRequest.Builder<NavigationDetailBean> builder = new MyJsonRequest.Builder<>();
//        builder.apiVer("100").typeKey("BY_BranchOffice_storeDetail")
//                .param("id", storeId);
//        builder.requestListener(new XRequestListener<NavigationDetailBean>() {
//
//            @Override
//            public void onResponse(final NavigationDetailBean bean) {
//                if (bean != null) {
//                    lat = bean.getLat();
//                    lng = bean.getLng();
//                    if (bean.getBranchImgs() != null)
//                        branches = bean.getBranchImgs().split(",");
//                    addrDetail = bean.getAddressDetail();
//                    contactName = bean.getPersonName();
//                    phone = bean.getMobile();
//                    mobile = bean.getMobile();
//                    setaStoreInfo(bean);
//                    // setMapView();
//
////                     LatLng point = new LatLng(bean.getLat(), bean.getLng());
////                     constructionLocationIcon(point);
//                    relyoutDaoHang.setOnClickListener(new OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//                            // TODO Auto-generated method stub
//                            if (bean.getAddressDetail() == null) {
//                                Byalert.alert("当前没有店铺哟!");
//                                xScrollView.setVisibility(View.GONE);
//                                lineMap.setVisibility(View.GONE);
//                            } else {
//                                // mapView.setVisibility(View.VISIBLE);
//                                lineMap.setVisibility(View.VISIBLE);
//                                xScrollView.setVisibility(View.VISIBLE);
//                                Intent intent1 = new Intent(getActivity(),
//                                        Navigationactivity.class);
//                                intent1.putExtra("areaCode", areaCode);
//                                intent1.putExtra("cityCode", cityCode);
//                                intent1.putExtra("searchType", searchType);
//                                startActivity(intent1);
//                            }
//
//                        }
//                    });
//                    tvNavigation.setOnClickListener(new OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//                            // TODO Auto-generated method stub
//                            if (bean.getAddressDetail() == null) {
//                                Byalert.alert("当前没有店铺哟!");
//                            } else {
//                                // mapView.setVisibility(View.VISIBLE);
//                                xScrollView.setVisibility(View.VISIBLE);
//                                Intent intent1 = new Intent(getActivity(),
//                                        Navigationactivity.class);
//                                intent1.putExtra("areaCode", areaCode);
//                                intent1.putExtra("cityCode", cityCode);
//                                intent1.putExtra("searchType", searchType);
//                                intent1.putExtra("navi", navi);
//                                startActivity(intent1);
//                            }
//                        }
//                    });
//                }
//            }
//        }).errorListener(new XErrorListener() {
//
//            @Override
//            public void onErrorResponse(Exception exception, int code,
//                                        String msg) {
//
//            }
//        });
//
//        HttpRequest.getDefaultRequestQueue().add(builder.build());
//    }
//
//    // 設置店鋪信息
//    protected void setaStoreInfo(NavigationDetailBean navi) {
//        // TODO Auto-generated method stub
//        addr.setText(navi.getAddressDetail());
//        tvContacts.setText(navi.getPersonName());
//
//        tvPhones.setText(navi.getPhone());
//        tvMobilePhones.setText(navi.getMobile());
//
//        getTopBannerData(navi);
//    }

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
