package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.custom.MyListView;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.DensityUtil;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.GoodsDetailBean;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.shopcart.AddShopCartFragment;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.GlidHelper;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.LunBoHelper;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.CarouselView;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentScrollView;

import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitleBarStyle2;

public class GoodDetailActivity extends LingShouBaseActivity implements TranslucentScrollView.TranslucentChangedListener, Observer {


    MyListView xlistview;
    WebAdapter webAdapter;
    TranslucentActionBar actionBar;
    TranslucentScrollView trans_scrollview;
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
        store_id=getIntent().getStringExtra("store_id");
        initTitleBarStyle2(this, actionBar, "", trans_scrollview, this, null);
        actionBar.setNeedTranslucent();
        actionBar.setLeftTitle("");
        actionBar.setLeftIcon(true,R.mipmap.ic_left_light);
        lingShouPresenter = new LingShouPresenter(this);
        id = getIntent().getStringExtra("id");
        lingShouPresenter.getGoodsDetail(id);
        initViews();
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
                GoodsDetailBean bean = (GoodsDetailBean) entity.getData();
                setData(bean);
            } else if (entity.getEventType() == LingShouPresenter.getGoodsDetail_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    private void setData(GoodsDetailBean bean) {
        this.bean = bean;
        setLunBo();
        setOtherData();
    }

    private void setOtherData() {
        name.setText(bean.getName());
        xinghao.setText(bean.getSku_list().get(0).getSku_desc());
        jiage.setText("￥" + bean.getSku_list().get(0).getPrice());

    }

    private void setLunBo() {
        List<String> arCarsourImage = bean.getCarousel_images();
        for (int i = 0; i < arCarsourImage.size(); i++) {
            String s = arCarsourImage.get(i);
            if (!s.startsWith("http")) {
                arCarsourImage.set(i,Const.imgurl + s);
            }
        }
        new LunBoHelper().setLunBoData(this, lunbo, arCarsourImage);
    }

    private class WebAdapter extends BaseAdapter {
//        WebView webview;
//
//        public WebAdapter() {
//
//            webview = new WebView(GoodDetailActivity.this);
//            webview.getSettings().setJavaScriptEnabled(true);
//
//            webview.setWebViewClient(new WebViewClient() {
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    view.loadUrl(url);
//                    return true;
//                }
//            });
//
//            webview.loadUrl("http://sunsun.8raw.com/index.php/Webview/Product/detail?id=55");
//        }
        ImageView webview;
        public WebAdapter() {
//
            webview = new ImageView(GoodDetailActivity.this);


        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            webview = new ImageView(GoodDetailActivity.this);
            GlidHelper.glidLoad(webview,Const.imgurl+bean.getDetail_img().get(position).getImg_id());
            return webview;
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

            return 1;
        }
    }

    @SuppressLint("NewApi")
    private void initViews() {
        View headerView = getLayoutInflater().inflate(
                R.layout.item_goods_detail_headerview, xlistview, false);
        lunbo = (CarouselView) headerView.findViewById(R.id.lunbo);
//        fenlei = (RelativeLayout) headerView.findViewById(R.id.fenlei);

        int width = DensityUtil.screenWidth();
//        lunbo.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        name = (TextView) headerView.findViewById(R.id.name);
        xinghao = (TextView) headerView.findViewById(R.id.xinghao);
        jiage = (TextView) headerView.findViewById(R.id.jiage);

//        share = headerView.findViewById(R.id.share);
//        share.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                // onShareClick();
//                LoginController
//                        .onGoodsShareClick(GoodDetailActivity.this, null);
//            }
//        });
//        fenlei.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                LoginController.onBuyClick(GoodDetailActivity.this, null);
//
//            }
//        });
//        oldPrice = (TextView) headerView.findViewById(R.id.oldPrice);
//        price = (TextView) headerView.findViewById(R.id.price);
//        xiaoliang = (TextView) headerView.findViewById(R.id.xiaoliang);

        xlistview.addHeaderView(headerView);
        webAdapter = new WebAdapter();
        xlistview.setAdapter(webAdapter);

        final TextView tuwen = (TextView) headerView.findViewById(R.id.tuwen);

        final View tuwenUnderLine = headerView
                .findViewById(R.id.tuwenUnderLine);
    }
}
