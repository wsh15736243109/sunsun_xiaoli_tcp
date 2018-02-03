package sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.bean.NavigationBean;

import java.io.File;
import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.shuizuzhijia.store.Navigationactivity;

/***
 * 导航Adapter
 *
 * @author admin
 */
public class NavigationAdapter extends BaseAdapter {
    List<NavigationBean.NavigationDetail> dataList;
    Activity activity;
//    CameraPopupWindowmap cameraPopupWindow;
    String baidu;
    int position = 0;
    double lat, lng;

    public NavigationAdapter(List<NavigationBean.NavigationDetail> dataList, Activity activity) {
        this.dataList = dataList;
        this.activity = activity;
    }

    @Override
    public int getCount() {

        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {

        return dataList == null ? 0 : dataList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(MyApplication.getInstance()).inflate(
                    R.layout.item_navigation, null);
            holder = new ViewHolder();
            holder.destination = (TextView) convertView
                    .findViewById(R.id.destination);// 目的地
            holder.detail_addr = (TextView) convertView
                    .findViewById(R.id.detail_addr);// 详细地址
            holder.re_go_there = (RelativeLayout) convertView
                    .findViewById(R.id.re_go_there);
            holder.re_phone = (RelativeLayout) convertView
                    .findViewById(R.id.re_phone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        baidu = dataList.get(position).getAddressDetail();
        lat = dataList.get(position).getLat();
        lng = dataList.get(position).getLng();
        holder.destination.setText(dataList.get(position).getName());// 目的地
        holder.detail_addr.setText(dataList.get(position).getAddressDetail());// 详细地址
        holder.re_phone.setTag(position);
        holder.re_phone.setOnClickListener((Navigationactivity)activity);
        holder.re_go_there.setTag(position);
        holder.re_go_there.setOnClickListener((Navigationactivity)activity);
        return convertView;
    }

    /**
     * 打开浏览器进行百度地图导航
     */
    private void openWebMap(double slat, double slon, String sname, double dlat, double dlon, String dname, String city) {
        Uri mapUri = Uri.parse(getWebBaiduMapUri(String.valueOf(slat), String.valueOf(slon), sname,
                String.valueOf(dlat), String.valueOf(dlon),
                dname, city, ""));
        Intent loction = new Intent(Intent.ACTION_VIEW, mapUri);
        activity.startActivity(loction);
    }

    public static String getWebBaiduMapUri(String originLat, String originLon, String originName, String desLat, String desLon, String destination, String region, String appName) {
        String uri = "http://api.map.baidu.com/direction?origin=latlng:%1$s,%2$s|name:%3$s" +
                "&destination=latlng:%4$s,%5$s|name:%6$s&mode=driving&region=%7$s&output=html" +
                "&src=%8$s";
        return String.format(uri, originLat, originLon, originName, desLat, desLon, destination, region, appName);
    }


    class ViewHolder {
        TextView destination;
        TextView detail_addr;
        RelativeLayout re_go_there;// 到这里去
        RelativeLayout re_phone;// 电话


    }


//    private OnClickListener onclicklister = new OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            // TODO Auto-generated method stub
//            switch (view.getId()) {
//                case R.id.open_camera:
//                    onBaidu(baidu);
//
//                    break;
//                case R.id.pick_image:
//                    onGoade(position, baidu, lat, lng);
//                    break;
////                case R.id.pick_guge:
////                    onGuge(lat, lng);
////                    break;
//            }
//        }
//    };

    /***
     * 谷歌地图
     */
    public void onGuge(double lat, double lng) {
        if (isInstallByread("com.google.android.apps.maps")) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng + ", + Sydney +Australia");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            activity.startActivity(mapIntent);
        } else {
            Toast.makeText(activity, "您尚未安装谷歌地图", Toast.LENGTH_LONG).show();

            Uri uri = Uri.parse("market://details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            activity.startActivity(intent);
        }

    }

//    /***
//     * 高德地图
//     */
//    public void onGoade(int position, String baidu, double lat, double lng) {
//        String latdata = (String) SPUtils.get(App.ctx, null, SPContants.LATData,
//                "");
//        String lngdata = (String) SPUtils.get(App.ctx, null, SPContants.LNGdata,
//                "");
//        String addesss = (String) SPUtils.get(App.ctx, null, SPContants.ADDRESS,
//                "");
//        if (isInstallByread("com.autonavi.minimap")) {
//            try {
////                StoreFragment.addrDetail_navigation
////                dataList.get(position).getAddressDetail()
//                System.out.println("详细地址" + dataList.get(position).getAddressDetail() + lat + "lat" + lng + "lng");
////                Byalert.alert( dataList.get(position).getAddressDetail() );
//                Intent intent = Intent.getIntent("androidamap://navi?sourceApplication=" +"森森服务" + "&poiname=" + dataList.get(position).getAddressDetail() + "&lat=" + lat + "&lon=" + lng + "&dev=0");
//                activity.startActivity(intent);
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//        } else {
//            Toast.makeText(activity, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
//            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            activity.startActivity(intent);
//        }
//
//    }
//
//    /***
//     * 百度地图
//     */
//    public void onBaidu(String addres) {
//        System.out.println("adddddddddd" + addres);
//        //调起百度地图客户端
//        try {
//
//            String latdata = (String) SPUtils.get(App.ctx, null, SPContants.LATData,
//                    "");
//            String lngdata = (String) SPUtils.get(App.ctx, null, SPContants.LNGdata,
//                    "");
//            String addesss = (String) SPUtils.get(App.ctx, null, SPContants.ADDRESS,
//                    "");
//
//            Intent intent = Intent.getIntent("intent://map/direction?origin=latlng:" + latdata + "," + lngdata + "|name:" + addesss + "&destination=" + addres + "&mode=driving®ion=" + addres + "&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
//            if (isInstallByread("com.baidu.BaiduMap")) {
//                activity.startActivity(intent); //启动调用
//                Log.e("GasStation", "百度地图客户端已经安装");
//            } else {
//                Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
//                intent = new Intent(Intent.ACTION_VIEW, uri);
//                activity.startActivity(intent);
//            }
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }

    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

}
