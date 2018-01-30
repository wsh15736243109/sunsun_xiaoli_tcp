package sunsun.xiaoli.jiarebang.shuizuzhijia.product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.ProductSearchAdapter;

public class ProductcenterSearchActivity extends BaseActivity implements
        OnClickListener, Observer {
    // private EditText et_search;
    private EditText et_search;
    private ListView listView;
    private Button button, button_btn;
    private ImageView back;
    private BaseAdapter adapter;

    private LinearLayout produceterLiner;
    UserPresenter userPresenter;
    //	List<ShareBean> list = new ArrayList<ShareBean>();
    String name;
    private ArrayList<ProductBean.HomeListBean> homeListBeanArrayList;

    @Override
    @SuppressLint("InlinedApi")
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_produtcenter_search);
        et_search.setHint(R.string.keyword);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //do something;
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                    if (imm.isActive()) {

                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

                    }
                    pullUp();
                    return true;
                }
                return false;
            }
        });
        userPresenter = new UserPresenter(this);
        setStatusBarColor(getResources().getColor(R.color.main_blue));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (homeListBeanArrayList.get(position).getLevel() == 1) {
                    Intent intent = new Intent(
                            ProductcenterSearchActivity.this,
                            ProducenterChildActivity.class);
                    intent.putExtra("cate_id", homeListBeanArrayList.get(position).getId());
                    intent.putExtra("title", homeListBeanArrayList.get(position).getName());
                    startActivity(intent);
                } else if (homeListBeanArrayList.get(position).getLevel() == 2) {
                    Intent intent = new Intent(
                            ProductcenterSearchActivity.this,
                            ProductCenter_message_video_kefu_Activity.class);
                    intent.putExtra("cate_id", homeListBeanArrayList.get(position).getId());
                    intent.putExtra("title", homeListBeanArrayList.get(position).getName());
                    startActivity(intent);
                }
            }
        });
    }

    private void pullUp() {
        name = et_search.getText().toString();
        if (et_search.getText().toString().equals("")) {
            MAlert.alert("输入内容不能为空");
            return;
        }
        userPresenter.productSearch(name);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.button:
                pullUp();
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
                if (entity.getEventType() == UserPresenter.productSearch_success) {
                    homeListBeanArrayList = (ArrayList<ProductBean.HomeListBean>) entity.getData();
                    listView.setAdapter(new ProductSearchAdapter(homeListBeanArrayList));
                } else if (entity.getEventType() == UserPresenter.productSearch_fail) {
                    MAlert.alert(entity.getData());
                }
            }
        }
    }
}
