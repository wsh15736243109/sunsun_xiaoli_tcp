package sunsun.xiaoli.jiarebang.device.jinligang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;

import java.util.ArrayList;
import java.util.Iterator;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.PhotoAdapter;
import sunsun.xiaoli.jiarebang.adapter.recyclerviewstyle.GridSpacingItemDecoration;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.FileBean;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;
import sunsun.xiaoli.jiarebang.utils.FileOperateUtil;

/**
 * Created by Mr.w on 2017/5/9.
 */

public class PhotoAlbumActivity extends BaseActivity implements IRecycleviewClick {
    RecyclerView list_album;
    App app;
    ArrayList<FileBean> listFile;
    ImageView img_back;
    TextView txt_title;
    String[] listFileShuzu = new String[]{};
    private PhotoAdapter adapter;
    TextView txt_right,img_nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        app = (App) getApplication();
        listFile = app.videoUI.fileList;
        listFileShuzu = new String[listFile.size()];
        for (int i = listFileShuzu.length - 1; i >= 0; i--) {
            listFileShuzu[i] = listFile.get(i).getImgUrl();
        }
        txt_title.setText(getString(R.string.album_title));
        initRecyclerView();
    }

    private void initRecyclerView() {
        // 如果我们想要一个GridView形式的RecyclerView，那么在LayoutManager上我们就要使用GridLayoutManager
        // 实例化一个GridLayoutManager，列数为3
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        list_album.addItemDecoration(new GridSpacingItemDecoration(3, getResources().getDimensionPixelSize(R.dimen.padding_middle), true));
        list_album.setHasFixedSize(true);
        list_album.setLayoutManager(layoutManager);
        adapter = new PhotoAdapter(this,listFile, this);

        list_album.setAdapter(adapter);
        if (listFile.size()<=0) {
            img_nodata.setVisibility(View.VISIBLE);
        }else{
            img_nodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_right:
                showDeleteDialog();
                break;
        }
    }

    private void showDeleteDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.tips));
        alert.setMessage(getString(R.string.make_sure_delete_picture));
        alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Iterator<FileBean> iterator = listFile.iterator();
                while (iterator.hasNext()) {
                    FileBean fileBean = iterator.next();
                    if (fileBean.isSelect()) {
                        boolean re = FileOperateUtil.deleteFile(fileBean.getImgUrl().replace("/", "//"));
                        System.out.println("删除结果---" + re+"    删除路径---"+fileBean.getImgUrl().replace("/", "\\"));
                        iterator.remove();
                    }
                }
                adapter.notifyDataSetChanged();
                app.videoUI.setPhotoCount();
            }
        });
        alert.create();
        alert.show();
    }

    @Override
    public void onItemClick(int position) {
        if (adapter.getSelectVisible()) {
            listFile.get(position).setSelect(!listFile.get(position).isSelect());
            boolean hasAnySelect = false;
            for (FileBean fileBean : listFile) {
                if (fileBean.isSelect()) {
                    hasAnySelect = true;
                    break;
                } else {
                    hasAnySelect = false;
                }
            }
            if (hasAnySelect) {
                txt_right.setVisibility(View.VISIBLE);
                txt_right.setText(getString(R.string.delete));
            } else {
                txt_right.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
        } else {
            Intent intent = new Intent(this, ImageDetailActivity.class);
            intent.putExtra("img", listFileShuzu[position]);
            startActivity(intent);
        }
//        ImageViewerDialog imageViewerDialog=new ImageViewerDialog(this);
//        imageViewerDialog.setImageUrls(listFileShuzu);
//        imageViewerDialog.show(position);
    }

    @Override
    public void onItemLongClick(int position) {
        adapter.setSelectVisible(!adapter.getSelectVisible());
        adapter.notifyDataSetChanged();
    }
}
