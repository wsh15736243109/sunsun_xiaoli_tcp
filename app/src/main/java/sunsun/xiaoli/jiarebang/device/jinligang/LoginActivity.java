package sunsun.xiaoli.jiarebang.device.jinligang;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.PersonDataBean;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.BuildConfig;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.custom.ClearEditText;
import sunsun.xiaoli.jiarebang.device.DeviceActivity;
import sunsun.xiaoli.jiarebang.device.pondteam.PondTeamRegisterActivity;
import sunsun.xiaoli.jiarebang.logincontroller.LoginController;
import sunsun.xiaoli.jiarebang.logincontroller.LoginedState;


/**
 * Created by itboye on 2017/2/24.
 */

public class LoginActivity extends BaseActivity implements Observer {
    private TextView btn_login, btn_register;//denglu
    String email, password;
    ClearEditText editextUsetName, editextPassword;
    String userName = "";
    String userPass = "";
    UserPresenter userPresenter;
    TextView txt_forget_pass, title_login, btn_country;
    TextView bottom_icon;
    String country = "+86";
    private AlertDialog dialog;
    private ListView listView;
    private String[] strings;
    private ArrayList<Map<String, Object>> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userPresenter = new UserPresenter(this);
        String appType = BuildConfig.APP_TYPE;
        if (appType.toLowerCase().contains("pondteam".toLowerCase())) {
            title_login.setText("PondLinkByPondteam");
            bottom_icon.setVisibility(View.VISIBLE);
            btn_country.setVisibility(View.GONE);
            editextUsetName.setHint(getString(R.string.email));
        } else {
            title_login.setText(getString(R.string.login_sunsun));
            btn_country.setVisibility(View.VISIBLE);
            editextUsetName.setHint(getString(R.string.user_name));
            bottom_icon.setVisibility(View.GONE);
        }
        if (getPackageName().contains("pondlink")){
            title_login.setText("PondLinkByPondteam");
            bottom_icon.setVisibility(View.VISIBLE);
            bottom_icon.setBackgroundColor(getResources().getColor(R.color.login_color));
            bottom_icon.setText("pondLink");
            bottom_icon.setTextSize(20);
        }else if (getPackageName().contains("xiaomianyang")){
            title_login.setText(getString(R.string.login_yihu));
            btn_country.setVisibility(View.VISIBLE);
            editextUsetName.setHint(getString(R.string.user_name));
            bottom_icon.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        userName = editextUsetName.getText().toString();
        userPass = editextPassword.getText().toString();
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_register:
                if (BuildConfig.APP_TYPE.equals("pondTeam")) {
                    intent = new Intent(LoginActivity.this, PondTeamRegisterActivity.class);
                } else {
                    intent = new Intent(LoginActivity.this, RegisterActivity.class);
                }
                startActivity(intent);
//                onUIAlertView(view);
                break;
            case R.id.btn_login:
                if (userName.equals("") || userName.equals("")) {
                    MAlert.alert(getString(R.string.username_empty));
                    return;
                }
                showProgressDialog(getString(R.string.requesting),true);
                userPresenter.login(country, userName, userPass, "");
//                intent = new Intent(LoginActivity.this, AddPondDevice.class);
//                startActivity(intent);
//                onShowDlog();
                break;
            case R.id.txt_forget_pass:
                intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
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
                        listView.setAdapter(new SimpleAdapter(LoginActivity.this, listItems, android.R.layout.simple_list_item_1, new String[]{"country"}, new int[]{android.R.id.text1}));
                    }
                });
                btn_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String content= ed_place.getText().toString();
                        listItems=beginSearch(content);
                        listView.setAdapter(new SimpleAdapter(LoginActivity.this, listItems, android.R.layout.simple_list_item_1, new String[]{"country"}, new int[]{android.R.id.text1}));
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
        ResultEntity resultEntity = handlerError(data);
        try {
            closeProgressDialog();
        }catch (Exception e){

        }
        if (resultEntity != null) {
            if (resultEntity.getCode() != 0) {
                MAlert.alert(resultEntity.getMsg());
                return;
            }
            if (resultEntity.getEventType() == UserPresenter.login_success) {
                PersonDataBean personDataBean = (PersonDataBean) resultEntity.getData();
                if (personDataBean != null) {
                    setMyData(personDataBean);
                    MAlert.alert(getString(R.string.login_success));
                    Intent intent = new Intent(this, DeviceActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else if (resultEntity.getEventType() == UserPresenter.login_fail) {
                MAlert.alert(resultEntity.getData());
            }

        }
    }

    public void setMyData(PersonDataBean bean) {
        SPUtils.put(LoginActivity.this, null,
                Const.UID, bean.getId());
        SPUtils.put(LoginActivity.this, null,
                Const.PaySecret, bean.getPaySecret());
        SPUtils.put(LoginActivity.this, null,
                Const.RELE, "6");
        if (bean.getIs_stores()!=null) {
            SPUtils.put(LoginActivity.this, null,
                    Const.IS_STORE, bean.getIs_stores());
        }
        SPUtils.put(LoginActivity.this, null,
                Const.EMAIL, bean.getEmail());
        SPUtils.put(LoginActivity.this, null,
                Const.USERNAME, bean.getUsername());
        SPUtils.put(LoginActivity.this, null,
                Const.PASSWORD, bean.getPassword());
        SPUtils.put(LoginActivity.this, null,
                Const.MOBILE, bean.getMobile());
        SPUtils.put(LoginActivity.this, null,
                Const.IS_LOGINED, true);
        SPUtils.put(LoginActivity.this, null,
                Const.HEAD, bean.getHead());
        SPUtils.put(LoginActivity.this, null,
                Const.NICK, bean.getNickname());
//        SPUtils.put(LoginActivity.this, null,
//                Const.ISAUTH, bean.getRolesInfo().get(0).getIsAuth());
        SPUtils.put(LoginActivity.this, null,
                Const.S_ID, bean.getAutoLoginCode());
        LoginController.setLoginState(new LoginedState());
    }
}
