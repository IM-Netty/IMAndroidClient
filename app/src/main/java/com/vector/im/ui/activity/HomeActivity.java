package com.vector.im.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vector.im.R;
import com.vector.im.adapter.HomeChatListAdapter;
import com.vector.im.entity.Chat;
import com.vector.im.entity.ChatMessage;
import com.vector.im.entity.User;
import com.vector.im.manager.IMUserManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * author: vector.huang
 * date：2016/4/19 20:29
 */
public class HomeActivity extends BaseActivity {

    private ListView mListView;
    private List<Chat> mChats;
    private HomeChatListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        mListView = (ListView) findViewById(R.id.list_view);
        mChats = new ArrayList<>();
        mAdapter = new HomeChatListAdapter(this, mChats);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(
                (p, v, position,id) -> {
                    Chat chat = mChats.get(position);
                    Intent intent = new Intent(this, ChatActivity.class);
                    intent.putExtra("id",chat.getId());
                    intent.putExtra("name",chat.getName());
                    startActivity(intent);
                });
        EventBus.getDefault().register(this);
        IMUserManager.onlineUserReq(4);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUsers(List<User> users){
        for(User user : users){
            Chat chat = new Chat();
            chat.setId(user.getId());
            chat.setName(user.getUsername());
            mChats.add(chat);
        }
        mAdapter.notifyDataSetInvalidated();
    }
}
