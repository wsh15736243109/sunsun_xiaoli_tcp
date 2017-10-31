package sunsun.xiaoli.jiarebang.device.weishiqi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.bean.DeviceListBean;
import com.itboye.pondteam.db.DBManager;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import ChirdSdk.CHD_Client;
import ChirdSdk.ClientCallBack;
import ChirdSdk.StreamView;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.device.camera.CameraDeviceListActivity;

import static com.itboye.pondteam.utils.Const.imagePath;
import static com.itboye.pondteam.utils.Const.patten;
import static com.itboye.pondteam.utils.EmptyUtil.getSp;

/**
 * Created by Mr.w on 2017/10/20.
 */

public class WeiShiQiDetailActivity extends BaseActivity implements Observer {

    ImageView img_back, img_dongtaitishi;
    TextView txt_title, txt_shipin;
    RelativeLayout re_shipinguankan, re_weishimode_choose, re_zidingyisetting, re_weishi_single;
    private Intent intent;
    private String did;
    private DeviceDetailModel deviceDetailModel;
    private String id;
    UserPresenter lingShouPresenter;
    private ArrayList<DeviceListBean> arrayList;
    private String chirdDid;
    private String pass;
    private CHD_Client mClient;
    private StreamView mStreamView;
    FrameLayout mVideoLayout;
    private String todayTime;
    private String yesTerdayTime;
    private DBManager dbManager;
    private int[] flow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weishiqidetail);
        lingShouPresenter = new UserPresenter(this);
        did = getIntent().getStringExtra("did");
        id = getIntent().getStringExtra("id");
        deviceDetailModel = (DeviceDetailModel) getIntent().getSerializableExtra("detailModel");
        txt_shipin.setText(Html.fromHtml(getString(R.string.shipinguankan) + "<br/><font color='#F49636'>" + getString(R.string.video_disconnect) + "</font>"));
        lingShouPresenter.cameraQuery(did);
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
        txt_title.setText(deviceDetailModel.getDevice_nickname());
        img_dongtaitishi.setBackgroundResource(R.drawable.guan);
        img_dongtaitishi.setBackgroundResource(R.drawable.kai);

    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }

            if (entity.getEventType() == UserPresenter.cameraQuery_success) {
                //查询到了摄像头设备
                arrayList = (ArrayList<DeviceListBean>) entity.getData();
                if (arrayList.size() > 0) {
                    chirdDid = arrayList.get(0).getSlave_did();
                    pass = arrayList.get(0).getSlave_pwd();
                    if (mClient != null) {
                        mStreamView = new StreamView(this, null);
                        mVideoLayout.addView(mStreamView);
                        if (chirdDid != null) {
                            if (!chirdDid.equals("")) {
                                new MyTask().execute();
                            } else {
                                txt_shipin.setText(Html.fromHtml(getString(R.string.shipinguankan) + "<br/><font color='#F49636'>" + getString(R.string.video_disconnect) + "</font>"));
                            }
                        } else {
                            txt_shipin.setText(Html.fromHtml(getString(R.string.shipinguankan) + "<br/><font color='#F49636'>" + getString(R.string.video_disconnect) + "</font>"));
                        }
                    } else {
                        txt_shipin.setText(Html.fromHtml(getString(R.string.shipinguankan) + "<br/><font color='#F49636'>" + getString(R.string.video_disconnect) + "</font>"));
                    }
                } else {
                    txt_shipin.setText(Html.fromHtml(getString(R.string.shipinguankan) + "<br/><font color='#F49636'>" + getString(R.string.video_disconnect) + "</font>"));
                }
            } else if (entity.getEventType() == UserPresenter.cameraQuery_fail) {
                MAlert.alert(entity.getData());
            }else if (entity.getEventType() == UserPresenter.getdeviceinfosuccess) {
                MAlert.alert(entity.getData());
            }else if (entity.getEventType() == UserPresenter.getdeviceinfofail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            clientCallBackListener();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //统计流量
            initFlowPlus();
            setViewVisible(mClient.isConnect());
        }

    }

    private void initFlowPlus() {
        todayTime = new SimpleDateFormat(patten).format(new Date());
        try {
            yesTerdayTime = new SimpleDateFormat(patten).format(new SimpleDateFormat(patten).parse(Long.parseLong(todayTime) - 1 + ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dbManager = new DBManager(this);
        //查询昨日使用的流量
        flow = dbManager.queryFlow(chirdDid, getSp(Const.UID), todayTime);
//        txt_shuiwei_status.setText("昨日视频累计流量" + flow / 1024 + "Kb");
    }

    /**
     * 没有摄像头调用
     */
    private void setViewVisible(boolean hasAddSheXiangtou) {
        txt_wangsu.setVisibility(hasAddSheXiangtou ? View.VISIBLE : View.GONE);
        img_camera.setVisibility(hasAddSheXiangtou ? View.VISIBLE : View.GONE);//截屏按钮
        img_quanping.setVisibility(hasAddSheXiangtou ? View.VISIBLE : View.GONE);//全屏按钮
//        add.setVisibility(hasAddSheXiangtou ? View.GONE : View.VISIBLE);//添加摄像头按钮
        mVideoLayout.setVisibility(hasAddSheXiangtou ? View.VISIBLE : View.GONE);//视频所在画布按钮
    }

    private void clientCallBackListener() {

        mClient.setClientCallBack(new ClientCallBack() {

            public void paramChangeCallBack(int changeType) {
            }

            public void disConnectCallBack() {
                //视频连接状态
                txt_shipin.post(new Runnable() {
                    @Override
                    public void run() {
                        //视频连接状态
                        txt_shipin.setText(Html.fromHtml(getString(R.string.shipinguankan) + "<br/><font color='#F49636'>" + (mClient.isConnect() ? getString(R.string.video_connect) : getString(R.string.video_disconnect)) + "</font>"));
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setCameraOpen(mClient.isConnect());
                    }
                });
            }

            public void snapBitmapCallBack(Bitmap bitmap) {
                MAlert.alert(getString(R.string.caturesuccess) + imagePath);
            }

            public void recordTimeCountCallBack(String times) {
            }

            public void recordStopBitmapCallBack(Bitmap bitmap) {
            }

            public void videoStreamBitmapCallBack(final Bitmap bitmap) {
                //视频连接状态
                txt_shipin.post(new Runnable() {
                    @Override
                    public void run() {
                        if (bitmap != null) {
                            setCameraOpen(true);
                        } else {
                            //视频连接状态
                            setCameraOpen(false);
                        }
                        txt_shipin.setText(Html.fromHtml(getString(R.string.shipinguankan) + "<br/><font color='#F49636'>" + (mClient.isConnect() ? getString(R.string.video_connect) : getString(R.string.video_disconnect)) + "</font>"));
                    }
                });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mClient.isConnect()) {
                            txt_wangsu.setText(mClient.getVideoFrameBps());
                        } else {
//                            setCameraOpen(false);
                        }
                    }
                });
                mStreamView.showBitmap(bitmap);
            }

            public void videoStreamDataCallBack(int format, int width, int height, int datalen, byte[] data) {
            }

            public void serialDataCallBack(int datalen, byte[] data) {
                Log.v("test", "RecvSerialDataLen:" + datalen);
            }

            public void audioDataCallBack(int datalen, byte[] data) {
            }
        });
        mClient.connectDevice(chirdDid, pass);
        mClient.openVideoStream();
    }

    TextView txt_wangsu;
    ImageView img_camera, img_quanping;

    /**
     * 设摄像头状态
     *
     * @param b true:打开  false:关闭
     */
    private void setCameraOpen(boolean b) {
        if (b) {
//            txt_isOpen.setVisibility(View.GONE);
            mVideoLayout.setBackgroundColor(Color.parseColor("#ffffffff"));
            txt_wangsu.setVisibility(View.VISIBLE);
            img_camera.setVisibility(View.VISIBLE);
            img_quanping.setVisibility(View.VISIBLE);
        } else {
            mVideoLayout.removeAllViews();
//            txt_isOpen.setVisibility(View.VISIBLE);
            mVideoLayout.setBackgroundColor(Color.parseColor("#000000"));
            txt_wangsu.setVisibility(View.GONE);
            img_camera.setVisibility(View.GONE);
            img_quanping.setVisibility(View.GONE);
        }
    }
}
