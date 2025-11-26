package com.ainutribox.module.member.controller.admin.dietition.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 营养师信息新增/修改 Request VO")
@Data
public class DietitionInfoSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1673")
    private Long id;

    @Schema(description = "用户id", example = "27536")
    private Long userId;

    @Schema(description = "描述内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "描述内容不能为空")
    private String content;

    @Schema(description = "评分")
    private Long score;

    @Schema(description = "昵称", example = "王五")
    private String nickName;

    @Schema(description = "经验")
    private Long experience;

    @Schema(description = "好评率")
    private Long favorableRate;

    @Schema(description = "咨询量")
    private Long adviceNum;

    @Schema(description = "案例数")
    private Long caseNum;

    @Schema(description = "营养师等级 0 初级 1 中级 2 高级")
    private Integer level;

    @Schema(description = "图片")
    private String pic;

    @Schema(description = "详情图片")
    private String infoPic;

    @Schema(description = "名片背景")
    private String cardPic;

    @Schema(description = "手机号")
    private String phone;

}