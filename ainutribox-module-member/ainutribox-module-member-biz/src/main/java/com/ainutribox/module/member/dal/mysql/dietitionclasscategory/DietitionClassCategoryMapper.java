package com.ainutribox.module.member.dal.mysql.dietitionclasscategory;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.dietitionclasscategory.DietitionClassCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.dietitionclasscategory.vo.*;

/**
 * 营养师课程分类 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface DietitionClassCategoryMapper extends BaseMapperX<DietitionClassCategoryDO> {

    default PageResult<DietitionClassCategoryDO> selectPage(DietitionClassCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DietitionClassCategoryDO>()
                .likeIfPresent(DietitionClassCategoryDO::getName, reqVO.getName())
                .eqIfPresent(DietitionClassCategoryDO::getPicUrl, reqVO.getPicUrl())
                .eqIfPresent(DietitionClassCategoryDO::getSort, reqVO.getSort())
                .eqIfPresent(DietitionClassCategoryDO::getDescription, reqVO.getDescription())
                .eqIfPresent(DietitionClassCategoryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(DietitionClassCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DietitionClassCategoryDO::getId));
    }
    default  List<DietitionClassCategoryDO> getListByStatus(Integer status){
        return selectList(DietitionClassCategoryDO::getStatus, status);
    }
}