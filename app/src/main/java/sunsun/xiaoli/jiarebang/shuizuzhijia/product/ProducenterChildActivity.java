package sunsun.xiaoli.jiarebang.shuizuzhijia.product;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.ProductBean;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.ProductAdapter;

public class ProducenterChildActivity extends BaseActivity implements
        OnClickListener, Observer {
    BaseAdapter adapter;
    GridView gridviewProducenter;
    String title;
    TextView txt_title;
    ImageView close_icon;
    UserPresenter userPresenter;
    int parent;
    private ArrayList<ProductBean.HomeListBean> homeListBeanArrayList;
    ImageView img_back;
    @Override
    @SuppressLint("InlinedApi")
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_producenter_gridview);

        title = getIntent().getStringExtra("title");
        txt_title.setText(title);
        parent = getIntent().getIntExtra("cate_id", 0);
        userPresenter = new UserPresenter(this);
        gridviewProducenter.setVerticalSpacing(1);
        gridviewProducenter.setHorizontalSpacing(1);
        pullDown();

        initItemClick();
    }

    private void initItemClick() {
        gridviewProducenter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ProducenterChildActivity.this, ProductCenter_message_video_kefu_Activity.class)
                        .putExtra("title",homeListBeanArrayList.get(position).getName())
                        .putExtra("cate_id",homeListBeanArrayList.get(position).getId()));
            }
        });
    }

    private void pullDown() {
        userPresenter.queryProductIndex(parent);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            default:
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
                    gridviewProducenter.setAdapter(new ProductAdapter(this, homeListBeanArrayList,1f));
                } else if (entity.getEventType() == UserPresenter.queryProductIndex_fail) {
                    MAlert.alert(entity.getData());
                }
            }
        }
    }
}
