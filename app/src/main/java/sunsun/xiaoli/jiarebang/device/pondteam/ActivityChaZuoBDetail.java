package sunsun.xiaoli.jiarebang.device.pondteam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itboye.pondteam.R;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.WeiShiModel;
import com.itboye.pondteam.custom.XAlertDialog;
import com.itboye.pondteam.custom.swipexpandlistview.expandablelistview.SwipeMenuExpandableCreator;
import com.itboye.pondteam.custom.swipexpandlistview.expandablelistview.SwipeMenuExpandableListView;
import com.itboye.pondteam.custom.swipexpandlistview.swipemenulistview.SwipeMenu;
import com.itboye.pondteam.custom.swipexpandlistview.swipemenulistview.SwipeMenuItem;
import com.itboye.pondteam.interfaces.IRecyclerviewclicklistener;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;
import com.itboye.pondteam.volley.TimesUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.adapter.SwipWeiShiadapter;
import sunsun.xiaoli.jiarebang.app.App;

import static com.itboye.pondteam.utils.ScreenUtil.getDimension;
import static com.itboye.pondteam.utils.StringFormatUtils.getFormatTime;


public class ActivityChaZuoBDetail extends BaseActivity implements IRecyclerviewclicklistener, XAlertDialog.OnEditInputFinishedListener, Observer {

    //    RecyclerView recyclerview;
    TextView txt_title;
    ImageView img_back;
    ImageView img_add_weishi;
    private XAlertDialog xAlertDialog;
    ImageView chazuoB_weishi;
    private SwipeMenuExpandableListView exListView;
    App myApplication;
    private UserPresenter userPresenter;
    private ArrayList<WeiShiModel> arrayListInner;
    private SwipWeiShiadapter swipWeiShiadapter;
    private int groupPosition;
    boolean isUpdateUI = true;
    String chazuo_type = "A";
    private NumberPicker mHourSpinner, mMinuteSpinner, mSecondSpinner;
    private Calendar mDate;
    RelativeLayout re_mode_selection;
    TextView chazuoA_mode;
    private int tempType;
    ArrayList<String> arB = new ArrayList<>(), arA = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cha_zuo_bdetail);
        myApplication = (App) getApplication();
        myApplication.chazuoBDetail = this;
        arrayListInner = new ArrayList<>();
        chazuo_type = getIntent().getStringExtra("chazuo_type");
        swipWeiShiadapter = new SwipWeiShiadapter(this, arrayListInner);
        exListView.setAdapter(swipWeiShiadapter);

        userPresenter = new UserPresenter(this);
        txt_title.setText(getIntent().getStringExtra("title"));
        SwipeMenuExpandableCreator creator = new SwipeMenuExpandableCreator() {
            @Override
            public void createGroup(SwipeMenu menu) {
                SwipeMenuItem item2 = new SwipeMenuItem(ActivityChaZuoBDetail.this);
                item2.setBackground(new ColorDrawable(Color.rgb(0xff, 0x6e, 0xa5)));
                item2.setWidth(getDimension(ActivityChaZuoBDetail.this, 90));
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
                if (!myApplication.pondDeviceDetailUI.isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return false;
                }
                try {
                    if (chazuo_type.equals("A")) {
                        arA.remove(groupPosition);
                    } else {
                        arB.remove(groupPosition);
                    }
                } catch (Exception e) {

                }
                arrayListInner.remove(groupPosition);
                String str1 = new Gson().toJson(arA);
                String str2 = new Gson().toJson(arB);
                String str3 = new Gson().toJson(arrayListInner);
                if (chazuo_type.equals("A")) {
                    userPresenter.deviceSet(myApplication.pondDeviceDetailUI.did, str1, null, "", -1, "", "", "", "", "", "", "", "", "", -1, -1, "", "", "", str3, -1, -1);
                } else {
                    userPresenter.deviceSet(myApplication.pondDeviceDetailUI.did, null, str2, "", -1, "", "", "", "", "", "", "", "", "", -1, -1, "", "", str3, "", -1, -1);
                }
                return true;
            }
        });
        exListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
        ////////////////尾部///////////////////////////
        View view = View.inflate(this, R.layout.item_weishi_father, null);
        ImageView img_add_weishi = (ImageView) view.findViewById(R.id.img_add_weishi);
        img_add_weishi.setVisibility(View.VISIBLE);
        img_add_weishi.setOnClickListener(this);
        TextView txt_weishi_name = (TextView) view.findViewById(R.id.txt_weishi_name);
        txt_weishi_name.setVisibility(View.GONE);
        TextView txt_weishi = (TextView) view.findViewById(R.id.txt_weishi);
        txt_weishi.setText(getString(R.string.addnew_weishi));
        exListView.addFooterView(view);


        //////////////////头部//////////////////
        View viewHeader = View.inflate(this, R.layout.header_chazuo, null);
        re_mode_selection = (RelativeLayout) viewHeader.findViewById(R.id.re_mode_selection);
        re_mode_selection.setOnClickListener(this);
        chazuoA_mode = (TextView) viewHeader.findViewById(R.id.chazuoA_mode);
        exListView.addHeaderView(viewHeader);
        setChaZuoData();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.img_back) {
            finish();

        } else if (i == R.id.img_add_weishi) {
            if (!myApplication.pondDeviceDetailUI.isConnect) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            if (chazuo_type.equals("A")) {
                if ((Integer.parseInt(myApplication.pondDeviceDetailUI.detailModelTcp.getOut_state_a()) & (int) Math.pow(2, 1)) == (int) Math.pow(2, 1)) {

                } else {
                    MAlert.alert(getString(R.string.turn_to_auto_mode));
                    return;
                }
            } else {
                if ((Integer.parseInt(myApplication.pondDeviceDetailUI.detailModelTcp.getOut_state_b()) & (int) Math.pow(2, 1)) == (int) Math.pow(2, 1)) {

                } else {
                    MAlert.alert(getString(R.string.turn_to_auto_mode));
                    return;
                }
            }
            xAlertDialog = new XAlertDialog(this, this);
//                xAlertDialog.create();
            xAlertDialog.show();
            xAlertDialog.setType(0);//0为添加喂食

        } else if (i == R.id.re_weishi_closetime) {
            if (!myApplication.pondDeviceDetailUI.isConnect) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            if ((Integer.parseInt(myApplication.pondDeviceDetailUI.detailModelTcp.getOut_state_b()) & (int) Math.pow(2, 1)) == (int) Math.pow(2, 1)) {

            } else {
                MAlert.alert(getString(R.string.turn_to_auto_mode));
                return;
            }
            groupPosition = (int) v.getTag();
//            setTimePicker((TextView) v.findViewById(R.id.txt_close_time), false);
            showTimerPicker(false);
        } else if (i == R.id.re_weishi_opentime) {
            if (!myApplication.pondDeviceDetailUI.isConnect) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            if ((Integer.parseInt(myApplication.pondDeviceDetailUI.detailModelTcp.getOut_state_b()) & (int) Math.pow(2, 1)) == (int) Math.pow(2, 1)) {

            } else {
                MAlert.alert(getString(R.string.turn_to_auto_mode));
                return;
            }
            groupPosition = (int) v.getTag();
            showTimerPicker(true);
//            setTimePicker((TextView) v.findViewById(R.id.txt_open_time), true);
        } else if (i == R.id.re_weishi_zhouqi) {
            if (!myApplication.pondDeviceDetailUI.isConnect) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            if ((Integer.parseInt(myApplication.pondDeviceDetailUI.detailModelTcp.getOut_state_b()) & (int) Math.pow(2, 1)) == (int) Math.pow(2, 1)) {

            } else {
                MAlert.alert(getString(R.string.turn_to_auto_mode));
                return;
            }
            groupPosition = (int) v.getTag();
            Intent intent = new Intent(this, WenDuPeriodActivity.class);
            intent.putExtra("zhouqi", ((TextView) v.findViewById(R.id.txt_zhouqi_time)).getText().toString());
            startActivityForResult(intent, 101);
        } else if (i == R.id.txt_weishi_name) {
            if (!myApplication.pondDeviceDetailUI.isConnect) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            if ((Integer.parseInt(myApplication.pondDeviceDetailUI.detailModelTcp.getOut_state_b()) & (int) Math.pow(2, 1)) == (int) Math.pow(2, 1)) {

            } else {
                MAlert.alert(getString(R.string.turn_to_auto_mode));
                return;
            }
            groupPosition = (int) v.getTag();
            xAlertDialog = new XAlertDialog(this, this);
            xAlertDialog.show();
            xAlertDialog.setHint(getString(R.string.update_weishi_name));
            xAlertDialog.setType(1);//一为修改喂食名称
        } else if (i == R.id.re_mode_selection) {
            if (!myApplication.pondDeviceDetailUI.isConnect) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            showModeSelctionPopupWindow();
        }
    }

    private void showModeSelctionPopupWindow() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        View view = View.inflate(this, R.layout.item_mode_selection, null);
        final TextView txt_shoudong_open = (TextView) view.findViewById(R.id.txt_shoudong_open);
        txt_shoudong_open.setTag(false);
        final TextView txt_shoudong_close = (TextView) view.findViewById(R.id.txt_shoudong_close);
        txt_shoudong_close.setTag(false);
        final TextView txt_auto = (TextView) view.findViewById(R.id.txt_auto);
        txt_auto.setTag(false);
        switch (tempType) {
            case 1:
                txt_shoudong_open.setTextColor(getResources().getColor(R.color.main_green));
                txt_shoudong_open.setTag(true);
                break;
            case 2:
                txt_shoudong_close.setTextColor(getResources().getColor(R.color.main_green));
                txt_shoudong_close.setTag(true);
                break;
            case 3:
                txt_auto.setTextColor(getResources().getColor(R.color.main_green));
                txt_auto.setTag(true);
                break;
        }
        txt_shoudong_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectColor(v, txt_shoudong_close, txt_auto);
            }

        });
        txt_shoudong_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectColor(v, txt_shoudong_open, txt_auto);

            }
        });

        txt_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectColor(v, txt_shoudong_close, txt_shoudong_open);
            }
        });
        alert.setView(view);
        alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /**
                 * outStateA  outStateB
                 *     Bit0：插座通电状态
                 *   1：通电，0：断电
                 *     Bit1：定时模式开关
                 *   1：定时模式，0：手动模式
                 *     Bit2：插座A节水使能标志
                 *   1：节水使能，0：节水禁止
                 */
                if ((Boolean) txt_shoudong_open.getTag()) {
                    //手动模式开    01
                    if (chazuo_type.equals("A")) {
                        if (((Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_a()) & (int) Math.pow(2, 0)) == Math.pow(2, 0))
                                && (Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_a()) & (int) Math.pow(2, 1)) == 0) {
                            MAlert.alert(getString(R.string.mode_isshoudong_open));
                        } else {
                            int value = Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_a());
                            if ((Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_a()) & (int) Math.pow(2, 0)) == 0) {
                                value = Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_a()) ^ (int) Math.pow(2, 0);
                            }
                            if ((Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_a()) & (int) Math.pow(2, 1)) == (int) Math.pow(2, 1)) {
                                value = value ^ (int) Math.pow(2, 1);
                            }
                            userPresenter.deviceSet(myApplication.pondDeviceDetailUI.did, null, null, "", -1, "", "", "", "", "", "", "", "", "", value, -1, "", "", "", "", -1, -1);
                        }
                    } else {
                        if (((Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_b()) & (int) Math.pow(2, 0)) == Math.pow(2, 0))
                                && (Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_b()) & (int) Math.pow(2, 1)) == 0) {
                            MAlert.alert(getString(R.string.mode_isshoudong_open));
                        } else {
                            int value = Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_b());
                            if ((Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_b()) & (int) Math.pow(2, 1)) == 0) {
                                value = Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_b()) ^ (int) Math.pow(2, 0);
                            }
                            if ((Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_b()) & (int) Math.pow(2, 1)) == (int) Math.pow(2, 1)) {
                                value = value ^ (int) Math.pow(2, 1);
                            }
                            userPresenter.deviceSet(myApplication.pondDeviceDetailUI.did, null, null, "", -1, "", "", "", "", "", "", "", "", "", -1, value, "", "", "", "", -1, -1);
                        }
                    }
                } else if ((Boolean) txt_shoudong_close.getTag()) {
                    //手动模式关
                    if (chazuo_type.equals("A")) {
                        if (((Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_a()) & (int) Math.pow(2, 0)) == 0)
                                && (Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_a()) & (int) Math.pow(2, 1)) == 0) {
                            MAlert.alert(getString(R.string.mode_isshoudong_close));
                        } else {
                            int value = Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_a());
                            if ((Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_a()) & (int) Math.pow(2, 0)) == (int) Math.pow(2, 0)) {
                                value = Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_a()) ^ (int) Math.pow(2, 0);
                            }
                            if ((Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_a()) & (int) Math.pow(2, 1)) == (int) Math.pow(2, 1)) {
                                value = value ^ (int) Math.pow(2, 1);
                            }
                            userPresenter.deviceSet(myApplication.pondDeviceDetailUI.did, null, null, "", -1, "", "", "", "", "", "", "", "", "", value, -1, "", "", "", "", -1, -1);
                        }
                    } else {
                        if (((Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_b()) & (int) Math.pow(2, 0)) == 0)
                                && (Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_b()) & (int) Math.pow(2, 1)) == 0) {
                            MAlert.alert(getString(R.string.mode_isshoudong_close));
                        } else {
                            int value = Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_b());
                            if ((Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_b()) & (int) Math.pow(2, 0)) == (int) Math.pow(2, 0)) {
                                value = Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_b()) ^ (int) Math.pow(2, 0);
                            }
                            if ((Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_b()) & (int) Math.pow(2, 1)) == (int) Math.pow(2, 1)) {
                                value = value ^ (int) Math.pow(2, 1);
                            }
                            userPresenter.deviceSet(myApplication.pondDeviceDetailUI.did, null, null, "", -1, "", "", "", "", "", "", "", "", "", -1, value, "", "", "", "", -1, -1);
                        }
                    }
                } else if ((Boolean) txt_auto.getTag()) {
                    if (chazuo_type.equals("A")) {
                        if ((Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_a()) & (int) Math.pow(2, 1)) == (int) Math.pow(2, 1)) {
                            MAlert.alert(getString(R.string.mode_isauto));
                        } else {
                            int value = Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_a()) ^ (int) Math.pow(2, 1);
                            userPresenter.deviceSet(myApplication.pondDeviceDetailUI.did, null, null, "", -1, "", "", "", "", "", "", "", "", "", value, -1, "", "", "", "", -1, -1);
                        }
                    } else {
                        if ((Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_b()) & (int) Math.pow(2, 1)) == (int) Math.pow(2, 1)) {
                            MAlert.alert(getString(R.string.mode_isauto));
                        } else {
                            int value = Integer.parseInt(myApplication.pondDeviceDetailUI.deviceDetailModel.getOut_state_b()) ^ (int) Math.pow(2, 1);
                            userPresenter.deviceSet(myApplication.pondDeviceDetailUI.did, null, null, "", -1, "", "", "", "", "", "", "", "", "", -1, value, "", "", "", "", -1, -1);
                        }
                    }
                }
            }
        });
        alert.create();
        alert.show();
    }

    private void setSelectColor(View v, TextView t1, TextView t2) {
        TextView textView = (TextView) v;
        textView.setTag(true);
        textView.setTextColor(getResources().getColor(R.color.main_green));
        t1.setTextColor(getResources().getColor(R.color.black));
        t1.setTag(false);
        t2.setTextColor(getResources().getColor(R.color.black));
        t2.setTag(false);
    }

    private void showTimerPicker(final boolean type) {
        View viewPicker = View.inflate(this, R.layout.timer_picker, null);
        mDate = Calendar.getInstance();
        int mHour = mDate.get(Calendar.HOUR_OF_DAY);
        int mMinute = mDate.get(Calendar.MINUTE);
        int mSecond = mDate.get(Calendar.SECOND);
        if (type) {
            mHour = Integer.parseInt(getFormatTime(arrayListInner.get(groupPosition).getSt()).split(":")[0]);
            mMinute = Integer.parseInt(getFormatTime(arrayListInner.get(groupPosition).getSt()).split(":")[1]);
            mSecond = Integer.parseInt(getFormatTime(arrayListInner.get(groupPosition).getSt()).split(":")[2]);
        } else {
            mHour = Integer.parseInt(getFormatTime(arrayListInner.get(groupPosition).getEt()).split(":")[0]);
            mMinute = Integer.parseInt(getFormatTime(arrayListInner.get(groupPosition).getEt()).split(":")[1]);
            mSecond = Integer.parseInt(getFormatTime(arrayListInner.get(groupPosition).getEt()).split(":")[2]);

        }
        mHourSpinner = (NumberPicker) viewPicker.findViewById(R.id.timerHours);
        mHourSpinner.setMaxValue(23);
        mHourSpinner.setMinValue(0);
        mHourSpinner.setValue(mHour);
        mHourSpinner.setFormatter(formatter);
        mHourSpinner.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mHourSpinner.setOnValueChangedListener(mOnHourChangedListener);

        mMinuteSpinner = (NumberPicker) viewPicker.findViewById(R.id.timerMinutes);
        mMinuteSpinner.setMaxValue(59);
        mMinuteSpinner.setMinValue(0);
        mMinuteSpinner.setValue(mMinute);
        mMinuteSpinner.setFormatter(formatter);
        mMinuteSpinner.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mMinuteSpinner.setOnValueChangedListener(mOnMinuteChangedListener);

        mSecondSpinner = (NumberPicker) viewPicker.findViewById(R.id.timerSeconds);
        mSecondSpinner.setMaxValue(59);
        mSecondSpinner.setMinValue(0);
        mSecondSpinner.setValue(mSecond);
        mSecondSpinner.setFormatter(formatter);
        mSecondSpinner.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mSecondSpinner.setOnValueChangedListener(mOnSecondChangedListener);
        AlertDialog.Builder alert = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        alert.setTitle(type ? getString(R.string.chazuoA_opentime) : getString(R.string.chazuoA_closetime)).setView(viewPicker).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String hour = mHourSpinner.getValue() + "";
                hour = TimesUtils.localToUTC(hour + "", "HH", "HH");
                if (type) {
                    arrayListInner.get(groupPosition).setSt(hour + "" + mMinuteSpinner.getValue() + "" + mSecondSpinner.getValue());
                } else {
                    arrayListInner.get(groupPosition).setEt(hour + "" + mMinuteSpinner.getValue() + "" + mSecondSpinner.getValue());
                }
                String json = new Gson().toJson(arrayListInner);
                if (chazuo_type.equals("A")) {
                    userPresenter.deviceSet(myApplication.pondDeviceDetailUI.did, null, null, "", -1, "", "", "", "", "", "", "", "", "", -1, -1, "", "", "", json, -1, -1);
                }else {
                    userPresenter.deviceSet(myApplication.pondDeviceDetailUI.did, null, null, "", -1, "", "", "", "", "", "", "", "", "", -1, -1, "", "", json, "", -1, -1);
                }
            }
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    private NumberPicker.OnValueChangeListener mOnHourChangedListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        }
    };
    private NumberPicker.OnValueChangeListener mOnMinuteChangedListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        }
    };
    private NumberPicker.OnValueChangeListener mOnSecondChangedListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        }
    };
    //数字格式化，<10的数字前自动加0
    private NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
        @Override
        public String format(int value) {
            String Str = String.valueOf(value);
            if (value < 10) {
                Str = "0" + Str;
            }
            return Str;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 102) {
            int weekBinary = data.getIntExtra("weekBinary", 0);
            arrayListInner.get(groupPosition).setWeek(weekBinary);
            String json = new Gson().toJson(arrayListInner);
            userPresenter.deviceSet(myApplication.pondDeviceDetailUI.did, null, null, "", -1, "", "", "", "", "", "", "", "", "", -1, -1, "", "", json, "", -1, -1);
        }
    }

    @Override
    public void onItemClick(String str) {

    }

    @Override
    public void onLeftMenuClick(String str) {

    }

    Type typeNickName = new TypeToken<ArrayList<String>>() {
    }.getType();

    @Override
    public void onRightMenuClick(String str) {
        MAlert.alert(str);
    }

    @Override
    public void editInputFinished(String password) {
        if (!myApplication.pondDeviceDetailUI.isConnect) {
            MAlert.alert(getString(R.string.disconnect));
            return;
        }
        try {
            WeiShiModel weishi = new WeiShiModel();
            //////////nickName修改//////////////
            ArrayList<WeiShiModel> arrayTemp = new ArrayList<>();
            if (xAlertDialog.getType() == 1) {
                arrayListInner.get(groupPosition).setNick_name(password);
                if (chazuo_type.equals("A")) {
                    if (arrayListInner.size()==arA.size()) {
                        arA.set(groupPosition, password);
                    }else if(arrayListInner.size()>arA.size()){
                        for (int i = 0; i < arrayListInner.size() - arA.size(); i++) {
                            arA.add("");
                        }
                        arA.set(groupPosition, password);
                    }else if(arrayListInner.size()<arA.size()){

                    }
                } else {
                    if (arrayListInner.size()==arB.size()) {
                        arB.set(groupPosition, password);
                    }else if(arrayListInner.size()>arB.size()){
                        for (int i = 0; i < arrayListInner.size() - arB.size(); i++) {
                            arB.add("");
                        }
                        arB.set(groupPosition, password);
                    }else if(arrayListInner.size()<arB.size()){

                    }
                }
            } else {

                String hour = TimesUtils.localToUTC(8 + "", "HH", "HH");
                weishi.setNick_name(password);
                weishi.setSt(hour + "0000");
                weishi.setEt(hour + "3000");
                weishi.setWeek(4);
                if (arrayListInner == null) {
                    arrayListInner = new ArrayList<>();
                }
                arrayTemp.addAll(arrayListInner);
                arrayTemp.add(weishi);

                if (chazuo_type.equals("A")) {
                    if (arrayListInner.size()==arA.size()) {
                        arA.add(password);
                    }else{
                        try {
                            //昵称数组大于时段数组
                            arA.add(arrayListInner.size(),password);
                        }catch (Exception e){
                            //昵称数组小于时段数组
                            for (int i = 0; i < arrayListInner.size() - arA.size(); i++) {
                                if (i!=arrayListInner.size()-1) {
                                    arA.add("");
                                }else{
                                    arA.add(password);
                                }
                            }
                        }
                    }
                } else {
                    if (arrayListInner.size()==arB.size()) {
                        arB.add(password);
                    }else{
                        try {
                            arB.add(arrayListInner.size(),password);
                        }catch (Exception e){
                            for (int i = 0; i < arrayListInner.size() - arB.size(); i++) {
                                if (i!=arrayListInner.size()-1) {
                                    arB.add("");
                                }else{
                                    arB.add(password);
                                }
                            }
                        }

                    }
                }
            }
            String json = new Gson().toJson(arrayTemp);
            hasEx = false;
            if (chazuo_type.equals("A")) {
                userPresenter.deviceSet(myApplication.pondDeviceDetailUI.did, new Gson().toJson(arA), null, "", -1, "", "", "", "", "", "", "", "", "", -1, -1, "", "", "", json, -1, -1);
            } else {
                userPresenter.deviceSet(myApplication.pondDeviceDetailUI.did, null, new Gson().toJson(arB), "", -1, "", "", "", "", "", "", "", "", "", -1, -1, "", "", json, "", -1, -1);
            }
        } catch (Exception e) {

        }

        xAlertDialog.dismiss();
    }

    boolean hasEx = false;

    @Override
    protected void onDestroy() {
        myApplication.chazuoBDetail=null;
        super.onDestroy();
    }

    public void setChaZuoData() {
        if (chazuo_type.equals("A")) {
            //插座A数据
            ArrayList<WeiShiModel> arTemp = new ArrayList<>();
            String period = myApplication.pondDeviceDetailUI.detailModelTcp.getOa_per();
            Type type = new TypeToken<ArrayList<WeiShiModel>>() {
            }.getType();
            arTemp = new Gson().fromJson(period, type);
            arA = new Gson().fromJson(myApplication.pondDeviceDetailUI.deviceDetailModel.getOa_per_name(), typeNickName);
            if (arA == null) {
                arA = new ArrayList<>();
            }
            if (arTemp != null) {
                for (int i = 0; i < arTemp.size(); i++) {
                    WeiShiModel weiShiModel = arTemp.get(i);
                    try {
                        weiShiModel.setNick_name(arA.get(i));
                    } catch (Exception e) {

                    }
                }
            }

            if (arrayListInner != null) {
                arrayListInner.clear();
                if (arTemp != null) {
                    arrayListInner.addAll(arTemp);
                    swipWeiShiadapter.notifyDataSetChanged();
                }
            }
        } else {
            //插座B数据
            ArrayList<WeiShiModel> arTemp = new ArrayList<>();
            //设置喂食时间
            String period = myApplication.pondDeviceDetailUI.detailModelTcp.getOb_per();
            Type type = new TypeToken<ArrayList<WeiShiModel>>() {
            }.getType();
            arTemp = new Gson().fromJson(period, type);
            arB = new Gson().fromJson(myApplication.pondDeviceDetailUI.deviceDetailModel.getOb_per_name(), typeNickName);
            if (arB == null) {
                arB = new ArrayList<>();
            }
            if (arTemp != null) {
                for (int i = 0; i < arTemp.size(); i++) {
                    WeiShiModel weiShiModel = arTemp.get(i);
                    try {
                        weiShiModel.setNick_name(arB.get(i));
                    } catch (Exception e) {

                    }
                }
            }
            if (arrayListInner != null) {
                arrayListInner.clear();
                if (arTemp != null) {
                    arrayListInner.addAll(arTemp);
                    swipWeiShiadapter.notifyDataSetChanged();
                }
            }
        }

//        if (hasEx == false) {
            if (swipWeiShiadapter != null) {
                for (int i = 0; i < swipWeiShiadapter.getGroupCount(); i++) {
                    exListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
//                    hasEx = true;
                }
            }

//        }
        setMode();
    }

    private void setMode() {
        int chaZuoBModeValue = 0;
        tempType = 0;
        if (chazuo_type.equals("A")) {
            chaZuoBModeValue = Integer.parseInt(myApplication.pondDeviceDetailUI.detailModelTcp.getOut_state_a());
        } else {
            chaZuoBModeValue = Integer.parseInt(myApplication.pondDeviceDetailUI.detailModelTcp.getOut_state_b());
        }

        if ((chaZuoBModeValue & (int) Math.pow(2, 1)) == (int) Math.pow(2, 1)) {
            //自动模式
            chazuoA_mode.setText(getString(R.string.mode_zidong));
            tempType = 3;
        } else {
            //手动模式
            if ((chaZuoBModeValue & (int) Math.pow(2, 0)) == (int) Math.pow(2, 0)) {
                //通电即为开
                chazuoA_mode.setText(getString(R.string.mode_shoudong_open));
                tempType = 1;
            } else {
                //断电即为关
                chazuoA_mode.setText(getString(R.string.mode_shoudong_close));
                tempType = 2;
            }
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == UserPresenter.deviceSet_success) {
                MAlert.alert(entity.getData());
                myApplication.pondDeviceDetailUI.threadStart();
            } else if (entity.getEventType() == UserPresenter.deviceSet_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
