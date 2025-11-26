package com.ainutribox.module.member.dal.mysql.dietition;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.dietition.vo.*;

/**
 * 营养师记录 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface DietitionMapper extends BaseMapperX<DietitionDO> {

    default PageResult<DietitionDO> selectPage(DietitionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DietitionDO>()
                .eqIfPresent(DietitionDO::getUserId, reqVO.getUserId())
                .eqIfPresent(DietitionDO::getContent, reqVO.getContent())
                .betweenIfPresent(DietitionDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DietitionDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DietitionDO::getTel, reqVO.getTel())
                .eqIfPresent(DietitionDO::getIdPicFront, reqVO.getIdPicFront())
                .eqIfPresent(DietitionDO::getIdPicBack, reqVO.getIdPicBack())
                .eqIfPresent(DietitionDO::getCertificatePic, reqVO.getCertificatePic())
                .orderByDesc(DietitionDO::getId));
    }

}