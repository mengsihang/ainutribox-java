package com.ainutribox.module.product.dal.mysql.tags;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.product.controller.admin.brand.vo.ProductBrandListReqVO;
import com.ainutribox.module.product.dal.dataobject.brand.ProductBrandDO;
import com.ainutribox.module.product.dal.dataobject.tags.TagsDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.product.controller.admin.tags.vo.*;

/**
 * 商品标签 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface TagsMapper extends BaseMapperX<TagsDO> {

    default PageResult<TagsDO> selectPage(TagsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TagsDO>()
                .likeIfPresent(TagsDO::getName, reqVO.getName())
                .eqIfPresent(TagsDO::getColor, reqVO.getColor())
                .eqIfPresent(TagsDO::getSort, reqVO.getSort())
                .eqIfPresent(TagsDO::getDescription, reqVO.getDescription())
                .eqIfPresent(TagsDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TagsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TagsDO::getId));
    }
    default List<TagsDO> selectListByStatus(Integer status) {
        return selectList(TagsDO::getStatus, status);
    }
}