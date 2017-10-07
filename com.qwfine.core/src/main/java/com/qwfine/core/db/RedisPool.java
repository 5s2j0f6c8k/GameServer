package com.qwfine.core.db;

import com.qwfine.core.comm.ConfigUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
    private  static JedisPool jedisPool = null;
    public static void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(ConfigUtil.getRedisMaxTotal());
        config.setMaxIdle(ConfigUtil.getRedisMaxIdle());
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config,ConfigUtil.getRedisIp(),ConfigUtil.getRedisPort(),10000,ConfigUtil.getRedisPassword());
    }

    public static Jedis getConnection(){
        return jedisPool.getResource();
    }
}
