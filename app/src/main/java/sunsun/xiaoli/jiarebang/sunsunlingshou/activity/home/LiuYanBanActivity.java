package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.bean.MessageListBean;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.LiuYanBanAdapter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class LiuYanBanActivity extends LingShouBaseActivity implements Observer {

    RecyclerView recyclerView;
    LingShouPresenter lingShouPresenter;
    private String msgType = "0";
    TranslucentActionBar actionBar;
    private LiuYanBanAdapter liuYanBanAdapter;
    ArrayList<MessageListBean> arLiuYanBan=new ArrayList<>();
    ImageView iv_actionbar_left;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_liu_lan_ban;
    }

    @Override
    protected void initData() {
        initTitlebarStyle1(this,actionBar,"留言板",R.mipmap.ic_left_light,"",0,"");
        lingShouPresenter = new LingShouPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lingShouPresenter.getMessageList(getSp(Const.UID), msgType);//获取留言板消息
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_root:
                startActivity(new Intent(this,ChatActivity.class).putExtra("from_id",v.getTag().toString()));
                break;
            case R.id.iv_actionbar_left:
                finish();
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity=handlerError(data);
        if (entity!=null) {
            if (entity.getCode()!=0) {
                MAlert.alert(entity.getMsg());
                return;
            }

            if (entity.getEventType()==LingShouPresenter.getMessageList_success) {
                ArrayList<MessageListBean> messageListBean= (ArrayList<MessageListBean>) entity.getData();
                arLiuYanBan.clear();
                arLiuYanBan.addAll(messageListBean);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
                recyclerView.setLayoutManager(linearLayoutManager);
                if (liuYanBanAdapter==null) {
                    liuYanBanAdapter =new LiuYanBanAdapter(LiuYanBanActivity.this,R.layout.item_liuyanban,arLiuYanBan);
                    recyclerView.setAdapter(liuYanBanAdapter);
                }else{
                    liuYanBanAdapter.notifyDataSetChanged();
                }
            }else if (entity.getEventType()==LingShouPresenter.getMessageList_fail) {
                MAlert.alert(entity.getMsg());
            }
        }
    }
}
