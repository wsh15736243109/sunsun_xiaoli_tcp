package sunsun.xiaoli.jiarebang.shuizuzhijia.me.messageFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.utils.SPUtils;
import com.itboye.pondteam.volley.XErrorListener;
import com.pobing.uilibs.feature.view.XListView;

import java.util.ArrayList;
import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;

public class KeFuMeFragment extends LingShouBaseFragment {

    private XListView xlistviewMessge;
    private int pageNum = 1;
    private RelativeLayout title_tbae;
    private BaseAdapter adapter;


    private TextView tvMessge, textStrimg;
    //	List<KeFuBeans> dataList = new ArrayList<KeFuBeans>();
    LinearLayout layoutNull;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kefu_messge;
    }

    @Override
    protected void initData() {

    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
////        xlistviewMessge.setPullRefreshEnable(false);
////        xlistviewMessge.setPullLoadEnable(false);
////
////        adapter = new KeFuAdapter(dataList);
////        xlistviewMessge.setAdapter(adapter);
////
////        pullDown();
//
//
//    }

//    private void pullDown() {
//
//        int page_num = 1;
//        int page_size = 10;
//        String uid = (String) SPUtils.get(App.ctx, null, SPContants.USER_ID,
//                "");
//        MyJsonRequest.Builder<KefuUIBeans> builder = new MyJsonRequest.Builder<KefuUIBeans>()
//                .apiVer("100").typeKey("BY_Customer_servicehis")
//                .param("uid", uid)
//                .param("page_no", page_num)
//                .param("page_size", page_size)
//                .requestListener(new XRequestListener<KefuUIBeans>() {
//
//                    @Override
//                    public void onResponse(final KefuUIBeans bean) {
//
//
//                        dataList.clear();
//                        if (bean.getList() != null) {
//                            dataList.addAll(bean.getList());
//                            adapter.notifyDataSetChanged();
//                        }
//                        if (adapter == null || bean.getList() == null) {
//                            layoutNull.setVisibility(View.VISIBLE);
//                            xlistviewMessge.setVisibility(View.GONE);
//                            textStrimg.setText("暂无客服消息");
//                        }
//                    }
//
//                }).errorListener(new XErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(Exception exception, int code,
//                                                String msg) {
//                        // onPullDownError(adapter, xlistview, exception, code,
//                        // msg);
//                    }
//                });
//
//        MyJsonRequest<KefuUIBeans> request = builder.build();
//        HttpRequest.getDefaultRequestQueue().add(request);
//    }


    @Override
    public void onClick(View v) {

    }
}
