package sunsun.xiaoli.jiarebang.shuizuzhijia.product;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.base.IsNeedClick;

import sunsun.xiaoli.jiarebang.R;

public class ProductCenter_message_video_kefu_Activity extends BaseActivity implements
        OnClickListener {

    LinearLayout content_layoutMycoll;
    TextView txt_title;
    ImageView img_back;
    @IsNeedClick
    private ImageView imgdetails, imgvideo, imgartificial;
    // 头部标签切换的Fragment
    private Fragment WodehuifuFragment, MyTieziHuifu, MyKefuFragment,
            currentFragment;

    Button btnTiezi, btnTieziHuifu, btnTiezikefu;
    private View viewright, viewLeiftkefu, viewLeift;
    ImageView close_icon;
    String id;
    String title;
    String notes;
    @Override
    @SuppressLint("InlinedApi")
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_message_video_kefu);
        initTab();
        imgdetails = (ImageView) findViewById(R.id.imgdetails);
        imgvideo = (ImageView) findViewById(R.id.imgvideo);
        imgartificial = (ImageView) findViewById(R.id.imgartificial);
        title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        notes = getIntent().getStringExtra("notes");
        txt_title.setText(title);
    }
    /**
     * 初始化头部标签
     */
    @SuppressWarnings("deprecation")
    private void initTab() {
        if (WodehuifuFragment == null) {
            WodehuifuFragment = new ProducenterProducenterFragemnt();
        }

        if (!WodehuifuFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_layoutMycoll, WodehuifuFragment).commit();

            // 记录当前Fragment
            currentFragment = WodehuifuFragment;
            // 设置图片文本的变化
            btnTiezi.setTextColor(getResources().getColor(R.color.main_blue));
            btnTieziHuifu.setTextColor(getResources().getColor(R.color.gray_6c7bb));
            viewright.setBackgroundColor(getResources().getColor(
                    R.color.main_blue));// (R.color.home_blue);
            viewLeift.setBackgroundColor(getResources().getColor(
                    R.color.backgroundColor));
            btnTiezikefu.setTextColor(getResources().getColor(R.color.gray_6c7bb));
            viewLeiftkefu.setBackgroundColor(getResources().getColor(
                    R.color.backgroundColor));
            imgdetails.setBackgroundResource(R.drawable.produceter_details);
            imgvideo.setBackgroundResource(R.drawable.video);
            imgartificial.setBackgroundResource(R.drawable.artificial);
        }
    }

    private void clickTab1Layout() {
        if (WodehuifuFragment == null) {
            WodehuifuFragment = new ProducenterProducenterFragemnt();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),
                WodehuifuFragment);

        // 设置底部tab变化
        btnTiezi.setTextColor(getResources().getColor(R.color.main_blue));
        btnTieziHuifu.setTextColor(getResources().getColor(R.color.gray_6c7bb));
        viewright
                .setBackgroundColor(getResources().getColor(R.color.main_blue));// (R.color.home_blue);
        viewLeift.setBackgroundColor(getResources().getColor(
                R.color.backgroundColor));
        btnTiezikefu.setTextColor(getResources().getColor(R.color.gray_6c7bb));
        viewLeiftkefu.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
        imgdetails.setBackgroundResource(R.drawable.produceter_details);
        imgvideo.setBackgroundResource(R.drawable.video);
        imgartificial.setBackgroundResource(R.drawable.artificial);
    }

    /**
     * 点击第二个tab
     */
    private void clickTab2Layout() {
        if (MyTieziHuifu == null) {
            MyTieziHuifu = new ProducenterShiPinFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),
                MyTieziHuifu);

        btnTieziHuifu.setTextColor(getResources().getColor(R.color.main_blue));
        btnTiezi.setTextColor(getResources().getColor(R.color.gray_6c7bb));
        viewright.setBackgroundColor(getResources().getColor(
                R.color.backgroundColor));// (R.color.home_blue);
        viewLeift
                .setBackgroundColor(getResources().getColor(R.color.main_blue));
        btnTiezikefu.setTextColor(getResources().getColor(R.color.gray_6c7bb));
        viewLeiftkefu.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
        imgdetails.setBackgroundResource(R.drawable.imgchanpin);
        imgvideo.setBackgroundResource(R.drawable.video_select);
        imgartificial.setBackgroundResource(R.drawable.artificial);
    }

    /**
     * 点击第三个tab
     */
    public void clickTab3Layout() {
        if (MyKefuFragment == null) {
            MyKefuFragment = new KeFuFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),
                MyKefuFragment);

        btnTieziHuifu.setTextColor(getResources().getColor(R.color.gray_6c7bb));
        btnTiezi.setTextColor(getResources().getColor(R.color.gray_6c7bb));
        btnTiezikefu.setTextColor(getResources().getColor(R.color.main_blue));
        viewLeiftkefu.setBackgroundColor(getResources().getColor(
                R.color.main_blue));
        viewright.setBackgroundColor(getResources().getColor(
                R.color.backgroundColor));// (R.color.home_blue);
        viewLeift.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
        imgdetails.setBackgroundResource(R.drawable.imgchanpin);
        imgvideo.setBackgroundResource(R.drawable.video);
        imgartificial.setBackgroundResource(R.drawable.imgkefu);

    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.content_layoutMycoll, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
//            case R.id.layoutMycollTiezi:
//                clickTab1Layout();
//                break;
//            case R.id.layoutTieZiVideo:
//                clickTab2Layout();
//                break;
//            case R.id.layoutTieZiHkefu:
//                clickTab3Layout();
////			LoginController.onShopCarClick(this, null);
//                break;
            case R.id.btnTiezi:
                clickTab1Layout();
                break;
            case R.id.btnTieziHuifu:
                clickTab2Layout();
                break;
            case R.id.btnTiezikefu:
                clickTab3Layout();
//			LoginController.onShopCarClick(this, null);
                break;
//            case R.id.imgdetails:
//                clickTab1Layout();
//                break;
//            case R.id.imgvideo:
//                clickTab2Layout();
//                break;
//            case R.id.imgartificial:
//                clickTab3Layout();
////			LoginController.onShopCarClick(this, null);
//                break;
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }
    }

}
