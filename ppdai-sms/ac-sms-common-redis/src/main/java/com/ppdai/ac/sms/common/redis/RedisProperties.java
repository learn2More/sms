package com.ppdai.ac.sms.common.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Created by kiekiyang on 2017/4/10.
 */
@ConfigurationProperties(prefix = "customer.redis.cluster")
public class RedisProperties {
    private String clusterNodes;

    private String timeout;

    private int redirects;

    private int maxIdle;

    private int maxTotal;

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public int getRedirects() {
        return redirects;
    }

    public void setRedirects(int redirects) {
        this.redirects = redirects;
    }
}
