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
 * Created by kiekiyang on 2017/4/25.
 */
@Configuration
@MapperScan(basePackages = "com.ppdai.ac.sms.api.gateway.dao.mapper.smsbase", sqlSessionTemplateRef = "smsbaseSqlSessionTemplate")
public class SMSBaseDataSourceConfig {
    @Bean(name = "smsbaseDataSource")
    @ConfigurationProperties(prefix = "datasource.smsbase")
    public DataSource smsbaseDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean(name = "smsbaseSqlSessionFactory")

    public SqlSessionFactory smsbaseSqlSessionFactory(@Qualifier("smsbaseDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setPlugins(new Interceptor[]{new CatMybatisPlugin()});
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/conf/mybatis/mapper/sms_base/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "smsbaseTransactionManager")

    public PlatformTransactionManager smsbaseTransactionManager(@Qualifier("smsbaseDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "smsbaseSqlSessionTemplate")

    public SqlSessionTemplate smsbaseSqlSessionTemplate(@Qualifier("smsbaseSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Primary
    public CatMybatisPlugin catMybatisPlugin() {
        return new CatMybatisPlugin();
    }
}
