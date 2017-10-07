package com.qwfine.core.connecter;

import akka.actor.ActorRef;
import com.qwfine.core.comm.UnionIDUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

/**
* @author   作者: qugang
* @E-mail   邮箱: qgass@163.com
* @date     创建时间：2017/10/7
* 类说明     连接消息处理类
*/
public class ConnecterChannelInboundHandler extends ChannelInboundHandlerAdapter {

    private ActorRef gameActorHandler;

    ConnecterChannelInboundHandler(ActorRef gameActorHandler){
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
        gameActorHandler.tell(new GameActorHandler.ByteMessage(reviceByte,ctx),null);
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

    // Return a unreleasable view on the given ByteBuf
    // which will just ignore release and retain calls.
    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled
            .unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",
                    CharsetUtil.UTF_8));  // 1

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        if (evt instanceof IdleStateEvent) {  // 2
            IdleStateEvent event = (IdleStateEvent) evt;
            String type = "";
            if (event.state() == IdleState.READER_IDLE) {
                type = "read idle";
            } else if (event.state() == IdleState.WRITER_IDLE) {
                type = "write idle";
            } else if (event.state() == IdleState.ALL_IDLE) {
                type = "all idle";
            }

            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(
                    ChannelFutureListener.CLOSE_ON_FAILURE);  // 3

        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
