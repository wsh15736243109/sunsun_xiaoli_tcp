package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.shopcart;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.custom.ptr.BasePtr;
import com.itboye.pondteam.interfaces.IRecyclerviewclicklistener;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.lingshouadapter.ShopCartAdapter;
import sunsun.xiaoli.jiarebang.beans.GoodsDetailBean;
import sunsun.xiaoli.jiarebang.beans.ShopCartBean;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.BuyType;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.refreshrecyvlerview.callback.PullToRefreshListener;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

/**
 * Created by Administrator on 2017/6/23.
 */

public class ShopCartChildFragment extends LingShouBaseFragment implements PullToRefreshListener, IRecyclerviewclicklistener, Observer {
    private RecyclerView recyclerView;
    private ShopCartAdapter adapter;
    RelativeLayout shopcart_li;
    RelativeLayout li_bottom;
    TextView tv_go_to_pay;
    LingShouPresenter lingShouPresenter;
    int product_type = 1;
    private ArrayList<ShopCartBean> arrayList = new ArrayList<>();
    TextView noData, heji;
//    SwipeRefreshLayout swip_refresh;
    //    PtrClassicFrameLayout ptrFrame;
    PtrFrameLayout ptrFrame;
    CheckBox all_chekbox;

    public ShopCartChildFragment(int product_type) {
        this.product_type = product_type;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.order_child_fragment;
    }

    @Override
    protected void initData() {
        beginRequest();
        shopcart_li.setPadding(0, 0, 0, 0);

        //-------------------GridView Style-----------------
        //  final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, getResources().getDimensionPixelSize(R.dimen.padding_middle), true));
        //  recyclerView.setHasFixedSize(true);
        //  recyclerView.setLayoutManager(layoutManager);
        //--------------------------------------------------
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        li_bottom.setVisibility(View.VISIBLE);
//        swip_refresh.setRefreshing(true);
        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                beginRequest();
                ptrFrame.autoRefresh();//自动刷新
            }
        }, 100);
//        ptrFrame.setPtrHandler(new PtrDefaultHandler2() {
//            @Override
//            public void onLoadMoreBegin(PtrFrameLayout frame) {//加载更多的时候
//                beginRequest();
//            }
//
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {//刷新开始
//                beginRequest();
//            }
//        });
//        swip_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                beginRequest();
//            }
//        });
        BasePtr.setRefreshOnlyStyle(ptrFrame);
        ptrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                beginRequest();
            }
        });
    }

    public void beginRequest() {
        String uid = getSp(Const.UID);
        if (!uid.equals("")) {
            lingShouPresenter = new LingShouPresenter(this);
            lingShouPresenter.getShopCart(getSp(Const.UID), product_type, getSp(Const.S_ID));
        } else {
            MAlert.alert("没登录");
        }
    }

    int position, currrentCount;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gwc_jian:
                position = (int) v.getTag();
                currrentCount = Integer.valueOf(arrayList.get(position).getCount());
                currrentCount--;
                arrayList.get(position).setCount(currrentCount + "");
                lingShouPresenter.updateShopCart(getSp(Const.UID), arrayList.get(position).getId(), currrentCount, getSp(Const.S_ID));
                break;
            case R.id.gwc_jia:
                position = (int) v.getTag();
                currrentCount = Integer.valueOf(arrayList.get(position).getCount());
                currrentCount++;
                arrayList.get(position).setCount(currrentCount + "");
                lingShouPresenter.updateShopCart(getSp(Const.UID), arrayList.get(position).getId(), currrentCount, getSp(Const.S_ID));
                break;
            case R.id.check_img:
                position = (int) v.getTag();
                boolean isSelect = (boolean) v.getTag(R.id.tag_first);
                arrayList.get(position).setSelect(!isSelect);
                adapter.notifyDataSetChanged();
                //判断是否已经全部选中
                isAllSelect();
                //根据选中的计算价格
                caculateMoney();
                break;
            case R.id.all_chekbox:
                if (arrayList != null) {
                    if (all_chekbox.isChecked()) {
                        for (ShopCartBean shopCartBean : arrayList) {
                            shopCartBean.setSelect(true);
                        }
                        all_chekbox.setText("全不选");
                    } else {
                        for (ShopCartBean shopCartBean : arrayList) {
                            shopCartBean.setSelect(false);
                        }
                        all_chekbox.setText("全选");
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                    caculateMoney();
                }
                break;
            case R.id.tv_go_to_pay:
                ArrayList<ShopCartBean> ar = (ArrayList<ShopCartBean>) arrayList.clone();
                ArrayList<GoodsDetailBean> detailBeanArrayList = new ArrayList<>();
                for (int i1 = 0; i1 < ar.size(); i1++) {
                    ShopCartBean shopCartBean = ar.get(i1);
                    if (shopCartBean.isSelect()) {

                    } else {
                        ar.remove(i1);
                    }
                }
                if (ar.size() <= 0) {
                    MAlert.alert("请选择要结算的商品");
                    return;
                }
                Intent intent = new Intent(getActivity(), MakeSureOrderActivity.class);
                intent.putExtra("model", ar);
                intent.putExtra("isShopCart", true);
                intent.putExtra("type", BuyType.Buy_ShopCart);
                startActivity(intent);
                break;
        }
    }

    private void isAllSelect() {
        boolean isAllSelect = false;
        if (arrayList != null) {
            for (ShopCartBean shopCartBean : arrayList) {
                if (shopCartBean.isSelect()) {
                    isAllSelect = true;
                } else {
                    isAllSelect = false;
                    break;
                }
            }
            if (isAllSelect) {
                all_chekbox.setText("全不选");
            } else {
                all_chekbox.setText("全选");
            }
            all_chekbox.setChecked(isAllSelect);
        }
    }

    private void caculateMoney() {
        double totalPrice = 0;
        boolean hasSelect = false;
        for (ShopCartBean shopCartBean : arrayList) {
            if (shopCartBean.isSelect()) {
                hasSelect = true;
                totalPrice += shopCartBean.getPrice()*Integer.parseInt(shopCartBean.getCount());
            }
        }
        if (!hasSelect) {
            heji.setVisibility(View.GONE);
        } else {
            heji.setVisibility(View.VISIBLE);
            heji.setText(Html.fromHtml("￥" + totalPrice));
        }
    }


    @Override
    public void onRefresh() {
        beginRequest();
    }

    @Override
    public void onLoadMore() {
        beginRequest();
    }

    @Override
    public void onItemClick(String str) {

    }

    @Override
    public void onLeftMenuClick(String str) {
        lingShouPresenter.deleteShopCart(getSp(Const.UID), arrayList.get(Integer.parseInt(str)).getId(), getSp(Const.S_ID));
    }

    @Override
    public void onRightMenuClick(String str) {
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
//        swip_refresh.setRefreshing(false);
        ptrFrame.refreshComplete();
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                judgeIsVisible();
                return;
            }
            if (entity.getEventType() == LingShouPresenter.getShopCart_success) {
                ArrayList<ShopCartBean> arTemp = (ArrayList<ShopCartBean>) entity.getData();
                arrayList.clear();
                arrayList.addAll(arTemp);
                if (arrayList != null) {
                    setListData();
                } else {
                    noData.setVisibility(View.VISIBLE);
                }
            } else if (entity.getEventType() == LingShouPresenter.getShopCart_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.updateShopCart_success) {
                MAlert.alert(entity.getData());
                adapter.notifyDataSetChanged();
                caculateMoney();
            } else if (entity.getEventType() == LingShouPresenter.updateShopCart_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.deleteShopCart_success) {
                MAlert.alert(entity.getData());
                lingShouPresenter.getShopCart(getSp(Const.UID), product_type, getSp(Const.S_ID));
            } else if (entity.getEventType() == LingShouPresenter.deleteShopCart_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    private void judgeIsVisible() {
        if (arrayList == null) {
            li_bottom.setVisibility(View.GONE);
        } else if (arrayList.size() <= 0) {
            li_bottom.setVisibility(View.GONE);
        } else {
            li_bottom.setVisibility(View.VISIBLE);
        }
    }

    private void setListData() {
        if (adapter == null) {
            adapter = new ShopCartAdapter(this, arrayList, this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        if (arrayList.size() <= 0) {
            noData.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.GONE);
        }
        judgeIsVisible();
        caculateMoney();
    }
}
