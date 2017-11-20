package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.shopcart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.interfaces.IRecyclerviewclicklistener;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;
import com.yanzhenjie.loading.LoadingView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.lingshouadapter.ShopCartAdapter;
import sunsun.xiaoli.jiarebang.beans.GoodsDetailBean;
import sunsun.xiaoli.jiarebang.beans.ShopCartBean;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.BuyType;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.refreshrecyvlerview.callback.PullToRefreshListener;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

/**
 * Created by Administrator on 2017/6/23.
 */

@SuppressLint("ValidFragment")
public class ShopCartChildFragment extends LingShouBaseFragment implements PullToRefreshListener, IRecyclerviewclicklistener, Observer {
    private SwipeMenuRecyclerView recyclerView;
    private ShopCartAdapter adapter;
    RelativeLayout shopcart_li;
    RelativeLayout li_bottom;
    TextView tv_go_to_pay;
    LingShouPresenter lingShouPresenter;
    int product_type = 1;
    private ArrayList<ShopCartBean> arrayList = new ArrayList<>();
    TextView noData, heji;
//    PtrFrameLayout ptrFrame;
    CheckBox all_chekbox;
    TranslucentActionBar actionBar;
//    VpSwipeRefreshLayout swipe_layout;
    SwipeRefreshLayout refresh_layout;
    public ShopCartChildFragment(int product_type) {
        this.product_type = product_type;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.shopcart_child_fragment;
    }

    @Override
    protected void initData() {

        //初始actionBar
        actionBar.setData("购物车", 0, "", 0, "", null);
        //开启渐变
//        actionBar.setNeedTranslucent();
        //设置状态栏高度
        actionBar.setStatusBarHeight(getStatusBarHeight());
        actionBar.setBarBackgroundColor(getResources().getColor(R.color.main_yellow));
        refresh_layout.setOnRefreshListener(mRefreshListener); // 刷新监听。
        beginRequest();
        shopcart_li.setPadding(0, 0, 0, 0);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DefaultItemDecoration defaultItem=new DefaultItemDecoration(ContextCompat.getColor(getActivity(), R.color.gray_eee));
        defaultItem.setmDividerHeight(getResources().getDimensionPixelSize(R.dimen.DIMEN_20DP));
        recyclerView.addItemDecoration(defaultItem);
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);

//        // 自定义的核心就是DefineLoadMoreView类。
//        DefineLoadMoreView loadMoreView = new DefineLoadMoreView(getActivity());
//        recyclerView.addFooterView(loadMoreView); // 添加为Footer。
//        recyclerView.setLoadMoreView(loadMoreView); // 设置LoadMoreView更新监听。
//        recyclerView.setLoadMoreListener(mLoadMoreListener); // 加载更多的监听。
        recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        li_bottom.setVisibility(View.VISIBLE);
    }

    /**
     * 刷新。
     */
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            beginRequest();
        }
    };
    /**
     * 加载更多。
     */
    private SwipeMenuRecyclerView.LoadMoreListener mLoadMoreListener = new SwipeMenuRecyclerView.LoadMoreListener() {
        @Override
        public void onLoadMore() {

            beginRequest();
//            recyclerView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
////                    List<String> codeArray = createDataList(mAdapter.getItemCount());
////                    mDataList.addAll(codeArray);
////                    // notifyItemRangeInserted()或者notifyDataSetChanged().
////                    mAdapter.notifyItemRangeInserted(mDataList.size() - codeArray.size(), codeArray.size());
//
//                    // 数据完更多数据，一定要掉用这个方法。
//                    // 第一个参数：表示此次数据是否为空。
//                    // 第二个参数：表示是否还有更多数据。
//                    begin
//                    // 如果加载失败调用下面的方法，传入errorCode和errorMessage。
//                    // errorCode随便传，你自定义LoadMoreView时可以根据errorCode判断错误类型。
//                    // errorMessage是会显示到loadMoreView上的，用户可以看到。
//                    // mRecyclerView.loadMoreError(0, "请求网络失败");
//                }
//            }, 1000);
        }
    };
    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {


            int width = getResources().getDimensionPixelSize(R.dimen.DIMEN_90DP);
            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            SwipeMenuItem addItem = new SwipeMenuItem(getActivity())
//                    .setBackground(R.drawable.selector_orange)// 点击的背景。
////                    .setImage(R.mipmap.ic_action_add) // 图标。
//                    .setWidth(width) // 宽度。
//                    .setHeight(height); // 高度。
//            swipeLeftMenu.addMenuItem(addItem); // 添加一个按钮到左侧菜单。

            SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                    .setBackground(R.drawable.selector_orange)
//                    .setImage(R.mipmap.ic_action_delete) // 图标。
                    .setText("删除") // 文字。
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(16) // 文字大小。
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。.

            // 上面的菜单哪边不要菜单就不要添加。
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                lingShouPresenter.deleteShopCart(getSp(Const.UID), arrayList.get(adapterPosition).getId(), getSp(Const.S_ID));
//                Toast.makeText(ViewTypeActivity.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
//                Toast.makeText(ViewTypeActivity.this, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };

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
        refresh_layout.setRefreshing(false);
        recyclerView.loadMoreFinish(false, true);
//        ptrFrame.refreshComplete();
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





    /**
     * 这是这个类的主角，如何自定义LoadMoreView。
     */
    static final class DefineLoadMoreView extends LinearLayout implements SwipeMenuRecyclerView.LoadMoreView, View.OnClickListener {

        private LoadingView mLoadingView;
        private TextView mTvMessage;

        private SwipeMenuRecyclerView.LoadMoreListener mLoadMoreListener;

        public DefineLoadMoreView(Context context) {
            super(context);
            setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            setGravity(Gravity.CENTER);
            setVisibility(GONE);

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

            int minHeight = (int) (displayMetrics.density * 60 + 0.5);
            setMinimumHeight(minHeight);

            inflate(context, R.layout.layout_fotter_loadmore, this);
            mLoadingView = (LoadingView) findViewById(R.id.loading_view);
            mTvMessage = (TextView) findViewById(R.id.tv_message);

            int color1 = ContextCompat.getColor(getContext(), R.color.colorPrimary);
            int color2 = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);
            int color3 = ContextCompat.getColor(getContext(), R.color.colorAccent);

            mLoadingView.setCircleColors(color1, color2, color3);
            setOnClickListener(this);
        }

        /**
         * 马上开始回调加载更多了，这里应该显示进度条。
         */
        @Override
        public void onLoading() {
            setVisibility(VISIBLE);
            mLoadingView.setVisibility(VISIBLE);
            mTvMessage.setVisibility(VISIBLE);
            mTvMessage.setText("正在努力加载，请稍后");
        }

        /**
         * 加载更多完成了。
         *
         * @param dataEmpty 是否请求到空数据。
         * @param hasMore   是否还有更多数据等待请求。
         */
        @Override
        public void onLoadFinish(boolean dataEmpty, boolean hasMore) {
            if (!hasMore) {
                setVisibility(VISIBLE);

                if (dataEmpty) {
                    mLoadingView.setVisibility(GONE);
                    mTvMessage.setVisibility(VISIBLE);
                    mTvMessage.setText("暂时没有数据");
                } else {
                    mLoadingView.setVisibility(GONE);
                    mTvMessage.setVisibility(VISIBLE);
                    mTvMessage.setText("没有更多数据啦");
                }
            } else {
                setVisibility(INVISIBLE);
            }
        }

        /**
         * 调用了setAutoLoadMore(false)后，在需要加载更多的时候，这个方法会被调用，并传入加载更多的listener。
         */
        @Override
        public void onWaitToLoadMore(SwipeMenuRecyclerView.LoadMoreListener loadMoreListener) {
            this.mLoadMoreListener = loadMoreListener;

            setVisibility(VISIBLE);
            mLoadingView.setVisibility(GONE);
            mTvMessage.setVisibility(VISIBLE);
            mTvMessage.setText("点我加载更多");
        }

        /**
         * 加载出错啦，下面的错误码和错误信息二选一。
         *
         * @param errorCode    错误码。
         * @param errorMessage 错误信息。
         */
        @Override
        public void onLoadError(int errorCode, String errorMessage) {
            setVisibility(VISIBLE);
            mLoadingView.setVisibility(GONE);
            mTvMessage.setVisibility(VISIBLE);

            // 这里要不直接设置错误信息，要不根据errorCode动态设置错误数据。
            mTvMessage.setText(errorMessage);
        }

        /**
         * 非自动加载更多时mLoadMoreListener才不为空。
         */
        @Override
        public void onClick(View v) {
            if (mLoadMoreListener != null) mLoadMoreListener.onLoadMore();
        }
    }

    protected List<String> createDataList(int start) {
        List<String> strings = new ArrayList<>();
        for (int i = start; i < start + 20; i++) {
            strings.add("第" + i + "个Item");
        }
        return strings;
    }

}
