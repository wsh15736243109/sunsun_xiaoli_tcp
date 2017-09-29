package sunsun.xiaoli.jiarebang.adapter.lingshouadapter;

import android.app.Activity;
import android.widget.ImageView;

import com.itboye.pondteam.utils.Const;

import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.GoodsDetailBean;
import sunsun.xiaoli.jiarebang.beans.ShopCartBean;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.shopcart.MakeSureOrderActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.BaseAdapter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.ViewHolder;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.ServiceBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.GlidHelper;


/**
 */

public class MakeSureOrderAdapter extends BaseAdapter {

    ServiceBean serviceBean;
    public MakeSureOrderAdapter(Activity context, List datas) {
        super(context, R.layout.item_makesure_goods, datas);
    }
    IRecycleviewClick recycleviewClick;

    public void setOnItemListener(IRecycleviewClick recycleviewClick){
        this.recycleviewClick=recycleviewClick;
    }


    public void setExtraModel(ServiceBean serviceBean){
        this.serviceBean=serviceBean;
    }

    @Override
    public void convert(ViewHolder holder, Object o,final int position) {
        if (o instanceof GoodsDetailBean) {
            GoodsDetailBean goodsDetailBean= (GoodsDetailBean) o;
            holder.setText(R.id.txt_goodsname, goodsDetailBean.getName());
            holder.setText(R.id.txt_guige,goodsDetailBean.getSku_list().get(goodsDetailBean.getSelectPositon()).getSku_desc());
            holder.setText(R.id.txt_price,"￥"+goodsDetailBean.getSku_list().get(goodsDetailBean.getSelectPositon()).getPrice());
            holder.setText(R.id.txt_count,"x"+goodsDetailBean.getCount());
            GlidHelper.glidLoad((ImageView) holder.getView(R.id.sure_order_icon), Const.imgurl+goodsDetailBean.getSku_list().get(goodsDetailBean.getSelectPositon()).getSkuimg());
            holder.setOnclickListener(R.id.sure_order_root,(MakeSureOrderActivity)context);
        }else if (o instanceof ShopCartBean){
            ShopCartBean shopCartBean= (ShopCartBean) o;
            holder.setText(R.id.txt_goodsname, shopCartBean.getName());
            holder.setText(R.id.txt_guige,shopCartBean.getSku_desc());
            holder.setText(R.id.txt_price,"￥"+shopCartBean.getPrice());
            holder.setText(R.id.txt_count,"x"+shopCartBean.getCount());
            GlidHelper.glidLoad((ImageView) holder.getView(R.id.sure_order_icon), Const.imgurl+shopCartBean.getIcon_url());
            holder.setOnclickListener(R.id.sure_order_root,(MakeSureOrderActivity)context);
        }else if (o instanceof ServiceBean.SkuInfoEntity){
            ServiceBean.SkuInfoEntity serviceBean= (ServiceBean.SkuInfoEntity) o;
            holder.setText(R.id.txt_goodsname, this.serviceBean.getProduct_info().getName());
            holder.setText(R.id.txt_guige,serviceBean.getSku_desc());
            GlidHelper.glidLoad((ImageView) holder.getView(R.id.sure_order_icon),Const.imgurl+this.serviceBean.getImg().getId());
            holder.setText(R.id.txt_price,"￥"+serviceBean.getPrice());
        }
    }
}
