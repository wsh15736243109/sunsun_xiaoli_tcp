package sunsun.xiaoli.jiarebang.device.led;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;

public class TiaoGuangActivity extends BaseActivity implements Observer {

    TextView[] textViews = new TextView[3];
    @IsNeedClick
    TextView txt_xitong, txt_red, txt_lvse, txt_zonghe, txt_title;
    RelativeLayout re_zonghe, re_red, re_green, re_xitong, re_blue;
    @IsNeedClick
    SeekBar seek_red, seek_green, seek_white, seek_blue;
    @IsNeedClick
    TextView txt_red_progress, txt_green_progress, txt_white_progress, txt_blue_progress;
    ImageView img_back;
    int position = 0;
    App mApp;
    //    public boolean isUpdateUI = true;
    UserPresenter userPresenter;
    LinearLayout li_theme, li_btn;
    Button btn_ok, btn_cancel;
    String[] title_C, title_H;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiao_guang);
        mApp = (App) getApplication();
        mApp.tiaoGuangUI = this;
        userPresenter = new UserPresenter(this);
        txt_title.setText(getString(R.string.masual_tiaoguang));
        position = getIntent().getIntExtra("position", -1);
        textViews[0] = txt_red;
        textViews[1] = txt_lvse;
        textViews[2] = txt_zonghe;
        title_C = new String[]{getString(R.string.tiaoguang_red), getString(R.string.tiaoguang_green), getString(R.string.tiaoguang_zonghe)};
        title_H = new String[]{getString(R.string.tiaoguang_bibo), getString(R.string.tiaoguang_lansemenghuan), getString(R.string.tiaoguang_zisegongjian)};
        initSeekbarChangeLisenter();
    }

    private void initSeekbarChangeLisenter() {
        seek_red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt_red_progress.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                userPresenter.deviceSet_led(mApp.ledDetailActivity.detailModel.getDid(), -1, -1, -1, "", -1, -1, -1, seekBar.getProgress(), -1);
            }
        });
        seek_blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt_blue_progress.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                userPresenter.deviceSet_led(mApp.ledDetailActivity.detailModel.getDid(), -1, -1, -1, "", -1, seekBar.getProgress(), -1, -1, -1);
            }
        });
        seek_green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                txt_green_progress.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                userPresenter.deviceSet_led(mApp.ledDetailActivity.detailModel.getDid(), -1, -1, -1, "", -1, -1, seekBar.getProgress(), -1, -1);
            }
        });
        seek_white.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                txt_white_progress.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                userPresenter.deviceSet_led(mApp.ledDetailActivity.detailModel.getDid(), -1, -1, -1, "", seekBar.getProgress(), -1, -1, -1, -1);
            }
        });
        initProgress();
    }

    public void initProgress() {
        if (mApp.ledDetailActivity.ledType.equals("ADT-C")) {
            re_blue.setVisibility(View.GONE);
        } else {
            re_blue.setVisibility(View.VISIBLE);
        }
        setXiLieValue();
        if (position == -1) {
            setColorValueAndProgress(mApp.ledDetailActivity.detailModelTcp.getW(), 0, mApp.ledDetailActivity.detailModelTcp.getG(), mApp.ledDetailActivity.detailModelTcp.getR());
            li_theme.setVisibility(View.VISIBLE);
            li_btn.setVisibility(View.GONE);
            setColorValueAndProgress(mApp.ledDetailActivity.detailModelTcp.getW(), mApp.ledDetailActivity.detailModelTcp.getB(), mApp.ledDetailActivity.detailModelTcp.getG(), mApp.ledDetailActivity.detailModelTcp.getR());
        } else {
            if (mApp.ledPeriodSettingsUI.arPer != null) {
                setColorValueAndProgress(mApp.ledPeriodSettingsUI.arPer.get(position).getW(), mApp.ledPeriodSettingsUI.arPer.get(position).getB(), mApp.ledPeriodSettingsUI.arPer.get(position).getG(), mApp.ledPeriodSettingsUI.arPer.get(position).getR());
                li_theme.setVisibility(View.GONE);
                li_btn.setVisibility(View.VISIBLE);
            }
        }

        swichCaseSelet();
    }

    //设置高亮选中项
    private void swichCaseSelet() {
        for (TextView textView : textViews) {
            textView.setTextColor(getResources().getColor(R.color.black));
        }
        if (mApp.ledDetailActivity.detailModelTcp.getW() == 100 && mApp.ledDetailActivity.detailModelTcp.getB() == 60 && mApp.ledDetailActivity.detailModelTcp.getG() == 80 && mApp.ledDetailActivity.detailModelTcp.getR() == 100) {
            textViews[0].setTextColor(getResources().getColor(R.color.red500));
        } else if (mApp.ledDetailActivity.detailModelTcp.getW() == 100 && mApp.ledDetailActivity.detailModelTcp.getB() == 80 && mApp.ledDetailActivity.detailModelTcp.getG() == 100 && mApp.ledDetailActivity.detailModelTcp.getR() == 40) {
            textViews[1].setTextColor(getResources().getColor(R.color.red500));
        } else if (mApp.ledDetailActivity.detailModelTcp.getW() == 100 && mApp.ledDetailActivity.detailModelTcp.getB() == 100 && mApp.ledDetailActivity.detailModelTcp.getG() == 100 && mApp.ledDetailActivity.detailModelTcp.getR() == 80) {
            textViews[2].setTextColor(getResources().getColor(R.color.red500));
        }
    }

    //设置水草灯或海水灯的各种颜色通道系列
    private void setXiLieValue() {
        for (int i = 0; i < textViews.length; i++) {
            TextView textView = textViews[i];
            if (mApp.ledDetailActivity.ledType.equals("ADT-C")) {
                textView.setText(title_C[i]);
            } else {
                textView.setText(title_H[i]);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApp.tiaoGuangUI = null;
    }

    private void putNewData(ArrayList<DeviceDetailModel.TimePeriod> arPer) {
        String str = new Gson().toJson(arPer);
        userPresenter.deviceSet_led(mApp.ledDetailActivity.detailModel.getDid(), -1, -1, -1, str, -1, -1, -1, -1, -1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.re_zonghe:
                setSelectTextValue(2);
                break;
            case R.id.re_red:
                setSelectTextValue(0);
                break;
            case R.id.re_green:
                setSelectTextValue(1);
                break;
            case R.id.btn_ok:
                mApp.ledPeriodSettingsUI.arPer.get(position).setR(seek_red.getProgress());
                mApp.ledPeriodSettingsUI.arPer.get(position).setG(seek_green.getProgress());
                mApp.ledPeriodSettingsUI.arPer.get(position).setW(seek_white.getProgress());
                putNewData(mApp.ledPeriodSettingsUI.arPer);
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    private void setSelectTextValue(int index) {
        for (TextView textView : textViews) {
            textView.setTextColor(getResources().getColor(R.color.black));
        }
        textViews[index].setTextColor(getResources().getColor(R.color.red500));
        switch (index) {
            case 0:
                setColorValueAndProgress(100, 60, 80, 100);
                userPresenter.deviceSet_led(mApp.ledDetailActivity.did, -1, -1, -1, "", 100, mApp.ledDetailActivity.ledType.equals("ADT-C") ? -1 : 60, 80, 100, -1);
                break;
            case 1:
                setColorValueAndProgress(100, 80, 100, 40);
                userPresenter.deviceSet_led(mApp.ledDetailActivity.did, -1, -1, -1, "", 100, mApp.ledDetailActivity.ledType.equals("ADT-C") ? -1 : 80, 100, 40, -1);
                break;
            case 2:
                setColorValueAndProgress(100, 100, 100, 80);
                userPresenter.deviceSet_led(mApp.ledDetailActivity.did, -1, -1, -1, "", 100, mApp.ledDetailActivity.ledType.equals("ADT-C") ? -1 : 100, 100, 80, -1);
                break;
        }
    }

    private void setColorValueAndProgress(int white, int blue, int green, int red) {
        seek_white.setProgress(white);
        seek_green.setProgress(green);
        seek_red.setProgress(red);
        seek_blue.setProgress(blue);
        txt_blue_progress.setText(blue + "%");
        txt_white_progress.setText(white + "%");
        txt_green_progress.setText(green + "%");
        txt_red_progress.setText(red + "%");
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
//                mApp.tiaoGuangUI.isUpdateUI = true;
                mApp.ledDetailActivity.beginRequest();
            } else if (entity.getEventType() == UserPresenter.deviceSet_led_success) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
