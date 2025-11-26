package com.ainutribox.module.member.service.report;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import com.ainutribox.framework.test.core.ut.BaseDbUnitTest;

import com.ainutribox.module.member.controller.admin.report.vo.*;
import com.ainutribox.module.member.dal.dataobject.report.ReportDO;
import com.ainutribox.module.member.dal.mysql.report.ReportMapper;
import com.ainutribox.framework.common.pojo.PageResult;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;
import static com.ainutribox.framework.test.core.util.AssertUtils.*;
import static com.ainutribox.framework.test.core.util.RandomUtils.*;
import static com.ainutribox.framework.common.util.date.LocalDateTimeUtils.*;
import static com.ainutribox.framework.common.util.object.ObjectUtils.*;
import static com.ainutribox.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link ReportServiceImpl} 的单元测试类
 *
 * @author 小泉山网络科技
 */
@Import(ReportServiceImpl.class)
public class ReportServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ReportServiceImpl reportService;

    @Resource
    private ReportMapper reportMapper;

    @Test
    public void testCreateReport_success() {
        // 准备参数
        ReportSaveReqVO createReqVO = randomPojo(ReportSaveReqVO.class).setId(null);

        // 调用
        Long reportId = reportService.createReport(createReqVO);
        // 断言
        assertNotNull(reportId);
        // 校验记录的属性是否正确
        ReportDO report = reportMapper.selectById(reportId);
        assertPojoEquals(createReqVO, report, "id");
    }

    @Test
    public void testUpdateReport_success() {
        // mock 数据
        ReportDO dbReport = randomPojo(ReportDO.class);
        reportMapper.insert(dbReport);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ReportSaveReqVO updateReqVO = randomPojo(ReportSaveReqVO.class, o -> {
            o.setId(dbReport.getId()); // 设置更新的 ID
        });

        // 调用
        reportService.updateReport(updateReqVO);
        // 校验是否更新正确
        ReportDO report = reportMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, report);
    }

    @Test
    public void testUpdateReport_notExists() {
        // 准备参数
        ReportSaveReqVO updateReqVO = randomPojo(ReportSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> reportService.updateReport(updateReqVO), REPORT_NOT_EXISTS);
    }

    @Test
    public void testDeleteReport_success() {
        // mock 数据
        ReportDO dbReport = randomPojo(ReportDO.class);
        reportMapper.insert(dbReport);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbReport.getId();

        // 调用
        reportService.deleteReport(id);
        // 校验数据不存在了
        assertNull(reportMapper.selectById(id));
    }

    @Test
    public void testDeleteReport_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> reportService.deleteReport(id), REPORT_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetReportPage() {
        // mock 数据
        ReportDO dbReport = randomPojo(ReportDO.class, o -> { // 等会查询到
            o.setMemberId(null);
            o.setContent(null);
            o.setSort(null);
            o.setStatus(null);
            o.setCreateTime(null);
        });
        reportMapper.insert(dbReport);
        // 测试 memberId 不匹配
        reportMapper.insert(cloneIgnoreId(dbReport, o -> o.setMemberId(null)));
        // 测试 content 不匹配
        reportMapper.insert(cloneIgnoreId(dbReport, o -> o.setContent(null)));
        // 测试 sort 不匹配
        reportMapper.insert(cloneIgnoreId(dbReport, o -> o.setSort(null)));
        // 测试 status 不匹配
        reportMapper.insert(cloneIgnoreId(dbReport, o -> o.setStatus(null)));
        // 测试 createTime 不匹配
        reportMapper.insert(cloneIgnoreId(dbReport, o -> o.setCreateTime(null)));
        // 准备参数
        ReportPageReqVO reqVO = new ReportPageReqVO();
        reqVO.setMemberId(null);
        reqVO.setContent(null);
        reqVO.setSort(null);
        reqVO.setStatus(null);
        reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

        // 调用
        PageResult<ReportDO> pageResult = reportService.getReportPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbReport, pageResult.getList().get(0));
    }

}