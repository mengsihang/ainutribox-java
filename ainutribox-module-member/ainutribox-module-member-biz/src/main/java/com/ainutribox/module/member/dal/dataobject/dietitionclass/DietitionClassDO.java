package com.ainutribox.module.member.dal.dataobject.dietitionclass;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.ainutribox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 营养师课程 DO
 *
 * @author 小泉山网络科技
 */
@TableName("member_dietition_class")
@KeySequence("member_dietition_class_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietitionClassDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 列表图片
     */
    private String picUrl;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String description;
    /**
     * 状态 0上架 1下架
     */
    private Integer status;
    /**
     * 营养师ID
     */
    private Long dietitionId;
    /**
     * 课程标题
     */
    private String courseTitle;
    /**
     * 课程路径
     */
    private String classUrl;
    /**
     * 课程类型 0 图文 1 视频
     */
    private Integer classType;
    /**
     * 详情图片
     */
    private String infoPic;
    /**
     * 课程标签 字符串@分割
     */
    private String tags;
    /**
     * 评论数
     */
    private Long commentNum;
    /**
     * 点赞数
     */
    private Long likeNum;
    /**
     * 评分
     */
    private Integer score;
    /**
     * 实际人数
     */
    private Integer actualPeople;
    /**
     * 虚拟人数
     */
    private Integer virtualPeople;
    /**
     * 原价 存储单位分
     */
    private Integer price;
    /**
     * 会员价格 存储单位分
     */
    private Integer vipPrice;
    /**
     * 课程分类
     */
    private Long categoryId;

}