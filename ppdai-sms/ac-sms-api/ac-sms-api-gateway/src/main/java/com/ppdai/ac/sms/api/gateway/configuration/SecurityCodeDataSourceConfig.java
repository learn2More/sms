package com.ppdai.ac.sms.api.gateway.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import ppdai.dbtracing.mybatis.CatMybatisPlugin;

import javax.sql.DataSource;

/**
 * Created by kiekiyang on 2017/4/28.
 */
@Configuration
@MapperScan(basePackages = "com.ppdai.ac.sms.api.gateway.dao.mapper.securitycode", sqlSessionTemplateRef = "securitycodeSqlSessionTemplate")
public class SecurityCodeDataSourceConfig {
    @Bean(name = "securitycodeDataSource")
    @ConfigurationProperties(prefix = "datasource.securitycode")
    public DataSource securitycodeDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean(name = "securitycodeSqlSessionFactory")
    public SqlSessionFactory securitycodeSqlSessionFactory(@Qualifier("securitycodeDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setPlugins(new Interceptor[]{new CatMybatisPlugin()});
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/conf/mybatis/mapper/securitycode/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "securitycodeTransactionManager")
    public PlatformTransactionManager securitycodeTransactionManager(@Qualifier("securitycodeDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "securitycodeSqlSessionTemplate")
    public SqlSessionTemplate securitycodeSqlSessionTemplate(@Qualifier("securitycodeSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}