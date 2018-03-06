package sunsun.xiaoli.jiarebang.shuizuzhijia.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.text.SpannedString;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.bean.ChatBean;
import com.itboye.pondteam.bean.DefaultMessage;
import com.itboye.pondteam.bean.HistoryChatBean;
import com.itboye.pondteam.bean.KefuBeans;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;
import com.yancy.imageselector.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter.CustomAdapter;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.UploadImageBean;
import sunsun.xiaoli.jiarebang.custom.FaceInputView;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.GlideLoader;
import sunsun.xiaoli.jiarebang.utils.Spanned2String;
import sunsun.xiaoli.jiarebang.utils.uploadmultipleimage.UploadImageUtils;

import static android.app.Activity.RESULT_OK;
import static com.itboye.pondteam.utils.EmptyUtil.getSp;

public class KeFuFragment extends LingShouBaseFragment implements OnRefreshListener, Observer, UploadImageUtils.UploadResult {

    //    private LinkedList<PeopleStudentBean> sList = null;
//    private LinkedList<PeopleTeacherBean> tList = null;
    private ChatBean beans = null;

    //** 聊天message 格式 *//
    private ListView lv;
    //** 信息发送按钮 *//*
    private TextView btnEnter;

    private CustomAdapter adapter;


    //
    ImageView addImg;
    View addEmoj;
//    FaceInputView faceInputView;

    ViewGroup bottom;
    LinearLayout selectImgContainer;
    LinearLayout imgPhoto;
    HorizontalScrollView horizontalScrollView;
    Integer gentiePosition = null;
    //    protected CameraPopupWindow mCameraPopupWindow;
    private File mCameraImageFile;// 照相机拍照得到的图片
    private FileUtils mFileUtils;
    private final int REQUEST_CODE_CAPTURE_CAMEIA = 100;
    private final int REQUEST_CODE_PICK_IMAGE = 200;
    private String keFuId = null;
    String lastCreateTime;

    ArrayList<ChatBean.ChatItem> arrayList = new ArrayList<ChatBean.ChatItem>();
    private SwipeRefreshLayout id_swipe_ly;
    int count = 0;
    String id;
    String notes;
    TextView btn_send;
    EditText et_input;
    private UserPresenter userPresenter;
    private int page_size = 6;
    private int page_no = 1;
    private String uid;
    private FaceInputView faceInputView;
    private int msg_type = 1;
    private KefuBeans kefuStatus;
    private String content;
    private String lastDataId = "0";


    //    ArrayList<ChatBean.ChatItem> arrayListT = new ArrayList<ChatBean.ChatItem>();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_producenter_kefu;
    }

    @Override
    protected void initData() {
        uid = getSp(Const.UID);
        getKefuStatus();
        id_swipe_ly.setOnRefreshListener(this);
        adapter = new CustomAdapter(getActivity(), arrayList, getActivity());
        lv.setAdapter(adapter);
        lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (addEmoj.isSelected()) {
                    showOrHideEmoj(addEmoj);
                }
                return false;
            }
        });
        beans = new ChatBean();
    }


    private void TextViewEdittext() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);

        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(
                    et_input.getWindowToken(), 0);
        }
    }

    private void getHistory() {
        // TODO Auto-generated method stub
        if (servicerUid == null) {
            id_swipe_ly.setRefreshing(false);
            return;
        }
        userPresenter.getCustomerHistory(uid, servicerUid, page_no, page_size);
    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
//            getMessage();
            getHistory();
            handler.sendEmptyMessage(1);
        }
    };


    public void getMessage() {
        if (keFuId == null) {
            id_swipe_ly.setRefreshing(false);
            return;
        }
        userPresenter.getCustomerAsk(keFuId, lastCreateTime);
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            System.out.println("-----------------------客服信息");
            handler.postDelayed(runnable, 3000);

        }

        ;
    };
    protected String servicerUid;

    /**
     * 客服初始化
     */
    public void getKefuStatus() {
        userPresenter = new UserPresenter(this);
        userPresenter.getCustomerStatus(uid);
    }


    // 发送图片
    private StringBuffer imgId = new StringBuffer();


    private Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }


    // 输入表情需要
    ImageGetter imageGetter = new ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            int id = Integer.parseInt(source);
            Drawable d = getResources().getDrawable(id);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            return d;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                String content = et_input.getText().toString();
                if (content.equals("")) {
                    MAlert.alert("内容为空");
                    return;
                }
                if (keFuId == null) {
                    //无客服情况
                    sendDefaultCustomer((et_input.getText()));
                } else {
                    sendMessage(Spanned2String.parse(et_input.getText()), 1);
                }
                break;
            case R.id.addImg:
                if (keFuId == null) {
                    MAlert.alert("当前客服繁忙，请稍后再试");
                    return;
                }
                openLibrary();
                break;
            case R.id.addEmoj:
                showOrHideEmoj(v);
                break;
        }
    }

    private void openLibrary() {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.blue))
                .titleBgColor(getResources().getColor(R.color.blue))
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            File file = new File(pathList.get(0));
//            Glide.with(getContext()).load(pathList.get(0)).placeholder(R.drawable.lingshou_logo).into(img_head);
//            img_head.setTag(pathList.get(0));
            new UploadImageUtils(getSp(Const.UID), "other").beginUpload("image", file, this);
        }
    }

    private void showOrHideEmoj(View v) {
        v.setSelected(!v.isSelected());
        if (faceInputView == null) {
            faceInputView = new FaceInputView(getActivity());
            bottom.addView(faceInputView);
            faceInputView.setOnClickListener(new FaceInputListenr());
        }
        if (v.isSelected()) {
            if (bottom.getChildCount() == 2) {
                bottom.getChildAt(1).setVisibility(View.GONE);
            } else if (bottom.getChildCount() == 3) {
                bottom.getChildAt(1).setVisibility(View.GONE);
                bottom.getChildAt(2).setVisibility(View.GONE);
            }
            // 监听软键盘显示与隐藏
            InputMethodManager inputMethodManager = (InputMethodManager) v
                    .getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE);

            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(
                        et_input.getWindowToken(), 0);
            }

            faceInputView.setVisibility(View.VISIBLE);
            addImg.setSelected(false);

        } else {
            faceInputView.setVisibility(View.GONE);

        }
    }

    @Override
    public void uploadSuccess(UploadImageBean response) {
        MAlert.alert("图片上传成功");
        sendMessage(response.getData().get(0).getId() + "", 2);
    }

    @Override
    public void uploadFail(VolleyError error) {
        MAlert.alert("图片上传失败");

    }

    private class FaceInputListenr implements FaceInputView.OnFaceClickListener {

        @Override
        public void selectedFace(FaceInputView.Face face) {
            int id = face.faceId;
            if (id == R.drawable.ic_face_delete_normal)// 删除按钮
            {
                int index = et_input.getSelectionStart();
                if (index == 0)
                    return;
                Editable editable = et_input.getText();
                editable.delete(index - 1, index);// 删除最后一个字符或表情
            } else {
                et_input.append(Html.fromHtml("<img src='" + id + "'/>",
                        imageGetter, null));// 添加表情
            }
        }

    }

    private void sendDefaultCustomer(Spanned content) {
        msg_type = 1;
        ChatBean.ChatItem item = new ChatBean.ChatItem();


        item.setMsgContent(content);
        item.setMsgType(msg_type + "");
        item.setOwnerType("1");
        item.setCreateTime(System.currentTimeMillis() / 1000.
                + "");
        lastCreateTime = System.currentTimeMillis() / 1000.
                + "";
        arrayList.add(item);
//
        userPresenter.sendDefaultCustomerMessage(Spanned2String.parse(content));
    }


    // 只发送文字
    public void sendMessage(String content, int msg_type) {
        this.msg_type = msg_type;
        this.content = content;
        userPresenter.sendCustomerMessage(keFuId, msg_type, uid, content, servicerUid);
    }

    boolean isRefresh = true;

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        page_no++;
        isRefresh = false;
        getHistory();
    }


    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                id_swipe_ly.setRefreshing(false);
            } else {
                if (entity.getEventType() == UserPresenter.getCustomerStatus_success) {
                    kefuStatus = (KefuBeans) entity.getData();
                    setKeFuStatus(kefuStatus);
                } else if (entity.getEventType() == UserPresenter.getCustomerStatus_fail) {
                    MAlert.alert(entity.getData());

                } else if (entity.getEventType() == UserPresenter.sendCustomerMessage_success) {
                    ChatBean.ChatItem item = new ChatBean.ChatItem();
                    item.setMsgContent(msg_type == 1 ? et_input.getText() : new SpannedString(content));
                    item.setMsgType(msg_type + "");
                    item.setOwnerType("1");
                    Log.v("request_params", "sendCustomerMessage_success_lastDataId" + lastDataId);
                    lastDataId = (Integer.parseInt(lastDataId) + 1) + "";
                    item.setId(lastDataId);
                    item.setCreateTime(System.currentTimeMillis() / 1000.
                            + "");
                    lastCreateTime = System.currentTimeMillis() / 1000.
                            + "";
                    arrayList.add(item);
                    adapter.notifyDataSetChanged();
                    lv.setSelection(lv.getBottom());
//                    page_no = 1;
//                    getHistory();
                    et_input.setText("");
                    MAlert.alert(entity.getData());
                } else if (entity.getEventType() == UserPresenter.sendCustomerMessage_fail) {
                    MAlert.alert(entity.getData());

                } else if (entity.getEventType() == UserPresenter.sendDefaultCustomerMessage_success) {
                    DefaultMessage defaultMessage = (DefaultMessage) entity.getData();
                    setDefualtMessage(defaultMessage);
                } else if (entity.getEventType() == UserPresenter.sendDefaultCustomerMessage_fail) {
                    MAlert.alert(entity.getData());

                } else if (entity.getEventType() == UserPresenter.getCustomerAsk_success) {
//                    MAlert.alert(entity.getData());
                    ChatBean chatBean = (ChatBean) entity.getData();
                    if (chatBean.getServicerStatus().equals("2")) {
                        MAlert.alert("客服已经断开连接");
                        keFuId = null;
                    } else {
                        if (chatBean.getHave().equals("1")) {
//                            arrayList.addAll(chatBean.getList());
//                            lastCreateTime = chatBean.getCreateTime();
//                            adapter.notifyDataSetChanged();
//                            lv.setSelection(lv.getBottom());
                        }
                    }
                } else if (entity.getEventType() == UserPresenter.getCustomerAsk_fail) {

                } else if (entity.getEventType() == UserPresenter.exitCommunion_success) {
                    MAlert.alert(entity.getData());

                } else if (entity.getEventType() == UserPresenter.exitCommunion_fail) {

                } else if (entity.getEventType() == UserPresenter.getCustomerHistory_success) {
//                    arrayList.clear();
//                    if (!isRefresh) {
//                        arrayList.clear();
//                        arrayList = ((HistoryChatBean) entity.getData()).getList();
//                        adapter = new CustomAdapter(getActivity(), arrayList, getActivity());
//                        lv.setAdapter(adapter);
//                    } else {
                    ArrayList<ChatBean.ChatItem> arrayListTemp = new ArrayList<>();
                    arrayListTemp = ((HistoryChatBean) entity.getData()).getList();
                    if (arrayListTemp.size() > 0) {

                        Log.v("request_params", "getCustomerHistory_success_lastDataId::" + lastDataId + "arrayListTemp:" + arrayListTemp.get(arrayListTemp.size() - 1).getId());
                        if (lastDataId.equals(arrayListTemp.get(arrayListTemp.size() - 1).getId())) {

                        } else {
                            arrayList.addAll(0, arrayListTemp);
                        }
                        lastDataId = arrayListTemp.get(arrayListTemp.size() - 1).getId();
                        Log.v("request_params", "getCustomerHistory_success_lastDataId2222:" + lastDataId);
                    }
                    adapter.notifyDataSetChanged();
                    id_swipe_ly.setRefreshing(false);
                } else if (entity.getEventType() == UserPresenter.getCustomerHistory_fail) {
//                    MAlert.alert(entity.getData());
                    id_swipe_ly.setRefreshing(false);
                }
            }
        }
        isRefresh = false;
    }

    private void setDefualtMessage(DefaultMessage defaultMessage) {
        ChatBean.ChatItem item = new ChatBean.ChatItem();
        Spanned a = new SpannedString("当前客服繁忙，如有紧急情况，" + defaultMessage.getContent());
        item = new ChatBean.ChatItem();
        item.setMsgContent(a);
        item.setMsgType(msg_type + "");
        item.setOwnerType("2");
        item.setCreateTime(System.currentTimeMillis() / 1000.
                + "");
        lastCreateTime = System.currentTimeMillis() / 1000.
                + "";
        arrayList.add(item);


        adapter.notifyDataSetChanged();
        lv.setSelection(lv.getBottom());
        et_input.setText("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyQueue();
    }

    // /聊天退出
    private void destroyQueue() {
        handler.removeCallbacks(runnable);
        if (keFuId != null) {
            userPresenter.exitcommunion(uid, keFuId, (lastCreateTime) + "");
        }
    }

    private void setKeFuStatus(KefuBeans kefuStatus) {
        if (kefuStatus != null) {
            SPUtils.put(App.getInstance(), null, Const.KEFUHEAD,
                    kefuStatus.getHead());
            SPUtils.put(App.getInstance(), null, Const.HEAD,
                    kefuStatus.getYouhead());
            if (!kefuStatus.getStatus().equals("1")) {
                ChatBean.ChatItem item = new ChatBean.ChatItem();
//                Spanned a = new SpannedString(notes);
//                if (notes == null || notes.equals("")) {
//                    MAlert.alert("暂无客服");
//                    return;
//                } else {
                item.setMsgContent(kefuStatus.getText());
                item.setMsgType("1");
                item.setOwnerType("2");
                item.setCreateTime(System.currentTimeMillis() / 1000.
                        + "");
                lastCreateTime = System.currentTimeMillis() / 1000.
                        + "";
                arrayList.add(item);
                adapter.notifyDataSetChanged();
                lv.setSelection(lv.getBottom());
                et_input.setText("");
//                }
//								sendMessgeContent();
            } else {
                new Thread(runnable).start();
                MAlert.alert("分配客服成功");
                keFuId = kefuStatus.getQueueid();
                lastCreateTime = kefuStatus.getCreateTime();
                servicerUid = kefuStatus.getServicerUid();

                SPUtils.put(App.getInstance(), null, Const.KEFUHEAD,
                        kefuStatus.getHead());
                SPUtils.put(App.getInstance(), null, Const.HEAD,
                        kefuStatus.getYouhead());


            }
        } else {
            MAlert.alert(kefuStatus + "客服繁忙,请重试...." + kefuStatus.getText());
        }
    }
}
