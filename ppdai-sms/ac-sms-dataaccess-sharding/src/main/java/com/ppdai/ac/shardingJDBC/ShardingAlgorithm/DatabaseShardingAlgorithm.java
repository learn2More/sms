package com.ppdai.ac.shardingJDBC.ShardingAlgorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;
import com.ppdai.ac.shardingJDBC.Utils.DateForMatUtil;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by xiejin on 2017/5/4.
 */
public class DatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Timestamp> {

    /**
     * 根据分片值和SQL的=运算符计算分片结果名称集合.
     * @param availableTargetNames 所有的可用目标名称集合, 一般是数据源或表名称
     * @param shardingValue        分片值
     * @return 分片后指向的目标名称, 一般是数据源或表名称
     */
    @Override
    public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Timestamp> shardingValue) {
        String databaseName = "";
        for (String targetName : availableTargetNames) {
            if (targetName.endsWith(DateForMatUtil.getYear(shardingValue.getValue()))) {
                databaseName = targetName;
                break;
            }
        }
        //TODO   如果DataBase不存在 请根据业务自己酌情考虑吧
        return databaseName;
    }


    @Override
    public Collection<String> doInSharding(Collection<String> availableTargetNames, ShardingValue<Timestamp> shardingValue){
        Collection<Timestamp> dates = shardingValue.getValues();
        Collection<String> result = new LinkedHashSet<>();
        for (String databaseName : availableTargetNames) {
            for (Timestamp date : dates) {
                if(databaseName.endsWith(DateForMatUtil.getYear(date))) {
                    result.add(databaseName);break;
                }
            }
        }
        return result;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> availableTargetNames, ShardingValue<Timestamp> shardingValue){
        Range<Timestamp> range = shardingValue.getValueRange();
        Collection<String> result = new LinkedHashSet<>();
        int startYear=Integer.parseInt(DateForMatUtil.getYear(range.lowerEndpoint()));
        int endYear=Integer.parseInt(DateForMatUtil.getYear(range.upperEndpoint()));
        for (String targetName : availableTargetNames) {
            for(int year=startYear;year<=endYear;year++){
                if (targetName.endsWith(String.valueOf(year))) {
                    result.add(targetName);
                    break;
                }
            }
        }
        return result;
    }
}
