package sunsun.xiaoli.jiarebang.shuizuzhijia.shop;

import android.view.View;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.bean.BannerBean;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.custom.RatioImageView;
import sunsun.xiaoli.jiarebang.utils.XGlideLoader;

import static com.itboye.pondteam.utils.Const.imgSunsunUrl;

/**
 * Created by Administrator on 2018/1/24.
 */

public class ShopFragment extends LingShouBaseFragment implements Observer {

    UserPresenter userPresenter;
    private ArrayList<BannerBean> bannerBeanArrayList;
    RatioImageView img_shop_first,img_shop_second, img_shop_third;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_aquarium_shop;
    }

    @Override
    protected void initData() {
        userPresenter = new UserPresenter(this);
        userPresenter.getBanners(6233);
    }

    @Override
    public void onClick(View v) {

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
                }
            }
        }
    }
}
