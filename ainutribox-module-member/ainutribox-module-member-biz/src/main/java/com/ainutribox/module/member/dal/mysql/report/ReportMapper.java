package com.ainutribox.module.member.dal.mysql.report;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.report.ReportDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.report.vo.*;

/**
 * 用户报告 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface ReportMapper extends BaseMapperX<ReportDO> {

    default PageResult<ReportDO> selectPage(ReportPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReportDO>()
                .eqIfPresent(ReportDO::getContent, reqVO.getContent())
                .eqIfPresent(ReportDO::getSort, reqVO.getSort())
                .eqIfPresent(ReportDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ReportDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ReportDO::getMemberId, reqVO.getMemberId())
                .likeIfPresent(ReportDO::getReportName, reqVO.getReportName())
                .orderByDesc(ReportDO::getId));
    }

}