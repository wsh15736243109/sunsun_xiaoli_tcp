package com.itboye.pondteam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    void request(){
        RequestQueue queue=Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap<String, String>();
        params.put("fromUser", "lesliefang");
        params.put("toUser", "xiaojun");

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, Const.URL,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                MAlert.alert(jsonobj+"成功");

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                MAlert.alert(error.getCause().getMessage()+"错误");
            }
        });
        queue.add(newMissRequest);
    }

    public void testRe(View v){
        request();
    }
}
