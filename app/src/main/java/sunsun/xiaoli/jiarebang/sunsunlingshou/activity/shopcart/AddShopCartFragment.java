package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.shopcart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itboye.pondteam.base.BaseDialogFragment;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.DensityUtil;
import com.itboye.pondteam.volley.ResultEntity;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.GoodsDetailBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.BuyType;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.GlidHelper;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.FlowLayout;
import sunsun.xiaoli.jiarebang.utils.XGlideLoader;

import static com.itboye.pondteam.base.BaseActivity.EVENT_TYPE_UNKNOWN;

/**
 * Created by Administrator on 2017/7/29.
 */

public class AddShopCartFragment extends BaseDialogFragment {
    View close;
    ImageView pic;
    TextView txt_price;
    TextView txt_fenlei;
    TextView kucun;
    ViewGroup guigeContainer;
    TextView ok, ok_liji;
    GoodsDetailBean goodsDetailBeans;
    View jian;
    View jia;
    EditText input;
    boolean isLiJIGouMai;
    ViewGroup container;
    String imgUrl;
    protected String price;
    protected String skuId;
    private boolean isxuokanzecanshu;
    private String uid;
    private String store_id;

    public AddShopCartFragment() {

    }

    public void setIsxuanzecanshu(boolean isxuokanzecanshu) {
        this.isxuokanzecanshu = isxuokanzecanshu;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    //        rgba(255, 174, 110, 1)
    @SuppressLint("ValidFragment")
    public AddShopCartFragment(GoodsDetailBean goodsDetailBeans,
                               boolean isLiJIGouMai) {
        this.goodsDetailBeans = goodsDetailBeans;
        this.isLiJIGouMai = isLiJIGouMai;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return LayoutInflater.from(getContext()).inflate(
                R.layout.dialogfragment_jiarugouwuche, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            goodsDetailBeans = (GoodsDetailBean) savedInstanceState
                    .getSerializable("goodsDetailBeans");
            isLiJIGouMai = savedInstanceState.getBoolean("isLiJIGouMai");
        }
        uid = SPUtils.get(getActivity(), null, Const.UID, "") + "";
        imgUrl = goodsDetailBeans.getMain_img();
        GlidHelper.glidLoad(pic, Const.imgurl + imgUrl);
        setPrice(goodsDetailBeans.getSku_list().get(0).getOri_price());
        addGuiGe();
        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = input.getText().toString();
                try {
                    int c = Integer.parseInt(s);
                    input.setText(c + 1 + "");
                } catch (NumberFormatException e) {
                    input.setText("1");
                }
                input.setSelection(input.getText().length());
            }
        });
        jian.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String s = input.getText().toString();
                try {
                    int c = Integer.parseInt(s);
                    if (c > 0) {
                        c--;
                    }
                    input.setText(c + "");
                } catch (NumberFormatException e) {
                    input.setText("0");
                }
                input.setSelection(input.getText().length());
            }
        });

        close.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                close();
            }
        });
        ok_liji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = checkGuiGeSelectedState();
                if (s == null) {
                    return;
                }
                String tString = kucun.getTag().toString();
                String string = input.getText().toString();
                System.out.println(tString + "tString" + string);
                if (Integer.parseInt(tString) < Integer
                        .parseInt(string)) {
                    Toast.makeText(getContext(), "库存不足", Toast.LENGTH_SHORT).show();
                    return;
                }
                int skuPid = 0;
                int position = -1;
                List<GoodsDetailBean.SkuListEntity> sku_list = goodsDetailBeans.getSku_list();
                GoodsDetailBean.SkuListEntity skuListEntity = new GoodsDetailBean.SkuListEntity();
                for (int i = 0; i < sku_list.size(); i++) {
                    skuListEntity = sku_list.get(i);
                    if (skuListEntity.getSku_id().equals(s)) {
                        skuListEntity.getPrice();
                        skuPid = Integer.valueOf(skuListEntity.getSku_pkid());
                        position = i;
                        break;
                    }
                }
                if (skuPid == 0) {
                    MAlert.alert("sku_pid有误");
                    return;
                }
                goodsDetailBeans.setSelectPositon(position);
                goodsDetailBeans.setCount(Integer.valueOf(string));
                goodsDetailBeans.setPrice(Double.parseDouble(skuListEntity.getPrice()));
                ArrayList<GoodsDetailBean> ar = new ArrayList<GoodsDetailBean>();
                ar.add(goodsDetailBeans);
                Intent intent = new Intent(getActivity(), MakeSureOrderActivity.class);
                intent.putExtra("skuPid", skuPid);
                intent.putExtra("isLiJiBuy", true);
                intent.putExtra("isGoodsBuy", true);
                intent.putExtra("type", BuyType.Buy_LiJiGouMai);
                intent.putExtra("model", ar);
                intent.putExtra("store_id", store_id);
                intent.putExtra("canPack", 0);
                startActivity(intent);
                dismiss();
            }
        });
        pic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (imgUrl != null) {
                    // ImageViewerDialog imageViewerDialog = new
                    // ImageViewerDialog(
                    // getActivity());
                    // String urls[] = new String[] { imgUrl };
                    // imageViewerDialog.setImageUrls(urls);
                    // imageViewerDialog.show(0);
                }

            }
        });
    }

    public ResultEntity handlerError(Object data) {
        ResultEntity result = (ResultEntity) data;
        if (result == null) {
            result = new ResultEntity(-1, "数据格式错误!~", data);
            return result;
        }

        if (result.hasError()) {
            MAlert.alert(result.getMsg());
            result.setEventType(EVENT_TYPE_UNKNOWN);
            return result;
        }
        return result;
    }

    protected String checkGuiGeSelectedState() {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < guigeContainer.getChildCount(); i++) {
            View vv = guigeContainer.getChildAt(i);
            if (!(vv instanceof FlowLayout)) {
                continue;
            }
            FlowLayout flowLayout = (FlowLayout) vv;
            int j;
            for (j = 0; j < flowLayout.getChildCount(); j++) {
                TextView textView = (TextView) flowLayout.getChildAt(j);
                if (textView.isSelected()) {
                    sb.append(textView.getTag() + ";");
                    break;
                }
            }
            if (j == flowLayout.getChildCount()) {
//               MAlert.alert("请选择"
//                                + goodsDetailBeans.getSku_info().get(i / 2)
//                                .getId(), Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        return sb.toString();
    }

    private void addGuiGe() {

        List<GoodsDetailBean.SkuInfoEntity> list = goodsDetailBeans
                .getSku_info();
        for (int i = 0; i < list.size(); i++) {
            GoodsDetailBean.SkuInfoEntity skuBean = list.get(i);
            TextView title = createTitle(skuBean.getSku_name());
            guigeContainer.addView(title);

            FlowLayout flowLayout = createFlowLayout(skuBean.getValue_list(),
                    skuBean.getSku_id() + "",
                    skuBean.getValue_list());
            guigeContainer.addView(flowLayout);
        }
        container.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @SuppressWarnings("deprecation")
                    @SuppressLint("NewApi")
                    @Override
                    public void onGlobalLayout() {
                        try {
                            container.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        } catch (Throwable e) {
                        }
                        if (container.getHeight() > DensityUtil
                                .screenHeigh() * 0.7) {

                            int h = DensityUtil.screenHeigh()
                                    - DensityUtil.dip2px(280);
                            ScrollView scrollView = new ScrollView(
                                    getContext());
                            scrollView.setLayoutParams(new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT, h));

                            ViewGroup vg = (ViewGroup) guigeContainer
                                    .getParent();
                            vg.removeView(guigeContainer);

                            scrollView.addView(guigeContainer);
                            vg.addView(scrollView);
                        }
                    }
                });
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            try {
                kucun.setText("");
                kucun.setTag("0");
                txt_price.setText("");
                txt_fenlei.setText("");
                if (v.isSelected()) {
                    v.setSelected(false);
                    ((TextView) v).setTextColor(Color.BLACK);
                    imgUrl = goodsDetailBeans.getMain_img();
                    if ((!goodsDetailBeans.getMain_img().isEmpty())
                            & goodsDetailBeans.getMain_img() != null) {
                        XGlideLoader.displayImage(getActivity(), Const.imgurl + goodsDetailBeans.getMain_img(), pic);
                    }
                    return;
                }
                FlowLayout clickTextViewParent = null;
                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < guigeContainer.getChildCount(); i++) {
                    View view = guigeContainer.getChildAt(i);
                    if (!(view instanceof FlowLayout)) {
                        continue;
                    }
                    FlowLayout flowLayout = (FlowLayout) view;
                    if (v.getParent() == flowLayout) {
                        clickTextViewParent = flowLayout;
                        stringBuilder.append(v.getTag());
                        stringBuilder.append(";");

                    } else {
                        for (int j = 0; j < flowLayout.getChildCount(); j++) {
                            View textview = flowLayout.getChildAt(j);
                            if (textview.isSelected()) {
                                stringBuilder.append(textview.getTag());
                                stringBuilder.append(";");
                                break;
                            }
                        }
                    }
                }
                List<GoodsDetailBean.SkuListEntity> skuList = goodsDetailBeans
                        .getSku_list();
                String selectedSku = stringBuilder.toString();
                for (GoodsDetailBean.SkuListEntity skuCombinationBean : skuList) {
                    String sku = skuCombinationBean.getSku_id();

                    if (sku.contains(selectedSku)) {
                        for (int j = 0; j < clickTextViewParent
                                .getChildCount(); j++) {
                            if (clickTextViewParent.getChildAt(j)
                                    .isSelected()) {
                                clickTextViewParent.getChildAt(j)
                                        .setSelected(false);
                                ((TextView) clickTextViewParent
                                        .getChildAt(j))
                                        .setTextColor(Color.BLACK);
                                break;
                            }
                        }
                        v.setSelected(true);
                        ((TextView) v).setTextColor(Color.WHITE);
                        break;
                    }
                }
                String s = isAllFenLeiSelected();
                if (s != null) {
                    ((View) kucun.getParent()).setVisibility(View.VISIBLE);
                    List<GoodsDetailBean.SkuListEntity> list = goodsDetailBeans
                            .getSku_list();
                    for (GoodsDetailBean.SkuListEntity skuCombinationBean : list) {
                        if (skuCombinationBean.getSku_id().equals(s)) {
                            kucun.setText("库存" + skuCombinationBean.getQuantity()
                                    + "件");
                            kucun.setTag(skuCombinationBean.getQuantity() + "");
                            price = skuCombinationBean.getPrice();
//                            if (!skuCombinationBean.getOri_price()
//                                    .equals("0.00")
//                                    && !skuCombinationBean.getMemberPrice()
//                                    .equals("")) {
//                                price = skuCombinationBean.getMemberPrice();
//                                jiege.setText("￥"
//                                        + skuCombinationBean
//                                        .getMemberPrice());
//                                jiege2.setText(skuCombinationBean.getOriPrice());
//                            } else {
//                                jiege.setText("￥"
//                                        + skuCombinationBean
//                                        .getMemberPrice());
//                                price = skuCombinationBean.getPrice();
//                                jiege2.setText("￥"
//                                        + skuCombinationBean.getPrice());
//                            }
                            skuId = skuCombinationBean.getSku_id();
//                            if (TextUtils.isEmpty(skuCombinationBean
//                                    .getIcon_url())) {
//                                XImageLoader.load(GlobalConfig.API_URL
//                                                + "/picture/index?id="
//                                                + goodsDetailBeans.getIcon_url(),
//                                        pic);
//                                imgUrl = goodsDetailBeans.getMainImg();
//                            } else {
//                                XImageLoader.load(GlobalConfig.API_URL
//                                                + "/picture/index?id="
//                                                + skuCombinationBean.getIconUrl(),
//                                        pic);
//                                imgUrl = skuCombinationBean.getIconUrl();
//                            }
                            txt_fenlei.setText(skuCombinationBean.getSku_desc());
                            setPrice(skuCombinationBean.getPrice());
                            break;
                        }
                    }
                } else {
//                    ((View) kucun.getParent())
//                            .setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void setPrice(String price) {
        txt_price.setText("￥" + Double.parseDouble(price) / 100);
    }

    private String isAllFenLeiSelected() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < guigeContainer.getChildCount(); i++) {
            View vv = guigeContainer.getChildAt(i);
            if (!(vv instanceof FlowLayout)) {
                continue;
            }
            FlowLayout flowLayout = (FlowLayout) vv;
            int j;
            for (j = 0; j < flowLayout.getChildCount(); j++) {
                TextView textView = (TextView) flowLayout.getChildAt(j);
                if (textView.isSelected()) {
                    sb.append(textView.getTag() + ";");
                    break;
                }
            }
            if (j == flowLayout.getChildCount()) {
                return null;
            }
        }
        return sb.toString();
    }

    /**
     * 当前已选中的规格集合
     *
     * @return
     */
    protected List<String> selectedGuige() {

        List<String> selectedGuige = new ArrayList<String>();
        for (int i = 0; i < guigeContainer.getChildCount(); i++) {
            View vv = guigeContainer.getChildAt(i);
            if (!(vv instanceof FlowLayout)) {
                continue;
            }
            FlowLayout flowLayout = (FlowLayout) vv;
            for (int j = 0; j < flowLayout.getChildCount(); j++) {
                TextView textView = (TextView) flowLayout.getChildAt(j);
                if (textView.isSelected()) {
                    selectedGuige.add((String) textView.getTag());
                }
            }
        }
        return selectedGuige;
    }

    private FlowLayout createFlowLayout(List<GoodsDetailBean.SkuInfoEntity.ValueListEntity> vid, String id,
                                        List<GoodsDetailBean.SkuInfoEntity.ValueListEntity> vids) {
        int dis = DensityUtil.dip2px(5);
        FlowLayout flowLayout = new FlowLayout(getContext());
        for (int i = 0; i < vid.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundResource(R.drawable.selector_guige);
            textView.setText(vid.get(i).getValue_name());
            textView.setTag(id + ":" + vids.get(i).getValue_id());
            textView.setOnClickListener(clickListener);
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            marginLayoutParams.setMargins(dis, dis, dis, dis);
            textView.setLayoutParams(marginLayoutParams);
            flowLayout.addView(textView);
        }
        return flowLayout;
    }

    /**
     * 设置套餐一、套餐二等标题栏
     *
     * @param s
     * @return
     */
    private TextView createTitle(String s) {
        TextView textView = new TextView(getContext());
        textView.setText(s);
        textView.setTextColor(Color.BLACK);
        return textView;
    }

    public void close() {
        View v = getView().findViewById(R.id.container);
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationY",
                0, v.getHeight()).setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator arg0) {
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                dismiss();
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                dismiss();
            }
        });
        animator.start();
    }

    @Override
    public void onSaveInstanceState(Bundle arg0) {
        super.onSaveInstanceState(arg0);
        // arg0.putSerializable("goodsDetailBeans", goodsDetailBeans);
        // arg0.putBoolean("isLiJIGouMai", isLiJIGouMai);
    }

}

