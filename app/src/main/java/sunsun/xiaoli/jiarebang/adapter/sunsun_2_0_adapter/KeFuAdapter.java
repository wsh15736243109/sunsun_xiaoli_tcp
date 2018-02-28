package sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.bean.ChatBean;

import java.text.SimpleDateFormat;
import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.utils.XGlideLoader;

public class KeFuAdapter extends BaseAdapter {
    List<ChatBean.ChatItem> list;

    public KeFuAdapter(List<ChatBean.ChatItem> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHouder houder;
        if (convertView == null) {
            houder = new ViewHouder();
            convertView = LayoutInflater.from(App.getInstance()).inflate(R.layout.fragment_item_kefu, null);
            houder.title = (TextView) convertView.findViewById(R.id.content);
            houder.time = (TextView) convertView.findViewById(R.id.time);
            houder.img = (ImageView) convertView.findViewById(R.id.iv1);
            houder.countext = (TextView) convertView.findViewById(R.id.tvCpount);
            convertView.setTag(houder);
        } else {
            houder = (ViewHouder) convertView.getTag();
        }

        houder.title.setText(list.get(position).getNickname());
        houder.time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long
                .parseLong(list.get(position).getCreate_time()) * 1000));
        houder.countext.setText((list.get(position).getMsgContent()));
        XGlideLoader.displayImage(App.getInstance(), list.get(position).getHead(), houder.img);
        return convertView;
    }

    class ViewHouder {
        private TextView title;
        private TextView time;
        private ImageView img;
        private TextView countext;
    }

}
