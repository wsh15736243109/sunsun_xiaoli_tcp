package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.XuLieNoModel;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.shopcart.MakeSureOrderActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.ServiceBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.BuyType;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.utils.loadingutil.ChooseXiliePopupWindow;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class AppointmentInstallStepOneActivity extends LingShouBaseActivity implements Observer {

    TranslucentActionBar actionBar;
    ImageView iv_actionbar_left;
    Button btn_next;
    @IsNeedClick
    TextView ed_xuliehao, ed_xilie;
    LingShouPresenter lingShouPresenter;
    ProgressDialog progressDialog;
    private String xuliehao;
    RelativeLayout re_xilie, re_xuliehao;
    private List<String> list;
    ChooseXiliePopupWindow popupWindow;
    private ArrayList<XuLieNoModel> xuLieNoModelArrayList;
    private ArrayList<ServiceBean> serviceBeanArrayList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_appointment_install;
    }

    @Override
    protected void initData() {
        initTitlebarStyle1(this, actionBar, "预约安装", R.mipmap.ic_left_light, "", 0, "");
        lingShouPresenter = new LingShouPresenter(this);
        lingShouPresenter.queryProNo(getSp(Const.UID), getSp(Const.S_ID));//查询产品型号

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.btn_next:
                xuliehao = ed_xuliehao.getText().toString();
                if (xuliehao.equals("")) {
                    MAlert.alert("请输入序列号");
                    return;
                }
                progressDialog.setMessage("正在验证序列号可用性");
                progressDialog.show();
                lingShouPresenter.getServiceSku(5);
//                lingShouPresenter.checkProductCode(getSp(Const.UID), xuliehao, getSp(Const.S_ID));
                break;
            case R.id.re_xilie:
                //选择序列
                if (xuLieNoModelArrayList == null) {
                    MAlert.alert("还未获取到序列号");
                    return;
                }
                if (xuLieNoModelArrayList.size() <= 0) {
                    return;
                }
                popupWindow.showAtLocation(v, Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
//                View view = LayoutInflater.from(this).inflate(R.layout.alertdialog_xilie, null);
//                WheelPicker myNumberPickerView = (WheelPicker) view.findViewById(R.id.number_picker1);
//                list = Arrays.asList(getResources().getStringArray(R.array.timeArray));
//                myNumberPickerView.setParent(list);
//                myNumberPickerView.setSelectedItemPosition(0);
//                PopupWindow popupWindow=new PopupWindow(getWindowManager().getDefaultDisplay().getWidth(), RelativeLayout.LayoutParams.WRAP_CONTENT);
//                popupWindow.setContentView(view);
//                popupWindow.showAtLocation(v, Gravity.BOTTOM
//                        | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.re_xuliehao:
                //选择序列号
                break;
            case R.id.txt_right:
                ed_xilie.setText(popupWindow.getSelectValue());
                popupWindow.dismiss();
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                if (progressDialog.isShowing()) {
                    progressDialog.setMessage(entity.getMsg());
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }, 1000);
                return;
            }
            if (entity.getEventType() == LingShouPresenter.checkProductCode_success) {
                progressDialog.setMessage(entity.getData() + "");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        progressDialog.dismiss();
                        progressDialog.setMessage("正在获取商品信息");
                        lingShouPresenter.getServiceSku(5);
//                        Intent intent = new Intent(AppointmentInstallStepOneActivity.this, MakeSureOrderActivity.class);
//                        intent.putExtra("title", "预约安装");
//                        intent.putExtra("selectValue", xuliehao);
//                        intent.putExtra("type", BuyType.Buy_YuYueAnZhuang);
//                        startActivity(intent);
                    }
                }, 1000);

            } else if (entity.getEventType() == LingShouPresenter.checkProductCode_fail) {
                progressDialog.setMessage(entity.getData() + "");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 1000);
            } else if (entity.getEventType() == LingShouPresenter.getServiceSku_success) {
                serviceBeanArrayList= (ArrayList<ServiceBean>) entity.getData();
                progressDialog.setMessage("获取成功");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Intent intent = new Intent(AppointmentInstallStepOneActivity.this, MakeSureOrderActivity.class);
                        intent.putExtra("title", "预约安装");
                        intent.putExtra("selectValue", serviceBeanArrayList.get(0));
                        intent.putExtra("type", BuyType.Buy_YuYueAnZhuang);
                        startActivity(intent);
                    }
                }, 1000);
            } else if (entity.getEventType() == LingShouPresenter.getServiceSku_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.queryProNo_success) {
                xuLieNoModelArrayList = (ArrayList<XuLieNoModel>) entity.getData();
                popupWindow = new ChooseXiliePopupWindow(
                        this, this);
                popupWindow.setParent(xuLieNoModelArrayList);
            } else if (entity.getEventType() == LingShouPresenter.queryProNo_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
