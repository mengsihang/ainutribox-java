package com.ainutribox.module.member.service.payclass;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.member.controller.admin.payclass.vo.*;
import com.ainutribox.module.member.dal.dataobject.payclass.PayClassDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.member.dal.mysql.payclass.PayClassMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * 用户购买课程 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class PayClassServiceImpl implements PayClassService {

    @Resource
    private PayClassMapper payClassMapper;

    @Override
    public Long createPayClass(PayClassSaveReqVO createReqVO) {
        // 插入
        PayClassDO payClass = BeanUtils.toBean(createReqVO, PayClassDO.class);
        payClassMapper.insert(payClass);
        // 返回
        return payClass.getId();
    }

    @Override
    public void updatePayClass(PayClassSaveReqVO updateReqVO) {
        // 校验存在
        validatePayClassExists(updateReqVO.getId());
        // 更新
        PayClassDO updateObj = BeanUtils.toBean(updateReqVO, PayClassDO.class);
        payClassMapper.updateById(updateObj);
    }

    @Override
    public void deletePayClass(Long id) {
        // 校验存在
        validatePayClassExists(id);
        // 删除
        payClassMapper.deleteById(id);
    }

    private void validatePayClassExists(Long id) {
        if (payClassMapper.selectById(id) == null) {
            throw exception(PAY_CLASS_NOT_EXISTS);
        }
    }

    @Override
    public PayClassDO getPayClass(Long id) {
        return payClassMapper.selectById(id);
    }

    @Override
    public PageResult<PayClassDO> getPayClassPage(PayClassPageReqVO pageReqVO) {
        return payClassMapper.selectPage(pageReqVO);
    }

}