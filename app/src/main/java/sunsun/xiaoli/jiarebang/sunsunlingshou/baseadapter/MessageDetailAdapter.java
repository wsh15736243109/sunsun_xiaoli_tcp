package sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.bean.MessageListBean;
import com.itboye.pondteam.utils.Const;

import java.util.ArrayList;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.GlidHelper;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

/**
 * Created by Administrator on 2017/8/17.
 */

public class MessageDetailAdapter extends android.widget.BaseAdapter {
    private String currUid;
    Activity activity;
    String result;
    ArrayList<MessageListBean> siXinXiangQingModel;

    class Holder1 {
        TextView  messageTv;
        ImageView img_head_receive;
        Holder1(View view) {
            messageTv = (TextView) view.findViewById(R.id.messageTv);
            img_head_receive= (ImageView) view.findViewById(R.id.img_head_receive);
        }

    }

    class Holder2 {
        TextView  messageTv;
        ImageView img_head_send;
        Holder2(View view) {
            messageTv = (TextView) view.findViewById(R.id.messageTv);
            img_head_send= (ImageView) view.findViewById(R.id.img_head_send);
        }
    }

    public MessageDetailAdapter(Activity activity, ArrayList<MessageListBean> siXinXiangQingModel) {
        this.siXinXiangQingModel = siXinXiangQingModel;
        this.activity = activity;
        currUid = getSp(Const.UID);
    }

    @Override
    public int getItemViewType(int position) {

        if (currUid.equals(siXinXiangQingModel.get(position)
                .getFrom_id())) {
            return 2;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 3;
    }

    @Override
    public int getCount() {
        return siXinXiangQingModel != null ? siXinXiangQingModel.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return siXinXiangQingModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);

        Holder1 holder1 = null;
        Holder2 holder2 = null;

        if (convertView == null) {
            // 选择某一个样式。。
            switch (type) {
                case 1:
                    convertView = LayoutInflater.from(activity).inflate(
                            R.layout.item_sixin_receive, null);
                    holder1 = new Holder1(convertView);

                    convertView.setTag(holder1);
                    break;
                case 2:
                    convertView = LayoutInflater.from(activity).inflate(
                            R.layout.item_sixin_send, null);
                    holder2 = new Holder2(convertView);

                    convertView.setTag(holder2);
                    break;
                default:
                    break;
            }
        }
        switch (type) {
            case 1:
                holder1 = (Holder1) convertView.getTag();
                holder1.messageTv.setText(siXinXiangQingModel.get(position).getContent());
//                GlidHelper.glidLoad(holder1.img_head_receive,siXinXiangQingModel.get(position).getFrom_id());
                GlidHelper.glidLoad(holder1.img_head_receive,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503051086668&di=466ae9fc44062c7ff638b1c7be953c25&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20160911%2F315ba0a00c8a48b0bc90664ec7480679_th.jpeg");

                break;
            case 2:
                holder2 = (Holder2) convertView.getTag();
                holder2.messageTv.setText(siXinXiangQingModel.get(position).getContent());
//                GlidHelper.glidLoad(holder2.img_head_send,siXinXiangQingModel.get(position).getFrom_id());
                GlidHelper.glidLoad(holder2.img_head_send,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503051086668&di=466ae9fc44062c7ff638b1c7be953c25&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20160911%2F315ba0a00c8a48b0bc90664ec7480679_th.jpeg");
                break;

            default:
                break;
        }


        return convertView;
    }

}
