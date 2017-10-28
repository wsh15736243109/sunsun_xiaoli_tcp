package sunsun.xiaoli.jiarebang.sunsunlingshou.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.HomeDeivcesAdapter;
import sunsun.xiaoli.jiarebang.adapter.HomeHotGoodsAdapter;
import sunsun.xiaoli.jiarebang.adapter.HomeNearStoreAdapter;
import sunsun.xiaoli.jiarebang.beans.GoodsListBean;
import sunsun.xiaoli.jiarebang.beans.StoreListBean;
import sunsun.xiaoli.jiarebang.custom.MyGridView;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.GoodsClassifyActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.GoodDetailActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.DeviceTypeModel;

import static com.itboye.pondteam.utils.ScreenUtil.getDimension;

/**
 * Created by Mr.w on 2017/10/28.
 */

@SuppressLint("ValidFragment")
public class MyTabFragment extends LingShouBaseFragment implements Observer, AdapterView.OnItemClickListener {
    public int type = 0;
    LingShouPresenter lingShouPresenter;
    MyGridView recycler_aqhardwareorhotgoods;
    ArrayList<DeviceTypeModel> arDevice = new ArrayList<>();
    private int pageIndex = 1;
    private StoreListBean bean;
    private GoodsListBean goodsList;
    LinearLayout near_store;
    ProgressBar progress;
    @SuppressLint("ValidFragment")
    public MyTabFragment(int type) {
        this.type = type;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mytab;
    }

    @Override
    protected void initData() {
        DeviceTypeModel deviceListBean = new DeviceTypeModel(R.drawable.home_aq_806, getString(R.string.device_zhineng806));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_jiarebang, getString(R.string.device_zhinengjiarebang));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_ph, getString(R.string.device_yuancheng_ph));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_shuibeng, getString(R.string.device_zhinengbianpinshuibeng));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_guolvtong, getString(R.string.device_chitangguolv));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_shenxiangtou, getString(R.string.device_zhinengshexiangtou));
        arDevice.add(deviceListBean);

        deviceListBean = new DeviceTypeModel(R.drawable.home_shuizudeng, getString(R.string.device_shuizudeng));
        arDevice.add(deviceListBean);
        deviceListBean = new DeviceTypeModel(R.drawable.home_aq_228, getString(R.string.device_zhineng228));
        arDevice.add(deviceListBean);
        lingShouPresenter = new LingShouPresenter(this);
        recycler_aqhardwareorhotgoods.setOnItemClickListener(this);
        switch (this.type) {
            case 0:
                near_store.setVisibility(View.VISIBLE);
                lingShouPresenter.getNearStore("330100", 120.377819 + "", 120.377819 + "", "", "", pageIndex, 10);
                break;
            case 1:
                progress.setVisibility(View.GONE);
                near_store.setVisibility(View.GONE);
                recycler_aqhardwareorhotgoods.setVerticalSpacing(0);
                recycler_aqhardwareorhotgoods.setNumColumns(3);
                HomeDeivcesAdapter adapter = new HomeDeivcesAdapter(getActivity(), arDevice, R.layout.item_lingshou_device);
//            HomeDeviceAdapter adapter = new HomeDeviceAdapter(getActivity(), arDevice, R.layout.item_lingshou_device);
                recycler_aqhardwareorhotgoods.setAdapter(adapter);
                break;
            case 2:
                near_store.setVisibility(View.GONE);
                lingShouPresenter.getHotSearchGoods();
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        progress.setVisibility(View.GONE);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == LingShouPresenter.getNearStore_success) {
                recycler_aqhardwareorhotgoods.setVerticalSpacing(getDimension(getActivity(),20));
                recycler_aqhardwareorhotgoods.setNumColumns(1);
                recycler_aqhardwareorhotgoods.setVisibility(View.VISIBLE);
                bean = (StoreListBean) entity.getData();
                HomeNearStoreAdapter adapter = new HomeNearStoreAdapter(this, bean.getList(), R.layout.item_home_nearshangjia);
                recycler_aqhardwareorhotgoods.setAdapter(adapter);
            } else if (entity.getEventType() == LingShouPresenter.getNearStore_fail) {
                MAlert.alert(entity.getData());
            }else if (entity.getEventType() == LingShouPresenter.getHotSearchGoods_success) {

                recycler_aqhardwareorhotgoods.setVerticalSpacing(0);
                recycler_aqhardwareorhotgoods.setNumColumns(2);
                recycler_aqhardwareorhotgoods.setVisibility(View.VISIBLE);
                goodsList = (GoodsListBean) entity.getData();
                ArrayList<GoodsListBean.ListEntity> arTemp = new ArrayList<>();
                if (goodsList != null) {
                    if (goodsList.getList() != null) {
                        arTemp.addAll(goodsList.getList());
                    }
                }
                HomeHotGoodsAdapter adapter = new HomeHotGoodsAdapter(getActivity(), arTemp, R.layout.item_home_shangpin);
                recycler_aqhardwareorhotgoods.setAdapter(adapter);
            } else if (entity.getEventType() == LingShouPresenter.getHotSearchGoods_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (this.type) {
            case 0:
                Intent intent = new Intent(getActivity(), GoodsClassifyActivity.class);
                intent.putExtra("model",bean.getList().get(position));
                intent.putExtra("store_id", bean.getList().get(position).getId());
                startActivity(intent);
                break;
            case 1:
                break;
            case 2:
                startActivity(new Intent(getActivity(), GoodDetailActivity.class).putExtra("id", goodsList.getList().get(position).getId()).putExtra("store_id", goodsList.getList().get(position).getStore_id()));
                break;
        }
    }
}
