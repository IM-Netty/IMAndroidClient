package com.vector.im.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vector.im.R;
import com.vector.im.entity.Chat;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author: vector.huang
 * date：2016/4/19 21:10
 */
public class HomeChatListAdapter extends BaseAdapter {

    private List<Chat> mData;
    private Context mContext;

    public HomeChatListAdapter(Context context,List<Chat> data){
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.home_chat_list_item,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bindData(mData.get(position));
        return convertView;
    }

    class ViewHolder{

        @Bind(R.id.head_image) ImageView mHeadImage;
        @Bind(R.id.chat_name) TextView mChatName;
        @Bind(R.id.chat_content) TextView mChatContent;
        @Bind(R.id.chat_time) TextView mChatTime;

        ViewHolder(View view){
            ButterKnife.bind(this,view);
        }

        public void bindData(Chat chat) {
            mChatName.setText(chat.getId()+":"+chat.getName());
            if(chat.getNewestMsg() == null){
                return;
            }
            mChatContent.setText(chat.getNewestMsg().getFromId()+":"+chat.getNewestMsg().getContent());
            mChatTime.setText(new SimpleDateFormat("HH:mm").format(chat.getNewestMsg().getSendTime()));
        }
    }
}
