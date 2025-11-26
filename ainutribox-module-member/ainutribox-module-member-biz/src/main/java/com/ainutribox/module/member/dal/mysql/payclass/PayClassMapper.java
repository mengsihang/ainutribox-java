package com.ainutribox.module.member.dal.mysql.payclass;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.payclass.PayClassDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.payclass.vo.*;

/**
 * 用户购买课程 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface PayClassMapper extends BaseMapperX<PayClassDO> {

    default PageResult<PayClassDO> selectPage(PayClassPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PayClassDO>()
                .eqIfPresent(PayClassDO::getUserId, reqVO.getUserId())
                .eqIfPresent(PayClassDO::getClassId, reqVO.getClassId())
                .eqIfPresent(PayClassDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(PayClassDO::getDietition, reqVO.getDietition())
                .betweenIfPresent(PayClassDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PayClassDO::getId));
    }

}