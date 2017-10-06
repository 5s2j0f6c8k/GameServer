package com.qwfine.core;


import com.qwfine.core.protocol.ProtocolFactory;
import com.qwfine.core.db.RedisPool;
import com.qwfine.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameServer {
    private IServer server;

    @Autowired
    public GameServer(IServer server){
        this.server = server;
    }

    public void Start(String protocolPackage){
        ProtocolFactory.genProtocol(protocolPackage);
        RedisPool.init();
        server.start(ConfigUtil.getIp(),ConfigUtil.getPort());
    }
}
