package sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter
import com.itboye.pondteam.bean.NavigationBean
import com.itboye.pondteam.utils.Const
import sunsun.xiaoli.jiarebang.R
import sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.baseAdapter.MutiplyCommonAdapter
import sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.baseAdapter.ViewHolder
import sunsun.xiaoli.jiarebang.shuizuzhijia.shop.ShopFragment
import sunsun.xiaoli.jiarebang.utils.XGlideLoaderNew

/**
 * Created by Administrator on 2018/2/3.
 */
class ShopAdapter(private var fragment: ShopFragment, datas: List<NavigationBean.NavigationDetail>, vararg layoutId: Int) : MutiplyCommonAdapter<NavigationBean.NavigationDetail>(fragment.activity, datas, layoutId[0]) {


    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getItemPosition(position: Int) {
    }

    override fun convert(holder: ViewHolder?, t: NavigationBean.NavigationDetail?, type: Int, position: Int) {
        XGlideLoaderNew.displayImageCircular(fragment.activity, Const.imgSunsunUrl+t!!.branchImgs.split(",")[0],holder!!.getView(R.id.iv_shop))
        holder!!.setText(R.id.tv_shop_name, t!!.name)
        holder!!.setText(R.id.tv_shop_address,t!!.addressDetail)
        holder!!.setOnClickListener(R.id.tv_shop_enter, position,fragment)
    }
}