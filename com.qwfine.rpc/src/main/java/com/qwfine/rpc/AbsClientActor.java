package com.qwfine.rpc;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.japi.pf.ReceiveBuilder;
import com.qwfine.rpc.comm.ConfigUtil;

import java.util.List;
public abstract class AbsClientActor extends AbstractActor {

    private final String id;
    private final String findType;

    private ActorSelection workActor =null;

    public ActorSelection getWorkActor() {
        return workActor;
    }

    public void setWorkActor(ActorSelection workActor) {
        this.workActor = workActor;
    }

    public AbsClientActor(String id, String findType) {
        this.id = id;
        this.findType = findType;
    }

    static public class FindResult {
        private final List<MasterActor.Register> list;

        public FindResult(List<MasterActor.Register> list) {
            this.list = list;
        }
    }

    @Override
    public void preStart() throws Exception {
        ActorSelection selection = getContext().actorSelection(ConfigUtil.getMasterPath());
        selection.tell(new MasterActor.FindWork(findType), self());
        super.preStart();
    }

    @Override
    public Receive createReceive() {
        return custemCreateReceive().match(FindResult.class,p->{
            this.setWorkActor(getContext().actorSelection(p.list.get(0).getPath()));
        }).build();
    }


   public abstract ReceiveBuilder custemCreateReceive();
}
