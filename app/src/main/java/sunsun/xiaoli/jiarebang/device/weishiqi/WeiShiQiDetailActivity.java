package sunsun.xiaoli.jiarebang.device.weishiqi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.DeviceDetailModel;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.device.camera.CameraDeviceListActivity;

/**
 * Created by Mr.w on 2017/10/20.
 */

public class WeiShiQiDetailActivity extends BaseActivity {

    ImageView img_back,img_dongtaitishi;
    TextView txt_title, txt_shipin;
    RelativeLayout re_shipinguankan, re_weishimode_choose, re_zidingyisetting, re_weishi_single;
    private Intent intent;
    private String did;
    private DeviceDetailModel deviceDetailModel;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weishiqidetail);

        did = getIntent().getStringExtra("did");
        id = getIntent().getStringExtra("id");
        deviceDetailModel = (DeviceDetailModel) getIntent().getSerializableExtra("detailModel");
        txt_shipin.setText(Html.fromHtml(getString(R.string.shipinguankan) + "<br/><font color='#F49636'>" + getString(R.string.video_disconnect) + "</font>"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.re_shipinguankan:
                intent = new Intent(this, CameraDeviceListActivity.class);
                intent.putExtra("title", getString(R.string.shipinguankan));
                intent.putExtra("did", did);
                startActivity(intent);
                break;
            case R.id.re_weishimode_choose:
                String[] weishiMode = new String[]{getString(R.string.weishiset_zidingyi), getString(R.string.weishiset_yanshi_eight), getString(R.string.weishiset_yanshi_twelve), getString(R.string.weishiset_yanshi_twenty)};
                showAlert(weishiMode, 1);
                break;
            case R.id.re_zidingyisetting:

                break;
            case R.id.re_weishi_single:
                String[] weishi_single = new String[5];
                for (int i = 0; i < weishi_single.length; i++) {
                    weishi_single[i] = String.format(getString(R.string.piece), i + 1);
                }
                showAlert(weishi_single, 2);
                break;
        }
    }

    private void showAlert(String[] arg1, final int arg2) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        final NumberPicker numberPicker = new NumberPicker(this);
        numberPicker.setDisplayedValues(arg1);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(arg1.length - 1);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        alert.setView(numberPicker);
        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (arg2 == 1) {

                } else if (arg2 == 2) {

                }
            }
        });
        alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    private void setData() {
//        txt_title.setText();
        img_dongtaitishi.setBackgroundResource(R.drawable.guan);
        img_dongtaitishi.setBackgroundResource(R.drawable.kai);

    }
}
