package com.qwfine.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigUtil {
    private static final Config config = ConfigFactory.load();

    public static String getIp(){
        return config.getString("server.ip");
    }

    public static int getPort(){
        return config.getInt("server.port");
    }

    public static String getRedisIp(){return config.getString("redis.ip");}

    public static int getRedisPort(){return config.getInt("redis.port");}

    public static String getRedisPassword(){return config.getString("redis.password");}

    public static Integer getRedisMaxTotal(){return config.getInt("redis.maxTotal");}

    public static Integer getRedisMaxIdle(){return config.getInt("redis.maxIdle");}

}
