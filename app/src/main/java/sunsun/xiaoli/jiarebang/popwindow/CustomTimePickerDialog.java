package sunsun.xiaoli.jiarebang.popwindow;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.itboye.pondteam.utils.loadingutil.MAlert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;

/**
 * Created by itboye on 2017/2/28.
 * 时间选择器
 */

public class CustomTimePickerDialog extends TimePickerDialog {
    private final static int TIME_PICKER_INTERVAL = 10;
    private TimePicker timePicker;
    private final OnTimeSetListener callback;
    private ArrayList<com.itboye.pondteam.bean.DeviceDetailModel.TimePeriod> str1;
    int position;
    boolean isKaiQi;
    CharSequence title;

    public CustomTimePickerDialog(Context context, int theme, OnTimeSetListener callBack,
                                  int hourOfDay, int minute, boolean is24HourView, CharSequence titleText) {
        super(context, theme, callBack, hourOfDay, minute / 10, is24HourView);
        this.callback = callBack;
        this.title = titleText;
        this.setButton(BUTTON_NEGATIVE, App.getInstance().getString(R.string.ok), new Message());
        this.setButton(BUTTON_POSITIVE, App.getInstance().getString(R.string.cancel), this);
        this.setTitle(titleText);

    }

    public void setTimerOtherTwo(ArrayList<com.itboye.pondteam.bean.DeviceDetailModel.TimePeriod> str1, int position, boolean isKaiQi) {
        this.str1 = str1;
        this.position = position;
        this.isKaiQi = isKaiQi;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == BUTTON_NEGATIVE) {
            if (callback != null && timePicker != null) {
                timePicker.clearFocus();
                int currentHour = timePicker.getCurrentHour();
                int currentMinute = timePicker.getCurrentMinute();
                callback.onTimeSet(timePicker, currentHour,
                        currentMinute * 10);
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
            NumberPicker mMinuteSpinner = (NumberPicker) timePicker.findViewById(field.getInt(null));
            NumberPicker mHourSpinner = (NumberPicker) timePicker.findViewById(fieldHour.getInt(null));
            mMinuteSpinner.setMinValue(0);
            mMinuteSpinner.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
            List<String> displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            mMinuteSpinner.setDisplayedValues(displayedValues.toArray(new String[0]));
            mHourSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    setTitle(title);
                }
            });
            mMinuteSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    setTitle(title);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        getButton(BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentHour = timePicker.getCurrentHour();
                int currentMinute = timePicker.getCurrentMinute();
                boolean hasTheSame = false;
                for (int i = 0; i < str1.size(); i++) {
                    if (i != position-1) {//不能与自身相比较
                        if (isKaiQi) {
                            if ((str1.get(i).getH0()) == (currentHour) && (str1.get(i).getM0()) == (currentMinute)&&str1.get(i).getH1()==str1.get(position-1).getH1()&&str1.get(i).getM1()==str1.get(position-1).getM1()) {
                                hasTheSame = true;
                                break;
                            } else {
                                hasTheSame = false;
                            }
                        } else {
                            if ((str1.get(i).getH1()) == (currentHour) && (str1.get(i).getM1()) == (currentMinute)&&str1.get(i).getH0()==str1.get(position-1).getH0()&&str1.get(i).getM0()==str1.get(position-1).getM0()) {
                                hasTheSame = true;
                                break;
                            } else {
                                hasTheSame = false;
                            }
                        }
                    }
                }
                if (hasTheSame) {
                    MAlert.alert("请设置不同于其他时间段的时间");
                } else {
                    callback.onTimeSet(timePicker, currentHour,
                            currentMinute * 10);
                    dismiss();
                }


            }
        });
    }
}
