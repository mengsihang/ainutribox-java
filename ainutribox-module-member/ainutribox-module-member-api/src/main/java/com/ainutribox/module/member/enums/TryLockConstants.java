package com.ainutribox.module.member.enums;

import java.util.concurrent.TimeUnit;

/**
 * 锁前缀
 *
 * @author lucifer
 * @date 2024-07-01 10:50
 */
public interface TryLockConstants {

    /**
     * 锁默认时间数值
     */
    Long DEFAULT_LOCK_TIME = 10L;

    /**
     * 默认类型秒
     */
    TimeUnit DEFAULT_LOCK_TIMEUNIT = TimeUnit.SECONDS;

    /**
     * 锁默认值value
     */
    int DEFAULT_LOCK_VALUE = 1;

    /**
     * 评论锁
     */
    Long COMMENT_LOCK_TIME =2L;

    /**
     * 加入课程
     */
    String JOIN_CLASS_LOCK = "joinClass";

    /**
     * 移除课程
     */
    String CANCEL_CLASS_LOCK = "cancelClass";

    /**
     * 点赞
     */
    String SPOT_CLASS_LOCK = "cancelClass";

    /**
     * 点赞
     */
    String COMMENT_CLASS_LOCK = "commentClass";
}
