package com.itboye.pondteam.base;

import android.R;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 创建Dialog建议继承自该BaseDialogFragment</br>
 * 继承自该Fragment的类不需要findViewById，在onActivityCreated中调用super.onActivityCreated()后即可直接使用全局的控件了，具体使用方式参看
 *
 */
public class BaseDialogFragment extends DialogFragment {


//	@Override
//	public View onCreateView(LayoutInflater inflater,
//			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		View v= super.onCreateView(inflater, container, savedInstanceState);
//		
//		smartInject(v);
//		return  v;
//	}
	
	@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setStyle(STYLE_NO_TITLE, R.style.Theme_Translucent);
		}
 
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		smartInject(getView());
	}

	protected void smartInject(View view) {  
        try {
			Class<? extends Fragment> clz= getClass();
			while ( clz!=BaseDialogFragment.class) {
				Field[] fs=clz.getDeclaredFields();  
			    Resources res = getResources();  
			    String packageName = getActivity().getPackageName();  
			    for (Field field : fs) {  
			    	if (!View.class.isAssignableFrom(field.getType())) {
						continue;
					}
			        int viewId = res.getIdentifier(field.getName(), "id", packageName);  
			        if (viewId==0)   
			            continue;  
			        field.setAccessible(true);  
			        try {  
			            View v=view.findViewById(viewId);  
			            field.set(this,v);  
			            Class<?> c=field.getType();  
			            Method m=c.getMethod("setOnClickListener", View.OnClickListener.class);
			            m.invoke(v, this);  
			        } catch (Throwable e) {  
			        }  
			        field.setAccessible(false);  
			          
			    }  
			   clz=(Class<? extends Fragment>) clz.getSuperclass();
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
          
    } 
	
}
