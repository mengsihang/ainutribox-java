package com.ainutribox.module.member.controller.admin.agent.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.ainutribox.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.ainutribox.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 代理商记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AgentPageReqVO extends PageParam {

    @Schema(description = "用户id", example = "22631")
    private Long userId;

    @Schema(description = "描述内容")
    private String content;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "申请状态：0 未通过 1 已通过", example = "1")
    private Integer status;

    @Schema(description = "手机号")
    private Long tel;

    @Schema(description = "身份证正面")
    private String idPicFront;

    @Schema(description = "身份证反面")
    private String idPicBack;

    @Schema(description = "证书")
    private String certificatePic;

}