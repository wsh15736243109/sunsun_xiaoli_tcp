package sunsun.xiaoli.jiarebang.shuizuzhijia.me;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.utils.XGlideLoaderNew;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

public class PersonSettingActivity extends BaseActivity implements
		OnClickListener {

	public static String AVARTACTION = "com.itboye.avart";

	public static String EMAILACTION = "com.itboye.email";
	private RelativeLayout nicknamerela;
	private RelativeLayout phonerela;
	private RelativeLayout emailrela;
	private RelativeLayout wechatrela;
	private RelativeLayout aboutrela;
	private RelativeLayout protocalrela;
	private RelativeLayout backrela;
	private RelativeLayout uploadrela;
	private RelativeLayout exitrela;
	private RelativeLayout relayoutUpdate;
	private DisplayImageOptions options;
	private ImageView avatarview;
	// private ImageView wechatview;
	private TextView phone;
	private TextView versionname;
	private TextView nicktext;
	private IWXAPI api;
	private static final String APP_ID = "wxabf9bc3a048000ac";
	private TextView emailtext;
	private String phonenumber;

	public static String EXITCHANGE = "com.itboye.sunsun.person.exit";
	// 点击数字拨打电话

	private String localSelectPath;
//	private CameraPopupWindow mCameraPopupWindow;

	private ImageView back;

	TextView wechatview;
	String openid;

	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);
		setContentView(R.layout.activity_personsetting);
		// 微信按钮
//		openid = (String) SPUtils.get(App.ctx, null, SPContants.WX_Openid, "");
		// Toast.makeText(App.ctx, openid, 0).show();
//		if (openid.equals("") || openid == null) {
//			wechatview.setVisibility(View.GONE);
//		} else {
//			wechatview.setVisibility(View.VISIBLE);
//			wechatview.setText("已绑定");
//		}
		emailtext.setClickable(false);
		nicktext.setClickable(false);
//		setStatusBarColor(R.color.home_blue);
		phonenumber = (String) SPUtils
				.get(MyApplication.getInstance(), null, getSp(Const.MOBILE), "");
		phone.setText(phonenumber);

		String emailtex = (String) SPUtils.get(App.getInstance(), null, getSp(Const.EMAIL),
				"");

		nicktext.setText((String) SPUtils.get(App.getInstance(), null, getSp(Const.NICK),
				""));
		// emailtext.setText((String) SPUtils.get(App.ctx, null,
		// SPContants.EMAIL,
		// ""));
		initavatar();

		if (!emailtex.equals("")) {
			try {

				String hiemial = emailtex.substring(0, 3) + "****"
						+ emailtex.substring(6, emailtex.length());
				emailtext.setText(hiemial);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}


		// 显示版本号
		try {
			PackageManager pm = App.getInstance().getPackageManager();
			PackageInfo info = pm.getPackageInfo(App.getInstance().getPackageName(), 0);
			String appVersion = info.versionName;
			versionname.setText(appVersion);
		} catch (Exception e) {

		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nicknamerela:
//			Intent intent = new Intent(PersonSettingActivity.this,
//					SetNickNameActivity.class);
//			startActivityForResult(intent, 1);
			break;
		case R.id.emailrela:
//			Intent intent1 = new Intent(PersonSettingActivity.this,
//					SetEmailActivity.class);
//			startActivityForResult(intent1, 3);
			break;

		case R.id.back:
			this.finish();
			break;
		case R.id.uploadrela:
			// TODO Auto-generated method stub
//			mCameraPopupWindow = new CameraPopupWindow(
//					PersonSettingActivity.this, onclicklister);
//			mCameraPopupWindow.showAtLocation(v, Gravity.BOTTOM
//					| Gravity.CENTER_HORIZONTAL, 0, 0);

			break;
//		case R.id.exitrela:
//			exit();
//			break;
		case R.id.wechatrela:

			if (openid.equals("") || openid == null) {
				welongin();
				// ByAlert.alert("已经绑定过了");
				// wechatview.setVisibility(View.VISIBLE);

				// wechatview.setText("已绑定");
			} else {
				System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKK" + openid);
			}

			break;
		case R.id.phonerela:
			onPhone();
			// Intent intent5 = new Intent(PersonSettingActivity.this,
			// ChangeBindActivity.class);
			// startActivity(intent5);
			break;
		case R.id.relayoutUpdate:
//			Intent intent6 = new Intent(PersonSettingActivity.this,
//					UpdateRsetPassActivity.class);
//			startActivity(intent6);
			break;

		default:
			break;

		}

	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	// 判断手机号是否为空，当为空的时候去绑定手机不为空的时候去修改
	public void onPhone() {
		System.out.println("phonenumberphonenumberphonenumber" + phonenumber);
		if (!isMobileNO(phonenumber)) {
//
//			Intent intent5 = new Intent(PersonSettingActivity.this,
//					PhoneBandingActivity.class);
//			startActivity(intent5);

		} else {

//			Intent intent5 = new Intent(PersonSettingActivity.this,
//					ChangeBindActivity.class);
//			startActivity(intent5);

		}
	}

	private OnClickListener onclicklister = new OnClickListener() {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.open_camera:
				/**
				 * 打开照相机
				 */
				String state = Environment.getExternalStorageState();
				if (state.equals(Environment.MEDIA_MOUNTED)) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(intent,
							CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				} else {
					MAlert.alert("请确认已经插入SD卡");
				}
				break;
			case R.id.pick_image:
				Intent intent2 = new Intent();
				intent2.setType("image/*");// 可选择图片视频
				// 修改为以下两句代码

				intent2.setAction(Intent.ACTION_PICK);
				intent2.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 使用以上这种模式，并添加以上两
				// intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				// "image/*");
				startActivityForResult(intent2, 0);
				break;
			case R.id.img:
				break;
			default:
				break;
			}
		}
	};

	private String picture_name;

	private String fileName;
	// 使用相机拍照
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 110;
	public static final int IMAGE_COMPLETE = 6; // 结果

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {
				/**
				 * 当选择的图片不为空的话，在获取到图片的途径
				 */
//				Uri selectedImage = data.getData();
//				localSelectPath = null;
//				// Cursor cursor = this.getContentResolver().query(
//				// data.getData(), null, null, null, null);
//				// cursor.moveToFirst();
//				// int columnIndex = cursor.getColumnIndex("_data");
//				// localSelectPath = cursor.getString(columnIndex);
//				localSelectPath = getRealPathFromURI(selectedImage);
//				localSelectPath = ImageCompress.compress(localSelectPath);
//				Intent intent2 = new Intent(PersonSettingActivity.this,
//						ClipActivity.class);
//				intent2.putExtra("path", localSelectPath);
//				startActivityForResult(intent2, IMAGE_COMPLETE);
			} else if (requestCode == 3) {
				// Toast.makeText(App.ctx, "haha", 0).show();
				emailtext.setText("" + data.getStringExtra("email"));
			} else if (requestCode == 1) {
				nicktext.setText("" + data.getStringExtra("nickname"));
			} else if (requestCode == 6) {
//				UploadImage.getInstance().execute(localSelectPath, this);
//				mCameraPopupWindow.dismiss();
			} else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE
					&& resultCode == RESULT_OK) {
//				String sdStatus = Environment.getExternalStorageState();
//				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
//					Log.i("TestFile",
//							"SD card is not avaiable/writeable right now.");
//					return;
//				}
//				picture_name = android.text.format.DateFormat.format(
//						"yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))
//						+ ".jpg";
//				Bundle bundle = data.getExtras();
//				Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
//				// SavePicInLocal(bitmap);
//				FileOutputStream b = null;
//				File file = new File("/sdcard/Image/");
//				file.mkdirs();// 创建文件夹
//				fileName = "/sdcard/Image/" + picture_name;
//
//				try {
//					b = new FileOutputStream(fileName);
//					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				} finally {
//					try {
//						b.flush();
//						b.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//				// fileName=ImageCompress.compress(fileName);
//				UploadImage.getInstance().execute(fileName, this);
//				mCameraPopupWindow.dismiss();

			}
		}
	}

	/**
	 * This method is used to get real path of file from from uri<br/>
	 * http://stackoverflow.com/questions/11591825/how-to-get-image-path-just-
	 * captured-from-camera
	 * 
	 * @param contentUri
	 * @return String
	 */
	public String getRealPathFromURI(Uri contentUri) {
		try {
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = this.managedQuery(contentUri, proj, null, null,
					null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} catch (Exception e) {
			return contentUri.getPath();
		}
	}

	/**
	 * 该调用系统的图片截取方法已被遗弃，华为手机报错
	 * 
	 * @param url
	 */
	public void startCrop(Uri url) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(url, "image/*");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		// intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);

	}

	// 显示头像
	public void initavatar() {

		String uri = (String) SPUtils.get(App.getInstance(), null, Const.UID,
				"");
		XGlideLoaderNew.displayImageCircularForUser(this,uri, avatarview);

	}

//	@Override
//	public void OnSuccess(String resultData) {
//		Toast.makeText(App.ctx, "上传成功", 0).show();
//		initavatar();
//		Intent intent = new Intent(AVARTACTION);
//		sendBroadcast(intent);
//	}
//
//	@Override
//	public void OnError(String resultData) {
//		Toast.makeText(App.ctx, resultData, 0).show();
//
//	}


	public void deleteInfo() {

//		SharedPreferences sp = getSharedPreferences("share_by_data",
//				MODE_PRIVATE);
//		sp.edit().putString(SPContants.USER_ID, "")
//				.putString(SPContants.MOBILE, "")
//				.putString(SPContants.EMAIL, "").putString(SPContants.NICK, "")
//				.putString(SPContants.PASSWORD, "")
//				.putString(SPContants.AVATAR_URL, "")
//				.putString(SPContants.USERNAME, "")
//				.putString(SPContants.WX_Openid, "")
//				.putBoolean(SPContants.IS_LOGINED, false).commit();
	}

	public void exit() {

//		try {
//			PushAgent.getInstance(App.getInstance())
//					.removeAlias(
//							(String) SPUtils.get(App.getInstance(), null,
//									Const.UID, ""), "sunsun");
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//		deleteInfo();
//		LoginController.setLoginState(new UnLoginState());
//
//		Intent intent = new Intent(PersonSettingActivity.this,
//				MainHomeActivity.class);
//		startActivity(intent);
//		Intent intent1 = new Intent(EXITCHANGE);
//		sendBroadcast(intent1);
//		this.finish();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	// 微信绑定
	private void welongin() {
		final SendAuth.Req req = new SendAuth.Req();
		req.scope = "snsapi_userinfo";
		req.state = "none";

		req.transaction = "bind";
		api.sendReq(req);
		// finish();

	}

	private void wechatBind(String uid) {

//		MyJsonRequest<String> request = new MyJsonRequest.Builder<String>()
//				.apiVer("100").typeKey("BY_Weixin_bind").param("code", code)
//				.param("uid", uid)
//				.requestListener(new XRequestListener<String>() {
//
//					@Override
//					public void onResponse(String response) {
//						// TODO Auto-generated method stub
//						Toast.makeText(MyApplication.context, "绑定成功", 0).show();
//						Intent intent = new Intent(NotifyPerson);
//						sendBroadcast(intent);
//
//					}
//				}).errorListener(new XErrorListener() {
//
//					@Override
//					public void onErrorResponse(Exception exception, int code,
//							String msg) {
//						if (exception instanceof CodeErrorException) {
//							Toast.makeText(MyApplication.context, msg, 0)
//									.show();
//						} else {
//							Toast.makeText(MyApplication.context,
//									exception.toString(), 0).show();
//
//						}
//
//					}
//				}).build();
//		HttpRequest.getDefaultRequestQueue().add(request);
	}


}
