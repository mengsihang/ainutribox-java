package com.ainutribox.module.member.service.spotclass;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.member.controller.admin.spotclass.vo.*;
import com.ainutribox.module.member.dal.dataobject.spotclass.SpotClassDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.member.dal.mysql.spotclass.SpotClassMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * 用户点赞表 本表删除使用物理删除 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class SpotClassServiceImpl implements SpotClassService {

    @Resource
    private SpotClassMapper spotClassMapper;

    @Override
    public Long createSpotClass(SpotClassSaveReqVO createReqVO) {
        // 插入
        SpotClassDO spotClass = BeanUtils.toBean(createReqVO, SpotClassDO.class);
        spotClassMapper.insert(spotClass);
        // 返回
        return spotClass.getId();
    }

    @Override
    public void updateSpotClass(SpotClassSaveReqVO updateReqVO) {
        // 校验存在
        validateSpotClassExists(updateReqVO.getId());
        // 更新
        SpotClassDO updateObj = BeanUtils.toBean(updateReqVO, SpotClassDO.class);
        spotClassMapper.updateById(updateObj);
    }

    @Override
    public void deleteSpotClass(Long id) {
        // 校验存在
        validateSpotClassExists(id);
        // 删除
        spotClassMapper.deleteById(id);
    }

    private void validateSpotClassExists(Long id) {
        if (spotClassMapper.selectById(id) == null) {
            throw exception(SPOT_CLASS_NOT_EXISTS);
        }
    }

    @Override
    public SpotClassDO getSpotClass(Long id) {
        return spotClassMapper.selectById(id);
    }

    @Override
    public PageResult<SpotClassDO> getSpotClassPage(SpotClassPageReqVO pageReqVO) {
        return spotClassMapper.selectPage(pageReqVO);
    }

}