package org.alvin.distrijedis;

/**
 * Created by zhangshuang on 15/11/20.
 */
@SuppressWarnings("UnusedDeclaration")
public class DRedisServerBean {

    /**
     * redis server host to connect
     */
    private String host;

    /**
     * redis server port to connect
     */
    private int port;

    /**
     * redis server db to init
     */
    private int db;

    /**
     * redis server password
     */
    private String password;


    /**
     * timeout when connecting
     */
    private int timeout;


    public DRedisServerBean() {
        this.host = "127.0.0.1";
        this.port = 6379;
        this.db = 0;
        this.password = "";
        this.timeout = 1;
    }

    public DRedisServerBean(String host) {
        this.host = host;
        this.port = 6379;
        this.db = 0;
        this.password = "";
        this.timeout = 1;
    }

    public DRedisServerBean(String host, int port) {
        this.port = port;
        this.host = host;
        this.db = 0;
        this.password = "";
        this.timeout = 1;
    }

    public DRedisServerBean(String host, int port, String password) {
        this.password = password;
        this.port = port;
        this.host = host;
        this.db = 0;
        this.timeout = 1;
    }

    public DRedisServerBean(String host, int port, int timeout) {
        this.port = port;
        this.host = host;
        this.timeout = timeout;
        this.db = 0;
        this.password = "";
    }

    public DRedisServerBean(String host, int port, String password, int timeout) {
        this.host = host;
        this.port = port;
        this.password = password;
        this.timeout = timeout;
        this.db = 0;
    }

    public DRedisServerBean(String host, int port, String password, int timeout, int db) {
        this.host = host;
        this.port = port;
        this.password = password;
        this.timeout = timeout;
        this.db = db;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
