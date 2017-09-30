package sunsun.xiaoli.jiarebang.device.jinligang;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.AddDeviceAdapter;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.AddBenas;
import sunsun.xiaoli.jiarebang.beans.AqDeviceInfo;
import sunsun.xiaoli.jiarebang.device.ActivityStepFirst;
import sunsun.xiaoli.jiarebang.device.AddDeviceActivity;
import sunsun.xiaoli.jiarebang.device.ManualAddDeviceActivity;
import sunsun.xiaoli.jiarebang.utils.DeviceType;


/**
 * 日期：2015-04-30 1. 添加版本识别功能，支持AQ107、AQ209和AQ805设备 日期：2015-05-05 1. 添加手动添加设备功能
 */
@SuppressLint("NewApi")
public class AddDeviceNewActivity extends BaseActivity implements
        OnClickListener {
    int count = 0;
    boolean mReadyExit;
    ListView mListView;
    Context mContext;
    AqDeviceInfo mSelectDeviceInfo;

    private TextView title;
    private ImageView imgAdd;
    private ListView listview;
    AddDeviceAdapter adapter;
    List<AddBenas> benas;
    ImageView img_back;
    DeviceType deviceType;//设备类型
    public static String name[] = null;
    int url[] = {
            R.drawable.device_aq,
            R.drawable.device_500,
            R.drawable.device_700,
            R.drawable.device_jiarebang,
            R.drawable.device_ph,
            R.drawable.device_shuibeng,
            R.drawable.device_chitangguolv,
            R.drawable.device_shexiangtou,
            R.drawable.device_shuizudeng,
            R.drawable.device_jiaozhiliubeng,
    };
    int add[] = {
            R.drawable.add_device, R.drawable.add_device,
            R.drawable.add_device, R.drawable.add_device,
            R.drawable.add_device, R.drawable.add_device,
            R.drawable.add_device, R.drawable.add_device,
            R.drawable.add_device, R.drawable.add_device,
            R.drawable.add_device};

    PopupWindow popupWindow;
    private App myApp;
    TextView txt_title;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_add_news_device);
        myApp = (App) getApplication();
        name = new String[]{
                getString(R.string.zhineng806),
                getString(R.string.zhineng228),
                getString(R.string.zhineng700),
                getString(R.string.zhinengjiarebang),
                getString(R.string.yuancheng_ph),
                getString(R.string.zhinengbianpinshuibeng),
                getString(R.string.chitangguolv),
                getString(R.string.zhinengshexiangtou),
                getString(R.string.shuizudeng),
                getString(R.string.zhinengqibeng)};
        listview = (ListView) findViewById(R.id.addListview);
        mContext = this;
        myApp.addDeviceUI = this;
        txt_title.setText(getString(R.string.add_new_device));
        onAdapter();
        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                switch (position) {
                    case 0:
                        deviceType = DeviceType.DEVICE_AQ806;
                        break;
                    case 1:
                        deviceType = DeviceType.DEVICE_AQ500;
                        break;
                    case 2:
                        deviceType = DeviceType.DEVICE_AQ700;
                        break;
                    case 3:
                        deviceType = DeviceType.DEVICE_JIAREBANG;
                        break;
                    case 4:
                        deviceType = DeviceType.DEVICE_PH;
                        break;
                    case 5:
                        deviceType = DeviceType.DEVICE_SHUIBENG;
                        break;
                    case 6:
                        deviceType = DeviceType.DEVICE_GUOLVTONG;
                        break;
                    case 7:
                        deviceType = DeviceType.DEVICE_CAMERA;
                        break;
                    case 8:
                        deviceType = DeviceType.DEVICE_SHUIZUDENG;
                        break;
                    case 9:
                        deviceType = DeviceType.DEVICE_QIBENG;
                        break;
                }
                showPopwindow(position);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myApp.addDeviceUI = null;
    }

    private void showPopwindow(final int position) {
        View popView = View.inflate(this, R.layout.add_menu_windss, null);

        TextView open_configuration = (TextView) popView
                .findViewById(R.id.open_configuration);
        TextView open_camera = (TextView) popView
                .findViewById(R.id.open_camera);
        TextView pick_image = (TextView) popView.findViewById(R.id.pick_image);
        TextView camera_cancel_tv = (TextView) popView
                .findViewById(R.id.camera_cancel_tv);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels - 20;

        final PopupWindow popWindow = new PopupWindow(popView, width, ActionBar.LayoutParams.WRAP_CONTENT);
        // popWindow.setAnimationStyle(R.style.AnimBottom);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);// 设置允许在外点击消失


        open_configuration.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                Intent mainIntent = new Intent(AddDeviceNewActivity.this,
                        ActivityStepFirst.class);
                mainIntent.putExtra("device_type", name[position]);
                mainIntent.putExtra("position", position);
                mainIntent.putExtra("device", deviceType);
                startActivity(mainIntent);
//						Intent mainIntent = new Intent(AddDeviceNewActivity.this,
//								MDeviceActivity.class);
//						startActivity(mainIntent);
            }
        });
        open_camera.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 添加局域网设备
                popWindow.dismiss();
                Intent mainIntent = new Intent(AddDeviceNewActivity.this,
                        AddDeviceActivity.class);
                mainIntent.putExtra("device_type", name[position]);
                mainIntent.putExtra("position", position);
                mainIntent.putExtra("device", deviceType);
                startActivity(mainIntent);
            }
        });
        pick_image.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 手动添加设备
                popWindow.dismiss();
                Intent mainIntent = new Intent(AddDeviceNewActivity.this,
                        ManualAddDeviceActivity.class);
                mainIntent.putExtra("device_type", name[position]);
                mainIntent.putExtra("position", position);
                mainIntent.putExtra("device", deviceType);
                startActivity(mainIntent);
//				Intent mainIntent = new Intent(AddDeviceNewActivity.this,
//						ManualAddDeviceActivity.class);
//				startActivity(mainIntent);
            }
        });
        camera_cancel_tv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popWindow.dismiss();
            }
        });

        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(popView, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @SuppressWarnings("unused")
    public void onAdapter() {
        benas = new ArrayList<AddBenas>();
        for (int i = 0; i < name.length; i++) {
            AddBenas addBenas = new AddBenas();
            addBenas.setName(name[i]);
            addBenas.setBitmp(add[i]);
            addBenas.setImg(url[i]);
            benas.add(addBenas);
        }
        adapter = new AddDeviceAdapter(benas, myApp.addDeviceUI);
        listview.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

}
