package com.ainutribox.module.trade.dal.mysql.dietitianorderlog;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.trade.dal.dataobject.dietitianorderlog.DietitianOrderLogDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.trade.controller.admin.dietitianorderlog.vo.*;

/**
 * 营养师自组营养包售卖订单日志 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface DietitianOrderLogMapper extends BaseMapperX<DietitianOrderLogDO> {

    default PageResult<DietitianOrderLogDO> selectPage(DietitianOrderLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DietitianOrderLogDO>()
                .eqIfPresent(DietitianOrderLogDO::getUserId, reqVO.getUserId())
                .eqIfPresent(DietitianOrderLogDO::getUserType, reqVO.getUserType())
                .eqIfPresent(DietitianOrderLogDO::getDietitianId, reqVO.getDietitianId())
                .eqIfPresent(DietitianOrderLogDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(DietitianOrderLogDO::getOrderNo, reqVO.getOrderNo())
                .eqIfPresent(DietitianOrderLogDO::getShowStatus, reqVO.getShowStatus())
                .eqIfPresent(DietitianOrderLogDO::getSettlementStatus, reqVO.getSettlementStatus())
                .eqIfPresent(DietitianOrderLogDO::getPayPrice, reqVO.getPayPrice())
                .betweenIfPresent(DietitianOrderLogDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DietitianOrderLogDO::getItemNumber, reqVO.getItemNumber())
                .likeIfPresent(DietitianOrderLogDO::getDietitianSpuName, reqVO.getDietitianSpuName())
                .eqIfPresent(DietitianOrderLogDO::getDietitianSpuId, reqVO.getDietitianSpuId())
                .orderByDesc(DietitianOrderLogDO::getId));
    }

}