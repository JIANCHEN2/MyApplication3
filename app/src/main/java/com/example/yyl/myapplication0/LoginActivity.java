package com.example.yyl.myapplication0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText username;
    private EditText password;
    private Button login;
    private Button register;
    private Button web;
    private String infoString;//服务器返回的数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        //初始化信息
        username = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);
        login = (Button)findViewById(R.id.button01);
        register = (Button)findViewById(R.id.button02);
        web=(Button)findViewById(R.id.button03);

        //设置按钮监听器
        login.setOnClickListener(this);
        web.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button01:
                //设置提示框
                Toast.makeText(LoginActivity.this,"正在登陆",Toast.LENGTH_SHORT).show();
                //设置子线程，分别进行Get和Post传输数据
                new Thread(new MyThread()).start();
                break;
            case R.id.button02:
                //跳转注册页面
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.button03:
                mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);

                if (mAccessToken.isSessionValid()) {//授权成功
                    Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    //显示Access_Token
                    tvAccessToken.setText("Access_token:\n" + mAccessToken.getToken());
                    //获取用户具体信息
//                getUserInfo();
                } else {
                    /**
                     *  以下几种情况，您会收到 Code：
                     * 1. 当您未在平台上注册应用程序的包名与签名时；
                     * 2. 当您注册的应用程序包名与签名不正确时；
                     * 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                     */
                    String code = bundle.getString("code");//直接从bundle里边获取
                    if (!TextUtils.isEmpty(code)) {
                        Toast.makeText(MainActivity.this, "签名不正确", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
//            String phoneNum = mAccessToken.getPhoneNum();//通过手机短信授权登录时可以拿到，此demo未实现此种授权方式
        }

    }
    }

    public class MyThread implements Runnable{
        @Override
        public void run() {
            infoString = WebServiceGet.executeHttpGet(username.getText().toString(),password.getText().toString(),"ServLogin");//获取服务器返回的数据

            //更新UI，使用runOnUiThread()方法
            showResponse(infoString);
        }
    }
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            //更新UI
            @Override
            public void run() {
                if(response.equals("false")){
                    Toast.makeText(LoginActivity.this,"登陆失败！", Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(LoginActivity.this,"登陆成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,FrontActivity.class);
                    //上下文，目标活动
                    startActivity(intent);//执行Intent
                }
            }
        });
    }


}
