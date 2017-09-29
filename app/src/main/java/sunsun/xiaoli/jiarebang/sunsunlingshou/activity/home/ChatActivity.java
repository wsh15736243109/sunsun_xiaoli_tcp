package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.itboye.pondteam.bean.MessageListBean;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.BaseOtherActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.MessageDetailAdapter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class ChatActivity extends BaseOtherActivity implements Observer, SwipeRefreshLayout.OnRefreshListener {

    ListView listView;
    Button btn_send;
    String from_id;
    LingShouPresenter lingShouPresenter;
    private String msg_type = "6074", title, summary, content, extra;
    TranslucentActionBar actionBar;
    ImageView iv_actionbar_left;
    EditText edt_comments;
    SwipeRefreshLayout id_swipe_ly;
    private MessageDetailAdapter messageDetailAdapter;
    ArrayList<MessageListBean> arMessage=new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initData() {
        setStatusBarColor(R.color.main_yellow);
        initTitlebarStyle1(this, actionBar, "留言板", R.mipmap.ic_left_light, "", 0, "");
        from_id = getIntent().getStringExtra("from_id");
        //设置向下拉多少出现刷新
        id_swipe_ly.setDistanceToTriggerSync(150);
        id_swipe_ly.setColorSchemeColors(getResources().getColor(R.color.main_green));
        id_swipe_ly.setOnRefreshListener(this);
        id_swipe_ly.setRefreshing(true);
        lingShouPresenter = new LingShouPresenter(this);
        beginRequest();
    }

    private void beginRequest() {
        lingShouPresenter.getChatDetail(getSp(Const.UID), from_id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                content = edt_comments.getText().toString();
                if (content.isEmpty()) {
                    MAlert.alert("请输入回复内容");
                    return;
                }
                lingShouPresenter.addMessage(getSp(Const.UID), from_id, msg_type, title, summary, content, extra);
                break;
            case R.id.iv_actionbar_left:
                finish();
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        id_swipe_ly.setRefreshing(false);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == LingShouPresenter.getChatDetail_success) {
                ArrayList<MessageListBean> messageListBean = (ArrayList<MessageListBean>) entity.getData();
                arMessage.clear();
                arMessage.addAll(messageListBean);
                if (messageDetailAdapter==null) {
                    messageDetailAdapter = new MessageDetailAdapter(this, arMessage);
                    listView.setAdapter(messageDetailAdapter);
                }else{
                    messageDetailAdapter.notifyDataSetChanged();
                }
                if (messageDetailAdapter.getCount()>0) {
                    listView.setSelection(messageDetailAdapter.getCount()-1);
                }
            } else if (entity.getEventType() == LingShouPresenter.getChatDetail_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.addMessage_success) {
                MAlert.alert(entity.getData());
                edt_comments.setText("");
                InputMethodManager imm = (InputMethodManager) App.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edt_comments.getWindowToken(), 0);
                beginRequest();
            } else if (entity.getEventType() == LingShouPresenter.addMessage_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    @Override
    public void onRefresh() {
        beginRequest();
    }
}
