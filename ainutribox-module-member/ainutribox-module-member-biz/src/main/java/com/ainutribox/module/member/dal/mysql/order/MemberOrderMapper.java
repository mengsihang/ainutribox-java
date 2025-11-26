package com.ainutribox.module.member.dal.mysql.order;

import java.time.LocalDateTime;
import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.order.MemberOrderDO;
import com.ainutribox.module.trade.dal.dataobject.order.TradeOrderDO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.order.vo.*;

/**
 * 用户购买课程和VIP订单 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface MemberOrderMapper extends BaseMapperX<MemberOrderDO> {

    default PageResult<MemberOrderDO> selectPage(MemberOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MemberOrderDO>()
                .eqIfPresent(MemberOrderDO::getNo, reqVO.getNo())
                .eqIfPresent(MemberOrderDO::getType, reqVO.getType())
                .eqIfPresent(MemberOrderDO::getUserId, reqVO.getUserId())
                .eqIfPresent(MemberOrderDO::getTerminal, reqVO.getTerminal())
                .eqIfPresent(MemberOrderDO::getUserIp, reqVO.getUserIp())
                .eqIfPresent(MemberOrderDO::getStatus, reqVO.getStatus())
                .eqIfPresent(MemberOrderDO::getPayOrderId, reqVO.getPayOrderId())
                .eqIfPresent(MemberOrderDO::getPayStatus, reqVO.getPayStatus())
                .eqIfPresent(MemberOrderDO::getDietitionId,reqVO.getDietitionId())
                .betweenIfPresent(MemberOrderDO::getPayTime, reqVO.getPayTime())
                .eqIfPresent(MemberOrderDO::getPayChannelCode, reqVO.getPayChannelCode())
                .betweenIfPresent(MemberOrderDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MemberOrderDO::getBuyId, reqVO.getBuyId())
                .likeIfPresent(MemberOrderDO::getBuyName, reqVO.getBuyName())
                .eqIfPresent(MemberOrderDO::getPayPrice, reqVO.getPayPrice())
                .orderByDesc(MemberOrderDO::getId));
    }
    default List<MemberOrderDO> selectListByStatusAndCreateTimeLt(Integer status, LocalDateTime createTime) {
        return selectList(new LambdaUpdateWrapper<MemberOrderDO>()
                .eq(MemberOrderDO::getStatus, status)
                .lt(MemberOrderDO::getCreateTime, createTime));
    }

}