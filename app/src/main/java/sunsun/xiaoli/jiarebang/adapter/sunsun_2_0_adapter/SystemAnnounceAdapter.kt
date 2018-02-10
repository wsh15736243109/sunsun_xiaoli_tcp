package sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter

import com.itboye.pondteam.bean.MessageBean
import sunsun.xiaoli.jiarebang.R
import sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.baseAdapter.MutiplyCommonAdapter
import sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.baseAdapter.ViewHolder
import sunsun.xiaoli.jiarebang.shuizuzhijia.me.messageFragment.MessageXiTongFragmet
import java.util.*

/**
 * Created by Administrator on 2018/2/10.
 */
class SystemAnnounceAdapter(private var fragment: MessageXiTongFragmet, datas: List<MessageBean.MessageArrayEntity>, vararg layoutId: Int) : MutiplyCommonAdapter<MessageBean.MessageArrayEntity>(fragment.activity, datas, layoutId[0]) {


    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getItemPosition(position: Int) {
    }

    override fun convert(holder: ViewHolder?, t: MessageBean.MessageArrayEntity?, type: Int, position: Int) {
        holder!!.setText(R.id.content, t!!.title)
        holder!!.setText(R.id.tv_summary, t!!.summary)
        holder!!.setText(R.id.time, Date(t!!.sendTime * 1000).toLocaleString())
    }
}