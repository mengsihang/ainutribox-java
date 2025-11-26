package com.ainutribox.module.member.controller.admin.dietitionclass.vo;

import com.ainutribox.framework.common.validation.InEnum;
import com.ainutribox.module.product.enums.spu.ProductSpuStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "用户 App - 课程 Status 更新 Request VO")
@Data
public class ClassUpdateStatusReqVO {

    @Schema(description = "课程编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "课程编号不能为空")
    private Long id;

    @Schema(description = "课程状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "课程状态不能为空")
    @InEnum(ProductSpuStatusEnum.class)
    private Integer status;

}
