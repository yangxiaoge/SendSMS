package com.example.haha.sendsms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class MainActivity extends AppCompatActivity {
    private Button getSMSBtn;

    private String APPKEY = "ed06cfaeaab8";
    private String APPSECRET = "bd2d15419e704a768bbe9823cc1d823c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SMSSDK.initSDK(this, APPKEY, APPSECRET);

        getSMSBtn = (Button) findViewById(R.id.getsms);
        getSMSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    @Override
                    public void afterEvent(int event, int result, Object data) {
                        if (event == SMSSDK.RESULT_COMPLETE) {
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");

                            //可以不用这个方法
                            commitUserInfo(country, phone);
                        }
                    }
                });
                registerPage.show(MainActivity.this);
            }
        });
    }

    /**
     * 提交用户信息
     *
     * @param country <br>
     * @param phone   <br>
     */
    private void commitUserInfo(String country, String phone) {
        Random rd = new Random();
        String uid = Math.abs(rd.nextInt()) + "";
        String nickName = "珠珠";
        SMSSDK.submitUserInfo(uid, nickName, null, country, phone);
    }
}
