package com.ainutribox.module.member.dal.dataobject.vippackage;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.ainutribox.framework.mybatis.core.dataobject.BaseDO;

/**
 * vip套餐 DO
 *
 * @author 小泉山网络科技
 */
@TableName("member_vip_package")
@KeySequence("member_vip_package_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VipPackageDO extends BaseDO {

    /**
     * 主键编号
     */
    @TableId
    private Long id;
    /**
     * 价格
     */
    private Integer price;
    /**
     * 持续月数 年套餐请填写12月
     */
    private Integer durationMonth;
    /**
     * 套餐名
     */
    private String packageName;
    /**
     * 套餐简介
     */
    private String packageBrief;
    /**
     * 活动优惠价格
     */
    private Integer activityPrice;

}