package com.ainutribox.module.member.controller.admin.agent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.ainutribox.framework.excel.core.annotations.DictFormat;
import com.ainutribox.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 代理商记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AgentRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11048")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "用户id", example = "22631")
    @ExcelProperty("用户id")
    private Long userId;

    @Schema(description = "描述内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("描述内容")
    private String content;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "申请状态：0 未通过 1 已通过", example = "1")
    @ExcelProperty(value = "申请状态：0 未通过 1 已通过", converter = DictConvert.class)
    @DictFormat("apply_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "手机号")
    @ExcelProperty("手机号")
    private Long tel;

    @Schema(description = "身份证正面")
    @ExcelProperty("身份证正面")
    private String idPicFront;

    @Schema(description = "身份证反面")
    @ExcelProperty("身份证反面")
    private String idPicBack;

    @Schema(description = "证书")
    @ExcelProperty("证书")
    private String certificatePic;

}