package com.qwfine.core.messageActor;

import akka.actor.*;
import com.qwfine.core.protocol.ProtocolFactory;
import com.qwfine.core.protocol.ProtocolHandler;
import io.netty.channel.ChannelHandlerContext;

public abstract class GameActorHandler extends AbstractActor {
    private ProtocolFactory factory = new ProtocolFactory();

    static public class ByteMessage {
        private final byte[] receiveByte;
        private final ChannelHandlerContext channel;

        public ByteMessage(byte[] receiveByte, ChannelHandlerContext channel) {
            this.receiveByte = receiveByte;
            this.channel = channel;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(ByteMessage.class, g -> {
            int protocolId = g.receiveByte[0] << 8 | g.receiveByte[1];
            ProtocolHandler handler = factory.createProtocol(protocolId);
            handler.messageHandler(g.receiveByte,g.channel);
        }).build();
    }
}
