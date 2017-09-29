package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;
import com.itboye.pondteam.volley.TimesUtils;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.MyPublishBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.GlidHelper;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentScrollView;

import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitleBarStyle2;

public class MyPublishDetailActivity extends LingShouBaseActivity implements TranslucentScrollView.TranslucentChangedListener,Observer {

    TranslucentActionBar actionBar;
    TranslucentScrollView pullzoom_scrollview;
    ImageView publish_img,iv_actionbar_left;
    Button btn_update;
    LingShouPresenter lingShouPresenter;
    MyPublishBean.PublishBean myPublishBean;
    TextView txt_title,txt_time,txt_content;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_publish_detail;
    }

    @Override
    protected void initData() {
        initTitleBarStyle2(this,actionBar,"",pullzoom_scrollview,this,publish_img);
        actionBar.setData("",R.mipmap.ic_left_light,"",0,"",null);
        String id = getIntent().getStringExtra("id");
        lingShouPresenter= new LingShouPresenter(this);
        lingShouPresenter.getArticleInfo(id);
        if(myPublishBean!=null){

        }

    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.iv_actionbar_left:
                 finish();
                 break;

         }
    }

    @Override
    public void onTranslucentChanged(int transAlpha) {

    }

    @Override
    public void update(Observable o, Object arg) {
        ResultEntity entity =handlerError(arg);
        if(entity!=null){
            if(entity.getCode()!=0){
                MAlert.alert(entity.getMsg());
                return;
            }else  if(entity.getEventType()==LingShouPresenter.getArticleInfo_succes){
                myPublishBean = (MyPublishBean.PublishBean) entity.getData();
                GlidHelper.glidLoad(publish_img, Const.imgurl+ myPublishBean.getImg());
                txt_time.setText("时间:" + TimesUtils.getStringTime(myPublishBean.getCreate_time(),"yyyy.MM.dd"));
                txt_content.setText(myPublishBean.getDetail());
                txt_title.setText(myPublishBean.getTitle());

            }else if(entity.getEventType()== LingShouPresenter.getArticleInfo_fail){
                MAlert.alert(entity.getData());
            }
        }
    }
}
