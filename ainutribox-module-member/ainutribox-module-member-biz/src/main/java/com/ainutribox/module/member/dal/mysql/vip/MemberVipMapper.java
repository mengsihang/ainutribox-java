package com.ainutribox.module.member.dal.mysql.vip;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.vip.MemberVipDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.vip.vo.*;

/**
 * 用户vip Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface MemberVipMapper extends BaseMapperX<MemberVipDO> {

    default PageResult<MemberVipDO> selectPage(MemberVipPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MemberVipDO>()
                .eqIfPresent(MemberVipDO::getUserId, reqVO.getUserId())
                .betweenIfPresent(MemberVipDO::getVipStartTime, reqVO.getVipStartTime())
                .betweenIfPresent(MemberVipDO::getVipEndTime, reqVO.getVipEndTime())
                .betweenIfPresent(MemberVipDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MemberVipDO::getId));
    }

}