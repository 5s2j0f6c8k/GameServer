package com.qwfine.core.protocol;

import io.netty.channel.ChannelHandlerContext;

public abstract class ProtocolHandler {

    public abstract void messageHandler(byte[] receiveByte,ChannelHandlerContext channel);
}
