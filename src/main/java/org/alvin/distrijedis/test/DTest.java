package org.alvin.distrijedis.test;

import org.alvin.distrijedis.DRedis;
import org.alvin.distrijedis.DRedisServerBean;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshuang on 15/11/23.
 */
public class DTest {
    public static void main(String[] args) {
        List<DRedisServerBean> servers = new ArrayList<DRedisServerBean>();
        for (int i = 0; i < 3; i++) {
            String serverHost = "127.0.0.1";
            int serverPort = 6379 + i;
            DRedisServerBean dRedisServerBean = new DRedisServerBean(serverHost,serverPort);
            servers.add(dRedisServerBean);
        }

        if(0 != servers.size()) {
            DRedis dRedis = DRedis.getInstance(servers);
            for(int i=0; i< 100; i++) {
                String key = "DRF_" + i;
                String value = "FVG_" + i;
                int node = dRedis.getDRedisNode(key);
                System.out.println("debug\t" + node);
                Jedis jedis = dRedis.getJedisInstance(key);
                jedis.set(key, value);
            }
        }
    }
}
