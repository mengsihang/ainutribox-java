package com.ainutribox.module.member.service.report;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.member.controller.admin.report.vo.*;
import com.ainutribox.module.member.dal.dataobject.report.ReportDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.member.dal.mysql.report.ReportMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * 用户报告 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportMapper reportMapper;

    @Override
    public Long createReport(ReportSaveReqVO createReqVO) {
        // 插入
        ReportDO report = BeanUtils.toBean(createReqVO, ReportDO.class);
        reportMapper.insert(report);
        // 返回
        return report.getId();
    }

    @Override
    public void updateReport(ReportSaveReqVO updateReqVO) {
        // 校验存在
        validateReportExists(updateReqVO.getId());
        // 更新
        ReportDO updateObj = BeanUtils.toBean(updateReqVO, ReportDO.class);
        reportMapper.updateById(updateObj);
    }

    @Override
    public void deleteReport(Long id) {
        // 校验存在
        validateReportExists(id);
        // 删除
        reportMapper.deleteById(id);
    }

    private void validateReportExists(Long id) {
        if (reportMapper.selectById(id) == null) {
            throw exception(REPORT_NOT_EXISTS);
        }
    }

    @Override
    public ReportDO getReport(Long id) {
        return reportMapper.selectById(id);
    }

    @Override
    public PageResult<ReportDO> getReportPage(ReportPageReqVO pageReqVO) {
        return reportMapper.selectPage(pageReqVO);
    }

}