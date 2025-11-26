package com.ainutribox.module.member.controller.admin.dietition.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.ainutribox.framework.excel.core.annotations.DictFormat;
import com.ainutribox.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 营养师信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DietitionInfoRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1673")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "用户id", example = "27536")
    @ExcelProperty("用户id")
    private Long userId;

    @Schema(description = "描述内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("描述内容")
    private String content;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "评分")
    @ExcelProperty("评分")
    private Long score;

    @Schema(description = "昵称", example = "王五")
    @ExcelProperty("昵称")
    private String nickName;

    @Schema(description = "手机号", example = "186")
    @ExcelProperty("手机号")
    private String phone;

    @Schema(description = "经验")
    @ExcelProperty("经验")
    private Long experience;

    @Schema(description = "好评率")
    @ExcelProperty("好评率")
    private Long favorableRate;

    @Schema(description = "咨询量")
    @ExcelProperty("咨询量")
    private Long adviceNum;

    @Schema(description = "案例数")
    @ExcelProperty("案例数")
    private Long caseNum;

    @Schema(description = "营养师等级 0 初级 1 中级 2 高级")
    @ExcelProperty(value = "营养师等级 0 初级 1 中级 2 高级", converter = DictConvert.class)
    @DictFormat("member_dietition_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer level;

    @Schema(description = "图片")
    @ExcelProperty("图片")
    private String pic;

    @Schema(description = "详情图片")
    @ExcelProperty("详情图片")
    private String infoPic;

    @Schema(description = "名片背景")
    @ExcelProperty("名片背景")
    private String cardPic;

}