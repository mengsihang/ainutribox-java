package com.ainutribox.module.member.dal.dataobject.code;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.ainutribox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 积分兑换码 DO
 *
 * @author 小泉山网络科技
 */
@TableName("member_code")
@KeySequence("member_code_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeDO extends BaseDO {

    /**
     * 自增主键
     */
    @TableId
    private Long id;
    /**
     * 可兑换积分数量
     */
    private Integer point;
    /**
     * 兑换码
     */
    private Integer code;
    /**
     * 状态
     *
     * 枚举
     */
    private Integer status;

}