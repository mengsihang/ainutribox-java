package com.ainutribox.module.member.dal.dataobject.dietition;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.ainutribox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 营养师信息 DO
 *
 * @author 小泉山网络科技
 */
@TableName("member_dietition_info")
@KeySequence("member_dietition_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietitionInfoDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 描述内容
     */
    private String content;
    /**
     * 评分
     */
    private Long score;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 经验
     */
    private Long experience;
    /**
     * 好评率
     */
    private Long favorableRate;
    /**
     * 咨询量
     */
    private Long adviceNum;
    /**
     * 案例数
     */
    private Long caseNum;
    /**
     * 营养师等级 0 初级 1 中级 2 高级
     *
     * 枚举
     */
    private Integer level;
    /**
     * 图片
     */
    private String pic;
    /**
     * 详情图片
     */
    private String infoPic;
    /**
     * 名片背景
     */
    private String cardPic;
    /**
     * 导师状态
     */
    private Integer status;

    /**
     * 手机号
     */
    private String phone;

}