package com.ppdai.ac.sms.contract.configuration;

import com.ppdai.ac.shardingJDBC.DataSource.ShardingDataSource;
import com.ppdai.ac.shardingJDBC.DataSource.ShardingDataSourceConfig;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
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
@MapperScan(basePackages = "com.ppdai.ac.sms.contract.dao.mapper.smsmessage",sqlSessionTemplateRef = "smsmessageSqlSessionTemplate")
public class SMSMessageDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "datasource.smsmessage")
    public ShardingDataSourceConfig ShardingDataSourceConfig(){
        return new ShardingDataSourceConfig();
    }

    @Bean(name = "smsmessageDataSource")
    @Primary
    public DataSource smsmessageDataSource() {
        return new ShardingDataSource(ShardingDataSourceConfig()).createShardingDataSource();
    }

    @Bean(name = "smsmessageSqlSessionFactory")
    @Primary
    public SqlSessionFactory smsmessageSqlSessionFactory(@Qualifier("smsmessageDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPlugins(new Interceptor[]{new CatMybatisPlugin()});
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/conf/mybatis/mapper/sms_message/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "smsmessageTransactionManager")
    @Primary
    public PlatformTransactionManager smsmessageTransactionManager(@Qualifier("smsmessageDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "smsmessageSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate smsmessageSqlSessionTemplate(@Qualifier("smsmessageSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
