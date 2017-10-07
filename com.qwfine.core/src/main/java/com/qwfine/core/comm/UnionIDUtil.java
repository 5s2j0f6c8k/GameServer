package com.qwfine.core.comm;
import java.util.UUID;

public class UnionIDUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
