package com.ainutribox.module.product.dal.mysql.dietitian;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.product.dal.dataobject.dietitian.DietitianDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.product.controller.admin.dietitian.vo.*;

/**
 * 营养师自组营养包 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface DietitianMapper extends BaseMapperX<DietitianDO> {

    default PageResult<DietitianDO> selectPage(DietitianPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DietitianDO>()
                .eqIfPresent(DietitianDO::getProductText, reqVO.getProductText())
                .eqIfPresent(DietitianDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DietitianDO::getDietitianId, reqVO.getDietitianId())
                .betweenIfPresent(DietitianDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DietitianDO::getId));
    }

}