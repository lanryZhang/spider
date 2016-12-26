package com.ifeng.pgc.core.distribute.handlers;

import com.ifeng.pgc.core.distribute.message.BaseMessage;
import com.ifeng.pgc.core.distribute.message.MessageFactory;
import com.ifeng.pgc.core.distribute.message.MessageType;
import com.ifeng.pgc.core.monitor.spider.SpiderJobDescriptor;
import com.ifeng.pgc.core.monitor.spider.SpiderMonitor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * Created by zhanglr on 2016/9/8.
 */
public class SpiderMonitorRespHandler extends SimpleChannelInboundHandler<BaseMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMessage msg) throws Exception {
        if (msg.getHeader().getType() == MessageType.MONITOR_REQ){
            SpiderMonitor.analysis();
            ctx.writeAndFlush(MessageFactory.createMonitorRespMessage(null));
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //ctx.fireExceptionCaught(cause);
        cause.printStackTrace();
    }
}
