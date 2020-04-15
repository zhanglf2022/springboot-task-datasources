package com.lingoace.task.config.master;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description 主数据元配置
 * @author zhanglifeng
 * @date 2020-04-15
 */
@ConfigurationProperties(prefix = "spring.datasource.master")
@Component
@Data
public class MasterProperties {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
