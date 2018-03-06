package sunsun.xiaoli.jiarebang.shuizuzhijia.shop;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.bean.BannerBean;
import com.itboye.pondteam.bean.NavigationBean;
import com.itboye.pondteam.custom.ptr.BasePtr;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.ShopAdapter;
import sunsun.xiaoli.jiarebang.custom.RatioImageView;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.AddressFragment;
import sunsun.xiaoli.jiarebang.utils.WebUtil;
import sunsun.xiaoli.jiarebang.utils.LocationUtil;
import sunsun.xiaoli.jiarebang.utils.Util;
import sunsun.xiaoli.jiarebang.utils.XGlideLoader;

import static com.itboye.pondteam.utils.Const.TAOBAO_URL;
import static com.itboye.pondteam.utils.Const.imgSunsunUrl;

/**
 * Created by Administrator on 2018/1/24.
 */

public class ShopFragment extends LingShouBaseFragment implements Observer, LocationUtil.OnLocationResult, AddressFragment.GetInforListener {

    UserPresenter userPresenter;
    private ArrayList<BannerBean> bannerBeanArrayList;
    RatioImageView img_shop_first, img_shop_second, img_shop_third;

    LocationUtil locationUtil;
    ListView list_shop;
    private String cityNo = "";
    private int size = 3;
    private int page = 1;
    private String area;
    private NavigationBean navigationBean;
    TextView txt_exist, txt_title;
    ImageView img_back;
    private String cityName;
    private String provinceName;

    PtrFrameLayout ptrFrame_shop;
    private ArrayList<NavigationBean.NavigationDetail> navigationDetailArrayList = new ArrayList<>();
    private ShopAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_aquarium_shop;
    }

    @Override
    protected void initData() {
        initTop();
        initPtrFrameLayout();
        locationUtil = new LocationUtil(getActivity(), this);
        userPresenter = new UserPresenter(this);
        userPresenter.getBanners(Const.SHOP_TOP_BANNER_POSITION);
        list_shop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (navigationDetailArrayList != null) {
                        String s = navigationDetailArrayList.get(position).getTaobao_store_url();
                        //跳转淘宝
//                        s = "https://sensen.tmall.com"; s = "taobao://sensen.tmall.com";
                        WebUtil.startActivityForTaoBao(getActivity(), s);
//                        WebUtil.startActivityForTaoBao(getActivity(), navigationBean.getList().get(position).getTaobao_store_url());
                }
            }
        });
    }

    private void initPtrFrameLayout() {
        BasePtr.setPagedPtrStyle(ptrFrame_shop);
        ptrFrame_shop.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                page++;
                queryShop();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                queryShop();
            }
        });
    }

    private void initTop() {
        txt_exist.setText(getString(R.string.position_ing));
        txt_title.setText(getString(R.string.shop_xianshang));
        txt_title.setTextColor(getResources().getColor(R.color.main_green));
        img_back.setVisibility(View.GONE);
        txt_exist.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.jiantou_down, 0);

        adapter = new ShopAdapter(this, navigationDetailArrayList, R.layout.item_shop);
        list_shop.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        String url = "";
        String url_type = "";
        switch (v.getId()) {
            case R.id.tv_shop_enter:
                url = Const.TAOBAO_TEST_URL;
                goToTaoBao(url, url_type);
                break;
            case R.id.img_shop_first:
            case R.id.img_shop_second:
            case R.id.img_shop_third:
                String position = v.getTag(R.id.tag_first).toString();
                url_type = v.getTag(R.id.tag_second) + "";
                for (BannerBean bannerBean : bannerBeanArrayList) {
                    if (bannerBean.getTitle().equals(position)) {
                        if (bannerBean.getUrl_type().equals("6071")) {
                            url = String.format(TAOBAO_URL, lng, lat, bannerBean.getUrl());
                            break;
                        } else if (bannerBean.getUrl_type().equals("6070")) {
                            //直接跳转web
                            url = bannerBean.getUrl();
                            break;
                        } else if (bannerBean.getUrl_type().equals("")) {
                            url = "";
                            break;
                        }
                    }
                }
                goToTaoBao(url, url_type);
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
        }
    }

    private void goToTaoBao(String url, String url_type) {
        if (url_type.equals("6071")) {
            //跳转链接(淘宝店铺)
            WebUtil.startActivityForTaoBao(getActivity(), url);
        } else if (url_type.equals("6070")) {
            //直接webView
            WebUtil.startActivityForUrl(getActivity(), url,"详情");
        } else if (url_type.equals("6072")) {
            //帖子详情
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        ptrFrame_shop.refreshComplete();
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
            } else {
                if (entity.getEventType() == UserPresenter.getBanners_success) {
                    bannerBeanArrayList = (ArrayList<BannerBean>) entity.getData();

                    for (BannerBean bannerBean : bannerBeanArrayList) {
                        if (bannerBean.getTitle().equals("top")) {
                            XGlideLoader.displayImage(getActivity(), imgSunsunUrl + bannerBean.getImg(), img_shop_first);
//                            img_shop_first.setTag(R.id.tag_first, bannerBean.getUrl());
                            img_shop_first.setTag(R.id.tag_first, bannerBean.getTitle());
                            img_shop_first.setTag(R.id.tag_second, bannerBean.getUrl_type());
                        } else if (bannerBean.getTitle().equals("left")) {
                            XGlideLoader.displayImage(getActivity(), imgSunsunUrl + bannerBean.getImg(), img_shop_second);
//                            img_shop_second.setTag(R.id.tag_first, bannerBean.getUrl());
                            img_shop_second.setTag(R.id.tag_first, bannerBean.getTitle());
                            img_shop_second.setTag(R.id.tag_second, bannerBean.getUrl_type());
                        } else if (bannerBean.getTitle().equals("right")) {
                            XGlideLoader.displayImage(getActivity(), imgSunsunUrl + bannerBean.getImg(), img_shop_third);
//                            img_shop_third.setTag(R.id.tag_first, bannerBean.getUrl());
                            img_shop_third.setTag(R.id.tag_first, bannerBean.getTitle());
                            img_shop_third.setTag(R.id.tag_second, bannerBean.getUrl_type());
                        }
                    }
                } else if (entity.getEventType() == UserPresenter.getBanners_fail) {
                    MAlert.alert(entity.getData());
                } else if (entity.getEventType() == UserPresenter.branchSearch_success) {
                    navigationBean = (NavigationBean) entity.getData();
                    if (page == 1) {
                        navigationDetailArrayList.clear();
                    }
                    navigationDetailArrayList.addAll(navigationBean.getList());
                    adapter.notifyDataSetChanged();
                } else if (entity.getEventType() == UserPresenter.branchSearch_fail) {
                    MAlert.alert(entity.getData());
                }
            }
        }
    }

    boolean isSearch = false;

    @Override
    public void getLatAndLng(final String provinceName, final String cityName, double lat, double lng, final String areaName) {
        if (!isSearch) {
            isSearch = true;
            this.cityNo = Util.queryCityNo(cityName);
            this.cityName = cityName;
            this.provinceName = provinceName;
            this.lng = lng;
            this.lat = lat;
//            area = Util.queryDistrictNo(areaName);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txt_exist.setText(cityName);
                }
            });
            queryShop();
        }
    }

    private double lng, lat;

    private void queryShop() {
        userPresenter.branchSearch(0, this.cityNo, area, lng, lat, page, size);
    }

    @Override
    public void onGetinforListener(String province, String city, String district, String provinceNo, String cityNo, String districtNo) {
        this.cityNo = cityNo;
        this.cityName = city;
        this.provinceName = province;
        txt_exist.setText(city);
        userPresenter.branchSearch(0, cityNo, null, -1, -1, page, size);
    }
}
