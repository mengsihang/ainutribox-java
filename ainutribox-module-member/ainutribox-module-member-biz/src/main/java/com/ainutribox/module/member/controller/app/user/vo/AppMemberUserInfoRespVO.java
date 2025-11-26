package com.ainutribox.module.member.controller.app.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "用户 APP - 用户个人信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppMemberUserInfoRespVO {

    private Long userId;

    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    private String nickname;

    @Schema(description = "用户头像", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn/xxx.png")
    private String avatar;

    @Schema(description = "用户手机号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15601691300")
    private String mobile;

    @Schema(description = "用户性别", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sex;

    @Schema(description = "积分", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer point;

    @Schema(description = "经验值", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer experience;

    @Schema(description = "用户等级")
    private Level level;

    @Schema(description = "是否成为推广员", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean brokerageEnabled;

    @Schema(description = "分销商ID")
    private Long distributorId;

    @Schema(description = "营养师ID")
    private Long nutritionId;

    @Schema(description = "APP推荐人ID")
    private Long memberId;

    @Schema(description = "微信openId")
    private String openId;

    @Schema(description = "用户类型")
    private Integer memberType;

    @Schema(description = "营养师状态 -1不存在记录 0未通过 1已通过")
    private Integer agentType;

    @Schema(description = "供应商状态 -1不存在记录 0未通过 1已通过")
    private Integer dietitionType;

    @Schema(description = "vip状态 是否是会员 0否 1是")
    private Integer vipStatus;

    @Schema(description = "用户 App - 会员等级")
    @Data
    public static class Level {

        @Schema(description = "等级编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Long id;

        @Schema(description = "等级名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
        private String name;

        @Schema(description = "等级", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Integer level;

        @Schema(description = "等级图标", example = "https://www.iocoder.cn/ainutribox.jpg")
        private String icon;

    }

}
