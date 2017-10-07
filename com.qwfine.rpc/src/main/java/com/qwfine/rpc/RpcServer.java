package com.qwfine.rpc;

import akka.actor.AbstractActor;
import akka.actor.Actor;

public class RpcServer extends AbstractActor {
    public Receive createReceive() {
        return receiveBuilder().match(RpcMessageReq.class,p->{
            
        }).build();
    }
}
