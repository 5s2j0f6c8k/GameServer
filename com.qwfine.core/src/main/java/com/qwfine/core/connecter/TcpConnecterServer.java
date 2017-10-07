package com.qwfine.core.connecter;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
* @author   作者: qugang
* @E-mail   邮箱: qgass@163.com
* @date     创建时间：2017/10/7
* 类说明     Tcp 接入类，使用2个字节长度高位在前低位在后
*/
public class TcpConnecterServer implements IConnecterServer  {
    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    private ActorSystem system = ActorSystem.create("system");

    public void start(String ip, int port) {
        ActorRef gameActorHandler = system.actorOf(Props.create(GameActorHandler.class), "GameActorHandler");
        ServerBootstrap boot = new ServerBootstrap();
        boot.group(bossGroup, workerGroup);
        boot.channel(NioServerSocketChannel.class);
        boot.option(ChannelOption.SO_BACKLOG, 256);

        boot.childHandler(new ChannelInitializer() {
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO))
                        .addLast(new IdleStateHandler(10, 0, 0))
                        .addLast("decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 2, 0, 2))
                        .addLast(new ConnecterChannelInboundHandler(gameActorHandler));
            }
        });
        try {
            ChannelFuture closeFuture = boot.bind(ip, port).sync().channel().closeFuture();
            closeFuture.addListener(future -> shutdown());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void shutdown() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
