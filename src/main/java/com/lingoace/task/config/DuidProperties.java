package com.lingoace.task.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhanglifeng
 * @Description [数据源连接池配置]
 * @date 2020-04-15
 */
@ConfigurationProperties(prefix = "spring.datasource.druid")
@Component
@Data
public class DuidProperties {
    private Integer initialSize;
    private Integer minIdle;
    private Integer maxActive;
    private Integer maxWait;
    private Integer timeBetweenEvictionRunsMillis;
    private Long minEvictableIdleTimeMillis;
    private String validationQuery;
    private Boolean testWhileIdle;
    private Boolean testOnReturn;
    private Boolean testOnBorrow;
    private Boolean poolPreparedStatements;
    private Integer maxOpenPreparedStatements;
    private String filters;
}
