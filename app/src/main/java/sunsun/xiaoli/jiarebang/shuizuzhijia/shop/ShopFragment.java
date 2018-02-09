package sunsun.xiaoli.jiarebang.shuizuzhijia.shop;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.bean.BannerBean;
import com.itboye.pondteam.bean.NavigationBean;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.ShopAdapter;
import sunsun.xiaoli.jiarebang.custom.RatioImageView;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.AddressFragment;
import sunsun.xiaoli.jiarebang.utils.GotoTaoBaoUtil;
import sunsun.xiaoli.jiarebang.utils.LocationUtil;
import sunsun.xiaoli.jiarebang.utils.Util;
import sunsun.xiaoli.jiarebang.utils.XGlideLoader;

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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_aquarium_shop;
    }

    @Override
    protected void initData() {
        initTop();
        locationUtil = new LocationUtil(getActivity(), this);
        userPresenter = new UserPresenter(this);
        userPresenter.getBanners(6233);
        list_shop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (navigationBean != null) {
                    if (navigationBean.getList() != null) {
                        GotoTaoBaoUtil.startActivity(getActivity(), navigationBean.getList().get(position).getTaobao_store_url());
                    }
                }
            }
        });
    }

    private void initTop() {
        txt_title.setText(getString(R.string.shop_xianshang));
        img_back.setVisibility(View.GONE);
        txt_exist.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.jiantou_down, 0);
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
                url = v.getTag(R.id.tag_first) + "";
                url_type = v.getTag(R.id.tag_second) + "";
                if (url.equals("")) {
//                    MAlert.alert("url is null");
                    return;
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
        if (url_type.equals("6070")) {
            //跳转链接

        } else if (url_type.equals("6071")) {
            //商品详情
        } else if (url_type.equals("6072")) {
            //帖子详情
        }
        GotoTaoBaoUtil.startActivity(getActivity(), url);
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
            } else {
                if (entity.getEventType() == UserPresenter.getBanners_success) {
                    bannerBeanArrayList = (ArrayList<BannerBean>) entity.getData();

                    XGlideLoader.displayImage(getActivity(), imgSunsunUrl + bannerBeanArrayList.get(2).getImg(), img_shop_first);
                    img_shop_first.setTag(R.id.tag_first, bannerBeanArrayList.get(2).getUrl());
                    img_shop_first.setTag(R.id.tag_second, bannerBeanArrayList.get(2).getUrl_type());

                    XGlideLoader.displayImage(getActivity(), imgSunsunUrl + bannerBeanArrayList.get(0).getImg(), img_shop_third);
                    img_shop_third.setTag(R.id.tag_first, bannerBeanArrayList.get(0).getUrl());
                    img_shop_third.setTag(R.id.tag_second, bannerBeanArrayList.get(0).getUrl_type());

                    XGlideLoader.displayImage(getActivity(), imgSunsunUrl + bannerBeanArrayList.get(1).getImg(), img_shop_second);
                    img_shop_second.setTag(R.id.tag_first, bannerBeanArrayList.get(1).getUrl());
                    img_shop_second.setTag(R.id.tag_second, bannerBeanArrayList.get(1).getUrl_type());
                } else if (entity.getEventType() == UserPresenter.getBanners_fail) {
                    MAlert.alert(entity.getData());
                } else if (entity.getEventType() == UserPresenter.branchSearch_success) {
                    navigationBean = (NavigationBean) entity.getData();
                    if (navigationBean != null) {
                        list_shop.setAdapter(new ShopAdapter(this, navigationBean.getList(), R.layout.item_shop));
                    } else {
                        MAlert.alert("出错了");
                    }
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
//            area = Util.queryDistrictNo(areaName);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txt_exist.setText(cityName);
                }
            });
            userPresenter.branchSearch(this.cityNo, area, lng, lat, page, size);
        }
    }

    @Override
    public void onGetinforListener(String province, String city, String district, String provinceNo, String cityNo, String districtNo) {
        this.cityNo = cityNo;
        this.cityName = city;
        this.provinceName = province;
        txt_exist.setText(city);
        userPresenter.branchSearch(cityNo, null, -1, -1, page, size);
    }
}
