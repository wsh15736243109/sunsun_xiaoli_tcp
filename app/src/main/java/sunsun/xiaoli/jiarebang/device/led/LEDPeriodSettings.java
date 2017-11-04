package sunsun.xiaoli.jiarebang.device.led;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.custom.XAlertDialog;
import com.itboye.pondteam.custom.swipexpandlistview.expandablelistview.SwipeMenuExpandableCreator;
import com.itboye.pondteam.custom.swipexpandlistview.expandablelistview.SwipeMenuExpandableListView;
import com.itboye.pondteam.custom.swipexpandlistview.swipemenulistview.SwipeMenu;
import com.itboye.pondteam.custom.swipexpandlistview.swipemenulistview.SwipeMenuItem;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.SwipLedAddShiDuanadapter;
import sunsun.xiaoli.jiarebang.app.App;

import static com.itboye.pondteam.utils.ScreenUtil.getDimension;
import static com.itboye.pondteam.volley.TimesUtils.localToUTC;

public class LEDPeriodSettings extends BaseActivity implements SwipLedAddShiDuanadapter.ISwitchClickListenter, XAlertDialog.OnEditInputFinishedListener, Observer {

    SwipeMenuExpandableListView exListView;
    ImageView img_back;
    TextView txt_title;
    private XAlertDialog xAlertDialog;
    App mApp;
    private SwipLedAddShiDuanadapter addShiDuanadapter;
    public ArrayList<DeviceDetailModel.TimePeriod> arPer = new ArrayList<>();
    UserPresenter userPresenter;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledperiod_settings);
        mApp = (App) getApplication();
        mApp.ledPeriodSettingsUI = this;
        userPresenter = new UserPresenter(this);
        txt_title.setText(getString(R.string.periodoflight));
        initSwipExpandListView();
        if (mApp.ledDetailActivity.detailModelTcp!=null) {
            if (mApp.ledDetailActivity.detailModelTcp.getPer()!=null) {
                setData(mApp.ledDetailActivity.detailModelTcp.getPer());
            }
        }
    }

    private void initSwipExpandListView() {
        SwipeMenuExpandableCreator creator = new SwipeMenuExpandableCreator() {
            @Override
            public void createGroup(SwipeMenu menu) {
                SwipeMenuItem item2 = new SwipeMenuItem(LEDPeriodSettings.this);
                item2.setBackground(new ColorDrawable(Color.rgb(0xff, 0x6e, 0xa5)));
                item2.setWidth(getDimension(LEDPeriodSettings.this, 90));
                item2.setTitle(getString(R.string.delete));
                item2.setTitleSize(18);
                item2.setTitleColor(Color.parseColor("#ffffff"));
                // item2.setIcon(R.drawable.ic_action_discard);
                menu.addMenuItem(item2);
            }

            @Override
            public void createChild(SwipeMenu menu) {
                // Create different menus depending on the view type
            }
        };
        exListView.setMenuCreator(creator);
        exListView.setOnMenuItemClickListener(new SwipeMenuExpandableListView.OnMenuItemClickListenerForExpandable() {
            @Override
            public boolean onMenuItemClick(int groupPosition, int childPosition, SwipeMenu menu, int index) {
                arPer.remove(groupPosition);
                putNewData(arPer);
                return true;
            }
        });
        exListView.setSwipEnable(false);
        addShiDuanadapter = new SwipLedAddShiDuanadapter(this, arPer);
        addShiDuanadapter.setSwitchClick(this);
        exListView.setAdapter(addShiDuanadapter);

        View view = View.inflate(this, com.itboye.pondteam.R.layout.item_weishi_father, null);
        ImageView img_add_weishi = (ImageView) view.findViewById(com.itboye.pondteam.R.id.img_add_weishi);
        img_add_weishi.setVisibility(View.VISIBLE);
        img_add_weishi.setOnClickListener(this);
        TextView txt_weishi_name = (TextView) view.findViewById(com.itboye.pondteam.R.id.txt_weishi_name);
        txt_weishi_name.setVisibility(View.GONE);
        TextView txt_weishi = (TextView) view.findViewById(com.itboye.pondteam.R.id.txt_weishi);
        txt_weishi.setText(getString(com.itboye.pondteam.R.string.addnew_weishi));
        exListView.addFooterView(view);
        exListView.setAdapter(addShiDuanadapter);
    }

    private void putNewData(ArrayList<DeviceDetailModel.TimePeriod> arPer) {
        String str = new Gson().toJson(arPer);
        userPresenter.deviceSet_led(mApp.ledDetailActivity.detailModel.getDid(), -1, -1, -1, str,-1, -1, -1,-1,-1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_add_weishi:
                if (arPer.size()>=5) {
                    MAlert.alert(getString(R.string.most_5));
                    return;
                }
                DeviceDetailModel.TimePeriod timePeriod = new DeviceDetailModel.TimePeriod();
                timePeriod.setH0(Integer.parseInt(localToUTC("6","HH","HH")));
                timePeriod.setM0(30);
                timePeriod.setH1(Integer.parseInt(localToUTC("6","HH","HH")));
                timePeriod.setM1(0);
                timePeriod.setEn(1);
                timePeriod.setW(50);
                timePeriod.setB(50);
                timePeriod.setG(50);
                timePeriod.setR(50);
                arPer.add(timePeriod);
                putNewData(arPer);
//                xAlertDialog = new XAlertDialog(this, this);
//                xAlertDialog.show();
                break;
            case R.id.re_tiaose:
                position= (int) v.getTag();
                startActivity(new Intent(this, TiaoGuangActivity.class).putExtra("position",position));
                break;
            case R.id.re_weishi_closetime:
                position= (int) v.getTag();
                setTimePicker((TextView) v.findViewById(R.id.txt_close_time),false);
                break;
            case R.id.re_weishi_opentime:
                position= (int) v.getTag();
                setTimePicker((TextView) v.findViewById(R.id.txt_open_time),true);
                break;
        }
    }

    public void setTimePicker(final TextView text,final boolean isKaiQi) {
        String[] times = text.getText().toString().split(":");
        com.itboye.pondteam.popwindow.CustomTimePickerDialog mPicker = new com.itboye.pondteam.popwindow.CustomTimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (isKaiQi) {
                    arPer.get(position).setH0(Integer.parseInt(localToUTC(hourOfDay+"","HH","HH")));
                    arPer.get(position).setM0(minute);
                }else{
                    arPer.get(position).setH1(Integer.parseInt(localToUTC(hourOfDay+"","HH","HH")));
                    arPer.get(position).setM1(minute);
                }
                putNewData(arPer);
            }
        }, Integer.parseInt(times[0]), Integer.parseInt(times[1]), true, isKaiQi ? getString(com.itboye.pondteam.R.string.chazuo_open_time) : getString(com.itboye.pondteam.R.string.chazuo_close_time));
        mPicker.setCanceledOnTouchOutside(false);
        mPicker.setAllPeriodData(arPer,isKaiQi,position);
        mPicker.show();
    }
    public void setData(String data) {
            Type type1 = new TypeToken<ArrayList<DeviceDetailModel.TimePeriod>>() {
            }.getType();
            ArrayList<DeviceDetailModel.TimePeriod> arTemp= new Gson().fromJson(data, type1);
            if (arTemp!=null) {
                arPer.clear();
                arPer.addAll(arTemp);
                if (addShiDuanadapter==null) {
                    addShiDuanadapter = new SwipLedAddShiDuanadapter(this, arPer);
                    addShiDuanadapter.setSwitchClick(this);
                    exListView.setAdapter(addShiDuanadapter);
                }else{
                    addShiDuanadapter.notifyDataSetChanged();
                }
                for (int i = 0; i < addShiDuanadapter.getGroupCount(); i++) {
                    exListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
                }
            }
    }

    @Override
    public void switchClick(int position) {
        arPer.get(position).setEn(arPer.get(position).getEn() == 0 ? 1 : 0);
        putNewData(arPer);
    }

    @Override
    public void editInputFinished(String password) {

    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == UserPresenter.deviceSet_led_success) {
                MAlert.alert(entity.getData());
                mApp.ledDetailActivity.beginRequest();
            } else if (entity.getEventType() == UserPresenter.deviceSet_led_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
