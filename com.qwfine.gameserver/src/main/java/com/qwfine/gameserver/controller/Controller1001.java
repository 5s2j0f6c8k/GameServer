package com.qwfine.gameserver.controller;

import com.qwfine.core.db.RedisPool;
import com.qwfine.core.protocol.ProtocolHandler;
import com.qwfine.core.protocol.ProtocolId;
import io.netty.channel.ChannelHandlerContext;

@ProtocolId(protocolId = 1001)
public class Controller1001 extends ProtocolHandler {

    public void messageHandler(byte[] receiveByte, ChannelHandlerContext channel) {
    }
}
