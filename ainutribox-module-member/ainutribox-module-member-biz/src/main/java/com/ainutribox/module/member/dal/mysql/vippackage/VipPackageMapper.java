package com.ainutribox.module.member.dal.mysql.vippackage;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.vippackage.VipPackageDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.vippackage.vo.*;

/**
 * vip套餐 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface VipPackageMapper extends BaseMapperX<VipPackageDO> {

    default PageResult<VipPackageDO> selectPage(VipPackagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VipPackageDO>()
                .eqIfPresent(VipPackageDO::getPrice, reqVO.getPrice())
                .eqIfPresent(VipPackageDO::getDurationMonth, reqVO.getDurationMonth())
                .likeIfPresent(VipPackageDO::getPackageName, reqVO.getPackageName())
                .eqIfPresent(VipPackageDO::getPackageBrief, reqVO.getPackageBrief())
                .eqIfPresent(VipPackageDO::getActivityPrice, reqVO.getActivityPrice())
                .betweenIfPresent(VipPackageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(VipPackageDO::getId));
    }

}