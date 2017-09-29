package ChirdSdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class StreamView extends SurfaceView implements SurfaceHolder.Callback {

	protected long lastDown = -1;
	public final static long DOUBLE_TOUCH_TIME = 250;

	private Bitmap sourceBitmap = null;

	private boolean surfaceDone = false;
	private SurfaceHolder mSurfaceHolder;
	private BitmapFactory.Options opts = new BitmapFactory.Options();

	private CallBack myCallBack = null;

	private boolean isMirrorImage = false;
	private Matrix matrix = new Matrix();
	

	/**
	 * 初始化状态常量
	 */
	public static final int STATUS_INIT = 1;

	/**
	 * 图片放大状态常量
	 */
	public static final int STATUS_ZOOM_OUT = 2;

	/**
	 * 图片缩小状态常量
	 */
	public static final int STATUS_ZOOM_IN = 3;

	/**
	 * 图片拖动状态常量
	 */
	public static final int STATUS_MOVE = 4;

	/**
	 * 记录当前操作的状态，可选值为STATUS_INIT、STATUS_ZOOM_OUT、STATUS_ZOOM_IN和STATUS_MOVE
	 */
	private int currentStatus;

	/**
	 * ZoomImageView控件的宽度
	 */
	private int swidth;

	/**
	 * ZoomImageView控件的高度
	 */
	private int sheight;

	/**
	 * 记录两指同时放在屏幕上时，中心点的横坐标值
	 */
	private float centerPointX;

	/**
	 * 记录两指同时放在屏幕上时，中心点的纵坐标值
	 */
	private float centerPointY;

	/**
	 * 记录当前图片的宽度，图片被缩放时，这个值会一起变动
	 */
	private float currentBitmapWidth;

	/**
	 * 记录当前图片的高度，图片被缩放时，这个值会一起变动
	 */
	private float currentBitmapHeight;

	/**
	 * 记录上次手指移动时的横坐标
	 */
	private float lastXMove = -1;

	/**
	 * 记录上次手指移动时的纵坐标
	 */
	private float lastYMove = -1;

	/**
	 * 记录手指在横坐标方向上的移动距离
	 */
	private float movedDistanceX;

	/**
	 * 记录手指在纵坐标方向上的移动距离
	 */
	private float movedDistanceY;

	/**
	 * 记录图片在矩阵上的横向偏移值
	 */
	private float totalTranslateX;

	/**
	 * 记录图片在矩阵上的纵向偏移值
	 */
	private float totalTranslateY;

	/**
	 * 记录图片在矩阵上的总缩放比例
	 */
	private float totalRatio;

	/**
	 * 记录手指移动的距离所造成的缩放比例
	 */
	private float scaledRatio;

	/**
	 * 记录图片初始化时的缩放比例
	 */
	private float initRatio;

	/**
	 * 记录上次两指之间的距离
	 */
	private double lastFingerDis;

	public interface CallBack {
		void callbackSurface(Surface surface);

		void callbackDestroy();
	}

	public StreamView(Context context, CallBack myListener) {
		super(context);

		setFocusable(true);
		swidth = getWidth();
		sheight = getHeight();

		mSurfaceHolder = getHolder();
		this.myCallBack = myListener;
		mSurfaceHolder.addCallback(this);
		mShowMode = StreamView.SHOW_MODE_BEST_FIT;

		currentStatus = STATUS_INIT;
	}

	/* 原始分辨率 */
	public final static int SHOW_MODE_STANDARD = 1;
	/* 等比适应屏幕放到最大 */
	public final static int SHOW_MODE_BEST_FIT = 4;
	/* 全屏显示，不支持触屏 */
	public final static int SHOW_MODE_FULLSCREEN = 8;
	private int mShowMode;
	public int getShowMode() {
		return mShowMode;
	}

	public void setShowMode(int mode) {
		mShowMode = mode;
		if (mShowMode != SHOW_MODE_FULLSCREEN) {
			currentStatus = STATUS_INIT;
			refreshScreen();
		}
	}

	/* Whether the video image Mirror display */
	public boolean getMirrorImage() {
		return isMirrorImage;
	}

	/* set video image mirror display */
	public void setMirrorImage(boolean mirror) {
		isMirrorImage = mirror;
	}

	private boolean isSupportTouchZoom = true;
	/* Whether to support the finger kneading zoom and move */
	public boolean isSupportTouchZoom() {
		return isSupportTouchZoom;
	}
	/* set video image support the finger kneading zoom and move */
	public void setSupportTouchZoom(boolean support) {
		isSupportTouchZoom = support;
		if (mShowMode != SHOW_MODE_FULLSCREEN) {
			currentStatus = STATUS_INIT;
			refreshScreen();
		}
	}

	/* show bitmap to screen */
	public void showBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			return;
		}
		Canvas canvas = null;
		if (surfaceDone) {
			try {
				canvas = mSurfaceHolder.lockCanvas();

				if (sourceBitmap != null
						&& bitmap != null
						&& (sourceBitmap.getWidth() != bitmap.getWidth() || sourceBitmap
								.getHeight() != bitmap.getHeight())) {
					currentStatus = STATUS_INIT;
				}

				synchronized (mSurfaceHolder) {
					try {
						sourceBitmap = bitmap;
						synchronized (sourceBitmap) {
							/* mirror transition */
							if (isMirrorImage) {
								Matrix m = new Matrix();
								m.postScale(-1, 1);
								sourceBitmap = Bitmap.createBitmap(
										sourceBitmap, 0, 0,
										sourceBitmap.getWidth(),
										sourceBitmap.getHeight(), m, true);
							}

							if (mShowMode == SHOW_MODE_FULLSCREEN) {
								Rect destRect = new Rect(0, 0, swidth, sheight);
								canvas.drawBitmap(sourceBitmap, null, destRect,
										null);
							} else {
								canvas.drawColor(Color.rgb(0x00, 0x00, 0x00));

								switch (currentStatus) {
								case STATUS_ZOOM_OUT:
								case STATUS_ZOOM_IN:
									zoom(canvas);
									break;
								case STATUS_MOVE:
									move(canvas);
									break;
								case STATUS_INIT:
									initBitmap(canvas);
								default:
									canvas.drawBitmap(sourceBitmap, matrix,
											null);
									break;
								}
								currentStatus = 0;
							}

						}
					} catch (Exception e) {
					}
				}
			} finally {
				if (canvas != null) {
					mSurfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}

	}

	/* Use system decoder JPEG and display */
	public void showJpegData(byte[] Data, int Length) {
		Rect destRect;
		Canvas canvas = null;
		if (surfaceDone) {
			try {
				canvas = mSurfaceHolder.lockCanvas();
				synchronized (mSurfaceHolder) {
					try {
						sourceBitmap = null;
						opts.inSampleSize = 1;
						sourceBitmap = BitmapFactory.decodeByteArray(Data, 0,
								Length, opts);

						if (isMirrorImage) {
							Matrix m = new Matrix();
							m.postScale(-1, 1);
							sourceBitmap = Bitmap.createBitmap(sourceBitmap, 0,
									0, sourceBitmap.getWidth(),
									sourceBitmap.getHeight(), m, true);

						}

						if (mShowMode != SHOW_MODE_FULLSCREEN) {
							canvas.drawColor(Color.rgb(0x00, 0x00, 0x00));
							switch (currentStatus) {
							case STATUS_ZOOM_OUT:
							case STATUS_ZOOM_IN:
								zoom(canvas);
								break;
							case STATUS_MOVE:
								move(canvas);
								break;
							case STATUS_INIT:
								initBitmap(canvas);
							default:
								canvas.drawBitmap(sourceBitmap, matrix, null);
								break;
							}
							currentStatus = 0;
						}
					} catch (Exception e) {
					}
				}
			} finally {
				if (canvas != null) {
					mSurfaceHolder.unlockCanvasAndPost(canvas);
				}

			}
		}
	}

	public void clearScreen() {
		Canvas c = null;
		Paint p = new Paint();

		if (surfaceDone) {
			try {
				c = mSurfaceHolder.lockCanvas();
				synchronized (mSurfaceHolder) {
					try {
						Rect destRect = new Rect(0, 0, swidth, sheight);
						c.drawColor(Color.rgb(0x00, 0x00, 0x00));
						c.drawBitmap(null, null, destRect, p);
					} catch (Exception e) {
					}
				}
			} finally {
				if (c != null) {
					mSurfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}

	}

	/* refresh screen display */
	public void refreshScreen() {
//		Canvas canvas = null;
//		if (surfaceDone) {
//			try {
//				canvas = mSurfaceHolder.lockCanvas();
//				synchronized (mSurfaceHolder) {
//					synchronized (sourceBitmap) {
//						canvas.drawColor(Color.rgb(0x00, 0x00, 0x00));
//						switch (currentStatus) {
//						case STATUS_ZOOM_OUT:
//						case STATUS_ZOOM_IN:
//							zoom(canvas);
//							break;
//						case STATUS_MOVE:
//							move(canvas);
//							break;
//						case STATUS_INIT:
//							initBitmap(canvas);
//						default:
//							canvas.drawBitmap(sourceBitmap, matrix, null);
//							break;
//						}
//
//						currentStatus = 0;
//
//					}
//
//				}
//			} finally {
//				if (canvas != null) {
//					mSurfaceHolder.unlockCanvasAndPost(canvas);
//				}
//			}
//		}

	}

	private int SCALING_MAX = 4;

	public boolean onTouchEvent(MotionEvent event) {
	
		if (!isSupportTouchZoom) {
			return true;
		}

		switch (event.getActionMasked()) {

		case MotionEvent.ACTION_POINTER_DOWN:
			if (event.getPointerCount() == 2) {
				// 当有两个手指按在屏幕上时，计算两指之间的距离
				lastFingerDis = distanceBetweenFingers(event);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (event.getPointerCount() == 1) {
				// 只有单指按在屏幕上移动时，为拖动状态
				float xMove = event.getX();
				float yMove = event.getY();
				if (lastXMove == -1 && lastYMove == -1) {
					lastXMove = xMove;
					lastYMove = yMove;
				}
				currentStatus = STATUS_MOVE;
				movedDistanceX = xMove - lastXMove;
				movedDistanceY = yMove - lastYMove;

				// 进行边界检查，不允许将图片拖出边界
				if (totalTranslateX + movedDistanceX > 0) {
					movedDistanceX = 0;
				} else if (swidth - (totalTranslateX + movedDistanceX) > currentBitmapWidth) {
					movedDistanceX = 0;
				}
				if (totalTranslateY + movedDistanceY > 0) {
					movedDistanceY = 0;
				} else if (sheight - (totalTranslateY + movedDistanceY) > currentBitmapHeight) {
					movedDistanceY = 0;
				}
				// 调用onDraw()方法绘制图片
				refreshScreen();

				lastXMove = xMove;
				lastYMove = yMove;
			} else if (event.getPointerCount() == 2) {
				// 有两个手指按在屏幕上移动时，为缩放状态
				centerPointBetweenFingers(event);
				double fingerDis = distanceBetweenFingers(event);
				if (fingerDis > lastFingerDis) {
					currentStatus = STATUS_ZOOM_OUT;
				} else {
					currentStatus = STATUS_ZOOM_IN;
				}
				// 进行缩放倍数检查，最大只允许将图片放大4倍，最小可以缩小到初始化比例
				if ((currentStatus == STATUS_ZOOM_OUT && totalRatio < SCALING_MAX
						* initRatio)
						|| (currentStatus == STATUS_ZOOM_IN && totalRatio > initRatio)) {
					scaledRatio = (float) (fingerDis / lastFingerDis);
					totalRatio = totalRatio * scaledRatio;
					if (totalRatio > SCALING_MAX * initRatio) {
						totalRatio = SCALING_MAX * initRatio;
					} else if (totalRatio < initRatio) {
						totalRatio = initRatio;
					}
					// 调用onDraw()方法绘制图片
					refreshScreen();

					lastFingerDis = fingerDis;
				}
			}
			break;
		case MotionEvent.ACTION_POINTER_UP:
			if (event.getPointerCount() == 2) {
				// 手指离开屏幕时将临时值还原
				lastXMove = -1;
				lastYMove = -1;
			}
			break;
		case MotionEvent.ACTION_UP:
			// 手指离开屏幕时将临时值还原
			lastXMove = -1;
			lastYMove = -1;
			break;
		default:
			break;
		}
		return true;
	}

	private void initBitmap(Canvas canvas) {
		if (sourceBitmap != null) {
			matrix.reset();
			int bitmapWidth = sourceBitmap.getWidth();
			int bitmapHeight = sourceBitmap.getHeight();

			if (mShowMode == SHOW_MODE_BEST_FIT
					|| (mShowMode == SHOW_MODE_STANDARD && (bitmapWidth > swidth || bitmapHeight > sheight))
					|| bitmapWidth > swidth || bitmapHeight > sheight) {
				if (bitmapWidth - swidth > bitmapHeight - sheight) {
					// 当图片宽度大于屏幕宽度时，将图片等比例压缩，使它可以完全显示出来
					float ratio = swidth / (bitmapWidth * 1.0f);
					matrix.postScale(ratio, ratio);
					float translateY = (sheight - (bitmapHeight * ratio)) / 2f;
					// 在纵坐标方向上进行偏移，以保证图片居中显示
					matrix.postTranslate(0, translateY);
					totalTranslateY = translateY;
					totalRatio = initRatio = ratio;
				} else {
					// 当图片高度大于屏幕高度时，将图片等比例压缩，使它可以完全显示出来
					float ratio = sheight / (bitmapHeight * 1.0f);
					matrix.postScale(ratio, ratio);
					float translateX = (swidth - (bitmapWidth * ratio)) / 2f;
					// 在横坐标方向上进行偏移，以保证图片居中显示
					matrix.postTranslate(translateX, 0);
					totalTranslateX = translateX;
					totalRatio = initRatio = ratio;
				}
			} else {
				// 当图片的宽高都小于屏幕宽高时，直接让图片居中显示
				float translateX = (swidth - sourceBitmap.getWidth()) / 2f;
				float translateY = (sheight - sourceBitmap.getHeight()) / 2f;
				matrix.postTranslate(translateX, translateY);
				totalTranslateX = translateX;
				totalTranslateY = translateY;
				totalRatio = initRatio = 1f;
				currentBitmapWidth = bitmapWidth;
				currentBitmapHeight = bitmapHeight;
			}

			currentBitmapWidth = bitmapWidth * initRatio;
			currentBitmapHeight = bitmapHeight * initRatio;

			canvas.drawBitmap(sourceBitmap, matrix, null);
		}
	}

	/**
	 * 对图片进行缩放处理。
	 * 
	 * @param canvas
	 */
	private void zoom(Canvas canvas) {
		matrix.reset();
		// 将图片按总缩放比例进行缩放
		matrix.postScale(totalRatio, totalRatio);
		float scaledWidth = sourceBitmap.getWidth() * totalRatio;
		float scaledHeight = sourceBitmap.getHeight() * totalRatio;
		float translateX = 0f;
		float translateY = 0f;

		// 如果当前图片宽度小于屏幕宽度，则按屏幕中心的横坐标进行水平缩放。否则按两指的中心点的横坐标进行水平缩放
		if (currentBitmapWidth < swidth) {
			translateX = (swidth - scaledWidth) / 2f;
		} else {
			translateX = totalTranslateX * scaledRatio + centerPointX
					* (1 - scaledRatio);
			// 进行边界检查，保证图片缩放后在水平方向上不会偏移出屏幕
			if (translateX > 0) {
				translateX = 0;
			} else if (swidth - translateX > scaledWidth) {
				translateX = swidth - scaledWidth;
			}
		}
		// 如果当前图片高度小于屏幕高度，则按屏幕中心的纵坐标进行垂直缩放。否则按两指的中心点的纵坐标进行垂直缩放
		if (currentBitmapHeight < sheight) {
			translateY = (sheight - scaledHeight) / 2f;
		} else {
			translateY = totalTranslateY * scaledRatio + centerPointY
					* (1 - scaledRatio);
			// 进行边界检查，保证图片缩放后在垂直方向上不会偏移出屏幕
			if (translateY > 0) {
				translateY = 0;
			} else if (sheight - translateY > scaledHeight) {
				translateY = sheight - scaledHeight;
			}
		}
		// 缩放后对图片进行偏移，以保证缩放后中心点位置不变
		matrix.postTranslate(translateX, translateY);
		totalTranslateX = translateX;
		totalTranslateY = translateY;
		currentBitmapWidth = scaledWidth;
		currentBitmapHeight = scaledHeight;
		canvas.drawBitmap(sourceBitmap, matrix, null);
	}

	/**
	 * 对图片进行平移处理
	 * 
	 * @param canvas
	 */
	private void move(Canvas canvas) {
		matrix.reset();
		// 根据手指移动的距离计算出总偏移值
		float translateX = totalTranslateX + movedDistanceX;
		float translateY = totalTranslateY + movedDistanceY;

		// 先按照已有的缩放比例对图片进行缩放
		matrix.postScale(totalRatio, totalRatio);
		// 再根据移动距离进行偏移
		matrix.postTranslate(translateX, translateY);
		totalTranslateX = translateX;
		totalTranslateY = translateY;
		canvas.drawBitmap(sourceBitmap, matrix, null);
	}

	/**
	 * 计算两个手指之间的距离。
	 * 
	 * @param event
	 * @return 两个手指之间的距离
	 */
	private double distanceBetweenFingers(MotionEvent event) {
		float disX = Math.abs(event.getX(0) - event.getX(1));
		float disY = Math.abs(event.getY(0) - event.getY(1));
		return Math.sqrt(disX * disX + disY * disY);
	}

	/**
	 * 计算两个手指之间中心点的坐标。
	 * 
	 * @param event
	 */
	private void centerPointBetweenFingers(MotionEvent event) {
		float xPoint0 = event.getX(0);
		float yPoint0 = event.getY(0);
		float xPoint1 = event.getX(1);
		float yPoint1 = event.getY(1);
		centerPointX = (xPoint0 + xPoint1) / 2;
		centerPointY = (yPoint0 + yPoint1) / 2;
	}

	public void setSurfaceSize(int width, int height) {
		synchronized (mSurfaceHolder) {
			swidth = width;
			sheight = height;
		}
	}

	public Surface getSurface() {
		return mSurfaceHolder.getSurface();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		setSurfaceSize(width, height);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		surfaceDone = true;
		if (myCallBack != null) {
			myCallBack.callbackSurface(holder.getSurface());
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		if (myCallBack != null) {
			myCallBack.callbackDestroy();
		}

		surfaceDone = false;
	}

}