package sunsun.xiaoli.jiarebang.shuizuzhijia.me.messageFragment;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.bean.ChatBean;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;
import com.itboye.pondteam.custom.ptr.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.KeFuAdapter;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

public class KeFuMeFragment extends LingShouBaseFragment implements Observer {

    private ListView xlistviewMessge;
    private RelativeLayout title_tbae;
    private BaseAdapter adapter;

    private TextView tvMessge, textStrimg;
    List<ChatBean.ChatItem> dataList = new ArrayList<>();
    LinearLayout layoutNull;

    UserPresenter userPresenter;

    int pageNo = 1, pageSize = 10;

    PtrFrameLayout ptr_framelayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kefu_messge;
    }

    @Override
    protected void initData() {
        BasePtr.setPagedPtrStyle(ptr_framelayout);
        ptr_framelayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                pageNo++;
                queryMessage();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNo = 1;
                queryMessage();
            }
        } );
        adapter = new KeFuAdapter(dataList);
        xlistviewMessge.setAdapter(adapter);
        userPresenter = new UserPresenter(this);
        queryMessage();
    }

    private void queryMessage() {
        userPresenter.queryMyKeFuMessage(getSp(Const.UID), pageNo, pageSize);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        ptr_framelayout.refreshComplete();
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getData());
            } else {
                if (entity.getEventType() == UserPresenter.queryMyKeFuMessage_success) {
                    ChatBean chatBean = (ChatBean) entity.getData();
                    ArrayList<ChatBean.ChatItem> chatItemArrayList = chatBean.getList();
                    if (pageNo == 1) {
                        dataList.clear();
                    } else {
                    }
                    dataList.addAll(chatItemArrayList);
                    adapter.notifyDataSetChanged();
                } else if (entity.getEventType() == UserPresenter.queryMyKeFuMessage_success) {
                    MAlert.alert(entity.getData());
                }
            }
        }
    }
}
