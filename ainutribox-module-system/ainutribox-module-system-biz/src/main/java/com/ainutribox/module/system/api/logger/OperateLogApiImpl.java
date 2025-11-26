package com.ainutribox.module.system.api.logger;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.module.system.api.logger.dto.OperateLogCreateReqDTO;
import com.ainutribox.module.system.api.logger.dto.OperateLogPageReqDTO;
import com.ainutribox.module.system.api.logger.dto.OperateLogRespDTO;
import com.ainutribox.module.system.dal.dataobject.logger.OperateLogDO;
import com.ainutribox.module.system.service.logger.OperateLogService;
import com.fhs.core.trans.anno.TransMethodResult;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 操作日志 API 实现类
 *
 * @author 河南小泉山科技
 */
@Service
@Validated
public class OperateLogApiImpl implements OperateLogApi {

    @Resource
    private OperateLogService operateLogService;

    @Override
    @Async
    public void createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        operateLogService.createOperateLog(createReqDTO);
    }

    @Override
    @TransMethodResult
    public PageResult<OperateLogRespDTO> getOperateLogPage(OperateLogPageReqDTO pageReqVO) {
        PageResult<OperateLogDO> operateLogPage = operateLogService.getOperateLogPage(pageReqVO);
        return BeanUtils.toBean(operateLogPage, OperateLogRespDTO.class);
    }

}
