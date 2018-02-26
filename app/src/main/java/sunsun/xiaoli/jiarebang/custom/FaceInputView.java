package sunsun.xiaoli.jiarebang.custom;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sunsun.xiaoli.jiarebang.R;


public class FaceInputView extends LinearLayout implements View.OnClickListener {

	private OnFaceClickListener clickListener;
	private ViewPager viewPager;
	private List<View> list;

	private ImageView[] page_ivs=new ImageView[5];//5个底部位置标记
	private View view;
	private Map<Integer,Integer> ivIdToImgId=new HashMap<Integer,Integer>();//由控件id得到里面图片id
	private Context context;
	public FaceInputView(final Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.context=context;
		initView();
	}
	
	public FaceInputView(final Context context) {
		super(context);
		this.context=context;
		initView();
	}
	
	
//	public EditText getEditText(){
//		return (EditText) view.findViewById(R.id.content_et);
//	}
//	public Button getSendButton(){
//		return (Button) view.findViewById(R.id.send_but);
//	}
	private void initView() {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.face_input_tabs, this);
		
//		 ////////////////////////////////////
		page_ivs[0]=(ImageView)view.findViewById(R.id.page_iv1);
		page_ivs[1]=(ImageView) view.findViewById(R.id.page_iv2);
		page_ivs[2]=(ImageView) view.findViewById(R.id.page_iv3);
		page_ivs[3]=(ImageView)view.findViewById(R.id.page_iv4);
		page_ivs[4]=(ImageView)view.findViewById(R.id.page_iv5);
		 
//		////////////////////////
		viewPager=(ViewPager)view. findViewById(R.id.viewpager);

		View view1=LayoutInflater.from(context).inflate(R.layout.face_input_tab1, null);
		View view2=LayoutInflater.from(context).inflate(R.layout.face_input_tab2, null);
		View view3=LayoutInflater.from(context).inflate(R.layout.face_input_tab3, null);
		View view4=LayoutInflater.from(context).inflate(R.layout.face_input_tab4, null);
		View view5=LayoutInflater.from(context).inflate(R.layout.face_input_tab5, null);
	 
		initFaceIv(view1);
		initFaceIv(view2);
		initFaceIv(view3);
		initFaceIv(view4);
		initFaceIv(view5);
	 
		list=new ArrayList<View>();
		list.add(view1);
		list.add(view2);
		list.add(view3);
		list.add(view4);
		list.add(view5);
		 

		viewPager.setAdapter(new MyAdapter());
		
//		view.findViewById(R.id.add_face_iv).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				InputMethodManager inputMethodManager= (InputMethodManager)context. getSystemService(FaceInputView.this.context.INPUT_METHOD_SERVICE);
//				inputMethodManager.hideSoftInputFromWindow(view.findViewById(R.id.content_et).getWindowToken(), 0); //强制隐藏键盘
//				 view.findViewById(R.id.faceline).setVisibility(View.VISIBLE);
//			}
//		});
//		
//		view.findViewById(R.id.keyboard_iv).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				view.findViewById(R.id.faceline).setVisibility(View.GONE);		
//				InputMethodManager imm = (InputMethodManager)context. getSystemService(FaceInputView.this.context.INPUT_METHOD_SERVICE);
//				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//			}
//		});
		
       viewPager.setOnPageChangeListener(new OnPageChangeListener(){
           int preposiition=0;
           @Override
           public void onPageSelected( int position) {
           	 
           	page_ivs[preposiition].setImageResource(R.drawable.not_this_page_img);
           	page_ivs[position].setImageResource(R.drawable.is_this_page_img);
           	preposiition=position;
          }
          
           @Override
           public void onPageScrolled( int arg0, float arg1, int arg2) {}
           @Override
           public void onPageScrollStateChanged( int arg0) {}
       });
       initMap();
      
	}
 
	private void initMap() {
		ivIdToImgId.put(R.id.face0, R.drawable.f000);
		ivIdToImgId.put(R.id.face1, R.drawable.f001);
		ivIdToImgId.put(R.id.face2, R.drawable.f002);
		ivIdToImgId.put(R.id.face3, R.drawable.f003);
		ivIdToImgId.put(R.id.face4, R.drawable.f004);
		ivIdToImgId.put(R.id.face5, R.drawable.f005);
		ivIdToImgId.put(R.id.face6, R.drawable.f006);
		ivIdToImgId.put(R.id.face7, R.drawable.f007);
		ivIdToImgId.put(R.id.face8, R.drawable.f008);
		ivIdToImgId.put(R.id.face9, R.drawable.f009);
		ivIdToImgId.put(R.id.face10, R.drawable.f010);
		ivIdToImgId.put(R.id.face11, R.drawable.f011);
		ivIdToImgId.put(R.id.face12, R.drawable.f012);
		ivIdToImgId.put(R.id.face13, R.drawable.f013);
		ivIdToImgId.put(R.id.face14, R.drawable.f014);
		ivIdToImgId.put(R.id.face15, R.drawable.f015);
		ivIdToImgId.put(R.id.face16, R.drawable.f016);
		ivIdToImgId.put(R.id.face17, R.drawable.f017);
		ivIdToImgId.put(R.id.face18, R.drawable.f018);
		ivIdToImgId.put(R.id.face19, R.drawable.f019);
		ivIdToImgId.put(R.id.face20, R.drawable.f020);
		ivIdToImgId.put(R.id.face21, R.drawable.f021);
		ivIdToImgId.put(R.id.face22, R.drawable.f022);
		ivIdToImgId.put(R.id.face23, R.drawable.f023);
		ivIdToImgId.put(R.id.face24, R.drawable.f024);
		ivIdToImgId.put(R.id.face25, R.drawable.f025);
		ivIdToImgId.put(R.id.face26, R.drawable.f026);
		ivIdToImgId.put(R.id.face27, R.drawable.f027);
		ivIdToImgId.put(R.id.face28, R.drawable.f028);
		ivIdToImgId.put(R.id.face29, R.drawable.f029);
		ivIdToImgId.put(R.id.face30, R.drawable.f030);
		ivIdToImgId.put(R.id.face31, R.drawable.f031);
		ivIdToImgId.put(R.id.face32, R.drawable.f032);
		ivIdToImgId.put(R.id.face33, R.drawable.f033);
		ivIdToImgId.put(R.id.face34, R.drawable.f034);
		ivIdToImgId.put(R.id.face35, R.drawable.f035);
		ivIdToImgId.put(R.id.face36, R.drawable.f036);
		ivIdToImgId.put(R.id.face37, R.drawable.f037);
		ivIdToImgId.put(R.id.face38, R.drawable.f038);
		ivIdToImgId.put(R.id.face39, R.drawable.f039);
		ivIdToImgId.put(R.id.face40, R.drawable.f040);
		ivIdToImgId.put(R.id.face41, R.drawable.f041);
		ivIdToImgId.put(R.id.face42, R.drawable.f042);
		ivIdToImgId.put(R.id.face43, R.drawable.f043);
		ivIdToImgId.put(R.id.face44, R.drawable.f044);
		ivIdToImgId.put(R.id.face45, R.drawable.f045);
		ivIdToImgId.put(R.id.face46, R.drawable.f046);
		ivIdToImgId.put(R.id.face47, R.drawable.f047);
		ivIdToImgId.put(R.id.face48, R.drawable.f048);
		ivIdToImgId.put(R.id.face49, R.drawable.f049);
		ivIdToImgId.put(R.id.face50, R.drawable.f050);
		ivIdToImgId.put(R.id.face51, R.drawable.f051);
		ivIdToImgId.put(R.id.face52, R.drawable.f052);
		ivIdToImgId.put(R.id.face53, R.drawable.f053);
		ivIdToImgId.put(R.id.face54, R.drawable.f054);
		ivIdToImgId.put(R.id.face55, R.drawable.f055);
		ivIdToImgId.put(R.id.face56, R.drawable.f056);
		ivIdToImgId.put(R.id.face57, R.drawable.f057);
		ivIdToImgId.put(R.id.face58, R.drawable.f058);
		ivIdToImgId.put(R.id.face59, R.drawable.f059);
		ivIdToImgId.put(R.id.face60, R.drawable.f060);
		ivIdToImgId.put(R.id.face61, R.drawable.f061);
		ivIdToImgId.put(R.id.face62, R.drawable.f062);
		ivIdToImgId.put(R.id.face63, R.drawable.f063);
		ivIdToImgId.put(R.id.face64, R.drawable.f064);
		ivIdToImgId.put(R.id.face65, R.drawable.f065);
		ivIdToImgId.put(R.id.face66, R.drawable.f066);
		ivIdToImgId.put(R.id.face67, R.drawable.f067);
		ivIdToImgId.put(R.id.face68, R.drawable.f068);
		ivIdToImgId.put(R.id.face69, R.drawable.f069);
		ivIdToImgId.put(R.id.face70, R.drawable.f070);
		ivIdToImgId.put(R.id.face71, R.drawable.f071);
		ivIdToImgId.put(R.id.face72, R.drawable.f072);
		ivIdToImgId.put(R.id.face73, R.drawable.f073);
		ivIdToImgId.put(R.id.face74, R.drawable.f074);
		ivIdToImgId.put(R.id.face75, R.drawable.f075);
		ivIdToImgId.put(R.id.face76, R.drawable.f076);
		ivIdToImgId.put(R.id.face77, R.drawable.f077);
		ivIdToImgId.put(R.id.face78, R.drawable.f078);
		ivIdToImgId.put(R.id.face79, R.drawable.f079);
		ivIdToImgId.put(R.id.face80, R.drawable.f080);
		ivIdToImgId.put(R.id.face81, R.drawable.f081);
		ivIdToImgId.put(R.id.face82, R.drawable.f082);
		ivIdToImgId.put(R.id.face83, R.drawable.f083);
		ivIdToImgId.put(R.id.face84, R.drawable.f084);
		ivIdToImgId.put(R.id.face85, R.drawable.f085);
		ivIdToImgId.put(R.id.face86, R.drawable.f086);
		ivIdToImgId.put(R.id.face87, R.drawable.f087);
		ivIdToImgId.put(R.id.face88, R.drawable.f088);
		ivIdToImgId.put(R.id.face89, R.drawable.f089);
		ivIdToImgId.put(R.id.face90, R.drawable.f090);
		ivIdToImgId.put(R.id.face91, R.drawable.f091);
		 

	}

	private void initFaceIv(View v) {
		ViewGroup vg=(ViewGroup)v;
		int count=vg.getChildCount();
		for(int i=0;i<count;i++){
			LinearLayout layout=(LinearLayout) vg.getChildAt(i);
			int faceCount=layout.getChildCount();
			for(int j=0;j<faceCount;j++){
				layout.getChildAt(j).setOnClickListener(this);
			}
		}		
	}

	public void setOnClickListener(OnFaceClickListener l) {
		clickListener=l;
	}
	
	public interface OnFaceClickListener{
		public void selectedFace(Face face);
	}
	
	public class Face{
		public final String faceName;
		public final int faceId;
		public Face(String faceName, int faceId) {
			super();
			this.faceName = faceName;
			this.faceId = faceId;
		}
		
	}
	

	@Override
	public void onClick(View v) {
		
		if(clickListener!=null){
			ImageView iv=(ImageView) v;
			Integer i=(Integer) ivIdToImgId.get(v.getId());
			if(i==null)
				i=R.drawable.ic_face_delete_normal;
			clickListener.selectedFace(new Face("", i));
		}
	}
	
	
	
	
	
	
	
	/////////////////////
	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager)container).removeView(list.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager)container).addView(list.get(position));
			return list.get(position);
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		
	}




}

