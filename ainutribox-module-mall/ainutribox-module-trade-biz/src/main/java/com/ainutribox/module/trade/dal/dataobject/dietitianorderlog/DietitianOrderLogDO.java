package com.ainutribox.module.trade.dal.dataobject.dietitianorderlog;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.ainutribox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 营养师自组营养包售卖订单日志 DO
 *
 * @author 小泉山网络科技
 */
@TableName("trade_dietitian_order_log")
@KeySequence("trade_dietitian_order_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietitianOrderLogDO extends BaseDO {

    /**
     * 日志主键
     */
    @TableId
    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 营养师id
     */
    private Long dietitianId;
    /**
     * 订单号
     */
    private Long orderId;
    /**
     * 订单流水号
     */
    private String orderNo;
    /**
     * 状态 0不展示 1展示
     */
    private Integer showStatus;
    /**
     * 结算状态 0未结算 1结算
     */
    private Integer settlementStatus;
    /**
     * 应付金额（总），单位：分
     */
    private Integer payPrice;
    /**
     * 同一个订单下第几组整合
     */
    private Integer itemNumber;
    /**
     * 营养师组装商品名(dietitian_product)
     */
    private String dietitianSpuName;
    /**
     * 营养师组装商品编号(dietitian_product)
     */
    private Long dietitianSpuId;

}