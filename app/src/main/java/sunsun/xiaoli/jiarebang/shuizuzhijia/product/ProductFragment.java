package sunsun.xiaoli.jiarebang.shuizuzhijia.product;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    RelativeLayout re_search;
    ImageView img_back;
    TextView txt_title;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product;
    }

    @Override
    protected void initData() {
        userPresenter = new UserPresenter(this);
        userPresenter.queryProductIndex(0);
        initTop();
        initItemClick();
    }

    private void initTop() {
        txt_title.setText(getString(R.string.product_server_center));
        txt_title.setTextColor(getResources().getColor(R.color.main_green));
        img_back.setVisibility(View.GONE);
    }

    private void initItemClick() {
        listviewxl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), ProducenterChildActivity.class).putExtra("cate_id", homeListBeanArrayList.get(position).getId()).putExtra("title",homeListBeanArrayList.get(position).getName()));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_search:
                Intent intent = new Intent(getActivity(),
                        ProductcenterSearchActivity.class);
                startActivity(intent);
                break;
        }
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
                    listviewxl.setAdapter(new ProductAdapter(getActivity(), homeListBeanArrayList));
                } else if (entity.getEventType() == UserPresenter.queryProductIndex_fail) {
                    MAlert.alert(entity.getData());
                }
            }
        }
    }
}
