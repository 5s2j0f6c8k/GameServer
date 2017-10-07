package com.qwfine.rpc;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import com.qwfine.rpc.comm.ConfigUtil;

abstract class AbsWorkActor extends AbstractActor {

    private final String id;
    private final String type;
    public AbsWorkActor(String id,String type){
        this.id = id;
        this.type = type;
    }

    @Override
    public void preStart()  throws Exception {
        ActorSelection selection = getContext().actorSelection(ConfigUtil.getMasterPath());
        selection.tell(new MasterActor.Register(this.id,self().path().toStringWithoutAddress(),this.type),self());
        super.preStart();
    }
}
