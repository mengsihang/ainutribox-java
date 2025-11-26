package com.ainutribox.module.member.dal.mysql.readclass;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.readclass.ReadClassDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.readclass.vo.*;

/**
 * 用户阅读记录 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface ReadClassMapper extends BaseMapperX<ReadClassDO> {

    default PageResult<ReadClassDO> selectPage(ReadClassPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReadClassDO>()
                .eqIfPresent(ReadClassDO::getUserId, reqVO.getUserId())
                .eqIfPresent(ReadClassDO::getClassId, reqVO.getClassId())
                .eqIfPresent(ReadClassDO::getMaxSerialNumber, reqVO.getMaxSerialNumber())
                .betweenIfPresent(ReadClassDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ReadClassDO::getId));
    }

}