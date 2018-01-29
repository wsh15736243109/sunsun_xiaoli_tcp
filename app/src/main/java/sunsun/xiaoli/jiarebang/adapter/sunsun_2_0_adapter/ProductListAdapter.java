package sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.bean.ProductBean;

import java.text.SimpleDateFormat;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.utils.XGlideLoader;

public class ProductListAdapter extends BaseAdapter {

	ProductBean bean;

	public ProductListAdapter(
			ProductBean bean) {
		// TODO Auto-generated constructor stub
		this.bean = bean;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bean == null ? 0 : bean.getList().size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {

			convertView = LayoutInflater.from(MyApplication.getInstance()).inflate(
					R.layout.item_producenter_chanpin, null);
			holder = new ViewHolder();
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.luntanTime = (TextView) convertView.findViewById(R.id.luntanTime);

			holder.iv1 = (ImageView) convertView.findViewById(R.id.iv1);

			holder.imgsContainer = (ViewGroup) convertView
					.findViewById(R.id.imgsContainer);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ProductBean.HomeListBean list=bean.getList().get(position);
		holder.content.setText(list.getPost_title());
		holder.luntanTime.setText("更新日期:");
		holder.time.setText(new SimpleDateFormat("yyyy年MM月dd日").format(list.getPost_date()*1000));
		XGlideLoader.displayImage(MyApplication.getInstance(),list.getMain_img(), holder.iv1);

		return convertView;
	}

	class ViewHolder {

		TextView content;
		TextView time;
		TextView luntanTime;
		ImageView iv1;
		ImageView luntanjing;

		ViewGroup imgsContainer;
	}
}
