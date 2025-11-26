package com.ainutribox.module.member.service.report;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.report.vo.*;
import com.ainutribox.module.member.dal.dataobject.report.ReportDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 用户报告 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface ReportService {

    /**
     * 创建用户报告
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReport(@Valid ReportSaveReqVO createReqVO);

    /**
     * 更新用户报告
     *
     * @param updateReqVO 更新信息
     */
    void updateReport(@Valid ReportSaveReqVO updateReqVO);

    /**
     * 删除用户报告
     *
     * @param id 编号
     */
    void deleteReport(Long id);

    /**
     * 获得用户报告
     *
     * @param id 编号
     * @return 用户报告
     */
    ReportDO getReport(Long id);

    /**
     * 获得用户报告分页
     *
     * @param pageReqVO 分页查询
     * @return 用户报告分页
     */
    PageResult<ReportDO> getReportPage(ReportPageReqVO pageReqVO);

}