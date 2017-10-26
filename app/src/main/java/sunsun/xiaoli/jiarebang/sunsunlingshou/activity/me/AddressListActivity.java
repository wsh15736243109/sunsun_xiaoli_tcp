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

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    protected void initData() {
        lingShouPresenter = new LingShouPresenter(this);
        if (getIntent().getStringExtra("title").equals(getString(R.string.choose_address))) {
            li_mylocation.setVisibility(View.GONE);
            btn_sure_address.setVisibility(View.GONE);
        } else if (getIntent().getStringExtra("title").equals(getString(R.string.manage_address))) {
            li_mylocation.setVisibility(View.VISIBLE);
            btn_sure_address.setText(getString(R.string.sure_address));
        }else{
            btn_sure_address.setText(getString(R.string.addnew_address));
        }
        initTitlebarStyle1(this, actionBar, getIntent().getStringExtra("title"), R.mipmap.ic_left_light, "", 0, "删除");
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
                if (getIntent().getStringExtra("title").equals(getString(R.string.choose_address))) {
                    AddressBean addressBeanTemp = null;
                    for (AddressBean addressBean : addressBeanArrayList) {
                        if (addressBean.isSelect()) {
                            addressBeanTemp = addressBean;
                            break;
                        }else{
                            addressBeanTemp = null;
                        }
                    }
                    if (addressBeanTemp==null) {
                        MAlert.alert(getString(R.string.choose_address_at_first));
                        return;
                    }
                    intent = new Intent();
                    intent.putExtra("model", addressBeanTemp);
                    setResult(102, intent);
                } else {
                    startActivity(new Intent(this,AddAddressOrMymessageActivity.class));
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
            case R.id.lay_actionbar_right:
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
                intent = new Intent(this, AddAddressOrMymessageActivity.class);
                intent.putExtra("title", "修改地址");
                intent.putExtra("model", addressBeanArrayList.get(position));
                startActivity(intent);
                break;
            case R.id.rootView:
                if (getIntent().getStringExtra("title").equals(getString(R.string.manage_address)) || getIntent().getStringExtra("title").equals("")) {
                    return;
                }
                position = (int) v.getTag();
                intent = new Intent();
                intent.putExtra("model", addressBeanArrayList.get(position));
                setResult(102, intent);
                finish();
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
                if (getIntent().getStringExtra("title").equals(getString(R.string.choose_address))) {
                    for (AddressBean addressBean : addressBeanArrayList) {
                        addressBean.setShow(true);
                    }
                    li_mylocation.setVisibility(View.VISIBLE);
                }
                if (adapter == null) {
                    adapter = new AddressListAdapter(this, R.layout.item_address, addressBeanArrayList);
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
            }
        }
    }
}
