package com.ainutribox.module.member.dal.dataobject.agent;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.ainutribox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 代理商记录 DO
 *
 * @author 小泉山网络科技
 */
@TableName("member_agent")
@KeySequence("member_agent_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentDO extends BaseDO {

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
     * 申请状态：0 未通过 1 已通过
     *
     * 枚举 {@link TODO apply_status 对应的类}
     */
    private Integer status;
    /**
     * 手机号
     */
    private Long tel;
    /**
     * 身份证正面
     */
    private String idPicFront;
    /**
     * 身份证反面
     */
    private String idPicBack;
    /**
     * 证书
     */
    private String certificatePic;

}