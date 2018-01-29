package sunsun.xiaoli.jiarebang.shuizuzhijia.product;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.bean.ProductBean;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.ProductAdapter;

/**
 * Created by Administrator on 2018/1/24.
 */

public class ProductFragment extends LingShouBaseFragment implements Observer {


    GridView listviewxl;
    UserPresenter userPresenter;
    private ArrayList<ProductBean.HomeListBean> homeListBeanArrayList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product;
    }

    @Override
    protected void initData() {
        userPresenter = new UserPresenter(this);
        userPresenter.queryProductIndex(0);
        initItemClick();
    }

    private void initItemClick() {
        listviewxl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(),ProducenterChildActivity.class).putExtra("cate_id",homeListBeanArrayList.get(position).getId()));
            }
        });
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
                if (entity.getEventType() == UserPresenter.queryProductIndex_success) {
                   homeListBeanArrayList = (ArrayList<ProductBean.HomeListBean>) entity.getData();
                    listviewxl.setAdapter(new ProductAdapter(getActivity(),homeListBeanArrayList));
                } else if (entity.getEventType() == UserPresenter.queryProductIndex_fail) {
                    MAlert.alert(entity.getData());
                }
            }
        }
    }
}
