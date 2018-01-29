package sunsun.xiaoli.jiarebang.shuizuzhijia.product;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.bean.ProductBean;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.ProductListAdapter;

public class ProducenterProducenterFragemnt extends LingShouBaseFragment implements Observer {


    private ListView xlistview;
    private int pageNum = 1;
    private int pageSize = 10;
    private BaseAdapter adapter;
//	 List<DeleteBeanProducer>dataList=new ArrayList<>();

    private RelativeLayout topbar;
    int cate_id;

    UserPresenter userPresenter;
    private ProductBean homeListBeanArrayList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cahkan_gengduo_pinglun;
    }

    @Override
    protected void initData() {
        cate_id = getActivity().getIntent().getIntExtra("cate_id", 0);
        userPresenter = new UserPresenter(this);
        userPresenter.queryProductPost(cate_id, 0, pageNum, pageSize);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    @Override
    public void onClick(View v) {

    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
            } else {
                if (entity.getEventType() == UserPresenter.queryProductPost_success) {
                    homeListBeanArrayList = (ProductBean) entity.getData();
                    xlistview.setAdapter(new ProductListAdapter(homeListBeanArrayList));
                } else if (entity.getEventType() == UserPresenter.queryProductPost_fail) {
                    MAlert.alert(entity.getData());
                }
            }
        }
    }
}
