package com.thinkis.common.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    public static final String DATASOURCE_ZHU = "zhuSource";
    public static final String DATASOURCE_BEI = "beiSource";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static String getCurrentLookupKey() {
        return (String) contextHolder.get();
    }


    public static void setCurrentLookupKey(String currentLookupKey) {
        contextHolder.set(currentLookupKey);
    }


    @Override
    protected Object determineCurrentLookupKey() {
        return getCurrentLookupKey();
    }
}
