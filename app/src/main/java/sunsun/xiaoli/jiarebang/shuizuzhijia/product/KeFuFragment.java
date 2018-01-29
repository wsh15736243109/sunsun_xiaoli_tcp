package sunsun.xiaoli.jiarebang.shuizuzhijia.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Html.ImageGetter;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.yancy.imageselector.utils.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sunsun.xiaoli.jiarebang.R;

public class KeFuFragment extends LingShouBaseFragment implements OnRefreshListener {

//    private LinkedList<PeopleStudentBean> sList = null;
//    private LinkedList<PeopleTeacherBean> tList = null;
//    private ChatBean beans = null;

    //** 聊天message 格式 *//
    private ListView lv;
    //** 信息编辑框 *//*
    private EditText input;
    //** 信息发送按钮 *//*
    private TextView btnEnter;

//    private CustomAdapter adapter;


    //
    View addImg;
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
//    ArrayList<ChatBean.ChatItem> arrayList = new ArrayList<ChatBean.ChatItem>();
    private int pageNo = 1;
    private SwipeRefreshLayout mSwipeLayout;
    int count = 0;
    String id;
    String notes;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
//        getKefuStatus();
//        id = getActivity().getIntent().getStringExtra("id");
//        notes = getActivity().getIntent().getStringExtra("notes");
//        mFileUtils = new FileUtils(getActivity());
////        sList = new LinkedList<PeopleStudentBean>();
////        tList = new LinkedList<PeopleTeacherBean>();
//
//        mSwipeLayout = (SwipeRefreshLayout) getView().findViewById(
//                R.id.id_swipe_ly);
//        mSwipeLayout.setOnRefreshListener(this);
//        mSwipeLayout.setColorScheme(android.R.color.holo_green_dark,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//
//        adapter = new CustomAdapter(getActivity(), arrayList, getActivity());
//        lv.setAdapter(adapter);
//        beans = new ChatBean();
//        initViewsMethod();
//
//        addEmoj.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                v.setSelected(!v.isSelected());
//                if (faceInputView == null) {
//                    faceInputView = new FaceInputView(getActivity());
//                    bottom.addView(faceInputView);
//                    faceInputView.setOnClickListener(new FaceInputListenr());
//                }
//                if (v.isSelected()) {
//                    if (bottom.getChildCount() == 2) {
//                        bottom.getChildAt(1).setVisibility(View.GONE);
//                    } else if (bottom.getChildCount() == 3) {
//                        bottom.getChildAt(1).setVisibility(View.GONE);
//                        bottom.getChildAt(2).setVisibility(View.GONE);
//                    }
//                    // 监听软键盘显示与隐藏
//                    InputMethodManager inputMethodManager = (InputMethodManager) v
//                            .getContext().getSystemService(
//                                    Context.INPUT_METHOD_SERVICE);
//
//                    if (inputMethodManager.isActive()) {
//                        inputMethodManager.hideSoftInputFromWindow(
//                                input.getWindowToken(), 0);
//                    }
//
//                    faceInputView.setVisibility(View.VISIBLE);
//                    addImg.setSelected(false);
//
//                } else {
//                    faceInputView.setVisibility(View.GONE);
//
//                }
//            }
//        });
//
//        addImg.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (selectImgContainer == null) {
//                    horizontalScrollView = (HorizontalScrollView) getActivity()
//                            .getLayoutInflater().inflate(
//                                    R.layout.item_producenter_kefu_select,
//                                    bottom, false);
//                    bottom.addView(horizontalScrollView);
//                    selectImgContainer = (LinearLayout) horizontalScrollView
//                            .getChildAt(0);
//
//                    selectImgContainer.getChildAt(0).setOnClickListener(
//                            new OnClickListener() {
//
//                                @Override
//                                public void onClick(View v) {
//
//                                    // onimgPhoto(v);
//                                    openCamera();
//                                }
//                            });
//                    selectImgContainer.getChildAt(1).setOnClickListener(
//                            new OnClickListener() {
//
//                                @Override
//                                public void onClick(View v) {
//                                    new PhotoSelectFragment(listener, 3)
//                                            .show(getActivity()
//                                                            .getSupportFragmentManager(),
//                                                    null);
//                                }
//                            });
//
//                }
//                addImg.setSelected(!addImg.isSelected());
//                if (addImg.isSelected()) {
//                    if (bottom.getChildCount() == 2) {
//                        bottom.getChildAt(1).setVisibility(View.GONE);
//                    } else if (bottom.getChildCount() == 3) {
//                        bottom.getChildAt(1).setVisibility(View.GONE);
//                        bottom.getChildAt(2).setVisibility(View.GONE);
//                    }
//                    ((View) selectImgContainer.getParent())
//                            .setVisibility(View.VISIBLE);
//                    addEmoj.setSelected(false);
//                } else {
//                    ((View) selectImgContainer.getParent())
//                            .setVisibility(View.GONE);
//                }
//            }
//
//        });
//        TextViewEdittext();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_producenter_kefu;
    }

    @Override
    protected void initData() {

    }


    private void TextViewEdittext() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);

        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(
                    input.getWindowToken(), 0);
        }
    }

    private void getHistory() {
        // TODO Auto-generated method stub

//        MyJsonRequest.Builder<ChatBean> builder = new MyJsonRequest.Builder<>();
//        builder.apiVer("100")
//                .typeKey("BY_Customer_histmsg")
//                .param("uid",
//                        SPUtils.get(getActivity(), null, SPContants.USER_ID, "")
//                                + "").param("servicer_uid", servicerUid)
//                .param("page_no", pageNo).param("page_size", 6)
//                .requestListener(new XRequestListener<ChatBean>() {
//
//                    @Override
//                    public void onResponse(ChatBean arg0) {
//                        if (arg0.getList() != null) {
//                            arrayList.addAll(0, arg0.getList());
//                            adapter.notifyDataSetChanged();
//                            lv.setSelection(6);
//                        }
//                        mSwipeLayout.setRefreshing(false);
//                    }
//                }).errorListener(new XErrorListener() {
//
//            @Override
//            public void onErrorResponse(Exception exception, int code,
//                                        String msg) {
//                // TODO Auto-generated method stub
//                // Toast.makeText(getApplicationContext(), msg,
//                // 1).show();
//
//                Log.v("errorcode", "haha--" + code);
//                Log.v("errormsg", "haha===" + msg + ":" + servicerUid);
//            }
//        });
//        HttpRequest.getDefaultRequestQueue().add(builder.build());
    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub

            getMessage();
            // getHistory();
            handler.sendEmptyMessage(1);
        }
    };

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
//        MyJsonRequest.Builder<KuFuBean> builder = new MyJsonRequest.Builder<>();
//        builder.apiVer("100")
//                .typeKey("BY_Customer_link")
//                .param("id", id)
//                .param("uid",
//                        (String) SPUtils.get(getActivity(), null,
//                                SPContants.USER_ID, ""))
//                .requestListener(new XRequestListener<KuFuBean>() {
//
//                    @Override
//                    public void onResponse(KuFuBean arg0) {
//                        // TODO Auto-generated method stub
//                        if (arg0 != null) {
//                            if (arg0.getStatus().equals("false")) {
//                                SPUtils.put(App.ctx, null, SPContants.KEFUHED,
//                                        arg0.getHead());
//                                SPUtils.put(App.ctx, null, SPContants.YOUHEAD,
//                                        arg0.getYouhead());
//                                System.out.println("<><><><><><><><<>><<<>" + arg0.getText());
//                                ChatBean.ChatItem item = new ChatItem();
//                                Spanned a = new SpannedString(notes);
//                                Log.d("SpannedString",notes+"");
//                                if (notes== null || notes.equals("")) {
//                                    ByAlert.alert("暂无客服");
//                                    return;
//                                } else {
//                                    item.setMsgContent(a);
//                                    item.setMsgType("1");
//                                    item.setOwnerType("2");
//                                    item.setCreateTime(System.currentTimeMillis() / 1000.
//                                            + "");
//                                    lastCreateTime = System.currentTimeMillis() / 1000.
//                                            + "";
//                                    arrayList.add(item);
//                                    adapter.notifyDataSetChanged();
//                                    lv.setSelection(lv.getBottom());
//                                    input.setText("");
//                                }
////								sendMessgeContent();
//                            } else {
//                                new Thread(runnable).start();
//                                ByAlert.alert("分配客服成功");
//                                keFuId = arg0.getQueueid();
//                                lastCreateTime = arg0.getCreateTime();
//                                servicerUid = arg0.getServicerUid();
//
//                                SPUtils.put(App.ctx, null, SPContants.KEFUHED,
//                                        arg0.getHead());
//                                SPUtils.put(App.ctx, null, SPContants.YOUHEAD,
//                                        arg0.getYouhead());
//
//
//                            }
//                        } else {
//                            ByAlert.alert(arg0 + "客服繁忙,请重试...." + arg0.getText());
//                        }
//                    }
//                }).errorListener(new XErrorListener() {
//
//            @Override
//            public void onErrorResponse(Exception exception, int code,
//                                        String msg) {
//                // TODO Auto-generated method stub
//                ByAlert.alert("请求出错");
//                Log.v("errorcode", "haha" + code);
//                Log.v("errormsg", "haha_link" + msg);
//            }
//        });
//        HttpRequest.getDefaultRequestQueue().add(builder.build());
    }


    //客服默认回复
    public void sendMessgeContent() {

//        if (input.getText().toString().equals("") || input.getText().toString() == null) {
//            ByAlert.alert("请输入内容！");
//            return;
//        }
//
//        MyJsonRequest.Builder<ChatBean> builder = new MyJsonRequest.Builder<ChatBean>();
//        builder.apiVer("100")
//                .typeKey("BY_Customer_onserviceask")
//
//                .param("content", Spanned2String.parse(input.getText()))
//                .requestListener(new XRequestListener<ChatBean>() {
//
//                    @Override
//                    public void onResponse(ChatBean arg0) {
////						ByAlert.alert(arg0 + "doudou");
//
//
//                        //用户信息
//                        ChatBean.ChatItem item = new ChatItem();
//                        item.setMsgContent(input.getText());
//                        item.setMsgType("1");
//                        item.setOwnerType("1");
//
//                        arrayList.add(item);
//
//                        Spanned a = new SpannedString(notes);
//                        if(notes==null || notes.equals("")){
//                            return;
//                        }
//                        //客服消息
//                        ChatBean.ChatItem item2 = new ChatItem();
//                            item2.setMsgContent(a);
//                            android.util.Log.d("chatitem", arg0.getContent() + "");
//                            item2.setMsgType("1");
//                            item2.setOwnerType("2");
//
//                            arrayList.add(item2);
//                            adapter.notifyDataSetChanged();
//                            lv.setSelection(lv.getBottom());
//                            input.setText("");
//
//                    }
//                }).errorListener(new XErrorListener() {
//
//            @Override
//            public void onErrorResponse(Exception exception, int code,
//                                        String msg) {
//                // TODO Auto-generated method stub
//                // Toast.makeText(getApplicationContext(), msg,
//                // 1).show();
//                ByAlert.alert("null" + msg);
//                Log.v("errorcode", "haha" + code);
//                Log.v("errormsg", "hahaSenTExt" + msg);
//            }
//        });
//        HttpRequest.getDefaultRequestQueue().add(builder.build());
    }

    // 只发送文字
    public void sendMessage() {
//        if (input.getText().toString().equals("") || input.getText().toString() == null) {
//            ByAlert.alert("请输入内容！");
//            return;
//        }
//        MyJsonRequest.Builder<String> builder = new MyJsonRequest.Builder<>();
//        builder.apiVer("100")
//                .typeKey("BY_Customer_receivemsg")
//                .param("queue_id", keFuId)
//                .param("msg_type", "1")
//                .param("msg_owner",
//                        (String) SPUtils.get(getActivity(), null,
//                                SPContants.USER_ID, ""))
//                .param("msg_receiver", servicerUid)
//                .param("msg_content", Spanned2String.parse(input.getText()))
//                .requestListener(new XRequestListener<String>() {
//
//                    @Override
//                    public void onResponse(String arg0) {
////						ByAlert.alert(arg0 + "");
//                        ChatBean.ChatItem item = new ChatItem();
//                        item.setMsgContent(input.getText());
//                        item.setMsgType("1");
//                        item.setOwnerType("1");
//                        item.setCreateTime(System.currentTimeMillis() / 1000.
//                                + "");
//                        lastCreateTime = System.currentTimeMillis() / 1000.
//                                + "";
//                        arrayList.add(item);
//                        adapter.notifyDataSetChanged();
//                        lv.setSelection(lv.getBottom());
//                        input.setText("");
//                    }
//                }).errorListener(new XErrorListener() {
//
//            @Override
//            public void onErrorResponse(Exception exception, int code,
//                                        String msg) {
//                // TODO Auto-generated method stub
//                // Toast.makeText(getApplicationContext(), msg,
//                // 1).show();
//
//                Log.v("errorcode", "haha" + code);
//                Log.v("errormsg", "hahaSenTExt" + msg);
//            }
//        });
//        HttpRequest.getDefaultRequestQueue().add(builder.build());
    }

    // 发送图片
    private StringBuffer imgId = new StringBuffer();

    private void sendImageIdToServer(String imgId, final String imgPath) {
        // TODO Auto-generated method stub

//        MyJsonRequest.Builder<String> builder = new MyJsonRequest.Builder<>();
//        builder.apiVer("100")
//                .typeKey("BY_Customer_receivemsg")
//                .param("queue_id", keFuId)
//                .param("msg_type", "2")
//                .param("msg_owner",
//                        (String) SPUtils.get(getActivity(), null,
//                                SPContants.USER_ID, ""))
//                .param("msg_content", imgId).param("msg_receiver", servicerUid)
//                .requestListener(new XRequestListener<String>() {
//
//                    @Override
//                    public void onResponse(String arg0) {
//                        // TODO Auto-generated method stub
////						ByAlert.alert(arg0);
//                        ChatBean.ChatItem item = new ChatItem();
//                        item.setMsgContent((Spanned) String2Spanned
//                                .parsed(imgPath));
//                        item.setMsgType("2");
//                        item.setOwnerType("1");
//                        item.setCreateTime(System.currentTimeMillis() / 1000.
//                                + "");
//                        arrayList.add(item);
//                        lastCreateTime = System.currentTimeMillis() / 1000.
//                                + "";
//                        adapter.notifyDataSetChanged();
//
//                        lv.setSelection(lv.getBottom());
//                        // if (arg0 != null) {
//                        // if (arg0.getStatus().equals("false")) {
//                        // ByAlert.alert(arg0.getText());
//                        // } else {
//                        // ByAlert.alert(arg0.getQueueid()
//                        // + "arg0.getQueueid()");
//                        // keFuId = arg0.getQueueid();
//                        // lastCreateTime = arg0.getCreateTime();
//                        // }
//                        // } else {
//                        // ByAlert.alert(arg0 + "<><>");
//                        // }
//                    }
//                }).errorListener(new XErrorListener() {
//
//            @Override
//            public void onErrorResponse(Exception exception, int code,
//                                        String msg) {
//                // TODO Auto-generated method stub
//                Log.v("errorcode", "haha" + code);
//                Log.v("errormsg", "hahaSenMMesgh" + msg);
//            }
//        });
//        HttpRequest.getDefaultRequestQueue().add(builder.build());
    }

    private Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    /**
     * 用当前时间给取得的图片命名
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyy_MM_dd_HH_mm_ss");
        return dateFormat.format(date) + ".jpg";
    }

    public void getMessage() {
//        MyJsonRequest.Builder<ChatBean> builder = new MyJsonRequest.Builder<>();
//        builder.apiVer("100").typeKey("BY_Customer_ask")
//                .param("queue_id", keFuId).param("create_time", lastCreateTime)
//                .requestListener(new XRequestListener<ChatBean>() {
//
//                    @Override
//                    public void onResponse(ChatBean arg0) {
//                        if (arg0 != null) {
//                            if (arg0.getList() != null) {
//                                System.out.println("客服队列Id---------" + count
//                                        + "--->" + keFuId + "==="
//                                        + arg0.getHave());
//                                if (arg0.getServicerStatus().equals("2")) {
//                                    ByAlert.alert("客服已经断开连接");
//                                } else {
//                                    if (arg0.getHave().equals("true")) {
//                                        arrayList.addAll(arg0.getList());
//                                        lastCreateTime = arg0.getCreateTime();
//                                        adapter.notifyDataSetChanged();
//                                        lv.setSelection(lv.getBottom());
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }).errorListener(new XErrorListener() {
//
//            @Override
//            public void onErrorResponse(Exception exception, int code,
//                                        String msg) {
//                // TODO Auto-generated method stub
//                // Toast.makeText(getApplicationContext(), msg,
//                // 1).show();
//                mSwipeLayout.setRefreshing(false);
//                Log.v("errorcode", "haha" + code);
//                Log.v("errormsg", "hahaGetMEss" + msg);
//            }
//        });
//        HttpRequest.getDefaultRequestQueue().add(builder.build());
    }

    // 打开相机
    private void openCamera() {
//        try {
//            File PHOTO_DIR = new File(mFileUtils.getStorageDirectory());
//            if (!PHOTO_DIR.exists())
//                PHOTO_DIR.mkdirs();// 创建照片的存储目录
//
//            mCameraImageFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
//            final Intent intent = getTakePickIntent(mCameraImageFile);
//            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
//        } catch (ActivityNotFoundException e) {
//        }
    }

    private List<String> uploadedUrl = new ArrayList<String>();
    public List<String> path = new ArrayList<>();
    protected int countImg;
//    private PhotoSelectListener listener = new PhotoSelectListener() {
//
//        @Override
//        public void onPhotoSelectFinish(final List<String> paths) {
//            // TODO Auto-generated method stub
//            if (keFuId == null) {
//                ByAlert.alert("客服不在线，请稍后重试...");
//
//
//                return;
//            }
//            showProgressDialog("图片上传中", true);
//            path = paths;
//            countImg = 0;
//            for (final String string : paths) {
//                // adapter.addItemNotifiChange(new
//                // Bean(mFileUtils.getFilePathFromUri(Uri
//                // .parse(string)), R.drawable.me,
//                // new Date() + "", 0));
//                countImg++;
//                ImageCompressAndUpload.getInstance().execute(string,
//                        new OnUploadlistener() {
//
//                            @Override
//                            public void OnSuccess(String resultData) {
//                                uploadedUrl.add(resultData);
//                                sendImageIdToServer(resultData, string);
//                                DebugLog.v("bytag", resultData);
//                                if (countImg == paths.size()) {
//                                    try {
//
//                                        closeProgressDialog();
//                                    } catch (Exception e) {
//                                        // TODO: handle exception
//                                    }
//
//                                    imgId.append(resultData + ",");
//                                    ByAlert.alert("图片上传成功");
//                                    // sendToServer();
//                                }
//                            }
//
//                            @Override
//                            public void OnError(String resultData) {
//                                ByAlert.alert(resultData + "error");
//                                try {
//                                    closeProgressDialog();
//
//                                } catch (Exception e) {
//                                    // TODO: handle exception
//                                }
//                            }
//                        });
//
//            }
//        }
//    };

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

    }

//    private class FaceInputListenr implements OnFaceClickListener {
//
//        @Override
//        public void selectedFace(Face face) {
//            int id = face.faceId;
//            if (id == R.drawable.ic_face_delete_normal)// 删除按钮
//            {
//                int index = input.getSelectionStart();
//                if (index == 0)
//                    return;
//                Editable editable = input.getText();
//                editable.delete(index - 1, index);// 删除最后一个字符或表情
//            } else {
//                input.append(Html.fromHtml("<img src='" + id + "'/>",
//                        imageGetter, null));// 添加表情
//            }
//        }
//
//    }

//    // /////////////////
//    //** 处理listView 的 item方法 *//*
//    private void initViewsMethod() {
//        input = (EditText) getActivity().findViewById(R.id.input);
//        btnEnter = (TextView) getActivity().findViewById(R.id.enter);
//
//        lv.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
//
//            @Override
//            public void onCreateContextMenu(ContextMenu menu, View v,
//                                            ContextMenuInfo menuInfo) {
//                // TODO Auto-generated method stub
//
//                menu.setHeaderTitle("提示：");
//                menu.setHeaderIcon(android.R.drawable.stat_notify_error);
//                menu.add(0, 0, 1, "删除");
//                menu.add(1, 1, 0, "取消");
//
//            }
//        });
//
//        btnEnter.setOnClickListener(new OnClickListener() {
//
//            @SuppressWarnings("unused")
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                if (keFuId == null) {
//                    sendMessgeContent();
////					input.setText("");
//
//                }
//                String name = input.getText().toString();
//                String txt = Spanned2String.parse(input.getText());
//
//                if (null == txt && txt == "") {
//                    ByAlert.alert("发送内容不能为空");
//
//                } else {
//                    sendMessage();
//                }
//            }
//        });
//
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        // TODO Auto-generated method stub
//        switch (item.getItemId()) {
//            case 0:
//                ByAlert.alert("删除成功！");
//                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
//                        .getMenuInfo();
//                // ChatItem bean = (ChatItem) adapter.getItem(info.position);
//                arrayList.remove(info.position);
//                adapter.notifyDataSetChanged();
//                break;
//        }
//        return super.onContextItemSelected(item);
//
//    }
//
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (addEmoj.isSelected()) {
//                faceInputView.setVisibility(View.GONE);
//                addEmoj.setSelected(false);
//                return true;
//            }
//
//            if (addImg.isSelected()) {
//                horizontalScrollView.setVisibility(View.GONE);
//                addImg.setSelected(false);
//                return true;
//            }
//        }
//        return onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != getActivity().RESULT_OK) {
//            return;
//        }
//        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
//            Uri uri = data.getData();
//            // tv_title.setText(Html.fromHtml("<img src='" + uri + "'/>"));
//            // richText.insertBitmap(mFileUtils.getFilePathFromUri(uri));
//        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA
//                && resultCode == getActivity().RESULT_OK) {
//
//            ImageCompressAndUpload.getInstance().execute(
//                    mCameraImageFile.getAbsolutePath(), new OnUploadlistener() {
//
//                        @Override
//                        public void OnSuccess(String resultData) {
//                            uploadedUrl.add(resultData);
//                            sendImageIdToServer(resultData,
//                                    mCameraImageFile.getAbsolutePath());
//
//                            try {
//
//                                closeProgressDialog();
//                            } catch (Exception e) {
//                                // TODO: handle exception
//                            }
//                            if (keFuId == null) {
//                                ByAlert.alert("客服不在线，请稍后重试...");
//                            } else
//                                imgId.append(resultData + ",");
//                            ByAlert.alert("图片发送成功！");
//                            // sendToServer();
//                        }
//
//                        @Override
//                        public void OnError(String resultData) {
//                            ByAlert.alert(resultData + "error");
//                            try {
//                                closeProgressDialog();
//
//                            } catch (Exception e) {
//                                // TODO: handle exception
//                            }
//                        }
//                    });
//            // richText.insertBitmap(mCameraImageFile.getAbsolutePath());
//            // adapter.addItemNotifiChange(new
//            // Bean(mCameraImageFile.getAbsolutePath(), R.drawable.me,
//            // new Date() + "", 0));
//        }
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (keFuId != null)
//            destroyQueue();
//
//        mFileUtils.deleteRichTextImage();
//        if (handler != null)
//            handler.removeCallbacks(runnable);
//    }
//
//    // /聊天退出
//    private void destroyQueue() {
//        // TODO Auto-generated method stub
//        MyJsonRequest.Builder<String> builder = new MyJsonRequest.Builder<>();
//        builder.apiVer("100")
//                .typeKey("BY_Customer_exitcommunion")
//                .param("uid",
//                        SPUtils.get(getActivity(), null, SPContants.USER_ID, "")
//                                + "").param("queueid", keFuId)
//                .param("create_time", lastCreateTime)
//                .requestListener(new XRequestListener<String>() {
//
//                    @Override
//                    public void onResponse(String arg0) {
//                        ByAlert.alert(arg0);
//                    }
//                }).errorListener(new XErrorListener() {
//
//            @Override
//            public void onErrorResponse(Exception exception, int code,
//                                        String msg) {
//                // TODO Auto-generated method stub
//                ByAlert.alert("客服请求失败！");
//            }
//        });
//        HttpRequest.getDefaultRequestQueue().add(builder.build());
//    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();

    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        pageNo++;
        getHistory();
    }


}
