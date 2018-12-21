package com.example.yyl.myapplication0.recylerview;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.yyl.myapplication0.R;
import java.util.ArrayList;
import java.util.List;


public class recycle_mainActivity extends AppCompatActivity {

    private List<Item> personList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//继承父类
        setContentView(R.layout.recycler_activity);//设置界面资源
        initPersons();//初始化数据
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        itemsAdapter adapter = new itemsAdapter(personList);
        recyclerView.setAdapter(adapter);
    }

    private void initPersons(){
        for (int i = 0;i < 6;i++){
            Item p1 = new Item("p1",R.drawable.ic_icon_archary);
            personList.add(p1);

            Item p2 = new Item("p2",R.drawable.ic_icon_volleyball);
            personList.add(p2);

            Item p3 = new Item("p3",R.drawable.ic_icon_football);
            personList.add(p3);

        }
    }
}

/**
 * 微博认证授权回调类。
 * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
 * 该回调才会被执行。
 * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
 * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
 */
class AuthListener implements WeiboAuthListener {

    @Override
    public void onComplete(Bundle bundle) {
//            System.out.println("onComplete(Bundle values)  ------>  "
//                    + bundle.toString());
        // onComplete(Bundle values) ------>
        // Bundle[ {_weibo_transaction = 1469413517894,
        // access_token = 2.00a64JBGyY87OCfa7707a82fzincGB,
        // refresh_token = 2.00a64JBGyY87OC11c02519480EWT1g,
        // expires_in = 2651682,
        // _weibo_appPackage = com.sina.weibo,
        // com.sina.weibo.intent.extra.NICK_NAME = 用户5513808278,
        // userName = 用户5513808278,
        // uid = 5513808278,
        // com.sina.weibo.intent.extra.USER_ICON = null} ]


        //从Bundle中解析Token
        mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
//            System.out.println("onComplete  mAccessToken  ------>  "
//                    + mAccessToken.toString());
        // onComplete mAccessToken ------>
        // uid: 5513808278,
        // access_token: 2.00a64JBGyY87OCfa7707a82fzincGB,
        // refresh_token: 2.00a64JBGyY87OC11c02519480EWT1g,
        // phone_num: ,
        // expires_in: 1472065200534

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
//            String phoneNum = mAccessToken.getPhoneNum();//通过手机短信授权登录时可以拿到，此demo未实现此种授权方式
    }

    @Override
    public void onWeiboException(WeiboException e) {
        Toast.makeText(MainActivity.this, "授权异常", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(MainActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
    }
}