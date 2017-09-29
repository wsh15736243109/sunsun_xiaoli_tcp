package com.itboye.pondteam.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Mr.w on 2017/5/13.
 */

public class XAlertDialog extends AlertDialog implements View.OnClickListener {
    private EditText edit_content;  //编辑框
    private TextView btn_ok, btn_cancel;  //确定取消按钮
    private OnEditInputFinishedListener mListener; //接口
    private int type;
    public  XAlertDialog(Context context, OnEditInputFinishedListener mListener) {
        super(context);
        this.mListener = mListener;
    }

    public void setType(int type){
        this.type=type;
    }

    public int getType(){
        return this.type;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == com.itboye.pondteam.R.id.btn_ok) {
            this.mListener.editInputFinished(edit_content.getText().toString());
        } else if (i == com.itboye.pondteam.R.id.btn_cancel) {
            dismiss();

        }
    }
    public void setHint(String value){
        edit_content.setHint(value);
    }

    public interface OnEditInputFinishedListener {
        void editInputFinished(String password);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.itboye.pondteam.R.layout.alert_x);
        //控件
        edit_content = (EditText) findViewById(com.itboye.pondteam.R.id.edit_content);
        btn_ok = (TextView) findViewById(com.itboye.pondteam.R.id.btn_ok);
        btn_cancel = (TextView) findViewById(com.itboye.pondteam.R.id.btn_cancel);

        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        //只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //加上下面这一行弹出对话框时软键盘随之弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
