package com.ainutribox.module.member.dal.dataobject.payclass;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.ainutribox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户购买课程 DO
 *
 * @author 小泉山网络科技
 */
@TableName("member_pay_class")
@KeySequence("member_pay_class_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayClassDO extends BaseDO {

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
     * 课程id
     */
    private Long classId;
    /**
     * 订单ID 哪个订单买的
     */
    private Long orderId;
    /**
     * 老师ID
     */
    private Long dietition;

}