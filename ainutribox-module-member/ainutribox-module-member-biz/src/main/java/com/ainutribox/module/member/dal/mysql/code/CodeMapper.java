package com.ainutribox.module.member.dal.mysql.code;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.code.CodeDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.code.vo.*;

/**
 * 积分兑换码 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface CodeMapper extends BaseMapperX<CodeDO> {

    default PageResult<CodeDO> selectPage(CodePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CodeDO>()
                .eqIfPresent(CodeDO::getPoint, reqVO.getPoint())
                .eqIfPresent(CodeDO::getCode, reqVO.getCode())
                .betweenIfPresent(CodeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CodeDO::getId));
    }

}