package com.vector.im.manager;

import com.vector.im.constant.ProtocolConstant;
import com.vector.im.entity.ChatMessage;
import com.vector.im.entity.Packet;
import com.vector.im.im.IMClient;

import org.greenrobot.eventbus.EventBus;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * author: vector.huang
 * date：2016/4/18 22:06
 */
public class IMMessageManager {

    public static void sendSingleMsgReq(int to,String content){
        byte[] bytes = content.getBytes();
        ByteBuf buf = IMClient.instance().channel().alloc().buffer(bytes.length+4);
        buf.writeInt(to);
        buf.writeBytes(bytes);
        Packet packet = new Packet(buf.readableBytes() + 12,
                ProtocolConstant.SID_MSG,ProtocolConstant.CID_MSG_SEND_SINGLE_REQ
                ,buf);
        IMClient.instance().channel().writeAndFlush(packet);
    }

    public static void receiveMsgSingleOut(ByteBuf body){
        int from = body.readInt();
        String content = body.toString(Charset.defaultCharset());

        ChatMessage msg= new ChatMessage();
        msg.setFromId(from);
        msg.setContent(content);

        EventBus.getDefault().post(msg);
    }

}
