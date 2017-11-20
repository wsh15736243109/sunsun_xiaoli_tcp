package sunsun.xiaoli.jiarebang.device.jinligang;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.TimeCount;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.custom.ClearEditText;


/**
 * Created by itboye on 2017/2/24.
 */

public class RegisterActivity extends BaseActivity implements Observer {

    ClearEditText cleMobile;//电话
    ClearEditText edtPassword;//登录密码
    //    ClearEditText clearUserName;//用户呢称
    ClearEditText cleYzm;//地址信息
    ClearEditText clearPhone;//联系电话
    private TextView btnOk, btnCancel, txt_getYzm;
    TextView txt_title;
    UserPresenter userPresenter;
    TimeCount timeCount;
    ImageView img_back;
    TextView btn_country;
    String country = "+86";
    private String[] strings;
    private AlertDialog.Builder alertDialog;
    private AlertDialog dialog;
    private ArrayList<Map<String, Object>> listItems;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txt_title.setText(getString(R.string.register));
        userPresenter = new UserPresenter(this);
        timeCount = new TimeCount(60000, 1000, txt_getYzm);// 构造CountDownTimer对象
    }

    String username = "";

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                if (cleMobile.getText().toString().equals("")) {
                    MAlert.alert(getString(R.string.phone_empty));
                    return;
                }
//                if (EmailUtil.emailValidation(cleMobile.getText().toString()) != true) {
//                    MAlert.alert("邮箱格式不对");
//                } else {
//                    MAlert.alert("输入邮箱地址正确");
//                }
                username = cleMobile.getText().toString();
                String password = edtPassword.getText().toString();
                String code = cleYzm.getText().toString();
                if (code.equals("")) {
                    MAlert.alert(getString(R.string.empty_code));
                    return;
                }
                userPresenter.registerByPhone(country, username, code, password);
                break;
            case R.id.btnCancel:
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_getYzm:
                username = cleMobile.getText().toString();
                if (username.equals("")) {
                    MAlert.alert(getString(R.string.phone_empty));
                    return;
                }
                userPresenter.sendVerificationCode(country, username, "1", 0);
                break;
            case R.id.btn_country:
                View codeView = View.inflate(this, R.layout.item_choose_code, null);
                dialog = new AlertDialog.Builder(this).setView(codeView).create();
                dialog.show();
                final EditText ed_place = (EditText) codeView.findViewById(R.id.edit_place);
                TextView btn_search = (TextView) codeView.findViewById(R.id.btn_search);
                listView = (ListView) codeView.findViewById(R.id.listView);
                strings = getResources().getStringArray(R.array.country_code_list);
                // 创建一个List集合，List集合的元素是Map
                listItems = new ArrayList<Map<String, Object>>();
                for (int i1 = 0; i1 < strings.length; i1++) {
                    String string = strings[i1];
                    Map<String, Object> listItem = new HashMap<String, Object>();
                    listItem.put("country", string);
                    listItems.add(listItem);
                }
                listView.setAdapter(new SimpleAdapter(this, listItems, android.R.layout.simple_list_item_1, new String[]{"country"}, new int[]{android.R.id.text1}));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        country = listItems.get(position).get("country").toString();
                        country = country.substring(country.indexOf("*") + 1, country.length());
                        btn_country.setText(country);
                        dialog.dismiss();
                    }
                });
                ed_place.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String content= ed_place.getText().toString();
                        listItems=beginSearch(content);
                        listView.setAdapter(new SimpleAdapter(RegisterActivity.this, listItems, android.R.layout.simple_list_item_1, new String[]{"country"}, new int[]{android.R.id.text1}));
                    }
                });
                btn_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       String content= ed_place.getText().toString();
                        listItems=beginSearch(content);
                        listView.setAdapter(new SimpleAdapter(RegisterActivity.this, listItems, android.R.layout.simple_list_item_1, new String[]{"country"}, new int[]{android.R.id.text1}));
                    }
                });
                break;
        }

    }

    private ArrayList<Map<String, Object>> beginSearch(String content) {
        listItems=new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].contains(content)) {
                Map<String, Object> listItem = new HashMap<String, Object>();
                listItem.put("country", strings[i]);
                listItems.add(listItem);
            }
        }
        return listItems;
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == UserPresenter.send_code_success) {
                MAlert.alert(entity.getData());
                timeCount.start();
            } else if (entity.getEventType() == UserPresenter.send_code_fail) {
                MAlert.alert(entity.getData());
            }
            if (entity.getEventType() == UserPresenter.registerByPhone_success) {
                MAlert.alert(entity.getData());
                finish();
            }
            if (entity.getEventType() == UserPresenter.registerByPhone_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
