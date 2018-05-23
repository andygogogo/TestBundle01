package com.test.bundle0523;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by andy on 2018/5/16.
 */

public class DsLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DsLoginActivity";
    private FrameLayout loginPb;
    //    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private ImageView avatar;
    private TextView uname;
    private EditText unum;
    private EditText upwd;
    private ImageButton login;
    private TextView comQuestion;
    private ImageView unseen;
    private SharedPreferences sp;
    private MyHandler myHandler;
    private boolean isPwdSeen = false;
    private TextView nologin;
    private TextView register;
    private TextView forgetpwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dslogin);
        findMyView();
        myHandler = new MyHandler(this);
        String value1 = getIntent().getStringExtra("Value1");
        if (!TextUtils.isEmpty(value1)) {
            Toast.makeText(this, value1, Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, BuildConfig.VERSION_NAME, Toast.LENGTH_SHORT).show();
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd, HH-mm-ss");
//        String current = df.format(Calendar.getInstance().getTime());
//        Toast.makeText(this, "测试热修复" + current, Toast.LENGTH_SHORT).show();
        uname.setVisibility(View.VISIBLE);
        uname.setText("Mr呆萌");
    }

    public void findMyView() {
        avatar = findViewById(R.id.iv_login_uavatar);
        uname = findViewById(R.id.tv_login_uname);
        unum = findViewById(R.id.et_login_unum);
        upwd = findViewById(R.id.et_login_upwd);
        login = findViewById(R.id.tv_login_login);
        unseen = findViewById(R.id.iv_pwd_unseen);
        nologin = findViewById(R.id.tv_nologin);
        register = findViewById(R.id.tv_register);
        forgetpwd = findViewById(R.id.tv_forgetpwd);
        login.setEnabled(false);
        comQuestion = findViewById(R.id.tv_login_otheruser);
        loginPb = findViewById(R.id.fl_login_pb);
        login.setOnClickListener(this);
        unseen.setOnClickListener(this);
        comQuestion.setOnClickListener(this);
        nologin.setOnClickListener(this);
        register.setOnClickListener(this);
        forgetpwd.setOnClickListener(this);
        sp = getSharedPreferences("user_info", MODE_PRIVATE);
        unum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !TextUtils.isEmpty(upwd.getText().toString())) {
                    login.setEnabled(true);
                } else {
                    login.setEnabled(false);
                }
            }
        });

        upwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !TextUtils.isEmpty(unum.getText().toString())) {
                    login.setEnabled(true);
                } else {
                    login.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_login_login) {
            String loginnum = unum.getText().toString();
            String loginpwd = upwd.getText().toString();
            if (TextUtils.isEmpty(loginnum)) {
                Toast.makeText(this, "请输入操作员码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(loginpwd)) {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = new User();
            user.setLgname(loginnum);
            user.setLgpassword(loginpwd);
            login(user);

        } else if (v.getId() == R.id.iv_pwd_unseen) {
            if (isPwdSeen) {
                upwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                unseen.setImageDrawable(getResources().getDrawable(R.drawable.login_icon_unseen));
                isPwdSeen = false;
            } else {
                upwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                unseen.setImageDrawable(getResources().getDrawable(R.drawable.login_icon_seen));
                isPwdSeen = true;
            }
            if (!TextUtils.isEmpty(upwd.getText().toString())) {
                upwd.setSelection(upwd.getText().toString().length());
            }
        } else if (v.getId() == R.id.tv_nologin) {
            Intent intent = new Intent();
            intent.setClassName(DsLoginActivity.this, "com.hzbank.dsweex.DSWeexMainActivity");
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.tv_login_otheruser) {
            Toast.makeText(this, "常见问题", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.tv_register) {
            Toast.makeText(this, "立即注册", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.tv_forgetpwd) {
            Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();
        }
    }

    public void login(final User user) {
        loginPb.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String loginUrl = "http://192.168.23.1:8080/ifp-emas/lginAction.do";
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.connectTimeout(10000, TimeUnit.MILLISECONDS);
                builder.readTimeout(10000, TimeUnit.MILLISECONDS);
                builder.writeTimeout(10000, TimeUnit.MILLISECONDS);
                OkHttpClient client = builder.build();
                FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
                formBody.add("lgname", user.getLgname());//传递键值对参数
                formBody.add("lgpassword", user.getLgpassword());
                Request request = new Request.Builder()
                        .url(loginUrl)
                        .post(formBody.build())
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
//                        if (e.getCause().equals(SocketTimeoutException.class)) {
                        Message message = Message.obtain();
                        message.what = 1;
                        myHandler.sendEmptyMessage(1);
//                        }

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Message message = Message.obtain();
                        Bundle bundle = new Bundle();
                        bundle.putString("response", response.body().string());
                        message.what = 0;
                        message.setData(bundle);
                        myHandler.sendMessage(message);
                    }
                });
            }
        }).start();

//        MtopRequest mtopRequest = new MtopRequest();
//        mtopRequest.setApiName("http://192.168.23.1:8080/ifp-emas/lginAction.do");
//        mtopRequest.setVersion("1.0.0");
//        mtopRequest.setNeedEcode(false);
//
//        MtopBuilder builder = Mtop.instance(DsLoginActivity.this).build(mtopRequest, "");
//        //设置请求级别自定义域名
//        builder.setCustomDomain("");
//        builder.protocol(ProtocolEnum.HTTP);
//        builder.addListener(new MtopCallback.MtopFinishListener() {
//            @Override
//            public void onFinished(MtopFinishEvent mtopFinishEvent, Object o) {
//                MtopResponse response = mtopFinishEvent.getMtopResponse();
//                if (response.isApiSuccess()) {
//                    LoginResponse loginResponse = MtopConvert.convertJsonToOutputDO(response.getBytedata(), LoginResponse.class);
//                }else if(response.isSystemError() || response.isNetworkError() || response.isExpiredRequest()
//                        || response.is41XResult() || response.isApiLockedResult()||response.isMtopSdkError()){
//                    // 系统错误，网络错误，防刷，防雪崩
//                    // do something handle system error.
//                } else {
//                    //业务错误
//                    //do something handle  api business error.
//                }
//            }
//        });
//        builder.reqMethod(MethodEnum.POST);
//        builder.setConnectionTimeoutMilliSecond(10000);
//        builder.setSocketTimeoutMilliSecond(10000);
//        ApiID apiId = builder.asyncRequest();
        //取消当前异步请求
//        apiId.cancelApiCall();
        //重试本次请求
//        apiId.retryApiCall();
    }

    /**
     * 创建静态内部类
     */
    private static class MyHandler extends Handler {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<DsLoginActivity> mActivty;

        private MyHandler(DsLoginActivity activity) {
            mActivty = new WeakReference<DsLoginActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            DsLoginActivity activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                //执行业务逻辑
                mActivty.get().loginPb.setVisibility(View.GONE);
                if (msg.what == 0) {
                    String str = msg.getData().getString("response");
                    Log.d(TAG, "handleMessage: " + str);
                    LoginResponse loginResponse = JSON.parseObject(str, LoginResponse.class);
                    if (loginResponse != null && loginResponse.body.error.equals("0")) {
                        Toast.makeText(mActivty.get(), "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClassName(mActivty.get(), "com.hzbank.dsweex.DSWeexMainActivity");
                        intent.putExtra("userinfo", str);
                        mActivty.get().startActivity(intent);
                        mActivty.get().finish();
                    } else {
                        Toast.makeText(mActivty.get(), "登录失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                }
                if (msg.what == 1) {
                    Toast.makeText(mActivty.get(), "登录失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
