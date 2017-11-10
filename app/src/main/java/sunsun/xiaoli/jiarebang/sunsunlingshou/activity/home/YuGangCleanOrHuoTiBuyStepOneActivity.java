package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.lingshouadapter.YuGangChiCunAdapter;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.shopcart.MakeSureOrderActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.ServiceBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.BuyType;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;

import static com.itboye.pondteam.utils.ScreenUtil.getDimension;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class YuGangCleanOrHuoTiBuyStepOneActivity extends LingShouBaseActivity implements IRecycleviewClick, Observer {

    TranslucentActionBar actionBar;
    ImageView iv_actionbar_left;
    Button btn_next;
    RecyclerView recyclerview_chicun;
    private YuGangChiCunAdapter adapter;
    String title;
    TextView txt_type;
    LingShouPresenter lingShouPresenter;
    int service_type;
    private ArrayList<ServiceBean> serviceBeanArrayList;
    App mApp;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_yugangqingli_stepone;
    }

    @Override
    protected void initData() {
        mApp= (App) getApplication();
        mApp.yuGangCleanOrHuoTiBuyStepOneActivityUI=this;
        title = getIntent().getStringExtra("title");
        service_type = getIntent().getIntExtra("type", 0);
        lingShouPresenter = new LingShouPresenter(this);
        lingShouPresenter.getServiceSku(service_type);
        initTitlebarStyle1(this, actionBar, title, R.mipmap.ic_left_light, "", 0, "");
        if (title.equals("鱼缸清理") || title.equals("造景装饰")) {
            if (title.equals("鱼缸清理")) {
                txt_type.setText("请选择鱼缸尺寸");
            } else {
                txt_type.setText("请选择造景尺寸");
            }
            LinearLayoutManager layoutManager = new GridLayoutManager(this, 3);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerview_chicun.setLayoutManager(layoutManager);
            RelativeLayout.LayoutParams layoutParamsRe = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsRe.addRule(RelativeLayout.BELOW, R.id.re_type);
            recyclerview_chicun.setLayoutParams(layoutParamsRe);
            int xiangsu = getDimension(this, 40);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, xiangsu);
            layoutParams.addRule(RelativeLayout.BELOW, R.id.recyclerview_chicun);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.setMargins(0, getDimension(this, 20), 0, 0);
            btn_next.setLayoutParams(layoutParams);
        } else if (title.equals("活体购买")) {
            txt_type.setText("请选择鱼种类");
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerview_chicun.setLayoutManager(layoutManager);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ABOVE, R.id.btn_next);
            layoutParams.addRule(RelativeLayout.BELOW, R.id.re_type);
            recyclerview_chicun.setLayoutParams(layoutParams);
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, getDimension(this, 40));
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.setMargins(getDimension(this, 10), getDimension(this, 10), getDimension(this, 10), getDimension(this, 10));
            btn_next.setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApp.yuGangCleanOrHuoTiBuyStepOneActivityUI=null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.btn_next:
                if (serviceBeanArrayList==null) {
                    MAlert.alert("当前无数据，请重新加载");
                    return;
                }
                boolean hasSelect=false;
                for (ServiceBean.SkuInfoEntity skuInfoEntity : serviceBeanArrayList.get(0).getSku_info()) {
                    if (skuInfoEntity.isSelect()) {
                        hasSelect=true;
                        break;
                    }
                }
                if (!hasSelect) {
                    MAlert.alert("请先选择参数信息");
                    return;
                }
                ServiceBean.SkuInfoEntity selectValue = new ServiceBean.SkuInfoEntity();
                if (serviceBeanArrayList != null) {
                    if (serviceBeanArrayList.size() > 0) {
                        Intent intent = new Intent(this, MakeSureOrderActivity.class);
                        intent.putExtra("title", title);
                        intent.putExtra("selectValue", serviceBeanArrayList.get(0));
                        intent.putExtra("canPack",serviceBeanArrayList.get(0).getProduct_info().getCanpack());
                        BuyType buyType=BuyType.Buy_HuoTiGouMai;
                        switch (service_type) {
                            case 2:
                                //活体购买
                                buyType=BuyType.Buy_HuoTiGouMai;
                                break;
                            case 3:
                                //鱼缸清理
                                buyType=BuyType.Buy_YuGangQingLi;
                                break;
                            case 4:
                                //造景装饰
                                buyType=BuyType.Buy_ZaoJingZhuangShi;
                                break;
                            case 5:
                                //预约安装
                                 buyType=BuyType.Buy_YuYueAnZhuang;
                                break;
                        }
                        intent.putExtra("type", buyType);
                        startActivity(intent);
                    }
                } else {
                    MAlert.alert("参数有误");
                }
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        for (ServiceBean.SkuInfoEntity testBean : serviceBeanArrayList.get(0).getSku_info()) {
            testBean.setSelect(false);
        }
        serviceBeanArrayList.get(0).getSku_info().get(position).setSelect(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemLongClick(int position) {

    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == LingShouPresenter.getServiceSku_success) {
                serviceBeanArrayList = (ArrayList<ServiceBean>) entity.getData();
                if (serviceBeanArrayList.size()>0) {
                    if (serviceBeanArrayList.get(0).getSku_info().size()>0) {
                        serviceBeanArrayList.get(0).getSku_info().get(0).setSelect(true);
                    }
                }
                adapter = new YuGangChiCunAdapter(this, R.layout.item_textview, serviceBeanArrayList.get(0).getSku_info());
                adapter.setOnItemListener(this);
                recyclerview_chicun.setAdapter(adapter);
            }
        } else if (entity.getEventType() == LingShouPresenter.getServiceSku_fail) {
            MAlert.alert(entity.getData());
        }
    }
}
