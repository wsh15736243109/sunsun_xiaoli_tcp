package sunsun.xiaoli.jiarebang.shuizuzhijia;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.base.IsNeedClick;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.shuizuzhijia.me.AquariumMeFragment;
import sunsun.xiaoli.jiarebang.shuizuzhijia.product.ProductFragment;
import sunsun.xiaoli.jiarebang.shuizuzhijia.shop.ShopFragment;
import sunsun.xiaoli.jiarebang.shuizuzhijia.store.StoreFragment;
import sunsun.xiaoli.jiarebang.shuizuzhijia.xiaoli.XiaoLiFragment;

/**
 * Created by Administrator on 2018/1/24.
 */

public class AquariumHomeMainActivity extends BaseActivity {
    private FragmentManager fragmentManager;
    RelativeLayout aquarium_device_layout, aquarium_shop_layout, aquarium_store_layout, aquarium_product_layout, aquarium_me_layout;
    @IsNeedClick
    TextView aquarium_device_text, aquarium_shop_text, aquarium_store_text, aquarium_product_text, aquarium_me_text;
    @IsNeedClick
    ImageView aquarium_device_image, aquarium_shop_image, aquarium_store_image, aquarium_product_image, aquarium_me_image;

    int index = 0;

    XiaoLiFragment xiaoLiFragment;
    ShopFragment shopFragment;
    StoreFragment storeFragment;
    ProductFragment productFragment;
    AquariumMeFragment aquariumMeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aquarium_home_main);
        fragmentManager = getSupportFragmentManager();
        setTabSelection(index);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aquarium_device_layout:
                index = 0;
                setTabSelection(index);
                break;
            case R.id.aquarium_shop_layout:
                index = 1;
                setTabSelection(index);
                break;
            case R.id.aquarium_store_layout:
                index = 2;
                setTabSelection(index);
                break;
            case R.id.aquarium_product_layout:
                index = 3;
                setTabSelection(index);
                break;
            case R.id.aquarium_me_layout:
                index = 4;
                setTabSelection(index);
                break;
        }
    }

    public void setTabSelection(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        clearStatus(transaction);
        switch (index) {
            case 0://首页
                if (xiaoLiFragment == null) {
                    //如果HomeFragment为空，则创建一个添加到界面
                    xiaoLiFragment = new XiaoLiFragment();
                    transaction.add(R.id.aquarium_content, xiaoLiFragment);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(xiaoLiFragment);
                }
                aquarium_device_text.setTextColor(getResources().getColor(R.color.main_green));
                break;
            case 1:
                if (shopFragment == null) {
                    //如果HomeFragment为空，则创建一个添加到界面
                    shopFragment = new ShopFragment();
                    transaction.add(R.id.aquarium_content, shopFragment);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(shopFragment);
                }
                aquarium_shop_text.setTextColor(getResources().getColor(R.color.main_green));
                break;
            case 2:
                if (storeFragment == null) {
                    //如果HomeFragment为空，则创建一个添加到界面
                    storeFragment = new StoreFragment();
                    transaction.add(R.id.aquarium_content, storeFragment);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(storeFragment);
                }
                aquarium_store_text.setTextColor(getResources().getColor(R.color.main_green));
                break;
            case 3:
                if (productFragment == null) {
                    //如果HomeFragment为空，则创建一个添加到界面
                    productFragment = new ProductFragment();
                    transaction.add(R.id.aquarium_content, productFragment);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(productFragment);
                }
                aquarium_product_text.setTextColor(getResources().getColor(R.color.main_green));
                break;
            case 4:
                if (aquariumMeFragment == null) {
                    //如果HomeFragment为空，则创建一个添加到界面
                    aquariumMeFragment = new AquariumMeFragment();
                    transaction.add(R.id.aquarium_content, aquariumMeFragment);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(aquariumMeFragment);
                }
                aquarium_me_text.setTextColor(getResources().getColor(R.color.main_green));
                break;
        }
        transaction.commit();
    }

    private void clearStatus(FragmentTransaction transaction) {
        if (xiaoLiFragment != null) {
            transaction.hide(xiaoLiFragment);
        }
        if (shopFragment != null) {
            transaction.hide(shopFragment);
        }
        if (storeFragment != null) {
            transaction.hide(storeFragment);
        }
        if (productFragment != null) {
            transaction.hide(productFragment);
        }
        if (aquariumMeFragment != null) {
            transaction.hide(aquariumMeFragment);
        }
        aquarium_device_text.setTextColor(getResources().getColor(R.color.gray_A1));
        aquarium_shop_text.setTextColor(getResources().getColor(R.color.gray_A1));
        aquarium_store_text.setTextColor(getResources().getColor(R.color.gray_A1));
        aquarium_product_text.setTextColor(getResources().getColor(R.color.gray_A1));
        aquarium_me_text.setTextColor(getResources().getColor(R.color.gray_A1));
//        if (orderFragment != null) {
//            transaction.hide(orderFragment);
//        }
//        if (consultationAndBuyFragment != null) {
//            transaction.hide(consultationAndBuyFragment);
//        }
//        if (shopCartFragment != null) {
//            transaction.hide(shopCartFragment);
//        }
    }
}
