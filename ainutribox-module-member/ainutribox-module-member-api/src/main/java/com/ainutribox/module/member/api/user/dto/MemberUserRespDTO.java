package com.ainutribox.module.member.api.user.dto;

import com.ainutribox.framework.common.enums.CommonStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息 Response DTO
 *
 * @author 河南小泉山科技
 */
@Data
public class MemberUserRespDTO {

    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 帐号状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 创建时间（注册时间）
     */
    private LocalDateTime createTime;

    // ========== 其它信息 ==========

    /**
     * 会员级别编号
     */
    private Long levelId;

    /**
     * 积分
     */
    private Integer point;

    /**
     * 用户类型
     */
    private Integer memberType;

    private String openId;

}
