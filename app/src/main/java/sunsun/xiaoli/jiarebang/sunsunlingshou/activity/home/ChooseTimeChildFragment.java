package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.lingshouadapter.ChooseTimeAdapter;
import sunsun.xiaoli.jiarebang.beans.StoreListBean;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.shopcart.MakeSureOrderActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.ServiceBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.BuyType;
import sunsun.xiaoli.jiarebang.utils.ChooseTimeUtil;

/**
 * Created by Administrator on 2017/7/3.
 */

public class ChooseTimeChildFragment extends LingShouBaseFragment implements IRecycleviewClick, Observer {
    RecyclerView recyclerview_choose_time;
    private ServiceBean list=new ServiceBean();
    private ChooseTimeAdapter adapter;
    Button btn_sure_time;
    StoreListBean.ListEntity listEntity;
    private LingShouPresenter lingShouPresenter;
    private ArrayList<ServiceBean> serviceBeanArrayList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_choose_time_child;
    }

    @Override
    protected void initData() {
        list= ChooseTimeUtil.createPeroid();
        lingShouPresenter=new LingShouPresenter(this);
        lingShouPresenter.getServiceSku(6);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),4);
        recyclerview_choose_time.setLayoutManager(gridLayoutManager);
        adapter=new ChooseTimeAdapter(getActivity(),R.layout.item_textview,list.getSku_info());
                adapter.setOnItemListener(this);
        recyclerview_choose_time.setAdapter(adapter);
        listEntity= (StoreListBean.ListEntity) getActivity().getIntent().getSerializableExtra("model");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure_time:
                String send_time="";
                for (ServiceBean.SkuInfoEntity testBean : list.getSku_info()) {
                    if (testBean.isSelect()) {
                        send_time=testBean.getSku_desc();
                    }
                }
                if (send_time.equals("")) {
                    MAlert.alert("请选择配送时间");
                    return;
                }
                send_time=((ChooseTimeActivity)getActivity()).titlesTag[((ChooseTimeActivity)getActivity()).position]+" "+send_time;
                if (listEntity==null) {
                    Intent intent=new Intent();
                    intent.putExtra("send_time",send_time);
                    if (((ChooseTimeActivity)getActivity()).txt_noZiTi.isSelected()) {

                    }else{

                    }
                    getActivity().setResult(302,intent);
                    getActivity().finish();
                }else{
                    //咨询购买进入
                    //先获取商品信息
                    if (serviceBeanArrayList==null) {
                        MAlert.alert("获取服务信息失败");
                        return;
                    }
                    if (serviceBeanArrayList.size()<=0) {
                        MAlert.alert("获取服务信息失败");
                        return;
                    }
                    Intent intent=new Intent(getActivity(), MakeSureOrderActivity.class);
                    intent.putExtra("zixunModel",listEntity);
                    intent.putExtra("send_time",send_time);
                    intent.putExtra("selectValue", serviceBeanArrayList.get(0));
                    intent.putExtra("type", BuyType.Buy_ZiXunGouMai);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        for (ServiceBean.SkuInfoEntity testBean : list.getSku_info()) {
            testBean.setSelect(false);
        }
        list.getSku_info().get(position).setSelect(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemLongClick(int position) {

    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity=handlerError(data);
        if (entity!=null) {
            if (entity.getCode()!=0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType()== LingShouPresenter.getServiceSku_success) {
                serviceBeanArrayList = (ArrayList<ServiceBean>) entity.getData();
                if (serviceBeanArrayList!=null) {
                    if (serviceBeanArrayList.size()>0) {
                        if (serviceBeanArrayList.get(0).getSku_info()!=null) {
                            if (serviceBeanArrayList.get(0).getSku_info().size()>0) {
                                serviceBeanArrayList.get(0).getSku_info().get(0).setSelect(true);
                            }
                        }
                    }
                }
            }else if(entity.getEventType()== LingShouPresenter.getServiceSku_fail){

            }
        }
    }
}
