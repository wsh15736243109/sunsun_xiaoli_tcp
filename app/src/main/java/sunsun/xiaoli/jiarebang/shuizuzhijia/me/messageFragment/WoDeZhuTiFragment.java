package sunsun.xiaoli.jiarebang.shuizuzhijia.me.messageFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.custom.swipemenulistview.SwipeMenuListView;

import sunsun.xiaoli.jiarebang.R;

public class WoDeZhuTiFragment extends LingShouBaseFragment {

	private SwipeMenuListView xlistview;
	private int pageNum = 1;

	private BaseAdapter adapter;

//	List<ZhuTiItemBean> dataList = new ArrayList<>();


	@Override
	protected int getLayoutId() {
		return R.layout.fragment_wo_de_tiezi;
	}

	@Override
	protected void initData() {

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//		xlistview.setPullRefreshEnable(true);
//		xlistview.setPullLoadEnable(true);
//		xlistview.setXListViewListener(new IXListViewListener() {
//
//			@Override
//			public void onRefresh() {
//				pullDown();
//			}
//
//			@Override
//			public void onLoadMore() {
//				pullUp();
//			}
//
//		});

		
		// adapter = new ShopCarAdapter(getApplication(), dataList, this);
		// xlistview.setAdapter(adapter);
		// adapter=onGetAdapter();
//		adapter = new WoDeZhuTiAdapter(dataList);
//		xlistview.setAdapter(adapter);
//		pullDown();
//
//		xlistview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				position--;
//				Intent intent = new Intent(getActivity(),
//						TieZiDetailActivity.class);
//				ZhuTiItemBean bean = dataList.get(position);
//				intent.putExtra("fid", bean.getFid());
//				intent.putExtra("tid", bean.getTid());
//				intent.putExtra("img", bean.getImg());
//
//				intent.putExtra("author", bean.getAuthor());
//				intent.putExtra("author_id", bean.getAuthorId());
//
//				startActivity(intent);
//			}
//		});
	}

	
//	private void pullDown() {
//
//		pageNum = 1;
//		MyJsonRequest.Builder<WoDeZhuTiBean> builder = new MyJsonRequest.Builder<WoDeZhuTiBean>()
//				.param("uid",
//						(String) SPUtils.get(getContext(), null,
//								SPContants.USER_ID, "")).apiVer("100")
//				.param("page_no", "" + pageNum)
//				.typeKey("BY_BbsUserCenter_queryThread")
//				.requestListener(new XRequestListener<WoDeZhuTiBean>() {
//
//					@Override
//					public void onResponse(WoDeZhuTiBean bean) {
//						// onPullDownFinish(adapter, xlistview, bean);
//						pageNum++;
//						xlistview.stopRefresh();
//						dataList.clear();
//						if (bean.getList() != null) {
//							dataList.addAll(bean.getList());
//							adapter.notifyDataSetChanged();
//						}
//
//					}
//				}).errorListener(new XErrorListener() {
//
//					@Override
//					public void onErrorResponse(Exception exception, int code,
//							String msg) {
//						xlistview.stopRefresh();
//
//						// onPullDownError(adapter, xlistview, exception, code,
//						// msg);
//					}
//				});
//
//		MyJsonRequest<WoDeZhuTiBean> request = builder.build();
//		HttpRequest.getDefaultRequestQueue().add(request);
//	}

//	private void pullUp() {
//		MyJsonRequest.Builder<WoDeZhuTiBean> builder = new MyJsonRequest.Builder<WoDeZhuTiBean>()
//				.param("uid",
//						(String) SPUtils.get(App.ctx, null, SPContants.USER_ID,
//								"")).apiVer("100")
//				.typeKey("BY_BbsUserCenter_queryThread")
//				.param("page_no", "" + pageNum)
//
//				.requestListener(new XRequestListener<WoDeZhuTiBean>() {
//
//					@Override
//					public void onResponse(WoDeZhuTiBean bean) {
//						// onPullUpFinish(adapter, xlistview, bean);
//						pageNum++;
//						xlistview.stopLoadMore();
//						if (bean.getList() != null) {
//							dataList.addAll(bean.getList());
//							adapter.notifyDataSetChanged();
//						}
//					}
//				}).errorListener(new XErrorListener() {
//
//					@Override
//					public void onErrorResponse(Exception exception, int code,
//							String msg) {
//						xlistview.stopLoadMore();
//						// onPullUpError(adapter, xlistview, exception, code,
//						// msg);
//					}
//				});
//
//		MyJsonRequest<WoDeZhuTiBean> request = builder.build();
//		HttpRequest.getDefaultRequestQueue().add(request);
//	}

	@Override
	public void onClick(View v) {

	}
}
