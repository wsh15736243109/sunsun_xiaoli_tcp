package sunsun.xiaoli.jiarebang.shuizuzhijia.me.messageFragment;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.bean.MessageBean;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kefu_messge;
    }

    @Override
    protected void initData() {
        userPresenter = new UserPresenter(this);
        userPresenter.queryMessage(getSp(Const.UID), 6042, startTime, pageIndex, pageSize);
    }

//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		tvMessge.setText(R.string.xitong_messge);
//		xlistviewMessge.setPullRefreshEnable(false);
//		xlistviewMessge.setPullLoadEnable(false);
//		// xlistviewMessge.setXListViewListener(new IXListViewListener() {
//		//
//		// @Override
//		// public void onRefresh() {
//		// getData();
//		// }
//		//
//		// @Override
//		// public void onLoadMore() {
//		// pullUp();
//		// }
//		//
//		// });
//		// adapter = new ShopCarAdapter(getApplication(), dataList, this);
//		// xlistview.setAdapter(adapter);
//		// adapter=onGetAdapter();
//
//
//		getData();
//
//		/*
//		 * xlistviewMessge.setOnItemClickListener(new OnItemClickListener() {
//		 *
//		 * @Override public void onItemClick(AdapterView<?> parent, View view,
//		 * int position, long id) { position--; Intent intent=new
//		 * Intent(getActivity(), TieZiDetailActivity.class); ZhuTiItemBean
//		 * bean=dataList.get(position); intent.putExtra("fid", bean.getFid());
//		 * intent.putExtra("tid", bean.getTid()); intent.putExtra("img",
//		 * bean.getImg());
//		 *
//		 * intent.putExtra("author", bean.getAuthor());
//		 * intent.putExtra("author_id", bean.getAuthorId());
//		 *
//		 * startActivity(intent); } });
//		 */
//	}
//
//	private void getData() {
//		MyJsonRequest<List<MessageBean>> request = new MyJsonRequest.Builder<List<MessageBean>>()
//				.typeKey("BY_Message_query")
//				.apiVer("100")
//				.param("uid",
//						(String) SPUtils.get(App.ctx, null, SPContants.USER_ID,
//								"")).param("msg_type", "1")
//				.requestListener(new XRequestListener<List<MessageBean>>() {
//
//					@Override
//					public void onResponse(List<MessageBean> arg0) {
//
//						if (arg0 != null) {
//							dataList.addAll(arg0);
//							adapter.notifyDataSetChanged();
//						}
//						if (adapter == null || arg0.size()<0) {
//							layoutNull.setVisibility(View.VISIBLE);
//							xlistviewMessge.setVisibility(View.GONE);
//							textStrimg.setText("暂无系统消息");
//						}
//					}
//				}).errorListener(new XErrorListener() {
//
//					@Override
//					public void onErrorResponse(Exception exception, int code,
//							String msg) {
//
//					}
//				}).build();
//		HttpRequest.getDefaultRequestQueue().add(request);
//	}

    @Override
    public void onClick(View v) {

    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getData());
            } else {
                if (entity.getEventType() == UserPresenter.queryMessage_success) {
                    MessageBean messageBean = (MessageBean) entity.getData();
                    dataList.addAll( messageBean.getList());
                    adapter = new SystemAnnounceAdapter(this,dataList, R.layout.item_systemannounce);
                    xlistviewMessge.setAdapter(adapter);
                } else if (entity.getEventType() == UserPresenter.queryMessage_fail) {
                    MAlert.alert(entity.getData());

                }
            }
        }
    }
}
