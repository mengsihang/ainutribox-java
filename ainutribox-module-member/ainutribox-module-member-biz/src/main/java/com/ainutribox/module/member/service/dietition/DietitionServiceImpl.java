package com.ainutribox.module.member.service.dietition;

import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionInfoDO;
import com.ainutribox.module.member.dal.mysql.dietition.DietitionInfoMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.member.controller.admin.dietition.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.member.dal.mysql.dietition.DietitionMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * 营养师记录 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class DietitionServiceImpl implements DietitionService {

    @Resource
    private DietitionMapper dietitionMapper;

    @Resource
    private DietitionInfoMapper dietitionInfoMapper;

    @Override
    public Long createDietition(DietitionSaveReqVO createReqVO) {
        // 插入
        DietitionDO dietition = BeanUtils.toBean(createReqVO, DietitionDO.class);
        dietitionMapper.insert(dietition);
        // 返回
        return dietition.getId();
    }

    @Override
    public void updateDietition(DietitionSaveReqVO updateReqVO) {
        // 校验存在
        DietitionDO dietitionDO = validateDietitionExists(updateReqVO.getId());
        // 更新
        DietitionDO updateObj = BeanUtils.toBean(updateReqVO, DietitionDO.class);
        dietitionMapper.updateById(updateObj);

        if(dietitionDO.getStatus() != (int)updateReqVO.getStatus() ){

            DietitionInfoDO dietitionInfoDO = new DietitionInfoDO();
            dietitionInfoDO.setUserId(updateReqVO.getId());
            dietitionInfoDO.setStatus(0);
            if(updateReqVO.getStatus() == 0 || updateReqVO.getStatus() == 2){
                dietitionInfoDO.setStatus(1);
            }
            LambdaQueryWrapperX<DietitionInfoDO> dietitionMapperLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
            dietitionMapperLambdaQueryWrapperX.eq(DietitionInfoDO::getUserId,updateReqVO.getId());
            dietitionInfoMapper.update(dietitionInfoDO,dietitionMapperLambdaQueryWrapperX);
        }

    }

    @Override
    public void deleteDietition(Long id) {
        // 校验存在
        validateDietitionExists(id);
        // 删除
        dietitionMapper.deleteById(id);
    }

    private DietitionDO validateDietitionExists(Long id) {
        DietitionDO dietitionDO = dietitionMapper.selectById(id);
        if (dietitionMapper.selectById(id) == null) {
            throw exception(DIETITION_NOT_EXISTS);
        }
        return dietitionDO;
    }

    @Override
    public DietitionDO getDietition(Long id) {
        return dietitionMapper.selectById(id);
    }

    @Override
    public DietitionDO getDietitionByUserId(Long id) {
        return dietitionMapper.selectOne("user_id", id);
    }

    @Override
    public PageResult<DietitionDO> getDietitionPage(DietitionPageReqVO pageReqVO) {
        return dietitionMapper.selectPage(pageReqVO);
    }

}