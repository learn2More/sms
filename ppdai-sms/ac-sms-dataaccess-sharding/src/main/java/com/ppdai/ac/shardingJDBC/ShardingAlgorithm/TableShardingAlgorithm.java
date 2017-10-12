package com.ppdai.ac.shardingJDBC.ShardingAlgorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;
import com.ppdai.ac.shardingJDBC.Utils.DateForMatUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by xiejin on 2017/5/4.
 */
public class TableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Timestamp> {

    private String originTableName;

    public TableShardingAlgorithm(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            throw new ExceptionInInitializerError("逻辑表名不能为空");
        }
        this.originTableName = tableName;
    }

    @Override
    public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Timestamp> shardingValue) {
        String dateStr = DateForMatUtil.getMonthAndWeekStr(shardingValue.getValue());
        return originTableName + "_" + dateStr;
    }

    @Override
    public Collection<String> doInSharding(Collection<String> availableTargetNames, ShardingValue<Timestamp> shardingValue) {
        Collection<Timestamp> dates = shardingValue.getValues();
        Collection<String> result = new LinkedHashSet<>();
        for (Timestamp date : dates) {
            result.add(originTableName + "_" + DateForMatUtil.getMonthAndWeekStr(date));
        }
        return result;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> availableTargetNames, ShardingValue<Timestamp> shardingValue) {
        Collection<String> result = new LinkedHashSet<>();
        Range<Timestamp> range = shardingValue.getValueRange();
        List<String> weeks= DateForMatUtil.getBetweenMonthAndWeekStr(range.lowerEndpoint(),range.upperEndpoint());
        for (String week : weeks) {
            result.add(originTableName+"_"+week);
        }
        return result;
    }
}
