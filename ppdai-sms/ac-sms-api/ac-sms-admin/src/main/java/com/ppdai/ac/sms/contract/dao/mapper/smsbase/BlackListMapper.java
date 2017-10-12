package com.ppdai.ac.sms.contract.dao.mapper.smsbase;

import com.ppdai.ac.sms.contract.model.vo.BlackListVo;
import com.ppdai.ac.sms.contract.utils.Page;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/6/13.
 */
public interface BlackListMapper {
     List<BlackListVo> getBlackListByparam(@Param(value = "mobile") String mobile,
                                          @Param(value = "beginTime") Timestamp beginTime,
                                          @Param(value = "endTime") Timestamp endTime,
                                          @Param(value = "createType") Integer createType,
                                          @Param("page")Page page);

    int getBlackListCount(@Param(value = "mobile") String mobile,
                          @Param(value = "beginTime") Timestamp beginTime,
                          @Param(value = "endTime") Timestamp endTime,
                          @Param(value = "createType") Integer createType);

    int delBlackList(@Param(value = "listId") Integer listId);

    int editBlackList(BlackListVo vo);

    int batchSaveBlack(@Param(value = "list")List<BlackListVo> requests);

    int saveBlack(BlackListVo vo);

    BlackListVo findBlackListByMobile(@Param(value = "mobile") String mobile,@Param(value = "listId") Integer listId);

}
