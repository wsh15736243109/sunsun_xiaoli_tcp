package sunsun.xiaoli.jiarebang.shuizuzhijia.me.messageFragment;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.bean.MessageBean;
import com.itboye.pondteam.custom.ptr.BasePtr;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.SystemAnnounceAdapter;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

public class MessageXiTongFragmet extends LingShouBaseFragment implements Observer {

    private ListView xlistviewMessge;
    private int pageNum = 1;

    private SystemAnnounceAdapter adapter;

    private TextView tvMessge;
    List<MessageBean.MessageArrayEntity> dataList = new ArrayList<>();
    TextView textStrimg;

    UserPresenter userPresenter;
    int startTime = -1, pageIndex = 1, pageSize = 10;

    PtrFrameLayout ptr_framelayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kefu_messge;
    }

    @Override
    protected void initData() {
        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
//        SimpleHeader header = new SimpleHeader(getActivity());
//        header.setTextColor(0xff0066aa);
//        header.setCircleColor(0xff33bbee);
//        xlistviewMessge.setHeadable(header);
//
//        // 设置加载更多的样式（可选）
//        SimpleFooter footer = new SimpleFooter(getActivity());
//        footer.setCircleColor(0xff33bbee);
//        xlistviewMessge.setFootable(footer);
//        // 下拉刷新事件回调（可选）
//        xlistviewMessge.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
//            @Override
//            public void onStart() {
//                pageIndex = 1;
//                queryMessage();
//            }
//        });
//
//        // 加载更多事件回调（可选）
//        xlistviewMessge.setOnLoadMoreStartListener(new  ZrcListView.OnStartListener() {
//            @Override
//            public void onStart() {
//                pageIndex++;
//                queryMessage();
//            }
//        });
//        xlistviewMessge.refresh(); // 主动下拉刷新
//        xlistviewMessge.startLoadMore();
        BasePtr.setPagedPtrStyle(ptr_framelayout);
//        xlistviewMessge.setPullLoadEnable(false);
//        xlistviewMessge.setPullRefreshEnable(false);
//        xlistviewMessge.setXListViewListener(new XListView.IXListViewListener() {
//            @Override
//            public void onRefresh() {
//                pageIndex = 1;
//                queryMessage();
//            }
//
//            @Override
//            public void onLoadMore() {
//                pageIndex++;
//                queryMessage();
//            }
//        });
//        ptr_framelayout.setLoadMore(true);
//        ptr_framelayout.setMaterialRefreshListener(new MaterialRefreshListener() {
//            @Override
//            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
//                pageIndex = 1;
//                queryMessage();
//            }
//
//            @Override
//            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
//                // TODO Auto-generated method stub
//                super.onRefreshLoadMore(materialRefreshLayout);
//
//                pageIndex++;
//                queryMessage();
//
//            }
//        });
        ptr_framelayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                pageIndex++;
                queryMessage();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageIndex = 1;
                queryMessage();
            }
        });
//        xlistviewMessge.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.v("request_params","ontouch");
//                return true;
//            }
//        });
        adapter = new SystemAnnounceAdapter(this, dataList, R.layout.item_systemannounce);
        xlistviewMessge.setAdapter(adapter);
        userPresenter = new UserPresenter(this);
        queryMessage();
    }

    private void queryMessage() {
        userPresenter.queryMessage(getSp(Const.UID), 6042, startTime, pageIndex, pageSize);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);//关闭刷新加载按钮
        ptr_framelayout.refreshComplete(); // 通知加载成功
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getData());
            } else {
                if (entity.getEventType() == UserPresenter.queryMessage_success) {
                    MessageBean messageBean = (MessageBean) entity.getData();
                    if (pageIndex==1) {
                        dataList.clear();
                    }
                    dataList.addAll(messageBean.getList());
                    adapter.notifyDataSetChanged();
                } else if (entity.getEventType() == UserPresenter.queryMessage_fail) {
                    MAlert.alert(entity.getData());
                }
            }
        }
    }
}
