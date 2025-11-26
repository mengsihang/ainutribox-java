package com.ainutribox.module.member.controller.admin.dietitionclasschild.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 课程小节 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DietitionClassChildRespVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "27563")
    @ExcelProperty("主键编号")
    private Long id;

    @Schema(description = "课程主表id", example = "10413")
    @ExcelProperty("课程主表id")
    private Long classId;

    @Schema(description = "小节名称", example = "芋艿")
    @ExcelProperty("小节名称")
    private String classHoureName;

    @Schema(description = "小节详情")
    @ExcelProperty("小节详情")
    private String classHoureDetail;

    @Schema(description = "营养师ID", example = "14366")
    @ExcelProperty("营养师ID")
    private Long dietitionId;

    @Schema(description = "音频url", example = "https://www.iocoder.cn")
    @ExcelProperty("音频url")
    private String videoUrl;

    @Schema(description = "音频时长")
    @ExcelProperty("音频时长")
    private Integer videoTime;

    @Schema(description = "小节类型 0试听 1会员免费 2收费", example = "2")
    @ExcelProperty("小节类型 0试听 1会员免费 2收费")
    private Integer childType;

    @Schema(description = "小节序号")
    @ExcelProperty("小节序号")
    private Integer serialNumber;

    @Schema(description = "状态 0上架 1下架", example = "2")
    @ExcelProperty("状态 0上架 1下架")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}