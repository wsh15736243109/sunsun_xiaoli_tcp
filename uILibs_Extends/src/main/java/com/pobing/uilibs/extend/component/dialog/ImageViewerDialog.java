package com.pobing.uilibs.extend.component.dialog;

import com.pobing.uilibs.extend.R;
import com.pobing.uilibs.extend.feature.view.XZoomImageView;
import com.pobing.uilibs.extend.feature.view.XZoomPagerItem;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageViewerDialog extends Dialog {
	
	private ViewPager mViewPager;
	private MyAdapter mAdapter;
	
	private TextView mImageDescText;
	private TextView mPageNumText;
	
	private String[] mImageDescs;
	
	private ArrayList<XZoomPagerItem> mPageList = new ArrayList<XZoomPagerItem>();
	
	public ImageViewerDialog(Context context) {
		this(context, R.style.ImageViewerDialog);
	}

	public ImageViewerDialog(Context context, int theme) {
		super(context, theme);
		init();
	}
	
	private void init() {
		DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(dm.widthPixels, LayoutParams.MATCH_PARENT);
		View view = getLayoutInflater().inflate(R.layout.uilibs_imageviewer_dialog, null);
		setContentView(view, lp);
		mImageDescText = (TextView)view.findViewById(R.id.img_desc);
		mPageNumText = (TextView)view.findViewById(R.id.page_num);
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		mAdapter = new MyAdapter();
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				for(int i = 0; i < mPageList.size(); i++) {
					if(i != arg0) {
						XZoomImageView img = mPageList.get(i).getImageView();
						img.reset();
					}
				}
				if(null != mImageDescs && mImageDescs.length > arg0) {
					mImageDescText.setText(mImageDescs[arg0]);
				}
				mPageNumText.setText((arg0 + 1) + "/" + mPageList.size());
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
	}
	
	public void setImageUrls(String[] imageUrls) {
		if(null != imageUrls) {
			createPageList(imageUrls);
			mAdapter.notifyDataSetChanged();
			int index = mViewPager.getCurrentItem();
			mPageNumText.setText((index + 1) + "/" + mPageList.size());
		}
	}
	
	public void setImageDescs(String[] imageDescs) {
		mImageDescs = imageDescs;
		int index = mViewPager.getCurrentItem();
		if(mImageDescs.length > index) {
			mImageDescText.setText(mImageDescs[index]);
		}
		mPageNumText.setText((index + 1) + "/" + mPageList.size());
	}

	private void createPageList(String[] imageUrls) {
		mPageList.clear();
		if(null != imageUrls && imageUrls.length > 0) {
			for(int i = 0; i < imageUrls.length; i++) {
				XZoomPagerItem item = new XZoomPagerItem(getContext());
				XZoomImageView image = item.getImageView();
				image.setImageUrl(imageUrls[i]);
				image.setTag(i);
				image.setOnClickListener(new android.view.View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dismiss();
					}
				});
				mPageList.add(item);
			}
		}
	}
	
	public void show(int index) {
		if(index >= 0 && index < mPageList.size()) {
			mViewPager.setCurrentItem(index);
			show();
		}
	}
	
	class MyAdapter extends PagerAdapter {
		
		@Override
		public int getCount() {
			return mPageList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			View view = mPageList.get(position);
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
}
