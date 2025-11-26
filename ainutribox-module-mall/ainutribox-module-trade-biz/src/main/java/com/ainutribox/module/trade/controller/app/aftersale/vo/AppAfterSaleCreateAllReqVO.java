package com.ainutribox.module.trade.controller.app.aftersale.vo;

import com.ainutribox.framework.common.validation.InEnum;
import com.ainutribox.module.trade.enums.aftersale.AfterSaleWayEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Schema(description = "用户 App - 交易售后创建 Request VO")
@Data
public class AppAfterSaleCreateAllReqVO {

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "订单编号不能为空")
    private Long orderId;

    @Schema(description = "售后方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "售后方式不能为空")
    @InEnum(value = AfterSaleWayEnum.class, message = "售后方式必须是 {value}")
    private Integer way;

    @Schema(description = "申请原因", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "申请原因不能为空")
    private String applyReason;

    @Schema(description = "补充描述", example = "商品质量不好")
    private String applyDescription;

    @Schema(description = "补充凭证图片", example = "https://www.iocoder.cn/1.png, https://www.iocoder.cn/2.png")
    private List<String> applyPicUrls;

}
