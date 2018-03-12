package sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.baseAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Html;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import sunsun.xiaoli.jiarebang.custom.MyTextView;


public class ViewHolder {
	private final SparseArray<View> mViews;
	private final Context mContext;
	private int mPosition;
	private View mConvertView;

	public ViewHolder(Context context, ViewGroup parent, int layoutId,
                      int position) {
		mContext = context;
		mPosition = position;
		mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		mConvertView.setTag(this);
	}

	public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {
		if (convertView == null)
			return new ViewHolder(context, parent, layoutId, position);
		ViewHolder holder = (ViewHolder) convertView.getTag();
		holder.mPosition = position;
		return holder;
	}

	public View getConvertView() {
		return mConvertView;
	}

	public int getmPosition() {
		return mPosition;
	}

	/**
	 * 
	 * @param viewId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	public ViewHolder setText(int viewId, String value) {
		TextView view = getView(viewId);
		view.setText(value);
		return this;
	}
	public ViewHolder setText(int viewId, CharSequence value) {
		TextView view = getView(viewId);
		view.setText(Html.fromHtml(value.toString()));
		return this;
	}
	public ViewHolder setTextSize(int viewId, int value) {
		TextView view = getView(viewId);
		view.setTextSize(value);
		return this;
	}

	public ViewHolder setGravity(int viewId, int value) {
		TextView view = getView(viewId);
		view.setGravity(value);
		return this;
	}

	public ViewHolder setVisible(int viewId, boolean visible) {
		View view = getView(viewId);
		view.setVisibility(visible ? View.VISIBLE : View.GONE);
		return this;
	}

	public ViewHolder setImageResource(int viewId, int imageResId) {
		ImageView view = getView(viewId);
		view.setImageResource(imageResId);
		return this;
	}

	public ViewHolder setBackgroundColor(int viewId, int color) {
		View view = getView(viewId);
		view.setBackgroundColor(color);
		return this;
	}
	
	public ViewHolder setImageBitmap(int viewId, Bitmap bit) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bit);
		return this;
	}

	public ViewHolder setBackgroundRes(int viewId, int backgroundRes) {
		View view = getView(viewId);
		view.setBackgroundResource(backgroundRes);
		return this;
	}

	public ViewHolder setTextColor(int viewId, int textColor) {
		TextView view = getView(viewId);
		view.setTextColor(textColor);
		return this;
	}

	public ViewHolder setTextColorRes(int viewId, int textColorRes) {
		TextView view = getView(viewId);
		view.setTextColor(mContext.getResources().getColor(textColorRes));
		return this;
	}

	@SuppressLint("NewApi") public ViewHolder setAlpha(int viewId, float value) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getView(viewId).setAlpha(value);
		} else {
			// Pre-honeycomb hack to set Alpha value
			AlphaAnimation alpha = new AlphaAnimation(value, value);
			alpha.setDuration(0);
			alpha.setFillAfter(true);
			getView(viewId).startAnimation(alpha);
		}
		return this;
	}

	public ViewHolder linkify(int viewId) {
		TextView view = getView(viewId);
		Linkify.addLinks(view, Linkify.ALL);
		return this;
	}

	public ViewHolder setTypeface(int viewId, Typeface typeface) {
		TextView view = getView(viewId);
		view.setTypeface(typeface);
		view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
		return this;
	}

	public ViewHolder setTypeface(Typeface typeface, int... viewIds) {
		for (int viewId : viewIds) {
			TextView view = getView(viewId);
			view.setTypeface(typeface);
			view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
		}
		return this;
	}

	public ViewHolder setProgress(int viewId, int progress) {
		ProgressBar view = getView(viewId);
		view.setProgress(progress);
		return this;
	}

	public ViewHolder setProgress(int viewId, int progress, int max) {
		ProgressBar view = getView(viewId);
		view.setMax(max);
		view.setProgress(progress);
		return this;
	}

	public ViewHolder setMax(int viewId, int max) {
		ProgressBar view = getView(viewId);
		view.setMax(max);
		return this;
	}

	public ViewHolder setRating(int viewId, float rating) {
		RatingBar view = getView(viewId);
		view.setRating(rating);
		return this;
	}

	public ViewHolder setRating(int viewId, float rating, int max) {
		RatingBar view = getView(viewId);
		view.setMax(max);
		view.setRating(rating);
		return this;
	}

	public ViewHolder setOnClickListener(int viewId, int position,
			View.OnClickListener listener) {
		View view = getView(viewId);
		view.setOnClickListener(listener);
		return this;
	}

	public ViewHolder setOnTouchListener(int viewId,
			View.OnTouchListener listener) {
		View view = getView(viewId);
		view.setOnTouchListener(listener);
		return this;
	}

	public ViewHolder setOnLongClickListener(int viewId,
			View.OnLongClickListener listener) {
		View view = getView(viewId);
		view.setOnLongClickListener(listener);
		return this;
	}

	public ViewHolder setOnItemClickListener(int viewId,
			AdapterView.OnItemClickListener listener) {
		AdapterView view = getView(viewId);
		view.setOnItemClickListener(listener);
		return this;
	}

	public ViewHolder setOnItemLongClickListener(int viewId,
			AdapterView.OnItemLongClickListener listener) {
		AdapterView view = getView(viewId);
		view.setOnItemLongClickListener(listener);
		return this;
	}

	public ViewHolder setOnItemSelectedClickListener(int viewId,
			AdapterView.OnItemSelectedListener listener) {
		AdapterView view = getView(viewId);
		view.setOnItemSelectedListener(listener);
		return this;
	}

	public ViewHolder setTag(int viewId, Object tag) {
		View view = getView(viewId);
		view.setTag(tag);
		return this;
	}

	public ViewHolder setTag(int viewId, int key, Object tag) {
		View view = getView(viewId);
		view.setTag(key, tag);
		return this;
	}

	public ViewHolder setChecked(int viewId, boolean checked) {
		Checkable view = (Checkable) getView(viewId);
		view.setChecked(checked);
		return this;
	}

	public ViewHolder setHint(int viewId, String text) {
		EditText view = (EditText) getView(viewId);
		view.setHint(text);
		return this;
	}

	public ViewHolder addViewLinearLayout(int viewId, View v) {
		LinearLayout flowLayout = getView(viewId);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.rightMargin = 2;
		layoutParams.setMargins(10, 10, 10, 10);
		flowLayout.addView(v, layoutParams);
		return this;
	}

	public ViewHolder setInputType(int edit, int typeTextFlagAutoComplete) {
		// TODO Auto-generated method stub
		EditText view = (EditText) getView(edit);
		view.setInputType(typeTextFlagAutoComplete);
		return this;
	}

    public void setNeedChange(int tv_shop_address, boolean needChange) {
		MyTextView view = (MyTextView) getView(tv_shop_address);
		view.setExceed(needChange);
    }
}
