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

    TextView[] textViews = new TextView[4];
    @IsNeedClick
    TextView txt_xitong, txt_red, txt_lvse, txt_zonghe, txt_title;
    RelativeLayout re_zonghe, re_red, re_green, re_xitong, re_blue;
    @IsNeedClick
    SeekBar seek_red, seek_green, seek_white;
    @IsNeedClick
    TextView txt_red_progress, txt_green_progress, txt_white_progress, txt_blue_progress;
    ImageView img_back;
    int position = 0;
    App mApp;
    public boolean isUpdateUI = true;
    UserPresenter userPresenter;
    LinearLayout li_theme, li_btn;
    Button btn_ok, btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiao_guang);
        mApp = (App) getApplication();
        mApp.tiaoGuangUI = this;
        userPresenter = new UserPresenter(this);
        txt_title.setText(getString(R.string.masual_tiaoguang));
        position = getIntent().getIntExtra("position", -1);
        textViews[0] = txt_xitong;
        textViews[1] = txt_red;
        textViews[2] = txt_lvse;
        textViews[3] = txt_zonghe;
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
            }
        });
        initProgress();

    }

    public void initProgress() {
        if (mApp.ledDetailActivity.detailModel.getDevice_type().equals("S06-1")) {
            re_blue.setVisibility(View.GONE);
        } else {
            re_blue.setVisibility(View.VISIBLE);
        }
        if (isUpdateUI) {
            if (position == -1) {
                seek_white.setProgress(mApp.ledDetailActivity.detailModel.getW());
                seek_green.setProgress(mApp.ledDetailActivity.detailModel.getG());
                seek_red.setProgress(mApp.ledDetailActivity.detailModel.getR());
                txt_white_progress.setText(mApp.ledDetailActivity.detailModel.getW() + "%");
                txt_green_progress.setText(mApp.ledDetailActivity.detailModel.getG() + "%");
                txt_red_progress.setText(mApp.ledDetailActivity.detailModel.getR() + "%");
                li_theme.setVisibility(View.VISIBLE);
                li_btn.setVisibility(View.GONE);
            } else {
                if (mApp.ledPeriodSettingsUI.arPer != null) {
                    seek_white.setProgress(mApp.ledPeriodSettingsUI.arPer.get(position).getW());
                    seek_green.setProgress(mApp.ledPeriodSettingsUI.arPer.get(position).getG());
                    seek_red.setProgress(mApp.ledPeriodSettingsUI.arPer.get(position).getR());
                    txt_white_progress.setText(mApp.ledPeriodSettingsUI.arPer.get(position).getW() + "%");
                    txt_green_progress.setText(mApp.ledPeriodSettingsUI.arPer.get(position).getG() + "%");
                    txt_red_progress.setText(mApp.ledPeriodSettingsUI.arPer.get(position).getR() + "%");
                    li_theme.setVisibility(View.GONE);
                    li_btn.setVisibility(View.VISIBLE);
                }
            }
            isUpdateUI = false;
        }
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
                setSelectTextValue(3);
                break;
            case R.id.re_red:
                setSelectTextValue(1);
                break;
            case R.id.re_green:
                setSelectTextValue(2);
                break;
            case R.id.re_xitong:
                setSelectTextValue(0);
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
            if (textView.getId() != R.id.txt_xitong) {
                textView.setTextColor(getResources().getColor(R.color.gray_color1));
            }
        }
        if (index != 0) {
            textViews[index].setTextColor(getResources().getColor(R.color.red500));
        }
        switch (index) {
            case 1:
                userPresenter.deviceSet_led(mApp.ledDetailActivity.did, -1, -1, -1, "", 100, -1, 80, 100, -1);
                break;
            case 2:
                userPresenter.deviceSet_led(mApp.ledDetailActivity.did, -1, -1, -1, "", 100, -1, 100, 40, -1);
                break;
            case 3:
                userPresenter.deviceSet_led(mApp.ledDetailActivity.did, -1, -1, -1, "", 100, -1, 100, 80, -1);
                break;
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
            if (entity.getEventType() == UserPresenter.deviceSet_led_success) {
                MAlert.alert(entity.getData());
                mApp.tiaoGuangUI.isUpdateUI = true;
                mApp.ledDetailActivity.beginRequest();
            } else if (entity.getEventType() == UserPresenter.deviceSet_led_success) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
