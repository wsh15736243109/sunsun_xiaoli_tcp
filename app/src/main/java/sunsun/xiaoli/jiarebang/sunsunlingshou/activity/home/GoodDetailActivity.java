package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.GoodsDetailBean;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.shopcart.AddShopCartFragment;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.LunBoHelper;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.CarouselView;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentScrollView;
import sunsun.xiaoli.jiarebang.utils.XGlideLoader;

public class GoodDetailActivity extends LingShouBaseActivity implements TranslucentScrollView.TranslucentChangedListener, Observer {


    ListView xlistview;
    WebAdapter webAdapter;
    TranslucentActionBar actionBar;
//    TranslucentScrollView trans_scrollview;
    String id;
    LingShouPresenter lingShouPresenter;
    private GoodsDetailBean bean;
    CarouselView lunbo;
    private TextView name, xinghao, jiage;
    Button liJiGouMai;
    ImageView iv_actionbar_left;
    private AddShopCartFragment jiaru;
    String store_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_good_detail;
    }

    @Override
    protected void initData() {
        store_id = getIntent().getStringExtra("store_id");
        actionBar.setData("", R.mipmap.ic_left_light, "", 0, "", null);
        actionBar.setStatusBarHeight(getStatusBarHeight());
        actionBar.setNeedTranslucent();
        lingShouPresenter = new LingShouPresenter(this);
        id = getIntent().getStringExtra("id");
        lingShouPresenter.getGoodsDetail(id);
        initViews();
    }


    @SuppressLint("NewApi")
    private void initViews() {
        View headerView = getLayoutInflater().inflate(
                R.layout.item_goods_detail_headerview, xlistview, false);
        lunbo = (CarouselView) headerView.findViewById(R.id.lunbo);
        name = (TextView) headerView.findViewById(R.id.name);
        xinghao = (TextView) headerView.findViewById(R.id.xinghao);
        jiage = (TextView) headerView.findViewById(R.id.jiage);
        xlistview.addHeaderView(headerView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.liJiGouMai:
                jiaru = new AddShopCartFragment(bean, true);
                jiaru.setIsxuanzecanshu(false);
                jiaru.setStore_id(store_id);
                jiaru.show(getSupportFragmentManager(), "");
                break;
            case R.id.iv_actionbar_left:
                finish();
                break;
        }
    }

    @Override
    public void onTranslucentChanged(int transAlpha) {

    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == LingShouPresenter.getGoodsDetail_success) {
                bean = (GoodsDetailBean) entity.getData();
                setData();
            } else if (entity.getEventType() == LingShouPresenter.getGoodsDetail_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    private void setData() {
        setLunBo();
        setOtherData();
    }

    private void setOtherData() {
        name.setText(bean.getName());
        xinghao.setText(bean.getSku_list().get(0).getSku_desc());
        jiage.setText("￥" + Double.parseDouble(bean.getSku_list().get(0).getPrice()) / 100);
        webAdapter = new WebAdapter();
        xlistview.setAdapter(webAdapter);
    }

    private void setLunBo() {
        List<String> arCarsourImage = bean.getCarousel_images();
        for (int i = 0; i < arCarsourImage.size(); i++) {
            String s = arCarsourImage.get(i);
            if (!s.startsWith("http")) {
                arCarsourImage.set(i, Const.imgurl + s);
            }
        }
        new LunBoHelper().setLunBoData(this, lunbo, arCarsourImage);
    }

    private class WebAdapter extends BaseAdapter {
        ImageView imageView;

        public WebAdapter() {
            imageView = new ImageView(GoodDetailActivity.this);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            imageView = new ImageView(GoodDetailActivity.this);
//            ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(getWindowManager().getDefaultDisplay().getWidth(),getWindowManager().getDefaultDisplay().getWidth());
//            imageView.setLayoutParams(layoutParams);
            XGlideLoader.displayRatioImageByScreenWidth(GoodDetailActivity.this, Const.imgurl + bean.getDetail_img().get(position).getImg_id(), imageView);
            return imageView;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public int getCount() {
            return bean.getDetail_img() == null ? 0 : bean.getDetail_img().size();
        }
    }

}
