package com.ainutribox.module.member.dal.dataobject.order;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.ainutribox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户购买课程和VIP订单 DO
 *
 * @author 小泉山网络科技
 */
@TableName("member_order")
@KeySequence("member_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrderDO extends BaseDO {

    /**
     * 主键编号
     */
    @TableId
    private Long id;
    /**
     * 流水号
     */
    private String no;
    /**
     * 订单类型 1课程 2vip
     */
    private Integer type;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 订单来源终端
     */
    private Integer terminal;
    /**
     * 用户 IP
     */
    private String userIp;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 支付订单编号
     */
    private Long payOrderId;
    /**
     * 是否已支付：[0:未支付 1:已经支付过]
     */
    private Boolean payStatus;
    /**
     * 订单支付时间
     */
    private LocalDateTime payTime;
    /**
     * 支付成功的支付渠道
     */
    private String payChannelCode;
    /**
     * 课程编号或者vip套餐编号
     */
    private Long buyId;
    /**
     * 课程名称或者套餐名
     */
    private String buyName;
    /**
     * 应付金额（总），单位：分
     */
    private Integer payPrice;

    /**
     * 营养师编号
     */
    private Long dietitionId;

    /**
     * 持续月数 购买vip本字段会有值
     *
     */
    private Integer durationMonth;

    /**
     * 取下订单时间
     */
    private LocalDateTime cancelTime;

    /**
     * 取下类型
     */
    private Integer cancelType;
}