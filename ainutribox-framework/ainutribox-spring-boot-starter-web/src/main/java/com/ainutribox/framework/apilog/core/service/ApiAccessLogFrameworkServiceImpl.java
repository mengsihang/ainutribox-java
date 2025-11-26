package com.ainutribox.framework.apilog.core.service;

import com.ainutribox.module.infra.api.logger.ApiAccessLogApi;
import com.ainutribox.module.infra.api.logger.dto.ApiAccessLogCreateReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

/**
 * API 访问日志 Framework Service 实现类
 *
 * 基于 {@link ApiAccessLogApi} 服务，记录访问日志
 *
 * @author 河南小泉山科技
 */
@RequiredArgsConstructor
public class ApiAccessLogFrameworkServiceImpl implements ApiAccessLogFrameworkService {

    private final ApiAccessLogApi apiAccessLogApi;

    @Override
    @Async
    public void createApiAccessLog(ApiAccessLogCreateReqDTO reqDTO) {
        apiAccessLogApi.createApiAccessLog(reqDTO);
    }

}
