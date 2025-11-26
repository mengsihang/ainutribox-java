package com.ainutribox.module.member.dal.dataobject.spotclass;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.ainutribox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户点赞表 本表删除使用物理删除 DO
 *
 * @author 小泉山网络科技
 */
@TableName("member_spot_class")
@KeySequence("member_spot_class_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpotClassDO extends BaseDO {

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

}