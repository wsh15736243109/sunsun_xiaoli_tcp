package sunsun.xiaoli.jiarebang.shuizuzhijia.product;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.ProductBean;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;
import com.pobing.uilibs.feature.view.XListView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;

public class ProductcenterSearchActivity extends BaseActivity implements
		OnClickListener, Observer {
	// private EditText et_search;
	private EditText et_search;
	private XListView listView;
	private Button button, button_btn;
	private ImageView back;
	private BaseAdapter adapter;

	private LinearLayout produceterLiner;
	UserPresenter userPresenter;
//	List<ShareBean> list = new ArrayList<ShareBean>();
	String name;
	private ArrayList<ProductBean.HomeListBean> homeListBeanArrayList;

	@Override
	@SuppressLint("InlinedApi")
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_produtcenter_search);
		et_search.setHint(R.string.keyword);
		listView.setVisibility(View.GONE);

		et_search.addTextChangedListener(textWatcher);
		userPresenter=new UserPresenter(this);
	}

	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

			System.out.println("<><><><<>ll" + et_search.getText().toString());
			button.setClickable(true);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			button.setClickable(false);
			System.out.println("<><><><<>oo" + et_search.getText().toString());
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			button.setClickable(true);
			System.out.println("<><><><<>oo" + et_search.getText().toString());
		}
	};

	private boolean isClickable() {
		// TODO Auto-generated method stub
		button.setClickable(true);
		return true;
	}

	private void pullUp() {
		name = et_search.getText().toString();
		if (et_search.getText().toString().equals("")) {
			MAlert.alert("输入内容不能为空");
			return;
		}
		showProgressDialog("搜索中，请稍后...", true);
		userPresenter.productSearch(name);
//		MyJsonRequest.Builder<ArrayList<ShareBean>> builder = new MyJsonRequest.Builder<ArrayList<ShareBean>>()
//				.apiVer("100").typeKey("BY_ProductCenter_search")
//				.param("name", et_search.getText().toString())
//				.requestListener(new XRequestListener<ArrayList<ShareBean>>() {
//
//					@Override
//					public void onResponse(ArrayList<ShareBean> bean) {
//						if (bean.size() <= 0) {
//							listView.setVisibility(View.GONE);
//							produceterLiner.setVisibility(View.VISIBLE);
//						} else {
//							adapter = new ShareAdapter(bean);
//							listView.setVisibility(View.VISIBLE);
//							listView.setAdapter(adapter);
//							produceterLiner.setVisibility(View.GONE);
//						}
//						try {
//							closeProgressDialog();
//						} catch (Exception e) {
//							// TODO: handle exception
//						}
//					}
//				}).errorListener(new XErrorListener() {
//
//					@Override
//					public void onErrorResponse(Exception exception, int code,
//							String msg) {
//						try {
//							closeProgressDialog();
//						} catch (Exception e) {
//							// TODO: handle exception
//						}
//					}
//				});
//
//		MyJsonRequest<ArrayList<ShareBean>> request = builder.build();
//		HttpRequest.getDefaultRequestQueue().add(request);
	}

//	class ShareAdapter extends BaseAdapter {
//		List<ShareBean> bean;
//
//		public ShareAdapter(List<ShareBean> bean) {
//			this.bean = bean;
//		}
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return bean.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		@Override
//		public View getView(final int position, View convertView,
//				ViewGroup parent) {
//			// TODO Auto-generated method stub
//			ViewHouder houder;
//			if (convertView == null) {
//				houder = new ViewHouder();
//				convertView = LayoutInflater.from(App.ctx).inflate(
//						R.layout.item_producenter_share, null);
//				houder.title = (TextView) convertView
//						.findViewById(R.id.tvshare);
//				houder.realyout = (RelativeLayout) convertView
//						.findViewById(R.id.realyout);
//				convertView.setTag(houder);
//			} else {
//				houder = (ViewHouder) convertView.getTag();
//			}
//			houder.title.setText(bean.get(position).getName());
//
//			if (bean.get(position).getLevel().equals("1")) {
//				houder.realyout.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Intent intent = new Intent(
//								ProducenterSeaerActivity.this,
//								ProducenterGridviewActivity.class);
//						intent.putExtra("parentid", bean.get(position).getId());
//						intent.putExtra("title", bean.get(position).getName());
//						System.out.println("LLLLLLLLLLLLLLL"
//								+ bean.get(position).getId());
//						startActivity(intent);
//					}
//				});
//			} else if (bean.get(position).getLevel().equals("2")) {
//				houder.realyout.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Intent intent = new Intent(
//								ProducenterSeaerActivity.this,
//								ProductCenterMyKefuActivity.class);
//						intent.putExtra("id", bean.get(position).getId());
//						System.out.println("LLLLLLLLLLLLLLL"
//								+ bean.get(position).getId());
//						startActivity(intent);
//					}
//				});
//			}
//
//			return convertView;
//		}
//
//		class ViewHouder {
//			TextView title;
//			RelativeLayout realyout;
//		}
//
//	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			this.finish();
			break;
		case R.id.button:
			button.setClickable(true);
			pullUp();
			break;
		default:
			break;
		}
	}

	@Override
	public void update(Observable o, Object data) {
		ResultEntity entity = handlerError(data);
		if (entity != null) {
			if (entity.getCode() != 0) {
				MAlert.alert(entity.getMsg());
			} else {
				if (entity.getEventType() == UserPresenter.queryProductPost_success) {
					homeListBeanArrayList = (ArrayList<ProductBean.HomeListBean>) entity.getData();
//					xlistview.setAdapter(new ProductListAdapter(homeListBeanArrayList));
				} else if (entity.getEventType() == UserPresenter.queryProductPost_fail) {
					MAlert.alert(entity.getData());
				}
			}
		}
	}
}
