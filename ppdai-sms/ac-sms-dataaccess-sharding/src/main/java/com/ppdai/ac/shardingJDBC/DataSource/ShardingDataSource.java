package com.ppdai.ac.shardingJDBC.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.ppdai.ac.shardingJDBC.ShardingAlgorithm.DatabaseShardingAlgorithm;
import com.ppdai.ac.shardingJDBC.ShardingAlgorithm.TableShardingAlgorithm;
import com.ppdai.ac.shardingJDBC.Utils.DateForMatUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.*;

/**
 * Shard-JDBC的数据源
 * Created by xiejin on 2017/5/12.
 */
public class ShardingDataSource {

    private static final Logger logger = LoggerFactory.getLogger(ShardingDataSource.class);


    private  ShardingDataSourceConfig config;

    public  ShardingDataSource(ShardingDataSourceConfig config){
        this.config=config;

    }

    public  DataSource createShardingDataSource(){
        //获取Sharding-JDBC的配置
        if(config==null)
            throw new ExceptionInInitializerError(" Initializer ShardingDataSourceConfig error");
        Integer startYear=config.getStartYear();
        Integer endYear=config.getEndYear();
        //获取Sharding-JDBC的配置开始年份和结束年份的配置
        if(startYear==null||endYear==null||startYear>endYear)
            throw new ExceptionInInitializerError(" Initializer ShardingDataSourceConfig error,startYear or endYear invaild");
        //获取开始年份和结束年份的所有年份
        List<String> years= DateForMatUtil.getYearList(startYear,endYear);
        //设置分库映射
        Map<String, DataSource> dataSourceMap = new HashMap<>(years.size());
        String databaseName=config.getDatabase();//获取数据库名字
        if(StringUtils.isBlank(databaseName))
            throw new ExceptionInInitializerError(" Initializer ShardingDataSourceConfig error,databaseName  can't null ");
        String placeholderChar=config.getPlaceholderChar();
        for(String year:years){
            dataSourceMap.put(databaseName+placeholderChar+year,createDataSource(databaseName+placeholderChar+year,config));
        }
        //设置默认的数据源
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap, databaseName+placeholderChar+ DateForMatUtil.getYear(new Date()));

        List<String> shardingTables=config.getShardingTable();
        List<String> shardingKeys=config.getShardingKey();
        if(shardingTables==null||shardingKeys==null||shardingTables.size()!=shardingKeys.size())
            throw new ExceptionInInitializerError(" Initializer ShardingDataSourceConfig error,shardingTable  or shardingKey invalid ");
        List<TableRule> shardingTableRuleList=new ArrayList<>();
        for(int i=0;i<shardingTables.size();i++){
            String tableName=shardingTables.get(i);
            TableRule shardingTableRule = TableRule.builder(tableName)
                    .dynamic(Boolean.TRUE)
                    .dataSourceRule(dataSourceRule)
                    .tableShardingStrategy(new TableShardingStrategy(shardingKeys.get(i), new TableShardingAlgorithm(tableName)))
                    .build();
            shardingTableRuleList.add(shardingTableRule);
        }
        //具体分库分表策略，按什么规则来分
        ShardingRule shardingRule = ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(shardingTableRuleList)
                .databaseShardingStrategy(new DatabaseShardingStrategy("insertTime", new DatabaseShardingAlgorithm())).build();
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
        return dataSource;
    }

    private  DataSource createDataSource(final String dataSourceName,ShardingDataSourceConfig config) {
        /**
         *使用druid连接数据库
         * 其他的一些连接池属性请自行添加
         * 目前只是简单run起来
         */
        DruidDataSource result = new DruidDataSource();
        try {
            result.setDriverClassName(config.getDriverClassName());
            String url=config.getUrl().replace("${database}",dataSourceName);
            result.setUrl(url);
            result.setUsername(config.getUsername());
            result.setPassword(config.getPassword());
            if(config.getInitialSize()!=null)
                result.setInitialSize(config.getInitialSize());
            if(config.getMinIdle()!=null)
                result.setMinIdle(config.getMinIdle());
            if(config.getMaxActive()!=null)
                result.setMaxActive(config.getMaxActive());
            if(config.getMaxWait()!=null)
                result.setMaxWait(config.getMaxWait());
            if(config.getTimeBetweenEvictionRunsMillis()!=null)
                result.setTimeBetweenEvictionRunsMillis(config.getTimeBetweenEvictionRunsMillis());
            if(config.getValidationQuery()!=null)
                result.setValidationQuery(config.getValidationQuery());
            if(config.getTestWhileIdle()!=null)
                result.setTestWhileIdle(config.getTestWhileIdle());
            if(config.getTestOnBorrow()!=null)
                result.setTestOnBorrow(config.getTestOnBorrow());
            if(config.getTestOnReturn()!=null)
                result.setTestOnReturn(config.getTestOnReturn());
            if(config.getPoolPreparedStatements()!=null)
                result.setPoolPreparedStatements(config.getPoolPreparedStatements());
            if(config.getMaxPoolPreparedStatementPerConnectionSize()!=null)
                result.setMaxPoolPreparedStatementPerConnectionSize(config.getMaxPoolPreparedStatementPerConnectionSize());
            if(config.getFilters()!=null)
                result.setFilters(config.getFilters());
            if(config.getConnectionProperties()!=null)
                result.setConnectProperties(config.getConnectionProperties());
        }catch (Exception e){
            result.close();
            logger.error("Initializer ShardingDataSourceConfig error",e);
            throw new ExceptionInInitializerError(e);
        }
        return result;
    }
}
