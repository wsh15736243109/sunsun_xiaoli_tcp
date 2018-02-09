package sunsun.xiaoli.jiarebang.shuizuzhijia.store;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.NavigationBean;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;
import com.itboye.pondteam.utils.loadingutil.MAlert;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.NavigationAdapter;
import sunsun.xiaoli.jiarebang.utils.CameraPopupWindowmap;
import sunsun.xiaoli.jiarebang.utils.PhoneUtil;


public class Navigationactivity extends BaseActivity {

    ListView listView;
    NavigationAdapter adapter;
    private ArrayList<NavigationBean.NavigationDetail> dataList = new ArrayList<>();
    String cityCode = "";
    private NavigationBean.NavigationDetail navi;
    TextView txt_title;
    ImageView img_back;
    private CameraPopupWindowmap cameraPopupWindow;

    @Override
    @SuppressLint("InlinedApi")
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_navigation);
        cityCode = getIntent().getStringExtra("cityCode");
        navi = (NavigationBean.NavigationDetail) getIntent().getSerializableExtra("navi");
        if (navi != null) {//选中了
            dataList.add(navi);
        } else {//没有选中
//            getDetailNaigation(cityCode, areaCode);
        }

        txt_title.setText("导航");
        adapter = new NavigationAdapter(dataList, this);
        listView.setAdapter(adapter);
    }

    private void getDetailNaigation(String cityCode, String areaCode) {
//		System.out.println("relyoutDaoHang"+cityCode+areaCode);
//		// TODO Auto-generated method stub
//		MyJsonRequest.Builder<ArrayList<NavigationDetailBean>> builder = new MyJsonRequest.Builder<ArrayList<NavigationDetailBean>>();
//		if (searchType) {// 为true是通过城市编码搜索
//			builder.param("city", cityCode);
//		} else {// 为false为通过区域搜索
//			builder.param("area", areaCode);
//		}
//		builder.apiVer("100")
//				.typeKey("BY_BranchOffice_search")
//				.param("city", cityCode)
//				.param("area",areaCode)
//				.requestListener(
//						new XRequestListener<ArrayList<NavigationDetailBean>>() {
//
//							@Override
//							public void onResponse(
//									ArrayList<NavigationDetailBean> orderBean) {
//								Log.v("count", orderBean.size() + "");
//								if (orderBean != null)
//									dataList.addAll(orderBean);
//								adapter.notifyDataSetChanged();
//
//							}
//						}).errorListener(new XErrorListener() {
//
//					@Override
//					public void onErrorResponse(Exception exception, int code,
//							String msg) {
//						// onPullDownError(adapter, xlistview, exception, code,
//						// msg);
//						ByAlert.alert("网络错误！");
//					}
//				});
//
//		MyJsonRequest<ArrayList<NavigationDetailBean>> request = builder.build();
//		HttpRequest.getDefaultRequestQueue().add(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.re_phone:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("确定拨打电话？");
                alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PhoneUtil.goTel(dataList.get(0).getPhone(), Navigationactivity.this);
                    }
                });
                alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                break;
            case R.id.re_go_there:
                cameraPopupWindow = new CameraPopupWindowmap(
                        this, this);
                cameraPopupWindow.showAtLocation(v, Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);

                break;
            case R.id.open_camera:
                onBaidu(dataList.get(0).getAddressDetail());
                break;
            case R.id.pick_image:
                onGoade(0);
                break;
        }
    }


    /***
     * 百度地图
     */
    public void onBaidu(String addres) {
        System.out.println("adddddddddd" + addres);
        //调起百度地图客户端
        try {

            String latdata = (String) SPUtils.get(MyApplication.getInstance(), null, Const.LOCATION_LAT,
                    "");
            String lngdata = (String) SPUtils.get(MyApplication.getInstance(), null, Const.LOCATION_LNG,
                    "");
//            String addesss = (String) SPUtils.get(MyApplication.getInstance(), null, SPContants.ADDRESS,
//                    "");
            String addesss = "";
            Intent intent = Intent.getIntent("intent://map/direction?origin=latlng:" + latdata + "," + lngdata + "|name:" + addesss + "&destination=" + addres + "&mode=driving®ion=" + addres + "&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            if (isInstallByread("com.baidu.BaiduMap")) {
                startActivity(intent); //启动调用
                Log.e("GasStation", "百度地图客户端已经安装");
            } else {
                Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /***
     * 高德地图
     */
    public void onGoade(int position) {
        if (isInstallByread("com.autonavi.minimap")) {
            try {
                String ss = "androidamap://route?sourceApplication=amap&dlat=" + dataList.get(position).getLat()  + "&dlon=" +  dataList.get(position).getLng() + "&dname=" +  dataList.get(position).getAddressDetail()+ "&dev=0&t=2";
                String str="androidamap://navi?sourceApplication=" +"小鲤智能" + "&poiname=" + dataList.get(position).getAddressDetail() + "&lat=" + dataList.get(position).getLat() + "&lon=" + dataList.get(position).getLng() + "&dev=0";
                Intent intent = Intent.getIntent(ss);
                startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            MAlert.alert("您尚未安装高德地");
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }

    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

}
