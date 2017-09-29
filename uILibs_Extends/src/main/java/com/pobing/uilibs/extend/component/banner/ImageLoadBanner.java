package com.pobing.uilibs.extend.component.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;

import com.pobing.uilibs.component.Banner;
import com.pobing.uilibs.extend.feature.features.ImageLoadFeature;
import com.pobing.uilibs.feature.view.XImageView;

import java.util.ArrayList;

/**
 * 
 * ImageLoadBanner is a kind of banner which's page contains only image which
 * come from network.
 * 
 * The download urls of images can be set by calling
 * {@link #setImageUrls(String[]) setImageUrls()}.
 * 
 * The page click listener can be set by calling
 * {@link #setOnPageClickListener(OnPageClickListener) setOnPageClickListener()}
 * .
 * 
 */
public class ImageLoadBanner extends Banner {

	public interface OnPageClickListener {
		/**the callback of page click
		 * 
		 * @param index: the page index,start from 0.
		 */
		void onPageClick(int index);
	}

	private String[] mUrls;
	
	private OnPageClickListener mOnPageClickListener;

	public ImageLoadBanner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ImageLoadBanner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ImageLoadBanner(Context context) {
		super(context);
	}

	/**set image download url
	 * @param urls
	 */
	public void setImageUrls(String[] urls) {
		mUrls = urls;
		apply();
	}

	/** set OnPageClickListener
	 * @param onPageClickListener
	 */
	public void setOnPageClickListener(OnPageClickListener onPageClickListener) {
		this.mOnPageClickListener = onPageClickListener;
	}
	
	private void apply() {
		ArrayList<XImageView> viewList = new ArrayList<XImageView>();
		int i = 0;
		for (String url : mUrls) {
			XImageView imageView = new XImageView(this.getContext());
			imageView.setScaleType(ScaleType.CENTER_CROP);
			
			ImageLoadFeature f = new ImageLoadFeature();
			imageView.addFeature(f);
			f.setImageUrl(url);
			viewList.add(imageView);
			
			final int index = i;
			imageView.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					if (mOnPageClickListener != null) {
						mOnPageClickListener.onPageClick(index);
					}		
				}
				
			});
			i ++;
		}

		SimpleAdapter adapter = new SimpleAdapter(viewList);
		setAdapter(adapter);
	}

	private class SimpleAdapter extends PagerAdapter {

		private ArrayList<XImageView> mViewList;

		public SimpleAdapter(ArrayList<XImageView> list) {
			mViewList = list;
		}

		@Override
		public int getCount() {
			return mViewList.size();
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
			View view = mViewList.get(position);
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
}
