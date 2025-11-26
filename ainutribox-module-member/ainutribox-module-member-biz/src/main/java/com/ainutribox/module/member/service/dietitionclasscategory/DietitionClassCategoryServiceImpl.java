package com.ainutribox.module.member.service.dietitionclasscategory;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.member.controller.admin.dietitionclasscategory.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietitionclasscategory.DietitionClassCategoryDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.member.dal.mysql.dietitionclasscategory.DietitionClassCategoryMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * 营养师课程分类 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class DietitionClassCategoryServiceImpl implements DietitionClassCategoryService {

    @Resource
    private DietitionClassCategoryMapper dietitionClassCategoryMapper;

    @Override
    public Long createDietitionClassCategory(DietitionClassCategorySaveReqVO createReqVO) {
        // 插入
        DietitionClassCategoryDO dietitionClassCategory = BeanUtils.toBean(createReqVO, DietitionClassCategoryDO.class);
        dietitionClassCategoryMapper.insert(dietitionClassCategory);
        // 返回
        return dietitionClassCategory.getId();
    }

    @Override
    public void updateDietitionClassCategory(DietitionClassCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateDietitionClassCategoryExists(updateReqVO.getId());
        // 更新
        DietitionClassCategoryDO updateObj = BeanUtils.toBean(updateReqVO, DietitionClassCategoryDO.class);
        dietitionClassCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteDietitionClassCategory(Long id) {
        // 校验存在
        validateDietitionClassCategoryExists(id);
        // 删除
        dietitionClassCategoryMapper.deleteById(id);
    }

    private void validateDietitionClassCategoryExists(Long id) {
        if (dietitionClassCategoryMapper.selectById(id) == null) {
            throw exception(DIETITION_CLASS_CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public DietitionClassCategoryDO getDietitionClassCategory(Long id) {
        return dietitionClassCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<DietitionClassCategoryDO> getDietitionClassCategoryPage(DietitionClassCategoryPageReqVO pageReqVO) {
        return dietitionClassCategoryMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DietitionClassCategoryDO> getListByStatus(Integer status) {
        return dietitionClassCategoryMapper.getListByStatus(status);
    }

}