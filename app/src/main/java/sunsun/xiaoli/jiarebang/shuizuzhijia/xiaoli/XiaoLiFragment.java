package sunsun.xiaoli.jiarebang.shuizuzhijia.xiaoli;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.bean.BannerBean;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.bean.DeviceListBean;
import com.itboye.pondteam.custom.swipemenulistview.SwipeMenu;
import com.itboye.pondteam.custom.swipemenulistview.SwipeMenuCreator;
import com.itboye.pondteam.custom.swipemenulistview.SwipeMenuItem;
import com.itboye.pondteam.custom.swipemenulistview.SwipeMenuListView;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;
import com.umeng.message.UTrack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.BuildConfig;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.PushModel;
import sunsun.xiaoli.jiarebang.custom.RatioImageView;
import sunsun.xiaoli.jiarebang.custom.VpSwipeRefreshLayout;
import sunsun.xiaoli.jiarebang.device.EditDeviceActivity;
import sunsun.xiaoli.jiarebang.device.jiarebang.DeviceJiaReBangDetailActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.AddDeviceNewActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.JinLiGangDetailActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.LoginActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.VideoActivity;
import sunsun.xiaoli.jiarebang.device.led.LEDDetailActivity;
import sunsun.xiaoli.jiarebang.device.phdevice.DevicePHDetailActivity;
import sunsun.xiaoli.jiarebang.device.pondteam.ActivityPondDeviceDetail;
import sunsun.xiaoli.jiarebang.device.pondteam.AddPondDevice;
import sunsun.xiaoli.jiarebang.device.qibeng.DeviceQiBengDetailActivity;
import sunsun.xiaoli.jiarebang.device.shuibeng.DeviceShuiBengDetailActivity;
import sunsun.xiaoli.jiarebang.device.weishiqi.WeiShiQiDetailActivity;
import sunsun.xiaoli.jiarebang.logincontroller.LoginController;
import sunsun.xiaoli.jiarebang.logincontroller.UnLoginState;
import sunsun.xiaoli.jiarebang.popwindow.SureDeleteDialog;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.web.WebActivity;
import sunsun.xiaoli.jiarebang.utils.WebUtil;
import sunsun.xiaoli.jiarebang.utils.LocationUtil;
import sunsun.xiaoli.jiarebang.utils.XGlideLoader;

import static com.itboye.pondteam.utils.Const.TAOBAO_URL;
import static com.itboye.pondteam.utils.EmptyUtil.getSp;


/**
 */
@SuppressLint("NewApi")
public class XiaoLiFragment extends LingShouBaseFragment implements Observer, SwipeRefreshLayout.OnRefreshListener, LocationUtil.OnLocationResult {
    int count = 0;
    int cnt = 0;
    App mApp;
    boolean mReadyExit;
    public SwipeMenuListView mListView;
    Context mContext;
    private ProgressDialog mProgressDialog;
    ImageView img_back;
    RelativeLayout nodata;
    // 连接进度
    private final static int SUCCESS = 0;
    int progress = 0;

    RelativeLayout relyout;
    VpSwipeRefreshLayout swipe_layout2;
    ImageView img_right;
    TextView txt_title;
    RelativeLayout re_addnew;
    TextView txt_add_jieshao;
    public String uid = "";
    UserPresenter userPresenter;
    public ArrayList<DeviceListBean> arrayList = new ArrayList<>();
    public int position;
    TextView txt_exist, txt_ceshu;
    private ProgressDialog loadingDialog;
    public DeviceListBean mSelectDeviceInfo;
    private ArrayList<HashMap<String, Object>> listItems;
    //    PtrFrameLayout ptr;
    private String currentDid, currentType;
    private SureDeleteDialog loadingDialogDelete;
    PushModel ummessage;
    VpSwipeRefreshLayout swipe_layout;
    public String extra;
    public int warnValue;
    View footerView;
    private SimpleAdapter listItemsAdapter;
    private DeviceDetailModel deviceDetailModel;

    View viewHeader;
    private RatioImageView ratioImageView;
    private LocationUtil locationUtil;
    private double lat;
    private double lng;
    private ArrayList<BannerBean> bannerBeanArrayList;

    public void showPushMessage() {
        mApp.showPushMessage(getActivity(), ummessage);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDeviceList();
    }

    private void initSwipListView() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // TODO Auto-generated method stub
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setTitle(getString(R.string.delete));
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
            public void onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
//					ShopCarBean model=new ShopCarBean();
//					model.setId(dataList.getList().get(position).getId());
//					carPresenter.delete(model);
//                        MAlert.alert("删除了");
                        // open
//					open(item);
                        //删除设备操作
                        loadingDialogDelete = new SureDeleteDialog(getActivity(), getString(R.string.tips), getString(R.string.make_sure_delete), getString(R.string.cancel), getString(R.string.ok), 0);
                        loadingDialogDelete.setCanceledOnTouchOutside(false);
                        loadingDialogDelete.show();
                        loadingDialogDelete.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadingDialogDelete.setMessage(getString(R.string.delete_ing));
                                loadingDialogDelete.getButton(DialogInterface.BUTTON_POSITIVE).setVisibility(View.GONE);
                                mSelectDeviceInfo = arrayList.get(position);
                                userPresenter.deleteDevice(arrayList.get(position).getId(), getSp(Const.UID));
                            }
                        });
                        loadingDialogDelete.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadingDialogDelete.dismiss();
                            }
                        });

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
        userPresenter.getMyDeviceList(uid);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

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


    @SuppressLint("WrongConstant")

    public void refreshDeviceListTest() {

        ArrayList<HashMap<String, Object>> listItemsTemp = new ArrayList<>();
        mListView.setVisibility(View.VISIBLE);
        relyout.setVisibility(View.VISIBLE);
        for (int i = 0; i < arrayList.size(); i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemName", arrayList.get(i).getDevice_nickname());
            map.put("ItemDid", "DID:" + arrayList.get(i).getDid());
            map.put("ItemState1", "");
            map.put("ItemState2", "");
            map.put("extra", arrayList.get(i).getExtra());
            map.put("type", arrayList.get(i).getDevice_type());
            map.put("ItemRightArrow", R.drawable.ic_right_arrow);
            if (!BuildConfig.APP_TYPE.equals("pondTeam")) {
                if (arrayList.get(i).getDid().startsWith("S01")) {
                    //过滤桶
                    map.put("ItemIcon", arrayList.get(i).getIs_disconnect() == 0 ? R.drawable.device_chitangguolv : R.drawable.off_pondteam);
                } else if (arrayList.get(i).getDevice_type().startsWith("S02")) {
                    //加热棒
                    map.put("ItemIcon", arrayList.get(i).getIs_disconnect() == 0 ? R.drawable.device_jiarebang : R.drawable.off_jiarebang);
                } else if (arrayList.get(i).getDevice_type().equalsIgnoreCase("S03")) {
                    //AQ806
                    map.put("ItemIcon", arrayList.get(i).getIs_disconnect() == 0 ? R.drawable.device_aq : R.drawable.off_aq806);
                } else if (arrayList.get(i).getDevice_type().equalsIgnoreCase("S03-1")) {
                    //AQ500
                    map.put("ItemIcon", arrayList.get(i).getIs_disconnect() == 0 ? R.drawable.device_500 : R.drawable.off_aq500);
                } else if (arrayList.get(i).getDevice_type().equalsIgnoreCase("S03-2")) {
                    //AQ700
                    map.put("ItemIcon", arrayList.get(i).getIs_disconnect() == 0 ? R.drawable.device_700 : R.drawable.off_aq700);
                } else if (arrayList.get(i).getDevice_type().equalsIgnoreCase("S03-3")) {
                    //AQ600
                    map.put("ItemIcon", arrayList.get(i).getIs_disconnect() == 0 ? R.drawable.device_600 : R.drawable.off_aq600);
                } else if (arrayList.get(i).getDevice_type().startsWith("S04")) {
                    map.put("ItemIcon", arrayList.get(i).getIs_disconnect() == 0 ? R.drawable.device_ph : R.drawable.off_ph);
                } else if (arrayList.get(i).getDevice_type().startsWith("S05")) {
                    map.put("ItemIcon", arrayList.get(i).getIs_disconnect() == 0 ? R.drawable.device_shuibeng : R.drawable.off_shuibeng);
                } else if (arrayList.get(i).getDevice_type().startsWith("S06")) {
                    map.put("ItemIcon", arrayList.get(i).getIs_disconnect() == 0 ? R.drawable.device_shuizudeng : R.drawable.off_led);
                } else if (arrayList.get(i).getDevice_type().equalsIgnoreCase("S07")) {
                    map.put("ItemIcon", arrayList.get(i).getIs_disconnect() == 0 ? R.drawable.device_jiaozhiliubeng : R.drawable.off_cp1000);
                } else if (arrayList.get(i).getDevice_type().startsWith("chiniao_wifi_camera")) {
                    map.put("ItemIcon", R.drawable.device_shexiangtou);
                } else if (arrayList.get(i).getDevice_type().startsWith("S08")) {
                    map.put("ItemIcon", arrayList.get(i).getIs_disconnect() == 0 ? R.drawable.device_118 : R.drawable.off_aq118);
                } else {
                    map.put("ItemIcon", R.drawable.ic_aplacher);
                }
            } else {
                map.put("ItemIcon", R.drawable.icon);
            }
            listItemsTemp.add(map);
        }
        listItems.clear();
        listItems.addAll(listItemsTemp);
        listItemsAdapter.notifyDataSetChanged();
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

        if (mApp.phJiaoZhunUI != null) {
            //更新APH校准时间
            mApp.phJiaoZhunUI.setJiaoZhunTimes();
        }
    }


    Button btn_addnew;

    @Override
    public void onClick(View v) {
        Intent intent = null;
        AlertDialog.Builder alert = null;
        switch (v.getId()) {
            case R.id.txt_title:
                Intent intentWeb = new Intent(getActivity(), WebActivity.class);
                intentWeb.putExtra("title", "ConfigInfo");
                intentWeb.putExtra("url", "file:///android_asset/html/config_detail.html");
                startActivity(intentWeb);
                break;
            case R.id.txt_ceshu:
                intentWeb = new Intent(getActivity(), WebActivity.class);
                intentWeb.putExtra("title", getString(R.string.velocity));
                intentWeb.putExtra("url", "http://" + Const.xiaoli_wrapUrl + "/web.php/net/index.html");
                startActivity(intentWeb);
                break;
            case R.id.img_right:
            case R.id.btn_addnew:
                if (BuildConfig.APP_TYPE.equals("pondTeam")) {
                    intent = new Intent(getActivity(), AddPondDevice.class);
                    intent.putExtra("device_type", "S01");
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), AddDeviceNewActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.txt_exist:
                alert = new AlertDialog.Builder(getActivity());
                alert.setMessage(getString(R.string.make_sure_exit));
                alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUserInfo();
                    }
                });
                alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        alert.
                    }
                });
                alert.create();
                alert.show();
                break;
            case R.id.img_header:
                gotoTaoBao();
                break;
        }
    }

    private void gotoTaoBao() {
        if (bannerBeanArrayList == null) {
//            MAlert.alert("缺失商品id");
            return;
        }
        if (bannerBeanArrayList.size() <= 0) {
//            MAlert.alert("没有s");
            return;
        }
        if (lng == 0 || lat == 0) {
//            MAlert.alert("没有s");
            return;
        }
        String url = "";
        String urlType = bannerBeanArrayList.get(0).getUrl_type();
        if (urlType.equals("6071")) {
            if (!bannerBeanArrayList.get(0).getUrl().equals("")) {
                url = String.format(TAOBAO_URL, lng, lat, bannerBeanArrayList.get(0).getUrl());
                WebUtil.startActivityForTaoBao(getActivity(), url);
            }
        } else if (urlType.equals("6070")) {
            url = bannerBeanArrayList.get(0).getUrl();
            WebUtil.startActivityForUrl(getActivity(), url, "详情");
        }
    }

    private void deleteUserInfo() {
        mApp.getInstance().mPushAgent.removeAlias(getSp(Const.UID), BuildConfig.UMENG_ALIAS, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean b, String s) {
                System.out.println(b + "  222  " + s);
            }
        });
        SPUtils.put(getActivity(), null, Const.UID, "");
        SPUtils.put(getActivity(), null, Const.EMAIL, "");
        SPUtils.put(getActivity(), null, Const.PaySecret, "");
        SPUtils.put(getActivity(), null, Const.USERNAME, "");
        SPUtils.put(getActivity(), null, Const.PASSWORD, "");
        SPUtils.put(getActivity(), null, Const.MOBILE, "");
        SPUtils.put(getActivity(), null, Const.IS_LOGINED, false);
        SPUtils.put(getActivity(), null, Const.HEAD, "");
        SPUtils.put(getActivity(), null, Const.NICK, "");
        SPUtils.put(getActivity(), null, Const.USER_DEVICE_NUMBER, "");
//        SPUtils.put(this, null, Const.S_ID, "");
        LoginController.setLoginState(new UnLoginState());
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        try {
            //停止刷新
            swipe_layout.setRefreshing(false);
//            swipe_layout2.setRefreshing(false);
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
                                setDialoadDismiss(loadingDialog);
                            }
                            if (loadingDialogDelete != null) {
                                loadingDialogDelete.dismiss();
                            }
                        }
                    }, 2000);
                } catch (Exception e) {

                }
                MAlert.alert(entity.getMsg());
                return;
            } else if (entity.getEventType() == UserPresenter.getMyDeviceList_success) {
                arrayList = (ArrayList<DeviceListBean>) entity.getData();
                refreshDeviceListTest();
            } else if (entity.getEventType() == UserPresenter.getMyDeviceList_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.deleteDevice_success) {
                loadingDialogDelete.setMessage(entity.getData() + "");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialogDelete.dismiss();
                    }
                }, 2000);
                getDeviceList();
            } else if (entity.getEventType() == UserPresenter.deleteDevice_fail) {
                loadingDialogDelete.setMessage(entity.getData() + "");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialogDelete.dismiss();
                    }
                }, 2000);
            } else if (entity.getEventType() == UserPresenter.authDevicePwdsuccess) {
                startDeviceUI(true);
            } else if (entity.getEventType() == UserPresenter.authDevicePwdfail) {
                loadingDialog.setMessage(entity.getData() + "");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setDialoadDismiss(loadingDialog);
                    }
                }, 2000);
            } else if (entity.getEventType() == UserPresenter.getdeviceinfosuccess) {
                deviceDetailModel = (DeviceDetailModel) entity.getData();
                if (deviceDetailModel == null) {

                    loadingDialog.setMessage(getString(R.string.get_device_status_fail));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setDialoadDismiss(loadingDialog);
                        }
                    }, 2000);
                    return;
                } else {
                    loadingDialog.setMessage(getString(R.string.get_device_status_success));
                    if (!deviceDetailModel.getIs_disconnect().equals("0")) {
                        loadingDialog.setMessage(getString(R.string.device) + getString(R.string.offline));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setDialoadDismiss(loadingDialog);
                            }
                        }, 2000);
                        return;
                    }
                }
                String psw = "sunsun123456";
                try {
                    JSONObject jsonObject = new JSONObject(mSelectDeviceInfo.getExtra());
                    psw = jsonObject.getString("pwd");
                    if (psw.equals("")) {
                        psw = "sunsun123456";
                    }
                } catch (JSONException e) {
                    psw = "sunsun123456";
                }
                System.out.println("设备密码" + psw);
                userPresenter.authDevicePwd(currentDid, "sunsun123456", currentType);
            } else if (entity.getEventType() == UserPresenter.getdeviceinfofail) {
                MAlert.alert(entity.getData());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setDialoadDismiss(loadingDialog);
                    }
                }, 2000);
            } else if (entity.getEventType() == UserPresenter.getBanners_success) {
                bannerBeanArrayList = (ArrayList<BannerBean>) entity.getData();
                XGlideLoader.displayImage(getActivity(), Const.imgSunsunUrl + bannerBeanArrayList.get(0).getImg(), ratioImageView);
//                if (!bannerBeanArrayList.get(0).getUrl().equals("")) {
//                    ratioImageView.setTag(R.id.tag_first, url);
//
//                }
                ratioImageView.setTag(R.id.tag_second, bannerBeanArrayList.get(0).getUrl_type());
                ratioImageView.setOnClickListener(this);
            } else if (entity.getEventType() == UserPresenter.getBanners_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    private void setDialoadDismiss(ProgressDialog loadingDialog) {
        try {
            loadingDialog.dismiss();
        } catch (Exception e) {

        }
    }

    private void startDeviceUI(final boolean hasPsw) {
        loadingDialog.setMessage(getString(R.string.yanzheng_success));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setDialoadDismiss(loadingDialog);
                Intent intent = null;
                String deviceType = mSelectDeviceInfo.getDevice_type();
                if (deviceType.contains("S02")) {
                    //加热棒
                    intent = new Intent(getActivity(), DeviceJiaReBangDetailActivity.class);
                } else if (deviceType.contains("S01")) {
                    //过滤桶
                    intent = new Intent(getActivity(), ActivityPondDeviceDetail.class);
                } else if (deviceType.contains("S03")) {
                    //806
                    intent = new Intent(getActivity(), JinLiGangDetailActivity.class);
                } else if (deviceType.contains("S04")) {
                    //aph
                    intent = new Intent(getActivity(), DevicePHDetailActivity.class);
                } else if (deviceType.contains("S05")) {
                    //变频水泵
                    intent = new Intent(getActivity(), DeviceShuiBengDetailActivity.class);
                } else if (deviceType.contains("S06")) {
                    //Led水族灯
                    intent = new Intent(getActivity(), LEDDetailActivity.class);
                } else if (deviceType.contains("S07")) {
                    //CP1000
                    intent = new Intent(getActivity(), DeviceQiBengDetailActivity.class);
                } else if (deviceType.contains("S08")) {
                    //变频水泵
                    intent = new Intent(getActivity(), DeviceShuiBengDetailActivity.class);
                } else if (deviceType.contains("S09")) {
                    //变频水泵
                    intent = new Intent(getActivity(), DeviceShuiBengDetailActivity.class);
                } else if (deviceType.contains("chiniao_wifi_camera")) {
                    //摄像头
                    intent = new Intent(getActivity(), VideoActivity.class);
                } else {
                    MAlert.alert(getString(R.string.no_support_device));
                    return;
                }
                intent.putExtra("title", mSelectDeviceInfo.getDevice_nickname());
                intent.putExtra("did", mSelectDeviceInfo.getDid());
                intent.putExtra("id", mSelectDeviceInfo.getId());
                intent.putExtra("hasPsw", hasPsw);//无密码则应该重新进入插入密码
                intent.putExtra("detailModel", deviceDetailModel);
                startActivityForResult(intent, 101);
            }
        }, 2000);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 101 && resultCode == 102) {
//            getDeviceList();
//        }
//    }

    @Override
    public void onRefresh() {
        getDeviceList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device;
    }

    @Override
    protected void initData() {
        mApp = (App) getActivity().getApplication();
        mApp.mXiaoLiUi = this;
        txt_ceshu.setVisibility(View.VISIBLE);
        footerView = LayoutInflater.from(getActivity()).inflate(R.layout.device_list_footer, null);
        nodata = (RelativeLayout) footerView.findViewById(R.id.nodata);
        btn_addnew = (Button) footerView.findViewById(R.id.btn_addnew);
        btn_addnew.setOnClickListener(this);

        locationUtil = new LocationUtil(getActivity(), this);
        txt_add_jieshao = (TextView) footerView.findViewById(R.id.txt_add_jieshao);
        viewHeader = View.inflate(getActivity(), R.layout.imageview, null);
        ratioImageView = (RatioImageView) viewHeader.findViewById(R.id.img_header);
        ummessage = (PushModel) getActivity().getIntent().getSerializableExtra("ummessage");
        if (ummessage != null) {
            showPushMessage();
        }
        img_right.setBackgroundResource(R.drawable.menu);
        txt_title.setText(getString(R.string.myadvice));
        txt_title.setTextColor(getResources().getColor(R.color.main_green));
        userPresenter = new UserPresenter(this);
        uid = getSp(Const.UID);
        //设置向下拉多少出现刷新
        swipe_layout.setDistanceToTriggerSync(150);
        swipe_layout.setColorSchemeColors(getResources().getColor(R.color.main_green));
        swipe_layout.setOnRefreshListener(this);
        swipe_layout.setRefreshing(true);
        listItems = new ArrayList<>();
        listItemsAdapter = new SimpleAdapter(getActivity(), listItems,
                R.layout.device_item, new String[]{"ItemName", "ItemDid",
                "ItemState1", "ItemState2", "ItemRightArrow", "ItemIcon"},
                new int[]{R.id.textView_dev_name, R.id.textView_dev_did,
                        R.id.textView_dev_state1, R.id.textView_dev_state2,
                        R.id.imageView_dev_right_arrow, R.id.imageView_dev_logo});
        mListView.addHeaderView(viewHeader);
        mListView.addFooterView(footerView);
        mListView.setAdapter(listItemsAdapter);
        img_back.setVisibility(View.GONE);
//        txt_exist.setText(getString(R.string.exist_login));
        txt_exist.setVisibility(View.GONE);
        txt_ceshu.setVisibility(View.GONE);
        userPresenter.getBanners(Const.XIAOLI_TOP_BANNER_POSITION);
        initSwipListView();
        mContext = getActivity();
        mReadyExit = false;
        String htmlText = getString(com.itboye.pondteam.R.string.click) + "<img src='" + com.itboye.pondteam.R.drawable.add_small + "'>" + getString(com.itboye.pondteam.R.string.tips_detail);
        txt_add_jieshao.setText(Html.fromHtml(htmlText, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                int id = Integer.parseInt(source);
                Drawable drawable = ContextCompat.getDrawable(getActivity(), id);//getResoueces.getDrawable(id,null)已经过时
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                return drawable;
            }
        }, null));
        mProgressDialog = new ProgressDialog(getActivity());

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
        //列表项短按键处理
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                XiaoLiFragment.this.position = position - 1;
                mSelectDeviceInfo = arrayList.get(position - 1);
                if (!mSelectDeviceInfo.getDevice_type().equals("chiniao_wifi_camera") && mSelectDeviceInfo.getIs_disconnect() != 0) {
                    MAlert.alert(getString(R.string.connect_device_offline));
                    return;
                }
                currentDid = listItems.get(position - 1).get("ItemDid").toString().substring(4, listItems.get(position - 1).get("ItemDid").toString().length());
                currentType = listItems.get(position - 1).get("type").toString();
                extra = listItems.get(position - 1).get("extra").toString();
                loadingDialog = new ProgressDialog(getActivity());
                if (currentDid.startsWith("S08")) {
                    startActivity(new Intent(getActivity(), WeiShiQiDetailActivity.class).putExtra("id", mSelectDeviceInfo.getId()).putExtra("did", currentDid));
                    return;
                }
                if (!BuildConfig.APP_TYPE.equals("pondTeam")) {
                    if (!currentDid.toLowerCase().startsWith("SCHD".toLowerCase())) {
//                        loadingDialog.setTitle(getString(R.string.tips));
                        loadingDialog.setMessage(getString(R.string.get_deviceInfoing));
                        loadingDialog.setCanceledOnTouchOutside(false);
                        loadingDialog.show();
                        userPresenter.getDeviceDetailInfo(currentDid, getSp((Const.UID)));
                    } else {
                        String psw = "";
                        try {
                            JSONObject jsonObject = new JSONObject(extra);
                            psw = jsonObject.getString("pwd");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(getActivity(), VideoActivity.class);
                        intent.putExtra("cameraDid", currentDid);
                        intent.putExtra("cameraPsw", psw);
                        intent.putExtra("isMasterDevice", true);
                        intent.putExtra("model", mSelectDeviceInfo);
                        startActivity(intent);
                    }
                } else {
                    if (currentDid.startsWith("S01")) {
//                        loadingDialog.setTitle(getString(R.string.tips));
                        loadingDialog.setMessage(getString(R.string.get_deviceInfoing));
                        loadingDialog.setCanceledOnTouchOutside(false);
                        loadingDialog.show();
                        userPresenter.getDeviceDetailInfo(currentDid, getSp((Const.UID)));
                    } else {
                        MAlert.alert(getString(R.string.no_support_device));
                    }
                }
            }

        });

        // 列表项长按键处理
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view, final int position, long id) {
                mSelectDeviceInfo = arrayList.get(position - 1);
                mApp.mEditDeviceInfo = mSelectDeviceInfo;
                Intent mainIntent = new Intent(getActivity(),
                        EditDeviceActivity.class);
                mainIntent.putExtra("id", mSelectDeviceInfo.getId());
                mainIntent.putExtra("type", mSelectDeviceInfo.getDevice_type());
                getActivity().startActivity(mainIntent);
                return true;
            }
        });
    }

    @Override
    public void getLatAndLng(String provinceName, String cityName, double lat, double lng, String areaName) {
        this.lat = lat;
        this.lng = lng;
//        String url=String.format(TAOBAO_URL,lng,lat,bannerBeanArrayList.get(0).getUrl());
//        ratioImageView.setTag(R.id.tag_first, url);
    }
}
