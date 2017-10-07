package com.qwfine.rpc.comm;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

 public class ConfigUtil {
    private static final Config config = ConfigFactory.load();
    public static String getMasterPath(){return config.getString("akka.MasterPath");}
}
