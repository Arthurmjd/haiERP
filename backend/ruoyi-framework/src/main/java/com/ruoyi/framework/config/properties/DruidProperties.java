package com.ruoyi.framework.config.properties;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidProperties {
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private int maxWait;
    private int connectTimeout;
    private int socketTimeout;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private int maxEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;

    public DruidDataSource dataSource(DruidDataSource datasource) {
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setConnectTimeout(connectTimeout);
        datasource.setSocketTimeout(socketTimeout);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        return datasource;
    }

    // 所有属性的 getter / setter（必须）
    public int getInitialSize() { return initialSize; }
    public void setInitialSize(int initialSize) { this.initialSize = initialSize; }
    public int getMinIdle() { return minIdle; }
    public void setMinIdle(int minIdle) { this.minIdle = minIdle; }
    public int getMaxActive() { return maxActive; }
    public void setMaxActive(int maxActive) { this.maxActive = maxActive; }
    public int getMaxWait() { return maxWait; }
    public void setMaxWait(int maxWait) { this.maxWait = maxWait; }
    public int getConnectTimeout() { return connectTimeout; }
    public void setConnectTimeout(int connectTimeout) { this.connectTimeout = connectTimeout; }
    public int getSocketTimeout() { return socketTimeout; }
    public void setSocketTimeout(int socketTimeout) { this.socketTimeout = socketTimeout; }
    public int getTimeBetweenEvictionRunsMillis() { return timeBetweenEvictionRunsMillis; }
    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) { this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis; }
    public int getMinEvictableIdleTimeMillis() { return minEvictableIdleTimeMillis; }
    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) { this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis; }
    public int getMaxEvictableIdleTimeMillis() { return maxEvictableIdleTimeMillis; }
    public void setMaxEvictableIdleTimeMillis(int maxEvictableIdleTimeMillis) { this.maxEvictableIdleTimeMillis = maxEvictableIdleTimeMillis; }
    public String getValidationQuery() { return validationQuery; }
    public void setValidationQuery(String validationQuery) { this.validationQuery = validationQuery; }
    public boolean isTestWhileIdle() { return testWhileIdle; }
    public void setTestWhileIdle(boolean testWhileIdle) { this.testWhileIdle = testWhileIdle; }
    public boolean isTestOnBorrow() { return testOnBorrow; }
    public void setTestOnBorrow(boolean testOnBorrow) { this.testOnBorrow = testOnBorrow; }
    public boolean isTestOnReturn() { return testOnReturn; }
    public void setTestOnReturn(boolean testOnReturn) { this.testOnReturn = testOnReturn; }
}