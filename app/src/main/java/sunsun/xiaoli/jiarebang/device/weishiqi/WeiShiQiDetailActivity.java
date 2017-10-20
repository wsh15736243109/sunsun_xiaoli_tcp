package sunsun.xiaoli.jiarebang.device.weishiqi;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;

import sunsun.xiaoli.jiarebang.R;

/**
 * Created by Mr.w on 2017/10/20.
 */

public class WeiShiQiDetailActivity extends BaseActivity {

    ImageView img_back;
    TextView txt_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weishiqidetail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void setData(){
//        txt_title.setText();
    }
}
