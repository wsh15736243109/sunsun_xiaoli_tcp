package sunsun.xiaoli.jiarebang.sunsunlingshou.activity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.AddressBean;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.AddressFragment;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

public class AddAddressOrMymessageActivity extends LingShouBaseActivity implements Observer, AddressFragment.GetInforListener {

    TranslucentActionBar actionBar;
    ImageView iv_actionbar_left;
    Button btn_save;
    RelativeLayout locationrela;
    String title;
    LingShouPresenter lingShouPresenter;
    EditText nameedit, phoneedit, detail, zipcode;
    TextView provincetext;
    String provinceId, province, city, cityid, area, areaid, country_id = "+86", country = "中国", lng = getSp(Const.LNG), lat = getSp(Const.LAT);
    AddressBean addressBean;
    private int is_default;
    CheckBox checkBox;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addaddress;
    }

    @Override
    protected void initData() {
        lingShouPresenter = new LingShouPresenter(this);
        title = getIntent().getStringExtra("title");
        addressBean = (AddressBean) getIntent().getSerializableExtra("model");
        //初始actionBar
        actionBar.setData(title, R.mipmap.ic_left_light, "", 0, "", null);
        //开启渐变
//        actionBar.setNeedTranslucent();
        //设置状态栏高度
        actionBar.setStatusBarHeight(getStatusBarHeight());
        actionBar.setBarBackgroundColor(getResources().getColor(R.color.main_yellow));
        if (addressBean != null) {
            //修改地址
            nameedit.setText(addressBean.getContactname());
            phoneedit.setText(addressBean.getMobile());
            provincetext.setText(addressBean.getProvince() + addressBean.getCity() + addressBean.getArea());
            detail.setText(addressBean.getDetailinfo());
            zipcode.setText(addressBean.getPostal_code());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.btn_save:
                String name = nameedit.getText().toString();
                String phone = phoneedit.getText().toString();
                String detailAddress = detail.getText().toString();
                String zip = zipcode.getText().toString();
                is_default = checkBox.isChecked() ? 1 : 0;
                if (addressBean != null) {
                    //修改地址
                    lingShouPresenter.updateAddress(getSp(Const.UID), addressBean.getId(), is_default, province, provinceId, city, area, cityid, areaid, detailAddress, name, phone, country_id, country, lng, lat, getSp(Const.S_ID));
                } else {
                    //增加地址
                    lingShouPresenter.addAddress(getSp(Const.UID),is_default, province, provinceId, city, area, cityid, areaid, detailAddress, name, phone, country_id, country, lng, lat, getSp(Const.S_ID));
                }

                break;
            case R.id.locationrela:
                AddressFragment addressFragment = new AddressFragment(this);
                addressFragment.show(getSupportFragmentManager(), "addressfragment");
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == LingShouPresenter.addAddress_success) {
                MAlert.alert("添加成功");
                finish();
            } else if (entity.getEventType() == LingShouPresenter.addAddress_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.updateAddress_success) {
                MAlert.alert(entity.getData());
                finish();
            } else if (entity.getEventType() == LingShouPresenter.updateAddress_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    @Override
    public void onGetinforListener(String province, String city, String district, String provinceNo, String cityNo, String districtNo) {
        this.province = province;
        this.city = city;
        this.area = district;
        this.provinceId = provinceNo;
        this.cityid = cityNo;
        this.areaid = districtNo;
        if (city.equals("市")||city.equals("县") || city.equals("市辖区")) {
            this.city = province;
            provincetext.setText(province + district);
        } else {

            provincetext.setText(province + city + district);
        }
    }
}
