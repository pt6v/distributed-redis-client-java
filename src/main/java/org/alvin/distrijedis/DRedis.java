package org.alvin.distrijedis;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by zhangshuang on 15/11/20.
 */
@SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "UnusedDeclaration"})
public class DRedis {

    private static final AtomicReference<DRedis> instance = new AtomicReference<DRedis>();

    private List<Jedis> redisClients;

    private int redisClientNum;

    private static List<DRedisServerBean> beans;


    private DRedis(List<DRedisServerBean> beans) {
        redisClients = new ArrayList<Jedis>();
        DRedis.beans = beans;
        for (DRedisServerBean bean : DRedis.beans) {
            this.connect(bean);
        }
        this.redisClientNum = this.redisClients.size();
    }

    public static DRedis getInstance(List<DRedisServerBean> beans) {
        if (instance.get() == null) {
            instance.compareAndSet(null, new DRedis(beans));
        }
        return instance.get();
    }

    public static DRedis getInstance() {
        if (instance.get() == null) {
            instance.compareAndSet(null, new DRedis(beans));
        }
        return instance.get();
    }

    private void connect(DRedisServerBean dRedis) {

        if ("".equals(dRedis.getHost()) || 0 == (dRedis.getPort())) {
            return;
        }

        Jedis jedis;

        if (dRedis.getTimeout() != 0) {
            int timeout = dRedis.getTimeout()*1000;
            jedis = new Jedis(dRedis.getHost(), dRedis.getPort(), timeout);
        } else {
            jedis = new Jedis(dRedis.getHost(), dRedis.getPort());
        }
        try {
            jedis.connect();
            System.out.println("connecting...\t" + dRedis.getHost()+"\t" + dRedis.getPort());
            if (dRedis.getPassword() != null && !("".equals(dRedis.getPassword()))) {
                jedis.auth(dRedis.getPassword());
            }

            if (dRedis.getDb() > 0) {
                jedis.select(dRedis.getDb());
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        this.redisClients.add(jedis);


    }

    public List<Jedis> getRedisClients() {
        return this.redisClients;
    }

    public int getRedisClientNum() {
        return this.redisClientNum;
    }

    public int getDRedisNode(String key) {
        if (this.redisClients == null || this.redisClients.size() == 0) {
            return -1;
        }
        List<Integer> nodes = new ArrayList<Integer>();

        for (int i = 0; i < this.redisClients.size(); i++) {
            nodes.add(i);
        }

        ConsistentHash consistentHash = new ConsistentHash();
        consistentHash.addNodes(nodes);
        return consistentHash.getNode(key);
    }

    public Jedis getJedisInstance(String key) {
        int node = this.getDRedisNode(key);
        if (node >= 0) {
            return this.redisClients.get(node);
        }
        return null;
    }


}
