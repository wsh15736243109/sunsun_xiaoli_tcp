package sunsun.xiaoli.jiarebang.sunsunlingshou.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.bean.ArticalBean;
import com.itboye.pondteam.bean.BannerBean;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.lingshouadapter.HomeDeviceAdapter;
import sunsun.xiaoli.jiarebang.adapter.lingshouadapter.HomeNearShangJiaAdapter;
import sunsun.xiaoli.jiarebang.adapter.lingshouadapter.HomeShangPinAdapter;
import sunsun.xiaoli.jiarebang.adapter.recyclerviewstyle.GridViewItemDecoration;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.GoodsListBean;
import sunsun.xiaoli.jiarebang.beans.StoreListBean;
import sunsun.xiaoli.jiarebang.custom.RatioImageView;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;
import sunsun.xiaoli.jiarebang.logincontroller.LoginController;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.ArticleActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.GoodsClassifyActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.AppointmentInstallStepOneActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.GoodDetailActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.RedBagAcitivty;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.YuGangCleanOrHuoTiBuyStepOneActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.AddressListActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.LingShouSwitchLoginOrRegisterActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.DeviceTypeModel;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.GlidHelper;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.LunBoHelper;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.CarouselView;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentScrollView;

import static com.itboye.pondteam.utils.Const.CITY_CODE;
import static com.itboye.pondteam.utils.Const.lat;
import static com.itboye.pondteam.utils.Const.lng;
import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static com.itboye.pondteam.utils.ScreenUtil.getDimension;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitleBarStyle2;


/**
 * 首页
 */
public class HomeFragment extends LingShouBaseFragment implements TranslucentScrollView.TranslucentChangedListener, IRecycleviewClick, Observer {

    private TranslucentActionBar actionBar;//可渐变的标题栏
    private TranslucentScrollView pullzoom_scrollview;//添加滑动监听的滑动组件
    ViewFlipper vf_home;
    CarouselView home_carouseview;
    Button btn_yuyue, btn_qingli, btn_goumai, btn_zaojingzhuangshi;
    EditText ed_actionbar_search;
    RelativeLayout area_left, area_right, area_center;
    LinearLayout near_store;
    ArrayList<String> arrayList = new ArrayList<>();
    @IsNeedClick
    TextView txt_center, txt_left, txt_right;
    RecyclerView recycler_aqhardwareorhotgoods;
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
    ArrayList<DeviceTypeModel> arDevice = new ArrayList<>();
    ProgressBar progress;
    ArticalBean articalBean;
    RatioImageView home_img;
    Dialog alert;
    RadioButton tvTitle,tvMessage;
    private StoreListBean.ListEntity listEntity1;
    RelativeLayout store_fenlei;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    private SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
            //这里我们拿到回掉回来的bitmap，可以加载到我们想使用到的地方
//            testImg.setImageBitmap((bitmap));
        }
    };

    @Override
    protected void initData() {
//        Glide.with(getActivity()).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503478934176&di=45c483741d5b9e90c51e2d5a77cd85ba&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D2183734545c2d562e605d8ae8f78fa9a%2F8435e5dde71190efee7d4ffac41b9d16fdfa6015.jpg").into(testImg);
//        Glide.with(getActivity()).load("https://www.baidu.com/img/bdlogo.png").transform(new GlideRoundTransform(getActivity(),10)).into(testImg);
        app = (App) getActivity().getApplication();
        app.homeFragment = this;
        lingShouPresenter = new LingShouPresenter(this);
        lingShouPresenter.getBanner(position);//获取轮播图
        lingShouPresenter.getVerticalArtical(CITY_CODE, lng + "", lat + "");//获取垂直滚动文章
        initTitleBarStyle2(getActivity(), actionBar, "", pullzoom_scrollview, this, null);
        DeviceTypeModel deviceListBean = new DeviceTypeModel(R.drawable.home_aq_806, getString(R.string.device_zhineng806));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_jiarebang, getString(R.string.device_zhinengjiarebang));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_ph, getString(R.string.device_yuancheng_ph));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_shuibeng, getString(R.string.device_zhinengbianpinshuibeng));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_guolvtong, getString(R.string.device_chitangguolv));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_shenxiangtou, getString(R.string.device_zhinengshexiangtou));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_shuizudeng, getString(R.string.device_shuizudeng));
        arDevice.add(deviceListBean);
        deviceListBean = new DeviceTypeModel(R.drawable.home_aq_228, getString(R.string.device_zhineng228));
        arDevice.add(deviceListBean);
        //开启渐变
        actionBar.setNeedTranslucent(true, false);
        actionBar.setSearchBarVisible(true);
        setSelectArea("商家");
    }

    boolean hasRe = false;

    public void setCityName(final String cityName, double lat, double lng) {
        try {
            //初始actionBar
//            actionBar.setParent("", R.drawable.img_dingwei, cityName, R.drawable.img_unread, "", null);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_actionbar_left.setText(cityName);
                }
            });
        } catch (Exception e) {

        }
//        if (hasRe == false) {
//            lingShouPresenter.getNearStore(CITY_CODE, lng + "", lat + "", "", "", pageIndex, 10);
//            hasRe = true;
//        }
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
                startActivity(new Intent(getActivity(), AppointmentInstallStepOneActivity.class).putExtra("type", 5));
                break;
            case R.id.btn_qingli:
                if (getSp(Const.UID).equals("")) {
                    startActivity(new Intent(getActivity(), LingShouSwitchLoginOrRegisterActivity.class));
                    return;
                }
                intent = new Intent(getActivity(), YuGangCleanOrHuoTiBuyStepOneActivity.class);
                intent.putExtra("type", 3);
                intent.putExtra("title", "鱼缸清理");
                startActivity(intent);
                break;
            case R.id.ed_actionbar_search:
                startActivity(new Intent(getActivity(), GoodsClassifyActivity.class));
                break;
            case R.id.btn_goumai:
                if (getSp(Const.UID).equals("")) {
                    startActivity(new Intent(getActivity(), LingShouSwitchLoginOrRegisterActivity.class));
                    return;
                }
                intent = new Intent(getActivity(), YuGangCleanOrHuoTiBuyStepOneActivity.class);
                intent.putExtra("title", "活体购买");
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.btn_zaojingzhuangshi:
                if (getSp(Const.UID).equals("")) {
                    startActivity(new Intent(getActivity(), LingShouSwitchLoginOrRegisterActivity.class));
                    return;
                }
                intent = new Intent(getActivity(), YuGangCleanOrHuoTiBuyStepOneActivity.class);
                intent.putExtra("title", "造景装饰");
                intent.putExtra("type", 4);
                startActivity(intent);
                break;
            case R.id.home_img:
                startActivity(new Intent(getActivity(), RedBagAcitivty.class));
                break;
            case R.id.area_left:
            case R.id.area_right:
            case R.id.area_center:
                setSelectArea(v.getTag());
                break;
            case R.id.haoping:
                haoping.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_border_blue_bg_blue));
                haoping.setTextColor(getActivity().getResources().getColor(R.color.white));
                zuijin.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_border_blue_bg_white));
                zuijin.setTextColor(getActivity().getResources().getColor(R.color.blue500));
                break;
            case R.id.zuijin:
                zuijin.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_border_blue_bg_blue));
                zuijin.setTextColor(getActivity().getResources().getColor(R.color.white));
                haoping.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_border_blue_bg_white));
                haoping.setTextColor(getActivity().getResources().getColor(R.color.blue500));
                break;
            case R.id.lay_actionbar_left:
                startActivity(new Intent(getActivity(), AddressListActivity.class).putExtra("title", getString(R.string.choose_address)));
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
            case R.id.txt_boda:
                listEntity1 = (StoreListBean.ListEntity) v.getTag();
                alert = new Dialog(getActivity(),R.style.callphonedialog);
                View view = View.inflate(getActivity(), R.layout.poup_home_callphone, null);
                view.setMinimumWidth(getActivity().getWindowManager().getDefaultDisplay().getWidth()-100);
                tvTitle = (RadioButton) view.findViewById(R.id.tvTitle);
                tvTitle.setChecked(true);
                tvTitle.setText("手机:" + listEntity1.getPhone());
                tvMessage = (RadioButton) view.findViewById(R.id.tvMessage);
                tvMessage.setText("电话:" + listEntity1.getMobile());

                TextView tvBtnLeft = (TextView) view.findViewById(R.id.tvBtnLeft);
                tvBtnLeft.setOnClickListener(this);
                TextView tvBtnRight = (TextView) view.findViewById(R.id.tvBtnRight);
                tvBtnRight.setOnClickListener(this);
                alert.setContentView(view);
                alert.show();
                break;
            case R.id.tvBtnLeft:
                alert.dismiss();
                break;
            case R.id.tvBtnRight:
                try {
                    String number="";
                    if (tvMessage.isChecked()) {
                        number=listEntity1.getMobile();
                    }else{
                        number=listEntity1.getPhone();
                    }
                    if (number.equals("")) {
                        MAlert.alert("当前电话不可用");
                        return;
                    }
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+number));
                    intent.setAction(Intent.ACTION_DIAL);
                    startActivity(intent);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                alert.dismiss();
                break;
        }
    }

    String t;

    private void setSelectArea(Object tag) {
        //添加智能设备---->附近商家---->热门商品
        progress.setVisibility(View.VISIBLE);
        recycler_aqhardwareorhotgoods.setVisibility(View.GONE);
        store_fenlei.setVisibility(View.GONE);
        t = tag + "";
        if (t.equals("商品")) {
            lingShouPresenter.getHotSearchGoods();
            setTextStyle(txt_center, "热门商品", R.drawable.circle_yellow, area_center, "商品", R.drawable.shangpin);
            setTextStyle(txt_left, "附近商家", R.drawable.rect_green, area_left, "商家", R.drawable.shangjia);
            setTextStyle(txt_right, "添加智能设备", R.drawable.rect_blue, area_right, "硬件", R.drawable.yingjian);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            recycler_aqhardwareorhotgoods.setLayoutManager(layoutManager);
        } else if (t.equals("硬件")) {
            progress.setVisibility(View.GONE);
            recycler_aqhardwareorhotgoods.setVisibility(View.VISIBLE);
            setTextStyle(txt_center, "添加智能设备", R.drawable.circle_blue, area_center, "硬件", R.drawable.yingjian);
            setTextStyle(txt_left, "热门商品", R.drawable.rect_yellow, area_left, "商品", R.drawable.shangpin);
            setTextStyle(txt_right, "附近商家", R.drawable.rect_green, area_right, "商家", R.drawable.shangjia);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
            recycler_aqhardwareorhotgoods.setLayoutManager(layoutManager);
            //把我们自定义的ItemDecoration设置给RecyclerView
            recycler_aqhardwareorhotgoods.addItemDecoration(new GridViewItemDecoration(getActivity()));
            HomeDeviceAdapter adapter = new HomeDeviceAdapter(getActivity(), arDevice, R.layout.item_lingshou_device);
            adapter.setOnItemListener(this);
            recycler_aqhardwareorhotgoods.setAdapter(adapter);
        } else if (t.equals("商家")) {
            store_fenlei.setVisibility(View.VISIBLE);
            lingShouPresenter.getNearStore("330100", 120.377819 + "", 120.377819 + "", "", "", pageIndex, 10);
            near_store.setVisibility(View.VISIBLE);
            setTextStyle(txt_center, "附近商家", R.drawable.circle_green, area_center, "商家", R.drawable.shangjia);
            setTextStyle(txt_left, "添加智能设备", R.drawable.rect_blue, area_left, "硬件", R.drawable.yingjian);
            setTextStyle(txt_right, "热门商品", R.drawable.rect_yellow, area_right, "商品", R.drawable.shangpin);
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
            startActivity(new Intent(getActivity(), GoodDetailActivity.class).putExtra("id", goodsList.getList().get(position).getId()).putExtra("store_id", goodsList.getList().get(position).getStore_id()));
        } else if (t.equals("硬件")) {

        } else {
            Intent intent = new Intent(getActivity(), GoodsClassifyActivity.class);
            intent.putExtra("model",bean.getList().get(position));
            intent.putExtra("store_id", bean.getList().get(position).getId());
            startActivity(intent);
        }

    }

    @Override
    public void onItemLongClick(int position) {

    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        progress.setVisibility(View.GONE);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == LingShouPresenter.getNearStore_success) {
                recycler_aqhardwareorhotgoods.setVisibility(View.VISIBLE);
                bean = (StoreListBean) entity.getData();
                HomeNearShangJiaAdapter adapter = new HomeNearShangJiaAdapter(this, bean.getList(), R.layout.item_home_nearshangjia);
                adapter.setOnItemListener(this);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recycler_aqhardwareorhotgoods.setLayoutManager(layoutManager);
                recycler_aqhardwareorhotgoods.setAdapter(adapter);
            } else if (entity.getEventType() == LingShouPresenter.getNearStore_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.getVerticalArtical_success) {
                articalBean = (ArticalBean) entity.getData();
                if (articalBean != null) {
                    if (articalBean.getList() != null) {
                        for (ArticalBean.ListEntity listEntity : articalBean.getList()) {
                            View articalView = View.inflate(getActivity(), R.layout.item_home_notify, null);
                            ImageView img_homead = (ImageView) articalView.findViewById(R.id.img_homead);
                            TextView txt_homead_name = (TextView) articalView.findViewById(R.id.txt_homead_name);
                            TextView txt_homead_content = (TextView) articalView.findViewById(R.id.txt_homead_content);
                            GlidHelper.glidLoad(img_homead, Const.imgurl + listEntity.getImg());
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
            } else if (entity.getEventType() == LingShouPresenter.getHotSearchGoods_success) {
                recycler_aqhardwareorhotgoods.setVisibility(View.VISIBLE);
                goodsList = (GoodsListBean) entity.getData();
                ArrayList<GoodsListBean.ListEntity> arTemp = new ArrayList<>();
                if (goodsList != null) {
                    if (goodsList.getList() != null) {
                        arTemp.addAll(goodsList.getList());
                    }
                }
                HomeShangPinAdapter adapter = new HomeShangPinAdapter(getActivity(), arTemp, R.layout.item_home_shangpin);
                adapter.setOnItemListener(this);
                recycler_aqhardwareorhotgoods.addItemDecoration(new GridViewItemDecoration(getActivity()));
                recycler_aqhardwareorhotgoods.setAdapter(adapter);
            } else if (entity.getEventType() == LingShouPresenter.getHotSearchGoods_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
