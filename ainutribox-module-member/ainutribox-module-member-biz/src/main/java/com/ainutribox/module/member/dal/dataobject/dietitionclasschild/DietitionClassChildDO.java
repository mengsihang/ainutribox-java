package com.ainutribox.module.member.dal.dataobject.dietitionclasschild;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.ainutribox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 课程小节 DO
 *
 * @author 小泉山网络科技
 */
@TableName("member_dietition_class_child")
@KeySequence("member_dietition_class_child_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietitionClassChildDO extends BaseDO {

    /**
     * 主键编号
     */
    @TableId
    private Long id;
    /**
     * 课程主表id
     */
    private Long classId;
    /**
     * 小节名称
     */
    private String classHoureName;
    /**
     * 小节详情
     */
    private String classHoureDetail;
    /**
     * 营养师ID
     */
    private Long dietitionId;
    /**
     * 音频url
     */
    private String videoUrl;
    /**
     * 音频时长
     */
    private Integer videoTime;
    /**
     * 小节类型 0试听 1会员免费 2收费
     */
    private Integer childType;
    /**
     * 小节序号
     */
    private Integer serialNumber;
    /**
     * 状态 0上架 1下架
     */
    private Integer status;

}