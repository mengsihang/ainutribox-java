package com.ainutribox.module.trade.service.dietitianorderlog;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.trade.controller.admin.dietitianorderlog.vo.*;
import com.ainutribox.module.trade.dal.dataobject.dietitianorderlog.DietitianOrderLogDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.trade.dal.mysql.dietitianorderlog.DietitianOrderLogMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.trade.enums.ErrorCodeConstants.*;

/**
 * 营养师自组营养包售卖订单日志 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class DietitianOrderLogServiceImpl implements DietitianOrderLogService {

    @Resource
    private DietitianOrderLogMapper dietitianOrderLogMapper;

    @Override
    public Long createDietitianOrderLog(DietitianOrderLogSaveReqVO createReqVO) {
        // 插入
        DietitianOrderLogDO dietitianOrderLog = BeanUtils.toBean(createReqVO, DietitianOrderLogDO.class);
        dietitianOrderLogMapper.insert(dietitianOrderLog);
        // 返回
        return dietitianOrderLog.getId();
    }

    @Override
    public void updateDietitianOrderLog(DietitianOrderLogSaveReqVO updateReqVO) {
        // 校验存在
        validateDietitianOrderLogExists(updateReqVO.getId());
        // 更新
        DietitianOrderLogDO updateObj = BeanUtils.toBean(updateReqVO, DietitianOrderLogDO.class);
        dietitianOrderLogMapper.updateById(updateObj);
    }

    @Override
    public void deleteDietitianOrderLog(Long id) {
        // 校验存在
        validateDietitianOrderLogExists(id);
        // 删除
        dietitianOrderLogMapper.deleteById(id);
    }

    private void validateDietitianOrderLogExists(Long id) {
        if (dietitianOrderLogMapper.selectById(id) == null) {
            throw exception(DIETITIAN_ORDER_LOG_NOT_EXISTS);
        }
    }

    @Override
    public DietitianOrderLogDO getDietitianOrderLog(Long id) {
        return dietitianOrderLogMapper.selectById(id);
    }

    @Override
    public PageResult<DietitianOrderLogDO> getDietitianOrderLogPage(DietitianOrderLogPageReqVO pageReqVO) {
        return dietitianOrderLogMapper.selectPage(pageReqVO);
    }

}