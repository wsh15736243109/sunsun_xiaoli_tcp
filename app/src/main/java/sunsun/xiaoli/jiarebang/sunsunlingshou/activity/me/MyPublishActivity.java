package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.custom.ptr.BasePtr;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.lingshouadapter.MyPublishAdapter;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.MyPublishBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;

import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class MyPublishActivity extends LingShouBaseActivity implements IRecycleviewClick, Observer {

    Button btn_publish;
    TranslucentActionBar actionBar;
    private MyPublishAdapter adapter;
    private ArrayList<MyPublishBean.PublishBean> publishArraylist;
    RecyclerView recyclerView;
    ImageView iv_actionbar_left;
    LingShouPresenter lingShouPresenter;
    PtrFrameLayout ptrFrame;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_publish;
    }

    @Override
    protected void initData() {
        lingShouPresenter = new LingShouPresenter(this);
//        lingShouPresenter.getMyPublish(getSp(Const.UID));
        BasePtr.setPagedPtrStyle(ptrFrame);
        ptrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                lingShouPresenter.getMyPublish("6");
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                lingShouPresenter.getMyPublish("6");
            }
        });

        initTitlebarStyle1(this, actionBar, "我的发布", R.mipmap.ic_left_light, "", 0, "");
        publishArraylist = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyPublishAdapter(this, publishArraylist);
        adapter.setOnItemListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lingShouPresenter.getMyPublish("6");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_publish:
                startActivity(new Intent(this, AddPublishActivity.class));
                break;
            case R.id.iv_actionbar_left:
                finish();
                break;
        }
    }


    @Override
    public void onItemClick(int position) {
        Intent id = new Intent(this, MyPublishDetailActivity.class);
        MyPublishBean.PublishBean publishBean = publishArraylist.get(position);

        id.putExtra("id",publishBean.getId());
        Log.i("============",publishBean.getId()+"======");
        startActivity(id);

    }

    @Override
    public void onItemLongClick(int position) {

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
            if (entity.getEventType() == LingShouPresenter.getMyPublish_success) {
                MyPublishBean arrayList = (MyPublishBean) entity.getData();
                publishArraylist.clear();
                if (arrayList != null) {
                    publishArraylist.addAll(arrayList.getList());
                    adapter.notifyDataSetChanged();
                }
            } else if (entity.getEventType() == LingShouPresenter.getMyPublish_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
