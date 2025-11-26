package com.ainutribox.module.bpm.dal.mysql.definition;


import com.ainutribox.module.bpm.controller.admin.definition.vo.form.BpmFormPageReqVO;
import com.ainutribox.module.bpm.dal.dataobject.definition.BpmFormDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.framework.mybatis.core.query.QueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 动态表单 Mapper
 *
 * @author 风里雾里
 */
@Mapper
public interface BpmFormMapper extends BaseMapperX<BpmFormDO> {

    default PageResult<BpmFormDO> selectPage(BpmFormPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<BpmFormDO>()
                .likeIfPresent("name", reqVO.getName())
                .orderByDesc("id"));
    }

}
