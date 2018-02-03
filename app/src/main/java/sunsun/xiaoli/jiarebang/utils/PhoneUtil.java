package sunsun.xiaoli.jiarebang.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class PhoneUtil {
	public static void goTel(String phone,Activity activity){
         Uri uri=Uri.parse("tel:"+phone);  
         Intent intent=new Intent();  
         intent.setAction(Intent.ACTION_CALL);  
         intent.setData(uri);  
         activity.startActivity(intent);  
	}
}
