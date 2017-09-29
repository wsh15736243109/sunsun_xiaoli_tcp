package com.pobing.uilibs.extend.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import android.util.Log;

public final class ImageTool {

    private final static int MAX_LENTH = 640;
    private static final String tag = "imageTool";

    /**
     * Tools for handler picture
     */

    /**
     * Transfer drawable to bitmap
     *
     * @param drawable
     * @return
     */
    /*
		public static Bitmap drawableToBitmap(Drawable drawable) {
			int w = drawable.getIntrinsicWidth();
			int h = drawable.getIntrinsicHeight();

			Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
					: Bitmap.Config.RGB_565;
			Bitmap bitmap = Bitmap.createBitmap(w, h, config);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, w, h);
			drawable.draw(canvas);
			return bitmap;
		}*/

    /**
     * Bitmap to drawable
     *
     * @param bitmap
     * @return
     */
		/*
		public Drawable bitmapToDrawable(Bitmap bitmap) {
			return new BitmapDrawable(bitmap);
		}
		 */
    /**
     * Input stream to bitmap
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
	/*
		public static Bitmap inputStreamToBitmap(InputStream inputStream)
				throws Exception {
			return BitmapFactory.decodeStream(inputStream);
		}*/

    /**
     * Byte transfer to bitmap
     *
     * @param byteArray
     * @return
     */
	/*
		public static Bitmap byteToBitmap(byte[] byteArray) {
			if (byteArray.length != 0) {
				return BitmapFactory
						.decodeByteArray(byteArray, 0, byteArray.length);
			} else {
				return null;
			}
		}*/

    /**
     * Byte transfer to drawable
     *
     * @param byteArray
     * @return
     */
	/*
		public Drawable byteToDrawable(byte[] byteArray) {
			ByteArrayInputStream ins = null;
			if (byteArray != null) {
				ins = new ByteArrayInputStream(byteArray);
			}
			return Drawable.createFromStream(ins, null);
		}*/

    /**
     * Bitmap transfer to bytes
     *
     * @param byteArray
     * @return
     */
    public static byte[] bitmapToBytes(Bitmap bm) {
        byte[] bytes = null;
        if (bm != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
            bytes = baos.toByteArray();
        }
        return bytes;
    }

    /**
     * Drawable transfer to bytes
     *
     * @param drawable
     * @return
     */
		/*
		public static byte[] drawableToBytes(Drawable drawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			Bitmap bitmap = bitmapDrawable.getBitmap();
			byte[] bytes = bitmapToBytes(bitmap);
			;
			return bytes;
		}*/

    /**
     * Base64 to byte[]
     //		 */
//		public static byte[] base64ToBytes(String base64) throws IOException {
//			byte[] bytes = Base64.decode(base64);
//			return bytes;
//		}
    //
//		/**
//		 * Byte[] to base64
//		 */
//		public static String bytesTobase64(byte[] bytes) {
//			String base64 = Base64.encode(bytes);
//			return base64;
//		}

    /**
     * Create reflection images
     *
     * @param bitmap
     * @return
     */
		/*
		public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
			final int reflectionGap = 4;
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();

			Matrix matrix = new Matrix();
			matrix.preScale(1, -1);

			Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
					h / 2, matrix, false);

			Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
					Config.ARGB_8888);

			Canvas canvas = new Canvas(bitmapWithReflection);
			canvas.drawBitmap(bitmap, 0, 0, null);
			Paint deafalutPaint = new Paint();
			canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

			canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

			Paint paint = new Paint();
			LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
					bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
					0x00ffffff, TileMode.CLAMP);
			paint.setShader(shader);
			// Set the Transfer mode to be porter duff and destination in
			paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
			// Draw a rectangle using the paint with our linear gradient
			canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
					+ reflectionGap, paint);

			return bitmapWithReflection;
		}*/

    /**
     * Get rounded corner images
     *
     * @param bitmap
     * @param roundPx
     *            5 10
     * @return
     */
		/*
		public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, w, h);
			final RectF rectF = new RectF(rect);
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);

			return output;
		}*/
		
		/*
		 *  rotate bitmap with degree
		 */
		/*
		public static Bitmap rotateBitmap(Bitmap b, int degrees)
		{
			if (degrees != 0 && b != null)
			{
				Matrix m = new Matrix();
				m.setRotate(degrees, b.getWidth() / 2, b.getHeight() / 2);
				try
				{
					Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
					b.recycle();
					b = null;
					return b2;
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				catch (Throwable ex)
				{
					ex.printStackTrace();
				}
			}
			return b;
		}*/

    /**
     * Resize the bitmap
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    public static int getZoomWidth(int width, int height, int maxLenth) {
        int mMaxLenth = MAX_LENTH;
        if (maxLenth > MAX_LENTH)
            mMaxLenth = maxLenth;
        float scale = 0;
        if (width > height)
            scale = (float) width / mMaxLenth;
        else
            scale = (float) height / mMaxLenth;
        return (int) (width / scale + 0.5);
    }

    public static int getZoomHeight(int width, int height, int maxLenth) {
        int mMaxLenth = MAX_LENTH;
        if (maxLenth > MAX_LENTH)
            mMaxLenth = maxLenth;
        float scale = 0;
        if (width > height)
            scale = (float) width / mMaxLenth;
        else
            scale = (float) height / mMaxLenth;
        return (int) (height / scale + 0.5);
    }

    /**
     * Resize the drawable
     * @param drawable
     * @param w
     * @param h
     * @return
     */
		/*
		public Drawable zoomDrawable(Drawable drawable, int w, int h) {
			int width = drawable.getIntrinsicWidth();
			int height = drawable.getIntrinsicHeight();
			Bitmap oldbmp = drawableToBitmap(drawable);
			Matrix matrix = new Matrix();
			float sx = ((float) w / width);
			float sy = ((float) h / height);
			matrix.postScale(sx, sy);
			Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
					matrix, true);
			return new BitmapDrawable(newbmp);
		}
		*/

    /**
     * Get images from SD card by path and the name of image
     *
     * @param photoName
     * @return
     */
    public static Bitmap getPhotoFromSDCard(String path, String photoName) {
        Bitmap photoBitmap = BitmapFactory.decodeFile(path + "/" + photoName + ".jpg");
        if (photoBitmap == null) {
            return null;
        } else {
            return photoBitmap;
        }
    }

    public static Bitmap getPhotoFromSDCard(String fullname) {

        Bitmap photoBitmap = BitmapFactory.decodeFile(fullname);
        if (photoBitmap == null) {
            return null;
        } else {
            return photoBitmap;
        }
    }

    public static int getMaxLenthOfPic(String fullname) {

        //int degree = ThumbnailUtil.readRotationDegree(fullname);

        File file = new File(fullname);
        if (!file.exists()) {
            return 0;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // 获取这个图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(fullname, options);
        int maxLenth = options.outHeight > options.outWidth ? options.outHeight : options.outWidth;
        if (bitmap != null) {
            if (!bitmap.isRecycled())
                bitmap.recycle();
        }

        return maxLenth;
    }

    public static Bitmap getPhotoFromSDCardandZoom(String fullname, int maxLenth) {

        //int degree = ThumbnailUtil.readRotationDegree(fullname);

        File file = new File(fullname);
        if (!file.exists()) {
            return null;
        }

        int mMaxLenth = MAX_LENTH;
        if (maxLenth > MAX_LENTH)
            mMaxLenth = maxLenth;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // 获取这个图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(fullname, options);

        int temp = options.outHeight > options.outWidth ? options.outHeight : options.outWidth;
        int be = (int) (temp / (float) mMaxLenth);
        int ys = temp % mMaxLenth;//求余数
        float fe = ys / (float) temp;
        if (fe >= 0.5) be = be + 1;
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;

        //重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
        options.inJustDecodeBounds = false;
        if (bitmap != null) {
            if (!bitmap.isRecycled())
                bitmap.recycle();
        }

        Bitmap photoBitmap = null;
        try {
            photoBitmap = BitmapFactory.decodeFile(fullname, options);
        } catch (OutOfMemoryError outOfMemoryError) {
        }


        return photoBitmap;
    }
    private static String TAG = "ImageTool";


    /**
     * Check the SD card
     *
     * @return
     */
    public static boolean checkSDCardAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * Get image from SD card by path and the name of image
     *
     * @param fileName
     * @return
     */
    public static boolean findPhotoFromSDCard(String path, String photoName) {
        boolean flag = false;

        if (checkSDCardAvailable()) {
            File dir = new File(path);
            if (dir.exists()) {

                File folders = new File(path);
                File photoFile[] = folders.listFiles();
                for (int i = 0; i < photoFile.length; i++) {
                    String fileName = photoFile[i].getName().split("\\.")[0];
                    if (fileName.equals(photoName)) {
                        flag = true;
                    }
                }
            } else {
                flag = false;
            }
//				File file = new File(path + "/" + photoName  + ".jpg" );
//				if (file.exists()) {
//					flag = true;
//				}else {
//					flag = false;
//				}

        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * creat a file
     *
     * @param fileName
     * @param path
     */
    public static void creatFile(String path, String fileName) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(path, fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            file.delete();
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Save image to the SD card
     *
     * @param photoBitmap
     * @param photoName
     * @param path
     */
    public static void savePhotoToSDCard(Bitmap photoBitmap, String path, String photoName) {
        savePhotoDirectToSDCard(photoBitmap, new File(path, photoName + ".jpg"));
    }

    public static void savePhotoDirectToSDCard(Bitmap photoBitmap, File photoFile) {
        if (photoFile.getParentFile() != null) {
            photoFile.getParentFile().mkdirs();
        }
        FileOutputStream fileOutputStream = null;
        try {
            photoFile.createNewFile();
            fileOutputStream = new FileOutputStream(photoFile);
            if (photoBitmap != null) {
                if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
                    fileOutputStream.flush();
                }
            }
        } catch (FileNotFoundException e) {
            photoFile.delete();
            e.printStackTrace();
        } catch (IOException e) {
            photoFile.delete();
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Delete the image from SD card
     *
     * @param context
     * @param path    file:///sdcard/temp.jpg
     */
		/*
		public void deleteAllPhoto(String path){
			if (checkSDCardAvailable()) {
				File folder = new File(path);
				File[] files = folder.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
		}
		
		public void deletePhotoAtPathAndName(String path,String fileName){
			if (checkSDCardAvailable()) {
				File folder = new File(path);
				File[] files = folder.listFiles();
				for (int i = 0; i < files.length; i++) {
					System.out.println(files[i].getName());
					if (files[i].getName().equals(fileName)) {
						files[i].delete();
					}
				}
			}
		}
		*/
    public static void deletePhotobyName(String fileName) {
        if (checkSDCardAvailable()) {
            File fl = new File(fileName);
            if (fl.exists()) {
                fl.delete();
            }
        }
    }

    public static boolean isExistFile(String fileName) {
        if (null != fileName) {
            File fl = new File(fileName);
            return fl.exists();
        } else {
            return false;
        }
    }

    public static boolean isLocalFile(String picUrl) {
        if (TextUtils.isEmpty(picUrl)) {
            return false;
        }
        return (picUrl.contains("/storage") || picUrl.startsWith("/"));
    }

    public static String getSystemTimeString() {
        String str = String.valueOf(System.currentTimeMillis());
        return str;
    }

    public static int getRandom(int a) {
        return (int) (Math.random() * a);
    }

}
