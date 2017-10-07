package com.qwfine.core.connecter;


import com.qwfine.core.protocol.ProtocolFactory;
import com.qwfine.core.db.RedisPool;
import com.qwfine.core.comm.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnecterServer {
    private IConnecterServer server;

    @Autowired
    public ConnecterServer(IConnecterServer server){
        this.server = server;
    }

    public void Start(String protocolPackage){
        ProtocolFactory.genProtocol(protocolPackage);
        RedisPool.init();
        server.start(ConfigUtil.getIp(),ConfigUtil.getPort());
    }
}
