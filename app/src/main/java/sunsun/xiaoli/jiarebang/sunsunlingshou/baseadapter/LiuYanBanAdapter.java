package sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter;

import android.app.Activity;
import android.widget.ImageView;

import com.itboye.pondteam.bean.MessageListBean;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.volley.TimesUtils;

import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.LiuYanBanActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.GlidHelper;


/**
 * Created by Kun on 2016/12/14.
 * GitHub: https://github.com/AndroidKun
 * CSDN: http://blog.csdn.net/a1533588867
 * Description:模版
 */

public class LiuYanBanAdapter extends BaseAdapter {

    Activity activity;

    public LiuYanBanAdapter(Activity activity, int layout, List datas) {
        super(activity, layout, datas);
        this.activity = activity;
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {
        MessageListBean entityList = (MessageListBean) o;
        holder.setText(R.id.txt_nickname, entityList.getNickname());
        holder.setText(R.id.txt_time, TimesUtils.getStringTime(entityList.getSend_time(), "yyyy.MM.dd"));
        holder.setText(R.id.txt_content, entityList.getContent());
        GlidHelper.glidLoad((ImageView) holder.getView(R.id.img_head), Const.imgurl + entityList.getFrom_id());
        if (entityList.getMsg_status() == 0) {
            //旧消息
            holder.setViewBackgroundResource(R.id.img_news, R.drawable.liuyanban_old);
        } else {
            //新消息
            holder.setViewBackgroundResource(R.id.img_news, R.drawable.liuyanban_new);
        }
        holder.setTag(R.id.re_root,-1,entityList.getFrom_id());
        holder.setOnclickListener(R.id.re_root, (LiuYanBanActivity) activity);//已经支付状态下的操作
    }
}
