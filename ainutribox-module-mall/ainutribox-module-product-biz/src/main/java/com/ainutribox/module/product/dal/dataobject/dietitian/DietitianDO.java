package com.ainutribox.module.product.dal.dataobject.dietitian;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.ainutribox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 营养师自组营养包 DO
 *
 * @author 小泉山网络科技
 */
@TableName("product_dietitian")
@KeySequence("product_dietitian_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietitianDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 商品数据
     */
    private String productText;
    /**
     * 状态 1 上架（开启） 0 下架（禁用）
     */
    private Integer status;
    /**
     * 营养师id
     */
    private Long dietitianId;

}