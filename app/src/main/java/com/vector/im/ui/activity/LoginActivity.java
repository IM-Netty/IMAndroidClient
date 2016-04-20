package com.vector.im.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.Toast;

import com.vector.im.R;
import com.vector.im.event.UserLoginEvent;
import com.vector.im.im.IMLoginClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: vector.huang
 * date：2016/4/20 0:51
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.host)
    EditText mHost;
    @Bind(R.id.port)
    EditText mPort;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void  onLoginSuccess(UserLoginEvent event){
        Toast.makeText(this,"连接成功",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }

    @OnClick(R.id.login_btn)
    public void login() {
        try {
            String host = mHost.getText().toString().trim();
            String portStr = mPort.getText().toString().trim();
            int port = Integer.parseInt(portStr);
            IMLoginClient.instance().connect(host,port);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
