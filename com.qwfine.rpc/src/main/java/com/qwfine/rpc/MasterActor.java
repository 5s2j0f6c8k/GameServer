package com.qwfine.rpc;

import akka.actor.AbstractActor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MasterActor extends AbstractActor {

    static public class Register {
        private final String id;
        private final String path;
        private final String type;

        public Register(String id, String path, String type) {
            this.id = id;
            this.path = path;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public String getPath() {
            return path;
        }

        public String getType() {
            return type;
        }
    }

    static public class FindWork {
        private final String type;

        public FindWork(String type) {
            this.type = type;
        }
    }

    List<Register> list = new ArrayList<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Register.class, r -> {
            list.add(r);
        }).match(FindWork.class, r -> {
            List<Register> tempList = list.stream().filter(p -> p.type == r.type).collect(Collectors.toList());
            sender().tell(new AbsClientActor.FindResult(list),self());
        }).build();
    }
}
