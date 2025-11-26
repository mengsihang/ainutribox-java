package com.ainutribox.module.member.dal.mysql.spotclass;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.spotclass.SpotClassDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.spotclass.vo.*;

/**
 * 用户点赞表 本表删除使用物理删除 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface SpotClassMapper extends BaseMapperX<SpotClassDO> {

    default PageResult<SpotClassDO> selectPage(SpotClassPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SpotClassDO>()
                .eqIfPresent(SpotClassDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SpotClassDO::getClassId, reqVO.getClassId())
                .betweenIfPresent(SpotClassDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SpotClassDO::getId));
    }

}