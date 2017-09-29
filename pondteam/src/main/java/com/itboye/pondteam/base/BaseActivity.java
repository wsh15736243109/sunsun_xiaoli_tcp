package com.itboye.pondteam.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.itboye.pondteam.R;
import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.utils.loadingutil.LoadingDialog;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;


@SuppressLint("ResourceAsColor") @TargetApi(Build.VERSION_CODES.HONEYCOMB) public abstract class BaseActivity extends FragmentActivity implements
		OnClickListener {
	protected Context mContext;
	private View statusBar;
	public boolean isGestureOpen;
	private ViewContainer container;
	LoadingDialog progressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
//		setContentView(layout_id());

		mContext = getApplicationContext();
		progressDialog = new LoadingDialog();
//		switchLanguage(SPUtils.get(this,null, Const.laguageType,"ch")+"");
//		PushAgent.getInstance(getApplicationContext()).onAppStart();
		other();
		setStatusBarColor(0xffdcdddd);
	}

	public void AlertShow(Object msg){
		MAlert.alert(msg);
	}

	/**
	 * //     * @param data
	 *
	 * @return 2016-4-23 hebidu
	 */
	public ResultEntity handlerError(Object data) {
		ResultEntity result = (ResultEntity) data;
		if (result == null) {
			result = new ResultEntity(result.getCode(), "数据格式错误!~", data);
			return result;
		}

		if (result.hasError()) {

			if (result.getMsg().contains("java.net.UnknownHostException")) {
				MAlert.alert("请连接网络 ");
			} else {
				MAlert.alert(result.getMsg());
			}
			result.setEventType(EVENT_TYPE_UNKNOWN);
			return result;
		}

		return result;
	}



	@SuppressLint({"InlinedApi", "WrongConstant"})
	void other() {
//		statusBar = new View(this);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window window = getWindow();
			// Translucent status bar
			window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

			statusBar = new View(this);
			ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
			statusBar.setLayoutParams(param);
			 setStatusBarColor(R.color.main_green);

			container = new ViewContainer(getApplicationContext());
			container.setOrientation(LinearLayout.VERTICAL);
			container.addView(statusBar);
		}
		smartInject();// 自动获取控件
	}

	/**
	 *
	 * <切换语言>
	 *
	 * @param language
	 * @see [类、类#方法、类#成员]
	 */
	protected void switchLanguage(String language)
	{
		// 设置应用语言类型
		Resources resources = getResources();
		Configuration config = resources.getConfiguration();
		DisplayMetrics dm = resources.getDisplayMetrics();
		if (language.equals("en"))
		{
			config.locale = Locale.ENGLISH;
		}
		else
		{
			// 简体中文
			config.locale = Locale.SIMPLIFIED_CHINESE;
		}
		resources.updateConfiguration(config, dm);

//		// 保存设置语言的类型
//		SPUtils.put(this,null,Const.laguageType,"ch");
//		PreferenceUtil.commitString("language", language);
	}

	public static final String EVENT_TYPE_UNKNOWN = BaseActivity.class
			.getName() + "_unknown";

//	/**
//	 * @param data
//	 * @return 2016-4-23 hebidu
//	 */
//	public ResultEntity handlerError(Object data) {
//		ResultEntity result = (ResultEntity) data;
//		if (result == null) {
//			result = new ResultEntity("-1", "数据格式错误!~", data);
//			return result;
//		}
//
//		if (result.hasError()) {
//			ByAlert.alert(result.getMsg());
//			result.setEventType(EVENT_TYPE_UNKNOWN);
//			return result;
//		}
//
//		return result;
//	}
//
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}
	

	@Override
	public void setContentView(@LayoutRes int layoutResID) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (container != null) {
				LayoutInflater.from(this).inflate(layoutResID, container, true);
				setContentView(container);
			}
		} else {
			setContentView(layoutResID);
		}
		smartInject();
		// FrameLayout container = new FrameLayout(this);
		// // 在用户布局外面嵌套一个FrameLayout，防止用户布局中根控件的margin失效
		// LayoutInflater.from(this).inflate(layoutResID, container, true);
		// setContentView(container);

	}

	/**
	 * 设置状态栏颜色，
	 * 
	 * @param color
	 *            颜色资源id， 如 R.color.orange</br> 0 黑色
	 */
	protected void setStatusBarColor(int color) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (color == 0) {
				Log.v("hutou","0");
				statusBar.setBackgroundColor(Color.BLACK);
			} else {
				Log.v("hutou","1");
				try {
					statusBar.setBackgroundColor(getResources().getColor(color));
				}catch (Exception e){
					statusBar.setBackgroundColor(color);
				}
			}

		}
	}

	protected int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height",
				"dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
	
	/**
	 * 显示进度对话框,带有消息 flag 是否可以取消
	 */
	@SuppressLint("NewApi")
	public void showProgressDialog(String message, boolean flag) {
		if (progressDialog == null)
			return;
		setProgressDialogMessage(message);
		if (!progressDialog.isAdded()) {
			progressDialog.show(getFragmentManager(), message);
			progressDialog.setCancelable(flag);
		}
	}

	/**
	 * 设置进度对话框消息
	 * 
	 * @param message
	 */
	public void setProgressDialogMessage(String message) {
		progressDialog.setMsg(message);
	}

	/**
	 * 关闭进度对话框
	 */
	@SuppressLint("NewApi")
	public void closeProgressDialog() {
		if (progressDialog != null) {
//			if (progressDialog.getDialog().isShowing()) {
				progressDialog.dismiss();
//			}
		}
	}

	private void smartInject() {

		try {
			Class<? extends Activity> clz = getClass();

			while (clz != BaseActivity.class) {

				Field[] fs = clz.getDeclaredFields();
				Resources res = getResources();
				String packageName = getPackageName();
				for (Field field : fs) {
					if (!View.class.isAssignableFrom(field.getType())) {
						continue;
					}
					int viewId = res.getIdentifier(field.getName(), "id",
							packageName);
					if (viewId == 0)
						continue;
					field.setAccessible(true);
					try {
						View v = findViewById(viewId);
						field.set(this, v);
						Class<?> c = field.getType();
						//判断是否有注解
						if (field.getAnnotations() != null) {
							if (field.isAnnotationPresent(IsNeedClick.class)) {//如果属于这个注解
								//为这个控件设置属性
								field.setAccessible(true);//允许修改反射属性
								IsNeedClick inject = field.getAnnotation(IsNeedClick.class);
//                                int value=inject.value();//得到注解的值
//                                if(value==-1){
//
//                                }
							} else {
								Method m = c.getMethod("setOnClickListener",
										View.OnClickListener.class);
								m.invoke(v, this);
							}
						} else {
//                            Method m = c.getMethod("setOnClickListener",
//                                    View.OnClickListener.class);
//                            m.invoke(v, this);
						}
					} catch (Throwable e) {
					}
					field.setAccessible(false);

				}

				clz = (Class<? extends Activity>) clz.getSuperclass();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 屏幕左侧右划返回容器 ,
	 * 
	 * @author Young
	 * 
	 */
	private class ViewContainer extends LinearLayout {

		private int leftMargin;
		private VelocityTracker tracker;
		private float startX;
		private float startY;

		public ViewContainer(Context context) {
			super(context);
//			leftMargin = DensityUtil.dip2px(35);

		}

		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			if (isGestureOpen == false) {
				return super.dispatchTouchEvent(ev);
			}
			switch (ev.getAction()) {
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				// 当满足下面条件时 视为 右划关闭手势
				// 起始按压位置x坐标小与leftMargin&& 向右滑动 && 向右滑动距离 > 竖直方向距离
				if (startX < leftMargin
						&& ev.getRawX() > startX
						&& ev.getRawX() - startX > Math.abs(ev.getRawY()
								- startY)) {
					// 速度大于2500时关闭activity
					tracker.computeCurrentVelocity(1000);
					if (tracker.getXVelocity() > 2500) {
						finish();
					}

				}

				tracker.recycle();
				break;

			case MotionEvent.ACTION_DOWN:
				startX = ev.getRawX();
				startY = ev.getRawY();
				tracker = VelocityTracker.obtain();
				tracker.addMovement(ev);
				break;
			case MotionEvent.ACTION_MOVE:
				tracker.addMovement(ev);
				break;
			}

			return super.dispatchTouchEvent(ev);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if (isGestureOpen == false) {
				return super.onTouchEvent(event);
			}
			return true;
		}

	}


}
