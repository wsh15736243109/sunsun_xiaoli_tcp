package sunsun.xiaoli.jiarebang.sunsunlingshou.activity;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.ClassifyBean;
import sunsun.xiaoli.jiarebang.beans.GoodsListBean;
import sunsun.xiaoli.jiarebang.beans.StoreListBean;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.GoodDetailActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.MyListViewAdapter1;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.MyListViewAdapter2;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.LunBoHelper;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.CarouselView;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;

import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;


public class GoodsClassifyActivity extends LingShouBaseActivity implements Observer {

    private int selectIndex = 0;
    private ListView list_item_1;
    GridView list_item_2;
    private MyListViewAdapter1 adapter1;
    private MyListViewAdapter2 adapter2;
    TranslucentActionBar actionBar;
    ImageView iv_actionbar_left;
    LingShouPresenter lingShouPresenter;
    private ArrayList<ClassifyBean> bean;
    private GoodsListBean goodsList;
    String store_id;
    LinearLayout li_storeinfo;
    StoreListBean.ListEntity model;
    TextView txt_contact, txt_mobile, txt_boda, txt_address, txt_peisongfei;
    CarouselView lunbo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_classify;
    }

    @Override
    protected void initData() {
        initView();
    }

    private void initView() {
        initTitlebarStyle1(this, actionBar, "分类查找", R.mipmap.ic_left_light, "", 0, "");
        store_id = getIntent().getStringExtra("store_id");
        model = (StoreListBean.ListEntity) getIntent().getSerializableExtra("model");
        if (model == null) {
            li_storeinfo.setVisibility(View.GONE);
        } else {
            actionBar.setTitle(model.getName());
            li_storeinfo.setVisibility(View.VISIBLE);
            txt_contact.setText("联系人"+model.getContacts());
            txt_mobile.setText(Html.fromHtml("手机："+model.getPhone()+"<br />电话："+model.getMobile()));
            txt_address.setText("地址"+model.getAddress());
            txt_peisongfei.setText("配送费￥"+model.getPay());
            new LunBoHelper().setLunBoData(this,lunbo,model.getLogo());
        }
//        adapter2=new MyListViewAdapter2(allData,this,selectIndex);
//        list_item_2.setAdapter(adapter2);

        list_item_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectIndex = position;
                //把下标传过去，然后刷新adapter
                adapter1.setIndex(position);
                adapter1.notifyDataSetChanged();
                //当点击某个item的时候让这个item自动滑动到listview的顶部(下面item够多，如果点击的是最后一个就不能到达顶部了)
                list_item_1.smoothScrollToPositionFromTop(position, 0);

                lingShouPresenter.getGoodsList("", bean.get(position).getId(), "", 1, 10);
//
//                adapter2.setIndex(position);
//                list_item_2.setAdapter(adapter2);
            }
        });

        list_item_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(GoodsClassifyActivity.this, GoodDetailActivity.class).putExtra("id", goodsList.getList().get(position).getId()).putExtra("store_id", goodsList.getList().get(position).getStore_id()));
            }
        });
        lingShouPresenter = new LingShouPresenter(this);
        lingShouPresenter.getMainClassify();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == LingShouPresenter.getMainClassify_success) {
                bean = (ArrayList<ClassifyBean>) entity.getData();
                adapter1 = new MyListViewAdapter1(bean, this, selectIndex);
                list_item_1.setAdapter(adapter1);
                if (bean!=null) {
                    if (bean.size()>0) {
                        lingShouPresenter.getGoodsList("", bean.get(0).getId(), "", 1, 10);
                    }
                }
//                list_item_1.addFooterView(new View(GoodsClassifyActivity.this));
            } else if (entity.getEventType() == LingShouPresenter.getMainClassify_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.getSeconfClassify_success) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.getSeconfClassify_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.getGoodsList_success) {
                goodsList = (GoodsListBean) entity.getData();
                adapter2 = new MyListViewAdapter2(goodsList.getList(), this, selectIndex);
                list_item_2.setAdapter(adapter2);
            } else if (entity.getEventType() == LingShouPresenter.getGoodsList_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
