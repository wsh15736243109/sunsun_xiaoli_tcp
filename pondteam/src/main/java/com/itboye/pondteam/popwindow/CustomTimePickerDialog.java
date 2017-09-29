package com.itboye.pondteam.popwindow;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.itboye.pondteam.R;
import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.utils.loadingutil.MAlert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itboye on 2017/2/28.
 * 时间选择器
 */

public class CustomTimePickerDialog extends TimePickerDialog {
    private final static int TIME_PICKER_INTERVAL = 10;
    private TimePicker timePicker;
    private final OnTimeSetListener callback;
    private int currentMinute;
    private NumberPicker mMinuteSpinner;
    CharSequence title;
    private NumberPicker mHourSpinner;
    private ArrayList<DeviceDetailModel.TimePeriod> arPer;
    private int position;
    private boolean isOpen;

    public CustomTimePickerDialog(Context context, OnTimeSetListener callBack,
                                  int hourOfDay, int minute, boolean is24HourView, CharSequence titleText) {
        super(context, callBack, hourOfDay, minute, is24HourView);
        this.callback = callBack;
        this.setButton(BUTTON_NEGATIVE, MyApplication.getInstance().getString(R.string.ok), this);
        this.setButton(BUTTON_POSITIVE, MyApplication.getInstance().getString(R.string.cancel), this);
        this.title=titleText;
        this.setTitle(titleText);
    }

    public CustomTimePickerDialog(Context context, int theme, OnTimeSetListener callBack,
                                  int hourOfDay, int minute, boolean is24HourView, CharSequence titleText) {
        super(context, theme, callBack, hourOfDay, minute/10, is24HourView);
        this.callback = callBack;
        currentMinute = minute;
        this.title=titleText;
        this.setButton(BUTTON_NEGATIVE, MyApplication.getInstance().getString(R.string.ok), new Message());
        this.setButton(BUTTON_POSITIVE, MyApplication.getInstance().getString(R.string.cancel), this);
        this.setTitle(titleText);
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == BUTTON_NEGATIVE) {
            if (callback != null && timePicker != null) {
                timePicker.clearFocus();
                callback.onTimeSet(timePicker, timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());
            }
        }
    }

    @Override
    protected void onStop() {
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            Field timePickerField = classForid.getField("timePicker");
            this.timePicker = (TimePicker) findViewById(timePickerField.getInt(null));
            Field field = classForid.getField("minute");
            Field fieldHour = classForid.getField("hour");
            mHourSpinner = (NumberPicker) timePicker.findViewById(fieldHour.getInt(null));
            mMinuteSpinner = (NumberPicker) timePicker.findViewById(field.getInt(null));
            mMinuteSpinner.setMinValue(0);
            mMinuteSpinner.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
            List<String> displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            mMinuteSpinner.setDisplayedValues(displayedValues.toArray(new String[0]));
            mMinuteSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    setTitle(title);
                }
            });
            mHourSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    setTitle(title);
                }
            });
            timePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止NumberPicker输入
        } catch (Exception e) {
            e.printStackTrace();
        }

        getButton(BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasSet=false;//已经设置了该时间段
                int currentHour = timePicker.getCurrentHour();
                int currentMinute = timePicker.getCurrentMinute();
                //先判断开始时间小于关闭时间（先忽略）
                //判断当前设置的时间段不能同于当前所有时间段的某一个时间段
                if (arPer!=null) {
                    for (int i = 0; i < arPer.size(); i++) {
                        DeviceDetailModel.TimePeriod time=arPer.get(i);
                        if (isOpen) {
                            if (time.getH0()==currentHour&&time.getM0()==currentMinute*10&&arPer.get(position).getH1()==time.getH1()&&arPer.get(position).getM1()==time.getM1()) {
                                hasSet=true;
                                break;
                            }else{

                                hasSet=false;
                            }
                        }else{
                            if (time.getH1()==currentHour&&time.getM1()==currentMinute*10&&arPer.get(position).getH0()==time.getH0()&&arPer.get(position).getM0()==time.getM0()) {
                                hasSet=true;
                                break;
                            }else{
                                hasSet=false;
                            }
                        }

                    }
                    if (hasSet) {
                        MAlert.alert(MyApplication.getInstance().getString(R.string.already_timeset));
                        return;
                    }
                }

                callback.onTimeSet(timePicker, currentHour,
                        currentMinute * 10);
                dismiss();
            }
        });
    }

    public void setAllPeriodData(ArrayList<DeviceDetailModel.TimePeriod> arPer,boolean isOpen,int position) {
        this.arPer=arPer;
        this.isOpen=isOpen;
        this.position=position;
    }
}
