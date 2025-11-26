package com.ainutribox.module.trade.controller.app.brokerage.vo.withdraw;

import com.ainutribox.module.trade.enums.brokerage.BrokerageWithdrawTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户 App - 分销提现 Response VO")
@Data
public class AppBrokerageWithdrawRespVO {

    @Schema(description = "提现编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long id;

    @Schema(description = "提现状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer status;

    @Schema(description = "提现状态名", requiredMode = Schema.RequiredMode.REQUIRED, example = "审核中")
    private String statusName;

    @Schema(description = "提现金额，单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer price;

    @Schema(description = "提现时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "提现类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer type;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String accountNo;

    @Schema(description = "银行名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bankName;

    @Schema(description = "开户地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bankAddress;

    @Schema(description = "审核驳回原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private String auditReason;

}
