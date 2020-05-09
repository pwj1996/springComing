package com.pwj.upload.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.pwj.upload.util.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

//datasource最终使用该配置类
//使用@Configuatoin配置数据源的时候，application.yml 文件需要使用jdbc-url代替url
@Configuration
public class DataSourceConfig {

    @Bean(name = "dataSource1")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource1.druid")
    public DataSource dataSource1() {
        System.out.println("dataSource1");
        return new DruidDataSource();
//        return DataSourceBuilder.create().build();
    }

    /**
     * 第二个数据源
     */
    @Bean(name = "dataSource2")
    @ConfigurationProperties(prefix = "spring.datasource2.druid")
    public DataSource dataSource2() {
        System.out.println("datasource2");
        return new DruidDataSource();
    }

    /**
     * 动态数据源
     *
     * @return AbstractRoutingDataSource的实现类，实现相关Datasource的注册
     */
    @Bean
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dataSource = new DynamicDataSource();
        //默认数据源dataSource1，在没有切换数据源的时候使用该数据源
        dataSource.setDefaultTargetDataSource(dataSource1());
        HashMap<Object, Object> map = new HashMap<>();
        map.put("dataSource1", dataSource1());
        map.put("dataSource2", dataSource2());
        //设置数据源Map，动态切换就是根据key从map中获取
        dataSource.setTargetDataSources(map);
        return dataSource;
    }

    /**
     * 和Mybatis整合必须设置SqlSessionFactory的数据源为动态数据源
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        //此处必须设置动态数据源
        factoryBean.setDataSource(dynamicDataSource());
        factoryBean.setVfs(SpringBootVFS.class);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        factoryBean.setConfiguration(configuration);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*.xml"));
        return factoryBean.getObject();
    }

    /**
     * 设置事务管理器
     *
     * @return 设置DynamicDataSource()的事务管理
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }

    @Bean
    public Object testBean(PlatformTransactionManager platformTransactionManager){
        System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
        return new Object();
    }
}
