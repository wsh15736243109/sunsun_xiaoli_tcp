package sunsun.xiaoli.jiarebang.sunsunlingshou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.bean.ArticalBean;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;
import com.itboye.pondteam.utils.loadingutil.MAlert;

import com.itboye.pondteam.volley.TimesUtils;



import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.ChatActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.LingShouSwitchLoginOrRegisterActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.GlidHelper;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentScrollView;

import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitleBarStyle2;


/**
 * Created by 张朝 on 2017/9/13.
 */

public class ArticleActivity extends LingShouBaseActivity implements TranslucentScrollView.TranslucentChangedListener {
    ImageView artical_iv;
    TextView artical_title,artical_author,artical_time,artical_answer,artical_content;
    ArticalBean.ListEntity listEntity;
    TranslucentActionBar actionBar;
    TranslucentScrollView pullzoom_scrollview;
    ImageView iv_actionbar_left;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_article;
    }

    @Override
    protected void initData() {
        initTitleBarStyle2(this,actionBar,"",pullzoom_scrollview,this,artical_iv);
        actionBar.setData("",R.mipmap.ic_left_light,"",0,"",null);
        listEntity = (ArticalBean.ListEntity) getIntent().getSerializableExtra("ListEntity");

        GlidHelper.glidLoad(artical_iv, Const.imgurl+ listEntity.getImg());
        artical_time.setText("时间:" + TimesUtils.getStringTime(listEntity.getCreate_time(),"yyyy.MM.dd"));
        artical_author.setText(listEntity.getNickname());
        artical_content.setText(listEntity.getDetail());
        artical_title.setText(listEntity.getTitle());
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.artical_answer:
             boolean isLogin = (boolean) SPUtils.get(this, null, Const.IS_LOGINED, false);
             if(isLogin){
                 startActivity(new Intent(ArticleActivity.this, ChatActivity.class).putExtra("from_id",listEntity.getUid()));
             }else{
                 MAlert.alert("未登录，去登陆");
                 //startActivity(new Intent(ArticleActivity.this,LingShouSwitchLoginOrRegisterActivity.class));
             }
             break;
         case R.id.iv_actionbar_left:
             finish();
             break;
     }
    }

    @Override
    public void onTranslucentChanged(int transAlpha) {


    }
}
