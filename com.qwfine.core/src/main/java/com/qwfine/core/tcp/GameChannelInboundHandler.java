package com.qwfine.core.tcp;

import akka.actor.ActorRef;
import com.qwfine.core.messageActor.GameActorHandler;
import com.qwfine.util.UnionIDUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

/**
* @author   作者: qugang
* @E-mail   邮箱: qgass@163.com
* @date     创建时间：2017/10/7
* 类说明     连接消息处理类
*/
public class GameChannelInboundHandler extends ChannelInboundHandlerAdapter {

    private ActorRef gameActorHandler;

    GameChannelInboundHandler(ActorRef gameActorHandler){
        this.gameActorHandler = gameActorHandler;
    }

    private final AttributeKey<String> counter = AttributeKey.valueOf("counter");
    /**
     * 读取数据事件
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
        byte[] reviceByte = new byte[in.readableBytes()];
        in.readBytes(reviceByte);
        super.channelRead(ctx, msg);
    }

    /**
     * 套接字出错事件
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 客户端连接事件
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().attr(counter).set(UnionIDUtil.getUUID());
        super.channelActive(ctx);
    }

    /**
     * 客户端关闭事件
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
