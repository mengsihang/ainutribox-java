package com.ainutribox.module.member.service.dietitionclasscategory;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.dietitionclasscategory.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietitionclasscategory.DietitionClassCategoryDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 营养师课程分类 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface DietitionClassCategoryService {

    /**
     * 创建营养师课程分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDietitionClassCategory(@Valid DietitionClassCategorySaveReqVO createReqVO);

    /**
     * 更新营养师课程分类
     *
     * @param updateReqVO 更新信息
     */
    void updateDietitionClassCategory(@Valid DietitionClassCategorySaveReqVO updateReqVO);

    /**
     * 删除营养师课程分类
     *
     * @param id 编号
     */
    void deleteDietitionClassCategory(Long id);

    /**
     * 获得营养师课程分类
     *
     * @param id 编号
     * @return 营养师课程分类
     */
    DietitionClassCategoryDO getDietitionClassCategory(Long id);

    /**
     * 获得营养师课程分类分页
     *
     * @param pageReqVO 分页查询
     * @return 营养师课程分类分页
     */
    PageResult<DietitionClassCategoryDO> getDietitionClassCategoryPage(DietitionClassCategoryPageReqVO pageReqVO);

    /**
     * 获取指定状态的分类列表
     *
     * @param status 状态
     * @return  返回列表
     */
    List<DietitionClassCategoryDO> getListByStatus(Integer status);
}