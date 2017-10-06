package com.qwfine.core.protocol;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProtocolFactory {

    private static Map<Integer, Class> map = new HashMap<>();

    public ProtocolHandler createProtocol(int protocolId) throws IllegalAccessException, InstantiationException {
        return (ProtocolHandler) map.get(protocolId).newInstance();
    }


    public static void genProtocol(String packageName) {
        Reflections reflections = new Reflections(packageName);

        Set<Class<?>> allClasses =
                reflections.getSubTypesOf(Object.class);
        allClasses.forEach((Class<?> p) -> {
            ProtocolId protocolId = p.getAnnotation(ProtocolId.class);
            if (protocolId != null) {
                map.put(protocolId.protocolId(), p);
            }
        });
    }
}
