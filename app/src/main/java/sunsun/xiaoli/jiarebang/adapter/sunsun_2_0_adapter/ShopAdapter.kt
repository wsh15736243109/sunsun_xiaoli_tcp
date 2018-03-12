package sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter

import com.itboye.pondteam.bean.NavigationBean
import sunsun.xiaoli.jiarebang.R
import sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.baseAdapter.MutiplyCommonAdapter
import sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.baseAdapter.ViewHolder
import sunsun.xiaoli.jiarebang.shuizuzhijia.shop.ShopFragment
import sunsun.xiaoli.jiarebang.utils.XGlideLoader

/**
 * Created by Administrator on 2018/2/3.
 */
class ShopAdapter(private var fragment: ShopFragment, datas: List<NavigationBean.NavigationDetail>, vararg layoutId: Int) : MutiplyCommonAdapter<NavigationBean.NavigationDetail>(fragment.activity, datas, layoutId[0]) {

    var needChange = false
    var holder: ViewHolder? = null
    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getItemPosition(position: Int) {
    }

    override fun convert(holder: ViewHolder?, t: NavigationBean.NavigationDetail?, type: Int, position: Int) {
        this.holder=holder
//        XGlideLoaderNew.displayImageCircular(fragment.activity, Const.imgSunsunUrl+t!!.branchImgs.split(",")[0],holder!!.getView(R.id.iv_shop))
        XGlideLoader.displayImage(fragment.activity, R.drawable.taobao, holder!!.getView(R.id.iv_shop))
        holder!!.setText(R.id.tv_shop_name, t!!.name)

//        holder!!.setText(R.id.tv_shop_address,"址地址地址地址地址地址地hh")
        holder!!.setText(R.id.tv_shop_address, "地址：" + t!!.addressDetail)
    }

    fun notifyData() {
        needChange = (true)
        holder!!.setNeedChange(R.id.tv_shop_address,needChange)
        notifyDataSetChanged()
    }
}