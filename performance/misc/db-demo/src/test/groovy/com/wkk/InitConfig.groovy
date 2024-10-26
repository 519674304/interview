package com.wkk

import com.alibaba.druid.pool.DruidDataSource
import com.alibaba.druid.pool.DruidDataSourceFactory
import com.wkk.business.mapper.IDriverMapper
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.jdbc.datasource.DataSourceTransactionManager

import javax.sql.DataSource

@Configuration
@ComponentScan(basePackages = "com.wkk")
class InitConfig {
    @Bean
    SqlSessionFactory sqlSessionFactory(){
        def bean = new SqlSessionFactoryBean()
        bean.setDataSource(dataSource())
        def resource = new PathMatchingResourcePatternResolver()
                .getResource("classpath*:mybatis.mapper/**/*.xml")
        bean.setMapperLocations(resource)
        bean.setConfiguration(configuration())
        bean.getObject()
    }

    @Bean
    org.apache.ibatis.session.Configuration configuration(){
        new org.apache.ibatis.session.Configuration(
                mapUnderscoreToCamelCase: true
        )
    }

    @Bean
    DataSource dataSource() {
        new DruidDataSource(
                url: "jdbc:mysql://192.168.247.142:3306/driver?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC",
                username: "root",
                password: "root",
                driverClassName: "com.mysql.jdbc.Driver"
        )
    }

    @Bean
    IDriverMapper driverMapper() {
        def template = new SqlSessionTemplate(sqlSessionFactory())
        template.getMapper(IDriverMapper)
    }

    @Bean
    DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
