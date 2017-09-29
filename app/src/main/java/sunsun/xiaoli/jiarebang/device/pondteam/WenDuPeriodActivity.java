package sunsun.xiaoli.jiarebang.device.pondteam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itboye.pondteam.adapter.PeriodAdapter;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.PeriodModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sunsun.xiaoli.jiarebang.R;

public class WenDuPeriodActivity extends BaseActivity {

    ListView list;
    ArrayList<PeriodModel> ar = new ArrayList<>();
    PeriodAdapter adapter;
    TextView txt_title;
    String weekSet="";
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wen_du_period);
        txt_title.setText(getString(R.string.wendushezhi));
        weekSet=getIntent().getStringExtra("zhouqi");
        String[] weeks=weekSet.split("、");

        List<String> arWeek = Arrays.asList(getResources().getStringArray(R.array.WheelArrayWeek));
        for (String s : arWeek) {
            PeriodModel per = new PeriodModel();
            if (Arrays.asList(weeks).contains(s)) {
                per.setSelect(true);
            }else{
                per.setSelect(false);
            }
            per.setWeek(s);
            ar.add(per);
        }
        adapter = new PeriodAdapter(this, ar);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ar.get(position).setSelect(!ar.get(position).isSelect());
                adapter.notifyDataSetChanged();
            }
        });
        list.addFooterView(new View(this));//添加ListView footer
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.img_back) {
            saveOrNotSave();

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                saveOrNotSave();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void saveOrNotSave() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.tips));
        alert.setMessage(getString(R.string.is_save));
        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Collections.reverse(ar);
                int num = 0;
                for (int i = 0; i < ar.size(); i++) {
                    PeriodModel periodModel = ar.get(i);
                    if (periodModel.isSelect()) {
                        num += Math.pow(2, i);
                    } else {
//                        num += Math.pow(2, i);
                    }

                }
                Intent intent = new Intent();
                intent.putExtra("weekBinary",num);
                setResult(102, intent);
                finish();
            }
        });
        alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.create();
        alert.show();
    }
}
