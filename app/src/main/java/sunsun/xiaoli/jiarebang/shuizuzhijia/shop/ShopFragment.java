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
import sunsun.xiaoli.jiarebang.utils.GotoTaoBaoUtil;
import sunsun.xiaoli.jiarebang.utils.LocationUtil;
import sunsun.xiaoli.jiarebang.utils.Util;
import sunsun.xiaoli.jiarebang.utils.XGlideLoader;

import static com.itboye.pondteam.utils.Const.imgSunsunUrl;

/**
 * Created by Administrator on 2018/1/24.
 */

public class ShopFragment extends LingShouBaseFragment implements Observer, LocationUtil.OnLocationResult {

    UserPresenter userPresenter;
    private ArrayList<BannerBean> bannerBeanArrayList;
    RatioImageView img_shop_first, img_shop_second, img_shop_third;

    LocationUtil locationUtil;
    ListView list_shop;
    private String city = "";
    private int size = 3;
    private int page = 1;
    private String area;
    private NavigationBean navigationBean;
    TextView txt_exist,txt_title;
    ImageView img_back;
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
                GotoTaoBaoUtil.startActivity(getActivity(), Const.TAOBAO_TEST_URL);
            }
        });
    }

    private void initTop() {
        txt_title.setText(getString(R.string.shop_xianshang));
        img_back.setVisibility(View.GONE);
        txt_exist.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_shop_enter:
                GotoTaoBaoUtil.startActivity(getActivity(), Const.TAOBAO_TEST_URL);
                break;
        }
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
                    XGlideLoader.displayImage(getActivity(), imgSunsunUrl + bannerBeanArrayList.get(0).getImg(), img_shop_first);
                    XGlideLoader.displayImage(getActivity(), imgSunsunUrl + bannerBeanArrayList.get(2).getImg(), img_shop_second);
                    XGlideLoader.displayImage(getActivity(), imgSunsunUrl + bannerBeanArrayList.get(1).getImg(), img_shop_third);
                } else if (entity.getEventType() == UserPresenter.getBanners_fail) {
                    MAlert.alert(entity.getData());
                }else if (entity.getEventType() == UserPresenter.branchSearch_success) {
                    navigationBean = (NavigationBean) entity.getData();
                    if (navigationBean != null) {
                        if (navigationBean.getList().size() > 0) {
                            list_shop.setAdapter(new ShopAdapter(this,navigationBean.getList(),R.layout.item_shop));
                        }
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
    public void getLatAndLng(final String cityName, double lat, double lng,final String areaName) {
        if (!isSearch) {
            isSearch = true;
            city = Util.queryCityNo(cityName);
//            area = Util.queryDistrictNo(areaName);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txt_exist.setText(cityName+areaName);
                }
            });
            userPresenter.branchSearch(city, area, lng, lat, page, size);
        }
    }
}
