package sunsun.xiaoli.jiarebang.sunsunlingshou.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import sunsun.xiaoli.jiarebang.R;

import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.ScreenUtils.dip2px;


/**
 * 
 * @author Young
 * 
 */
public class CarouselView extends ViewPager {

	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	// private int index;
	private float mRatio;
	private List<View> viewContainter = new ArrayList<View>();
	Context context;
	AttributeSet attrs;
	private long durating = 2000;// 切换间隔
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			try {
				setCurrentItem((getCurrentItem() + 1) % viewContainter.size());
			} catch (Exception e) {
				 
			}
		};
	};

	public CarouselView(Context context) {
		super(context);
		this.context=context;
		init();
	}

	public CarouselView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		this.attrs=attrs;
		init();
	}

	private void init() {
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.LoweImageView);
		mRatio = typedArray.getFloat(R.styleable.LoweImageView_ratio, 0);
		typedArray.recycle();
		setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				switch (arg0) {
				case ViewPager.SCROLL_STATE_DRAGGING:
					handler.removeMessages(1);
					break;
				case ViewPager.SCROLL_STATE_IDLE:
					handler.sendEmptyMessageDelayed(1, durating);

					break;
				default:
					break;
				}
			}
		});

		// 修改滚动持续时间
		try {
			Field field = ViewPager.class.getDeclaredField("mScroller");
			field.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(getContext(),
					new LinearInterpolator());

			field.set(this, scroller);
			scroller.setmDuration(1000);
		} catch (Exception e) {

		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		//
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		//
		int heightSize;
		//
		if (widthMode == MeasureSpec.EXACTLY && mRatio > 0) {
			heightSize = (int) (widthSize / mRatio + 0.5f);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
					MeasureSpec.EXACTLY);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	private class FixedSpeedScroller extends Scroller {
		private int mDuration = 1500;

		public FixedSpeedScroller(Context context) {
			super(context);
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator) {
			super(context, interpolator);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy,
				int duration) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		public void setmDuration(int time) {
			mDuration = time;
		}

		public int getmDuration() {
			return mDuration;
		}
	}

	public void setImageBitmaps(final List<ImageView> imageViews) {

		if (imageViews.size() == 0) {
			return;
		}
		viewContainter.clear();

		for (ImageView imageView : imageViews) {
			viewContainter.add(imageView);
		}
		setAdapter(new PagerAdapter() {

			@Override
			public void destroyItem(ViewGroup container, int position,
                                    Object object) {
				container.removeView(viewContainter.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(viewContainter.get(position));
				return viewContainter.get(position);

			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return viewContainter.size();
			}
		});

		start();
	}

	public void start() {
		handler.sendEmptyMessageDelayed(1, durating);
	}

	/**
	 * 
	 * 修改切换间隔时间 毫秒
	 * 
	 * @param durating
	 */
	public void setDuration(long durating) {
		this.durating = durating;
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {

		super.dispatchDraw(canvas);

		canvas.translate(getScrollX(), 0);
		if (viewContainter != null) {
			int bottom = dip2px(10);
			int r = dip2px(3);
			int distance = dip2px(10);

			int marginLeft = (getWidth() - distance
					* (viewContainter.size() - 1)) / 2;
			canvas.translate(marginLeft, getHeight() - bottom);
			for (int i = 0; i < viewContainter.size(); i++) {
				if (i == getCurrentItem()) {
					paint.setColor(Color.RED);
				} else {
					paint.setColor(Color.WHITE);
				}
				canvas.drawCircle(0, 0, r, paint);
				canvas.translate(distance, 0);
			}
		}

	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (handler != null) {
			handler.removeMessages(1);

		}

	}

	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
		if (handler != null) {
			handler.sendEmptyMessageDelayed(1, durating);
		}
	}
}
