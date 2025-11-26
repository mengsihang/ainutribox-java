package com.ainutribox.module.crm.controller.admin.statistics.vo.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户客户统计响应 Base Response VO
 *
 * 目的：可以统一拼接子 VO 的 ownerUserId、ownerUserName 属性
 */
@Data
public class CrmStatisticsCustomerByUserBaseRespVO {

    @Schema(description = "负责人编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long ownerUserId;

    @Schema(description = "负责人", requiredMode = Schema.RequiredMode.REQUIRED, example = "河南小泉山科技")
    private String ownerUserName;

}
