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
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.EmptyUtil;
import com.itboye.pondteam.utils.TimeCount;
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

import static com.itboye.pondteam.utils.EmptyUtil.getSp;


public class ForgetPasswordActivity extends BaseActivity implements Observer {

    ImageView img_back;
    TextView txt_title, btn_ok, btn_cancel, btn_get_yzm;
    ClearEditText edit_make_sure_password, edit_username, edit_password, edit_yzm;
    UserPresenter userPresenter;
    private TimeCount time;// 倒计时
    TextView btn_country;
    String country="+86";
    private AlertDialog dialog;
    private ListView listView;
    private String[] strings;
    private ArrayList<Map<String, Object>> listItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        txt_title.setText(getString(R.string.forget_pass_title));
        userPresenter = new UserPresenter(this);
        if (BuildConfig.APP_TYPE.toLowerCase().equals("pondteam".toLowerCase())) {
            edit_username.setHint(getString(R.string.email));
            btn_country.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_ok:
                if (EmptyUtil.isEmpty(edit_username)) {
                    MAlert.alert(getString(R.string.username_empty));
                    return;
                }
                if (EmptyUtil.isEmpty(edit_yzm)) {
                    MAlert.alert(getString(R.string.yzm_empty));
                    return;
                }
                if (EmptyUtil.isEmpty(edit_password)) {
                    MAlert.alert(getString(R.string.pass_empty));
                    return;
                }
                if (!EmptyUtil.getCustomText(edit_password).equals(EmptyUtil.getCustomText(edit_make_sure_password))) {
                    MAlert.alert(getString(R.string.different_password));
                    return;
                }
                showProgressDialog(getString(R.string.posting), true);
                if (BuildConfig.APP_TYPE.toLowerCase().equals("pondteam".toLowerCase())) {
                    userPresenter.updatePassByEmail(getSp(Const.S_ID),EmptyUtil.getCustomText(edit_yzm), EmptyUtil.getCustomText(edit_username), EmptyUtil.getCustomText(edit_password));
                }else{
                    userPresenter.updatePassByPhone(getSp(Const.S_ID),country, EmptyUtil.getCustomText(edit_yzm), EmptyUtil.getCustomText(edit_username), EmptyUtil.getCustomText(edit_password));
                }
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_get_yzm:
                if (EmptyUtil.isEmpty(edit_username)) {
                    MAlert.alert(getString(R.string.username_empty));
                    return;
                }
                if (BuildConfig.APP_TYPE.toLowerCase().equals("pondteam".toLowerCase())) {
                    userPresenter.sendEmailCode(EmptyUtil.getCustomText(edit_username), 2, 2);
                }else {
                    userPresenter.sendVerificationCode(country, EmptyUtil.getCustomText(edit_username), "2", 0);
                }
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
                        listView.setAdapter(new SimpleAdapter(ForgetPasswordActivity.this, listItems, android.R.layout.simple_list_item_1, new String[]{"country"}, new int[]{android.R.id.text1}));
                    }
                });
                btn_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String content= ed_place.getText().toString();
                        listItems=beginSearch(content);
                        listView.setAdapter(new SimpleAdapter(ForgetPasswordActivity.this, listItems, android.R.layout.simple_list_item_1, new String[]{"country"}, new int[]{android.R.id.text1}));
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
        try {
            closeProgressDialog();
        } catch (Exception e) {

        }
        ResultEntity resultEntity = handlerError(data);
        if (resultEntity != null) {
            if (resultEntity.getCode() != 0) {
                MAlert.alert(resultEntity.getMsg());
                return;
            }
            if (resultEntity.getEventType() == UserPresenter.update_pass_bymobile_success) {
                MAlert.alert(resultEntity.getData());
                finish();
            } else if (resultEntity.getEventType() == UserPresenter.update_pass_bymobile_fail) {
                MAlert.alert(resultEntity.getData());
            } else if (resultEntity.getEventType() == UserPresenter.send_code_success) {
                MAlert.alert(resultEntity.getData());
                time = new TimeCount(60000, 1000, btn_get_yzm);// 构造CountDownTimer对象
                time.start();
            } else if (resultEntity.getEventType() == UserPresenter.send_code_fail) {
                MAlert.alert(resultEntity.getData());
            }else if (resultEntity.getEventType() == UserPresenter.updata_pass_success) {
                MAlert.alert(resultEntity.getData());
                finish();
            } else if (resultEntity.getEventType() == UserPresenter.updata_pass_fail) {
                MAlert.alert(resultEntity.getData());
            }
        }
    }
}
