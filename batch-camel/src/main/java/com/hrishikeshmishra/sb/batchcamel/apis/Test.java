package com.hrishikeshmishra.sb.batchcamel.apis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hrishikesh.mishra on 28/09/16.
 */
public class Test {

    public static void main(String[] args) {


        //multiExample();
        blpopExample();
    }

    private static void blpopExample(){

        JedisSentinelPool jedisSentinelPool = getJedisSentinelPool();
        Jedis jedis = jedisSentinelPool.getResource();

        List<String> messages = null;
        while(true){
            System.out.println("Waiting for a message in the queue");
            messages = jedis.blpop(10,"queue");
            System.out.println("Got the message");
            System.out.println("KEY:" + messages.get(0) + " VALUE:" + messages.get(1));
            String payload = messages.get(1);
            System.out.println("Message received:" + payload);
        }
    }


    private static void multiExample(){

        JedisSentinelPool jedisSentinelPool = getJedisSentinelPool();
        Jedis jedis = jedisSentinelPool.getResource();

        int size = 100 ;
        for (int i=0; i <5; i++ ) {
            Transaction t = jedis.multi();
            Response<List<String>> keys = t.lrange("queue", 0, size);
            t.ltrim("queue", size + 1, -1);
            t.exec();
            System.out.println("Found keys in ("+i+") iteration : " + keys.get());
        }

    }

    private static JedisSentinelPool getJedisSentinelPool( ) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(2);
        Set<String> sentinel = new HashSet<>();
        sentinel.add("127.0.0.1:26379");
        return new JedisSentinelPool("mymaster", sentinel, poolConfig, 100 );
    }

}
