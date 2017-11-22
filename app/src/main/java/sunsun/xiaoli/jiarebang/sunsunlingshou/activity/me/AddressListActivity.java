package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.lingshouadapter.AddressListAdapter;
import sunsun.xiaoli.jiarebang.beans.AddressBean;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.AddAddressOrMymessageActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class AddressListActivity extends LingShouBaseActivity implements Observer {

    TranslucentActionBar actionBar;
    RecyclerView recyclerview_address;
    private AddressListAdapter adapter;
    ImageView iv_actionbar_left;
    Button btn_sure_address;
    TextView txt_relocation, txt_add_address;
    LingShouPresenter lingShouPresenter;
    private ArrayList<AddressBean> addressBeanArrayList = new ArrayList<>();
    RelativeLayout lay_actionbar_right;
    private Intent intent;
    LinearLayout li_mylocation;
    private String action;
    TextView tv_actionbar_right;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    protected void initData() {
        lingShouPresenter = new LingShouPresenter(this);
        action = getIntent().getStringExtra("action");
        if (action != null) {
            if (action.equals("manage_address")) {
                li_mylocation.setVisibility(View.GONE);
                btn_sure_address.setText(getString(R.string.addnew_address));
                initTitlebarStyle1(this, actionBar, getIntent().getStringExtra("title"), R.mipmap.ic_left_light, "", 0, "");
            } else if (action.equals("location_address")) {
                li_mylocation.setVisibility(View.VISIBLE);
                btn_sure_address.setText(getString(R.string.sure_address));
                initTitlebarStyle1(this, actionBar, getIntent().getStringExtra("title"), R.mipmap.ic_left_light, "", 0, "删除");
            } else {
                btn_sure_address.setText(getString(R.string.addnew_address));
                li_mylocation.setVisibility(View.GONE);

                initTitlebarStyle1(this, actionBar, getIntent().getStringExtra("title"), R.mipmap.ic_left_light, "", 0, "");
            }
        }
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview_address.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.btn_sure_address:
                if (action.equals("choose_address") || action.equals("manage_address")) {
                    //添加地址
                    startActivity(new Intent(this, AddAddressOrMymessageActivity.class));
                } else if (action.equals("location_address")) {
                    //设置默认地址
                    int count = 0;
                    AddressBean addressSelect=null;
                    for (int i = 0; i < addressBeanArrayList.size(); i++) {
                        if (addressBeanArrayList.get(i).isSelect()) {
                            addressSelect=addressBeanArrayList.get(i);
                            count++;
                        }
                    }
                    if (count <= 0) {
                        MAlert.alert(getString(R.string.choose_address_at_first));
                        return;
                    }

                    if (count > 1) {
                        MAlert.alert(getString(R.string.only_one_address_set_default));
                        return;
                    }
                    String json=new Gson().toJson(addressSelect);
                    SPUtils.put(this,null,Const.SELECT_ADDRESS,json);
//                    lingShouPresenter.setDefaultAddress(getSp(Const.UID),addressSelect.getId(),getSp(Const.S_ID));
                } else {
                }
                break;
            case R.id.txt_relocation://重新定位
                break;
            case R.id.txt_add_address:
                intent = new Intent(this, AddAddressOrMymessageActivity.class);
                intent.putExtra("title", "新增地址");
                startActivity(intent);
                break;
            case R.id.arrow_right:
                int position = (int) v.getTag();
                addressBeanArrayList.get(position).setSelect(!addressBeanArrayList.get(position).isSelect());
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_actionbar_right:
                StringBuffer id = new StringBuffer();
                for (int i = 0; i < addressBeanArrayList.size(); i++) {
                    if (addressBeanArrayList.get(i).isSelect()) {
                        id.append(addressBeanArrayList.get(i).getId() + ",");
                    }
                }
                if (id.toString().length() <= 0) {
                    MAlert.alert("请先选择要删除的地址");
                    return;
                }
                id = new StringBuffer(id.toString().endsWith(",") ? id.toString().substring(0, id.toString().length() - 1) : id.toString());
                lingShouPresenter.deleteAddress(getSp(Const.UID), id.toString(), getSp(Const.S_ID));
                break;
            case R.id.txt_update:
                position = (int) v.getTag();
                if (((TextView)v).getText().equals(getString(R.string.update_address))) {
                    //修改地址
                    intent = new Intent(this, AddAddressOrMymessageActivity.class);
                    intent.putExtra("title", "修改地址");
                    intent.putExtra("model", addressBeanArrayList.get(position));
                    startActivity(intent);
                }else{
                    //删除地址
                    lingShouPresenter.deleteAddress(getSp(Const.UID), addressBeanArrayList.get(position).getId(), getSp(Const.S_ID));
                }
                break;
            case R.id.rootView:
                position = (int) v.getTag();
                if (action.equals("choose_address")) {
                    //确认订单点击选择地址
                    intent = new Intent();
                    intent.putExtra("model", addressBeanArrayList.get(position));
                    setResult(102, intent);
                    finish();
                } else if (action.equals("my_address")) {
                    //我的地址进入查看地址详情
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        lingShouPresenter.queryAddress(getSp(Const.UID), getSp(Const.S_ID));//获取地址列表;
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == LingShouPresenter.queryAddress_success) {
                ArrayList<AddressBean> ar = (ArrayList<AddressBean>) entity.getData();
                addressBeanArrayList.clear();
                addressBeanArrayList.addAll(ar);
                if (getIntent().getStringExtra("action").equals("location_address")) {
                    for (AddressBean addressBean : addressBeanArrayList) {
                        addressBean.setShow(true);
                    }
                }
                if (adapter == null) {
                    adapter = new AddressListAdapter(this, R.layout.item_address, addressBeanArrayList);
                    if (action.equals("location_address")) {
                        adapter.setUpdate(true);
                    }
                    recyclerview_address.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            } else if (entity.getEventType() == LingShouPresenter.queryAddress_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.deleteAddress_success) {
                lingShouPresenter.queryAddress(getSp(Const.UID), getSp(Const.S_ID));//获取地址列表;
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.deleteAddress_fail) {
                MAlert.alert(entity.getData());
            }else if (entity.getEventType() == LingShouPresenter.setDefaultAddress_success) {
                lingShouPresenter.queryAddress(getSp(Const.UID), getSp(Const.S_ID));//获取地址列表;
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.setDefaultAddress_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
