package sunsun.xiaoli.jiarebang.sunsunlingshou.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.bean.ArticalBean;
import com.itboye.pondteam.bean.BannerBean;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.AddressBean;
import sunsun.xiaoli.jiarebang.beans.GoodsListBean;
import sunsun.xiaoli.jiarebang.beans.StoreListBean;
import sunsun.xiaoli.jiarebang.custom.RatioImageView;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;
import sunsun.xiaoli.jiarebang.logincontroller.LoginController;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.ArticleActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.GoodsClassifyActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.RedBagAcitivty;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.LingShouSwitchLoginOrRegisterActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.LunBoHelper;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.CarouselView;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentScrollView;
import sunsun.xiaoli.jiarebang.utils.GotoTaoBaoUtil;
import sunsun.xiaoli.jiarebang.utils.LocationUtil;
import sunsun.xiaoli.jiarebang.utils.Util;
import sunsun.xiaoli.jiarebang.utils.XGlideLoader;

import static com.itboye.pondteam.utils.Const.CITY_CODE;
import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static com.itboye.pondteam.utils.ScreenUtil.getDimension;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitleBarStyle2;
import static sunsun.xiaoli.jiarebang.utils.Util.queryDistrictNo;


/**
 * 首页
 */
public class HomeFragmentVersionNew extends LingShouBaseFragment implements TranslucentScrollView.TranslucentChangedListener, IRecycleviewClick, Observer, LocationUtil.OnLocationResult {

    private TranslucentActionBar actionBar;//可渐变的标题栏
    private TranslucentScrollView pullzoom_scrollview;//添加滑动监听的滑动组件
    ViewFlipper vf_home;
    CarouselView home_carouseview;
    Button btn_yuyue, btn_qingli, btn_goumai, btn_zaojingzhuangshi;
    TextView ed_actionbar_search;
    RelativeLayout area_left, area_right, area_center, bottom_area_left, bottom_area_right;
    //    LinearLayout near_store;
    ArrayList<String> arrayList = new ArrayList<>();
    @IsNeedClick
    TextView txt_center, txt_left, txt_right, txt_bottom_left, txt_bottom_right;
    //    MyGridView recycler_aqhardwareorhotgoods;
    Button haoping, zuijin;
    ArrayList<Integer> aqType = new ArrayList<>();
    LingShouPresenter lingShouPresenter;
    private int pageIndex = 1;
    private StoreListBean bean;
    App app;
    RelativeLayout lay_actionbar_left;
    int position = 6079;
    RelativeLayout lay_actionbar_right;
    @IsNeedClick
    TextView tv_actionbar_left;
    ImageView testImg;
    private GoodsListBean goodsList;
    //    ProgressBar progress;
    ArticalBean articalBean;
    RatioImageView home_img;
    Dialog alert;
    RadioButton tvTitle, tvMessage;
    private StoreListBean.ListEntity listEntity1;
    //    RelativeLayout store_fenlei;
    FrameLayout tab_content;

    public MyTabFragment myTabFragment1;
    MyTabFragment myTabFragment2;
    MyTabFragment myTabFragment3;
    private FragmentManager fragmentManager;
    private String uid;
    private String selectAddress;
    private AddressBean selectAddressBean;
    private BroadcastReceiver receiver;
    public int index = 2;
    public LocationUtil locationUtil;
    private FragmentTransaction transaction;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_version_new;
    }

    private SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
            //这里我们拿到回掉回来的bitmap，可以加载到我们想使用到的地方
//            testImg.setImageBitmap((bitmap));
        }
    };

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (myTabFragment1 != null) {
            transaction.hide(myTabFragment1);
        }
        if (myTabFragment2 != null) {
            transaction.hide(myTabFragment2);
        }
        if (myTabFragment3 != null) {
            transaction.hide(myTabFragment3);
        }
    }

    static HomeFragmentVersionNew myHomeFragment;

    public static HomeFragmentVersionNew getHomeFragment() {
        return myHomeFragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    public void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        // 开启一个Fragment事务
        transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);

        switch (index) {
            case 0://商品
                if (myTabFragment3 == null) {
                    //如果HomeFragment为空，则创建一个添加到界面
                    myTabFragment3 = new MyTabFragment(index);
                    transaction.add(R.id.tab_content, myTabFragment3);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(myTabFragment3);
                }
                break;
            case 1://硬件
                if (myTabFragment2 == null) {
                    //如果HomeFragment为空，则创建一个添加到界面
                    myTabFragment2 = new MyTabFragment(index);
                    transaction.add(R.id.tab_content, myTabFragment2);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(myTabFragment2);
                }
                break;
            case 2://附近商家
                if (myTabFragment1 == null) {
                    //如果HomeFragment为空，则创建一个添加到界面
                    myTabFragment1 = new MyTabFragment(index);
                    transaction.add(R.id.tab_content, myTabFragment1);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(myTabFragment1);
                }
                break;
        }
        transaction.commit();
    }


    @Override
    protected void initData() {
        myHomeFragment = this;
        hasRe = false;
        locationUtil = new LocationUtil(getActivity(), this);
        fragmentManager = getChildFragmentManager();
//        Glide.with(getActivity()).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503478934176&di=45c483741d5b9e90c51e2d5a77cd85ba&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D2183734545c2d562e605d8ae8f78fa9a%2F8435e5dde71190efee7d4ffac41b9d16fdfa6015.jpg").into(testImg);
//        Glide.with(getActivity()).load("https://www.baidu.com/img/bdlogo.png").transform(new GlideRoundTransform(getActivity(),10)).into(testImg);
        app = (App) getActivity().getApplication();
        app.homeFragmentVersionNew = this;
        initTabLayout();
        lingShouPresenter = new LingShouPresenter(this);
        lingShouPresenter.getBanner(position);//获取轮播图
        initTitleBarStyle2(getActivity(), actionBar, "", pullzoom_scrollview, this, null);
        //开启渐变
        actionBar.setNeedTranslucent(true, false);
        actionBar.setSearchBarVisible(false);
        setSelectArea("硬件");
        //注册地址改变广播
        registerBroadcast();
        //获取默认收货地址
        uid = getSp(Const.UID);
//        setSelectAddress();
    }

    private void initTabLayout() {
        setTextStyle(txt_center, "智能设备", R.drawable.circle_blue, area_center, "硬件", R.drawable.yingjian);
        setTextStyle(txt_left, "新品推荐", R.drawable.rect_pink, area_left, "商品", R.drawable.shangjia);//新品推荐
        setTextStyle(txt_right, "线上连锁", R.drawable.rect_purple, area_right, "连锁", R.drawable.shangpin);//线上连锁
        setTextStyle(txt_bottom_left, "门店查询", R.drawable.rect_orange, bottom_area_left, "商家", R.drawable.mendianchaxun);//门店查询
        setTextStyle(txt_bottom_right, "水族论坛", R.drawable.rect_green, bottom_area_right, "论坛", R.drawable.shuizuluntan);//水族论坛
    }

    @Override
    public void onResume() {
        super.onResume();
        //是否领取过优惠券
        setYouHuiQuan();
    }

    private void setYouHuiQuan() {
        String hasCharge = getSp(Const.HAS_CHARGE);
        if (hasCharge.equals("0")) {
            //没有领取过
            home_img.setBackgroundResource(R.drawable.home_nocharge);
        } else if (hasCharge.equals("")) {
            home_img.setBackgroundResource(R.drawable.home_charge);
        } else {
            //领取过
            home_img.setBackgroundResource(R.drawable.home_charge);
        }
        home_img.setTag(hasCharge);
    }


    private void registerBroadcast() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Const.YOUHUIQUAN_CHANGE)) {
                    setYouHuiQuan();
                } else {
                    setSelectAddress();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(Const.ADDRESS_CHANGE);
        intentFilter.addAction(Const.YOUHUIQUAN_CHANGE);
        getActivity().registerReceiver(receiver, intentFilter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
//        myTabFragment1=null;
//        myTabFragment2=null;
//        myTabFragment3=null;
    }

    /**
     * 1、用户选择地址了，设置为改变地址后的city
     * 2、没登录显示定位的城市
     * 3、没选择城市选择默认地址的城市
     * 4、没添加地址选择定位城市
     */
    public void setSelectAddress() {
        uid = getSp(Const.UID);
        if (uid.equals("")) {
            //没登录 用定位到的城市
            if (getSp(Const.LNG).equals("") || getSp(Const.LAT).equals("")) {
                return;
            }
            setCityName("", getSp(Const.CITY), Double.parseDouble(getSp(Const.LNG)), Double.parseDouble(getSp(Const.LAT)), "");
        } else {
            selectAddress = getSp(Const.SELECT_ADDRESS);
            if (!selectAddress.equals("")) {
                //用户选择了地址
                selectAddressBean = new Gson().fromJson(selectAddress, AddressBean.class);
                if (selectAddressBean != null) {
                    //获取到默认地址
                    setCityName(selectAddressBean.getCityid(), selectAddressBean.getCity(), Double.parseDouble(selectAddressBean.getLat()), Double.parseDouble(selectAddressBean.getLng()), "");
                } else {
                    //没获取到默认地址
                    setCityName("", getSp(Const.CITY), Double.parseDouble(getSp(Const.LNG)), Double.parseDouble(getSp(Const.LAT)), "");
                }
            } else {
                //用戶沒有選擇地址就獲取默認地址
                lingShouPresenter.getDefaultAddress(uid, getSp(Const.S_ID));
//                setCityName(getSp(Const.CITY), Double.parseDouble(getSp(Const.LNG)), Double.parseDouble(getSp(Const.LAT)));
            }
        }

    }

    boolean hasRe = false;

    public void setCityName(String cityId, final String cityName, double lat, double lng, String area) {
        if (hasRe == false) {
            getStore(cityId, cityName, lat, lng);
            getVerticalArt(area, lat, lng);
            hasRe = true;
        }

    }

    private void getVerticalArt(String area, double lat, double lng) {
        String areaNo = queryDistrictNo(area);
        SPUtils.put(getActivity(), null, Const.AREA_CODE, areaNo + "");
        lingShouPresenter.getVerticalArtical(areaNo, lat + "", lng + "");//获取垂直滚动文章
    }

    private void getStore(String cityId, final String cityName, double lat, double lng) {
        String cityNo = cityId;
        if ("".equals(cityId)) {
            cityNo = Util.queryCityNo(cityName);
        }
        tv_actionbar_left.setText(cityName);
        SPUtils.put(getActivity(), null, Const.LNG, lng + "");
        SPUtils.put(getActivity(), null, Const.LAT, lat + "");
        SPUtils.put(getActivity(), null, Const.CITY_CODE, cityNo);
        if (index == 2) {
            myTabFragment1.getNearStore();
            //通知咨询购买Fragment更新数据
            Intent intent = new Intent(Const.STORE_CHANGE);
            getActivity().sendBroadcast(intent);
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_yuyue:
                if (getSp(Const.UID).equals("")) {
                    startActivity(new Intent(getActivity(), LingShouSwitchLoginOrRegisterActivity.class));
                    return;
                }
                GotoTaoBaoUtil.startActivity(getActivity(), Const.TAOBAO_TEST_URL);
//                startActivity(new Intent(getActivity(), AppointmentInstallStepOneActivity.class).putExtra("type", 5));
                break;
            case R.id.btn_qingli:
                if (getSp(Const.UID).equals("")) {
                    startActivity(new Intent(getActivity(), LingShouSwitchLoginOrRegisterActivity.class));
                    return;
                }
                GotoTaoBaoUtil.startActivity(getActivity(), Const.TAOBAO_TEST_URL);
//                intent = new Intent(getActivity(), YuGangCleanOrHuoTiBuyStepOneActivity.class);
//                intent.putExtra("type", 3);
//                intent.putExtra("title", "鱼缸清理");
//                startActivity(intent);
                break;
            case R.id.ed_actionbar_search:
                startActivity(new Intent(getActivity(), GoodsClassifyActivity.class));
                break;
            case R.id.btn_goumai:
                if (getSp(Const.UID).equals("")) {
                    startActivity(new Intent(getActivity(), LingShouSwitchLoginOrRegisterActivity.class));
                    return;
                }
//                intent = new Intent(getActivity(), YuGangCleanOrHuoTiBuyStepOneActivity.class);
//                intent.putExtra("title", "活体购买");
//                intent.putExtra("type", 2);
//                startActivity(intent);
                GotoTaoBaoUtil.startActivity(getActivity(), Const.TAOBAO_TEST_URL);
                break;
            case R.id.btn_zaojingzhuangshi:
                if (getSp(Const.UID).equals("")) {
                    startActivity(new Intent(getActivity(), LingShouSwitchLoginOrRegisterActivity.class));
                    return;
                }

                GotoTaoBaoUtil.startActivity(getActivity(), Const.TAOBAO_TEST_URL);
//                intent = new Intent(getActivity(), YuGangCleanOrHuoTiBuyStepOneActivity.class);
//                intent.putExtra("title", "造景装饰");
//                intent.putExtra("type", 4);
//                startActivity(intent);
                break;
            case R.id.home_img:
                if (getSp(Const.UID).equals("")) {
                    startActivity(new Intent(getActivity(), LingShouSwitchLoginOrRegisterActivity.class));
                    return;
                }
                if (home_img.getTag().equals("1") || home_img.getTag().equals("")) {

                } else {
                    startActivity(new Intent(getActivity(), RedBagAcitivty.class));
                }
                break;
            case R.id.area_left:
            case R.id.area_right:
            case R.id.area_center:
            case R.id.bottom_area_left:
            case R.id.bottom_area_right:
                setSelectArea(v.getTag());
                break;
            case R.id.haoping:
                haoping.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_border_green_bg_green));
                haoping.setTextColor(getActivity().getResources().getColor(R.color.white));
                zuijin.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_border_green_bg_white));
                zuijin.setTextColor(getActivity().getResources().getColor(R.color.blue500));
                break;
            case R.id.zuijin:
                zuijin.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_border_blue_bg_blue));
                zuijin.setTextColor(getActivity().getResources().getColor(R.color.white));
                haoping.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_border_blue_bg_white));
                haoping.setTextColor(getActivity().getResources().getColor(R.color.blue500));
                break;
            case R.id.lay_actionbar_left:
                LoginController.goToQueryAddress(getActivity(), null);
                break;
            case R.id.lay_actionbar_right:
                LoginController.goToMessageList(getActivity(), null);
                break;
            case R.id.vf_home:
                int id = vf_home.getDisplayedChild();
                ArticalBean.ListEntity listEntity = articalBean.getList().get(id);
                Intent intent1 = new Intent(getActivity(), ArticleActivity.class);
                intent1.putExtra("ListEntity", listEntity);
                startActivity(intent1);
                break;

        }
    }

    String t;

    private void setSelectArea(Object tag) {
        //添加智能设备---->附近商家---->热门商品
//        progress.setVisibility(View.VISIBLE);
//        recycler_aqhardwareorhotgoods.setVisibility(View.GONE);
//        store_fenlei.setVisibility(View.GONE);
        t = tag + "";
        index = 0;
        if (t.equals("商品")) {
//            recycler_aqhardwareorhotgoods.setVerticalSpacing(0);
//            recycler_aqhardwareorhotgoods.setNumColumns(2);
//            lingShouPresenter.getHotSearchGoods();
//            setTextStyle(txt_center, "热门商品", R.drawable.circle_yellow, area_center, "商品", R.drawable.shangpin);
//            setTextStyle(txt_left, "附近商家", R.drawable.rect_green, area_left, "商家", R.drawable.shangjia);
//            setTextStyle(txt_right, "智能设备", R.drawable.rect_blue, area_right, "硬件", R.drawable.yingjian);
            index = 0;
            setTabSelection(index);
        } else if (t.equals("硬件")) {
//            recycler_aqhardwareorhotgoods.setVerticalSpacing(0);
//            recycler_aqhardwareorhotgoods.setNumColumns(3);
//            progress.setVisibility(View.GONE);
//            recycler_aqhardwareorhotgoods.setVisibility(View.VISIBLE);
//            setTextStyle(txt_center, "智能设备", R.drawable.circle_blue, area_center, "硬件", R.drawable.yingjian);
//            setTextStyle(txt_left, "热门商品", R.drawable.rect_yellow, area_left, "商品", R.drawable.shangpin);
//            setTextStyle(txt_right, "附近商家", R.drawable.rect_green, area_right, "商家", R.drawable.shangjia);
//            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
//            HomeDeivcesAdapter adapter = new HomeDeivcesAdapter(getActivity(), arDevice, R.layout.item_lingshou_device);
//            HomeDeviceAdapter adapter = new HomeDeviceAdapter(getActivity(), arDevice, R.layout.item_lingshou_device);
            index = 1;
            setTabSelection(index);
//            recycler_aqhardwareorhotgoods.setAdapter(adapter);
        } else if (t.equals("商家")) {
//            recycler_aqhardwareorhotgoods.setVerticalSpacing(getDimension(getActivity(),20));
//            recycler_aqhardwareorhotgoods.setNumColumns(1);
//            store_fenlei.setVisibility(View.VISIBLE);
//            lingShouPresenter.getNearStore("330100", 120.377819 + "", 120.377819 + "", "", "", pageIndex, 10);
//            near_store.setVisibility(View.VISIBLE);
//            setTextStyle(txt_center, "附近商家", R.drawable.circle_green, area_center, "商家", R.drawable.shangjia);
//            setTextStyle(txt_left, "智能设备", R.drawable.rect_blue, area_left, "硬件", R.drawable.yingjian);
//            setTextStyle(txt_right, "热门商品", R.drawable.rect_yellow, area_right, "商品", R.drawable.shangpin);
            index = 2;
            setTabSelection(index);
        } else if (t.equals("连锁")) {
            GotoTaoBaoUtil.startActivity(getActivity(), Const.TAOBAO_TEST_URL);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 103) {
            AddressBean addressBean = (AddressBean) data.getSerializableExtra("model");
            getStore(addressBean.getCityid(), addressBean.getCity(), Double.parseDouble(addressBean.getLat()), Double.parseDouble(addressBean.getLng()));

            lingShouPresenter.getVerticalArtical(CITY_CODE, Double.parseDouble(addressBean.getLat()) + "", Double.parseDouble(addressBean.getLng()) + "");//获取垂直滚动文章
        }
    }

    public void setTextStyle(TextView textView, String text, int resBg, RelativeLayout relativeLayout, String tag, int resTop) {
        Drawable drawableTop = getActivity().getResources().getDrawable(resTop);
        drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(), drawableTop.getMinimumHeight());
        textView.setCompoundDrawablePadding(getDimension(getActivity(), 5));
        textView.setText(text);
        textView.setCompoundDrawables(null, drawableTop, null, null);
        relativeLayout.setTag(tag);
        relativeLayout.setBackgroundResource(resBg);
    }

    @Override
    public void onTranslucentChanged(int transAlpha) {
//        actionBar.tvTitle.setVisibility(transAlpha > 48 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemClick(int position) {
        //
        if (t.equals("商品")) {
            GotoTaoBaoUtil.startActivity(getActivity(), Const.TAOBAO_TEST_URL);
//            startActivity(new Intent(getActivity(), GoodDetailActivity.class).putExtra("id", goodsList.getList().get(position).getId()).putExtra("store_id", goodsList.getList().get(position).getStore_id()));
        } else if (t.equals("硬件")) {

        } else {
            GotoTaoBaoUtil.startActivity(getActivity(), Const.TAOBAO_TEST_URL);
//            Intent intent = new Intent(getActivity(), GoodsClassifyActivity.class);
//            intent.putExtra("model", bean.getList().get(position));
//            intent.putExtra("store_id", bean.getList().get(position).getId());
//            startActivity(intent);
        }

    }

    @Override
    public void onItemLongClick(int position) {

    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
//        progress.setVisibility(View.GONE);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == LingShouPresenter.getVerticalArtical_success) {
                articalBean = (ArticalBean) entity.getData();
                if (articalBean != null) {
                    if (articalBean.getList() != null) {
                        for (ArticalBean.ListEntity listEntity : articalBean.getList()) {
                            View articalView = View.inflate(getActivity(), R.layout.item_home_notify, null);
                            ImageView img_homead = (ImageView) articalView.findViewById(R.id.img_homead);
                            TextView txt_homead_name = (TextView) articalView.findViewById(R.id.txt_homead_name);
                            TextView txt_homead_content = (TextView) articalView.findViewById(R.id.txt_homead_content);
                            XGlideLoader.displayImageCircularForUser(getActivity(), Const.imgurl + listEntity.getImg(), img_homead);
                            txt_homead_name.setText(listEntity.getTitle());
                            txt_homead_content.setText(listEntity.getDetail());
                            vf_home.addView(articalView);
                        }
                    }
                }
            } else if (entity.getEventType() == LingShouPresenter.getVerticalArtical_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.getBanner_success) {
                ArrayList<BannerBean> bannerList = (ArrayList<BannerBean>) entity.getData();
                ArrayList<String> imgUrl = new ArrayList<>();
                if (bannerList != null) {
                    for (int i = 0; i < bannerList.size(); i++) {
                        imgUrl.add(Const.imgurl + bannerList.get(i).getImg());
                    }
                    new LunBoHelper().setLunBoData(getActivity(), home_carouseview, imgUrl);
                }
            } else if (entity.getEventType() == LingShouPresenter.getBanner_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.getDefaultAddress_success) {
                ArrayList<AddressBean> addressBeanArrayList = (ArrayList<AddressBean>) entity.getData();
                if (addressBeanArrayList != null) {
                    if (addressBeanArrayList.size() > 0) {
                        //获取到默认地址
                        setCityName(addressBeanArrayList.get(0).getCityid(), addressBeanArrayList.get(0).getCity(), Double.parseDouble(addressBeanArrayList.get(0).getLat()), Double.parseDouble(addressBeanArrayList.get(0).getLng()), "");
                    } else {
                        //没获取到默认地址
                        setCityName("", getSp(Const.CITY), Double.parseDouble(getSp(Const.LNG)), Double.parseDouble(getSp(Const.LAT)), "");
                    }
                } else {
                    //没获取到默认地址
                    setCityName("", getSp(Const.CITY), Double.parseDouble(getSp(Const.LNG)), Double.parseDouble(getSp(Const.LAT)), "");
                }
            } else if (entity.getEventType() == LingShouPresenter.getDefaultAddress_fail) {
                //没获取到默认地址
                MAlert.alert(entity.getData());
                setCityName("", getSp(Const.CITY), Double.parseDouble(getSp(Const.LNG)), Double.parseDouble(getSp(Const.LAT)), "");
            }
        }
    }

    @Override
    public void getLatAndLng(final String cityName, final double lat, final double lng, final String area) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setCityName("", cityName, lat, lng, area);
            }
        });

    }
}
