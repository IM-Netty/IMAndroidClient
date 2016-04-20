package com.vector.im.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.vector.im.R;
import com.vector.im.entity.ChatMessage;
import com.vector.im.manager.IMMessageManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: vector.huang
 * date：2016/4/19 20:45
 */
public class ChatActivity extends BaseActivity {

    private int mId;
    private String mName;

    @Bind(R.id.list_view)
    ListView mListView;
    @Bind(R.id.content_txt)
    EditText mContent;

    private ArrayAdapter<String> mAdapter;
    private List<String> mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        mId = getIntent().getIntExtra("id",0);
        mName = getIntent().getStringExtra("name");

        ActionBar bar = getSupportActionBar();
        bar.setTitle(mId+":"+mName);
        bar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mData = new ArrayList<>();
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mData);
        mListView.setAdapter(mAdapter);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(ChatMessage msg){
        mData.add(mId+":"+mName+":"+msg.getContent());
        mAdapter.notifyDataSetInvalidated();
    }

    @OnClick(R.id.send_btn)
    public void send(){

        String content = mContent.getText().toString().trim();
        if(content.isEmpty()){
            return;
        }

        IMMessageManager.sendSingleMsgReq(mId,content);
        mData.add(content);
        mAdapter.notifyDataSetInvalidated();
        mContent.setText("");
    }

}
