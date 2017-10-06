package com.qwfine.core.session;

import io.netty.channel.ChannelHandlerContext;

abstract class GameSession {
    private ChannelHandlerContext channel;

    public ChannelHandlerContext getChannel() {
        return channel;
    }

    public void setChannel(ChannelHandlerContext channel) {
        this.channel = channel;
    }
}
