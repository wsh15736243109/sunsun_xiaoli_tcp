package sunsun.xiaoli.jiarebang.sunsunlingshou.fragment.OrderFragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.custom.ptr.BasePtr;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.OrderBean;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.OrderDetailActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.StorePingJiaActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.PayTypeActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.ModeAdapter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.BuyType;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.refreshrecyvlerview.callback.PullToRefreshListener;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

/**
 * Created by Administrator on 2017/6/23.
 */

@SuppressLint("ValidFragment")
public class OrderChildFragment extends LingShouBaseFragment implements PullToRefreshListener, Observer {
    private String orderType;
    private int status = 0;
    private RecyclerView recyclerView;
    private ModeAdapter adapter;
    LingShouPresenter lingShouPresenter;
    private int page_size = 10;
    private int page_index = 1;
    private String keyword = "";
    TextView noData;
    private OrderBean orderBean;
    RelativeLayout activity_main;
    private OrderBean.ListEntity entityTemp;

    PtrFrameLayout ptrFrame;

    public OrderChildFragment(int status) {
        this.status = status;
    }

    public OrderChildFragment(String orderType) {
        this.orderType = orderType;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.order_child_fragment;
    }

    //    http://sunsun.8raw.com/index.php/Webview/Product/detail?id=55

    @Override
    protected void initData() {

//        lingShouPresenter.queryOrder("11",query_status,keyword,page_index,page_size,"itboye");
        //-------------------GridView Style-----------------
        //  final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        //  recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, getResources().getDimensionPixelSize(R.dimen.padding_middle), true));
        //  recyclerView.setHasFixedSize(true);
        //  recyclerView.setLayoutManager(layoutManager);
        //--------------------------------------------------
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        ptrFrame.setLastUpdateTimeRelateObject(this);
        BasePtr.setPagedPtrStyle(ptrFrame);
        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                beginRequest();
                ptrFrame.autoRefresh();//自动刷新
            }
        }, 100);
        ptrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                page_index++;
                beginRequest();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page_index = 1;
                beginRequest();
            }
        });

        IntentFilter intentFilter = new IntentFilter(Const.ORDER_CHANGE);
        getActivity().registerReceiver(receiver, intentFilter);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            beginRequest();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    public void beginRequest() {
        lingShouPresenter = new LingShouPresenter(this);
        if (getSp(Const.UID).equals("")) {
            return;
        }
        lingShouPresenter.queryOrder(getSp(Const.UID), status, keyword, page_index, page_size, getSp(Const.S_ID));
    }

    @Override
    public void onResume() {
        super.onResume();
        beginRequest();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main:
                entityTemp = (OrderBean.ListEntity) v.getTag();
                if (entityTemp.getOrder_status() == 5) {
                    startActivity(new Intent(getActivity(), StorePingJiaActivity.class).putExtra("model", entityTemp));
                } else if (entityTemp.getOrder_status() == 4 && entityTemp.getCs_status() == 0) {
                    startActivity(new Intent(getActivity(), OrderDetailActivity.class).putExtra("model", entityTemp));
                }
                break;
            case R.id.order_pro_status:
                entityTemp = (OrderBean.ListEntity) v.getTag();
                if (entityTemp.getPay_status() == 0) {
                    BuyType buyType=BuyType.Buy_OrderPay;
                    startActivity(new Intent(getActivity(), PayTypeActivity.class).putExtra("model", entityTemp).putExtra("buyType",buyType));
                }

                break;
        }
    }

    ArrayList<OrderBean.ListEntity> listEntityArrayList = new ArrayList<>();

    @Override
    public void onRefresh() {
        beginRequest();
    }

    @Override
    public void onLoadMore() {
        beginRequest();
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        ptrFrame.refreshComplete();
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == LingShouPresenter.queryOrder_success) {
                orderBean = (OrderBean) entity.getData();
                if (page_index==1) {
                    listEntityArrayList.clear();
                }
                listEntityArrayList.addAll(orderBean.getList());
                if (orderBean != null) {
                    if (listEntityArrayList.size() > 0) {
                        noData.setVisibility(View.GONE);
                        if (adapter == null) {
                            adapter = new ModeAdapter(this, R.layout.item_mode, listEntityArrayList);
                            recyclerView.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        noData.setVisibility(View.VISIBLE);
                    }
                } else {
                    noData.setVisibility(View.VISIBLE);
                }
            } else if (entity.getEventType() == LingShouPresenter.queryOrder_success) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
