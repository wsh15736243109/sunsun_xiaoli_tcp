package sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.baseAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/***
 * 通用Adapter,
 * @author mr.w
 *
 * @param <T>实体类
 */
public abstract class MutiplyCommonAdapter<T> extends BaseAdapter {
	protected Context mContext;
	protected List<T> mDatas;
	private int[] layoutId;
	private String EVENT_TYPE_UNKNOWN="_unknown";
	public MutiplyCommonAdapter(){

	}
	public MutiplyCommonAdapter(Context context, List<T> datas, int... layoutId){
		this.mContext = context;
		this.mDatas = datas;
		this.layoutId = layoutId;
	}
	
	@Override
	public int getCount(){
		return mDatas.size();
	}

	@Override
	public T getItem(int position){
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position){
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return layoutId.length;
	}
	
//	/**
//	 * 显示进度对话框,带有消息 flag 是否可以取消
//	 */
//	@SuppressLint("NewApi")
//	public void showProgressDialog(String message, boolean flag) {
//		if (progressDialog == null)
//			return;
//		setProgressDialogMessage(message);
//
//		progressDialog.show;
//		progressDialog.setCancelable(flag);
//
//	}

//	/**
//	 * 设置进度对话框消息
//	 * 
//	 * @param message
//	 */
//	public void setProgressDialogMessage(String message) {
//		progressDialog.setMsg(message);
//	}
//
//	/**
//	 * 关闭进度对话框
//	 */
//	@SuppressLint("NewApi")
//	public void closeProgressDialog() {
//		if (progressDialog != null) {
////			if (progressDialog.getDialog().isShowing()) {
//				progressDialog.dismiss();
////			}
//		}
//	}

	
	@Override
	public abstract int getItemViewType(int position);
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder = ViewHolder.get(mContext, convertView, parent,
				layoutId[getItemViewType(position)], position);
		convert(holder, getItem(position),getItemViewType(position),position);
		return holder.getConvertView();
	}
	public abstract void getItemPosition(int position);
	public abstract void convert(ViewHolder holder, T t,int type,int position);
	
	public void add(T elem) {
		mDatas.add(elem);
        notifyDataSetChanged();
    }

    public void addAll(List<T> elem) {
    	mDatas.addAll(elem);
        notifyDataSetChanged();
    }

    public void set(T oldElem, T newElem) {
        set(mDatas.indexOf(oldElem), newElem);
    }

    public void set(int index, T elem) {
    	mDatas.set(index, elem);
        notifyDataSetChanged();
    }

    public void remove(T elem) {
    	mDatas.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
    	mDatas.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
    	mDatas.clear();
    	mDatas.addAll(elem);
        notifyDataSetChanged();
    }

    public boolean contains(T elem) {
        return mDatas.contains(elem);
    }

    /** Clear data list */
    public void clear() {
    	mDatas.clear();
        notifyDataSetChanged();
    }


}
