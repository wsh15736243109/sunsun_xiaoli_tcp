package sunsun.xiaoli.jiarebang.utils.loadingutil;


import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.itboye.pondteam.custom.wheelview.view.WheelPicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.XuLieNoModel;

public class ChooseXiliePopupWindow extends PopupWindow {
    private final List<String> list;
    private  WheelPicker myNumberPickerView1;
    private  WheelPicker myNumberPickerView2;
    private View cameraMenuView;
    Activity context;
    TextView txt_left,txt_right;
    private ArrayList<XuLieNoModel> xuLieNoModelArrayLit;
    private List<XuLieNoModel.ChildBean> listChild;
    private List<String> listChildString=new ArrayList<>();
    private List<String> listParentString=new ArrayList<>();
    @SuppressWarnings("deprecation")
    public ChooseXiliePopupWindow(Activity context,
                                  OnClickListener itemOnclick) {
        super(context);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cameraMenuView = layoutInflater.inflate(R.layout.alertdialog_xilie,
                null);
        this.context = context;
        myNumberPickerView1 = (WheelPicker) cameraMenuView.findViewById(R.id.number_picker1);
        myNumberPickerView2 = (WheelPicker) cameraMenuView.findViewById(R.id.number_picker2);
        TextView txt_left= (TextView) cameraMenuView.findViewById(R.id.txt_left);
        TextView txt_right= (TextView) cameraMenuView.findViewById(R.id.txt_right);
        list = Arrays.asList(context.getResources().getStringArray(R.array.timeArray));
        txt_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ////////////数据填充1
        txt_right.setOnClickListener(itemOnclick);
//        myNumberPickerView.setData(list);
//        myNumberPickerView.setSelectedItemPosition(0);
//
//        ////////////数据填充2
//        myNumberPickerView2.setData(listChildString);
//        myNumberPickerView2.setSelectedItemPosition(0);
        Rect rect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = context.getWindow().getDecorView().getHeight();
        this.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0,
                winHeight + rect.bottom);
        Log.d("titltetete", winHeight + "");
        System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>" + winHeight);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.setContentView(cameraMenuView);
        this.setWidth(LayoutParams.FILL_PARENT);
        this.setHeight(LayoutParams.FILL_PARENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);
        ColorDrawable dw = new ColorDrawable(0xb000000);
        this.setBackgroundDrawable(dw);
        cameraMenuView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return true;
            }
        });
    }

    public void setParent(ArrayList<XuLieNoModel> xuLieNoModelArrayList) {
        this.xuLieNoModelArrayLit=xuLieNoModelArrayList;
        if (xuLieNoModelArrayLit.size()>0) {
            for (XuLieNoModel xuLieNoModel : this.xuLieNoModelArrayLit) {
                listParentString.add(xuLieNoModel.getName()) ;
            }
            myNumberPickerView1.setData(listParentString);
            setChlid(0);
        }
    }

    public void setChlid(int parentPosition){
        this.listChild=xuLieNoModelArrayLit.get(parentPosition).getChild();
        for (XuLieNoModel.ChildBean childBean : this.listChild) {
            listChildString.add(childBean.getModel());
        }
        myNumberPickerView2.setData(listChildString);
    }

    public String  getSelectValue(){
        return xuLieNoModelArrayLit.get(myNumberPickerView1.getCurrentItemPosition()).getChild().get(myNumberPickerView2.getCurrentItemPosition()).getModel();
    }

}
