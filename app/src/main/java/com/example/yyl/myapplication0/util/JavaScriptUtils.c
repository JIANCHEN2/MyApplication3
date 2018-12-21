//
// Created by ASUS on 2018-12-21.
//
public static final int WEIBO_LOGIN = 6009;
public static final int WEIBO_SHARE = 6010;
/**
 * 微博登录
 */
@JavascriptInterface
public void wbLogin () {
    MainActivity.mHandle.sendEmptyMessage(WEIBO_LOGIN);
}

/**
     * 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
     */
    private SsoHandler mSsoHandler;

    private AuthInfo mAuthInfo;

    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能，用于在回调方法中接收授权成功后返回的信息
     */
    private Oauth2AccessToken mAccessToken;
    // 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
    mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
    mSsoHandler = new SsoHandler(this, mAuthInfo);
/**
 * 微博分享
 */
@JavascriptInterface
public void wbShare(){
    MainActivity.mHandle.sendEmptyMessage(WEIBO_SHARE);
}

// 消息接受handle,android调用js
public static Handler mHandler = new Handler() {
    public void handleMessage(Message msg) {
        String method = "";
        if (msg.what == WEIBO_LOGIN) {
            String result = (String) msg.obj;
            method = "javascript:_Native_weiboLogin("+result+")";
        } else if (msg.what == WEIBO_SHARE) {
            String result = (String) msg.obj;
            method = "javascript:_Native_weiboShare('"+result+"')";
        }
        mWebView.evaluateJavascript(method, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                //此处为 js 返回的结果
            }
        });
    }
};

