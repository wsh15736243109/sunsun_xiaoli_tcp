package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.PhotoAdapter;
import sunsun.xiaoli.jiarebang.adapter.recyclerviewstyle.GridSpacingItemDecoration;
import sunsun.xiaoli.jiarebang.beans.FileBean;
import sunsun.xiaoli.jiarebang.beans.UploadImageBean;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.utils.uploadmultipleimage.UploadImageUtils;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class AddPublishActivity extends LingShouBaseActivity implements IRecycleviewClick, Observer, UploadImageUtils.UploadResult {

    TranslucentActionBar actionBar;
    RecyclerView recycler_chooseimage;
    TextView img_add;
    private ArrayList<FileBean> arrayListPath = new ArrayList<>();
    private PhotoAdapter adapter;
    Button btn_publish;
    LingShouPresenter lingShouPresenter;
    EditText ed_title, ed_content;
    String sid = "1";
    private String imgId="3";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_publish;
    }

    @Override
    protected void initData() {
        lingShouPresenter = new LingShouPresenter(this);
        initTitlebarStyle1(this, actionBar, "添加发布", R.mipmap.ic_left_light, "", 0, "");
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recycler_chooseimage.addItemDecoration(new GridSpacingItemDecoration(3, getResources().getDimensionPixelSize(R.dimen.padding_middle), true));
        recycler_chooseimage.setHasFixedSize(true);
        recycler_chooseimage.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.img_add:
                openLibrary();
                break;
            case R.id.btn_publish:
                String title = ed_title.getText().toString();
                String content = ed_content.getText().toString();
                if (title.equals("")) {
                    MAlert.alert("请输入标题");
                    return;
                }
                if (content.equals("")) {
                    MAlert.alert("请输入内容");
                    return;
                }
                lingShouPresenter.addArtical(sid, getSp(Const.UID), title, imgId, content);
                break;
        }
    }

    private void openLibrary() {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.black))
                .titleBgColor(getResources().getColor(R.color.black))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // (截图默认配置：关闭    比例 1：1    输出分辨率  500*500)
//                .crop(2, 1, 1000, 500)
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();
        ImageSelector.open(this, imageConfig);   // 开启图片选择器
//        requestPermission(new String[]{Manifest.permission.CAMERA}, 0x0003);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            for (String path : pathList) {
                Log.i("ImagePathList", path);
            }
            arrayListPath = new ArrayList<>();
            FileBean fileBean = new FileBean();
            fileBean.setImgUrl(pathList.get(0));
            arrayListPath.add(fileBean);
            adapter = new PhotoAdapter(this, arrayListPath, this);

            recycler_chooseimage.setAdapter(adapter);
            img_add.setVisibility(View.GONE);
            new UploadImageUtils(getSp(Const.UID),"other").beginUpload("image",new File(pathList.get(0)),this);
        }
    }

    @Override
    public void onItemClick(int position) {
        openLibrary();
    }

    @Override
    public void onItemLongClick(int position) {

    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == LingShouPresenter.addArtical_success) {
                MAlert.alert(entity.getData());
                finish();
            } else if (entity.getEventType() == LingShouPresenter.addArtical_fail) {
                MAlert.alert(entity.getData());

            }
        }
    }

    @Override
    public void uploadSuccess(UploadImageBean response) {
        MAlert.alert("上传成功");
        imgId=response.getData().get(0).getId()+"";
    }

    @Override
    public void uploadFail(VolleyError error) {
        MAlert.alert(error.getMessage()+"上传失败");
    }
}
