package com.wkk.business.config.db

import com.alibaba.druid.pool.DruidDataSourceFactory
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory

class DruidDataSourceFactoryInject extends PooledDataSourceFactory {
    DruidDataSourceFactoryInject() {
        new DruidDataSourceFactory()
    }
}
