package com.ainutribox.module.product.service.dietitian;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.product.controller.admin.dietitian.vo.*;
import com.ainutribox.module.product.dal.dataobject.dietitian.DietitianDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.product.dal.mysql.dietitian.DietitianMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.product.enums.ErrorCodeConstants.*;

/**
 * 营养师自组营养包 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class DietitianServiceImpl implements DietitianService {

    @Resource
    private DietitianMapper dietitianMapper;

    @Override
    public Long createDietitian(DietitianSaveReqVO createReqVO) {
        // 插入
        DietitianDO dietitian = BeanUtils.toBean(createReqVO, DietitianDO.class);
        dietitianMapper.insert(dietitian);
        // 返回
        return dietitian.getId();
    }

    @Override
    public void updateDietitian(DietitianSaveReqVO updateReqVO) {
        // 校验存在
        validateDietitianExists(updateReqVO.getId());
        // 更新
        DietitianDO updateObj = BeanUtils.toBean(updateReqVO, DietitianDO.class);
        dietitianMapper.updateById(updateObj);
    }

    @Override
    public void deleteDietitian(Long id) {
        // 校验存在
        validateDietitianExists(id);
        // 删除
        dietitianMapper.deleteById(id);
    }

    private void validateDietitianExists(Long id) {
        if (dietitianMapper.selectById(id) == null) {
            throw exception(DIETITIAN_NOT_EXISTS);
        }
    }

    @Override
    public DietitianDO getDietitian(Long id) {
        return dietitianMapper.selectById(id);
    }

    @Override
    public PageResult<DietitianDO> getDietitianPage(DietitianPageReqVO pageReqVO) {
        return dietitianMapper.selectPage(pageReqVO);
    }

}