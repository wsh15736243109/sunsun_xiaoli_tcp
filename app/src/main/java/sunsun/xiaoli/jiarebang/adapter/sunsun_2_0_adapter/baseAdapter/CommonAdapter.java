package sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.baseAdapter;

import android.content.Context;

import java.util.List;

public abstract class CommonAdapter<T> extends MutiplyCommonAdapter<T>{

	private CommonAdapter(Context context, List<T> datas, int... layoutId) {
		super(context, datas, layoutId);
	}

	public CommonAdapter(Context context, List<T> datas, int layoutId) {
		this(context, datas, new int[]{layoutId});
	}
	
	@Override
	public int getItemViewType(int position) {
		return 0;
	}
	
	public abstract void convert(ViewHolder holder, T t,int position);
	
	@Override
	public void convert(ViewHolder holder, T t,int type,int position){
		convert(holder,t,position);
	}
}
