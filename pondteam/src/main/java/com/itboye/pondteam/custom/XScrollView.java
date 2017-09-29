package com.itboye.pondteam.custom;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ScrollView;

import com.nineoldandroids.view.ViewHelper;


/**
 * 下拉hui tan
 *
 * @author Young
 */
public class XScrollView extends ScrollView {

    //private static final long PRESSTIME = 1000;//延时1000ms 后悬停
    private Context context;
    //private long pressTime;

    public XScrollView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        this.context = context;
        initView();
    }

    public XScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public XScrollView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {

        setOverScrollMode(View.OVER_SCROLL_NEVER);
        //setScrollBarStyle(ScrollView.SCROLLBARS_OUTSIDE_OVERLAY);
    }

    private OnOverScrollListener listener;

    public void setOnOverScrollListener(OnOverScrollListener listener) {
        this.listener = listener;
    }

    public interface OnOverScrollListener {
        void overScrollDown(float dis);

        void overScrollUp(float dis);

        void onScrollResumeFromBottomToTop(float maxDis, float dis);

        void onScrollResumeFromTopToBottom(float maxDis, float dis);

        void onScrollResumeFinished();

    }

    private ScrollListener scrollListener;

    public void setOnScrollListener(ScrollListener listener) {
        this.scrollListener = listener;
    }

    public interface ScrollListener {
        public void OnScrollViewScrolled(int dis);
    }

    private float pressY;
    private float downDis;
    private float upDis;

    private float pressX;

    private ObjectAnimator animator;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return super.onInterceptTouchEvent(ev);
        }

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//			if (animator!=null) {
//				animator.end();
//			}
            pressY = ev.getRawY();
            pressX = ev.getRawX();
            //	pressTime=System.currentTimeMillis();
//			System.out.println("onInterceptTouchEvent down "+pressY);
        }
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            float nowX = ev.getRawX();
            float nowY = ev.getRawY();

            float disX = Math.abs(nowX - pressX);
            float disY = Math.abs(nowY - pressY);
            if (disY >= ViewConfiguration.get(context).getScaledTouchSlop() && disY > disX) {
                return true;
            }

        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return super.onTouchEvent(ev);
        }
        try {//2.3会抛异常

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressY = ev.getRawY();

//			System.out.println("down "+pressY);
                    break;
                case MotionEvent.ACTION_MOVE:
//				if (Math.abs(ev.getRawY()-pressY)<ViewConfiguration.get(getContext()).getScaledEdgeSlop()) {
//					pressTime=System.currentTimeMillis();
//				}


//			System.out.println("move "+pressTime);
                    int sy = getScrollY();
//			System.out.println("move    getRawY： " +ev.getRawY()+   "    getScrollY: "+sy  +"   getChildAt(0).getHeight()："+getChildAt(0).getHeight()+"   getHigh():"+getHeight());
                    if (sy == 0 && ev.getRawY() > pressY) {
//				isDown=true;
                        downDis = (float) ((ev.getRawY() - pressY) / 3);
                        ViewHelper.setTranslationY(getChildAt(0), downDis);
                        //	getChildAt(0).setTranslationY(downDis);
                        if (listener != null) {
                            listener.overScrollDown(downDis);
                        }
                        return true;
                    }
//			if (isDown) {
//				
//				setTranslationY(ev.getRawY()-pressY);
//				
//				return true;
//			}

                    if (sy + getHeight() == getChildAt(0).getHeight() && ev.getRawY() < pressY) {
//				isUp=true;
                        upDis = (float) ((ev.getRawY() - pressY) / 3);
                        ViewHelper.setTranslationY(getChildAt(0), upDis);
//					getChildAt(0).setTranslationY(upDis);
                        if (listener != null) {
                            listener.overScrollUp(upDis);
                        }
                        return true;

                    }

                    if (getHeight() >= getChildAt(0).getHeight() && ev.getRawY() < pressY) {
                        upDis = (float) ((ev.getRawY() - pressY) / 3);
                        ViewHelper.setTranslationY(getChildAt(0), upDis);
//					getChildAt(0).setTranslationY(upDis);
                        if (listener != null) {
                            listener.overScrollUp(upDis);
                        }
                        return true;
                    }
//			if (isUp) {
//				setTranslationY(ev.getRawY()-pressY);
//				return true;
//				
//			}
                    pressY = ev.getRawY();
                    try {
                        return super.onTouchEvent(ev);
                    } catch (Exception e) {

                        e.printStackTrace();
                        return false;
                    }

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    //	long holdTime=System.currentTimeMillis();
//			System.out.println("up   "+holdTime);
//				if (holdTime-pressTime>PRESSTIME&&ev.getRawY()>pressY) {
//					
//					try {
//						return super.onTouchEvent(ev);
//					} catch (Exception e) {
//						
//						e.printStackTrace();
//						return false;
//					}
//				}
//				final float start=getChildAt(0).getTranslationY();
                    final float start = ViewHelper.getTranslationY(getChildAt(0));
                    animator = ObjectAnimator.ofFloat(getChildAt(0), "translationY", start, 0);
                    animator.setDuration(500);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {

                            if (listener != null) {
                                if (ev.getRawY() > pressY) {
                                    listener.onScrollResumeFromBottomToTop(start, (Float) animation.getAnimatedValue());
                                } else
                                    listener.onScrollResumeFromTopToBottom(start, (Float) animation.getAnimatedValue());
                            }
                        }
                    });

                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {

                            super.onAnimationEnd(animation);
                            if (listener != null) {
                                listener.onScrollResumeFinished();
                            }
                        }
                    });
                    animator.start();

                    break;


            }
            try {
                return super.onTouchEvent(ev);
            } catch (Exception e) {

                e.printStackTrace();
                return false;
            }
        } catch (Throwable e) {

            try {
                return super.onTouchEvent(ev);
            } catch (Exception e2) {

                e2.printStackTrace();
                return false;
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        super.onScrollChanged(l, t, oldl, oldt);

        if (scrollListener != null) {
            scrollListener.OnScrollViewScrolled(t);
        }
    }
}
