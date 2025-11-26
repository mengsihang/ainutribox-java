package com.ainutribox.module.member.controller.admin.vip.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 用户vip Response VO")
@Data
@ExcelIgnoreUnannotated
public class MemberVipRespVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14926")
    @ExcelProperty("主键编号")
    private Long id;

    @Schema(description = "用户id", example = "18706")
    @ExcelProperty("用户id")
    private Long userId;

    @Schema(description = "vip开始时间")
    @ExcelProperty("vip开始时间")
    private LocalDateTime vipStartTime;

    @Schema(description = "vip结束时间")
    @ExcelProperty("vip结束时间")
    private LocalDateTime vipEndTime;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}