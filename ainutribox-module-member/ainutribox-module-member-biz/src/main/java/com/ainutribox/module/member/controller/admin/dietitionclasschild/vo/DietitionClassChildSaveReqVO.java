package com.ainutribox.module.member.controller.admin.dietitionclasschild.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 课程小节新增/修改 Request VO")
@Data
public class DietitionClassChildSaveReqVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "27563")
    private Long id;

    @Schema(description = "课程主表id", example = "10413")
    private Long classId;

    @Schema(description = "小节名称", example = "芋艿")
    private String classHoureName;

    @Schema(description = "小节详情")
    private String classHoureDetail;

    @Schema(description = "营养师ID", example = "14366")
    private Long dietitionId;

    @Schema(description = "音频url", example = "https://www.iocoder.cn")
    private String videoUrl;

    @Schema(description = "音频时长")
    private Integer videoTime;

    @Schema(description = "小节类型 0试听 1会员免费 2收费", example = "2")
    private Integer childType;

    @Schema(description = "小节序号")
    private Integer serialNumber;

    @Schema(description = "状态 0上架 1下架", example = "2")
    private Integer status;

}