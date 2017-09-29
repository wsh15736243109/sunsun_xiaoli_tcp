package com.itboye.pondteam.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;

import com.itboye.pondteam.R;
import com.itboye.pondteam.app.MyApplication;


/**
 * Created by Administrator on 2017/3/22.
 */

public class ColoTextUtil {
    public static void setColorfulValue(int startPo, int endPo, int color, String msg, TextView textView) {
        SpannableStringBuilder style = new SpannableStringBuilder(msg);
        style.setSpan(new ForegroundColorSpan(MyApplication.getInstance().getResources().getColor(color)), startPo, endPo, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(style);
    }
    public static void setColorfulValue2(int startPo, int endPo,int startPo2, int endPo2, int color, String msg, TextView textView) {
        SpannableStringBuilder style = new SpannableStringBuilder(msg);
        style.setSpan(new ForegroundColorSpan(MyApplication.getInstance().getResources().getColor(color)), startPo, endPo, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(MyApplication.getInstance().getResources().getColor(color)), startPo2, endPo2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(style);
    }
    public static void setDifferentSizeForTextView(int startPo, int endPo, String msg, TextView textView) {
        SpannableString styledText = new SpannableString(msg);
        styledText.setSpan(new TextAppearanceSpan(MyApplication.getInstance(), R.style.style0), startPo, endPo, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(MyApplication.getInstance(), R.style.style1), 0, startPo, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(styledText, TextView.BufferType.SPANNABLE);
    }
}
