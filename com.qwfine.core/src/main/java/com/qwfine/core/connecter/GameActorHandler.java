package com.qwfine.core.connecter;

import akka.japi.pf.ReceiveBuilder;
import com.qwfine.core.comm.UnionIDUtil;
import com.qwfine.core.protocol.ProtocolFactory;
import com.qwfine.core.protocol.ProtocolHandler;
import com.qwfine.rpc.AbsClientActor;
import io.netty.channel.ChannelHandlerContext;

public abstract class GameActorHandler extends AbsClientActor {
    private ProtocolFactory factory = new ProtocolFactory();
    public GameActorHandler() {
        super(UnionIDUtil.getUUID(), "server1");
    }

    static public class ByteMessage {
        private final byte[] receiveByte;
        private final ChannelHandlerContext channel;

        public ByteMessage(byte[] receiveByte, ChannelHandlerContext channel) {
            this.receiveByte = receiveByte;
            this.channel = channel;
        }
    }


    @Override
    public ReceiveBuilder custemCreateReceive() {
        return receiveBuilder().match(ByteMessage.class, g -> {
            int protocolId = g.receiveByte[0] << 8 | g.receiveByte[1];
            ProtocolHandler handler = factory.createProtocol(protocolId);
            handler.messageHandler(g.receiveByte,g.channel);
        });
    }
}
