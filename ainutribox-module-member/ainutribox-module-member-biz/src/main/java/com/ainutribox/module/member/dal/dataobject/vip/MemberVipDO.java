package com.ainutribox.module.member.dal.dataobject.vip;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.ainutribox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户vip DO
 *
 * @author 小泉山网络科技
 */
@TableName("member_vip")
@KeySequence("member_vip_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberVipDO extends BaseDO {

    /**
     * 主键编号
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * vip开始时间
     */
    private LocalDateTime vipStartTime;
    /**
     * vip结束时间
     */
    private LocalDateTime vipEndTime;

}