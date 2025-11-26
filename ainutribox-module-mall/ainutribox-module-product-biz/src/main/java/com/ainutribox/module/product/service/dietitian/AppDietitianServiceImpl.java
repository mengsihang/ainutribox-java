package com.ainutribox.module.product.service.dietitian;

import com.ainutribox.framework.common.enums.CommonStatusEnum;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.module.product.controller.admin.spu.vo.ProductSpuUpdateStatusReqVO;
import com.ainutribox.module.product.dal.dataobject.dietitian.DietitianDO;
import com.ainutribox.module.product.dal.mysql.dietitian.DietitianMapper;
import com.ainutribox.module.product.enums.spu.ProductSpuStatusEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.product.enums.ErrorCodeConstants.SPU_NOT_EXISTS;


/**
 * App 营养师商品
 *
 * @author lucifer
 */
@Service
public class AppDietitianServiceImpl implements AppDietitianService{

    @Resource
    private DietitianMapper dietitianMapper;

    @Override
    public List<DietitianDO> getDietitianList(Long userId,Integer type) {

        LambdaQueryWrapperX<DietitianDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(DietitianDO::getDeleted, CommonStatusEnum.ENABLE.getStatus());
        queryWrapper.eq(DietitianDO::getDietitianId,userId);
        if(type == 2){
            queryWrapper.eq(DietitianDO::getStatus, ProductSpuStatusEnum.ENABLE);
        }

        return dietitianMapper.selectList(queryWrapper);
    }

    @Override
    public void updateSpuStatus(ProductSpuUpdateStatusReqVO updateReqVO) {
        // 校验存在
        validateSpuExists(updateReqVO.getId());

        // 更新状态
        DietitianDO dietitianDO = dietitianMapper.selectById(updateReqVO.getId()).setStatus(updateReqVO.getStatus());
        dietitianMapper.updateById(dietitianDO);
    }

    private void validateSpuExists(Long id) {
        if (dietitianMapper.selectById(id) == null) {
            throw exception(SPU_NOT_EXISTS);
        }
    }
}
