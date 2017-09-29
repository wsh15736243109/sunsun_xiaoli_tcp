package sunsun.xiaoli.jiarebang.device;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;


public class FeedbackActivity extends BaseActivity implements TextWatcher, Observer {
    Button btnSend;
    EditText ediContent, edit_contact;
    ImageView fankuiback;
    CheckBox CheckBox;
    TextView device_type;
    //	AqDeviceMessageType messageType;
    int progress = 0;
    private final int SUCCESS = 0;
    int type = 0;
    UserPresenter userPresenter;
    String deviceTypeName = "";

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub com.itboye.sunsunhome.www.aq.PondTeamFeedbackActivity
        super.onCreate(arg0);
        setContentView(R.layout.activity_feedback);
        userPresenter = new UserPresenter(this);
        int deviceType = getIntent().getIntExtra("device_type", 0);
        device_type.setText( getString(R.string.wentifankui));
//        switch (deviceType) {
//            case 0:
//                break;
//            case 1:
//                deviceTypeName = "S01";
//                device_type.setText(getString(R.string.chitangguolv) + getString(R.string.wentifankui));
//                break;
//            case 2:
//                deviceTypeName = "S03";
//                device_type.setText(getString(R.string.sensenshuizu) + getString(R.string.wentifankui));
//                break;
//            case 3:
//                break;
//            case 4:
//                deviceTypeName = "S05";
//                device_type.setText(getString(R.string.bianpinshuibeng) + getString(R.string.wentifankui));
//                break;
//            case 5:
//                //加热棒
//                deviceTypeName = "S02";
//                device_type.setText(getString(R.string.jiarebang) + getString(R.string.wentifankui));
//                break;
//            case 6:
//                deviceTypeName = "S03";
//                device_type.setText(getString(R.string.bianpinshuibeng) + getString(R.string.wentifankui));
//                break;
//            case 7:
//                device_type.setText(getString(R.string.yuancheng_ph) + getString(R.string.wentifankui));
//                break;
//            case 8:
//                device_type.setText(getString(R.string.yuancheng_ph) + getString(R.string.wentifankui));
//                break;
//        }

        fankuiback = (ImageView) findViewById(R.id.fankuiback);
        CheckBox = (android.widget.CheckBox) findViewById(R.id.checkbox);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fankuiback:
                finish();
                break;
            case R.id.btnSend:
                //S01: 过滤桶 S02: 加热棒 S03: 变频水泵
                String contact = edit_contact.getText().toString();
                if (contact.equals("")) {
                    AlertShow("请输入联系方式！");
                    return;
                }
                String content = ediContent.getText().toString();
                if (content.equals("")) {
                    AlertShow("请输入反馈内容！");
                    return;
                }
                userPresenter.feedback(deviceTypeName, "", getSp(Const.EMAIL), contact, getSp(Const.UID), content);
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
            if (entity.getEventType() == UserPresenter.feedBack_success) {
                MAlert.alert(entity.getData(), Gravity.CENTER, getResources().getColor(R.color.main_green));
                finish();
            } else if (entity.getEventType() == UserPresenter.feedBack_success) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
