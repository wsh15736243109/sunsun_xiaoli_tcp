package sunsun.xiaoli.jiarebang.device.camera;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.DeviceListBean;
import com.itboye.pondteam.custom.swipemenulistview.SwipeMenu;
import com.itboye.pondteam.custom.swipemenulistview.SwipeMenuCreator;
import com.itboye.pondteam.custom.swipemenulistview.SwipeMenuItem;
import com.itboye.pondteam.custom.swipemenulistview.SwipeMenuListView;
import com.itboye.pondteam.db.DBManager;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.custom.VpSwipeRefreshLayout;
import sunsun.xiaoli.jiarebang.device.ActivityStepFirst;
import sunsun.xiaoli.jiarebang.device.AddDeviceActivity;
import sunsun.xiaoli.jiarebang.device.EditDeviceActivity;
import sunsun.xiaoli.jiarebang.device.ManualAddDeviceActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.VideoActivity;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;


/**
 */
@SuppressLint("NewApi")
public class CameraDeviceListActivity extends BaseActivity implements Observer, SwipeRefreshLayout.OnRefreshListener {
    int count = 0;
    int cnt = 0;
    App mApp;
    boolean mReadyExit;
    SwipeMenuListView mListView;
    Context mContext;
    private ProgressDialog mProgressDialog;
    ImageView img_back;
    //    RelativeLayout nodata;
    // 连接进度
    private final static int SUCCESS = 0;
    int progress = 0;

    RelativeLayout relyout, nodata;
    ImageView img_right;
    TextView txt_title;
    RelativeLayout re_addnew;
    TextView txt_add_jieshao;
    public String uid = "";
    UserPresenter userPresenter;
    public ArrayList<DeviceListBean> arrayList = new ArrayList<>();
    public int position;
    TextView txt_exist;
    private ProgressDialog loadingDialog;
    public DeviceListBean mSelectDeviceInfo;
    DBManager dbManager;
    private ArrayList<HashMap<String, Object>> listItems;
    //    PtrFrameLayout ptr;
    private String currentDid, currentType;
    String aq_did;

    VpSwipeRefreshLayout swipe_layout;
    private View footerView;
    private SimpleAdapter listItemsAdapter;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_device);
        mApp = (App) getApplication();
        mApp.isStartSearch = true;
        mApp.mCameraDevice = this;
        dbManager = new DBManager(this);
        aq_did = getIntent().getStringExtra("did");
        img_right.setBackgroundResource(R.drawable.add_device);
        txt_title.setText(getString(R.string.myadvice));
        footerView = LayoutInflater.from(this).inflate(R.layout.device_list_footer, null);
        nodata = (RelativeLayout) footerView.findViewById(R.id.nodata);
        txt_add_jieshao = (TextView) footerView.findViewById(R.id.txt_add_jieshao);
        btn_addnew = (Button) footerView.findViewById(R.id.btn_addnew);
        btn_addnew.setOnClickListener(this);
        listItems = new ArrayList<>();
        listItemsAdapter = new SimpleAdapter(this, listItems,
                R.layout.device_item, new String[]{"ItemName", "ItemDid",
                "ItemState1", "ItemState2", "ItemRightArrow", "ItemIcon"},
                new int[]{R.id.textView_dev_name, R.id.textView_dev_did,
                        R.id.textView_dev_state1, R.id.textView_dev_state2,
                        R.id.imageView_dev_right_arrow, R.id.imageView_dev_logo});

        mListView.addFooterView(footerView);
        mListView.setAdapter(listItemsAdapter);
        userPresenter = new UserPresenter(this);
        uid = getSp(Const.UID);
        //设置向下拉多少出现刷新
        swipe_layout.setDistanceToTriggerSync(150);
        swipe_layout.setColorSchemeColors(getResources().getColor(R.color.main_green));
        swipe_layout.setOnRefreshListener(this);
        showProgressDialog(getString(R.string.get_deviceInfoing), true);
        img_back.setVisibility(View.VISIBLE);
        txt_exist.setVisibility(View.GONE);

        mContext = this;
        mReadyExit = false;
        mProgressDialog = new ProgressDialog(this);

        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                // Cancel task.
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mProgressDialog.dismiss();
                }
                return false;
            }
        });
        /**
         * // mProgressDialog = ProgressDialog.show(DeviceActivity.this, //
         * mSelectDeviceInfo.mDeviceName, "连接中，请稍后……"); // mProgressDialog = new
         * ProgressDialog(App.context);
         */
//		 列表项短按键处理
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CameraDeviceListActivity.this.position = position;
                mSelectDeviceInfo = arrayList.get(position);
                startDeviceUI(false);
            }

        });

        // 列表项长按键处理
        mListView
                .setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, final int position, long id) {
                        mSelectDeviceInfo = arrayList.get(position);
                        mApp.mEditDeviceInfo = mSelectDeviceInfo;
                        Intent mainIntent = new Intent(CameraDeviceListActivity.this,
                                EditDeviceActivity.class);
                        mainIntent.putExtra("model", mSelectDeviceInfo);
                        mainIntent.putExtra("isMasterDevice", false);
                        mainIntent.putExtra("type", mSelectDeviceInfo.getSlave_device_type());
                        mainIntent.putExtra("id", mSelectDeviceInfo.getId());
                        CameraDeviceListActivity.this.startActivity(mainIntent);
                        return true;
                    }
                });
        refreshDeviceListTest();
        initSwipListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDeviceList();
    }

    private void initSwipListView() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // TODO Auto-generated method stub
                SwipeMenuItem deleteItem = new SwipeMenuItem(CameraDeviceListActivity.this);
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setTitle(getString(R.string.unbind_device));
                deleteItem.setTitleColor(Color.WHITE);
//				// set item width
                deleteItem.setWidth(dp2px(90));
                deleteItem.setTitleSize(18);
//				// set a icon
//				deleteItem.setIcon(R.drawable.ic_delete);
//				deleteItem.setBackground(R.drawable.delete_list_item_bg);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        mListView.setMenuCreator(creator);
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
//					ShopCarBean model=new ShopCarBean();
//					model.setId(dataList.getList().get(position).getId());
//					carPresenter.delete(model);
//                        MAlert.alert("删除了");
                        // open
//					open(item);
                        //删除设备操作
                        mSelectDeviceInfo = arrayList.get(position);
                        userPresenter.cameraUnBind(mSelectDeviceInfo.getMaster_did(), mSelectDeviceInfo.getSlave_did());
                        break;
                    case 1:
                        // delete
//					delete(item);
//					mAppList.remove(position);
//					mAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public void getDeviceList() {
        userPresenter.cameraQuery(aq_did);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApp.mCameraDevice = null;
        mApp.isStartSearch = false;
    }

    /**
     * 时间计时器
     *
     * @param duration
     * @return
     */
    public static String timeParse(long duration) {
        String time = "";

        long minute = duration / 60000;
        long seconds = duration % 60000;

        long second = Math.round((float) seconds / 1000);

        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";

        if (second < 10) {
            time += "0";
        }
        time += second;

        return time;
    }


    public void refreshDeviceListTest() {
        mListView.setVisibility(View.VISIBLE);
        relyout.setVisibility(View.VISIBLE);
        listItems = new ArrayList<>();
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ItemName", arrayList.get(i).getSlave_name());
                map.put("ItemDid", "DID:" + arrayList.get(i).getSlave_did());
                map.put("ItemState1", "");
                map.put("ItemState2", "");
                map.put("type", "camera");
                map.put("ItemRightArrow", R.drawable.ic_right_arrow);
                map.put("ItemIcon", R.drawable.device_shexiangtou);
                listItems.add(map);
            }
        } else {
            arrayList = new ArrayList<>();
        }

        listItemsAdapter = new SimpleAdapter(this, listItems,
                R.layout.device_item, new String[]{"ItemName", "ItemDid",
                "ItemState1", "ItemState2", "ItemRightArrow", "ItemIcon"},
                new int[]{R.id.textView_dev_name, R.id.textView_dev_did,
                        R.id.textView_dev_state1, R.id.textView_dev_state2,
                        R.id.imageView_dev_right_arrow, R.id.imageView_dev_logo});
        mListView.setAdapter(listItemsAdapter);
        if (listItems.size() > 0) {
            mListView.setVisibility(View.VISIBLE);
            relyout.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);
        } else {
            nodata.setVisibility(View.VISIBLE);
        }
        try {
            mSelectDeviceInfo = arrayList.get(position);
        } catch (Exception e) {
            mSelectDeviceInfo = new DeviceListBean();
        }
    }


    protected void onStop() {
        mProgressDialog.dismiss();
        super.onStop();
    }

    Button btn_addnew;

    @Override
    public void onClick(View v) {
        Intent intent = null;
        AlertDialog.Builder alert = null;
        switch (v.getId()) {
            case R.id.img_right:
            case R.id.btn_addnew:
                showPopwindow(7);
                break;
            case R.id.img_back:
                finish();
                break;
        }
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


        open_configuration.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                Intent intent = new Intent(CameraDeviceListActivity.this, ActivityStepFirst.class);
                intent.putExtra("aq_did", aq_did);
                intent.putExtra("device_type", "摄像头");
                intent.putExtra("position", position);
                startActivity(intent);
//						Intent mainIntent = new Intent(AddDeviceNewActivity.this,
//								MDeviceActivity.class);
//						startActivity(mainIntent);
            }
        });
        open_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 添加局域网设备
                popWindow.dismiss();
                Intent intent = new Intent(CameraDeviceListActivity.this,
                        AddDeviceActivity.class);
                intent.putExtra("aq_did", aq_did);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        pick_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 手动添加设备
                popWindow.dismiss();
                Intent intent = new Intent(CameraDeviceListActivity.this,
                        ManualAddDeviceActivity.class);
                intent.putExtra("aq_did", aq_did);
                intent.putExtra("position", position);
                startActivity(intent);
//				Intent mainIntent = new Intent(AddDeviceNewActivity.this,
//						ManualAddDeviceActivity.class);
//				startActivity(mainIntent);
            }
        });
        camera_cancel_tv.setOnClickListener(new View.OnClickListener() {

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


    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        try {
            swipe_layout.setRefreshing(false);
//            ptr.refreshComplete();
            closeProgressDialog();
        } catch (Exception e) {

        }
        if (entity != null) {
            if (entity.getCode() != 0) {
                try {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            if (loadingDialog != null) {
                                loadingDialog.dismiss();
                            }
                        }
                    }, 2000);
                } catch (Exception e) {

                }
                MAlert.alert(entity.getMsg());
                return;
            } else if (entity.getEventType() == UserPresenter.cameraQuery_success) {
                arrayList = (ArrayList<DeviceListBean>) entity.getData();
                refreshDeviceListTest();
            } else if (entity.getEventType() == UserPresenter.cameraQuery_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.deleteDevice_success) {
                MAlert.alert(entity.getData());
                dbManager.deleteDeviceDataByDid(mSelectDeviceInfo.getDid(), getSp(Const.UID));
                getDeviceList();
            } else if (entity.getEventType() == UserPresenter.deleteDevice_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.authDevicePwdsuccess) {
                startDeviceUI(true);
            } else if (entity.getEventType() == UserPresenter.authDevicePwdfail) {
                loadingDialog.setMessage(entity.getData() + "");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismiss();
                    }
                }, 2000);
            } else if (entity.getEventType() == UserPresenter.getdeviceinfosuccess) {
                loadingDialog.setMessage(getString(R.string.get_device_status_success));
                String psw = dbManager.queryDevicePswByDid(currentDid, getSp(Const.UID));
                if (psw.equals("")) {
                    startDeviceUI(false);
                } else {
                    userPresenter.authDevicePwd(currentDid, psw, currentType);
                }
            } else if (entity.getEventType() == UserPresenter.getdeviceinfofail) {
                MAlert.alert(entity.getData());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismiss();
                    }
                }, 2000);
            } else if (entity.getEventType() == UserPresenter.cameraUnBind_success) {
                MAlert.alert(entity.getData());
                getDeviceList();
            } else if (entity.getEventType() == UserPresenter.cameraUnBind_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    private void startDeviceUI(final boolean hasPsw) {
        if (loadingDialog != null) {
            loadingDialog.setMessage(getString(R.string.yanzheng_success));
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                }
                Intent intent = null;
                String deviceType = "chiniao_wifi_camera";
                if (deviceType.contains("chiniao_wifi_camera")) {
                    //摄像头
                    intent = VideoActivity.createIntent(CameraDeviceListActivity.this);
                } else {
                    MAlert.alert(getString(R.string.no_support_device));
                    return;
                }
//                intent.putExtra("title", mSelectDeviceInfo.getDevice_nickname());
//                intent.putExtra("did", mSelectDeviceInfo.getDid());
//                intent.putExtra("id", mSelectDeviceInfo.getId());
                if (intent != null) {
                    intent.putExtra("cameraDid", mSelectDeviceInfo.getSlave_did());
                    intent.putExtra("cameraPsw", mSelectDeviceInfo.getSlave_pwd());
                    intent.putExtra("model", mSelectDeviceInfo);
                    intent.putExtra("isMasterDevice", false);
                    intent.putExtra("hasPsw", hasPsw);//无密码则应该重新进入插入密码
                    startActivityForResult(intent, 101);
                } else {
//                    MAlert.alert("创建了多个ac");
                }

            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 102) {
            getDeviceList();
        }
    }


    @Override
    public void onRefresh() {
        getDeviceList();
    }
}
