package sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.bean.ChatBean;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.utils.XGlideLoader;

public class CustomAdapter extends BaseAdapter {

    private ArrayList<ChatBean.ChatItem> beans = null;
    private LayoutInflater li;
    private final int ITEM_TYPES = 2, TYPE_1 = 1, TYPE_2 = 2;
    private Activity activity;
    Spanned phone;
    public static ArrayList list;

    Context context;

    public CustomAdapter(Context context, ArrayList<ChatBean.ChatItem> beans, Activity activity) {
        // TODO Auto-generated constructor stub
        this.beans = beans;
        li = LayoutInflater.from(context);
        this.context = context;
        this.activity = activity;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (beans == null) {
            return 0;
        }
        return beans.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        if (beans == null) {
            return 0;
        }
        return beans.size();
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return ITEM_TYPES;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        if (beans != null) {
            int tp = Integer.parseInt(beans.get(position).getOwnerType());// 1：客服//2：我
            System.out.println("ownerType-----" + tp);
            if (TYPE_1 == tp)
                return 0;
            else if (TYPE_2 == tp)
                return 1;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ChatBean.ChatItem bean = new ChatBean.ChatItem();
        PeopleView s = null;
        if (beans != null) {
            bean = beans.get(position);
        }
        int type = getItemViewType(position);

        if (null == convertView) {
            s = new PeopleView();

            switch (type) {
                case 0:
                    convertView = li.inflate(R.layout.listview_item_teacher, null);
                    break;
                case 1:
                    convertView = li.inflate(R.layout.listview_item_student, null);
                    break;
            }
            phone = bean.getMsgContent();

            s.time = (TextView) convertView.findViewById(R.id.Time);
            s.message = (TextView) convertView.findViewById(R.id.Msg);
            s.portrait = (ImageView) convertView.findViewById(R.id.Img);
            s.img = (ImageView) convertView.findViewById(R.id.Imgview);
            convertView.setTag(s);
        } else
            s = (PeopleView) convertView.getTag();
        if (beans != null) {
            try {
                s.time.setText((beans.get(position).getCreateTime()));

            } catch (Exception e) {
                // TODO: handle exception
            }
            // SpannableString spannableString =
            // FaceConversionUtil.getInstace().getExpressionString(App.ctx,
            // bean.gettMessage());


            String uri = (String) SPUtils.get(context, null, Const.KEFUHEAD,
                    "");
            String youhead = (String) SPUtils.get(context, null, Const.HEAD,
                    "");


            if (type == 0) {
                XGlideLoader.displayImageCircularForUser(context, youhead, s.portrait);
                System.out.println("<><><><><<<<<<<<<<<<<<<<<<<<<\t0" + uri + "<><><><><RERE" + youhead);
            } else {
                XGlideLoader.displayImageCircularForUser(context, uri, s.portrait);
                System.out.println("<><><><><<<<<<<<<<<<<<<<<<<<<\t1" + youhead + "<><><><>" + uri);
            }
            if (beans.get(position).getMsgType().equals("1")) {// 文字
                s.img.setVisibility(View.GONE);
                s.message.setVisibility(View.VISIBLE);
                s.message.setText(bean.getMsgContent());
                list = new ArrayList();
                //如果电话号码多的话，则要建立一个数组
//                list = UtilPhoneNum.getNumber2(list, phone);
//                if (list.size() <= 0 || list == null) {
//
//                } else {
//                    s.message.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//
//                            cameraPopupWindow = new SchoolReportFragment(
//                                    activity, onclicklister, phone);
//                            cameraPopupWindow.showAtLocation(v, Gravity.BOTTOM
//                                    | Gravity.CENTER_HORIZONTAL, 0, 0);
//
//
//                        }
//                    });
//                }
//				s.portrait.setImageBitmap(null);


            } else {// 图片
//				if()
                s.img.setVisibility(View.VISIBLE);
                s.message.setVisibility(View.GONE);

                try {
                    Integer.parseInt(beans.get(position).getMsgContent().toString());
                    XGlideLoader.displayImage(context, Const.imgSunsunUrl + beans.get(position).getMsgContent() + "",


                            s.img);
                } catch (Exception e) {
                    // TODO: handle exception
                    XGlideLoader.displayImage(context, "file://" + beans.get(position).getMsgContent() + "",
                            s.img);
                }
            }


        }
        // if(bean.getBitmap()==null){
        // s.img.setVisibility(View.GONE);
        // }else {
        // s.img.setVisibility(View.VISIBLE);
        // }

        return convertView;

    }

    class PeopleView {
        TextView time;
        TextView message;
        ImageView portrait;
        ImageView img;

    }


    public void AddItemImage(String url) {

    }

    public static boolean isWholeNumber(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    private static boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }

}
