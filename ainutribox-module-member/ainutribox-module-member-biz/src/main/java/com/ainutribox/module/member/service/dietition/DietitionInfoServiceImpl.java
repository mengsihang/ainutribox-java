package com.ainutribox.module.member.service.dietition;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.member.controller.admin.dietition.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionInfoDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.member.dal.mysql.dietition.DietitionInfoMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * 营养师信息 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class DietitionInfoServiceImpl implements DietitionInfoService {

    @Resource
    private DietitionInfoMapper dietitionInfoMapper;

    @Override
    public Long createDietitionInfo(DietitionInfoSaveReqVO createReqVO) {
        // 插入
        DietitionInfoDO dietitionInfo = BeanUtils.toBean(createReqVO, DietitionInfoDO.class);
        dietitionInfoMapper.insert(dietitionInfo);
        // 返回
        return dietitionInfo.getId();
    }

    @Override
    public void updateDietitionInfo(DietitionInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateDietitionInfoExists(updateReqVO.getId());
        // 更新
        DietitionInfoDO updateObj = BeanUtils.toBean(updateReqVO, DietitionInfoDO.class);
        dietitionInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteDietitionInfo(Long id) {
        // 校验存在
        validateDietitionInfoExists(id);
        // 删除
        dietitionInfoMapper.deleteById(id);
    }

    private void validateDietitionInfoExists(Long id) {
        if (dietitionInfoMapper.selectById(id) == null) {
            throw exception(DIETITION_INFO_NOT_EXISTS);
        }
    }

    @Override
    public DietitionInfoDO getDietitionInfo(Long id) {
        return dietitionInfoMapper.selectById(id);
    }

    @Override
    public PageResult<DietitionInfoDO> getDietitionInfoPage(DietitionInfoPageReqVO pageReqVO) {
        return dietitionInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public DietitionInfoDO getDietitionByUserId(Long userId) {
        return dietitionInfoMapper.selectOne("user_id",userId,"status",0);
    }


}
