package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.itboye.pondteam.base.BaseDialogFragment;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.address.AddressSelectView;


@SuppressLint("ValidFragment")
public class AddressFragment extends BaseDialogFragment implements OnClickListener {

	
	private AddressSelectView addressSelect;
	public interface GetInforListener
	{
		void onGetinforListener(String province, String city, String district, String provinceNo, String cityNo, String districtNo);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 
		
		View v = inflater.inflate(R.layout.fragment_address2, container, false);
		v.findViewById(R.id.btn_confirm).setOnClickListener(this);
		addressSelect = (AddressSelectView) v.findViewById(R.id.addressselect);
		return v;
	}
	GetInforListener listener;
	@SuppressLint("ValidFragment")
	public AddressFragment(GetInforListener listener){
		this.listener=listener;
	}
	 

//	@Override
//	public void onStart() {
//		super.onStart();
//		final DisplayMetrics dm = new DisplayMetrics();
//		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//		final WindowManager.LayoutParams layoutParams = getDialog().getWindow()
//				.getAttributes();
//		layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//		layoutParams.gravity = Gravity.BOTTOM;
//		layoutParams.height=380;
//		getDialog().getWindow().setAttributes(layoutParams);
//	}

	@Override
	public void onClick(View arg0) {
		addressSelect.getAddressData();
		String provinceNo=Integer.toString(addressSelect.getProvinceNo());
		String cityNo=Integer.toString(addressSelect.getCityNo());
		String districtNo=Integer.toString(addressSelect.getDistrictNo());
		String province=addressSelect.getProvinceName();
		String city=addressSelect.getCityName();
		String districName= addressSelect.getDistrictName();
		this.listener.onGetinforListener(province,city, districName, provinceNo, cityNo, districtNo);
		//添加数据
//		Bundle bundle=new Bundle();
//		bundle.putString("provincename", addressSelect.getProvinceName());
//		bundle.putString("cityname", addressSelect.getCityName());
//		bundle.putString("districtname", addressSelect.getDistrictName());
//		bundle.putString("provinceno", provinceNo);
//		bundle.putString("cityno", cityNo);
//		fragment.setArguments(bundle);
//		FragmentManager fragmentManager = getFragmentManager();
//        //开始Fragment事务
//        FragmentTransaction fTransaction = fragmentManager.beginTransaction();
//        //将Fragment添加到事务中，并指定一个TAG 
//        fTransaction.add(fragment, "123");
//        //提交Fragment事务
//        fTransaction.commit();
		this.dismiss();

	}

}
