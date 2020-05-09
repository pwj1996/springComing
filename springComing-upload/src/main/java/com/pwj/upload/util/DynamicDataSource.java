package com.pwj.upload.util;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 决定使用哪个数据源
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        //从ThreadLocal中获取数据源的beanName
        return DynamicThreadLocalHolder.getDataSource();
    }
}
