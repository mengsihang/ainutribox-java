package com.ainutribox.module.member.dal.mysql.dietitionclasschild;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.dietitionclasschild.DietitionClassChildDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.dietitionclasschild.vo.*;

/**
 * 课程小节 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface DietitionClassChildMapper extends BaseMapperX<DietitionClassChildDO> {

    default PageResult<DietitionClassChildDO> selectPage(DietitionClassChildPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DietitionClassChildDO>()
                .eqIfPresent(DietitionClassChildDO::getClassId, reqVO.getClassId())
                .likeIfPresent(DietitionClassChildDO::getClassHoureName, reqVO.getClassHoureName())
                .eqIfPresent(DietitionClassChildDO::getClassHoureDetail, reqVO.getClassHoureDetail())
                .eqIfPresent(DietitionClassChildDO::getDietitionId, reqVO.getDietitionId())
                .eqIfPresent(DietitionClassChildDO::getVideoUrl, reqVO.getVideoUrl())
                .betweenIfPresent(DietitionClassChildDO::getVideoTime, reqVO.getVideoTime())
                .eqIfPresent(DietitionClassChildDO::getChildType, reqVO.getChildType())
                .eqIfPresent(DietitionClassChildDO::getSerialNumber, reqVO.getSerialNumber())
                .eqIfPresent(DietitionClassChildDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(DietitionClassChildDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DietitionClassChildDO::getId));
    }

}