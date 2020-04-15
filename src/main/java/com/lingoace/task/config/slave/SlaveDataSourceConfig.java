package com.lingoace.task.config.slave;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import com.lingoace.task.config.DuidProperties;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author zhanglifeng
 * @Description 次数据源配置
 * @date 2020-04-15
 */
@Configuration
@MapperScan(basePackages = "com.lingoace.task.mapper.slave", sqlSessionFactoryRef = "slaveSqlSessionFactory")
public class SlaveDataSourceConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlaveDataSourceConfig.class);

    @Autowired
    private SlaveProperties slaveProperties;

    @Autowired
    private DuidProperties duidProperties;


    @Bean(name = "slaveDataSource")
    public DataSource slaveDataSource() {
        LOGGER.info("##### slave dataSource init ##### url = {}", slaveProperties.getUrl());
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(slaveProperties.getUrl());
        datasource.setUsername(slaveProperties.getUsername());
        datasource.setPassword(slaveProperties.getPassword());
        datasource.setDriverClassName(slaveProperties.getDriverClassName());
        datasource.setInitialSize(duidProperties.getInitialSize());
        datasource.setMinIdle(duidProperties.getMinIdle());
        datasource.setMaxActive(duidProperties.getMaxActive());
        datasource.setMaxWait(duidProperties.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(duidProperties.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(duidProperties.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(duidProperties.getValidationQuery());
        datasource.setTestWhileIdle(duidProperties.getTestWhileIdle());
        datasource.setTestOnBorrow(duidProperties.getTestOnBorrow());
        datasource.setTestOnReturn(duidProperties.getTestOnReturn());
        datasource.setPoolPreparedStatements(duidProperties.getPoolPreparedStatements());
        datasource.setMaxOpenPreparedStatements(duidProperties.getMaxOpenPreparedStatements());
        try {
            datasource.setFilters(duidProperties.getFilters());
        } catch (SQLException e) {
            LOGGER.error("##### slave dataSource init ##### error = ", e);
        }
        return datasource;
    }


    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("slaveDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        //给映射类注册别名，注册后可以直接使用类名，而不用使用全路径，全限定的类名。虽然这里支持，最好还是用全限定类名哈，方便点进去看，利于维护
        factoryBean.setTypeAliasesPackage("com.lingoace.task.entity");
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/slave/*.xml"));
        //添加PageHelper插件
        Interceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        //数据库
        properties.setProperty("helperDialect", "sqlite");
        //是否将参数offset作为PageNum使用
        properties.setProperty("offsetAsPageNum", "true");
        //是否进行count查询
        properties.setProperty("rowBoundsWithCount", "true");
        //是否分页合理化
        properties.setProperty("reasonable", "false");
        interceptor.setProperties(properties);
        factoryBean.setPlugins(new Interceptor[]{interceptor});
        factoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return factoryBean.getObject();
    }

    @Bean(name = "slaveTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("slaveDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "slaveSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("slaveSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}